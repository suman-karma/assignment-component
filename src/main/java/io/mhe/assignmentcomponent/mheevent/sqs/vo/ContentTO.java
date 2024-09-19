package io.mhe.assignmentcomponent.mheevent.sqs.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;

@JsonInclude(Include.NON_EMPTY)
public class ContentTO {
private long sectionId = 0L;
private long activityId = 0L;
private String userId = "";
private String nativeAlaId;
private long assignmentId = 0L;
private long sectionLineItemActivityId = 0L;
private Date modifiedDate;
private int attemptNo;
private String eventType;
private int retryCount = 0;

public long getSectionId() {
		return sectionId;
}
public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
}
public long getActivityId() {
		return activityId;
}
public void setActivityId(long activityId) {
		this.activityId = activityId;
}
public String getUserId() {
		return userId;
}
public void setUserId(String userId) {
		this.userId = userId;
}
public String getNativeAlaId() {
		return nativeAlaId;
}
public void setNativeAlaId(String nativeAlaId) {
		this.nativeAlaId = nativeAlaId;
}
public long getAssignmentId() {
		return assignmentId;
}
public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
}
public long getSectionLineItemActivityId() {
		return sectionLineItemActivityId;
}
public void setSectionLineItemActivityId(long sectionLineItemActivityId) {
		this.sectionLineItemActivityId = sectionLineItemActivityId;
}
public Date getModifiedDate() {
		return modifiedDate;
}
public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
}



public ContentTO() {
		super();
}

public ContentTO(long sectionId, long activityId, int attemptNo, String userId) {
		super();
		this.sectionId = sectionId;
		this.activityId = activityId;
		this.userId = userId;
		this.attemptNo = attemptNo;
}

public ContentTO(long sectionId, long assignmentId) {
		super();
		this.sectionId = sectionId;
		this.assignmentId = assignmentId;
}

@Override
public String toString() {
    String builder = "ContentTO [sectionId=" +
            sectionId +
            ", activityId=" +
            activityId +
            ", userId=" +
            userId +
            ", nativeAlaId=" +
            nativeAlaId +
            ", assignmentId=" +
            assignmentId +
            ", sectionLineItemActivityId=" +
            sectionLineItemActivityId +
            ", modifiedDate=" +
            modifiedDate +
            ", attemptNo=" +
            attemptNo +
            ", eventType=" +
            eventType +
            ", retryCount=" +
            retryCount +
            "]";
		return builder;
}
public int getAttemptNo() {
		return attemptNo;
}
public void setAttemptNo(int attemptNo) {
		this.attemptNo = attemptNo;
}
public String getEventType() {
		return eventType;
}
public void setEventType(String eventType) {
		this.eventType = eventType;
}
public int getRetryCount() {
		return retryCount;
}
public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
}
}
