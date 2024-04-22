package io.mhe.assignmentcomponent.sqs.vo;

import com.mhe.event.Event;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ReprocessGradeTO implements Event {

private ContentTO originalMessage;
private String errorCode;
private List<String> failedConnectUserIds;
private List<String> failedUserXids;
private String originalException;
private String eventType;
private String traceId;

public ContentTO getOriginalMessage() {
		return originalMessage;
}
public void setOriginalMessage(ContentTO originalMessage) {
		this.originalMessage = originalMessage;
}
public String getErrorCode() {
		return errorCode;
}
public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
}
public List<String> getFailedConnectUserIds() {
		return failedConnectUserIds;
}
public void setFailedConnectUserIds(List<String> failedConnectUserIds) {
		this.failedConnectUserIds = failedConnectUserIds;
}
public List<String> getFailedUserXids() {
		return failedUserXids;
}
public void setFailedUserXids(List<String> failedUserXids) {
		this.failedUserXids = failedUserXids;
}
public String getOriginalException() {
		return originalException;
}
public void setOriginalException(String originalException) {
		this.originalException = originalException;
}

public String getTraceId() {
		return traceId;
}
public void setTraceId(String traceId) {
		this.traceId = traceId;
}
@Override
public String getEventType() {
		return eventType;
}

@Override
public String toString()
{
		return ToStringBuilder.reflectionToString(this);
}
}
