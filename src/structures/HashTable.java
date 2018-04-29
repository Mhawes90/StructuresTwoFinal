package structures;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

/*
 * by Mark Hawes
 * 
 * HashTable class holds all User IDs that are created
 */

public class HashTable<K, V> implements HashTableInterface<K, V>{
	private HashMap<String, String> map = new HashMap<String, String>();
	private HashMap<String, String> saltMap = new HashMap<String, String>();
	public LinkedList<String> lList = new LinkedList<String>();
	Password pass = new Password();
	public HashMap<String, LinkedList> chainMap = new HashMap<>();
	public LinkedList iDChain = new LinkedList();
	
	// displays hashtable
	public void displayHashTable(){
		System.out.println(map.toString());
	}
	
	// hashes input string
	public String hashFunction(String in) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] byteHash = digest.digest(in.getBytes(StandardCharsets.UTF_8));
		String hash = DatatypeConverter.printBase64Binary(byteHash);
		return hash;
	}
	
	// prompt for password to be entered in order to be checked
	public String enterPassword(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter password");
		String pw = (String)scan.next();
		return pw;
	}
	
	// changes password for an existing userID
	public void changePassword(String ID, String password){
		boolean changed = false;
		String temp = null;
		
		for (Entry<String, String> entry : map.entrySet())
			if (entry.getValue().equals(ID))
				temp = entry.getKey();
		saltMap.remove(ID);
		while(!changed){
			updatePassword(ID, password, temp);
			validatePassword(ID, password);
			changed = true;
		}
		System.out.println("Thank you for using Login.java");
	}
	
	// creates a password for a new userID
	public void makePassword(String ID, String password, String oldPW){
		if(isUnique(ID)){
			pass.setUID(ID);
			pass.newSalt();
			String pHash = hashFunction(password + pass.getSalt());
			map.remove(oldPW);
			map.put(pHash, ID);
			saltMap.put(ID, pass.getSalt());
		}else{
			System.out.println("Invalid ID, cannot create password");
		}
	}
	
	// created in case I couldn't fully get makePassword work with updating the password.
	public void updatePassword(String ID, String password, String oldPW){
		pass.setUID(ID);
		pass.newSalt();
		String pHash = hashFunction(password + pass.getSalt());
		map.remove(oldPW);
		map.put(pHash, ID);
		saltMap.put(ID, pass.getSalt());
	}

	// validates that password is currect/valid
	@Override
	public boolean validatePassword(String UID, String password) {
		if(map.containsKey(hashFunction(password + saltMap.get(UID))) && 
				map.get(hashFunction(password + saltMap.get(UID))).equals(UID)){
			System.out.println("Authentication passed");
			return true;
		}else{
			System.out.println("Authentication failed");
			return false;
		}
	}

	// return whether the userID is unique
	// designed to take in userID and check the hashmap for it
	@Override
	public boolean isUnique(String in) {
		return !map.containsValue(in);
	}
	
	// adds user to linked list hashmap chain
	// borrowed from Zack Albers
	public void addUserToChain(String userID, String results) {

		// System.out.println("Please enter desired user Id");
		// user1 = scan.next();

		iDChain = chainMap.get(userID);
		if (iDChain == null) {
			iDChain = new LinkedList();
			iDChain.add(0, results);
			chainMap.put(userID, iDChain);
		}

		iDChain.add(0,results);
	}
}
