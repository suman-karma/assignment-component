package io.mhe.assignmentcomponent.vo;

public class MarathonSection implements Model {
	private static final long	serialVersionUID	= 9149078819762160411L;
	
	private long				marathonId;
	private long				sectionId;
	private long				primaryInstructorId;
	private long				secondaryInstructorId;
	private String				isEditable;
	
	/**
	 * @return the marathonId
	 */
	public long getMarathonId() {
		return marathonId;
	}
	/**
	 * @param marathonId the marathonId to set
	 */
	public void setMarathonId(long marathonId) {
		this.marathonId = marathonId;
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
	/**
	 * @return the primaryInstructorId
	 */
	public long getPrimaryInstructorId() {
		return primaryInstructorId;
	}
	/**
	 * @param primaryInstructorId the primaryInstructorId to set
	 */
	public void setPrimaryInstructorId(long primaryInstructorId) {
		this.primaryInstructorId = primaryInstructorId;
	}
	/**
	 * @return the secondaryInstructorId
	 */
	public long getSecondaryInstructorId() {
		return secondaryInstructorId;
	}
	/**
	 * @param secondaryInstructorId the secondaryInstructorId to set
	 */
	public void setSecondaryInstructorId(long secondaryInstructorId) {
		this.secondaryInstructorId = secondaryInstructorId;
	}
	/**
	 * @return the isEditable
	 */
	public String getIsEditable() {
		return isEditable;
	}
	/**
	 * @param isEditable the isEditable to set
	 */
	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}
	
	
}