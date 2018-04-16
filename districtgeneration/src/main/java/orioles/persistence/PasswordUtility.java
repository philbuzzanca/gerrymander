package orioles.persistence;

import orioles.constants.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordUtility {
	private static final Random SALT_GENERATOR = new SecureRandom();

	public String getHashedPassword(String userPassword) {
		return getHashedPassword(userPassword, getSalt());
	}

	public String getHashedPassword(String userPassword, String salt) {
		byte[] hash = hashPassword(userPassword, salt);
		return hash != null ? String.format("%064x", new java.math.BigInteger(1, hash)) : null;
	}

	private String getSalt() {
		byte[] salt = new byte[Constants.SALT_SIZE];
		SALT_GENERATOR.nextBytes(salt);
		return new String(salt);
	}

	private byte[] hashPassword(String password, String salt) {
		try {
			String toHash = password + salt;
			MessageDigest hashAlgo = MessageDigest.getInstance(Constants.HASHING_ALGORITHM);
			return hashAlgo.digest(toHash.getBytes(Constants.CHARSET));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
