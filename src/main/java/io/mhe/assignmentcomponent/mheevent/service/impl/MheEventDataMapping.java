package io.mhe.assignmentcomponent.mheevent.service.impl;

import com.google.common.collect.Lists;
import io.mhe.assignmentcomponent.mheevent.exception.MheEventExceptionUtils;
import io.mhe.assignmentcomponent.mheevent.service.IAmazonSQSMheEventHelper;
import io.mhe.assignmentcomponent.mheevent.service.IMheEventService;
import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventData;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventDataTO;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MheEventDataMapping implements IMheEventService {

	Logger logger = LoggerFactory.getLogger(MheEventDataMapping.class);
	
	@Autowired
	private IAmazonSQSMheEventHelper amazonSQSMheEventHelper;

	abstract List<MheEventTO> mapData(MheEventData data);

	@Override
	//@Async("mheExecutor")
	public void sendMheEventToSQS(MheEventData data) {
		logger.debug("sendMheEventToSQS : {}",data);
		try {
			List<MheEventTO> mheEventTOList = mapData(data);
			// Add Partitioning logic
			this.prepareMheEventTOList(mheEventTOList);
			// send to SQS
			writeToSQS(mheEventTOList);
		} catch (Exception e) {
			MheEventExceptionUtils.logException(data, e);
		}
	}

	protected void writeToSQS(List<MheEventTO> mheEventTOList) {
		logger.debug("Inside writeToSQS {}",mheEventTOList);
		if (null != mheEventTOList && !mheEventTOList.isEmpty()) {
			mheEventTOList.stream()
					.filter(mheEventTO -> mheEventTO != null && mheEventTO.getDataList() != null && !mheEventTO.getDataList().isEmpty())
					.forEach(mheEventTO -> {
						amazonSQSMheEventHelper.writeToSQS(mheEventTO);
					});
		}
	}

	private void prepareMheEventTOList(List<MheEventTO> mheEventTOList) {
		logger.debug("Partitioning mheEventTOList {}",mheEventTOList);
		//Partition Logic 
		List<MheEventTO> mheEventTOs = new ArrayList<>();
		// Stream List To Reconstruct a New One With max data of element of size
		// --sublist_size
		mheEventTOList.stream().forEach(mheEventTO -> {
			// partition by sublist_size
			List<List<MheEventDataTO>> mheEventDataTOSubLists = Lists.partition(mheEventTO.getDataList(), MheEventConstants.SUBLIST_SIZE);
			mheEventTOs.addAll(mheEventDataTOSubLists.stream()
					.filter(l -> !l.isEmpty())
					.map(
						l ->  MheEventTO.builder()
						.dataList(l)
						.trackbackUrl(mheEventTO.getTrackbackUrl())
						.mheEvent(mheEventTO.getMheEvent())
						.mheEventAction(mheEventTO.getMheEventAction())
						.eventSensedDate(mheEventTO.getEventSensedDate())
						.build())
					.collect(Collectors.toList()));
		});

		mheEventTOList.clear();
		mheEventTOList.addAll(mheEventTOs);
		logger.debug("Partitioned mheEventToList to {} ",mheEventTOList.size());
	}
	
}
