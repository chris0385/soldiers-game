package de.chris0385.lobby;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

public class UserManager {

	public Optional<User> login(String name, String password) {
		String passwordKey = toSHA1(name + "//" + password);
		//TODO:
		return null;
	}
	
	public static String toSHA1(String str) {
	    try {
	    	MessageDigest md = null;
	        md = MessageDigest.getInstance("SHA-1");
	        byte[] digest = md.digest(str.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(digest);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace(); // TODO
			throw new RuntimeException(e);
		}
	}
}
