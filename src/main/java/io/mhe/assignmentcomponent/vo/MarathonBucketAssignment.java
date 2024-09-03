package io.mhe.assignmentcomponent.vo;



import io.mhe.assignmentcomponent.common.IMarathonVisitor;
import io.mhe.assignmentcomponent.common.constant.Visitable;

import java.util.Date;

public class MarathonBucketAssignment implements Model, Visitable<IMarathonVisitor> {

	private static final long	serialVersionUID	= 9143308819762160411L;

	private long				assignmentId;
	private Date				dueDate;
	private Date				startDate;
	private String				title;
	private float				minScore			= 0.0f;
	private long				assignmentOrder;
	private String				status;
	//private AssignmentType 		assignmentType 		= "FLAGGED;
	private String 		assignmentType 		= "FLAGGED";
	private boolean 			manuallyGraded = false;
	private String 				categoryType;
	private String				providerType;
	private String				assignmentTypeIconClass;


	// Shows is the appropriate assignment is active or not
	// Should be an Object as we don't obtain this value in some cases
	private Boolean				isActive;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @return the assignmentId
	 */
	public long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the minScore
	 */
	public float getMinScore() {
		return minScore;
	}

	/**
	 * @param minScore the minScore to set
	 */
	public void setMinScore(float minScore) {
		this.minScore = minScore;
	}

	/**
	 * @return the assignment_order
	 */
	public long getAssignmentOrder() {
		return assignmentOrder;
	}

	/**
	 * @param assignmentOrder the assignment_order to set
	 */
	public void setAssignmentOrder(long assignmentOrder) {
		this.assignmentOrder = assignmentOrder;
	}

	@Override
	public void accept(IMarathonVisitor visitor) {
		visitor.visitMarathonAssignment(this);
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	/**
	 * @return the manuallyGraded
	 */
	public boolean isManuallyGraded() {
		return manuallyGraded;
	}

	/**
	 * @param manuallyGraded the manuallyGraded to set
	 */
	public void setManuallyGraded(boolean manuallyGraded) {
		this.manuallyGraded = manuallyGraded;
	}

	/**
	 * @return the categoryType
	 */
	public String getCategoryType() {
		return categoryType;
	}

	/**
	 * @param categoryType the categoryType to set
	 */
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * @return the providerType
	 */
	public String getProviderType() {
		return providerType;
	}

	/**
	 * @param providerType the providerType to set
	 */
	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	
	public String getAssignmentTypeIconClass() {
		return assignmentTypeIconClass;
	}

	public void setAssignmentTypeIconClass(String assignmentTypeIconClass) {
		this.assignmentTypeIconClass = assignmentTypeIconClass;
	}
	
}
