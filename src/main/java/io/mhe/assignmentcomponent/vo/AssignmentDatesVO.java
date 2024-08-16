package io.mhe.assignmentcomponent.vo;

public class AssignmentDatesVO {
	private Long assignmentId;
	private Long sectionId;
	private String startDateStr;
	private String dueDateStr;
	private boolean isLateSubmissionEnabled;
	
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
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getDueDateStr() {
		return dueDateStr;
	}
	public void setDueDateStr(String dueDateStr) {
		this.dueDateStr = dueDateStr;
	}
	
	public boolean isLateSubmissionEnabled() {
		return isLateSubmissionEnabled;
	}
	public void setLateSubmissionEnabled(boolean isLateSubmissionEnabled) {
		this.isLateSubmissionEnabled = isLateSubmissionEnabled;
	}
	
	@Override
	public String toString() {
		return "AssignmentDatesVO [assignmentId=" + assignmentId + ", sectionId=" + sectionId + ", startDateStr="
				+ startDateStr + ", dueDateStr=" + dueDateStr + ", isLateSubmissionEnabled=" + isLateSubmissionEnabled
				+ "]";
	}
	
}
