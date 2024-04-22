package io.mhe.assignmentcomponent.mheevent.service;

import io.mhe.assignmentcomponent.mheevent.vo.AmazonSQSMheEventTO;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventTO;

public interface IAmazonSQSMheEventHelper {

/**
 * This method is called when assignmentId is present instead of activityId
 * along with event type and action (EDS)
 * 
 * @param assignmentId
 * @param sectionId
 * @param studentId
 * @param attemptNo
 * @param transactionType
 * @param event
 * @param action
 * @throws Exception 
 */
public boolean writeToSQS(MheEventTO eventTO);

/**
 * This method is called to send  MHE Event message to SQS
 * 
 * @param amazonSqsMheEventTo
 * @return success or failure
 * @throws Exception 
 */
public boolean sendMessage(AmazonSQSMheEventTO amazonSqsMheEventTo) throws Exception;
}
