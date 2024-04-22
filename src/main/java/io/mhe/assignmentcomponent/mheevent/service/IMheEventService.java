package io.mhe.assignmentcomponent.mheevent.service;

import io.mhe.assignmentcomponent.mheevent.vo.MheEventData;

public interface IMheEventService {

void sendMheEventToSQS(MheEventData data);
}
