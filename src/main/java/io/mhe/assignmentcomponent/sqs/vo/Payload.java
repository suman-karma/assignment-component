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
    String builder = "Payload [contentTO=" +
            contentTO +
            "]";
		return builder;
}
}
