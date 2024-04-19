package io.mhe.assignmentcomponent.mheevent.exception;

import io.mhe.assignmentcomponent.mheevent.vo.MheEventData;
import io.mhe.assignmentcomponent.mheevent.vo.MheEventDataTO;
import io.mhe.assignmentcomponent.mheevent.vo.TrackbackUrlTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MheEventExceptionUtils {

	// Do not update these error messages as they are used to create dashboards.
	public static final String PREFIX_EXCEPTION_SQS = "Error in writing to Amazon SQS with sqsEnd :";
	private static final String PREFIX_EXCEPTION_MSG = " >>> MHE Event Error : ";
	private static Logger logger = LoggerFactory.getLogger(MheEventExceptionUtils.class);

	public static void logException(MheEventData eventData, Exception e) {
		StringBuilder returnStr = new StringBuilder(PREFIX_EXCEPTION_MSG);
		try {
			if (eventData != null) {
				returnStr.append("TrackbackURL :: " + eventData.getTrackbackUrl());
				returnStr.append("Event details :: " + eventData.toString());
			}
		} catch (Exception ex) {
			returnStr.append("Exception while logging " + ex);
		}
		logger.error(returnStr.toString(), e);
	}
	public static void logException(List<MheEventData> mheEventDataList, Exception e) {
		mheEventDataList.forEach(event -> logException(event,e));
	}

	public static void logException(MheEventDataTO eventDataTo, TrackbackUrlTO trackbackUrl, Exception e) {
		StringBuilder returnStr = new StringBuilder(PREFIX_EXCEPTION_MSG);
		try {
			returnStr.append("-> TrackbackURL :: " + trackbackUrl);
			if (eventDataTo != null) {
				returnStr.append(" -> MheEventDataTO :: " + eventDataTo);
			}
		} catch (Exception ex) {
			returnStr.append("Exception while logging " + ex);
		}
		logger.error(returnStr.toString(), e);
	}

	public static void logException(List<MheEventDataTO> mheEventDataList, TrackbackUrlTO trackbackUrl, Exception e) {
		StringBuilder returnStr = new StringBuilder(PREFIX_EXCEPTION_MSG);
		try {
			returnStr.append("-> TrackbackURL :: " + trackbackUrl);
			if (mheEventDataList != null && mheEventDataList.size() > 0) {
				mheEventDataList.forEach(returnStr::append);
			}
		} catch (Exception ex) {
			returnStr.append("Exception while logging " + ex);
		}
		logger.error(returnStr.toString(), e);
	}

	public static void logException(String msg, TrackbackUrlTO trackbackUrl, Exception e) {
		StringBuilder returnStr = new StringBuilder(PREFIX_EXCEPTION_MSG);
		try {
			returnStr.append("-> TrackbackURL :: " + trackbackUrl);
			if (msg != null) {
				returnStr.append(" -> Message :: " + msg);
			}
		} catch (Exception ex) {
			returnStr.append("Exception while logging " + ex);
		}
		logger.error(returnStr.toString(), e);
	}
}
