package io.mhe.assignmentcomponent.vo;

import com.mhe.connect.business.marathon.common.IMarathonVisitor;
import com.mhe.connect.common.Visitable;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class MarathonBucket implements Model, Visitable<IMarathonVisitor> {
	private static final long				serialVersionUID		= 9149011819762160411L;

	private long							bucketId;
	private long							order;
	private List<MarathonBucketAssignment>	marathonAssignmentList	= new ArrayList<MarathonBucketAssignment>();
	private String							label;

	/**
	 * @return the bukcetId
	 */
	public long getBucketId() {
		return bucketId;
	}

	/**
	 * @param bucketId the bucketId to set
	 */
	public void setBucketId(long bucketId) {
		this.bucketId = bucketId;
	}

	/**
	 * @return the order
	 */
	public long getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(long order) {
		this.order = order;
	}

	/**
	 * @return the marathonAssignment
	 */
	public List<MarathonBucketAssignment> getMarathonAssignmentList() {
		return marathonAssignmentList;
	}

	/**
	 * @param marathonAssignmentList the marathonAssignment list to set
	 */
	public void setMarathonAssignmentList(List<MarathonBucketAssignment> marathonAssignmentList) {
		this.marathonAssignmentList = marathonAssignmentList;
	}

	/**
	 * @return the identifier
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the identifier to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Bucket is treated as active if it contains at least one active bucket assignment.
	 * @return
	 */
	public boolean isActive() {
		if (CollectionUtils.isEmpty(marathonAssignmentList)) {
			return false;
		}
		for (final MarathonBucketAssignment mba : marathonAssignmentList) {
			if (mba.isActive() == null) {
				throw new IllegalStateException("Could not retrieve the bucket status since assignments statuses are not initialized.");
			}
			else if (mba.isActive()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void accept(IMarathonVisitor visitor) {
		if (visitor.beforeMarathonBucket(this)) {
			if (this.getMarathonAssignmentList() != null) {
				for (final MarathonBucketAssignment assignment : this.getMarathonAssignmentList()) {
					assignment.accept(visitor);
				}
			}
			visitor.afterMarathonBucket(this);
		}
	}
}
