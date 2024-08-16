package io.mhe.assignmentcomponent.vo;



import io.mhe.assignmentcomponent.common.IMarathonVisitor;
import io.mhe.assignmentcomponent.common.constant.Visitable;

import java.util.Date;
import java.util.List;

public class MarathonInfo extends Marathon implements Model, Visitable<IMarathonVisitor> {
	private static final long		serialVersionUID	= 9149098819062160411L;

	private String					notes;
	private Date					updatedDate;
	private List<MarathonBucket>	bucketList				= null;
	private String					isDeleted;
	private String					isEditable;

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the bucket
	 */
	public List<MarathonBucket> getBucketList() {
		return bucketList;
	}

	/**
	 * @param bucketList the bucket list to set
	 */
	public void setBucketList(List<MarathonBucket> bucketList) {
		this.bucketList = bucketList;
	}

	/**
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public void accept(IMarathonVisitor visitor) {
		if (visitor.beforeMarathon(this)) {
            if (this.getBucketList() != null) {
                for (final MarathonBucket bucket : this.getBucketList()) {
                    bucket.accept(visitor);
                }
            }
			visitor.afterMarathon(this);
		}
	}

	/**
	 * @return the isEditable
	 */
	public String getIsEditable() {
		return isEditable;
	}

	/**
	 * @param isEditable the isEditable to set
	 */
	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}

}
