package io.mhe.assignmentcomponent.service.generic.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Constants for Generic Assignment
 * @author Mayank
 * 
 */
public abstract class GenericAssignmentConstants {

	private final Logger logger = LoggerFactory.getLogger(GenericAssignmentConstants.class);
	public enum ASSIGNMENT_STATUS_TYPE {
		PUBLISH("publish"),
		HIDDEN("hidden");

		private String	value;

		private ASSIGNMENT_STATUS_TYPE(String value) {
			this.value = value;
		}

		public static ASSIGNMENT_STATUS_TYPE getAssignmentStatusType(String statusType) {
			for (ASSIGNMENT_STATUS_TYPE statusTypeConstant : ASSIGNMENT_STATUS_TYPE.values()) {
				if (statusTypeConstant.value.equalsIgnoreCase(statusType)) {
					return statusTypeConstant;
				}
			}
			return null;
		}

	}

	public static final String	MODULE_ID								= "moduleId";
	public static final String	PRODUCT_ID								= "productId";
	public static final String	PRODUCT_TYPE							= "productType";
	public static final String	SECTION_ID								= "sectionId";
	public static final String	CONTEXT_ID								= "contextId";
	public static final String	SESSION_COURSE_ID						= "courseid";
	public static final String	USER_ID									= "userid";
	public static final String	ISBN									= "isbn";
	public static final String	USER_LOCALE								= "userLocale";
	public static final String  INSTALLATIONID                			= "installationId";
	public static final String	ASSIGNMENT_ID							= "assignmentId";
	public static final String	ASSIGNMENT								= "assignment";
	public static final String	SESSION_SECTION_ID						= "sectionid";
	public static final String	PRODUCT									= "product";
	public static final String	MODE									= "mode";
	public static final String	EDIT									= "edit";
	public static final String	TAKE_ASSIGNMENT							= "take_assignment";
	public static final String	LAUNCH_ASSIGNMENT						= "launch_assignment";
	public static final String	VIEW_REPORT								= "view_report";
	public static final String	MANUAL_GRADABLE							= "manual_grade";
	public static final String	CREATE									= "create";
	public static final String	MUZZY_LANE_POLICY						= "muzzylanepolicy";
	public static final String	AVALON_RETURN_PAGE						= "return";
	public static final String	PREVIEW									= "preview";
	public static final String	SUCCESS									= "success";
	public static final String	FAILURE									= "failure";
	public static final String	DEFAULT_TITLE							= "unnamed title";
	public static final String	DEFAULT_ASSIGNMENT_NAME					= "unnamed assignment";
	public static final String	INSTRUCTOR_ROLE							= "I";
	public static final String	NO_URL									= "no_url";
	public static final String	COURSE_ID								= "courseId";
	public static final String	COURSE_TIMEZONE							= "courseTimeZone";
	public static final String	CANCEL_URL								= "cancelUrl";
	public static final String	SAVE_EXIT_URL							= "saveAndExitUrl";
	public static final String	POLICY_PAGE_URL							= "policyUrl";
	public static final String	REVIEW_ASSIGN_URL						= "reviewAndAssignUrl";
	public static final String	BASE_URL								= "baseUrl";
	public static final String	COURSE_TIME_ZONE						= "courseTimeZone";
	public static final String	COURSE_START_DATE						= "courseStartDate";
	public static final String	COURSE_END_DATE							= "courseEndDate";
	public static final String	COURSE									= "course";
	public static final String	SECTION									= "section";
	public static final String	COURSENAME								= "courseName";
	public static final String	COURSE_TIME_ZONE_DATE					= "courseTimeZoneDate";
	public static final String	SECTION_MODULES							= "sectionModules";
	public static final String	SECTION_NAME							= "sectionName";
	public static final String	LINKED									= "linked";
	public static final String	SESSION_INSTRUCTOR_ROLE					= "instructorRole";
	public static final String	MORE_SECTION							= "moreSections";
	public static final String	COURSE_INSTRUCTOR_INFO					= "CourseInstructorInfo";
	public static final String	INSTRUCTOR_POLICY						= "InstructorsPolicy";
	public static final String	TAB_ASSIGNMENTS							= "Assignments";
	public static final String	ASSIGNMENT_SECTION_LIST					= "AssignmentAssignedSectionsList";
	public static final String	IS_ACTIVE								= "isActive";
	public static final String	ASSIGNMENT_PRIMARY_INS					= "assignmentPrimaryIns";
	public static final String	ASSIGNMENT_PRIMARY_INS_NAME				= "assgnPrimaryInsName";
	public static final String	SESSION_USER_ID							= "userId";
	public static final String	REVIEW_AND_ASSIGN						= "reviewAndAssign";
	public static final String	SECTION_HOME							= "hmSectionHome";
	public static final String	HIDDEN									= "Hidden";
	public static final String	TEMPLATE_TYPE_DEFAULT					= "default";
	public static final String	MUZZY_GROUP_PARAMETER					= "group";
	public static final String	MUZZY_GROUP_PARAMETER_START_DATE		= "start_date";
	public static final String	MUZZY_GROUP_PARAMETER_END_DATE			= "end_date";
	public static final String	MUZZY_GROUP_PARAMETER_CAN_BE_ASSIGNED   = "assignable";
	public static final String	MODULE_TYPE								= "moduleType";
	public static final String	DEFAULT_MODULE_TYPE						= "default";
	public static final String 	ENDPOINT_COUNTRY_CODE					= "endpointCountryCode";
	public static final String	START_DATE_POLICY						= "p_startdate";
	public static final String	DUE_DATE_POLICY							= "p_duedate";
	public static final String	ATTEMPTS_POLICY							= "p_attempts";
	public static final String	ABA_GROUP_POLICY						= "p_groupassignment";
	public static final String	LATE_SUBMISSION_DUE			                = "p_latesubmissiondue";
	public static final String	ALLOW_LATE_SUBMISSION						= "p_allowlatesubmission";
	public static final String	TOOL_DRAFTS_POLICY			                = "p_tooldrafts";
	public static final String	POLICY_TOOL_MANAGED			                = "p_toolmanaged";
	public static final String  POLICY_CUSTOM_DATA 				            = "p_toolcustomdata";
	public static final String	GENERIC_LATE_SUBMIT_POLICY				= "generic_late_submission";
	public static final String	ASSIGNMENT_WEIGHT						= "assignment_weight";
	public static final String	DATE_FORMAT								= "yyyy-MM-dd HH:mm:ss";
	public static final String	EXT_DATE_FORMAT							= "E MMM dd HH:mm:ss z yyyy";
	public static final String	CATEGORY_HOMEWORK						= "Homework";
	public static final String	IS_TOOL_MANAGED                         ="isToolManaged";
	public static final String	ACTIVITY_PREFIX							= "connect_";
	public static final String	ACTIVITY_TYPE							= "connect_";
	public static final String	ACTIVITY_DEAFULT_TITLE					= "connect_";
	public static final String	ONE_SPACE								= " ";
	public static final String	DUMMY_URL								= "http://dummy.com";
	public static final String	TILES_DEF_BODY							= "body";
	public static final String	TRUE_STRING								= "true";

