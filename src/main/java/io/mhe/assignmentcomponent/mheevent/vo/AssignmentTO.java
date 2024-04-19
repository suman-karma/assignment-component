package io.mhe.assignmentcomponent.mheevent.vo;

public class AssignmentTO {
	private Long assignmentId;
	private Long sectionId;
	
	public AssignmentTO(Long assignmentId, Long sectionId) {
		this.assignmentId = assignmentId;
		this.sectionId = sectionId;
	}
	
	public Long getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
}
