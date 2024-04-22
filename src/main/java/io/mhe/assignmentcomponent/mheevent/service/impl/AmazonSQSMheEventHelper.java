package io.mhe.assignmentcomponent.mheevent.service.impl;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mhe.assignmentcomponent.mheevent.exception.MheEventExceptionUtils;
import io.mhe.assignmentcomponent.mheevent.service.IAmazonSQSMheEventHelper;
import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants;
import io.mhe.assignmentcomponent.mheevent.util.Util;
import io.mhe.assignmentcomponent.mheevent.vo.AmazonSQSMheEventTO;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventPayload;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AmazonSQSMheEventHelper implements IAmazonSQSMheEventHelper {

    private static Logger logger = LoggerFactory.getLogger(AmazonSQSMheEventHelper.class);

    private boolean initialized = false;
    //private int COMPRESSION_ABOVE = 0;
    private AmazonSQS sqs = null;
    private int delayInSeconds = 0;

    private static Environment environment;

    public static void setEnvironment(Environment environment) {
        AmazonSQSMheEventHelper.environment = environment;
    }

    public boolean writePayLoad(MheEventTO eventTO) {
        long startTime = System.currentTimeMillis();
        boolean success = false;
        try {
            logger.debug("[AmazonSQSMheEventHelper]  >>> Inside writePayLoad : {}", eventTO);
            AmazonSQSMheEventTO amazonSQSTO = getConsumerJson(eventTO);
            amazonSQSTO.setCompressAbove(Util.getConfigValueAsInteger(MheEventConstants.CARDIO_COMPRESS_ABOVE_LIMIT));
            amazonSQSTO.setSqsRegion(Util.getConfigValue(MheEventConstants.CARDIO_SQS_REGION));
            amazonSQSTO.setAccessKey(Util.getConfigValue(MheEventConstants.CARDIO_ACCESS_KEY));
            amazonSQSTO.setSecretKey(Util.getConfigValue(MheEventConstants.CARDIO_SECRET_KEY));
            amazonSQSTO.setConnectionReadTimeout(Util.getConfigValue(MheEventConstants.CARDIO_READ_TIMEOUT));
            amazonSQSTO.setRequestTimeout(Util.getConfigValue(MheEventConstants.CARDIO_REQ_TIMEOUT));
            logger.debug(" [AmazonSQSMheEventHelper] >>> Inside writePayLoad with amazonSQSTO : {} ", amazonSQSTO);
            if (null != amazonSQSTO && StringUtils.isNotBlank(amazonSQSTO.getSqsEndPoint())) {
                success = this.sendMessage(amazonSQSTO);
            }
        } catch (Exception e) {
            MheEventExceptionUtils.logException(MheEventExceptionUtils.PREFIX_EXCEPTION_SQS + eventTO, eventTO.getTrackbackUrl(), e);
        }
        logger.debug(" [AmazonSQSMheEventHelper] >>> Payload written to SQS success : {} Time Taken :  {} ms", success,
                (System.currentTimeMillis() - startTime));
        return success;
    }

    private AmazonSQSMheEventTO getConsumerJson(MheEventTO eventTO) {
        AmazonSQSMheEventTO amazonSQSTO = new AmazonSQSMheEventTO();
        try {
            String connectionReadTimeout = Util.getConfigValue(MheEventConstants.CARDIO_READ_TIMEOUT);
            String reqTimeout = Util.getConfigValue(MheEventConstants.CARDIO_REQ_TIMEOUT);

            String sqsEndPoint = Util.getConfigValue(MheEventConstants.GRADE_SQS_ENDPOINT, MheEventConstants.GRADE_SQS_ENDPOINT_DEFAULT);
            logger.debug("[AmazonSQSMheEventHelper] THe value of SQSEnpoint for MheEventType : {} is : {}", eventTO.getMheEvent(), sqsEndPoint);
            MheEventPayload mheEventPayload = new MheEventPayload();
            mheEventPayload.setEventTO(eventTO);

            amazonSQSTO.setMheEventPayload(mheEventPayload);
            amazonSQSTO.setSqsEndPoint(sqsEndPoint);
            amazonSQSTO.setConnectionReadTimeout(connectionReadTimeout);
            amazonSQSTO.setRequestTimeout(reqTimeout);
        } catch (Exception e) {
            throw e;
        }
        return amazonSQSTO;
    }

    @Override
    public boolean writeToSQS(MheEventTO eventTO) {
        logger.debug(" [AmazonSQSMheEventHelper] >>> Calling SQS for assignementId: {} with eventTO: {}", eventTO);
        return writePayLoad(eventTO);
    }

    @Override
    public boolean sendMessage(AmazonSQSMheEventTO amazonSqsMheEventTo) throws Exception {
        boolean success = false;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(amazonSqsMheEventTo.getMheEventPayload().getEventTO());
            logger.debug("[AmazonSQSMheEventHelper] The message to send to SQS is : {}", message);
            if (StringUtils.isNotBlank(message)) {
                AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                        .withRegion(amazonSqsMheEventTo.getSqsRegion())
                        .build();
                long startTime = System.currentTimeMillis();
                if (StringUtils.isNotBlank(amazonSqsMheEventTo.getSqsEndPoint())) {
                    logger.debug("[AmazonSQSMheEventHelper] Started writing into SQS");
                    SendMessageRequest send_msg_request = new SendMessageRequest()
                            .withQueueUrl(amazonSqsMheEventTo.getSqsEndPoint()).withMessageBody(message)
                            .withDelaySeconds(delayInSeconds);
                    SendMessageResult response = sqs.sendMessage(send_msg_request);
                    if (response != null && StringUtils.isNotBlank(response.getMessageId())) {
                        success = true;
                        logger.debug("[AmazonSQSMheEventHelper] Message has been successfully sent to SQS: Time taken {} Response {}", new Object[]{System.currentTimeMillis() - startTime, response});
                    }
                } else {
                    logger.error("[AmazonSQSMheEventHelper] Message has been not sent to SQS:SqsEndPoint not defined");
                    success = false;
                }
            }
        } catch (Exception e) {
            logger.error("Exception in send message {}", e);
            throw e;
        }
        return success;
    }

}