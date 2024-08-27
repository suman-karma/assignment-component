package io.mhe.assignmentcomponent.vo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LearningOutcomeCategoryScoreReportData implements Model{

	
	private long categoryId;
	private String categoryName;
	private long categoryWeight;
	private int assignmentCount;
	private double categoryWeightedAverage;
	private double categoryAverage;
	private List <LearningOutcomeScoreReportData> outcomeScoreList = new ArrayList <LearningOutcomeScoreReportData>();
	private Map <String,Integer> barchartData = new LinkedHashMap <String,Integer>();
    private List<String> legends = new ArrayList<String>();
	
	public Map<String, Integer> getBarchartData() {
		return barchartData;
	}
	public void setBarchartData(Map<String, Integer> barchartData) {
		this.barchartData = barchartData;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}	
	public long getCategoryWeight() {
		return categoryWeight;
	}
	public void setCategoryWeight(long categoryWeight) {
		this.categoryWeight = categoryWeight;
	}
	public int getAssignmentCount() {
		return assignmentCount;
	}
	public void setAssignmentCount(int assignmentCount) {
		this.assignmentCount = assignmentCount;
	}
	public double getCategoryWeightedAverage() {
		return categoryWeightedAverage;
	}
	public void setCategoryWeightedAverage(double categoryWeightedAverage) {
		this.categoryWeightedAverage = categoryWeightedAverage;
	}
	public double getCategoryAverage() {
		return categoryAverage;
	}
	public void setCategoryAverage(double categoryAverage) {
		this.categoryAverage = categoryAverage;
	}
	

    public List<LearningOutcomeScoreReportData> getOutcomeScoreList() {
		return outcomeScoreList;
	}
	public void setOutcomeScoreList(
			List<LearningOutcomeScoreReportData> outcomeScoreList) {
		this.outcomeScoreList = outcomeScoreList;
	}
	public List<String> getLegends() {
        return legends;
    }

    public void setLegends(List<String> legends) {
        this.legends = legends;
    }

    @Override
    public String toString() {
        return "LearningOutcomeCategoryScoreReportData{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryWeight=" + categoryWeight +
                ", assignmentCount=" + assignmentCount +
                ", categoryWeightedAverage=" + categoryWeightedAverage +
                ", categoryAverage=" + categoryAverage +
                ", outcomeScoreList=" + outcomeScoreList +
                ", barchartData=" + barchartData +
                ", legends=" + legends +
                '}';
    }
}
