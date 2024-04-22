package io.mhe.assignmentcomponent.mheevent.vo;

import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants.MheEventAction;

public abstract class MheEventData {

protected TrackbackUrlTO trackbackUrl;
protected MheEventAction mheEventAction;

public enum MheEventOperation {
		CREATE_MODIFY_DELETE, SHARE_ASSINGMENTS_TO_SECTIONS, RESTORE_ASSIGNMENT_EVENT, COPY_DUPLICATE,
		DELETE_SECTION_COURSE, SHARE_SECTION, SCORE_ONLY, ATTEMPT_ONLY, BOTH_SCORE_ATTEMPT, WITHOUT_STUDENTID,
		DELETE_ASSIGNMENTS, SHOW_HIDE, WITH_SECTION_STUDENT, WITH_ALL_PARAM, WITH_STUDENT_LIST,
		COPY_ASSIGNMENTS_TO_SECTIONS, UPDATE_DATES, EXTENSION_FOR_BULK, DELETE_EXTENSION, FLAG_MANUAL_GRADE,
		ROSTER_UPDATE, DELETE_STUDENT,GRADE
}

public TrackbackUrlTO getTrackbackUrl() {
		return trackbackUrl;
}

public void setTrackbackUrl(TrackbackUrlTO trackbackUrl) {
		this.trackbackUrl = trackbackUrl;
}

public MheEventAction getMheEventAction() {
		return mheEventAction;
}

public void setMheEventAction(MheEventAction mheEventAction) {
		this.mheEventAction = mheEventAction;
}

}