	public static final boolean	TRUE									= true;
	public static final boolean	FALSE									= false;

	public static final long	ZERO_LONG								= 0l;
	public static final int		ZERO_INT								= 0;
	public static final String	ATTEMPTS								= "attempts";
	public static final String	ATTEMPT									= "attempt";
	public static final String	ATTEMPT_NO								= "attemptNo";
	public static final String	ATTEMPTS_VAL							= "1,2,3,unlimited";
	public static final String	IS_PRIMARY								= "isPrimary";
	public static final String	PRIVILEGE_POLICY						= "privilegePolicy";

	public static final String	STARTDATE_ENDDATE_INVALIDITY			= "End date can't be before start date";
	public static final String	STARTDATE_CURRENTDATE_INVALIDITY		= "Start date can't be before current date";
	public static final String	ENDDATE_COURSEENDDATE_INVALIDITY		= "End date can't be after course end date";
	public static final String	STARTDATE_COURSESTARTDATE_VALIDITY		= "Start date can't be before course start date";
	public static final String	VALID_DATES								= "valid dates";

	// parameters to pass 3rd party for copy assignment apart from ISBN
	public static final String	SERVICE_CONSUMER_NAME					= "serviceConsumerName";
	public static final String	SERVICE_PRODUCER_NAME					= "serviceProducerName";
	public static final String	SERVICE_CONSUMER_TRANSACTION_ID			= "serviceConsumerTransactionId";
	public static final String	SERVICE_PRODUCER_TRANSACTION_ID			= "serviceProducerTransactionId";
	public static final String	SOURCE_SECTION_ID						= "sourceSectionId";
	public static final String	SOURCE_ASSIGNMENT_ID					= "sourceAssignmentId";
	public static final String	DESTINATION_SECTION_ID					= "destinationSectionId";
	public static final String	DESTINATION_ASSIGNMENT_ID				= "destinationAssignmentId";
	public static final String	INSTRUCTOR_ID							= "instructorId";
	public static final String	TOTAL_POINTS							= "totalPoints";
	public static final String	CONSUMER_CONNECT						= "Connect";
	public static final float	ZERO_FLOAT								= 0.0f;
	public static final float	HUNDRED_FLOAT							= 100.0f;
	public static final String	PRODUCER_SEQUENCE_NAME					= "seq_gerenic_producer_id";
	public static final String	CONSUMER_SEQUENCE_NAME					= "seq_gerenic_consumer_id";
	public static final String	WEB_SERIVCE_TRACNSACTION_SEQUENCE_NAME	= "seq_generic_ws_transaction_id";
	public static final String	GENERIC_CONSUMER_INSERT					= "INSERT INTO WS_GENERIC_CONSUMER_REQUEST   (   CONSUMER_TRANSACTION_ID , CONSUMER_NAME , PRODUCER_TRANSACTION_ID  ,  PRODUCER_NAME ,   STATUS_MESSAGE ,  WS_MODE ,  MESSAGE ,  WS_SERVICE_TRANSACTION_ID, TRANSACTION_TIMESTAMP ) VALUES ( ?,?,?,?,?,?,?,?, systimestamp )";
	public static final String	GENERIC_PRODUCER_INSERT					= "INSERT INTO WS_GENERIC_PRODUCER_REQUEST  (  CONSUMER_TRANSACTION_ID   ,   CONSUMER_NAME , PRODUCER_TRANSACTION_ID  , PRODUCER_NAME , STATUS_MESSAGE , WS_MODE , MESSAGE , WS_SERVICE_TRANSACTION_ID  ,  TRANSACTION_TIMESTAMP  ) VALUES ( ?,?,?,?,?,?,?,?, systimestamp )";
	public static final String	GENERIC_MESSAGE_INSERT					= "INSERT INTO ws_generic_message( WS_SERVICE_TRANSACTION_ID, MESSAGE_BODY, ASSIGNMENT_ID, TRANSACTION_TIMESTAMP, USER_ID) VALUES ( ?,?,?,systimestamp,? ) ";

