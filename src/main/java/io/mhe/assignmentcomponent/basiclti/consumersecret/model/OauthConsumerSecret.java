package io.mhe.assignmentcomponent.basiclti.consumersecret.model;

public class OauthConsumerSecret {
	private String consumerKey;
	private String consumerSecret;
	public OauthConsumerSecret(){}
	public OauthConsumerSecret(String consumerKey, String consumerSecret) {
	    super();
	    this.consumerKey = consumerKey;
	    this.consumerSecret = consumerSecret;
    }
	public String getConsumerKey() {
    	return consumerKey;
    }
	public String getConsumerSecret() {
    	return consumerSecret;
    }
	public void setConsumerKey(String consumerKey) {
    	this.consumerKey = consumerKey;
    }
	public void setConsumerSecret(String consumerSecret) {
    	this.consumerSecret = consumerSecret;
    }
}
