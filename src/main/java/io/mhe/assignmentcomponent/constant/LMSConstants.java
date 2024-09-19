package io.mhe.assignmentcomponent.constant;

public interface LMSConstants {
	String	LMS_ERROR_CODE_MSG_001						= "Installation Id not found.";
	String	LMS_ERROR_CODE_MSG_002						= "External Course Id not found.";
	String	LMS_ERROR_CODE_MSG_003						= "External Role not found.";
	String	LMS_ERROR_CODE_MSG_004						= "Internal Role not found.";
	String	LMS_ERROR_CODE_MSG_005						= "External User Id not found.";
	String	LMS_ERROR_CODE_MSG_006						= "Internal User Id not found.";
	String	LMS_ERROR_CODE_MSG_007						= "Data not available for arguments.";

	/**
	 * LMS manual grade sync related constants
	 */
	String	LMS_GRADE_SYNC_RESULT						= "lmsGradeSyncResult";
	String	LMS_GRADE_SYNC_FAILED_SIUDENT_IDS_MESSAGE	= "lmsGradeSyncFailedStudentIdsMessage";

	String	LMS_ERROR_CODE_MSG_008						= "Invalid installation id {0}";
	String	LMS_ERROR_CODE_MSG_009						= "Invalid user email {0}";
	String	LMS_ERROR_CODE_MSG_010						= "Invalid user id {0}";
	String	LMS_ERROR_CODE_MSG_011						= "Invalid section id {0}";
	String	LMS_ERROR_CODE_MSG_012						= "Invalid assignment id {0}";
	String	LMS_ERROR_CODE_MSG_013						= "Error while checking for the existence of installation for id {0}";
	String	LMS_ERROR_CODE_MSG_014						= "Error while getting user details for email address {0}";
	String	LMS_ERROR_CODE_MSG_015						= "User not found with the email address {0}";
	String	LMS_ERROR_CODE_MSG_016						= "Error while checking for user pairing/mapping for user id {0}";
	String	LMS_ERROR_CODE_MSG_017						= "User pairing not found for user id {0}";
	String	LMS_ERROR_CODE_MSG_018						= "Error while fetching LMS courses with paired sections for installation id {0} and user id {1}";
	String	LMS_ERROR_CODE_MSG_019						= "Error while fetching LMS deployed assignments for section id {0}";
	String	LMS_ERROR_CODE_MSG_020						= "Error while fetching LMS information for section id {0}";
	String	LMS_ERROR_CODE_MSG_021						= "LMS system information not found for section id {0}";
	String	LMS_ERROR_CODE_MSG_022						= "Error while fetching registered students for section id {0}";
	String	LMS_ERROR_CODE_MSG_023						= "There are no students registered for section id {0}";
	String	LMS_ERROR_CODE_MSG_024						= "Error while checking for user pairing for the students registered with section";
	String	LMS_ERROR_CODE_MSG_025						= "User pairing not found for any of the students registered with section";
	String	LMS_ERROR_CODE_MSG_026						= "Error while fetching score details for section id {0}, assignment id {1} and for all paired student ids";
	String	LMS_ERROR_CODE_MSG_027						= "No score details found for section id {0}, assignment id {1} and for all paired student ids";
	String	LMS_ERROR_CODE_MSG_028						= "Grade posting to LMS through MHCampus failed for section id {0}, assignment id {1} and student id {2}";
	String	LMS_ERROR_CODE_MSG_029						= "LMS grades synchronization is successful for section id {0}, assignment id {1} and for all paired students";
	String	LMS_ERROR_CODE_MSG_030						= "LMS user pairing not found for student ids - {0}";
	String	LMS_ERROR_CODE_MSG_031						= "LMS grade synchronization failed for student ids - {0}";

	String	LMS_ERROR_CODE_001							= "OAPI_8001";
	String	LMS_ERROR_CODE_002							= "OAPI_8002";
	String	LMS_ERROR_CODE_003							= "OAPI_8003";
	String	LMS_ERROR_CODE_004							= "OAPI_8004";
	String	LMS_ERROR_CODE_005							= "OAPI_8005";
	String	LMS_ERROR_CODE_006							= "OAPI_8006";
	String	LMS_ERROR_CODE_007							= "OAPI_8007";