	public static final String	WS_REQUEST_COPY_MODE					= "copy";
	public static final String	WS_REQUEST_SHARE_MODE					= "share";
	public static final String	WS_REQUEST_UNSHARE_MODE					= "unshare";
	public static final String	WS_REQUEST_DELETE_MODE					= "delete";

	// for preview flow
	public static final String	ASSIGNMENT_TYPE							= "assignmentType";
	public static final String	FALSE_STR								= "false";
	public static final String	YES_STR									= "yes";
	public static final String	ATTEMPTS_ALLOWED						= "attemptsAllowed";
	public static final String	POLICIES								= "policies";
	public static final String	ACTIVITY_ID								= "activityId";
	public static final String	PRIMARY_INSTRUCTOR_ID					= "primary_instructor_id";
	public static final String	ASSIGNMENT_DETAILS_HEADER				= "assignmentDetailsHeader";
	public static final String	ASSIGNMENT_STATUS						= "assignmentStatus";
	public static final String	ASSIGNMENT_TITLE						= "assignmentTitle";
	public static final String	ASSIGNMENT_ACTIVE						= "assignmentActive";
	public static final String	ACCESS_LEVEL							= "accessLevel";
	public static final String	SERVER_TIMEZONE_OFFSET					= "serverTimezoneOffset";
	public static final String	STUDENT_PREVIEW							= "studentPreview";
	public static final String	STUDENT_FOR_EXCEPTION					= "studentForException";
	public static final String	TARGET_GENERIC_PREVIEW					= "genericPreviewAssignment";

