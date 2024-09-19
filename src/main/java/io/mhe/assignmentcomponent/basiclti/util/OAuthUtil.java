package io.mhe.assignmentcomponent.basiclti.util;


import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.signature.OAuthSignatureMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class OAuthUtil {

	private static final Logger logger = LoggerFactory.getLogger(OAuthUtil.class);
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	private OAuthUtil(){ }
	
	public static String generateSign(String methodType, String cunsumerKey,  String secretKey, Map<String,String> parameters, String uri)
	{
		return generateSign(methodType, cunsumerKey, secretKey, parameters, uri, null);

	}
	
	public static String generateSign(String methodType, String cunsumerKey,  String secretKey, Map<String,String> parameters, String uri, String requestBody){
		OAuthMessage authMessage = null;
		OAuthAccessor accessor = null;
		String signatureCreated = null;
		
		ByteArrayInputStream requestBodyAsInputStream = null;
		
		try {
			if(null != requestBody){
				requestBodyAsInputStream = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8));
			}
			authMessage = new OAuthMessage(methodType, uri, parameters.entrySet(), requestBodyAsInputStream);
			logger.debug("OAuth message{}",authMessage);
			
			OAuthConsumer consumer = new OAuthConsumer(null, cunsumerKey, secretKey, null);
			
			accessor = new OAuthAccessor(consumer);
			accessor.accessToken = "";
			authMessage.sign(accessor);
			
			logger.debug("base string {}",OAuthSignatureMethod.getBaseString(authMessage));
			
			signatureCreated = authMessage.getSignature();
			
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		return signatureCreated;
	}
	

	public static String getRandomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
}
