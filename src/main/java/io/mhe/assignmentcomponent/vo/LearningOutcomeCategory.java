package io.mhe.assignmentcomponent.vo;

import java.util.ArrayList;
import java.util.List;

public class LearningOutcomeCategory {
	
	private long categoryId;
	private String categoryName;
	private int categoryOrderingId;
	private List<LearningOutcome> outcomeList = new ArrayList<LearningOutcome>();
	private int categoryWeight;
	private boolean assignmentMapped;
	private long assignRubricCategoryXrefId;
	private boolean studentOBAView;
	
	public boolean isStudentOBAView() {
		return studentOBAView;
	}
	public void setStudentOBAView(boolean studentOBAView) {
		this.studentOBAView = studentOBAView;
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
	public int getCategoryOrderingId() {
		return categoryOrderingId;
	}
	public void setCategoryOrderingId(int categoryOrderingId) {
		this.categoryOrderingId = categoryOrderingId;
	}
	public List<LearningOutcome> getOutcomeList() {
		return outcomeList;
	}
	public void setOutcomeList(List<LearningOutcome> outcomeList) {
		this.outcomeList = outcomeList;
	}	
	public int getCategoryWeight() {
		return categoryWeight;
	}
	public void setCategoryWeight(int categoryWeight) {
		this.categoryWeight = categoryWeight;
	}
	public boolean isAssignmentMapped() {
		return assignmentMapped;
	}
	public void setAssignmentMapped(boolean assignmentMapped) {
		this.assignmentMapped = assignmentMapped;
	}
	public long getAssignRubricCategoryXrefId() {
		return assignRubricCategoryXrefId;
	}
	public void setAssignRubricCategoryXrefId(long assignRubricCategoryXrefId) {
		this.assignRubricCategoryXrefId = assignRubricCategoryXrefId;
	}
	
}
