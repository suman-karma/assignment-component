package io.mhe.assignmentcomponent.dao;

import io.mhe.assignmentcomponent.common.util.Utility;
import io.mhe.assignmentcomponent.vo.*;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AssignmentCopyDAO implements IAssignmentCopyDAO{
    private final Logger logger = LoggerFactory.getLogger(AssignmentCopyDAO.class);
    @Autowired(required=true)
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private INonForceGradeAssignmentsDAO iNonForceGradeAssignmentsDAO;

    private Connection connection = null;
    static final String SEQUENCE_NAME                   = "GBS_SEQUENCE";
    private static final String SOFT_DELETE_MULTIPLE_ASSIGNMENT_SQL = "update assignment set is_deleted='true', updated_date = sysdate where assignment_id in (:assignmentIds)";

    private static final String GET_ACTIVITY_FOR_ASSIGNMENT_ID = "SELECT *"
            + " FROM ACTIVITY WHERE ASSIGNMENT_ID =:aID"
            + " ORDER BY SEQUENCE_NO";

    private static final String UPDATE_ACTIVITY_WITH_ALA = "update activity set alamgr_id =:alaManagerID, title =:title, weight =:weight , updated_date = sysdate, num_questions =:questions, available_questions =:availableQuestions "
            +
            ", native_ala_id =:NativeAlaId, ala_content_provider =:alaContentProvider  where assignment_id =:assignmentID";

    private static final String UPDATE_ASSIGNMENT_UPDATED_DATE = "UPDATE ASSIGNMENT SET updated_date= SYSDATE WHERE assignment_id =:assignmentId ";

    private static final String ADD_NEW_ACTIVITY_FOR_CLOB = "INSERT INTO ACTIVITY ( ACTIVITY_ID, ASSIGNMENT_ID,"
            +
            " TITLE, WEIGHT, SEQUENCE_NO, WEIGHT_YN, BEGIN_NOTE, END_NOTE, ALAMGR_ID, REPEATABLE_YN,"
            +
            " UPDATED_DATE, ACTIVITY_TYPE, TIMABLE_YN, TIME_ALLOCATED, REPEATABLE_TYPE, REPEATABLE_VALUE, PRINTABLE_YN, NUM_QUESTIONS, AVAILABLE_QUESTIONS "
            +
            " , NATIVE_ALA_ID, ALA_CONTENT_PROVIDER) VALUES( :ACTIVITY_ID, :ASSIGNMENT_ID,:TITLE, :WEIGHT, :SEQUENCE_NO, :WEIGHT_YN, :BEGIN_NOTE, :END_NOTE, "
            +
            ":ALAMGR_ID, :REPEATABLE_YN,:UPDATED_DATE,:ACTIVITY_TYPE,:TIMABLE_YN, :TIME_ALLOCATED, :REPEATABLE_TYPE, :REPEATABLE_VALUE, :PRINTABLE_YN,"
            +
            " :NUM_QUESTIONS, :AVAILABLE_QUESTIONS,:NATIVE_ALA_ID, :ALA_CONTENT_PROVIDER )";

    private static final String GET_ACTIVITY_ITEMS_FOR_ASSIGNMENT = "select ait.* from activity_item ait where ait.activity_id in (select activity_id from activity ";

    public static final String GET_MAX_SEQUENCE_NO = "SELECT MAX( SEQUENCE_NO ) AS SEQUENCE_NO FROM ACTIVITY WHERE ASSIGNMENT_ID = :assignmentID";

    private static final String ADD_NEW_AI = "INSERT INTO ACTIVITY_ITEM ( ACTIVITY_ITEM_ID, ACTIVITY_ID, ALAMGR_ID,"
            +
            " SEQUENCE_NO, WEIGHT, NATIVE_ALA_ID, TITLE, RENDERING_URL )"
            +
            " VALUES( ?, ?, ?, ?, ?, ?, ?, ? )";

    private static final String INSERT_ACTIVITY_ITEM_SKILL_CATEGORY_XREF = "INSERT INTO ACTIVITY_ITEM_SKILL_CAT_XREF(ACTIVITY_ITEM_ID, SKILL_CATEGORY_ID) "
            +
            " VALUES(?,?)";

    private static final String UPDATE_POINTS_QUESTIONS_FOR_ASSIGNMENT = "update activity set weight =:points, num_questions =:questions, available_questions =:availableQuestions where assignment_id =:assignmentId";

    private static final String INSERT_ASSIGNMENT_SYNC_STATUS = "INSERT INTO ASSIGNMENT_SYNC_STATUS(ASSIGNMENT_ID, NATIVE_ALA_ID, SYNC_STATUS, CREATED_DATE, UPDATED_DATE) "
            + " VALUES(:assignmentId,:nativeAlaId,:status, SYSDATE, SYSDATE)";

    private static final String INSERT_PARENT_ASSIGNMENT_STATUS = "INSERT INTO ASSIGNMENT_PARENT_STATUS (ID,ASSIGNMENT_ID, PARENT_ASSIGNMENT_ID, PARENT_ASSIGNMENT_STATUS, CREATED_DATE) "
            + " VALUES(:ID,:ASSIGNMENT_ID,:PARENT_ASSIGNMENT_ID,:PARENT_ASSIGNMENT_STATUS, SYSDATE)";

    private static final String GET_GROUPASSIGNMENT_BY_ASSIGNMENTID = "select a.*, sax.bb_is_deployed, sax.bb_deploy_on,sax.lms_deploy_on,sax.lms_deployed from assignment a, section_assignment_xref sax where "
            + "a.assignment_id = sax.assignment_id and a.assignment_id =:assignmentId and sax.section_id =:sectionId";

    private static final String GET_ACTIVITY_ITEM_FOR_ACTIVITY_ID = "SELECT WEIGHT, ac.ACTIVITY_ITEM_ID, ALAMGR_ID, SEQUENCE_NO, UPDATED_DATE, NATIVE_ALA_ID, ac.TITLE ,ACEXTND.SCORING"
            +
            " FROM ACTIVITY_ITEM AC LEFT OUTER JOIN  ACTIVITY_ITEM_EXTENDED_INFO ACEXTND ON ACEXTND.ACTIVITY_ITEM_ID = AC.ACTIVITY_ITEM_ID WHERE ac.ACTIVITY_ID= :ACTIVITY_ID ORDER BY ac.SEQUENCE_NO";

// this method getAssignmentName is for testing will remove.
    public String getAssignmentName(long id){
      String assignmentName = "";
      Map<String,String> paramMap = new HashMap<String,String>();
      paramMap.put("assignmentId",String.valueOf(id));
      String sql = "select title from assignment where assignment_id = :assignmentId";
      assignmentName = namedParameterJdbcTemplate.queryForObject(sql,paramMap,String.class);

      return assignmentName;
  }

    @Override
    public boolean copyHMPublicAssignments(CopyAssignmentTO[] srcAssignments, long srcSectionId, long dstSectionId, long[] oldCategoryIds, long[] newCategoryIds, long newCourseId, long originalCourseId) throws Exception {

        connection = jdbcTemplate.getDataSource().getConnection().unwrap(OracleConnection.class);;
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("hm_copy_public_assignments")
                .declareParameters(new SqlParameter[]{
                        new SqlParameter("srcAssignmentIds", Types.ARRAY),
                        new SqlParameter("sectionId", Types.NUMERIC),
                        new SqlParameter("newSectionId", Types.NUMERIC),
                        new SqlParameter("oldCategoryId", Types.ARRAY),
                        new SqlParameter("newCategoryId", Types.ARRAY),
                        // out param
                        new SqlOutParameter("newAssignmentIds", Types.VARCHAR),
                        new SqlParameter("primaryInstructorId", Types.VARCHAR),
                        new SqlParameter("newNativeAlaIds", Types.ARRAY),
                        new SqlParameter("newCourseId", Types.NUMERIC),
                        new SqlParameter("originalCourseId", Types.NUMERIC),
                        new SqlParameter("isProctringCopyEnabled", Types.VARCHAR),
                })

                .returningResultSet("", new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return null;
                    }
                });
        HashMap map = new HashMap();
        long[] srcAssignmentIds = new long[srcAssignments.length];
        String[] newNativeIdsArray = new String[srcAssignments.length];
        for (int i = 0; i < srcAssignments.length; i++) {
            map.put("" + srcAssignments[i].getAssignmentId(), srcAssignments[i]);
            srcAssignmentIds[i] = srcAssignments[i].getAssignmentId();
            newNativeIdsArray[i] = srcAssignments[i].getNewNativeAlaId();
        }

        ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("NUM_ARRAY", connection);
        ArrayDescriptor stringDescriptor = ArrayDescriptor.createDescriptor("STRING_ARRAY", connection);

        ARRAY oldCatArray = new ARRAY(descriptor, connection, oldCategoryIds);
        ARRAY newCatArray = new ARRAY(descriptor, connection, newCategoryIds);

        ARRAY newNativeidArray = new ARRAY(stringDescriptor, connection, newNativeIdsArray);
        ARRAY srcAssignmentArray = new ARRAY(descriptor, connection, srcAssignmentIds);

        //
        logger.info("*********************************************");
        logger.info("srcAssignmentIds {}",srcAssignmentArray);
        logger.info("sectionId {}",srcSectionId);
        logger.info("newSectionId {}",dstSectionId);
        logger.info("oldCategoryId {}",oldCatArray);
        logger.info("newCategoryId {}",newCatArray);
        logger.info("primaryInstructorId {}",srcAssignments[0].getNewPrimaryInstructorId());
        logger.info("newNativeAlaIds {}",newNativeidArray);
        logger.info("newCourseId {}",newCourseId);
        logger.info("originalCourseId {}",originalCourseId);
        logger.info("*********************************************");
        //

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("srcAssignmentIds",srcAssignmentArray);
        mapSqlParameterSource.addValue("sectionId",srcSectionId);
        mapSqlParameterSource.addValue("newSectionId",dstSectionId);
        mapSqlParameterSource.addValue("oldCategoryId",oldCatArray);
        mapSqlParameterSource.addValue("newCategoryId",newCatArray);
        mapSqlParameterSource.addValue("primaryInstructorId",srcAssignments[0].getNewPrimaryInstructorId());
        mapSqlParameterSource.addValue("newNativeAlaIds",newNativeidArray);
        mapSqlParameterSource.addValue("newCourseId",newCourseId);
        mapSqlParameterSource.addValue("originalCourseId",originalCourseId);
        mapSqlParameterSource.addValue("isProctringCopyEnabled","Y"); // to do


        Map<String, Object> result = simpleJdbcCall.execute(mapSqlParameterSource);
        // get from result.
        String newAssignmentIdStr = (String) result.get("newAssignmentIds");

        logger.info("&&&&&&&&&&&&&&&&&&             newAssignmentIdStr {} -- {}", newAssignmentIdStr , newAssignmentIdStr.substring(1));
        srcAssignments[0].setNewAssignmentId(Long.parseLong(newAssignmentIdStr.substring(1)));

        // to do assignments with line item to copy

        String[] newAssignmentIds = Utility.getArrayFromString(newAssignmentIdStr.substring(1), ",");

        ArrayList<CopyAssignmentTO> assignmentsWithLineItems = new ArrayList<CopyAssignmentTO>();
        for (int i = 0; i < newAssignmentIds.length; i++) {
            CopyAssignmentTO copy = (CopyAssignmentTO) map.get("" + srcAssignmentIds[i]);
            copy.setNewAssignmentId(Long.parseLong(newAssignmentIds[i]));
            if (copy.getType().equals("WRITING") ||
                    copy.getType().equals("BLOG") ||
                    copy.getType().equals("DISCUSSION")) {

                assignmentsWithLineItems.add(copy);
            }
        }

        this.copyAssignmentLineItemsForMultipleAssignments(
                assignmentsWithLineItems.toArray(new CopyAssignmentTO[0]));

        Map<Long, List<Long>> mapSectionAssignment = new HashMap<Long, List<Long>>();

        List<Long> assignmentlist = Arrays.stream(newAssignmentIds)
                .filter(assignStr -> StringUtils.isNumeric(assignStr))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        mapSectionAssignment.put(dstSectionId,assignmentlist);
        try {
            iNonForceGradeAssignmentsDAO.insertOrUpdateDate(mapSectionAssignment);
            logger.debug("[ASSIGNMENT_DATE_UPDATE] Successfully added/updated data from AssignmentListsDaoJdbc.copyHMPublicAssignments with mapSectionAssignment:{} ", new Object[] {mapSectionAssignment});
        }catch (Exception ex) {
            logger.error("[ASSIGNMENT_DATE_UPDATE] Exception occurs in AssignmentListsDaoJdbc.copyHMPublicAssignments with exception:{} ", new Object[] {ex});
        }



      return true;
    }



    @Override
    public void copySectionAssignmentXref(Map<Long, Long> sectionIdsMap, Map<Long, Long> assignmentsMap) {

        try {
            Iterator<Map.Entry<Long, Long>> it = sectionIdsMap.entrySet().iterator();
            long[] srcSectionIds = new long[sectionIdsMap.size()];
            long[] destSectionIds = new long[sectionIdsMap.size()];
            int i = 0;
            while (it.hasNext()) {
                Map.Entry<Long, Long> e = it.next();
                srcSectionIds[i] = e.getKey();
                destSectionIds[i] = e.getValue();
                i++;
            }

            it = assignmentsMap.entrySet().iterator();
            long[] srcAssignmentIds = new long[assignmentsMap.size()];
            long[] dstAssignmentIds = new long[assignmentsMap.size()];
            i = 0;
            while (it.hasNext()) {
                Map.Entry<Long, Long> e = (Map.Entry) it.next();
                srcAssignmentIds[i] = e.getKey();
                dstAssignmentIds[i] = e.getValue();
                i++;
            }

            connection = jdbcTemplate.getDataSource().getConnection().unwrap(OracleConnection.class);

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                //.withSchemaName("")
                .withProcedureName("copy_section_assignment_xref")
                .declareParameters(new SqlParameter[]{
                        new SqlParameter("srcSectionIds", Types.ARRAY),
                        new SqlParameter("dstSectionIds", Types.ARRAY),
                        new SqlParameter("srcAssignmentIds", Types.ARRAY),
                        new SqlParameter("dstAssignmentIds", Types.ARRAY)
                });

            ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("NUM_ARRAY", connection);
            ARRAY srcSectionIdsArray = new ARRAY(descriptor, connection, srcSectionIds);
            ARRAY destSectionIdsArray = new ARRAY(descriptor, connection, destSectionIds);
            ARRAY srcAssignmentIdsArray = new ARRAY(descriptor, connection, srcAssignmentIds);
            ARRAY dstAssignmentIdsArray = new ARRAY(descriptor, connection, dstAssignmentIds);

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("srcSectionIds",srcSectionIdsArray);
            mapSqlParameterSource.addValue("dstSectionIds",destSectionIdsArray);
            mapSqlParameterSource.addValue("srcAssignmentIds",srcAssignmentIdsArray);
            mapSqlParameterSource.addValue("dstAssignmentIds",dstAssignmentIdsArray);

            Map<String, Object> result = simpleJdbcCall.execute(mapSqlParameterSource);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteMultipleAssignments(List<Long> assignmentIds) {
        if (assignmentIds == null || assignmentIds.isEmpty()) {
            //logger.error("deleteMultipleAssignments() - assignmentIds list is either empty or null");
            return false;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assignmentIds", assignmentIds);
        int rowsUpdated = namedParameterJdbcTemplate.update(SOFT_DELETE_MULTIPLE_ASSIGNMENT_SQL, paramMap);
        //logger.debug("Number or assignments updated to is_deleted are - " + rowsUpdated);
        return true;
    }


    @Override
    public Activity[] getActivitiesForAssignment(long aID) {
        List<Activity> actSet = jdbcTemplate.query(
                GET_ACTIVITY_FOR_ASSIGNMENT_ID, new Object[] { aID }, new RowMapper<Activity>() {
                    @Override
                    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Activity act = new Activity();
                        act.setWeight(rs.getFloat("WEIGHT"));
                        act.setID(rs.getLong("ACTIVITY_ID"));
                        act.setTitle(rs.getString("TITLE"));
                        act.setSequenceNo(rs.getInt("SEQUENCE_NO"));
                        act.setBeginNote(rs.getString("BEGIN_NOTE") != null ? rs.getString("BEGIN_NOTE")
                                : "");
                        act.setEndNote(rs.getString("END_NOTE") != null ? rs.getString("END_NOTE") : "");
                        act.setAlaManagerID(rs.getLong("ALAMGR_ID"));
                        act.setMaxScore(rs.getFloat("MAX_SCORE"));
                        act.setWeightBased((rs.getString("WEIGHT_YN") != null && rs
                                .getString("WEIGHT_YN").equalsIgnoreCase("y")) ? true : false);
                        act.setQuestions(rs.getInt("NUM_QUESTIONS"));
                        act.setAvailableQuestions(rs.getInt("AVAILABLE_QUESTIONS"));
                        String type = rs.getString("ACTIVITY_TYPE");
                        if (type != null) {
                            act.setType(type);
                        }
                        act.setRepeatable(rs.getString("REPEATABLE_YN").equalsIgnoreCase("Y") ? true
                                : false);
                        act.setAssignmentID(Long.parseLong(rs.getString("ASSIGNMENT_ID")));

                        // Fix for bug # 3129.
                        // Needed to make sure the repeatableType and repeatableValue
                        // are set in the activity.
                        act.setRepeatableType(rs.getString("repeatable_type"));
                        act.setRepeatableValue(rs.getLong("repeatable_value"));
                        // Copying the Time Allocated
                        act.settimable(rs.getString("TIMABLE_YN").equalsIgnoreCase("Y") ? true : false);
                        if (act.istimable()) {
                            act.setTimeAllocatedInSeconds(rs.getInt("TIME_ALLOCATED"));
                        }
                        act.setPrintable(((rs.getString("PRINTABLE_YN")).equalsIgnoreCase("Y") ? true
                                : false));

                        // Adding native id information.
                        act.setNativeAlaId(rs.getString("NATIVE_ALA_ID"));
                        act.setAlaContentProvider(rs.getString("ALA_CONTENT_PROVIDER"));
                        logger.info("#################### {}", act);
                        return act;
                    }
                });
        return (Activity[]) actSet.toArray(new Activity[0]);
    }

    @Override
    public ActivityItem[] getActivityItemsForActivity(long activityID) {

        try {
            // logging the information
            logger.debug("in getActivityItemsForActivity" + activityID);
            // setting the parameters as required by spring named jdbc
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ACTIVITY_ID", activityID);

            if (logger.isDebugEnabled()) {
                logger.debug("Executing the query" + GET_ACTIVITY_ITEM_FOR_ACTIVITY_ID);
            }
            // this query will get the activity items and return the activity item list
            List<ActivityItem> listActivityItems = namedParameterJdbcTemplate.query(GET_ACTIVITY_ITEM_FOR_ACTIVITY_ID, params,
                    new RowMapper<ActivityItem>() {

                        @Override
                        public ActivityItem mapRow(ResultSet rst, int rowNum)
                                throws SQLException {

                            ActivityItem activityItem = new ActivityItem();
                            activityItem.setTitle(rst.getString("TITLE"));
                            activityItem.setWeight(rst.getFloat("WEIGHT"));
                            activityItem.setID(rst.getLong("ACTIVITY_ITEM_ID"));
                            activityItem.setAlaManagerID(rst.getLong("ALAMGR_ID"));
                            activityItem.setSequenceNo(rst.getInt("SEQUENCE_NO"));
                            activityItem.setUpdatedDate(rst.getDate("UPDATED_DATE"));

                            // BEgin Set Native ID and ALA Content Provider
                            logger.debug("Native ala id " + rst.getString("NATIVE_ALA_ID"));
                            activityItem.setNativeAlaId(rst.getString("NATIVE_ALA_ID"));
                            if (rst.getString("scoring") != null && rst.getString("scoring").equalsIgnoreCase("manual"))
                            {
                                activityItem.setManualGradingRequired(true);
                            }
                            else
                            {
                                activityItem.setManualGradingRequired(false);
                            }

                            return activityItem;
                        }
                    });
            return listActivityItems.toArray(new ActivityItem[0]);
        } catch (Exception e) {
            return  null;

        }

    }



    @Override
    public boolean addActivityItemsToActivity(ActivityItem[] items, long activityID) {

        long beginAddingActivityItems = System.currentTimeMillis();
        try {
            for (ActivityItem item : items) {
                item.setID(generateIDUsingSequence(SEQUENCE_NAME));
            }
            jdbcTemplate.batchUpdate(ADD_NEW_AI, new BatchPreparedStatementSetter() {
                public int getBatchSize() {
                    return items.length;
                }

                public void setValues(PreparedStatement pStmt, int i) throws SQLException {
                    ActivityItem item = items[i];
                    pStmt.setLong(1, item.getID());
                    pStmt.setLong(2, activityID);
                    pStmt.setLong(3, item.getAlaManagerID());
                    pStmt.setLong(4, item.getSequenceNo());
                    pStmt.setFloat(5, item.getWeight());
                    pStmt.setString(6, item.getNativeAlaId());
                    pStmt.setString(7, item.getTitle());
                    pStmt.setString(8, item.getRenderingUrl());
                }
            });
        } catch (Exception e) {

        }

        try {
            int[] argTypes = new int[] { Types.NUMERIC, Types.NUMERIC };
            List<Object[]> batchArgs = new ArrayList<Object[]>();
            for (ActivityItem item : items) {
                if (null != item.getSkillCategoryIds()) {
                    for (int i = 0; i < item.getSkillCategoryIds().length; i++) {
                        Object[] args = new Object[] { item.getID(), item.getSkillCategoryIds()[i] };
                        batchArgs.add(args);
                    }
                }
            }
            jdbcTemplate.batchUpdate(INSERT_ACTIVITY_ITEM_SKILL_CATEGORY_XREF, batchArgs, argTypes);
        } catch (Exception e) {

        }


        return true;
    }

    @Override
    public long updateActivity(Activity act, long assignmentId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("alaManagerID", act.getAlaManagerID());
        paramMap.put("title", act.getTitle());
        paramMap.put("weight", act.getWeight());
        paramMap.put("questions", act.getQuestions());
        paramMap.put("availableQuestions", act.getAvailableQuestions());
        paramMap.put("NativeAlaId", act.getNativeAlaId());
        paramMap.put("alaContentProvider", act.getAlaContentProvider());
        paramMap.put("assignmentID", assignmentId);
        namedParameterJdbcTemplate.update(UPDATE_ACTIVITY_WITH_ALA, paramMap);
        return act.getID();
    }

    @Override
    public void updateAssignmentUpdatedDate(long assignmentId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assignmentId", assignmentId);
        namedParameterJdbcTemplate.update(UPDATE_ASSIGNMENT_UPDATED_DATE, paramMap);
    }

    @Override
    public long addActivityToAssignment(Activity act, long assignmentID) {
        logger.info(" adding activities...addActivityToAssignment {}", assignmentID);
        try {
            act.setID(generateIDUsingSequence(SEQUENCE_NAME));
            long seq = 0;
            if (act.getSequenceNo() != 0) {
                seq = act.getSequenceNo();
            } else {
                seq = getNextSequenceNoForActivity(assignmentID);
            }

            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("ACTIVITY_ID", act.getID());
            if (assignmentID == -1) {
                parameterMap.put("ASSIGNMENT_ID", null);
            } else {
                parameterMap.put("ASSIGNMENT_ID", assignmentID);
            }
            parameterMap.put("TITLE", sanitize(act.getTitle()));
            parameterMap.put("WEIGHT", act.getWeight());
            parameterMap.put("SEQUENCE_NO", seq);
            parameterMap.put("WEIGHT_YN", act.isWeightBased() ? "Y" : "N");
            parameterMap.put("BEGIN_NOTE", sanitize(act.getBeginNote()));
            parameterMap.put("END_NOTE", sanitize(act.getEndNote()));
            parameterMap.put("ALAMGR_ID", act.getAlaManagerID());
            parameterMap.put("REPEATABLE_YN", act.isRepeatable() ? "Y" : "N");
            parameterMap.put("UPDATED_DATE", new Date(System.currentTimeMillis()));
            parameterMap.put("ACTIVITY_TYPE", act.getType());
            parameterMap.put("TIMABLE_YN", act.istimable() ? "Y" : "N");

            if (act.istimable()) {
                parameterMap.put("TIME_ALLOCATED", act.getTimeAllocatedInSeconds());
            } else {
                parameterMap.put("TIME_ALLOCATED", null);
            }

            if (act.isRepeatable()) {
                parameterMap.put("REPEATABLE_TYPE", act.getRepeatableType());
                parameterMap.put("REPEATABLE_VALUE", act.getRepeatableValue());
            } else {
                parameterMap.put("REPEATABLE_TYPE", null);
                parameterMap.put("REPEATABLE_VALUE", null);
            }

            parameterMap.put("PRINTABLE_YN", act.isPrintable() ? "Y" : "N");
            parameterMap.put("NUM_QUESTIONS", act.getQuestions());
            parameterMap.put("AVAILABLE_QUESTIONS", act.getAvailableQuestions());
            parameterMap.put("NATIVE_ALA_ID", act.getNativeAlaId());
            parameterMap.put("ALA_CONTENT_PROVIDER", act.getAlaContentProvider());

            namedParameterJdbcTemplate.update(ADD_NEW_ACTIVITY_FOR_CLOB, parameterMap);

            return act.getID();
        } catch (Exception de) {
            de.printStackTrace();
        }
        logger.info(" adding activities...addActivityToAssignment {} activity {}", assignmentID , act.getID());
        return act.getID();
    }

    @Override
    public ActivityItem[] getActivityItemsByAssignmentId(long assignmentId) {
        List<ActivityItem> activityItems = null;
        try {
            activityItems = jdbcTemplate.query(GET_ACTIVITY_ITEMS_FOR_ASSIGNMENT, new Object[] { assignmentId },
                    new RowMapper<ActivityItem>() {

                        @Override
                        public ActivityItem mapRow(ResultSet rst, int rowIndex) throws SQLException {
                            ActivityItem activityItem = new ActivityItem();
                            activityItem.setID(rst.getLong("ACTIVITY_ITEM_ID"));
                            activityItem.setActivityId(rst.getLong("ACTIVITY_ID"));
                            activityItem.setAlaManagerID(rst.getLong("ALAMGR_ID"));
                            activityItem.setWeight(rst.getFloat("WEIGHT"));
                            activityItem.setSequenceNo(rst.getInt("SEQUENCE_NO"));
                            activityItem.setCreatedDate(rst.getDate("CREATED_DATE"));
                            activityItem.setUpdatedDate(rst.getDate("UPDATED_DATE"));
                            activityItem.setNativeAlaId(rst.getString("NATIVE_ALA_ID"));
                            activityItem.setTitle(rst.getString("TITLE"));
                            activityItem.setRenderingUrl(rst.getString("RENDERING_URL"));
                            return activityItem;
                        }
                    });
        } catch (Exception e) {

        }
        if (null == activityItems) {
            activityItems = new ArrayList<ActivityItem>();
        }
        return (ActivityItem[]) activityItems.toArray(new ActivityItem[0]);
    }

    @Override
    public void deleteActivityItemsByActivityId(long activityId) {
        try {
            String deleteActivityItemExtendedInfo = "DELETE FROM ACTIVITY_ITEM_EXTENDED_INFO WHERE ACTIVITY_ITEM_ID IN (SELECT ACTIVITY_ITEM_ID FROM ACTIVITY_ITEM WHERE ACTIVITY_ID = "
                    + activityId + " )";
            String deleteAiSkillCatXrefByActivityId = "DELETE FROM ACTIVITY_ITEM_SKILL_CAT_XREF WHERE ACTIVITY_ITEM_ID IN ( SELECT ACTIVITY_ITEM_ID FROM ACTIVITY_ITEM WHERE ACTIVITY_ID = "
                    + activityId + " )";
            String deleteSecActivityItemScoreByActivityId = "DELETE FROM SECTION_ACTIVITY_ITEM_SCORE WHERE ACTIVITY_ITEM_ID IN ( SELECT ACTIVITY_ITEM_ID FROM ACTIVITY_ITEM WHERE ACTIVITY_ID = "
                    + activityId + " )";
            String deleteActCorrectionNotice = "DELETE FROM ACTIVITY_CORRECTION_NOTICE WHERE ACTIVITY_ID = " + activityId;
            String deleteAssociatedItems = "DELETE FROM ACTIVITY_ITEM WHERE ACTIVITY_ID = " + activityId;

            String[] deleteItemQueries = { deleteActivityItemExtendedInfo, deleteAiSkillCatXrefByActivityId, deleteSecActivityItemScoreByActivityId,
                    deleteActCorrectionNotice, deleteAssociatedItems };
            int[] executedStmntCount = jdbcTemplate.batchUpdate(deleteItemQueries);

        } catch (DataAccessException e) {
        } catch (Exception e) {
        }
    }

    @Override
    public void updatePointsAndQuestionsForAssignment(long assignmentId, float points, int numQuestions, int availableQuestions) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("points", points);
        paramMap.put("questions", numQuestions);
        paramMap.put("availableQuestions", availableQuestions);
        paramMap.put("assignmentId", assignmentId);
        namedParameterJdbcTemplate.update(UPDATE_POINTS_QUESTIONS_FOR_ASSIGNMENT, paramMap);
    }

    @Override
    public void insertSyncStatusForAssignment(long assignmentId, String nativeAlaId, String status) {
        logger.debug("###### AssignmentDatatStore : Insert sync status : assignmentId = " + assignmentId
                + " nativeAlaId = " + nativeAlaId + " status = " + status);
        Map<String, Object> syncStatusForAssignment = new HashMap<String, Object>();
        syncStatusForAssignment.put("assignmentId", assignmentId);
        syncStatusForAssignment.put("nativeAlaId", nativeAlaId);
        syncStatusForAssignment.put("status", status);
        namedParameterJdbcTemplate.update(INSERT_ASSIGNMENT_SYNC_STATUS, syncStatusForAssignment);
    }

    @Override
    public void insertParentAssignmentStatusForAssignment(long assignmentId, long parentAssignmentId, String status) {
        logger.debug(
                "AssignmentsDaoJdbc : insertParentAssignmentStatusForAssignment: Input param assignmentId {}, parent_assignment_id{} and status {}",
                new Object[] { assignmentId, parentAssignmentId, status });

        Map<String, Object> parentStatusForAssignment = new HashMap<String, Object>();
        try {
            parentStatusForAssignment.put("ID", generateIDUsingSequence("ASSIGN_PARENT_STATUS_SEQ"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        parentStatusForAssignment.put("ASSIGNMENT_ID", assignmentId);
        parentStatusForAssignment.put("PARENT_ASSIGNMENT_ID", parentAssignmentId);
        parentStatusForAssignment.put("PARENT_ASSIGNMENT_STATUS", status);
        namedParameterJdbcTemplate.update(INSERT_PARENT_ASSIGNMENT_STATUS, parentStatusForAssignment);
    }

    /*
    @Override
    public GroupAssignment getGroupAssignmentById(long assignmentId, long sectionId) {
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("assignmentId", assignmentId);
            paramMap.put("sectionId", sectionId);
            GroupAssignment ga = jdbcTemplate.queryForObject(
                    GET_GROUPASSIGNMENT_BY_ASSIGNMENTID, new Object[] { assignmentId, sectionId }, new RowMapper<GroupAssignment>() {
                        @Override
                        public GroupAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
                            GroupAssignment ga = new GroupAssignment();
                            getAssignmentWithoutExtensionInfo(rs, ga);
                            return ga;
                        }
                    });
            return ga;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int[] copyGroupAssignmentPropertiesForCopyAssignment(CopyAssignment[] ca) {
        final List<CopyAssignment> la = Arrays.stream(ca).toList();
        return this.jdbcTemplate.batchUpdate(
                "insert into section_group_assignment_xref (select ?, ?, 'N', students_per_group, sysdate, sysdate from "
                        + "section_group_assignment_xref where section_id = ? and assignment_id = ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        CopyAssignment copy = la.get(i);
                        ps.setLong(1, copy.getNewSectionId());
                        ps.setLong(2, copy.getNewAssignmentId());
                        ps.setLong(3, copy.getSectionId());
                        ps.setLong(4, copy.getAssignmentId());
                    }
                    public int getBatchSize() {
                        return la.size();
                    }
                });
    }




    private Assignment getAssignmentWithoutExtensionInfo(ResultSet rst, Assignment assignment) throws SQLException {
        if (assignment != null) {
            assignment.setID(rst.getLong("ASSIGNMENT_ID"));
            assignment.setTitle(rst.getString("TITLE"));

            /** DestinationId for assignment *
            assignment.setDestinationId(rst.getLong("DESTINATION_ID"));

            /** Timezone for US/Eastern *
            assignment.setStartDate(getTimestamp(rst, "START_DATE"));
            logger.debug("Assignment start date" + getTimestamp(rst, "START_DATE"));
            assignment.setDueDate(getTimestamp(rst, "DUE_DATE"));
            assignment.setWeight(rst.getFloat("WEIGHT"));
            logger.debug("AssignmentsDaoJdbc_getAssignmentWithoutExtensionInfo_IS_CHAT_ASSIGNMENT from DB:" + rst.getString("IS_CHAT_ASSIGNMENT"));
            assignment.setChatAssignment(("Y".equals(rst.getString("IS_CHAT_ASSIGNMENT"))) ? true
                    : false);
            logger.debug("AssignmentsDaoJdbc_getAssignmentWithoutExtensionInfo_IS_CHAT_ASSIGNMENT:" + assignment.isChatAssignment());

            if (rst.getString("ACCESS_LEVEL") != null) {
                assignment.setAccess_level(rst.getString("ACCESS_LEVEL"));
            }
            if (rst.getString("ISPOLICYOVERRIDDEN") != null) {
                assignment.setPolicyOverridden(rst.getString("ISPOLICYOVERRIDDEN"));
            }
            if (rst.getString("PRIMARY_INSTRUCTOR_ID") != null) {
                assignment.setPrimary_instructor_id(rst.getString("PRIMARY_INSTRUCTOR_ID"));
            }
            if (rst.getString("STATUS") != null) {
                assignment.setStatus(rst.getString("STATUS"));
            }
            if (rst.getString("CATEGORY_ID") != null) {
                assignment.setCategory_id(rst.getLong("CATEGORY_ID"));
            }

            if (rst.getString("SHOW_HIDE") != null && "N".equals(rst.getString("SHOW_HIDE"))) {
                assignment.setShowAssignment(false);
            } else {
                assignment.setShowAssignment(true);
            }
            assignment.setProducerId(NumberUtilities.parseLong(rst.getString("PRODUCER_ID"), 0L));
            assignment.setConsumerId(NumberUtilities.parseLong(rst.getString("CONSUMER_ID"), 0L));
            assignment.setNote(rst.getString("ASSIGNMENT_NOTE"));
            try {
                if (rst.getString("ASSIGNMENT_TYPE") != null) {
                    String assignmentType = rst.getString("ASSIGNMENT_TYPE");
                    if(AssignmentType.contains(assignmentType)){
                        assignment.setType(AssignmentType.newInstance(assignmentType));
                    }else{
                        throw new Exception("Invalid Assignment Type "+rst.getString("ASSIGNMENT_TYPE"));
                    }
                }

            } catch (Exception e) {

            }
            assignment.setAssignmentType(rst.getString("ASSIGNMENT_TYPE"));
            assignment.setSequenceNo(rst.getLong("SEQUENCE_NO"));
            assignment.setUpdatedDate(getTimestamp(rst, "UPDATED_DATE"));
            logger.debug("Date from resultset " + rst.getTimestamp("UPDATED_DATE"));
            logger.debug("UpdatedDate for " + assignment.getTitle() + " DataBase in Datastore "
                    + getTimestamp(rst, "UPDATED_DATE"));
            assignment.setCategory(rst.getString("CATEGORY_TYPE"));
            assignment.setLibraryAssignment((rst.getString("IS_LIBRARY_ASSIGNMENT") == null || (rst
                    .getString("IS_LIBRARY_ASSIGNMENT") != null && rst.getString(
                    "IS_LIBRARY_ASSIGNMENT").equals("N"))) ? false : true);
            // Set manual grade required for assignment.
            assignment.setManualGradeRequired("Y".equalsIgnoreCase(rst.getString("MANUAL_GRADE_REQUIRED")) ? true : false);

            assignment.setUuid(rst.getString("uuid"));
            String provider = rst.getString("PROVIDER");
            assignment.setContentProvider(provider);
            assignment.setProvider(provider);
            assignment.setParentSectionId(rst.getLong("PARENT_SECTION_ID"));
            assignment.setAssignmentReferenceId(StringUtilities.getNonNullString(rst
                    .getString("ASSIGNMENT_REFERENCE_ID")));
            if (rst.getString("PARENT_ASSIGNMENT_ID") != null) {
                assignment.setParentAssignmentId(rst.getLong("PARENT_ASSIGNMENT_ID"));
            }
            logger.debug("Assignment_chat_HAS_CONTENT_POLICIES :" + rst.getString("HAS_CONTENT_POLICIES"));
            if ("Y".equals(rst.getString("HAS_CONTENT_POLICIES"))) {
                assignment.setHasContentPolicies(true);
            } else {
                assignment.setHasContentPolicies(false);
            }
            logger.debug("Assignment_chat_HAS_CONTENT_POLICIES_getHasContentPolicies :" + assignment.getHasContentPolicies());
            logger.debug("Assignment_chat_ARE_CONTENT_POLICIES_DIRTY :" + rst.getString("ARE_CONTENT_POLICIES_DIRTY"));
            if ("Y".equals(rst.getString("ARE_CONTENT_POLICIES_DIRTY"))) {
                assignment.setAreContentPoliciesDirty(true);
            } else {
                assignment.setAreContentPoliciesDirty(false);
            }
            logger.debug("Assignment_chat_HAS_CONTENT_POLICIES_setAreContentPoliciesDirty :" + assignment.getAreContentPoliciesDirty());

            if ("Y".equals(rst.getString("PEER_REVIEW_ENABLED"))) {
                assignment.setPeerReview(true);
                String timestamp = StringUtilities.getNonNullString(rst
                        .getString("PEER_REVIEW_DUE_DATE"));
                if (ValidationUtils.stringIsBlankOrNull(timestamp)) {
                    assignment.setPeerReviewDueDate(new Date(0));
                } else {
                    assignment.setPeerReviewDueDate(getTimestamp(rst, "PEER_REVIEW_DUE_DATE"));
                }
            } else {
                assignment.setPeerReview(false);
            }
            // added to add BB_DEPLOY_ON flag value in assignment object.

            if ("Y".equals(rst.getString("BB_IS_DEPLOYED"))) {
                assignment.setBbDeployed(true);
            } else {
                assignment.setBbDeployed(false);
            }
            if ("Y".equals(rst.getString("BB_DEPLOY_ON"))) {
                assignment.setBbDeployOn(true);
            } else {
                assignment.setBbDeployOn(false);
            }
            if ("Y".equals(rst.getString("LMS_DEPLOY_ON"))) {
                assignment.setLmsDeployOn(Boolean.TRUE);
            } else {
                assignment.setLmsDeployOn(Boolean.FALSE);
            }
            if ("Y".equals(rst.getString("LMS_DEPLOYED"))) {
                assignment.setLmsDeployed(Boolean.TRUE);
            } else {
                assignment.setLmsDeployed(Boolean.FALSE);
            }
        }
        return assignment;
    }

    public static Timestamp getTimestamp(ResultSet resultSet, String columnName) throws SQLException {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone(DateUtil.DB_TIMEZONE_ID));
        return resultSet.getTimestamp(columnName, calendar);
    }

    */
    protected String sanitize(String query) {
        if (query != null) {
            return Replace.replace(query, "'", "''");
        }
        return query;
    }
    private long generateIDUsingSequence(String sequenceName) throws Exception{
        return jdbcTemplate.queryForObject("SELECT " + sequenceName + ".NEXTVAL FROM DUAL", Long.class);
    }

    private long getNextSequenceNoForActivity(long assignmentID) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("assignmentID", assignmentID);
        int cost = 1;
        Long sequence = namedParameterJdbcTemplate.queryForObject(GET_MAX_SEQUENCE_NO, map, Long.class);
        if (sequence != null){
            return sequence + cost;
        }
        return cost;
    }

    public void copyModuleAssignmentMapping(Map<String, String> modulesMap,Map<String, String> assignmentsMap) {

        try {
            Iterator<Map.Entry<String, String>> it = modulesMap.entrySet().iterator();
            long[] srcModuleIds = new long[modulesMap.size()];
            long[] destModuleIds = new long[modulesMap.size()];
            int i = 0;
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                srcModuleIds[i] = Long.parseLong(e.getKey());
                destModuleIds[i] = Long.parseLong(e.getValue());
                i++;
            }

            it = assignmentsMap.entrySet().iterator();
            long[] srcAssignmentIds = new long[assignmentsMap.size()];
            long[] dstAssignmentIds = new long[assignmentsMap.size()];
            i = 0;
            while (it.hasNext()) {
                Map.Entry<String, String> e = (Map.Entry) it.next();
                srcAssignmentIds[i] = Long.parseLong(e.getKey());
                dstAssignmentIds[i] = Long.parseLong(e.getValue());
                i++;
            }

            connection = jdbcTemplate.getDataSource().getConnection().unwrap(OracleConnection.class);

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    //.withSchemaName("")
                    .withProcedureName("copy_module_assignment_mapping")
                    .declareParameters(new SqlParameter[]{
                            new SqlParameter("srcModuleIds", Types.ARRAY),
                            new SqlParameter("dstModuleIds", Types.ARRAY),
                            new SqlParameter("srcAssignmentIds", Types.ARRAY),
                            new SqlParameter("dstAssignmentIds", Types.ARRAY)
                    });

            ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("NUM_ARRAY", connection);
            ARRAY srcModuleIdsArray = new ARRAY(descriptor, connection, srcModuleIds);
            ARRAY destModuleIdsArray = new ARRAY(descriptor, connection, destModuleIds);
            ARRAY srcAssignmentIdsArray = new ARRAY(descriptor, connection, srcAssignmentIds);
            ARRAY dstAssignmentIdsArray = new ARRAY(descriptor, connection, dstAssignmentIds);

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("srcModuleIds",srcModuleIdsArray);
            mapSqlParameterSource.addValue("dstModuleIds",destModuleIdsArray);
            mapSqlParameterSource.addValue("srcAssignmentIds",srcAssignmentIdsArray);
            mapSqlParameterSource.addValue("dstAssignmentIds",dstAssignmentIdsArray);

            Map<String, Object> result = simpleJdbcCall.execute(mapSqlParameterSource);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void copyAssignmentLineItemsForMultipleAssignments(CopyAssignmentTO[] copyAssignments) throws Exception {
        Connection conn = null;
        PreparedStatement ps2 = null;

        ResultSet rs1 = null;

        HashMap<String, List<String>> dstAssignmentIdLineItemIdsMap = new HashMap<String, List<String>>();

        String query1 = "insert into assignment_line_item (ID,ASSIGNMENT_ID,POLICY_INSTANCE_SET_ID,ASSIGN_LINE_ITEM_TYPE_ID,NAME,DELIVERABLE_FORMAT,CREATED_DATE,UPDATED_DATE,DRAFT_NO) "
                +
                "values (?, ?,  null, ?, ?, null, sysdate, sysdate, ?)";

        String query2 = "select t1.policy_instance_set_id from SEC_LINE_ITEM_POLICY_INSTANCE t1, assignment_line_item t2 " +
                " where t1.ASSIGN_LINE_ITEM_ID = t2.ID and t2.ASSIGNMENT_ID = ? and t1.SECTION_ID = ?  order by t2.draft_no desc";

        String query4 = "insert into sec_assign_line_item_activity (ID,SECTION_ID,ASSIGN_LINE_ITEM_ID,AUTHOR_USER_ID,AUTHOR_ROLE_TYPE,STATUS,CREATED_DATE,UPDATED_DATE) "
                +
                "values (?, ?, ?, ?, ?, ?, sysdate, sysdate)";

        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            List<Object[]> batchParams = new ArrayList<Object[]>();

            for (int i = 0; i < copyAssignments.length; i++) {
                AssignmentLineItem[] lineItemsForSrcAssignment = this.getAssignmentLineItemsForAssignment(copyAssignments[i].getAssignmentId(),
                        copyAssignments[i].getSectionId());

                ArrayList<String> dstLineItemids = new ArrayList<String>();

                /* Converting insert to jdbc template + adding column names to query- STARTS (TCS) */

                for (int j = 0; j < lineItemsForSrcAssignment.length; j++) {
                    long newAssignmentLineItemId = generateIDUsingSequence("gbs_sequence");

                    /* Converting batch insert to jdbc template + adding column names to query- STARTS (TCS) */
                    Object[] values = new Object[] {
                            newAssignmentLineItemId,
                            copyAssignments[i].getNewAssignmentId(),
                            lineItemsForSrcAssignment[j].getAssignmentLineItemTypeId(),
                            lineItemsForSrcAssignment[j].getName(),
                            lineItemsForSrcAssignment[j].getDraftNo() };
                    batchParams.add(values);

                    dstLineItemids.add("" + newAssignmentLineItemId);
                    if (copyAssignments[i].getType().equals("DISCUSSION")) {
                        copyAssignments[i].getAssignmetnLineItemIds().add(newAssignmentLineItemId);
                    }
                }
                dstAssignmentIdLineItemIdsMap.put("" + copyAssignments[i].getNewAssignmentId(), dstLineItemids);

            }
            jdbcTemplate.batchUpdate(query1, batchParams);

            boolean flg = false;

            batchParams = new ArrayList<Object[]>();
            for (int i = 0; i < copyAssignments.length; i++) {
                ps2 = conn.prepareStatement(query2);
                ps2.setLong(1, copyAssignments[i].getAssignmentId());
                ps2.setLong(2, copyAssignments[i].getSectionId());
                rs1 = ps2.executeQuery();
                List<String> dstLineItemIds = dstAssignmentIdLineItemIdsMap.get("" + copyAssignments[i].getNewAssignmentId());
                // rs1 should contain same number of rows as the size of dstAssignmentIdLineItemIdsMap.get("" +
                // copyAssignments[i].getNewAssignmentId())
                // because both are derived from the number of line items in source assignment.
                int j = 0;
                List<AssignmentLineItem> dstAssignmentLineItems = new ArrayList<AssignmentLineItem>();
                while (rs1.next()) {
                    long srcPolicyInstanceSetId = rs1.getLong("policy_instance_set_id");
                    long dstPolicyInstanceSetId = this.copyPolicyInstanceSet(srcPolicyInstanceSetId);

                    AssignmentLineItem lineItem = new AssignmentLineItem();
                    lineItem.setId(Long.parseLong(dstLineItemIds.get(j)));
                    PolicyInstanceSet polInstSet = new PolicyInstanceSet();
                    polInstSet.setId(dstPolicyInstanceSetId);
                    lineItem.setPolicyInstanceSet(polInstSet);
                    dstAssignmentLineItems.add(lineItem);
                    j++;
                }
                this.addAssignmentLineItemsToSection(dstAssignmentLineItems.toArray(new AssignmentLineItem[0]), copyAssignments[i].getNewSectionId());

                if (copyAssignments[i].getType().equals("DISCUSSION")) {
                    for (Object lineItemid : copyAssignments[i].getAssignmetnLineItemIds()) {
                        long actId = generateIDUsingSequence("gbs_sequence");
                        /* Converting batch insert to jdbc template + adding column names to query- STARTS (TCS) */
                        Object[] values = new Object[] {
                                actId,
                                copyAssignments[i].getNewSectionId(),
                                Long.parseLong(lineItemid.toString()),
                                copyAssignments[i].getNewPrimaryInstructorId(),
                                "I",
                                "not started" };
                        batchParams.add(values);

                        flg = true;
                    }
                }
            }
            if (flg) {
                jdbcTemplate.batchUpdate(query4, batchParams);

            }

            logger.debug("Finished copy");
        } catch (SQLException e) {
            logger.error("", e);
            logger.error("SQLException Message:" + e.getMessage());
            throw new Exception(e);
        } catch (DataAccessException e) {
            logger.error("", e);
            logger.error("SQLException Message:" + e.getMessage());
            throw new Exception(e);
        } finally {
            releaseResources(conn, ps2, rs1);
        }
    }

    public AssignmentLineItem[] getAssignmentLineItemsForAssignment(long assignmentId, long sectionId)
            throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<AssignmentLineItem> lineItemList = new ArrayList<AssignmentLineItem>();

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(GET_ASSINGMENT_LINE_ITEM);
            ps.setLong(1, assignmentId);
            ps.setLong(2, sectionId);
            rst = ps.executeQuery();
            while (rst.next()) {
                AssignmentLineItem lineItem = new AssignmentLineItem();
                lineItem.setId(rst.getLong("id"));
                lineItem.setAssignmentLineItemTypeId(rst.getLong("assign_line_item_type_id"));
                lineItem.setName(rst.getString("name"));
                lineItem.setDraftNo(rst.getInt("draft_no"));
                lineItem.setAssignmentId(assignmentId);
                if (rst.getLong("sec_ali_pol_inst_id") > 0) {
                    lineItem.setPolicyInstanceSet(getPolicyInstanceSetWithPolicies(rst.getLong("sec_ali_pol_inst_id")));
                }
                lineItemList.add(lineItem);

            }

            return lineItemList.toArray(new AssignmentLineItem[0]);
        } catch (SQLException e) {
            throw e;
        } finally {
            releaseResources(connection, ps, rst);
        }

    }

    private static final String GET_ASSINGMENT_LINE_ITEM = "select ali.*, slp.policy_instance_set_id as sec_ali_pol_inst_id from assignment_line_item ali, assignment_line_item_type alit, sec_line_item_policy_instance slp "
            + "where slp.assign_line_item_id = ali.id and ali.assign_line_item_type_id = alit.id and ali.assignment_id = ? and slp.section_id = ? order by ali.draft_no desc";

    private static final String ADD_LINE_ITEMS_TO_SECTION = "insert into SEC_LINE_ITEM_POLICY_INSTANCE (ID,POLICY_INSTANCE_SET_ID,ASSIGN_LINE_ITEM_ID,SECTION_ID,CREATED_DATE,UPDATED_DATE) ";

    private static final String GET_POLICY_INSTANCE = "select pis.*, pi.policy_id, pi.value, p.exchane_key, p.name as policy_name, p.is_content_policy from policy_instance_set pis "
            + "left outer join policy_instance pi "
            + "on pis.id = pi.policy_instance_set_id inner join policy p on p.id = pi.policy_id where pis.id = ?";

    private static final String INSERT_POLICY_INSTANCE_SET = "insert into policy_instance_set (ID,NAME,POLICY_SET_ID,PARENT_INSTANCE_SET_ID,CREATED_DATE,UPDATED_DATE) "
            +
            "values(:id, :name, :policySetId, :parentInstanceSetId, sysdate, sysdate)";
    private static final String INSERT_POLICY_INSTANCE_VALUES = "insert into policy_instance (ID,POLICY_ID,POLICY_INSTANCE_SET_ID,VALUE,CREATED_DATE,UPDATED_DATE) "
            +
            "values(:id, :policyId, :policyInstanceSetId, :value, sysdate, sysdate)";
    public void addAssignmentLineItemsToSection(AssignmentLineItem[] lineItems, long sectionId) throws Exception {
        try {

            /* Converting insert to jdbc template + adding column names to query- STARTS (TCS) */
            MapSqlParameterSource[] batchParams = new MapSqlParameterSource[lineItems.length];
            for (int i = 0; i < lineItems.length; i++) {
                batchParams[i] = new MapSqlParameterSource();
                batchParams[i].addValue("id", generateIDUsingSequence("gbs_sequence"));
                batchParams[i].addValue("policyInstanceSetId", lineItems[i].getPolicyInstanceSet().getId());
                batchParams[i].addValue("assignLineItemId", lineItems[i].getId());
                batchParams[i].addValue("sectionId", sectionId);

            }
            namedParameterJdbcTemplate.batchUpdate(ADD_LINE_ITEMS_TO_SECTION, batchParams);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public PolicyInstanceSet getPolicyInstanceSetWithPolicies(long policyInstanceSetId) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rst = null;
        PolicyInstanceSet policyInstanceSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(GET_POLICY_INSTANCE);
            ps.setLong(1, policyInstanceSetId);
            rst = ps.executeQuery();
            while (rst.next()) {
                if (policyInstanceSet == null) {
                    policyInstanceSet = new PolicyInstanceSet();
                    policyInstanceSet.setId(rst.getLong("id"));
                    policyInstanceSet.setName(rst.getString("name"));
                    policyInstanceSet.setPolicySetId(rst.getLong("policy_set_id"));
                    policyInstanceSet.setPolicyList(new ArrayList<Policy>());
                }

                if (rst.getString("policy_id") != null && policyInstanceSet != null) {
                    Policy p = new Policy();
                    p.setId(rst.getLong("policy_id"));
                    p.setExchange_key(rst.getString("exchane_key"));
                    p.setName(rst.getString("policy_name"));
                    p.setValue(rst.getString("value"));
                    p.setContentDrivenPolicy("Y".equals(rst.getString("is_content_policy")) ? true : false);
                    policyInstanceSet.getPolicyList().add(p);

                }
            }
            return policyInstanceSet;
        } catch (SQLException e) {
            throw e;
        } finally {
            releaseResources(connection, ps, rst);
        }

    }

    private long copyPolicyInstanceSet(long policyInstanceSetId) throws Exception {
        PolicyInstanceSet policyInstanceSet = this.getPolicyInstanceSetWithPolicies(policyInstanceSetId);
        return this.createPolicyInstanceSet(policyInstanceSet);

    }

    public long createPolicyInstanceSet(PolicyInstanceSet policyInstanceSet) throws Exception {
        long policyInstanceSetId = 0l;

        try {
            policyInstanceSetId = generateIDUsingSequence("gbs_sequence");
            policyInstanceSet.setId(policyInstanceSetId);

            /* Converting insert to jdbc template + adding column names to query- STARTS (TCS) */
            Map<String, Object> argsMap = new HashMap<String, Object>();
            argsMap.put("id", policyInstanceSet.getId());
            argsMap.put("name", policyInstanceSet.getName());
            argsMap.put("policySetId", policyInstanceSet.getPolicySetId());
            argsMap.put("parentInstanceSetId", null);

            namedParameterJdbcTemplate.update(INSERT_POLICY_INSTANCE_SET, argsMap);

            if (policyInstanceSet.getPolicyList() != null && policyInstanceSet.getPolicyList().size() > 0) {

                /* Converting batch insert to jdbc template + adding column names to query- STARTS (TCS) */
                List<MapSqlParameterSource> batch = new ArrayList<MapSqlParameterSource>();
                for (Policy p : policyInstanceSet.getPolicyList()) {
                    if (p.getValue() != null)
                    {
                        MapSqlParameterSource batchParams = new MapSqlParameterSource();
                        batchParams.addValue("id", generateIDUsingSequence("gbs_sequence"));
                        batchParams.addValue("policyId", p.getId());
                        batchParams.addValue("policyInstanceSetId", policyInstanceSetId);
                        batchParams.addValue("value", p.getValue());

                        batch.add(batchParams);
                    }
                }
                namedParameterJdbcTemplate.batchUpdate(INSERT_POLICY_INSTANCE_VALUES, batch.toArray(new MapSqlParameterSource[batch.size()]));
            }

            return policyInstanceSetId;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    protected void releaseResources(Connection jConnection, Statement stmt, ResultSet rst) {
        // need to refactor and get rid of this.
        /*if (rst != null) {
            try {
                rst.close();
            } catch (SQLException e) {
                logger.error(e);
            }
            rst = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.error(e);
            }
            stmt = null;
        }
        // This one keeps the Connection open til commit point in a transaction
        DataSourceUtils.releaseConnection(jConnection, getDataSource());*/
    }

}
