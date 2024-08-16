package io.mhe.assignmentcomponent.common.constant;

public enum AssignmentStatusEnum {

	MARATHON_ASSOCIATED_UNLOCKED("0"),
	MARATHON_ASSOCIATED_LOCKED("1"),
	MARATHON_NOT_ASSOCIATED("2"),
	MARATHON_ASSOCIATED("3");

	private String statusCode;

	private AssignmentStatusEnum(String s) {
		statusCode = s;
	}

	public String getStatusCode() {
		return statusCode;
	}
}