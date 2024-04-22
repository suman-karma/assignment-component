/*
 * $Source:
 * /web/cvs/connect-suite/connect/connect-server/src/main/java/com/mhe/connect/business/gradebook/valueobj/Activity
 * .java,v $ $Revision$ $Date$ Activity.java Copyright 2001 The McGraw-Hill Companies. All
 * Rights Reserved Created on Jul 9, 2003, 2:48:52 PM by Kameshwar.
 */

package io.mhe.assignmentcomponent.vo;

//xxx import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Kameshwar $Author$
 */
public class Activity implements Model {
private static final longserialVersionUID			= -2582181326899433424L;
public static final StringREPEATABLE_TYPE_UNLIMIT		= "UNLIMIT";
public static final StringREPEATABLE_TYPE_MAX_ATTEMPT= "MAX_ATTEMPT";
public static final StringREPEATABLE_TYPE_MIN_SCORE= "MIN_SCORE";
public static final floatMAX_POINT_LIMIT				= 2000.00f;

private long				id							= 0L;
private String				title						= "";
// Property _weight has been changed to float to support fractional points for Ez Test.
private float				weight						= 0.0f;
private long				assignmentID				= 0L;
private String				beginNote					= "";
private String				endNote						= "";
private boolean				weightBased					= false;
private float				maxScore					= 0F;
private long				sequenceNo					= 0L;
private Date				updatedDate					= new Date();
private Date				createdDate					= new Date();
private long				alaManagerID				= 0L;
private boolean				repeatable					= false;
private String				type						= "";
private String				repeatableType				= "";
private long				repeatableValue				= 0L;

// Added by Praveen.
private boolean				timable						= false;
private long				timeAllocatedInSeconds		= 0L;
// for printable feature
private boolean				printable					= true;

private int					questions					= 0;
private int					availableQuestions			= -1;

private String				nativeAlaId					= null;
private String				alaContentProvider			= null;

private List<ActivityItem>itemList					= new ArrayList<ActivityItem>();

public String getAlaContentProvider() {
		return alaContentProvider;
}

public void setAlaContentProvider(String alaContentProvider) {
		this.alaContentProvider = alaContentProvider;
}

public String getNativeAlaId() {
		return nativeAlaId;
}

public void setNativeAlaId(String nativeAlaId) {
		this.nativeAlaId = nativeAlaId;
}

public int getAvailableQuestions() {
		return availableQuestions;
}

public void setAvailableQuestions(int availableQuestions) {
		this.availableQuestions = availableQuestions;
}

public boolean isPrintable() {
		return printable;
}

public void setPrintable(boolean printable) {
		this.printable = printable;
}

public long getTimeAllocatedInSeconds() {
		return timeAllocatedInSeconds;
}

public void setTimeAllocatedInSeconds(long timeAllocatedInSeconds) {
		this.timeAllocatedInSeconds = timeAllocatedInSeconds;
}

public String getRepeatableType() {
		return repeatableType;
}

public void setRepeatableType(String repeatableType) {
		this.repeatableType = repeatableType;
}

public long getRepeatableValue() {
		return repeatableValue;
}

public void setRepeatableValue(long repeatableValue) {
		this.repeatableValue = repeatableValue;
}

public String getType() {
		return type;
}

public void setType(String type) {
		this.type = type;
}

public long getID() {
		return id;
}

public void setID(long id) {
		this.id = id;
}

public String getTitle() {
		return title;
}

public long getAssignmentID() {
		return assignmentID;
}

public void setAssignmentID(long assignmentID) {
		this.assignmentID = assignmentID;
}

public void setTitle(String title) {
		this.title = title;
}

public float getWeight() {
		return weight;
}

public void setWeight(float weight) {
		this.weight = weight;
}

public String getBeginNote() {
		return beginNote;
}

public void setBeginNote(String beginNote) {
		this.beginNote = beginNote;
}

public String getEndNote() {
		return endNote;
}

public void setEndNote(String endNote) {
		this.endNote = endNote;
}

public boolean isWeightBased() {
		return weightBased;
}

public void setWeightBased(boolean weightBased) {
		this.weightBased = weightBased;
}

public float getMaxScore() {
		return maxScore;
}

public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
}

public long getSequenceNo() {
		return sequenceNo;
}

public void setSequenceNo(long sequenceNo) {
		this.sequenceNo = sequenceNo;
}

public Date getUpdatedDate() {
		return updatedDate;
}

public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
}

public Date getCreatedDate() {
		return createdDate;
}

public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
}

public long getAlaManagerID() {
		return alaManagerID;
}

public void setAlaManagerID(long alaManagerID) {
		this.alaManagerID = alaManagerID;
}

public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
}

public boolean isRepeatable() {
		return this.repeatable;
}

public boolean istimable() {
		return timable;
}

public void settimable(boolean timable) {
		this.timable = timable;
}

public void addActivityItem(ActivityItem item) {
		this.itemList.add(item);
}

public ActivityItem[] getActivityItems() {
		return (ActivityItem[]) this.itemList.toArray(new ActivityItem[0]);
}

public int getQuestions() {
		return questions;
}

public void setQuestions(int questions) {
		this.questions = questions;
}

public void setActivityItems(ActivityItem[] activityItems) {
		for (int i = 0; i < activityItems.length; i++) {
			itemList.add(activityItems[i]);
		}
}

public void setActivityItems(List<ActivityItem> activityItems) {
		this.itemList = activityItems;
}

@Override
public String toString() {
		//xxx return ReflectionToStringBuilder.toString(this);
		return ""; //xxx
}
}