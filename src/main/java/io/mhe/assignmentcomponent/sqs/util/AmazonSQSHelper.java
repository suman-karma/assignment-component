package io.mhe.assignmentcomponent.sqs.util;

import com.mhe.sqs.AmazonSQSService;
import com.mhe.sqs.impl.AmazonSQSServiceImpl;
import com.mhe.sqs.utils.ObjectMapperUtility;
import io.mhe.assignmentcomponent.sqs.util.AmazonSQSConstants.TRANSACTION_TYPE;
import io.mhe.assignmentcomponent.sqs.vo.AmazonSQSTO;
import io.mhe.assignmentcomponent.sqs.vo.ContentTO;
import io.mhe.assignmentcomponent.sqs.vo.Payload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// environment.getProperty need all to set.

@Component
public class AmazonSQSHelper {

private final Logger logger = LoggerFactory.getLogger(AmazonSQSHelper.class);

@Autowired
Environment environment;

public boolean writePayLoad(ContentTO contentTO, String attemptNo, String transactionType, Date currentDate) {
		long startTime = System.currentTimeMillis();
		boolean success = false;
		String cardioOnOffSwitch = environment.getProperty(AmazonSQSConstants.CARDIO_SWITCH_ON);

		if(AmazonSQSConstants.TRUE.equalsIgnoreCase(cardioOnOffSwitch)) {
		    try {
		    if(contentTO != null) {
		    		contentTO.setModifiedDate(currentDate);
		    }
            logger.debug("Inside writePayLoad : {}", contentTO);
            String consumerJsonStr = getConsumerJson(contentTO, transactionType);
            logger.debug("Inside writePayLoad consumerJsonStr : {} ", consumerJsonStr);
            if(StringUtils.isNotBlank(consumerJsonStr)) {
                AmazonSQSService sqsClient = new AmazonSQSServiceImpl(); 
                logger.debug("Inside IF BLOCK sqsClient : {} ", sqsClient);
                success = sqsClient.sendMessageToQueue(consumerJsonStr, transactionType);
            }
            logger.debug("Inside writePayLoad success : {}", success);
        } catch (Exception e) {
            logger.error("Error in writing to Amazon SQS with contentTO {} : attemptNo : {} : transactionType : {}", new Object[]{contentTO, attemptNo, transactionType}, e);
        }
        logger.debug("Payload written to SQS success : {} Time Taken :  {} ms", success, (System.currentTimeMillis() - startTime));
		} else {
		    logger.info("Cardio is switched Off");
		}
		return success;
}

/**
 * 
 * Overloaded Method for LTIA
 */
public boolean writePayLoad(ContentTO contentTO, String transactionType, boolean isFifo) {
		long startTime = System.currentTimeMillis();
		boolean success = false;
		String intiaOnOffSwitch = environment.getProperty(AmazonSQSConstants.LTIA_SWITCH_ON);
		String ltiaGradeSyncBypassLambdaSwitch = environment.getProperty(AmazonSQSConstants.LTIA_GRADE_SYNC_BYPASS_LAMBDA);
		logger.debug("ltiaGradeSyncBypassLambdaSwitch is switched : {}", ltiaGradeSyncBypassLambdaSwitch);
		if(AmazonSQSConstants.TRUE.equalsIgnoreCase(intiaOnOffSwitch) &&
				AmazonSQSConstants.FALSE.equalsIgnoreCase(ltiaGradeSyncBypassLambdaSwitch)) {
		    try {
		    if(contentTO != null) {
		    		contentTO.setModifiedDate(new Date(System.currentTimeMillis()));
		    }
            logger.debug("Inside writePayLoad : {}", contentTO);
            String consumerJsonStr = getConsumerJson(contentTO, transactionType);
            logger.debug("Inside writePayLoad consumerJsonStr : {} ", consumerJsonStr);
            if(StringUtils.isNotBlank(consumerJsonStr)) {
                AmazonSQSService sqsClient = new AmazonSQSServiceImpl(); 
                logger.debug("Inside IF BLOCK sqsClient : {} ", sqsClient);
                success = sqsClient.sendMessageToQueue(consumerJsonStr, transactionType, isFifo);
            }
            logger.debug("Inside writePayLoad success : {}", success);
        } catch (Exception e) {
            logger.error("Error in writing to Amazon SQS with contentTO {} : attemptNo : {} : transactionType : {}", new Object[]{contentTO, contentTO.getAttemptNo(), transactionType}, e);
        }
        logger.debug("Payload written to SQS success : {} Time Taken :  {} ms", success, (System.currentTimeMillis() - startTime));
		} else {
		    logger.debug("LTIA is switched Off");
		}
		return success;
}


/**
 * 
 * Overloaded Method for LTIA
 */
public boolean writePayLoad(List<ContentTO> contentTOs, String transactionType, boolean isFifo) {
		long startTime = System.currentTimeMillis();
		boolean success = false;
		String intiaOnOffSwitch = environment.getProperty(AmazonSQSConstants.LTIA_SWITCH_ON);
		String ltiaGradeSyncBypassLambdaSwitch = environment.getProperty(AmazonSQSConstants.LTIA_GRADE_SYNC_BYPASS_LAMBDA);
		logger.debug("ltiaGradeSyncBypassLambdaSwitch is switched : {}", ltiaGradeSyncBypassLambdaSwitch);
		if(AmazonSQSConstants.TRUE.equalsIgnoreCase(intiaOnOffSwitch) &&
				AmazonSQSConstants.FALSE.equalsIgnoreCase(ltiaGradeSyncBypassLambdaSwitch)) {
			List<String> consumerJsons = new ArrayList<String>();
			for(ContentTO contentTO : contentTOs) {
					if(contentTO != null) {
						contentTO.setModifiedDate(new Date(System.currentTimeMillis()));
					}
					logger.debug("Inside writePayLoad : {}", contentTO);
					String consumerJsonStr = getConsumerJson(contentTO, transactionType);
					logger.debug("Inside writePayLoad consumerJsonStr : {} ", consumerJsonStr);
					if(StringUtils.isNotBlank(consumerJsonStr)) {
						consumerJsons.add(consumerJsonStr);
					}
			}
			if(logger.isDebugEnabled()) {
				logger.debug("The value of consumerJsons : {}", consumerJsons);
			}
			AmazonSQSService sqsClient = new AmazonSQSServiceImpl(); 
			logger.debug("Inside IF BLOCK sqsClient : {} ", sqsClient);
			try {
				success = sqsClient.sendMessageToQueueAsBatch(consumerJsons, transactionType, isFifo);
			} catch (Exception e) {
				logger.error("Error in writing to Amazon SQS with consumerJsons {} : isFifo : {} : transactionType : {}", new Object[]{consumerJsons, isFifo, transactionType}, e);
			}
			logger.debug("Payload written to SQS success : {} Time Taken :  {} ms", success, (System.currentTimeMillis() - startTime));
		} else {
		    logger.debug("LTIA is switched Off");
		}
		return success;
}


public String getConsumerJson(ContentTO contentTO, String transactionType) {
		String consumerJsonStr = "";
		TRANSACTION_TYPE trType = TRANSACTION_TYPE.valueOf(transactionType);
		String connectionReadTimeout = environment.getProperty(AmazonSQSConstants.CARDIO_READ_TIMEOUT);
		String reqTimeout = environment.getProperty(AmazonSQSConstants.CARDIO_REQ_TIMEOUT);
		
		String sqsEndPoint = environment.getProperty(trType.getTransactionType()); // TRANSACTION_TYPE
		String sqsRegion = environment.getProperty(AmazonSQSConstants.CARDIO_SQS_REGION);
		int compressAbove = Integer.parseInt(environment.getProperty(AmazonSQSConstants.CARDIO_COMPRESS_ABOVE_LIMIT));
		String accessKey = environment.getProperty(AmazonSQSConstants.CARDIO_ACCESS_KEY);
		String secretKey = environment.getProperty(AmazonSQSConstants.CARDIO_SECRET_KEY);
		logger.debug("SQSENDPOINT : {}  :: SQSREGION : {} :: COMPRESS Above : {}", new Object[]{sqsEndPoint, sqsRegion, compressAbove});
		if(StringUtils.isNotBlank(sqsEndPoint) && StringUtils.isNotBlank(sqsRegion) && compressAbove > 0) {
			Payload payload = new Payload();
			payload.setContentTO(contentTO);
			AmazonSQSTO amazonSQSTO = new AmazonSQSTO();
			amazonSQSTO.setPayload(payload);
			amazonSQSTO.setCompressAbove(compressAbove);
			amazonSQSTO.setSqsRegion(sqsRegion);
			amazonSQSTO.setSqsEndPoint(sqsEndPoint);
			amazonSQSTO.setAccessKey(accessKey);
			amazonSQSTO.setSecretKey(secretKey);
			amazonSQSTO.setConnectionReadTimeout(connectionReadTimeout);
			amazonSQSTO.setRequestTimeout(reqTimeout);
			
			consumerJsonStr = ObjectMapperUtility.getJson(amazonSQSTO);
		}
		
		return consumerJsonStr;
}
/**
 * 
 * @param activityId
 * @param sectionId
 * @param userId
 * @param attemptNo
 * @param transactionType
 */
public boolean writeToSQSQueue(long activityId, long sectionId, String userId, int attemptNo, String transactionType, String source, Date currentDate) {
    ContentTO contentTO = new ContentTO();
        contentTO.setSectionId(sectionId);
        contentTO.setActivityId(activityId);
        contentTO.setUserId(userId);
        logger.debug("Calling SQS from  {}   with contentTO:  {}  for transactionType: {}", new Object[]{source, contentTO, transactionType});
        return writePayLoad(contentTO, String.valueOf(attemptNo), transactionType, currentDate);
    }

/**
 * This method is called when assignmentId is present instead of activityId
 * @param assignmentId
 * @param sectionId
 * @param studentId
 * @param attemptNo
 * @param transactionType
 */
public boolean writeToSQSQueue(long assignmentId, long sectionId, long studentId, String transactionType, int attemptNo, String source, Date currentDate) {
        String attemptNoStr = attemptNo > 0 ? String.valueOf(attemptNo) : "1"; //Default value for attemptNo is 1
        ContentTO contentTO = new ContentTO();
        contentTO.setSectionId(sectionId);
        contentTO.setAssignmentId(assignmentId);
        contentTO.setUserId(String.valueOf(studentId));
        logger.debug("Calling SQS from {} with contentTO: {} for transactionType: {}", new Object[]{source, contentTO, transactionType});
        return writePayLoad(contentTO, attemptNoStr, transactionType, currentDate);
    }

/**
 * This method is called in absence of all other parameters, only secAssignLineItemActivityId is available
 * @param secAssignLineItemActivityId
 * @param attemptNo
 * @param transactionType
 */
public boolean writeToSQSQueue(long secAssignLineItemActivityId, int attemptNo, String transactionType, String source, Date currentDate) {
        String attemptNoStr = attemptNo > 0 ? String.valueOf(attemptNo) : "1"; //Default value for attemptNo is 1
        ContentTO contentTO = new ContentTO();
        contentTO.setSectionLineItemActivityId(secAssignLineItemActivityId);
        logger.debug("Calling SQS from  {}   with contentTO:  {}  for transactionType: {}", new Object[]{source, contentTO, transactionType});
        return writePayLoad(contentTO, attemptNoStr, transactionType, currentDate);
    }
}