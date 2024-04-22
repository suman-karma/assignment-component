package io.mhe.assignmentcomponent.mheevent.service;

import io.mhe.assignmentcomponent.mheevent.vo.MheEventData;

import java.util.List;

public interface IMheEventBusinessService {
/**
 * To perform Trigger of event
 * @param mheEventDataTO
 */
public void sendMheEventToSQS(MheEventData mheEventDataTO);

/**
 * To perform Trigger of event
 * @param mheEventDataList
 */
public void sendMheEventToSQS(List<MheEventData> mheEventDataList);

}
