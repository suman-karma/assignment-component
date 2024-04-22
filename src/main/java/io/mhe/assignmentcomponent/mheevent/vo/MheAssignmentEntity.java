package io.mhe.assignmentcomponent.mheevent.vo;

public class MheAssignmentEntity {

private String status;
private String wasPublished;
private String assignmentType;

public String getStatus() {
		return status;
}

public void setStatus(String status) {
		this.status = status;
}

public String getWasPublished() {
		return wasPublished;
}

public void setWasPublished(String wasPublished) {
		this.wasPublished = wasPublished;
}

public String getAssignmentType() {
		return assignmentType;
}

public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
}

private String isLibraryAssignment;

public String getIsLibraryAssignment() {
		return isLibraryAssignment;
}

public void setIsLibraryAssignment(String isLibraryAssignment) {
		this.isLibraryAssignment = isLibraryAssignment;
}
}
