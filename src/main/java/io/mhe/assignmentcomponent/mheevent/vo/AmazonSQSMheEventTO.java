package io.mhe.assignmentcomponent.mheevent.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.mhe.assignmentcomponent.mheevent.sqs.vo.LambdaAmazonSQSTO;

public class AmazonSQSMheEventTO extends LambdaAmazonSQSTO {

	@JsonProperty("mheEventPayload")
	private MheEventPayload mheEventPayload;

	public MheEventPayload getMheEventPayload() {
		return mheEventPayload;
	}

	public void setMheEventPayload(MheEventPayload mheEventPayload) {
		this.mheEventPayload = mheEventPayload;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("AmazonSQSMheEventTO [mheEventPayload=").append(mheEventPayload).append("]");
		return builder.toString();
	}

}
