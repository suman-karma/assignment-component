package io.mhe.assignmentcomponent.common.constant;

/**
 * Represents a validation error.
 * User: Stephen Lazaronak Date: 1/17/14
 */
public class MarathonValidationError {

	private Long						marathonID;
	private Long						bucketID;
	private String						bucketLabel;
	private Long						assignmentID;
	private MarathonValidationErrorEnum	errorType;
	private String						errorCode;
	private String						errorDescription;

	public MarathonValidationError(Long marathonID, Long bucketID, String bucketLabel,
			Long assignmentID, MarathonValidationErrorEnum errorType) {
		this.marathonID = marathonID;
		this.bucketID = bucketID;
		this.bucketLabel = bucketLabel;
		this.assignmentID = assignmentID;
		this.errorType = errorType;
		this.errorCode = errorType.getErrorCode();
		this.errorDescription = errorType.getErrorDescription();
	}

	public Long getMarathonID() {
		return marathonID;
	}

	public Long getBucketID() {
		return bucketID;
	}

	public String getBucketLabel() {
		return bucketLabel;
	}

	public Long getAssignmentID() {
		return assignmentID;
	}

	public MarathonValidationErrorEnum getErrorType() {
		return errorType;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

}
