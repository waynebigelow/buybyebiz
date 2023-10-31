package ca.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import org.apache.commons.codec.binary.Base64;

import ca.app.model.common.HashToken;

public final class HashUtil {

	public static void main(String[] args) {
		
		String key = "cahospital";
		
		String encoded1 = getBase64Hash("constance.robertson@msicorp.ca_12/20/2012_0", key);
		String encoded2 = getBase64Hash("constance.robertson@msicorp.ca_12/20/2012_0", key, true);
		System.out.println("Non URL Safe: " + encoded1);
		System.out.println("URL Safe: " + encoded2);
		
		String decoded1 = getValueFromHash(encoded1, key);
		String decoded2 = getValueFromHash(encoded2, key);
		System.out.println("Non URL Safe (decoded): " + decoded1);
		System.out.println("URL Safe (decoded): " + decoded2);
	}
	
	public static String getSha1Hash(String original) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException ex) {
			// Ignored, since I know it's valid
		}

		md.update(original.getBytes());
		return hexStringFromBytes(md.digest());
	}
	
	private static String hexStringFromBytes(byte[] b) {
		char[] HEX_CHARS = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		
		String hex = "";
		int msb;
		int lsb = 0;
		int i;

		for (i = 0; i < b.length; i++) {
			msb = ((int) b[i] & 0x000000FF) / 16;
			lsb = ((int) b[i] & 0x000000FF) % 16;
			hex = hex + HEX_CHARS[msb] + HEX_CHARS[lsb];
		}
		return (hex);
	}
	
	public static String getBase64Hash(String toEncode, String key) {
		return getBase64Hash(toEncode, key, false);
	}
	
	public static String getBase64Hash(String toEncode, String key, boolean urlSafe) {
		try {
			byte[] keyBytes = key.getBytes("UTF-8");
			byte[] userBytes = toEncode.getBytes("UTF-8");
			byte[] encrypted = new byte[userBytes.length];
			
			for(int i = 0; i < userBytes.length; i++){
				encrypted[i] = (byte)(userBytes[i] ^ keyBytes[i % keyBytes.length]);
			}
			
			String encoded = null;
			if (urlSafe) {
				encoded = Base64.encodeBase64URLSafeString(encrypted);
			} else {
				encoded = Base64.encodeBase64String(encrypted);
			}
			
			return encoded.trim();
		} catch (Exception e) {
			LogUtil.logException(HashUtil.class, "", e);
			return null;
		}
	}
	
	public static String getValueFromHash(String hash,String key) {
		try {
			byte[] keyBytes = key.getBytes("UTF-8");
			byte[] decoded =  Base64.decodeBase64(hash);
			byte[] decrypted = new byte[decoded.length];
			
			for(int i = 0; i < decoded.length; i++){
				decrypted[i] = (byte)(decoded[i] ^ keyBytes[i % keyBytes.length] );
			}
			
			return new String(decrypted);
		} catch (Exception e) {
			LogUtil.logException(HashUtil.class, "", e);
			return null;
		}
	}
	
	public static HashToken getNewOneTimeToken(String toEncode, String key, int validLengthInMinutes) {
		return getOneTimeToken(toEncode, key, validLengthInMinutes, false);
	}
	
	public static HashToken getOneTimeToken(int userId, int typeId, String hashKey, int validLengthInMinutes) {
		HashToken token = new HashToken();
		token.setUserId(userId);
		token.setTypeId(typeId);
		token.setHash(hashKey);
		token.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		token.setExpiredDate(new Timestamp(token.getCreatedDate().getTime() + (validLengthInMinutes * 60000)));
		return token;
	}
	
	public static HashToken getOneTimeToken(String toEncode, String key, int validLengthInMinutes, boolean urlSafe) {
		HashToken token = new HashToken();
		token.setHash(HashUtil.getBase64Hash(toEncode, key, urlSafe));
		token.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		token.setExpiredDate(new Timestamp(token.getCreatedDate().getTime() + (validLengthInMinutes * 60000)));
		return token;
	}
}