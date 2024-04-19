package io.mhe.assignmentcomponent.sqs.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payload {
	@JsonProperty("content")
	private ContentTO contentTO;

	public ContentTO getContentTO() {
		return contentTO;
	}

	public void setContentTO(ContentTO contentTO) {
		this.contentTO = contentTO;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Payload [contentTO=");
		builder.append(contentTO);
		builder.append("]");
		return builder.toString();
	}
}