	// for student take assignment
	public static final String	USER_TYPE								= "userType";
	public static final String	EXCEPTION_DUEDATE						= "exceptionDuedate";
	public static final String	TARGET_GENERIC_STUDENT_TAKE				= "genericStudentTakeAssignment";
	public static final String	EBOOK_LINK_URL							= "ebookLinkUrl";
	public static final String	EXTERNAL_PRODUCT_ID						= "externalProductId";
	public static final String	EMAIL_STR								= "email";
	public static final String	ROLE_STR								= "role";
	public static final String	KEY_STR									= "key";
	public static final String	FIRST_NAME								= "firstName";
	public static final String	LAST_NAME								= "lastName";
	public static final String	IS_EBOOK_ACCESS							= "isEbookAccess";
	public static final String	SECTION_URL								= "sectionUrl";
	public static final String	LOCAL_DOMAIN							= "http://local.mhhe.com";
	public static final String	RELATIVE_SECTION_URL					= "/connect/hmStudentSectionHomePortal.do?sectionId=";
	public static final String	RELATIVE_INSTRUCTOR_SECTION_URL			= "/connect/hmInstructorSectionHomePortal.do?sectionId=";
	public static final String	COURSE_BOOK_INFO						= "courseBookInfo";
	public static final String	PROFILE_USER_NAME						= "profile.userName";
	public static final String	OPENAPI_BASE_URL						= "openapi_base_url";
	public static final String	OPENAPI_GATEWAY_URL						= "muzzy_api_gateway_url";

	// manual grading
	public static final String	TARGET_GENERIC_MANUAL_GRADE				= "genericManualGradingForward";
	public static final String	ASSIGNMENT_IDS							= "assignmentIds";

	public static final String	LTI_IS_ACTIVE							= "is_active";
	public static final String	LTI_IS_PRIMARY							= "is_primary";
	public static final String	LTI_IS_SHARED							= "is_shared";
	public static final String	LTI_PRIVILEGE_POLICY					= "privilege_policy";
	public static final String	PROPAGATE_ASSIGNMENT_DATES				= "PROPAGATE_ASSIGNMENT_DATES";
	public static final String	SHOW_TIME_AND_ATTEMPTS					= "SHOW_TIME_AND_ATTEMPTS_FOR_EXTENSION";
	public static final String	UPDATEASSIGNMENTDATES					= "updateAssignmentDates";
	public static final String	UPDATEEASSIGNMENTEXTENSIONDATES			= "updateStudentExtDates";
	public static final String	SAVE_ASSIGNMENT_EXTENSION				= "SAVE_ASSIGNMENT_EXTENSION";
	public static final String	LTI_FROM_PAGE							= "from_page";
	public static final String	LTI_TYPE_EDIT							= "type_edit";
	public static final String	SAVE_ATTEMPTS_FOR_EXTENSION				= "SAVE_ATTEMPTS_FOR_EXTENSION";
	public static final String	COPY_FEAILURE_MESSAGE_KEY				= "copy.assignment.assignment.failed";
	public static final String	MUZZY_LANE								= "MUZZY_LANE";
	public static final String  CUSTOM_INSTRUCTIONS                     = "instructions";
	public static final String  CURRENT_USER_ID                         = "currentUserId";
	
