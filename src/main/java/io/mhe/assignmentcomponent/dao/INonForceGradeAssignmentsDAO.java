package io.mhe.assignmentcomponent.dao;



import io.mhe.assignmentcomponent.vo.AssignmentDatesVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface INonForceGradeAssignmentsDAO {
	
	/**
	 * Inserts or updates the GRADING_QUEUE_NONFG_ASSIGNMENTS table based on the date policy updates in ASSIGNMENT_POLICY_XREF table
	 * @param sectionAssignmentsMap
	 */
	public void insertOrUpdateDate(Map<Long,List<Long>> sectionAssignmentsMap);
	public void insertOrUpdateDate(List<AssignmentDatesVO> datesList);
	

}
