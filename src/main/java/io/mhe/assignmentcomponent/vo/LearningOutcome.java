package io.mhe.assignmentcomponent.vo;

import java.util.ArrayList;
import java.util.List;

public class LearningOutcome {
	
	private long outcomeId;
	private String outcomeName;
	private int outcomeOrderingId;
	boolean assignmentMapped;
	private List<LearningOutcomeRatingAndDescription> ratingDescriptionList = new ArrayList<LearningOutcomeRatingAndDescription>();
	private long gradedRatingId;
	private String gradedBy;
	private String feedBack;
	
	public long getOutcomeId() {
		return outcomeId;
	}
	public void setOutcomeId(long outcomeId) {
		this.outcomeId = outcomeId;
	}
	public String getOutcomeName() {
		return outcomeName;
	}
	public void setOutcomeName(String outcomeName) {
		this.outcomeName = outcomeName;
	}
	public int getOutcomeOrderingId() {
		return outcomeOrderingId;
	}
	public void setOutcomeOrderingId(int outcomeOrderingId) {
		this.outcomeOrderingId = outcomeOrderingId;
	}
	public List<LearningOutcomeRatingAndDescription> getRatingDescriptionList() {
		return ratingDescriptionList;
	}
	public void setRatingDescriptionList(
			List<LearningOutcomeRatingAndDescription> ratingDescriptionList) {
		this.ratingDescriptionList = ratingDescriptionList;
	}
	
	public boolean isAssignmentMapped() {
		return assignmentMapped;
	}
	public void setAssignmentMapped(boolean assignmentMapped) {
		this.assignmentMapped = assignmentMapped;
	}
	public long getGradedRatingId() {
		return gradedRatingId;
	}
	public void setGradedRatingId(long gradedRatingId) {
		this.gradedRatingId = gradedRatingId;
	}
	public String getGradedBy() {
		return gradedBy;
	}
	public void setGradedBy(String gradedBy) {
		this.gradedBy = gradedBy;
	}
	public String getFeedBack() {
		return feedBack;
	}
	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}
	
}
