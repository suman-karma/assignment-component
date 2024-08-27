package io.mhe.assignmentcomponent.vo;

import java.util.ArrayList;
import java.util.List;

public class CourseLearningOutcomes {
	
	private long rubricId;
	private String rubricName;
	private String isbn = null;
	private int maxRatingScore;
	private double totalRatingPoint;
	private List<LearningOutcomeCategory> learningOutcomeCategoryList=new ArrayList<LearningOutcomeCategory>();
	private List<LearningOutcomeRatingAndDescription> rubricRatingList = new ArrayList<LearningOutcomeRatingAndDescription>();
	private List<LearningOutcomeCategoryScoreReportData> categoryScoreReportDataList = new ArrayList<LearningOutcomeCategoryScoreReportData>();
	private boolean studentOBAView;
	
	public boolean isStudentOBAView() {
		return studentOBAView;
	}
	public void setStudentOBAView(boolean studentOBAView) {
		this.studentOBAView = studentOBAView;
	}
	public long getRubricId() {
		return rubricId;
	}
	public void setRubricId(long rubricId) {
		this.rubricId = rubricId;
	}
	public String getRubricName() {
		return rubricName;
	}
	public void setRubricName(String rubricName) {
		this.rubricName = rubricName;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public List<LearningOutcomeCategory> getLearningOutcomeCategoryList() {
		return learningOutcomeCategoryList;
	}
	public void setLearningOutcomeCategoryList(
			List<LearningOutcomeCategory> learningOutcomeCategoryList) {
		this.learningOutcomeCategoryList = learningOutcomeCategoryList;
	}
	public List<LearningOutcomeRatingAndDescription> getRubricRatingList() {
		return rubricRatingList;
	}
	public void setRubricRatingList(
			List<LearningOutcomeRatingAndDescription> rubricRatingList) {
		this.rubricRatingList = rubricRatingList;
	}
	public int getMaxRatingScore() {
		return maxRatingScore;
	}
	public void setMaxRatingScore(int maxRatingScore) {
		this.maxRatingScore = maxRatingScore;
	}
	public double getTotalRatingPoint() {
		return totalRatingPoint;
	}
	public void setTotalRatingPoint(double totalRatingPoint) {
		this.totalRatingPoint = totalRatingPoint;
	}
	public List<LearningOutcomeCategoryScoreReportData> getCategoryScoreReportDataList() {
		return categoryScoreReportDataList;
	}
	public void setCategoryScoreReportDataList(
			List<LearningOutcomeCategoryScoreReportData> categoryScoreReportDataList) {
		this.categoryScoreReportDataList = categoryScoreReportDataList;
	}

}
