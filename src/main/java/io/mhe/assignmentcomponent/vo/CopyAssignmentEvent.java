package io.mhe.assignmentcomponent.vo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CopyAssignmentEvent {
    private CopyAssignmentTO srcAssignment;
    private long oldSectionID;
    private long newSectionID;
    private long[] origCategoryIds;
    private long[] newCategoryIds;
    private long newCourseId;
    private long newSectionId;
    private HashMap modulesMap;
    private Map assignMap;
    private String coursePrimaryInstructorId ;
    Map<Long, Long> oldAndNewCategories = new HashMap<Long, Long>();
    Map<Long, Long> oldAndNewOutcomes = new HashMap<Long, Long>();
    boolean isMarathon;

    public CopyAssignmentEvent() {
    }

    public CopyAssignmentTO getSrcAssignment() {
        return srcAssignment;
    }

    public void setSrcAssignment(CopyAssignmentTO srcAssignment) {
        this.srcAssignment = srcAssignment;
    }

    public long getOldSectionID() {
        return oldSectionID;
    }

    public void setOldSectionID(long oldSectionID) {
        this.oldSectionID = oldSectionID;
    }

    public long getNewSectionID() {
        return newSectionID;
    }

    public void setNewSectionID(long newSectionID) {
        this.newSectionID = newSectionID;
    }

    public long[] getOrigCategoryIds() {
        return origCategoryIds;
    }

    public void setOrigCategoryIds(long[] origCategoryIds) {
        this.origCategoryIds = origCategoryIds;
    }

    public long[] getNewCategoryIds() {
        return newCategoryIds;
    }

    public void setNewCategoryIds(long[] newCategoryIds) {
        this.newCategoryIds = newCategoryIds;
    }

    public long getNewCourseId() {
        return newCourseId;
    }

    public void setNewCourseId(long newCourseId) {
        this.newCourseId = newCourseId;
    }

    public long getNewSectionId() {
        return newSectionId;
    }

    public void setNewSectionId(long newSectionId) {
        this.newSectionId = newSectionId;
    }

    public HashMap getModulesMap() {
        return modulesMap;
    }

    public void setModulesMap(HashMap modulesMap) {
        this.modulesMap = modulesMap;
    }

    public Map getAssignMap() {
        return assignMap;
    }

    public void setAssignMap(Map assignMap) {
        this.assignMap = assignMap;
    }

    public Map<Long, Long> getOldAndNewCategories() {
        return oldAndNewCategories;
    }

    public void setOldAndNewCategories(Map<Long, Long> oldAndNewCategories) {
        this.oldAndNewCategories = oldAndNewCategories;
    }

    public Map<Long, Long> getOldAndNewOutcomes() {
        return oldAndNewOutcomes;
    }

    public void setOldAndNewOutcomes(Map<Long, Long> oldAndNewOutcomes) {
        this.oldAndNewOutcomes = oldAndNewOutcomes;
    }

    public boolean isMarathon() {
        return isMarathon;
    }

    public void setMarathon(boolean marathon) {
        isMarathon = marathon;
    }

    public String getCoursePrimaryInstructorId() {
        return coursePrimaryInstructorId;
    }

    public void setCoursePrimaryInstructorId(String coursePrimaryInstructorId) {
        this.coursePrimaryInstructorId = coursePrimaryInstructorId;
    }

    @Override
    public String toString() {
        return "CopyAssignmentEvent{" +
                "srcAssignment=" + srcAssignment +
                ", oldSectionID=" + oldSectionID +
                ", newSectionID=" + newSectionID +
                ", origCategoryIds=" + Arrays.toString(origCategoryIds) +
                ", newCategoryIds=" + Arrays.toString(newCategoryIds) +
                ", newCourseId=" + newCourseId +
                ", newSectionId=" + newSectionId +
                ", modulesMap=" + modulesMap +
                ", assignMap=" + assignMap +
                ", coursePrimaryInstructorId='" + coursePrimaryInstructorId + '\'' +
                ", oldAndNewCategories=" + oldAndNewCategories +
                ", oldAndNewOutcomes=" + oldAndNewOutcomes +
                ", isMarathon=" + isMarathon +
                '}';
    }
}
