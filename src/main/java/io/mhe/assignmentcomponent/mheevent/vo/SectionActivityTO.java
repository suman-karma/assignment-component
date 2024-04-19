package io.mhe.assignmentcomponent.mheevent.vo;

public class SectionActivityTO {
	private Long activityId;
	private Long sectionId;
	private String studentId;
	private Integer attemptNo;
	private Long assignmentId;
	
	public SectionActivityTO(Long activityId, Long sectionId, String studentId, Integer attemptNo, Long assignmentId) {
		super();
		this.activityId = activityId;
		this.sectionId = sectionId;
		this.studentId = studentId;
		this.attemptNo = attemptNo;
		this.assignmentId = assignmentId;
	}

	public SectionActivityTO() {
		super();
	}

	public Long getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getAttemptNo() {
		return attemptNo;
	}

	public void setAttemptNo(Integer attemptNo) {
		this.attemptNo = attemptNo;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
}
