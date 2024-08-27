package io.mhe.assignmentcomponent.dao;

import com.mhe.common.configuration.IConfigurationProvider;
import com.mhe.common.configuration.SQLBackedConfigurationProvider;
import com.mhe.common.text.ITemplateService;
import io.mhe.assignmentcomponent.constant.LMSConstants;
import io.mhe.assignmentcomponent.constant.PoliciesServiceConstants;
import io.mhe.assignmentcomponent.vo.AssignmentDatesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("iNonForceGradeAssignmentsDAO")
public class NonForceGradeAssignmentsDaoJdbc implements INonForceGradeAssignmentsDAO {
	
	private static final String getAssignmentDatesFromPolicy = "policies/getAssignmentDatesFromPolicy.sql";

	private static final String insertOrUpdateAssignmentDatesInGradingQueue = "policies/mergeAssignmentDatesInGradingQueue.sql";
	
	private static final String insertOrUpdateAssignmentDatesInGradingQueueConditionally = "policies/mergeAssignmentDatesInGradingQueueConditionally.sql";
	
	@Autowired
	@Qualifier("sql")
	private ITemplateService queryBuilder;

	@Autowired
	@Qualifier("configurationProperties")
	private SQLBackedConfigurationProvider configurationProvider;

	@Autowired(required=true)
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static Logger logger = LoggerFactory.getLogger(NonForceGradeAssignmentsDaoJdbc.class);
	
	@Override
	public void insertOrUpdateDate(Map<Long,List<Long>> sectionAssignmentsMap) {
		String replicateAssignmentDates = configurationProvider.getProperty(LMSConstants.REPLICATE_ASSIGNMENT_DATES, LMSConstants.ENABLED);
		if(!LMSConstants.ENABLED.equalsIgnoreCase(replicateAssignmentDates)) {
			logger.debug("[ASSIGNMENT_DATE_UPDATE] will not proceed further as replicate_assignment_dates flag is {}", new Object[] {replicateAssignmentDates});
			return;
		}
		for (Long sectionId : sectionAssignmentsMap.keySet()) {
			insertOrUpdateAssignmentDates(sectionId, sectionAssignmentsMap.get(sectionId));
		}
	}
	
	@Override
	public void insertOrUpdateDate(List<AssignmentDatesVO> datesList) {
		String replicateAssignmentDates = configurationProvider.getProperty(LMSConstants.REPLICATE_ASSIGNMENT_DATES, LMSConstants.ENABLED);
		if(!LMSConstants.ENABLED.equalsIgnoreCase(replicateAssignmentDates)) {
			logger.debug("[ASSIGNMENT_DATE_UPDATE] will not proceed further as replicate_assignment_dates flag is {}", new Object[] {replicateAssignmentDates});
			return;
		}
		
		for(AssignmentDatesVO assignVO : datesList) {
			long sectionId = assignVO.getSectionId();
			long assignmentId = assignVO.getAssignmentId();
			insertOrUpdateDatesGradingQConditional(assignVO);
		}

	}
	
	private void insertOrUpdateDatesGradingQConditional(AssignmentDatesVO assignVO) {
		Map<String, Object> paramMap = getParamsForUpsert(assignVO);
		try {
			namedParameterJdbcTemplate.update(queryBuilder.process(insertOrUpdateAssignmentDatesInGradingQueueConditionally), paramMap);
		} catch (Exception ex) {
			logger.error("[ASSIGNMENT_DATE_UPDATE] Exception while insert or updating date policies in GRADING_QUEUE_NONFG_ASSIGNMENTS with assignmentId {}, sectionId {}, inputParams {} and exception {}: ",new Object[] { assignVO.getAssignmentId(), assignVO.getSectionId(), paramMap, ex});
		}
		return;
	}

	private void insertOrUpdateAssignmentDates(Long sectionId, List<Long> assignmentsAbsentInDB) {
		// TODO
		assignmentsAbsentInDB.stream()
			.map(assignmentId -> getAssignmentDatesAssgnPolXref(sectionId, assignmentId))
				.forEach(assignVO -> insertOrUpdateIntoGradingQ(assignVO));
	}

	private void insertOrUpdateIntoGradingQ(AssignmentDatesVO assignVO) {
		Map<String, Object> paramMap = getParamsForUpsert(assignVO);
		try {
			namedParameterJdbcTemplate.update(queryBuilder.process(insertOrUpdateAssignmentDatesInGradingQueue), paramMap);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			logger.error("[ASSIGNMENT_DATE_UPDATE] Exception while merging date policies in GRADING_QUEUE_NONFG_ASSIGNMENTS with assignmentId {}, sectionId {}, inputParams {} and exception {}: ",new Object[] {assignVO.getAssignmentId(), assignVO.getSectionId(), paramMap, ex});
		}
		return;
	}

	private Map<String, Object> getParamsForUpsert(AssignmentDatesVO assignVO) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("dueDate", assignVO.getDueDateStr());
		paramMap.put("startDate", assignVO.getStartDateStr());
		paramMap.put("sectionId", assignVO.getSectionId());
		paramMap.put("assignmentId", assignVO.getAssignmentId());
		return paramMap;
	}

	private AssignmentDatesVO getAssignmentDatesAssgnPolXref(Long sectionId, Long assignmentId) {
		AssignmentDatesVO assignVO = new AssignmentDatesVO();
		assignVO.setAssignmentId(assignmentId);
		assignVO.setSectionId(sectionId);
		// TODO
		try {
			namedParameterJdbcTemplate.query(queryBuilder.process(getAssignmentDatesFromPolicy), Map.of("sectionId", sectionId, "assignmentId", assignmentId), new ResultSetExtractor() {
				@Override
				public Object extractData(
						ResultSet rs) throws SQLException, DataAccessException {
					String dueDateStr = null;
					String lastSubmissionDateStr = null;
					while (rs.next()) {
						String exchangeKey = rs.getString("exchane_key");
						String value = rs.getString("value");
						if(PoliciesServiceConstants.P_STARTDATE.equalsIgnoreCase(exchangeKey)) {
							assignVO.setStartDateStr(value);
						} else if(PoliciesServiceConstants.P_DUEDATE.equalsIgnoreCase(exchangeKey)){
							dueDateStr = value;
						}
						else if(PoliciesServiceConstants.P_LATESUBMISSIONDUE.equalsIgnoreCase(exchangeKey)) {
							lastSubmissionDateStr = value;
						}
					}
					if(lastSubmissionDateStr!=null && !lastSubmissionDateStr.isEmpty()) {
						assignVO.setDueDateStr(lastSubmissionDateStr);
					} 
					else if(dueDateStr!=null && !dueDateStr.isEmpty()) {
						assignVO.setDueDateStr(dueDateStr);
					}
					return null;
				}
			});
		} catch (Exception ex) {
			logger.error("[ASSIGNMENT_DATE_UPDATE] Exception while getting date policies with assignmentId {}, sectionId {} and exception {}: ",new Object[] {assignmentId, sectionId, ex});
		}
		return assignVO;
	}
}
