package io.mhe.assignmentcomponent.constant;

public final class PoliciesServiceConstants {
	public static final String INPUT_DATA = "INPUT_DATA";
	public static final String ASSIGNMENT_MODULE_TYPE = "default";
	public static final String KEY_NEW_CATEGORY = "newcategory";
	public static final String KEY_CURRENT_MODULE = "currentModule";
	public static final String KEY_NEW_ASSIGNMENT_ID = "newAssignmentId";
	public static final String KEY_SET_IN_SESSION_SCOPE = "setInSessionScope";
	public static final String KEY_REMOVE_FROM_SESSION_SCOPE = "removeFromSessionScope";
	public static final String TEXTFLOW_COPY_ASSIGNMENT_INFO = "TextFlowCopyAssignmentInfo";
	public static final String KEY_RESPONSE_SEND_REDIRECT = "responseSendRedirect";

	public static final String FROM_PAGE_POLICIES = "policies";
	public static final String FROM_PAGE_WEB_ASSIGNMENT = "webAssignment";
	public static final String FROM_PAGE_TEXTFLOW_ASSIGNMENT_WITH_UUID = "assignmentWithUuid";
	public static final String WEB_MODE_CREATE = "create";
	public static final String ACTION_SAVE_EXIT = "SaveExit";
	public static final String ACTION_SET_POLICIES = "setPolicies";
	public static final String STATUS_HIDDEN = "Hidden";
	public static final String STATUS_PUBLISH = "publish";

	public static final String EDIT_ALL_POLICIES = "EDIT_ALL_POLICIES";
	public static final String EDIT_ONLY_DUE_DATES = "EDIT_ONLY_DUE_DATES";
	public static final String EDIT_NONE = "EDIT_NONE";

	public static final String P_STARTDATE = "p_startdate";
	public static final String P_DUEDATE = "p_duedate";
	public static final String P_PASSWORD = "p_password";
	public static final String P_STRICTDUE = "p_strictdue";
	public static final String P_DEDUCT_LATE = "p_deduct_late";
	public static final String P_DEDUCT_LATE_INCREMENT = "p_deduct_late_increment";

	public static final String P_FB_IGNOREACCENTS = "p_fb_ignoreaccents";
	public static final String P_FB_IGNORESPACING = "p_fb_ignorespacing";
	public static final String P_FB_IGNORECASE = "p_fb_ignorecase";

	public static final String FORCEGRADING_STATUS_NOT_STARTED = "NotStarted";
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YES = "YES";
	public static final String NO = "NO";
	public static final String TRUE = "true";
	
	public static final String P_FEEDBACK_ATTEMPT1="p_feedback_attempt1";
	public static final String P_FEEDBACK_ALLCORRECT="p_feedback_allcorrect";
	public static final String P_FEEDBACK_AFTER_ATTEMPT1="p_feedback_after_attempt1";
	public static final String P_ALLOWFEEDBACK_QUESTION="p_allowfeedback_question";
	public static final String P_PROCTORING_ENABLED="p_proctoring_enabled";
	public static final String P_GROUPASSIGNMENT_POLICY = "p_groupassignment";
	
	//CST-8583-EZT late submission
	public static final String P_LATESUBMISSIONDUE = "p_latesubmissiondue";
	public static final String P_DEDUCT_LATE_INCREMENT_ONCE = "once";
	public static final String P_DEDUCT_LATE_INCREMENT_DAY = "day";
	public static final String P_DEDUCT_LATE_INCREMENT_HOUR = "hour";
	public static final String LATE_SUBMIT_OPTION_ONCE_ONE = "1";
	public static final String LATE_SUBMIT_OPTION_INCREMENT_TWO = "2";
	public static final String LATE_SUBMIT_OPTION_NO_DEDUCT_THREE = "3";
	public static final String P_DEDUCT_LATE_ZERO = "0";
	public static final String IS_VALID_ORG_XID_FOR_EZT_LATE_SUBMISSION = "isValidOrgXidForEZTLateSubmission";
	public static final String LATE_SUBMISSION_DATE_FORMAT_IN_DB = "yyyy-MM-dd HH:mm:ss";
	public static final String LATE_SUBMISSION_DATE_FORMAT_FOR_DISPLAY = "MM/dd/yyyy hh:mm aaa z";
	
	//CST-10726-shift dates for late submission
	public static final String IS_SHIFT_DATES_LATE_SUBMISSION_FEATURE_ENABLED = "IS_SHIFT_DATES_LATE_SUBMISSION_FEATURE_ENABLED";
	public static final String SHIFT_DATES_LATE_SUBMISSION_ENABLED_ASSIGNMENT_TYPES = "SHIFT_DATES_LATE_SUBMISSION_ENABLED_ASSIGNMENT_TYPES";
	public static final String IS_VALID_ORG_XID_FOR_SHIFT_DATES_LATE_SUBMISSION = "isValidOrgXidForShiftDatesLateSubmission";
	
	public static final String EZT_LATE_SUBMISSION_DATE_VALIDATION_ERROR_KEY = "EZT_LATE_SUBMISSION_DATE_VALIDATION_ERROR_KEY";
	public static final String EZT_LATE_SUBMISSION_DATE_VALIDATION_MESSAGE = "Due date should be prior to Late submission date. Please correct the dates to save Assignment policy.";
	
	public static final String IS_MANAGE_DATES_LSD_VALIDATION_ON = "IS_MANAGE_DATES_LSD_VALIDATION_ON";
	
	private PoliciesServiceConstants() {
	}

}
