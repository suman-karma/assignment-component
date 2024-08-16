package io.mhe.assignmentcomponent.vo;

public class LearningOutcomeRatingAndDescription { 
	
	private long ratingId;
	private String ratingName;
	private int ratingScore;
	private long ratingDescriptionId;
	private String ratingDescription=" ";
	
	public long getRatingId() {
		return ratingId;
	}
	public void setRatingId(long ratingId) {
		this.ratingId = ratingId;
	}
	public String getRatingName() {
		return ratingName;
	}
	public void setRatingName(String ratingName) {
		this.ratingName = ratingName;
	}
	public int getRatingScore() {
		return ratingScore;
	}
	public void setRatingScore(int ratingScore) {
		this.ratingScore = ratingScore;
	}
	public long getRatingDescriptionId() {
		return ratingDescriptionId;
	}
	public void setRatingDescriptionId(long ratingDescriptionId) {
		this.ratingDescriptionId = ratingDescriptionId;
	}
	public String getRatingDescription() {
		return ratingDescription;
	}
	public void setRatingDescription(String ratingDescription) {
		this.ratingDescription = ratingDescription;
	}
	

}
