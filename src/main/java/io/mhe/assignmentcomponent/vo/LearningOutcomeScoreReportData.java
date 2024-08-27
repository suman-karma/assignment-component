package io.mhe.assignmentcomponent.vo;

public class LearningOutcomeScoreReportData implements Model{

	private long outcomeId;
	private double outcomeScore;
	private double outcomeWeightedScore;
	private String outcomeName;
	
	public long getOutcomeId() {
		return outcomeId;
	}
	public void setOutcomeId(long outcomeId) {
		this.outcomeId = outcomeId;
	}
	public double getOutcomeScore() {
		return outcomeScore;
	}
	public void setOutcomeScore(double outcomeScore) {
		this.outcomeScore = outcomeScore;
	}
	
	public double getOutcomeWeightedScore() {
		return outcomeWeightedScore;
	}
	public void setOutcomeWeightedScore(double outcomeWeightedScore) {
		this.outcomeWeightedScore = outcomeWeightedScore;
	}
	public String getOutcomeName() {
		return outcomeName;
	}
	public void setOutcomeName(String outcomeName) {
		this.outcomeName = outcomeName;
	}
	
}
