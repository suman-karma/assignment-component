package io.mhe.assignmentcomponent.basiclti.consumersecret.api;



import java.util.List;

public interface LMSConsumerService {
       	
       /**
	 * Gets a consumer key, returns null if none found.
	 *
         * @param consumerName
         * @return a key if one is stored, null if the name does not have a key assiciated.
	 */	
       String getConsumerKey(String consumerName);
	
	/**
	 * Gets a consumer secret, returns null if none found.
	 *
     * @param consumerKey
     * @return a secret if one is stored, null if the key does not have a secret assiciated.
	 */
	String getConsumerSecret(String consumerKey) ;

	/**
	 * Fetches all the available secrets.
	 * @return
	 */





	/**
	 * Stores a consumer secret. Also supports the implicit deletion by setting the secret to null or an empty string.
	 * @param consumerKey, the key, may never be null.
	 * @param consumerSecret, the secret, can be null or empty string in order to delete the key/secret association.
	 */
	void setConsumerSecret(String consumerKey, String consumerSecret);

    OAuthConsumerDetails getConsumerDetail(String consumerKey) ;
    
}