	public static final String SOURCE_COURSE_ID                         = "sourceCourseId";
	public static final String DESTINATION_COURSE_ID                    = "destinationCourseId";
	public static final String PRIMARY_INSTRUCTOR                       = "primaryInstructorId";
	public static final String SECONDARY_INSTRUCTOR_ID                  = "secondaryInstructorId";
	public static final String IS_SECONDARY_INSTRUCTOR                  = "isSecondaryInstructor";
	public static final String COURSE_TIME_ZONE_OFFSET                  = "courseTimeZoneOffset";
	public static final String ASSIGNMENT_START_DATE                    = "assignmentStartDate";
	public static final String ASSIGNMENT_DUE_DATE                      = "assignmentDueDate";
	public static final String MESSAGE                                  = "message";
	public static final String POLICY                                   = "policy";
	public static final String IS_ABA_GROUP                             = "isGroup";
	public static final String MUZZY_LTI_POST_URL                       = "MUZZY_LTI_POST_URL";
	public static final String MUZZY_CONSUMER_KEY                       = "MUZZY_CONSUMER_KEY";
	public static final String MUZZY_ACTIVITY                           = "activity";
	
	public static final String	GENERIClTI_POLICY						= "genericLtiPolicy";
	public static final String 	MM_DD_YYYY_H_MM 						= "MM/dd/yyyy H:mm";
	public static final String  TOOL_POLICY_MAP                         = "toolPolicyMap";
	public static final String  IS_ERROR                                = "isError";
	public static final String  ERROR_MSG                               = "errorMsg"; 
	public static final String  UTC_DATE_FORMAT                         = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String  DEFAULT_DATE_FORMAT                         = "yyyy-MM-dd HH:mm:ss";
	public static final String PROCTORING_TOOL_ID 						= "proctoringToolId";
	public static final String FULLY_GRADED 							= "FullyGraded";
	public static final String MANUAL_GRADED 							= "manual-graded";
	public static final String DUE 										= "due";
	public static final String TOGGLE_OFFER 							= "toggleOffer";
	
	public static final String RELEASE_PREVIEW 							= "releasePreview";

		
	// below part is for getting oAuth compliant signature



	public static String getAuthValue(String customerKey, String oAuthSignature, String nonce, String timeStamp, String oAuthSigMethod,
			String version) {
		StringBuilder buildString = new StringBuilder();
		buildString.append("OAuth");
		buildString.append(" ");
		buildString.append(OAUTH_CONSUMER_KEY);
		buildString.append("=\"");
		buildString.append(customerKey);
		buildString.append("\",");
		buildString.append(OAUTH_SIGNATURE);
		buildString.append("=\"");
		buildString.append(oAuthSignature);
		buildString.append("\",");
		buildString.append(OAUTH_NONCE);
		buildString.append("=\"");
		buildString.append(nonce);
		buildString.append("\",");
		buildString.append(OAUTH_TMESTAMP);
		buildString.append("=\"");
		buildString.append(timeStamp);
		buildString.append("\",");
		buildString.append(OAUTH_SIGNATURE_METHOD);
		buildString.append("=\"");
		buildString.append(oAuthSigMethod);
		buildString.append("\",");
		buildString.append("oauth_version");
		buildString.append("=\"");
		buildString.append(version);
		buildString.append("\"");
		return buildString.toString();
	}

	private static final String	OAUTH_VERSION			= "1.0";

	private static final String	OAUTH_CONSUMER_KEY		= "oauth_consumer_key";

	private static final String	OAUTH_SIGNATURE			= "oauth_signature";

	private static final String	OAUTH_SIGNATURE_METHOD	= "oauth_signature_method";

	private static final String	OAUTH_NONCE				= "oauth_nonce";

	private static final String	OAUTH_TMESTAMP			= "oauth_timestamp";
	
	private static final long THOUSAND =1000;

	public static final String	POP_OAUTH_KEY	= "power_of_process";
	

}
