package io.mhe.assignmentcomponent.sqs.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;

@JsonInclude(Include.NON_EMPTY)
public class ContentTO {
	private long sectionId = 0l;
	private long activityId = 0l;
	private String userId = "";
	private String nativeAlaId;
	private long assignmentId = 0l;
	private long sectionLineItemActivityId = 0l;
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
		StringBuilder builder = new StringBuilder();
		builder.append("ContentTO [sectionId=");
		builder.append(sectionId);
		builder.append(", activityId=");
		builder.append(activityId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", nativeAlaId=");
		builder.append(nativeAlaId);
		builder.append(", assignmentId=");
		builder.append(assignmentId);
		builder.append(", sectionLineItemActivityId=");
		builder.append(sectionLineItemActivityId);
		builder.append(", modifiedDate=");
		builder.append(modifiedDate);
		builder.append(", attemptNo=");
		builder.append(attemptNo);
		builder.append(", eventType=");
		builder.append(eventType);
		builder.append(", retryCount=");
		builder.append(retryCount);
		builder.append("]");
		return builder.toString();
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
