package io.mhe.assignmentcomponent.service.generic.vo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: Praveen_Meruga
 * Date: Mar 8, 2007
 * Time: 5:28:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class HMAssignmentCategoryPolicy extends HomeworkManagerCategoryPolicy {

    public long getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(long assignment_id) {
        this.assignment_id = assignment_id;
    }
    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }
    private long assignment_id = 0L;
    private long sectionId = 0L;
    
    @Override
    public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
