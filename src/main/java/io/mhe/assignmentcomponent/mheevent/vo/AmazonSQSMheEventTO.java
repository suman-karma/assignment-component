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
    return super.toString() + "AmazonSQSMheEventTO [mheEventPayload=" + mheEventPayload + "]";
}

}
