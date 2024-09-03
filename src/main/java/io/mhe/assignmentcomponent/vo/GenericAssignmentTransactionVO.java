package io.mhe.assignmentcomponent.vo;

import java.util.ArrayList;
import java.util.List;

public class GenericAssignmentTransactionVO {

	private String	serviceConsumerName;
	private String	serviceProducerName;
	private Long	serviceConsumerTransactionId;
	private Long	serviceProducerTransactionId;
	private Long	sourceSectionId;
	private Long	sourceAssignmentId;
	private Long	destinationSectionId;
	private Long	destinationAssignmentId;
	private String	primaryInstructorId;
	private String	secondaryInstructorId;
	private String	mode;
	private String	message;
	private String	status;
	private String	courseTimeZone;
	private String	courseTimeZoneOffset;
	private String	isbn;
	private String	assignmentStartDate;
	private String	assignmentDueDate;
	private String	generic_late_submission;
	private List<Long> sharedSectionId = new ArrayList<Long>();
	private Long   sourceCourseId;
	private Long   destinationCourseId;
	
	public String getServiceConsumerName() {
		return serviceConsumerName;
	}

	public void setServiceConsumerName(String serviceConsumerName) {
		this.serviceConsumerName = serviceConsumerName;
	}

	public String getServiceProducerName() {
		return serviceProducerName;
	}

	public void setServiceProducerName(String serviceProducerName) {
		this.serviceProducerName = serviceProducerName;
	}

	public Long getServiceConsumerTransactionId() {
		return serviceConsumerTransactionId;
	}

	public void setServiceConsumerTransactionId(Long serviceConsumerTransactionId) {
		this.serviceConsumerTransactionId = serviceConsumerTransactionId;
	}

	public Long getServiceProducerTransactionId() {
		return serviceProducerTransactionId;
	}

	public void setServiceProducerTransactionId(Long serviceProducerTransactionId) {
		this.serviceProducerTransactionId = serviceProducerTransactionId;
	}

	public Long getSourceSectionId() {
		return sourceSectionId;
	}

	public void setSourceSectionId(Long sourceSectionId) {
		this.sourceSectionId = sourceSectionId;
	}

	public Long getSourceAssignmentId() {
		return sourceAssignmentId;
	}

	public void setSourceAssignmentId(Long sourceAssignmentId) {
		this.sourceAssignmentId = sourceAssignmentId;
	}

	public Long getDestinationSectionId() {
		return destinationSectionId;
	}

	public void setDestinationSectionId(Long destinationSectionId) {
		this.destinationSectionId = destinationSectionId;
	}

	public Long getDestinationAssignmentId() {
		return destinationAssignmentId;
	}

	public void setDestinationAssignmentId(Long destinationAssignmentId) {
		this.destinationAssignmentId = destinationAssignmentId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the primaryInstructorId
	 */
	public String getPrimaryInstructorId() {
		return primaryInstructorId;
	}

	/**
	 * @param primaryInstructorId the primaryInstructorId to set
	 */
	public void setPrimaryInstructorId(String primaryInstructorId) {
		this.primaryInstructorId = primaryInstructorId;
	}

	/**
	 * @return the secondaryInstructorId
	 */
	public String getSecondaryInstructorId() {
		return secondaryInstructorId;
	}

	/**
	 * @param secondaryInstructorId the secondaryInstructorId to set
	 */
	public void setSecondaryInstructorId(String secondaryInstructorId) {
		this.secondaryInstructorId = secondaryInstructorId;
	}
	
	

	/**
	 * @return the courseTimeZone
	 */
	public String getCourseTimeZone() {
		return courseTimeZone;
	}

	/**
	 * @param courseTimeZone the courseTimeZone to set
	 */
	public void setCourseTimeZone(String courseTimeZone) {
		this.courseTimeZone = courseTimeZone;
	}

	/**
	 * @return the time zone offset
	 */
	public String getCourseTimeZoneOffset() {
		return courseTimeZoneOffset;
	}
	/**
	 * @return the isbn
	 */
	public void setCourseTimeZoneOffset(String courseTimeZoneOffset) {
		this.courseTimeZoneOffset = courseTimeZoneOffset;
	}
	/**
	 * @param time zone
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the assignmentStartDate
	 */
	public String getAssignmentStartDate() {
		return assignmentStartDate;
	}

	/**
	 * @param assignmentStartDate the assignmentStartDate to set
	 */
	public void setAssignmentStartDate(String assignmentStartDate) {
		this.assignmentStartDate = assignmentStartDate;
	}

	/**
	 * @return the assignmentDueDate
	 */
	public String getAssignmentDueDate() {
		return assignmentDueDate;
	}

	/**
	 * @param assignmentDueDate the assignmentDueDate to set
	 */
	public void setAssignmentDueDate(String assignmentDueDate) {
		this.assignmentDueDate = assignmentDueDate;
	}

	/**
	 * @return the generic_late_submission
	 */
	public String getGeneric_late_submission() {
		return generic_late_submission;
	}

	/**
	 * @param generic_late_submission the generic_late_submission to set
	 */
	public void setGeneric_late_submission(String generic_late_submission) {
		this.generic_late_submission = generic_late_submission;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GenericAssignmentTransactionVO [serviceConsumerName=");
		builder.append(serviceConsumerName);
		builder.append(", serviceProducerName=");
		builder.append(serviceProducerName);
		builder.append(", serviceConsumerTransactionId=");
		builder.append(serviceConsumerTransactionId);
		builder.append(", serviceProducerTransactionId=");
		builder.append(serviceProducerTransactionId);
		builder.append(", sourceSectionId=");
		builder.append(sourceSectionId);
		builder.append(", sourceAssignmentId=");
		builder.append(sourceAssignmentId);
		builder.append(", destinationSectionId=");
		builder.append(destinationSectionId);
		builder.append(", destinationAssignmentId=");
		builder.append(destinationAssignmentId);
		builder.append(", primaryInstructorId=");
		builder.append(primaryInstructorId);
		builder.append(", secondaryInstructorId=");
		builder.append(secondaryInstructorId);
		builder.append(", mode=");
		builder.append(mode);
		builder.append(", message=");
		builder.append(message);
		builder.append(", status=");
		builder.append(status);
		builder.append(", courseTimeZone=");
		builder.append(courseTimeZone);
		builder.append(", isbn=");
		builder.append(isbn);
		builder.append(", assignmentStartDate=");
		builder.append(assignmentStartDate);
		builder.append(", assignmentDueDate=");
		builder.append(assignmentDueDate);
		builder.append(", generic_late_submission=");
		builder.append(generic_late_submission);
		builder.append(", sourceCourseId=");
        builder.append(sourceCourseId);
        builder.append(", destinationCourseId=");
        builder.append(destinationCourseId);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the sharedSectionId
	 */
	public List<Long> getSharedSectionId() {
		return sharedSectionId;
	}

	/**
	 * @param sharedSectionId the sharedSectionId to set
	 */
	public void setSharedSectionId(List<Long> sharedSectionId) {
		this.sharedSectionId = sharedSectionId;
	}

    public Long getSourceCourseId() {
        return sourceCourseId;
    }

    public void setSourceCourseId(Long sourceCourseId) {
        this.sourceCourseId = sourceCourseId;
    }

    public Long getDestinationCourseId() {
        return destinationCourseId;
    }

    public void setDestinationCourseId(Long destinationCourseId) {
        this.destinationCourseId = destinationCourseId;
    }
}
