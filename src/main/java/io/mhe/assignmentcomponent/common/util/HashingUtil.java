package io.mhe.assignmentcomponent.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

/**
 * Some utility methods for digesting info using MD5.
 */
public final class HashingUtil {

private HashingUtil(){
		//Not invoked
}
public static final String CHAR_SET_UTF_8 = "UTF-8";
public static final String CHAR_SET_ISO_8859_1 = "ISO-8859-1";

public static final String HASH_ALGO_MD5 = "MD5";
public static final String HASH_ALGO_SHA1 = "SHA-1";

private static final int SHA_HASH_BUFFER_SIZE = 40;
private static final int HALF_BYTE = 4;
private static final int HEX_BOUNDRY_CONST = 0x0F;
private static final int HEX_LAST_SINGLE_DIGIT = 9;
private static final int HEX_FIRST_DOUBLE_DIGIT = 10;

private static final char[] hex = {
			'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
};

/**
 * Turns array of bytes into string representing each byte as unsigned hex number.
 * 
 * @param hash array of bytes to convert to hex-string
 * @return generated hex string
 */
public static final String toHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);

		for (int idx = 0; idx < hash.length; idx++) {
			buf.append(hex[(hash[idx] >> 4) & 0x0f]).append(hex[hash[idx] & 0x0f]);
		}

		return buf.toString();
}

/**
 * Digest the input.
 * 
 * @param input the data to be digested.
 * @return the md5-digested input
 */
public static final byte[] digest(byte[] input) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(HASH_ALGO_MD5);
			return md5.digest(input);
		} catch (NoSuchAlgorithmException nsae) {
			throw new Error(nsae);
		}
}

/**
 * Digest the input.
 * 
 * @param input1 the first part of the data to be digested.
 * @param input2 the second part of the data to be digested.
 * @return the md5-digested input
 */
public static final byte[] digest(byte[] input1, byte[] input2) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(HASH_ALGO_MD5);
			md5.update(input1);
			return md5.digest(input2);
		} catch (NoSuchAlgorithmException nsae) {
			throw new Error(nsae);
		}
}

/**
 * Digest the input.
 * 
 * @param input the data to be digested.
 * @return the md5-digested input as a hex string
 */
public static final String hexDigest(byte[] input) {
		return toHex(digest(input));
}

/**
 * Digest the input.
 * 
 * @param input1 the first part of the data to be digested.
 * @param input2 the second part of the data to be digested.
 * @return the md5-digested input as a hex string
 */
public static final String hexDigest(byte[] input1, byte[] input2) {
		return toHex(digest(input1, input2));
}

/**
 * Digest the input.
 * 
 * @param input the data to be digested.
 * @return the md5-digested input as a hex string
 */
public static final byte[] digest(String input) {
		try {
			return digest(input.getBytes(CHAR_SET_UTF_8));
		} catch (UnsupportedEncodingException uee) {
			throw new Error(uee.toString(),uee);
		}
}

/**
 * Digest the input.
 * 
 * @param input the data to be digested.
 * @return the md5-digested input as a hex string
 */
public static final String hexDigest(String input) {
		try {
			return toHex(digest(input.getBytes(CHAR_SET_UTF_8)));
		} catch (UnsupportedEncodingException uee) {
			throw new Error(uee.toString(),uee);
		}
}

public static boolean verifyData(String hash, String data, String pp) {
		String msgId = getHexDigest(data, pp);
		return msgId.equals(hash);
}

public static String getParamValue(String paramName, String queryStr) {
		StringTokenizer strTok = new StringTokenizer(queryStr, "&");
		while (strTok.hasMoreTokens()) {
			String data[] = strTok.nextToken().split("=");
			if (data[0].equalsIgnoreCase(paramName)) {
				try {
					return data[1];
				} catch (ArrayIndexOutOfBoundsException aex) {
					return "";
				}
			}
		}
		return null;
}

/**
 * Notice that this method actually digest twice.
 * 
 * @param qs
 * @param ps
 * @return
 */
public static String getHexDigest(String qs, String ps) {
		String msgId = "default";
		String convertString = ps + "" + qs;
		// digest once
		byte res[] = HashingUtil.digest(convertString.getBytes());
		// digest twice
		msgId = HashingUtil.hexDigest(res);

		return msgId;
}