	String	KEY_CUSTOMER_ID								= "customerId";
	String	KEY_CONTEXT_ID								= "contextId";
	String	KEY_USER_ID									= "userId";
	String	KEY_ASSIGNMENT_ID							= "assignmentId";
	String	CONNECT_INSTRUCTOR_ROLE						= "INSTRUCTOR";
	String	CONNECT_STUDENT_ROLE						= "STUDENT";
	String	KEY_EXTERNAL_COURSE_ID						= "externalCourseId";

	String	MHC_D2L_BASE_URL							= "MHC_D2L_BASE_URL";

	String	KEY_LMS										= "LMS";

	/**
	 * This constant is used for retrieving sequence for audit logs.
	 */
	String	LMS_AUDIT_SEQUENCE							= "LMS_AUDIT_SEQUENCE";

	String	LMS_SNHU_AUDIT_SEQUENCE						= "LMS_SNHU_AUDIT_SEQUENCE";

	String	FAILURE										= "FAILURE";

	String	SUCCESS										= "SUCCESS";
	
	String	ENABLED										= "true";

	String	MHCAMPUS_D2L								= "MHCAMPUS_D2L";

	String	IS_LMS_LOG_ENABLED							= "IS_LMS_LOG_ENABLED";

	String	FETCH_MODULE								= "FETCH_MODULE";

	String	GRADE_PUSH									= "GRADE_PUSH";

	String	MHCAMPUS_REST_CALL_TIMEOUT					= "MHCAMPUS_REST_CALL_TIMEOUT";

	String	MHCAMPUS_REST_CALL_SSO_TIMEOUT				= "MHCAMPUS_REST_CALL_SSO_TIMEOUT";

	int		DEFAULT_MHCAMPUS_REST_CALL_TIMEOUT			= 10000;

	int		DEFAULT_MHCAMPUS_REST_CALL_SSO_TIMEOUT		= 60000;

	String	IS_CONVERT_TO_GENIUS_ENABLED				= "isConvertToGeniusEnabled";

	enum LMS_ASS_DEPLOYMENT_ERR_CODES {
		MH_CAMPUS_001("Rest exception occured"),
		MH_CAMPUS_002("Assignment deployment failed"),
		MH_CAMPUS_003("system error occured");

		private final String	description;

