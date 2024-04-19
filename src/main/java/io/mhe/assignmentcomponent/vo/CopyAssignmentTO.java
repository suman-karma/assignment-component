package io.mhe.assignmentcomponent.vo;

import java.util.List;

public record CopyAssignmentTO(long assignmentId, long newAssignmentId, String newPrimaryInstructorId,
                               String nativeAlaId, String newNativeAlaId, String title,
                               String newTitle, long courseId, long newCourseId, long sectionId, long newSectionId,
                               String provider, String type, String copyEZTStatus,
                               String error, float weight, List<Long> assignmetnLineItemIds,
                               String parentAssignmentStatus) implements Model {

// weight = 0.0f;  assignmetnLineItemIds = new ArrayList<Long>();
	
}
