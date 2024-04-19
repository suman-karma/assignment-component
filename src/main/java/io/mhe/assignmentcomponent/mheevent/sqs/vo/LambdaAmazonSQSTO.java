package io.mhe.assignmentcomponent.mheevent.sqs.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LambdaAmazonSQSTO
 {
	@JsonProperty("sqs_end_point")
	private String sqsEndPoint = "";
	
	@JsonProperty("sqs_region")
	private String sqsRegion = "";
	
	@JsonProperty("compress_above")
	private int compressAbove = 0;
	
	@JsonProperty("payload")
	private Payload payload;
	
	@JsonProperty("accessKey")
    private String accessKey;
    
    @JsonProperty("secretKey")
    private String secretKey;
    
    @JsonProperty("connectionReadTimeout")
	private String connectionReadTimeout;
	
	@JsonProperty("requestTimeout")
	private String requestTimeout;

	public String getSqsEndPoint() {
		return sqsEndPoint;
	}

	public void setSqsEndPoint(String sqsEndPoint) {
		this.sqsEndPoint = sqsEndPoint;
	}

	public String getSqsRegion() {
		return sqsRegion;
	}

	public void setSqsRegion(String sqsRegion) {
		this.sqsRegion = sqsRegion;
	}

	public int getCompressAbove() {
		return compressAbove;
	}

	public void setCompressAbove(int compressAbove) {
		this.compressAbove = compressAbove;
	}
	
	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public String getConnectionReadTimeout() {
		return connectionReadTimeout;
	}

	public void setConnectionReadTimeout(String connectionReadTimeout) {
		this.connectionReadTimeout = connectionReadTimeout;
	}

	public String getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(String requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AmazonSQSTO [sqsEndPoint=").append(sqsEndPoint).append(", sqsRegion=").append(sqsRegion).append(", compressAbove=")
				.append(compressAbove).append(", payload=").append(payload).append(", accessKey=").append(accessKey).append(", secretKey=")
				.append(secretKey).append(", connectionReadTimeout=").append(connectionReadTimeout).append(", requestTimeout=")
				.append(requestTimeout).append("]");
		return builder.toString();
	}

    
}
