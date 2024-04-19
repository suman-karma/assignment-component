package io.mhe.assignmentcomponent.mheevent.util;

public class MheEventConstants {
	
    public static final String CARDIO_SQS_REGION = "CARDIO_SQS_REGION";
    public static final String CARDIO_COMPRESS_ABOVE_LIMIT = "CARDIO_COMPRESS_ABOVE_LIMIT";
    public static final String CARDIO_SWITCH_ON = "CARDIO_SWITCH_ON";
    public static final String CARDIO_ACCESS_KEY = "aws_dynamo_db_access_key";
    public static final String CARDIO_SECRET_KEY = "aws_dynamo_db_secret_key";
    public static final String TRUE = "true";
    public static final String CARDIO_REQ_TIMEOUT = "CARDIO_REQ_TIMEOUT";
    public static final String CARDIO_READ_TIMEOUT = "CARDIO_RD_TIMEOUT";
    public static final String  STS_PUBLISH = "PUBLISH";
    public static final String CONNECT_INSTRUMENTATION_SWITCH = "connect_instrumentation_switch";
    public static final String  MODE_CREATE = "create";
    public static final String  MODE_EDIT = "edit";
    private static final String MHE_EVENT_SQS_KEY = "INSTRUMENTATION_OTHER_QUEUE";
    private static final String MHE_EVENT_GRADE_SCORE_SQS_KEY = "INSTRUMENTATION_GRADE_QUEUE";
    private static final String MHE_EVENT_ASGN_ATTEMPT_SQS_KEY = "INSTRUMENTATION_ATTEMPT_QUEUE";
    public static final String MHE_EVENT_MAX_POOL_SIZE = "MHE_EVENT_MAX_POOL_SIZE";
    public static final String PUBLISH_STATUS = "status";
    public static final String HIDDEN = "hidden";
    public static final String WAS_PUBLISHED = "was_published";
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final String IS_LIBRARY_ASSIGNMENT = "is_library_assignment";
    public static final String URL_BASED = "url_based";
    public static final String ASSIGNMENT_TYPE = "assignment_type";
    public static final Integer SUBLIST_SIZE = 75;
    public static final String GRADE_SQS_ENDPOINT = "GRADE_SQS_ENDPOINT";
    public static final String GRADE_SQS_ENDPOINT_DEFAULT = "--SOME---QUEUE---";	
	
	public enum MheEvent {
		//value is SQS Key and using this key SQS URL is retrieved using Configuration.java
		ASSIGNMENT(MHE_EVENT_SQS_KEY),
		SCORE(MHE_EVENT_GRADE_SCORE_SQS_KEY),
		GRADE(MHE_EVENT_GRADE_SCORE_SQS_KEY),
		ASSIGNMENT_ATTEMPT(MHE_EVENT_ASGN_ATTEMPT_SQS_KEY),
		LEARNER_ASSIGNMENT(MHE_EVENT_SQS_KEY),
		GRADEBOOK_CONFIGURATION(MHE_EVENT_GRADE_SCORE_SQS_KEY);

		private String value;
		
		private MheEvent(String value) {
			this.value = value;
		}
		
		public String toString() {
			return value;
		}

	}
    
	public enum MheEventAction {
		CREATED("created"), UPDATED("updated"), DELETED("deleted"), NO_ACTION("NoAction"), UPSERT("upsert"),
		RESTORED("restored");

		private String value;

		private MheEventAction(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
	}
	
}
