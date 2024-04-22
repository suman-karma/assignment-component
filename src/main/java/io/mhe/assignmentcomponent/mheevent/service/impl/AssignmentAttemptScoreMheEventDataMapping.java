package io.mhe.assignmentcomponent.mheevent.service.impl;

import io.mhe.assignmentcomponent.mheevent.exception.MheEventExceptionUtils;
import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants.MheEvent;
import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants.MheEventAction;
import io.mhe.assignmentcomponent.mheevent.vo.AssignmentAttemptScoreMheEventData;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventData;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("assignmentAttemptScoreMheEventDataMapping")
public class AssignmentAttemptScoreMheEventDataMapping extends MheEventDataMapping {
@Override
public List<MheEventTO> mapData(MheEventData mheEventData) {
		List<MheEventTO> mheEventTOList = new ArrayList<MheEventTO>();
		try {
			if (mheEventData instanceof AssignmentAttemptScoreMheEventData) {
				AssignmentAttemptScoreMheEventData attemptEventData = (AssignmentAttemptScoreMheEventData) mheEventData;
				if (logger.isDebugEnabled()) {
					logger.debug(
							">>>> mheEventDataTO for AssignmentAttemptScoreMheEventData : " + attemptEventData);
				}
				if (validate(attemptEventData)) {
					switch (attemptEventData.getOperation()) {
					case ATTEMPT_ONLY:
						mheEventTOList.add(MheEventTO.builder().dataList(attemptEventData.getMheEventDataTOList())
								.mheEvent(MheEvent.ASSIGNMENT_ATTEMPT)
								.mheEventAction(attemptEventData.getMheEventAction()).eventSensedDate(new Date())
								.trackbackUrl(attemptEventData.getTrackbackUrl()).build());
						break;
					case SCORE_ONLY:
						mheEventTOList.add(MheEventTO.builder().dataList(attemptEventData.getMheEventDataTOList())
								.mheEvent(MheEvent.SCORE).mheEventAction(MheEventAction.UPSERT)
								.eventSensedDate(new Date()).trackbackUrl(attemptEventData.getTrackbackUrl()).build());
						break;
					case GRADE:
							mheEventTOList.add(MheEventTO.builder().dataList(attemptEventData.getMheEventDataTOList())
									.mheEvent(MheEvent.GRADE).mheEventAction(MheEventAction.UPSERT)
									.eventSensedDate(new Date()).trackbackUrl(attemptEventData.getTrackbackUrl()).build());
						break;
					case BOTH_SCORE_ATTEMPT:
						Date eventSensedDate = new Date();

						mheEventTOList.add(MheEventTO.builder().dataList(attemptEventData.getMheEventDataTOList())
								.mheEvent(MheEvent.ASSIGNMENT_ATTEMPT)
								.mheEventAction(attemptEventData.getMheEventAction()).eventSensedDate(eventSensedDate)
								.trackbackUrl(attemptEventData.getTrackbackUrl()).build());

						mheEventTOList.add(MheEventTO.builder().dataList(attemptEventData.getMheEventDataTOList())
								.mheEvent(MheEvent.SCORE).mheEventAction(MheEventAction.UPSERT)
								.eventSensedDate(eventSensedDate).trackbackUrl(attemptEventData.getTrackbackUrl())
								.build());
						break;
					default:
						MheEventExceptionUtils.logException("Failed with Operation case " + attemptEventData , attemptEventData.getTrackbackUrl(), new Exception());
						break;
					}
				} else {
					MheEventExceptionUtils.logException("Failed with mandatory fields " + attemptEventData , attemptEventData.getTrackbackUrl(), new Exception());
				}
			}
		} catch (Exception e) {
			MheEventExceptionUtils.logException(mheEventData, e);
		}

		return mheEventTOList;

}

private boolean validate(AssignmentAttemptScoreMheEventData attemptEventData) {
		if (attemptEventData == null || attemptEventData.getMheEventDataTOList() == null || attemptEventData.getMheEventDataTOList().isEmpty()
				|| attemptEventData.getOperation() == null || attemptEventData.getMheEventAction() == null
				|| attemptEventData.getTrackbackUrl() == null) {
			return false;
		} 
		attemptEventData.setMheEventDataTOList(
				attemptEventData.getMheEventDataTOList()
				.stream()
				.filter(e -> e!=null)
				.collect(Collectors.toList()));
		return true;
}
}
