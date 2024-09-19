package io.mhe.assignmentcomponent.common.constant;

public enum MarathonValidationErrorEnum {

    /*
     * These messages shows what should be checked by a user, NOT an error.
     */
	ACTIVE_ASSIGNMENT_ERROR("M-001", "There are active assignments"),
	START_DATES_ERROR("M-002", "Start dates conflict"),
	NO_TITLE_ERROR("M-003", "The set has no title"),
	ASSIGNMENTS_MIN_COUNT_ERROR("M-004", "There are fewer than two assignments"),
	ASSIGNMENTS_MIN_SCORE_ERROR("M-005", "Minimum scores are blank or invalid"),
    NEW_ASSIGNMENTS_ERROR("M-006", "There are new assignments that precede active assignments");

	private final String	errorCode;
	private String	errorDescription;

	MarathonValidationErrorEnum(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String toString() {
		return this.errorCode;
	}

	public String getErrorDescription() {
		return this.errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