/**
 * Generate MD 5 string ( digest once )
 * 
 * @param qs
 * @param ps
 * @return
 */
public static String getDigest(String qs, String ps) {
		String msgId = "default";
		String convertString = ps + "" + qs;
		byte res[] = HashingUtil.digest(convertString.getBytes());
		msgId = toHex(res);
		return msgId;
}

/**
 * A keyed digest is one in which a secret key is used to create a digest for a buffer of bytes. You can use
 * different keys to create different digests for the same buffer of bytes.
 */
public static byte[] getKeyedDigest(String passphrase, String sk) {
		byte[] buffer = passphrase.getBytes();
		byte[] key = sk.getBytes();
		try {
			MessageDigest md5 = MessageDigest.getInstance(HASH_ALGO_MD5);
			md5.update(buffer);
			return md5.digest(key);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
}

/**
 * This method will generate 'SHA-1' hash for the given "msg". It uses "UTF-8" as default encoding character set
 * 
 * @param msg text message/password for hash generation
 * @return the 'SHA-1' hash generated for the given "msg"
 * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException
 */
public static String getSHA1Digest(String msg)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return getSHA1Digest(msg, CHAR_SET_UTF_8);
}

/**
 * This method will generate 'SHA-1' hash for the given "msg" and "charSet".
 * 
 * @param msg text message/password for hash generation
 * @param charSet character set for encoding
 * @return the 'SHA-1' hash generated for the given "msg" and "charSet"
 * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException
 */
public static String getSHA1Digest(String msg, String charSet)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance(HASH_ALGO_SHA1);
		byte[] sha1Hash = new byte[SHA_HASH_BUFFER_SIZE];
		md.update(msg.getBytes(charSet), 0, msg.length());
		sha1Hash = md.digest();
		return convertToHex(sha1Hash);
}

/**
 * This method converts the byte array to string
 * 
 * @param data byte array
 * @return a converted string
 */
private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> HALF_BYTE) & HEX_BOUNDRY_CONST;
			int twoHalfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= HEX_LAST_SINGLE_DIGIT)) {
					buf.append((char) ('0' + halfbyte));
				} else {
					buf.append((char) ('a' + (halfbyte - HEX_FIRST_DOUBLE_DIGIT)));
				}
				halfbyte = data[i] & HEX_BOUNDRY_CONST;
			} while (twoHalfs++ < 1);
		}
		return buf.toString();
}

public static void main(String args[]) throws UnsupportedEncodingException {
		// this is the common para phrase shared and knowd to MH and PR only
		String paraphrase = "secret";

		String data = "a=b&c=d";
		// this is generated at MH before forwarding to PR
		String id = getHexDigest(data, paraphrase);
		// data is encoded to go as a single http parameter
		String encData = URLEncoder.encode(data, CHAR_SET_UTF_8);
		// the query string to the PR gateway is of format
		// pr will decode the data
		String dataAtPr = URLDecoder.decode(encData, CHAR_SET_UTF_8);
		// pr verifies the authenticity of data
		boolean valid = verifyData(id, dataAtPr, paraphrase);
		// after this PR will do a call to the session verification servlet in CW
		// to verify if the session at classware exists

		// Example for using private key for MD5
		String dataString = "redirect=http://www.highedmath.aleks.com/alekscgi/x/Isl.exe/1SRvFkcbndxQnJi64v-fsmXgmRdgCOsaW1-OWWjU9isg2QYj8qYiXBiLUhIviLHEgilzuSxBizP7nF4nU6yJO6fWhihcIY9Dkqasdwvzvb78pq3ywU72QvdKSHIpQ4h9?1AAYHYnSQ5_LDKt7VKPLbodYCfF0SO9xk2qpEyQUm9kEmeVs&date=2005-08-11";
		String secretKey = "test123";

		byte messageDigest[] = getKeyedDigest(dataString, secretKey);
		String hexstring = toHex(messageDigest);

		/** Get MD5 string */
		String queryParameters = "&userId=" + 8107 + "&sectionId=" + 336700 + "&isbn=" + 0072363711;
		String MD5String = getDigest(queryParameters, "Madison Square Gardens");

}
}
