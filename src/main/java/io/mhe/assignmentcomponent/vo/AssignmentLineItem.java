package io.mhe.assignmentcomponent.vo;

import java.util.Date;

public class AssignmentLineItem implements Model{
	
	private long id;
	private long assignmentId;
	private long assignmentLineItemTypeId;
	private PolicyInstanceSet policyInstanceSet;
	private String name;
	private Date createdDate;
	private Date updatedDate;
	private int draftNo;
	private long sectionId;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getAssignmentId() {
		return assignmentId;
	}
	
	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}
	
	public long getAssignmentLineItemTypeId() {
		return assignmentLineItemTypeId;
	}
	
	public void setAssignmentLineItemTypeId(long assignmentLineItemTypeId) {
		this.assignmentLineItemTypeId = assignmentLineItemTypeId;
	}
	
	public PolicyInstanceSet getPolicyInstanceSet() {
		return policyInstanceSet;
	}
	
	public void setPolicyInstanceSet(PolicyInstanceSet policyInstanceSet) {
		this.policyInstanceSet = policyInstanceSet;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getDraftNo() {
		return draftNo;
	}

	public void setDraftNo(int draftNo) {
		this.draftNo = draftNo;
	}

	@Override
	public String toString() {
	    return "AssignmentLineItem [id=" + id + ", assignmentId=" + assignmentId + ", assignmentLineItemTypeId=" + assignmentLineItemTypeId
		    + ", policyInstanceSet=" + policyInstanceSet + ", name=" + name + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
		    + ", draftNo=" + draftNo + "]";
	}

	/**
	 * @return the sectionId
	 */
	public long getSectionId() {
		return sectionId;
	}

	/**
	 * @param sectionId the sectionId to set
	 */
	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}
	
}
