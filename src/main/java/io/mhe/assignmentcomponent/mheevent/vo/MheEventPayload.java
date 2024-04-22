package io.mhe.assignmentcomponent.mheevent.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MheEventPayload {
@JsonProperty("mheEventContent")
private MheEventTO eventTO;

public MheEventTO getEventTO() {
		return eventTO;
}

public void setEventTO(MheEventTO eventTO) {
		this.eventTO = eventTO;
}

@Override
public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MheEventPayload [mheEventContent=");
		builder.append(eventTO);
		builder.append("]");
		return builder.toString();
}
}
