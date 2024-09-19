package io.mhe.assignmentcomponent.service.generic.dao;


import com.mhe.common.text.ITemplateService;
import io.mhe.assignmentcomponent.basiclti.consumersecret.api.OAuthConsumerDetails;
import io.mhe.assignmentcomponent.common.util.DateUtil;
import io.mhe.assignmentcomponent.common.util.GenUtil;
import io.mhe.assignmentcomponent.service.generic.util.AnswerTolerancePolicyUtil;
import io.mhe.assignmentcomponent.service.generic.vo.HMAssignmentCategoryPolicy;
import io.mhe.assignmentcomponent.service.generic.vo.HomeworkManagerCategory;
import io.mhe.assignmentcomponent.service.generic.vo.HomeworkManagerCategoryPolicy;
import io.mhe.assignmentcomponent.service.generic.vo.HomeworkManagerPolicy;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("genericAssignmentsDao")
public class GenericAssignmentsDao implements IGenericAssignmentsDao{

	private final Logger logger = LoggerFactory.getLogger(GenericAssignmentsDao.class);
	private static final String INSERTFAULTTOLERANCE = "insert into fault_tx_POWER_OF_PROCESS(id,operation,request_json,exception,url,http_method,created_date)values(GENERIC_ASSIGN_SEQ.nextval,?,?,?,?,?,sysdate)";
	private static final String P_FBEZTOTOLERANCE = "p_fbeztotolerance";
	private static final int DATA_LIMIT = 4000;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public enum CATEGORY_LEVEL {
		SYSTEM("system"), COURSE("course");

		private final String description;

		CATEGORY_LEVEL(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	@Autowired
	@Qualifier("sql")
	private ITemplateService queryBuilder;


	public static final String GET_COURSE_ID_BY_SECTION_ID = "SELECT COURSE_ID FROM SECTION where SECTION_ID =:sectionID and is_deleted = 'false' ";

	private static final StringBuilder GET_CONTENT_POLICIES_FOR_ASSIGNMENTS_AND_SECTION = new StringBuilder(
			"SELECT acpxf.assignment_id, p.id, p.name, p.type, acpxf.value, p.exchane_key")
			.append(" FROM assignment_content_policy_xref acpxf, policy p")
			.append(" WHERE acpxf.assignment_id in (:assignmentIds) AND section_id=:sectionId")
			.append(" AND acpxf.policy_id = p.id AND p.is_content_policy='Y' AND p.deleted='N'");

	private static final StringBuilder GET_POLICIES_FROM_POLICY_INSTANCE_SET_FOR_ASSIGNMENTS_AND_SECTION = new StringBuilder(
			"SELECT assign_pis.assignment_id, pi.policy_id, pi.value, p.exchane_key, p.name AS policy_name, p.is_content_policy")
			.append(" FROM policy_instance_set pis, policy_instance pi, policy p, (SELECT ali.assignment_id, slipi.policy_instance_set_id")
			.append(" FROM sec_line_item_policy_instance slipi, assignment_line_item ali WHERE slipi.section_id=:sectionId")
			.append(" AND ali.assignment_id in (:assignmentIds) AND slipi.assign_line_item_id=ali.id AND ali.draft_no = 0) assign_pis")
			.append(" WHERE pis.id =assign_pis.policy_instance_set_id AND pis.id = pi.policy_instance_set_id (+) AND p.id = pi.policy_id");

	public static final String	GET_COURSE_TIMEZONE_USING_SECTION_ID = "SELECT TIME_ZONE FROM COURSE WHERE COURSE_ID IN (SELECT COURSE_ID FROM SECTION WHERE SECTION_ID = ?)";

	public static final String GET_CUSTOMER_DETAILS = "SELECT O.* FROM OAPI_OAUTH_CONFIG O WHERE O.RCRD_STS_ID=1 AND CONSUMER_KEY = ?";

	/**
	 * DAO API to audit rest call failures for generic assignments
	 * @param operation
	 * @param jsonData
	 * @param exception
	 * @param url
	 * @param method
	 */
	@Override
	public void auditRestCallFailures(String operation,
			String jsonData, Exception exception,String url,String method) {
		String message = StringUtils.left(ExceptionUtils.getStackTrace(exception), DATA_LIMIT);
		jdbcTemplate.update(INSERTFAULTTOLERANCE, operation, jsonData,
				message, url,method);
	}

	@Override
	public long getCourseIDBySectionID(long sectionID) {
		logger.debug("getCourseIDBySectionID, sql={}, sectionID={}", GET_COURSE_ID_BY_SECTION_ID, sectionID);
		try {
			return jdbcTemplate.queryForObject(GET_COURSE_ID_BY_SECTION_ID, new Object[] { sectionID }, Long.class);
		} catch (EmptyResultDataAccessException e) {
			throw e;
		} catch (Exception e) {
			throw  e;
		}
	}


	public HMAssignmentCategoryPolicy getPoliciesBySectionAndAssignment(final Long sectionId, final Long assignmentId) {
		final HMAssignmentCategoryPolicy hmAssignCatPolicy = new HMAssignmentCategoryPolicy();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sectionId", sectionId);
		paramMap.put("assignmentId", assignmentId);
		String selectQuery = queryBuilder.process("policies/getPoliciesForAssignment.sql", paramMap);
		try {
			  namedParameterJdbcTemplate.query(selectQuery, paramMap, new ResultSetExtractor<Void>() {
				@Override
				public Void extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, HomeworkManagerCategoryPolicy> map = extractCategoryPolicyValuesFromResultSet(rs);
					for (Map.Entry<String, HomeworkManagerCategoryPolicy> entry : map.entrySet()) {
						hmAssignCatPolicy.setPoliciesList(entry.getValue().getPoliciesList());
						hmAssignCatPolicy.setName(entry.getValue().getName());
						hmAssignCatPolicy.setCategoryID(entry.getValue().getCategoryID());
						hmAssignCatPolicy.setCourseid(entry.getValue().getCourseid());
						hmAssignCatPolicy.setInstructorid(entry.getValue().getInstructorid());
						hmAssignCatPolicy.setLevel(entry.getValue().getLevel());
						hmAssignCatPolicy.setSectionId(sectionId);
						hmAssignCatPolicy.setAssignment_id(assignmentId);
						break;
					}
					return null;
				}
			});
		} catch (Exception ex) {
			throw ex;
		}
		// Set assignment content policies first. If no policies found, set
		// policies retrieved from policy instance set
		setAssignmentContentPoliciesOrPolciesFromPolicyInstanceSet(assignmentId, sectionId, hmAssignCatPolicy);

		return hmAssignCatPolicy;
	}

	@Override
	public String getCourseIsbn(long assignmentId)  {
		String query = "SELECT ISBN  FROM COURSE  WHERE IS_DELETED = 'false' AND COURSE_ID IN(SELECT DISTINCT COURSE_ID FROM SECTION WHERE SECTION.IS_DELETED = 'false' AND SECTION_ID IN(SELECT SECTION_ID FROM SECTION_ASSIGNMENT_XREF WHERE ASSIGNMENT_ID=?))";
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pst = null;
		String isbn = "";
		try {

			conn = jdbcTemplate.getDataSource().getConnection();
			pst = conn.prepareStatement(query);
			pst.setLong(1, assignmentId);
			rs = pst.executeQuery();
			if (rs.next()) {
				isbn = rs.getString(1);
			}
			return isbn;
		} catch (SQLException e) {

		}
        return isbn;
	}

	@Override
	public String getCourseTimeZoneUsingSectionId(long sectionId) {
		logger.debug("query={} :-sectionId={}", GET_COURSE_TIMEZONE_USING_SECTION_ID, sectionId);

		try {
			return jdbcTemplate.query(GET_COURSE_TIMEZONE_USING_SECTION_ID, new Object[] { sectionId }, new ResultSetExtractor<String>() {

				@Override
				public String extractData(ResultSet rst) throws SQLException, DataAccessException {
					String timeZome = DateUtil.DB_TIMEZONE_ID;
					if (rst.next()) {
						timeZome = rst.getString("TIME_ZONE");
					}
					return timeZome;
				}

			});
		} catch (Exception e) {
			throw e;
		}
	}

	private void setAssignmentContentPoliciesOrPolciesFromPolicyInstanceSet(final Long assignmentId,
																			final Long sectionId, final HMAssignmentCategoryPolicy hmAssignCatPolicy) {
		// Add assignment content policies
		final List<HomeworkManagerPolicy> assignmentContentPolicies = getAssignmentContentPolicies(assignmentId,
				sectionId);

		if (!GenUtil.isNull(assignmentContentPolicies)) {
			List<HomeworkManagerPolicy> currentPolicyList = hmAssignCatPolicy.getPoliciesList();
			currentPolicyList.addAll(assignmentContentPolicies);
			hmAssignCatPolicy.setPoliciesList(currentPolicyList);
		}

		// If no policies found, get policies from policy instance set for
		// assignment line item of WRITING / BLOG /
		// DISCUSSION assignment type.
		if (GenUtil.isNull(hmAssignCatPolicy.getPoliciesList())) {
			final List<HomeworkManagerPolicy> policiesFromPolicyInstanceSet = getAssignmentPoliciesFromPolicyInstanceSet(
					assignmentId, sectionId);
			if (!GenUtil.isNull(policiesFromPolicyInstanceSet)) {
				hmAssignCatPolicy.setPoliciesList(policiesFromPolicyInstanceSet);
			}
		}
	}

	public List<HomeworkManagerPolicy> getAssignmentPoliciesFromPolicyInstanceSet(Long assignmentId, Long sectionId)
			throws RuntimeException {
		final Map<Long, List<HomeworkManagerPolicy>> assignmentPoliciesMap = getPoliciesForAssignmentsFromPolicyInstanceSet(
                Collections.singletonList(assignmentId), sectionId);
		if (!CollectionUtils.isEmpty(assignmentPoliciesMap)) {
			return assignmentPoliciesMap.get(assignmentId);
		}
		return null;
	}

	private Map<Long, List<HomeworkManagerPolicy>> getPoliciesForAssignmentsFromPolicyInstanceSet(
			final List<Long> assignmentIds, final Long sectionId) throws RuntimeException {
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assignmentIds", assignmentIds);
		paramMap.put("sectionId", sectionId);
		try {
			return namedParameterJdbcTemplate.query(
					GET_POLICIES_FROM_POLICY_INSTANCE_SET_FOR_ASSIGNMENTS_AND_SECTION.toString(), paramMap,
					new ResultSetExtractor<Map<Long, List<HomeworkManagerPolicy>>>() {
						@Override
						public Map<Long, List<HomeworkManagerPolicy>> extractData(ResultSet rs) throws SQLException,
								DataAccessException {
							return extractAssignmentPolicyInstanceSetPoliciesFromResultSet(rs);
						}
					});
		} catch (Exception ex) {
			throw ex;
		}
	}

	private Map<Long, List<HomeworkManagerPolicy>> extractAssignmentPolicyInstanceSetPoliciesFromResultSet(
			final ResultSet rs) throws SQLException {
		final Map<Long, List<HomeworkManagerPolicy>> assignmentPoliciesMap = new HashMap<Long, List<HomeworkManagerPolicy>>();
		HomeworkManagerPolicy policy = null;
		while (rs.next()) {
			long assignmentId = rs.getLong("assignment_id");
			if (!assignmentPoliciesMap.containsKey(assignmentId)) {
				assignmentPoliciesMap.put(assignmentId, new ArrayList<HomeworkManagerPolicy>());
			}
			policy = new HomeworkManagerPolicy();
			policy.setId(rs.getLong("policy_id"));
			policy.setExchange_key(rs.getString("exchane_key"));
			policy.setName(rs.getString("policy_name"));
			policy.setValue(rs.getString("value"));
			policy.setContentDrivenPolicy("Y".equals(rs.getString("is_content_policy")));

			assignmentPoliciesMap.get(assignmentId).add(policy);
		}
		return assignmentPoliciesMap;
	}

	public List<HomeworkManagerPolicy> getAssignmentContentPolicies(Long assignmentId, Long sectionId)
			throws RuntimeException {
		final Map<Long, List<HomeworkManagerPolicy>> assignmentContentPoliciesMap = getContentPoliciesForAssignments(
                Collections.singletonList(assignmentId), sectionId);
		if (!CollectionUtils.isEmpty(assignmentContentPoliciesMap)) {
			return assignmentContentPoliciesMap.get(assignmentId);
		}
		return null;
	}

	private Map<Long, List<HomeworkManagerPolicy>> getContentPoliciesForAssignments(final List<Long> assignmentIds,
																					final Long sectionId) throws RuntimeException {
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assignmentIds", assignmentIds);
		paramMap.put("sectionId", sectionId);
		try {
			return namedParameterJdbcTemplate.query(
					GET_CONTENT_POLICIES_FOR_ASSIGNMENTS_AND_SECTION.toString(), paramMap,
					new ResultSetExtractor<Map<Long, List<HomeworkManagerPolicy>>>() {
						@Override
						public Map<Long, List<HomeworkManagerPolicy>> extractData(ResultSet rs) throws SQLException,
								DataAccessException {
							return extractAssignmentContentPoliciesFromResultSet(rs);
						}
					});
		} catch (Exception ex) {
			throw ex;
		}
	}

	private Map<Long, List<HomeworkManagerPolicy>> extractAssignmentContentPoliciesFromResultSet(final ResultSet rs)
			throws SQLException {
		final Map<Long, List<HomeworkManagerPolicy>> assignmentContentPoliciesMap = new HashMap<Long, List<HomeworkManagerPolicy>>();
		HomeworkManagerPolicy policy = null;
		while (rs.next()) {
			long assignmentId = rs.getLong("assignment_id");
			if (!assignmentContentPoliciesMap.containsKey(assignmentId)) {
				assignmentContentPoliciesMap.put(assignmentId, new ArrayList<HomeworkManagerPolicy>());
			}
			policy = new HomeworkManagerPolicy();
			policy.setId(rs.getLong("id"));
			policy.setName(rs.getString("name"));
			policy.setType(rs.getString("type"));
			policy.setExchange_key(rs.getString("exchane_key"));

			// Value can be null for content policy. If so, convert to empty
			// string
			String value = rs.getString("value");
			policy.setValue((value == null) ? "" : value);
			policy.setContentDrivenPolicy(true);

			assignmentContentPoliciesMap.get(assignmentId).add(policy);
		}
		return assignmentContentPoliciesMap;
	}

	private Map<String, HomeworkManagerCategoryPolicy> extractCategoryPolicyValuesFromResultSet(ResultSet rs)
			throws SQLException {
		final Map<String, HomeworkManagerCategoryPolicy> returnMap = new HashMap<String, HomeworkManagerCategoryPolicy>();

		Map<String, HomeworkManagerCategory> sysCatMap = new HashMap<String, HomeworkManagerCategory>();
		Map<String, HomeworkManagerCategory> courseCatMap = new HashMap<String, HomeworkManagerCategory>();
		while (rs.next()) {
			HomeworkManagerPolicy hmPolicy = this.getHomeworkManagerPolicyFromResultSet(rs);
			HomeworkManagerCategory category = this.getHomeworkManagerCategoryFromResultSet(rs);
			HomeworkManagerCategoryPolicy catPolVO = returnMap.get(category.getName());
			catPolVO = (catPolVO == null) ? new HomeworkManagerCategoryPolicy() : catPolVO;

			if (sysCatMap.get(category.getName()) == null
					&& CATEGORY_LEVEL.SYSTEM.getDescription().equalsIgnoreCase(category.getLevel())) {
				sysCatMap.put(category.getName(), category);
			}
			if (courseCatMap.get(category.getName()) == null
					&& CATEGORY_LEVEL.COURSE.getDescription().equalsIgnoreCase(category.getLevel())) {
				courseCatMap.put(category.getName(), category);
			}
			catPolVO.addPolicyToList(hmPolicy);

			this.addToleranceRelatedPolicies(catPolVO, hmPolicy);
			returnMap.put(category.getName(), catPolVO);
		}
		for (Map.Entry<String, HomeworkManagerCategoryPolicy> entry : returnMap.entrySet()) {
			String categoryName = entry.getKey();
			HomeworkManagerCategoryPolicy catPolVO = entry.getValue();
			if (courseCatMap.get(categoryName) != null && courseCatMap.get(categoryName).getCategoryID() > 0L) {
				catPolVO.setCategoryID(courseCatMap.get(categoryName).getCategoryID());
				catPolVO.setCourseid(courseCatMap.get(categoryName).getCourseid());
				catPolVO.setInstructorid(courseCatMap.get(categoryName).getInstructorid());
				catPolVO.setLevel(courseCatMap.get(categoryName).getLevel());
				catPolVO.setName(courseCatMap.get(categoryName).getName());

			} else if (sysCatMap.get(categoryName) != null && sysCatMap.get(categoryName).getCategoryID() > 0L) {
				catPolVO.setCategoryID(sysCatMap.get(categoryName).getCategoryID());
				catPolVO.setCourseid(sysCatMap.get(categoryName).getCourseid());
				catPolVO.setInstructorid(sysCatMap.get(categoryName).getInstructorid());
				catPolVO.setLevel(sysCatMap.get(categoryName).getLevel());
				catPolVO.setName(sysCatMap.get(categoryName).getName());
			}

			// Populate policy array from policy list as some of the old code
			// will refer array
			catPolVO.preparePolicyArrayFromList();
		}
		return returnMap;
	}

	private HomeworkManagerPolicy getHomeworkManagerPolicyFromResultSet(ResultSet rs) throws SQLException {
		HomeworkManagerPolicy hmPolicy = new HomeworkManagerPolicy();
		String exchangeKey = rs.getString("exchane_key");
		boolean isContentPolicy = "Y".equals(rs.getString("is_content_policy"));
		String policyValue = rs.getString("policy_value") == null ? "" : rs.getString("policy_value");
		Long policyId = rs.getLong("policy_id");
		hmPolicy.setContentDrivenPolicy(isContentPolicy);
		hmPolicy.setExchange_key(exchangeKey);
		hmPolicy.setId(policyId);
		hmPolicy.setValue(policyValue);
		return hmPolicy;
	}

	private HomeworkManagerCategory getHomeworkManagerCategoryFromResultSet(ResultSet rs) throws SQLException {
		String categoryLevel = rs.getString("category_level");
		String categoryName = rs.getString("name");
		Long courseId = rs.getLong("course_id");
		String instructorId = rs.getString("instructor_id");
		Long categoryId = rs.getLong("category_id");
		HomeworkManagerCategory category = new HomeworkManagerCategory();
		category.setCategoryID(categoryId);
		category.setName(categoryName);
		category.setCourseid(courseId);
		category.setInstructorid(instructorId);
		category.setLevel(categoryLevel);
		return category;
	}

	private void addToleranceRelatedPolicies(final HomeworkManagerCategoryPolicy assignmentCategoryPolicy,
											 final HomeworkManagerPolicy hmPolicy) {
		if (P_FBEZTOTOLERANCE.equals(hmPolicy.getExchange_key())) {
			logger.debug("{} value: {}", P_FBEZTOTOLERANCE, hmPolicy.getValue());
			final List<HomeworkManagerPolicy> tolerancePolicies = AnswerTolerancePolicyUtil
					.getAnswerTolerancePolicyList(hmPolicy.getValue());
			if (!GenUtil.isNull(tolerancePolicies)) {
				assignmentCategoryPolicy.getPoliciesList().addAll(tolerancePolicies);
			}
		}
	}


	@Override
	public OAuthConsumerDetails getConsumerDetail(String consumerKey) throws Exception {
		OAuthConsumerDetails consumerDetails = null;
		try {
			consumerDetails = jdbcTemplate.queryForObject(GET_CUSTOMER_DETAILS, new Object[] { consumerKey },
					new RowMapper<OAuthConsumerDetails>() {
						@Override
						public OAuthConsumerDetails mapRow(ResultSet rst, int rowNum) throws SQLException {

							OAuthConsumerDetails oAuthconsumerDetails = new OAuthConsumerDetails();

							oAuthconsumerDetails.setConsumerKey(rst
									.getString("consumer_key"));

							oAuthconsumerDetails.setSecret(rst
									.getString("consumer_secret"));

							oAuthconsumerDetails.setAlgorithm(rst.getString("algorithm"));

							oAuthconsumerDetails.setConsumerName(rst
									.getString("consumer_name"));

							oAuthconsumerDetails.setVersion(rst.getString("version"));

							oAuthconsumerDetails.setNonceReqd("Y".equals(rst
									.getString("nonce_reqd")) ? Boolean.TRUE
									: Boolean.FALSE);

							oAuthconsumerDetails.setIs2legged("Y".equals(rst
									.getString("is_2legged")) ? Boolean.TRUE
									: Boolean.FALSE);

							oAuthconsumerDetails.setOauthReqd("Y".equals(rst
									.getString("oauth_reqd")) ? Boolean.TRUE
									: Boolean.FALSE);

							oAuthconsumerDetails
									.setWindowSize(rst.getString("window_size") == null ? 10
											: Long.parseLong(rst
											.getString("window_size")));

							return oAuthconsumerDetails;

						}
					});
		} catch (EmptyResultDataAccessException e) {
			logger.info("NO RESULT FOUND FOR  " + consumerKey);
			return null;
		} catch (DataAccessException e) {
			logger.error("Exception occured while fetching consumer details for " + consumerKey, e);
			throw new Exception(e);
		}

		return consumerDetails;
	}

	@Override
	public void saveHMMessage(Map<String, Long> idMap, Set<Long> failedDesSectionsId, String copyType, String messageKey, Map<Long, Map<Long, String>> failedSecAndAssignmentMapWithExc, String exceptionDetailsForSectionFailed, Map<String, Boolean> booleanMap) throws Exception {

		// to do


	}
}
