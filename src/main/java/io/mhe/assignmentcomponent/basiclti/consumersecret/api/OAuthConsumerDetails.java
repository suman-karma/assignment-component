package io.mhe.assignmentcomponent.basiclti.consumersecret.api;

/**
 * @author Praveen Meruga
 */

/**
 * The same bean is also used by Open API.
 * TODO: We should consolidate both into One and repackage this.
 */
public class OAuthConsumerDetails {

    private String algorithm;
    private String version;
    private Boolean nonceReqd;
    private Boolean is2legged;
    private Boolean oauthReqd;
    private long windowSize;
    private String secret;
    private String consumerKey;
    private String consumerName;

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getNonceReqd() {
        return nonceReqd;
    }

    public void setNonceReqd(Boolean nonceReqd) {
        this.nonceReqd = nonceReqd;
    }

    public Boolean getIs2legged() {
        return is2legged;
    }

    public void setIs2legged(Boolean is2legged) {
        this.is2legged = is2legged;
    }

    public Boolean getOauthReqd() {
        return oauthReqd;
    }

    public void setOauthReqd(Boolean oauthReqd) {
        this.oauthReqd = oauthReqd;
    }

    public long getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(long windowSize) {
        this.windowSize = windowSize;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    @Override
    public String toString() {
        return "OAuthConsumerDetails{" +
                "algorithm='" + algorithm + '\'' +
                ", version='" + version + '\'' +
                ", nonceReqd=" + nonceReqd +
                ", is2legged=" + is2legged +
                ", oauthReqd=" + oauthReqd +
                ", windowSize=" + windowSize +
                ", secret='" + secret + '\'' +
                ", consumerKey='" + consumerKey + '\'' +
                ", consumerName='" + consumerName + '\'' +
                '}';
    }
}
