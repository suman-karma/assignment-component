package io.mhe.assignmentcomponent.mheevent.service.impl;

import io.mhe.assignmentcomponent.mheevent.exception.MheEventExceptionUtils;
import io.mhe.assignmentcomponent.mheevent.service.IMheEventBusinessService;
import io.mhe.assignmentcomponent.mheevent.service.IMheEventService;
import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants;
import io.mhe.assignmentcomponent.mheevent.util.Util;
import io.mhe.assignmentcomponent.mheevent.vo.AssignmentAttemptScoreMheEventData;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mheEventBusinessService")
public class MheEventBusinessService implements IMheEventBusinessService {

    private Logger logger = LoggerFactory.getLogger(MheEventBusinessService.class);

    @Autowired
    ApplicationContext context;

    @Override
    public void sendMheEventToSQS(MheEventData mheEventDataTO) {
        IMheEventService mheEventService = getMheEventService(mheEventDataTO);
        mheEventService.sendMheEventToSQS(mheEventDataTO);
    }

    @Override
    public void sendMheEventToSQS(List<MheEventData> mheEventDataTOList) {
        if (isConnectInstrumentationSwitchON()) {
            mheEventDataTOList.forEach(this::sendMheEventToSQS);
        }
    }

    private IMheEventService getMheEventService(MheEventData mheEventDataTO) {

        IMheEventService mheEventService = null;
        try {
            if (mheEventDataTO instanceof AssignmentAttemptScoreMheEventData) {
                mheEventService = context.getBean("assignmentAttemptScoreMheEventDataMapping", IMheEventService.class);

            } else {
                throw new Exception("error in creation of bean");
            }
        } catch (Exception e) {
            MheEventExceptionUtils.logException("While fetching the Service bean mheEventDataTO :: " + mheEventDataTO, mheEventDataTO.getTrackbackUrl(), e);
        }

        return mheEventService;
    }

    // To check edsSwitch
    public boolean isConnectInstrumentationSwitchON() {
        String edsSwitch = Util.getConfigValue(MheEventConstants.CONNECT_INSTRUMENTATION_SWITCH);
        if (logger.isDebugEnabled()) {
            logger.debug(">>> Connection with Connect Instrumentation switch :: " + edsSwitch);
        }
        return MheEventConstants.TRUE.equalsIgnoreCase(edsSwitch);
    }

}