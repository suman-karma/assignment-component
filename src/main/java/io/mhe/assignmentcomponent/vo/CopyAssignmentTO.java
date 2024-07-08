package io.mhe.assignmentcomponent.vo;

import java.util.ArrayList;
import java.util.List;

public class CopyAssignmentTO implements Model {

    public CopyAssignmentTO(){}
    public CopyAssignmentTO(long assignmentId, long newAssignmentId, String newPrimaryInstructorId,
                     String nativeAlaId, String newNativeAlaId, String title,
                     String newTitle, long courseId, long newCourseId, long sectionId, long newSectionId,
                     String provider, String type, String copyEZTStatus,
                     String error, float weight, List<Long> assignmetnLineItemIds,
                     String parentAssignmentStatus) {
        this.assignmentId = assignmentId;
        this.newAssignmentId = newAssignmentId;
        this.newPrimaryInstructorId = newPrimaryInstructorId;
        this.nativeAlaId = nativeAlaId;
        this.newNativeAlaId = newNativeAlaId;
        this.title = title;
        this.newTitle = newTitle;
        this.courseId = courseId;
        this.newCourseId = newCourseId;
        this.sectionId = sectionId;
        this.newSectionId = newSectionId;
        this.provider = provider;
        this.type = type;
        this.copyEZTStatus = copyEZTStatus;
        this.error = error;
        this.weight = weight;
        this.assignmetnLineItemIds = assignmetnLineItemIds;
        this.parentAssignmentStatus = parentAssignmentStatus;
    }


    private long assignmentId;
    private long newAssignmentId;
    private String newPrimaryInstructorId;
    private String nativeAlaId;
    private String newNativeAlaId;
    private String title;
    private String newTitle;
    private long courseId;
    private long newCourseId;
    private long sectionId;
    private long newSectionId;
    private String provider;
    private String type;
    private String copyEZTStatus;
    private String error;
    private float weight = 0.0f;
    private List<Long> assignmetnLineItemIds = new ArrayList<Long>();
    private String parentAssignmentStatus;

    private String sourceSectionRelease;
    private String destSectionRelease;


    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public void setCopyEZTStatus(String copyEZTStatus) {
        this.copyEZTStatus = copyEZTStatus;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getNewAssignmentId() {
        return newAssignmentId;
    }

    public String getNewPrimaryInstructorId() {
        return newPrimaryInstructorId;
    }

    public String getNativeAlaId() {
        return nativeAlaId;
    }

    public String getNewNativeAlaId() {
        return newNativeAlaId;
    }

    public String getTitle() {
        return title;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public long getCourseId() {
        return courseId;
    }

    public long getNewCourseId() {
        return newCourseId;
    }

    public long getSectionId() {
        return sectionId;
    }

    public long getNewSectionId() {
        return newSectionId;
    }

    public String getProvider() {
        return provider;
    }

    public String getType() {
        return type;
    }

    public String getCopyEZTStatus() {
        return copyEZTStatus;
    }

    public String getError() {
        return error;
    }

    public float getWeight() {
        return weight;
    }

    public List<Long> getAssignmetnLineItemIds() {
        return assignmetnLineItemIds;
    }

    public String getParentAssignmentStatus() {
        return parentAssignmentStatus;
    }

    public void setSourceSectionRelease(String sourceSectionRelease) {
        this.sourceSectionRelease = sourceSectionRelease;
    }

    public void setDestSectionRelease(String destSectionRelease) {
        this.destSectionRelease = destSectionRelease;
    }

    public void setNewNativeAlaId(String newNativeAlaId) {
        this.newNativeAlaId = newNativeAlaId;
    }

    public void setNewAssignmentId(long newAssignmentId) {
        this.newAssignmentId = newAssignmentId;
    }

    @Override
    public String toString() {
        return "CopyAssignmentTO{" +
                "assignmentId=" + assignmentId +
                ", newAssignmentId=" + newAssignmentId +
                ", newPrimaryInstructorId='" + newPrimaryInstructorId + '\'' +
                ", nativeAlaId='" + nativeAlaId + '\'' +
                ", newNativeAlaId='" + newNativeAlaId + '\'' +
                ", title='" + title + '\'' +
                ", newTitle='" + newTitle + '\'' +
                ", courseId=" + courseId +
                ", newCourseId=" + newCourseId +
                ", sectionId=" + sectionId +
                ", newSectionId=" + newSectionId +
                ", provider='" + provider + '\'' +
                ", type='" + type + '\'' +
                ", copyEZTStatus='" + copyEZTStatus + '\'' +
                ", error='" + error + '\'' +
                ", weight=" + weight +
                ", assignmetnLineItemIds=" + assignmetnLineItemIds +
                ", parentAssignmentStatus='" + parentAssignmentStatus + '\'' +
                ", sourceSectionRelease='" + sourceSectionRelease + '\'' +
                ", destSectionRelease='" + destSectionRelease + '\'' +
                '}';
    }
}