		LMS_ASS_DEPLOYMENT_ERR_CODES(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

    // LMS Constants and Constant values.
    String	LC_LMS_DISPLAY_NAME				= "LMS_DISPLAY_NAME";
	String	LC_LMS_CSS_CLASS				= "LMS_CSS_CLASS";
	String	LC_LMS_RETURN_TEXT				= "LMS_RETURN_TEXT";
	String	LC_LMS_RETURN_BEHAVIOUR			= "LMS_RETURN_BEHAVIOUR";
	String	LC_LMS_JS_INCLUDE_PATH			= "LMS_JS_INCLUDE_PATH";
	String	LC_LMS_CSS_INCLUDE_PATH			= "LMS_CSS_INCLUDE_PATH";
	String	LMS_DEFAULT_NAME				= "LMS";
	String	LMS_SCORE_PERCENTAGE			= "percentage";
	String	LMS_SCORE_POINTS				= "points";

	String			KEY_LMS_DISPLAY_NAME			= "lmsDisplayName";

	String			KEY_CONSTANT_LMS_FEATURE_VO		= "lmsFeatureVO";

	String	INSTRUCTOR_SECTION_HOME_PATH	= "/connect/hmInstructorSectionHomePortal.do";

	enum FEATURE_CONSTANTS {
		IS_LMS_MENU_MULTIPLE_OPTIONS("IS_LMS_MENU_MULTIPLE_OPTIONS", "IS_LMS_MENU_MULTIPLE_OPTIONS"),
		IS_CONFIRMATION_PAGE_ENABLED("IS_CONFIRMATION_PAGE_ENABLED", "Enable / Disable Course Confirmation Pairing page "),
		IS_RETURNED_TO("IS_RETURNED_TO", "This feature value is to show/hide return to LMS link"),
		IS_GO_TO("IS_GO_TO", "IS_GO_TO"),
		IS_MODULE_FETCH("IS_MODULE_FETCH", "Enable / Disable Module Fetch Functionality"),
		IS_DEPLOY_REVIEW_ASSIGN("IS_DEPLOY_REVIEW_ASSIGN", "Enable / Disable Deployment feature from Review and Assign Page"),
		IS_DEPLOY_SECTION_HOME("IS_DEPLOY_SECTION_HOME", "Enable / Disable Deployment of Assignment(s) from Section Home Page"),
		IS_GRADE_SYNC_ENABLED("IS_GRADE_SYNC_ENABLED", "Enable / Disable Grade Sync Feature"),
		IS_MANUAL_SYNC_ENABLED("IS_MANUAL_SYNC_ENABLED", "Enable / Disable Manual Sync Feature"),
		IS_USER_UNPAIRING("IS_USER_UNPAIRING", "This feature lets you enable / disable User Pairing Option on the Pairing Information Page"),
		IS_SCORE_TYPE_SELECTION("IS_SCORE_TYPE_SELECTION", "This Feature provides an ability to select the Attempt type at Section Level"),
		IS_AUTO_SHARING("IS_AUTO_SHARING", "This feature enables the Auto Sharing of Section , similar to SNHU"),
		IS_AUTO_COPY("IS_AUTO_COPY", "This feature enables the Auto Copy of Section"),
		IS_POLICY_SHARING_NO("IS_POLICY_SHARING_NO", "This Feature Enables the Policy Sharing feature while sharing section to None"),
		IS_POLICY_SHARING_ALL("IS_POLICY_SHARING_ALL", "This Feature Enables the Policy Sharing feature while sharing section to ALL"),
		IS_POLICY_SHARING_LIMITED("IS_POLICY_SHARING_LIMITED", "This Feature Enables the Policy Sharing feature while sharing section to Limited"),
		IS_AUTO_SHARING_SECTION("IS_AUTO_SHARING_SECTION", "This feature value is used to share a section automatically while creating an account"),
		IS_LMS_SIGN_ON_ENABLED("IS_LMS_SIGN_ON_ENABLED", "This Feature enables the Sign On page to login / register / forgot password"),
		IS_LMS_ACCOUNT_CREATION("IS_LMS_ACCOUNT_CREATION", "This Feature enables the Auto Creation of account based on Email address"),
		IS_COVER_SHEET_ENABLED("IS_COVER_SHEET_ENABLED", "This Feature enables the Coversheet for Students when assignment is accessed from LMS"),
		IS_AUTO_DEPLOY_ENABLED("IS_AUTO_DEPLOY_ENABLED", "This feature value is used to enable auto deploy"),
		DISPLAY_ASSIGNMENT_LIST_PAGE("DISPLAY_ASSIGNMENT_LIST_PAGE",
				"This Feature enables the Assignment List page similar to D2L when the User & Course is paired"),
		DISPLAY_COURSE_PAIRING_INTERMEDIATE_PAGE("DISPLAY_COURSE_PAIRING_INTERMEDIATE_PAGE",
				"This Feature enables the intermediate page to access Course Pairing link when User pairing is present."),
		IS_AUTO_COURSE_PAIR_ENABLED("IS_AUTO_COURSE_PAIR_ENABLED",
				"This Feature lets you do the Auto Creation of course with default bundle based on ISBN."),
		IS_RESOURCE_LINK_EXTERNAL_ID("IS_RESOURCE_LINK_EXTERNAL_ID",
				"This feature is to enable LMS resource id to internal resource/assignment id mapping"),
		IS_STUDENT_SHP_ENABLE("IS_STUDENT_SHP_ENABLE", "This Feature enables the Student Section Home for Students"),
		LMS_USER_UNPAIR_TEXT("LMS_USER_UNPAIR_TEXT", "This feature value contains the display text for reset single sign-on for section confirmation page"),
		LMS_SHP_DEPLOY_ICON_CLASS("LMS_SHP_DEPLOY_ICON_CLASS", "This feature value is used for CSS to show deploy icon for deployed assignments"),
		CSS_CLASS("CSS_CLASS", "CSS class for showing LMS Icon in the UI"), RETURN_TEXT("RETURN_TEXT", "This feature value contains the return text for section confirmation page"),
		RETURN_BEHAVIOUR("RETURN_BEHAVIOUR", "This feature value is used for CSS class to enabling/disabling closing of window"),
		LMS_DISPLAY_NAME("LMS_DISPLAY_NAME", "This is for the Display of LMS NAME like Canvas, BlackBoard"),
		LMS_DSA_LINK_TEXT("LMS_DSA_LINK_TEXT", "This feature value contains the display text for DSA link"),
		LMS_DSA_LINK("LMS_DSA_LINK", "This Feature contains the LMS DSA Link"),
		SWITCH_TO_LMS_TEXT("SWITCH_TO_LMS_TEXT", "This feature value contains the LMS name for section confirmation page"),
		BACK_TO_LMS_HOME_TEXT("BACK_TO_LMS_HOME_TEXT", "HTML Text to display the Return to LMS Link"),
		SCORE_TYPE_SETTINGS_TEXT("SCORE_TYPE_SETTINGS_TEXT", "This feature value contains the description for score type settings for section confirmation page"),
		LMS_PAIRING_HEADER_TEXT("LMS_PAIRING_HEADER_TEXT", "This feature value contains the display text for Header for section confirmation page"),
		ABOUT_LMS_PAIRING_INFO_ONE("ABOUT_LMS_PAIRING_INFO_ONE", "DSA Help Links for Section Confirmation Page"),
		ABOUT_LMS_PAIRING_INFO_TWO("ABOUT_LMS_PAIRING_INFO_TWO", "DSA Help Links for Section Confirmation Page"),
		ABOUT_LMS_PAIRING_INFO_THREE("ABOUT_LMS_PAIRING_INFO_THREE", "DSA Help Links for Section Confirmation Page"),
		LMS_DEPLOY_ICON_CLASS("LMS_DEPLOY_ICON_CLASS", "This feature value is used for CSS to show deploy icon"),
		IS_CREATE_ASSIGNMENT_OPENS_IN_NEW_WINDOW("IS_CREATE_ASSIGNMENT_OPENS_IN_NEW_WINDOW",
				"This flag is used for opening create assignment page in a new browser window."),
		IS_COURSE_PAIRING_OPENS_IN_NEW_WINDOW("IS_COURSE_PAIRING_OPENS_IN_NEW_WINDOW",
				"This flag is used for opening course pairing page in a new browser window."),
		IS_USER_PAIRING_OPENS_IN_NEW_WINDOW("IS_USER_PAIRING_OPENS_IN_NEW_WINDOW",
				"This flag is used for opening user pairing page in a new browser window."),
		IS_SECTION_HOME_PAGE_OPENS_IN_NEW_WINDOW("IS_SECTION_HOME_PAGE_OPENS_IN_NEW_WINDOW",
				"This flag is used for opening section home page in a new browser window."),
		LMS_JSP_PATH("LMS_JSP_PATH", "This feature value shows the JSP path for review and assign page"),
		LMS_DEPLOY_ICON_NAME("LMS_DEPLOY_ICON_NAME", "Name of the lms deploy icon from the images dir"),
		IS_SHP_OVERRIDE_ENABLED("IS_SHP_OVERRIDE_ENABLED", "This feature overrides every other feature and takes user to Section Home"),
		AUTO_POPULATE_COURSE_INFO_ENABLED("AUTO_POPULATE_COURSE_INFO_ENABLED", "This feature populates the course info sent as a lti request."),
		IS_LMS_INTERMEDIATE_PAGE_REQUIRED("IS_LMS_INTERMEDIATE_PAGE_REQUIRED", "Intermediate page to access Connect from an IFRAME"),

		IS_LMS_NOTIFY_ENABLED("IS_LMS_NOTIFY_ENABLED", "Send notification to lms on unpairing."),
		MYLINK_ENABLED("MYLINK_ENABLED", "Enable/Disable my links container in all pages"),
		MYLINK_TEXT("MYLINK_TEXT", "UI display text for my links container"),
		ACCOUNT_UNPAIR_TEXT("ACCOUNT_UNPAIR_TEXT", "HTML Text to display in the UI for Account Unpairing Link"),
		COURSE_UNPAIR_TEXT("COURSE_UNPAIR_TEXT", "HTML Text to display in the UI for Course Unpairing Link"),

		INSIGHT_LINK_ENABLED("INSIGHT_LINK_ENABLED", "Enabling/Disabling Insight link for my links container"),
		INSIGHT_LINK_TEXT("INSIGHT_LINK_TEXT", "Insight Link UI display text for my links container"),
		INSIGHT_LINK_URL("INSIGHT_LINK_URL", "Insight Link URL for my links container"),

		GRADE_BOOK_LINK_ENABLED("GRADE_BOOK_LINK_ENABLED", "Enabling/Disabling Gradebook link for my links container"),
		GRADE_BOOK_LINK_TEXT("GRADE_BOOK_LINK_TEXT", "Gradebook Link UI display text for my links container"),
		GRADE_BOOK_LINK_URL("GRADE_BOOK_LINK_URL", "Gradebook Link URL for my links container"),
		REPORT_LINK_ENABLED("REPORT_LINK_ENABLED", "Enabling/Disabling Report link for my links container"),
		REPORT_LINK_TEXT("REPORT_LINK_TEXT", "Report Link UI display text for my links container"),
		REPORT_LINK_URL("REPORT_LINK_URL", "Report Link URL for my links container"),
		MANUAL_SYNC_TEXT("MANUAL_SYNC_TEXT", "Manual Sync Link UI display text for my links container"),
		MANUAL_SYNC_URL("MANUAL_SYNC_URL", "Manual Sync Link URL for my links container"),
		GRADE_BOOK_LINK_OPEN_NEW_WINDOW("GRADE_BOOK_LINK_OPEN_NEW_WINDOW", "Whether to open gradebook link in new window or same frame"),
		REPORT_LINK_OPEN_NEW_WINDOW("REPORT_LINK_OPEN_NEW_WINDOW", "Whether to open Report link in new window or same frame"),
		MANUAL_SYNC_LINK_OPEN_NEW_WINDOW("MANUAL_SYNC_LINK_OPEN_NEW_WINDOW", "Whether to open Manual Sync link in new window or same frame"),
		SECTION_SETTING_LINK_OPEN_NEW_WINDOW("SECTION_SETTING_LINK_OPEN_NEW_WINDOW",
				"Whether to open Section Setting link in new window or same frame"),
		INSIGHT_LINK_OPEN_NEW_WINDOW("INSIGHT_LINK_OPEN_NEW_WINDOW", "Whether to open Insight link in new window or same frame"),
		DUMMY_A_LINK_ENABLED("DUMMY_A_LINK_ENABLED", "Enabling/Disabling first dummy link for my links container"),
		DUMMY_A_LINK_TEXT("DUMMY_A_LINK_TEXT", "First Dummy Link UI display text for my links container"),
		DUMMY_A_LINK_URL("DUMMY_A_LINK_URL", "First Dummy Link URL for my links container"),
		DUMMY_A_LINK_OPEN_NEW_WINDOW("DUMMY_A_LINK_OPEN_NEW_WINDOW", "Whether to open first dummy link in new window or same frame"),
		DUMMY_B_LINK_ENABLED("DUMMY_B_LINK_ENABLED", "Enabling/Disabling second dummy link for my links container"),
		DUMMY_B_LINK_TEXT("DUMMY_B_LINK_TEXT", "Second Dummy Link UI display text for my links container"),
		DUMMY_B_LINK_URL("DUMMY_B_LINK_URL", "Second Dummy Link URL for my links container"),
		DUMMY_B_LINK_OPEN_NEW_WINDOW("DUMMY_B_LINK_OPEN_NEW_WINDOW", "Whether to open second dummy link in new window or same frame"),
		DUMMY_C_LINK_ENABLED("DUMMY_C_LINK_ENABLED", "Enabling/Disabling third dummy link for my links container"),
		DUMMY_C_LINK_TEXT("DUMMY_C_LINK_TEXT", "Third Dummy Link UI display text for my links container"),
		DUMMY_C_LINK_URL("DUMMY_C_LINK_URL", "Third Dummy Link URL for my links container"),
		DUMMY_C_LINK_OPEN_NEW_WINDOW("DUMMY_C_LINK_OPEN_NEW_WINDOW", "Whether to open third dummy link in new window or same frame"),
		LMS_GRADE_SYNC_ENABLED("LMS_GRADE_SYNC_ENABLED", "Enabling/Disabling LMS Grade Sync link for my links container"),
		LMS_GRADE_SYNC_TEXT("LMS_GRADE_SYNC_TEXT", "LMS Grade Sync Link text for my links container"),
		LMS_GRADE_SYNC_URL("LMS_GRADE_SYNC_URL", "LMS Grade Sync Link URL for my links container"),
		LMS_GRADE_SYNC_OPEN_NEW_WINDOW("LMS_GRADE_SYNC_OPEN_NEW_WINDOW", "Whether to open grade sync page in a new window or same frame"),
		GRADE_SYNC_ASSIGNMENT_LIST_CONTENT("GRADE_SYNC_ASSIGNMENT_LIST_CONTENT",
				"This will contain the dynamic text which we need to display in Grade synchronization page for assignment list"),
		GRADE_SYNC_STUDENT_LIST_CONTENT("GRADE_SYNC_STUDENT_LIST_CONTENT",
				"This will contain the dynamic text which we need to display in Grade synchronization page for student list"),
		SCORE_TYPE("SCORE_TYPE", "This feature values shows score type either points or percentage"),
		LMS_FOOTER_TEXT("LMS_FOOTER_TEXT", "Text to display in the footer for LMS pages"),
		SECTION_SETTING_ENABLED("SECTION_SETTING_ENABLED", "Enabling/Disabling Section Setting link for my links container"),
		SECTION_SETTING_TEXT("SECTION_SETTING_TEXT", "Section Setting Link UI display text for my links container"),
		SECTION_SETTING_URL("SECTION_SETTING_URL", "Section Setting Link URL for my links container"),
		IS_INSTRUCTOR_COVERSHEET_ENABLED("IS_INSTRUCTOR_COVERSHEET_ENABLED",
				"This Feature enables the Coversheet for instructor when assignment is accessed from LMS"),
		LMS_ROSTERS_LIST_ICON("LMS_ROSTERS_LIST_ICON", "This icon is used on rosters list page"),
		LMS_ROSTER_ICON("LMS_ROSTER_ICON", "This icon is used on eidt roster info page"),
		IS_ROSTERS_PAGE_SUPPORTED("IS_ROSTERS_PAGE_SUPPORTED", "This flag is used for enabling/disabling rosters feature to any LMS"),
		IS_INSTRUCTOR_LMS_INTERMEDIATE_PAGE_REQUIRED("IS_INSTRUCTOR_LMS_INTERMEDIATE_PAGE_REQUIRED",
				"Intermediate page to access Connect from an IFRAME for instructor"),
		DO_NOT_DEPLOY_ASSIGNMENTS_TO_LMS("DO_NOT_DEPLOY_ASSIGNMENTS_TO_LMS", "Avoid deployment of assignments for any LMS"),

		CANVAS_FORCE_PUBLISH("CANVAS_FORCE_PUBLISH", "true/false. If true, deployed assignment in Canvas is forcefully published, ignoring account setting");

		FEATURE_CONSTANTS(String value, String description) {
			this.value = value;
			this.description = description;
		}

		private String	value;
		private String	description;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}

	/**
	 * Enum which holds the MHCAMPUS_D2L related urls
	 * 
	 * 
	 */
    enum MHCAMPUS_D2L_URL {
		GRADE_PUSH_URL("/v1/LMS/customer/{customerId}/course/{contextId}/user/{userId}/assignment/{assignmentId}/DeployScoreForConnect"),
		DEPLOY_ASSIGNMENT_URL("/v1/LMS/customer/{customerId}/course/{contextId}/DeployAssignmentLinkForConnect"),
		UNDEPLOY_ASSIGNMENT_URL("/v1/LMS/customer/{customerId}/course/{contextId}/assignment/{assignmentId}/DeleteAssignmentLinkForConnect"),
		MODULE_URL("/v1/LMS/customer/{customerId}/course/{contextId}/GetLinkDestinationsForConnect");

		private final String	url;

		MHCAMPUS_D2L_URL(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
	}

	enum LMS_TYPE_LOOK_UP {
		LMS("MHCAMPUS"),
		D2L("D2L"),
		CANVAS("CANVAS");

		private final String	type;

		LMS_TYPE_LOOK_UP(String type) {
			this.type = type;
		}
		public String getType() {
			return type;
		}
	}
	
	String	LTI_LINK_ID					= "LtiLinkId";

	String	ORG_UNIT_ID					= "OrgUnitId";

	String	ID							= "Id";

	String	TOKEN_KEY					= "tokenKey";

	String	X_B							= "x_b";

	String	D2L_VERSION					= "1.3";

	String	LMS_QUICKLAUNCH_PLUGIN_URL	= "/lmslaunch/login.htm";

	String	ASSIGNMENT_TOPIC_TYPE		= "3";

	String	ASSIGNMENT_TYPE				= "1";

	String	ASSIGNMENT_ID				= "assignment_id";

	String	HTML_DESCRIPTION_TYPE		= "Html";

	String	NUMERIC_GRADE_TYPE			= "Numeric";
	
	String LMS_ASSIGNMENT_LAUNCH_SUMMARY= "LMS_ASSIGNMENT_LAUNCH_SUMMARY";

    String BYPASS_OLD_KAFKA = "bypass.old.kafka";
    
    String FALSE = "false";
    
    String SWITCH_MSK_NOTIFY = "switch.msk.notify";

	String NOTIFY_TOPIC = "notify.rooms.all.incoming";
	
	String REPLICATE_ASSIGNMENT_DATES = "replicate_assignment_dates";

}
