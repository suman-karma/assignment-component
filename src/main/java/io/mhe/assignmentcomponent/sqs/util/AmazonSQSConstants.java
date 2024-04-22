package io.mhe.assignmentcomponent.sqs.util;

public class AmazonSQSConstants {

    public static final String CARDIO_SQS_REGION = "CARDIO_SQS_REGION";
    public static final String CARDIO_COMPRESS_ABOVE_LIMIT = "CARDIO_COMPRESS_ABOVE_LIMIT";
    public static final String CARDIO_SQS_SUBMISSION_QUEUE_URL = "CARDIO_SQS_SUBMISSION_QUEUE_URL";
    public static final String CARDIO_SQS_MANUAL_GRADING_QUEUE_URL = "CARDIO_SQS_MANUAL_GRADING_QUEUE_URL";
    public static final String CARDIO_SQS_ADJUST_CREDIT_QUEUE_URL = "CARDIO_SQS_ADJUST_CREDIT_QUEUE_URL";
    public static final String CARDIO_SQS_SKILL_CATEGORY_QUEUE_URL = "CARDIO_SQS_SKILL_CATEGORY_QUEUE_URL";
    public static final String CONNECT_SQS_LTIA_EVENTS_QUEUE_URL = "CONNECT_SQS_LTIA_EVENTS_QUEUE_URL";
    public static final String ACTIVITY_TYPE_SUBMISSION = "submission";
    public static final String ACTIVITY_TYPE_MANUAL_GRADING = "manualGrading";
    public static final String ACTIVITY_TYPE_ADJUST_CREDIT = "adjustCredit";
    public static final String ACTIVITY_TYPE_SKILL_CATEGORY = "skillCategory";
    public static final String ACTIVITY_TYPE_LTIA_EVENTS = "ltiaevents";
    public static final String CARDIO_SWITCH_ON = "CARDIO_SWITCH_ON";
    public static final String LTIA_SWITCH_ON = "LTIA_SWITCH_ON";
    public static final String LTIA_GRADE_SYNC_BYPASS_LAMBDA = "LTIA_GRADE_SYNC_BYPASS_LAMBDA";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String CARDIO_ACCESS_KEY = "aws_dynamo_db_access_key";
    public static final String CARDIO_SECRET_KEY = "aws_dynamo_db_secret_key";
    
    public static final String CARDIO_REQ_TIMEOUT = "CARDIO_REQ_TIMEOUT";
    public static final String CARDIO_READ_TIMEOUT = "CARDIO_RD_TIMEOUT";
    public static final String LTIA_ACP_ON = "LTIA_ACP_ON";
    public static final String LTIA_GRADE_RESYNC_ON = "LTIA_GRADE_RESYNC_ON";
    public static final String FEATURE_LTIA_ACP_COPY = "FEATURE_LTIA_ACP_COPY";

    public static final String DIRECTORY_DELIMITER = "/";
    
    

enum TRANSACTION_TYPE {
		submission(CARDIO_SQS_SUBMISSION_QUEUE_URL), manualGrading(CARDIO_SQS_MANUAL_GRADING_QUEUE_URL), 
		adjustCredit(CARDIO_SQS_ADJUST_CREDIT_QUEUE_URL), skillCategory(CARDIO_SQS_SKILL_CATEGORY_QUEUE_URL),
		ltiaevents(CONNECT_SQS_LTIA_EVENTS_QUEUE_URL);

		private String transactionType;

		private TRANSACTION_TYPE(String arg1) {
			transactionType = arg1;
		}

		public String getTransactionType() {
			return transactionType;
		}
}

    // Added for private bucket and cloudfront changes
    public static final String AMAZON_PRIVATE_BUCKET_NAME = "amazonPrivateBucketName";
    public static final String AMAZON_CLOUD_FRONT_BASE_URL = "amazonCloudFrontBaseURL";
    public static final String AMAZON_CLOUD_FRONT_KEY_PAIR_ID = "amazonCloudFrontId";
    public static final String AMAZON_CLOUD_FRONT_PRIVATE_KEY_PATH = "amazonCloudFrontPrivateKey";
    public static final String CONNECT_SUITE_AWS_TOKEN_EXPIRATION_TIME = "CONNECT_SUITE_AWS_TOKEN_EXPIRATION_TIME";
    public static final String CLOUD_FRONT_SSM_KEY_REGION = "cloudfrontSSMKeyRegion";
}
