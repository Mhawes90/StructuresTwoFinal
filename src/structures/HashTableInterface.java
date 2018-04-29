package structures;

/*
 * by Mark Hawes
 * 
 * HashTableInterface is the base interface for the hashtable class
 */

public interface HashTableInterface<K, V> {

	String hashFunction(String in);
	void displayHashTable();
	void changePassword(String UID,String password);
	void makePassword(String UID, String password, String oldPW);
	public boolean validatePassword(String UID, String password);
	public boolean isUnique(String in);
}