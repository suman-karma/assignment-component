package io.mhe.assignmentcomponent.service.generic.vo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: Praveen_Meruga
 * Date: Mar 8, 2007
 * Time: 5:40:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeworkManagerCategory implements Model {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryID() {
        return id;
    }

    public void setCategoryID(long id) {
        this.id = id;
    }

    private String name = "";
    private long id = 0l;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getCourseid() {
        return courseid;
    }

    public void setCourseid(long courseid) {
        this.courseid = courseid;
    }

    public String getInstructorid() {
        return instructorid;
    }

    public void setInstructorid(String instructorid) {
        this.instructorid = instructorid;
    }

    private String instructorid = "0";

    private String level = "";
    private long courseid = 0l;
    private boolean isPolicyRestructureEnabled;
    
    public boolean isPolicyRestructureEnabled() {
		return isPolicyRestructureEnabled;
	}

	public void setPolicyRestructureEnabled(boolean isPolicyRestructureEnabled) {
		this.isPolicyRestructureEnabled = isPolicyRestructureEnabled;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
