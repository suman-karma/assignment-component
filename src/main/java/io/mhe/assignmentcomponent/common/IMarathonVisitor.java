package io.mhe.assignmentcomponent.common;


import io.mhe.assignmentcomponent.vo.MarathonBucket;
import io.mhe.assignmentcomponent.vo.MarathonBucketAssignment;
import io.mhe.assignmentcomponent.vo.MarathonInfo;

/**
 * Represents an interface to visit a marathon structure including all its hierarchy. User: Stephen Lazaronak Date:
 * 1/15/14
 */
public interface IMarathonVisitor {

	/**
	 * Called before processing a marathon.
	 * @param marathonInfo
	 * @return true if should continue processing or false otherwise.
	 */
	boolean beforeMarathon(final MarathonInfo marathonInfo);

	/**
	 * Called after processing a marathon.
	 * @param marathonInfo
	 */
	void afterMarathon(final MarathonInfo marathonInfo);

	/**
	 * Called before processing a marathon bucket.
	 * @param mBucket
	 * @return true if should continue processing or false otherwise.
	 */
	boolean beforeMarathonBucket(final MarathonBucket mBucket);

	/**
	 * Called after processing a marathon bucket.
	 * @param mBucket
	 */
	void afterMarathonBucket(final MarathonBucket mBucket);

	void visitMarathonAssignment(final MarathonBucketAssignment mAssignment);

	abstract class AbstractEmptyVisitor implements IMarathonVisitor {
		@Override
		public boolean beforeMarathon(MarathonInfo marathonInfo) {
			return true;
		}

		@Override
		public void afterMarathon(MarathonInfo marathonInfo) {
		}

		@Override
		public boolean beforeMarathonBucket(MarathonBucket mBucket) {
			return true;
		}

		@Override
		public void afterMarathonBucket(MarathonBucket mBucket) {
		}

		@Override
		public void visitMarathonAssignment(MarathonBucketAssignment mAssignment) {
		}
	}
}
