package ca.app.util;

import java.util.Random;

public class PasswordUtil {
	
	private static final String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String generatePassword() {
		Random rand = new Random(System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			int pos = rand.nextInt(charset.length());
			sb.append(charset.charAt(pos));
		}
		
		return sb.toString();
	}
}