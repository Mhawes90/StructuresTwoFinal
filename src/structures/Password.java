package structures;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

/*
 * by Mark Hawes
 * 
 * Password class gets a Salt based on User ID
 */

public class Password {
	private String UID;
	private String Salt;
	
	public Password(){
		setSalt();
	}
	
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
	
	protected String getUID(){
		return UID;
	}

	protected void setUID(String UID){
		this.UID = UID;
	}
	
	public String getSalt(){
		return Salt;
	}
	
	public void newSalt(){
		setSalt();
	}
	
	private void setSalt(){
		Salt = complicateSalt();
	}
	
	private String complicateSalt(){
		return Integer.toHexString(generateSalt());
	}
	
	private int generateSalt(){
		Random rand = new Random();
		return rand.nextInt(1000)+1;
	}
}
