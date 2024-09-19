package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.dao.IAssignmentCopyDAO;
import io.mhe.assignmentcomponent.sqs.util.AmazonSQSConstants;
import io.mhe.assignmentcomponent.sqs.util.AmazonSQSHelper;
import io.mhe.assignmentcomponent.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class AssignmentCopyService  implements IAssignmentCopyService{
    private final Logger logger = LoggerFactory.getLogger(AssignmentCopyService.class);

    @Autowired
    private IAssignmentCopyDAO assignmentCopyDAO;

    @Autowired
    private IIntegrationRestService iIntegrationRestService;

    @Autowired
    private AmazonSQSHelper amazonSQSHelper;

    public void copyAssignmentsToNewSection(
            CopyAssignmentTO srcAssignment, long oldSectionID,
                                            long newSectionID,
                                            long[] origCategoryIds,
                                            long[] newCategoryIds,
                                            long newCourseId,
                                            long newSectionId,
                                            HashMap modulesMap,
                                            String coursePrimaryInstructorId, Map<Long, Long> oldAndNewCategories, Map<Long, Long> oldAndNewOutcomes, boolean isMarathon) throws Exception {
    }

    void doGenericCopyAssignment(CopyAssignmentTO srcAssignment, long oldSectionID,
                                 long newSectionID,
                                 long[] origCategoryIds,
                                 long[] newCategoryIds,
                                 long newCourseId,
                                 long newSectionId
    ) throws Exception{

        logger.error("####################### in copyAssignmentsToNewSection srcAssignment {}",srcAssignment);
        this.copyHMPublicAssignments(new CopyAssignmentTO[] { srcAssignment }, oldSectionID, newSectionID,
                origCategoryIds, newCategoryIds, newCourseId, newSectionId);
        logger.error("####################### in copyAssignmentsToNewSection after set1 srcAssignment {}",srcAssignment);
        Map<Long, Long> sectionIdsMap = new HashMap<Long, Long>();
        HashMap assignmentsMapForSection = new HashMap();

        sectionIdsMap.clear();
        assignmentsMapForSection.clear();

        sectionIdsMap.put(oldSectionID, newSectionID);
        assignmentsMapForSection.put(srcAssignment.getAssignmentId(), srcAssignment.getNewAssignmentId());
        this.copySectionAssignmentXref(sectionIdsMap, assignmentsMapForSection);
    }

    void doRelatedUpdatesPostCopy(CopyAssignmentTO srcAssignment, long oldSectionID,
                                  long newSectionId,
                                  HashMap modulesMap,
                                  String coursePrimaryInstructorId,
                                  Map<Long, Long> oldAndNewCategories,
                                  Map<Long, Long> oldAndNewOutcomes,
                                  boolean isMarathon
    ) throws Exception{
        // all
        Map assignMap = new HashMap();
        assignMap.put("" + srcAssignment.getAssignmentId(), "" + srcAssignment.getNewAssignmentId());
        logger.info("####### modulesMap {}", modulesMap);
        logger.info("####### assignMap {}", assignMap);
        this.copyModuleAssignmentMapping(modulesMap, assignMap);

        // other updates
        try {
            this.copyCategoryAndOutcomeMappingToMultipleAssignment(assignMap, oldSectionID, newSectionId,
                    oldAndNewCategories, oldAndNewOutcomes, srcAssignment.getCourseId(),srcAssignment.getNewCourseId()); // this to be completed huge dependicies.
        } catch (Exception ex) {
            logger.error("[copyCourse] Error with copyCategoryAndOutcomeMappingToMultipleAssignment: ", ex);
            throw ex;
        }

        if (isMarathon) {
            Map<Long, Long> sourceAndNewAssignmentMap = new HashMap<Long, Long>();
            Iterator<Map.Entry<String, String>> it = assignMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                sourceAndNewAssignmentMap.put(Long.parseLong(e.getKey()), Long.parseLong(e.getValue()));
            }
            this.copyMarathons(oldSectionID, newSectionId, Long.parseLong(coursePrimaryInstructorId),sourceAndNewAssignmentMap);
        }
    }




    public boolean copyHMPublicAssignments(CopyAssignmentTO[] srcAssignments, long srcSectionId, long dstSectionId, long[] oldCategoryIds,
                                           long[] newCategoryIds,
                                           long newCourseId, long originalCourseId) throws Exception {
        try {
            return assignmentCopyDAO.copyHMPublicAssignments(srcAssignments, srcSectionId, dstSectionId, oldCategoryIds, newCategoryIds,
                    newCourseId,
                    originalCourseId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void copySectionAssignmentXref(Map<Long, Long> sectionIdsMap, Map<Long, Long> assignmentsMap) {
         assignmentCopyDAO.copySectionAssignmentXref(sectionIdsMap,assignmentsMap);
    }


    @Override
    public void registerActivityFirstTime(long assignmentId, String nativeAlaId, String alaTitle) throws Exception{
        if (logger.isDebugEnabled()) {
            logger.debug("AlaManagerBean registerEmptyAla : assignment id = "
                    + assignmentId + " nativeAlaId = " + nativeAlaId
                    + " ala title = " + alaTitle);
        }
        String alaType = "Custom";
       // String contentProvider = ProductVariables.PROVIDER_EZTEST; public static final String PROVIDER_EZTEST = "EZTestOnline";
        String contentProvider = "EZTestOnline";
        Date currentDate = new Date();
        Activity activity = new Activity();
        activity.setNativeAlaId(nativeAlaId);
        activity.setType(alaType);
        activity.setAssignmentID(assignmentId);
        activity.setTitle(alaTitle);
        activity.setAlaContentProvider(contentProvider);

        this.addActivitiesAndItemsForAssignment(activity, assignmentId);

        // writing to SQS start
        // method parameters - long assignmentId, long sectionId, long studentId, String transactionType, int attemptNo, String source
        try {
            amazonSQSHelper.writeToSQSQueue(assignmentId, 0, 0, AmazonSQSConstants.ACTIVITY_TYPE_SKILL_CATEGORY, 0,
                    "AlaManagerBusinessService -> registerActivityFirstTime()", currentDate);
        } catch(Exception e) {
            logger.error("Error in writing to Amazon SQS with assignmentId {}", new Object[]{assignmentId}, e);
        }
        // writing to SQS done

    }

    @Override
    public Activity[] getActivitiesForAssignment(long assignmentId) throws Exception {
        return assignmentCopyDAO.getActivitiesForAssignment(assignmentId);
    }

    @Override
    public void updatePointsAndQuestionsForAssignment(long assignmentId, float points, int numQuestions, int availableQuestions) {
        assignmentCopyDAO.updatePointsAndQuestionsForAssignment(assignmentId,points,numQuestions,availableQuestions);
    }

    @Override
    public void insertSyncStatusForAssignment(long assignmentId, String nativeAlaId, String status) {
        assignmentCopyDAO.insertSyncStatusForAssignment(assignmentId,  nativeAlaId,  status);
    }

    @Override
    public void insertParentAssignmentStatusForAssignment(long assignmentId, long parentAssignmentId, String status) throws Exception {
        assignmentCopyDAO.insertParentAssignmentStatusForAssignment(assignmentId,  parentAssignmentId,  status);
    }





    @Override
    public void copyGroupAssignmentPropertiesForCopyAssignment(CopyAssignmentTO[] ca) {
        assignmentCopyDAO.copyGroupAssignmentPropertiesForCopyAssignment(ca);
    }

    @Override
    public Assignment getURLBasedAssignment(long assignmentId) {

        return assignmentCopyDAO.getURLBasedAssignment(assignmentId);
    }

    @Override
    public ActivityItem[] getActivityItemsForActivity(long id) {
        return assignmentCopyDAO.getActivityItemsForActivity(id);
    }

    @Override
    public void addActivityAndALAInfoForAssignment(Assignment assignment) {
        assignmentCopyDAO.addActivityAndALAInfoForAssignment(assignment);
    }


    @Override
    public void addActivityItemsToActivity(ActivityItem[] items, long id) {
        assignmentCopyDAO.addActivityItemsToActivity(items,id);
    }
    @Override
    public boolean addActivitiesAndItemsForAssignment(Activity activity, long assignmentId) throws Exception{
        Activity[] existingActivities = this.getActivitiesForAssignment(assignmentId);
        logger.info(" adding activities...");
        // Step 5 If Existing Activities , Activity Items are not null then delete Activities and Activity Items and all
        // the Associations
        if (existingActivities != null && existingActivities.length > 1) {
            throw new Exception("The Activities associated with assignment cannot be greater than 1");
        }
        if (activity == null) {
            throw new Exception("Activity Cannot be Null");
        }

        long actNID = 0L;
        logger.info("Adding activities existingActivities{}", (Object) existingActivities);
        if (existingActivities != null && existingActivities.length == 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("addAct = " + activity);
                logger.debug("alamanager_id = " + activity.getAlaManagerID());
            }
            activity.setID(existingActivities[0].getID());
            long beginUpdateActivity = System.currentTimeMillis();
            actNID = this.updateActivity(activity, assignmentId);
            this.updateAssignmentUpdatedDate(assignmentId);
            logger.debug("AlaManagerBusinessService.addActivitiesRegistrationInfo() -Time taken to update activity "
                    + (System.currentTimeMillis() - beginUpdateActivity) + " ms");
        } else {
            long addActivityTime = System.currentTimeMillis();
            logger.info(" adding activities... addActivityToAssignment activity {} assignmentId {}", activity, assignmentId);
            actNID = this.addActivityToAssignment(activity, assignmentId);
            logger.debug("AlaManagerBusinessService.addActivitiesRegistrationInfo() - Time taken for adding activity to activity table "
                    + (System.currentTimeMillis() - addActivityTime) + " ms");
        }
        ActivityItem[] activityItems = this.getActivityItemsByAssignmentId(assignmentId);
        if (activityItems.length > 0) {
            // Deleting the existing activity items.
            this.deleteActivityItemsByActivityId(actNID);
        }
        if (activity.getActivityItems() != null) {
            logger.info(" adding activities...items");
            this.addActivityItemsToActivity(activity.getActivityItems(), actNID);
        }
        logger.info(" Activities Created and associated with Assignment ");
        return true;
    }


    @Override
    public long updateActivity(Activity activity, long assignmentId) {
        return assignmentCopyDAO.updateActivity(activity,assignmentId);
    }
    @Override
    public void updateAssignmentUpdatedDate(long assignmentId) {
        assignmentCopyDAO.updateAssignmentUpdatedDate(assignmentId);
    }
    @Override
    public long addActivityToAssignment(Activity activity, long assignmentId) {
        return assignmentCopyDAO.addActivityToAssignment(activity,assignmentId);
    }

    @Override
    public ActivityItem[] getActivityItemsByAssignmentId(long assignmentId) {
        return new ActivityItem[0];
    }

    @Override
    public void deleteActivityItemsByActivityId(long actNID) {
        assignmentCopyDAO.deleteActivityItemsByActivityId(actNID);
    }
    @Override
    public void copyModuleAssignmentMapping(Map<String, String> modulesMap, Map<String, String> assignmentsMap) {
        assignmentCopyDAO.copyModuleAssignmentMapping(modulesMap, assignmentsMap);
    }

    public void copyCategoryAndOutcomeMappingToMultipleAssignment(Map assignmentsMap, long currentSectionId, long destinationSectionId,
                                                                  Map<Long, Long> oldAndNewCategories, Map<Long, Long> oldAndNewOutcomes, long sourceCourseId, long destinationCourseId ) {
        if(currentSectionId == 0L || destinationSectionId == 0L || assignmentsMap == null || oldAndNewCategories == null || oldAndNewOutcomes == null){
            throw new UnsupportedOperationException("This API should be used only for OBA" );
        }
        Map <String, String>assignMap = assignmentsMap;

        if (oldAndNewCategories.size() == 0) {

            for (Map.Entry<String, String> map : assignMap.entrySet()) {
                long oldAssignmentId = Long.parseLong(map.getKey());
                long newAssignmentId = Long.parseLong(map.getValue());
                if (sourceCourseId == destinationCourseId) {
                    CourseLearningOutcomes courseLearningOutcomes = this
                            .reviewRubricForAssignment(oldAssignmentId,
                                    currentSectionId);
                    if (courseLearningOutcomes
                            .getLearningOutcomeCategoryList().size() > 0) {

                        try {
                            this.createRubricForAssignment(newAssignmentId,
                                    destinationSectionId,
                                    courseLearningOutcomes);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }else{
                    assignmentCopyDAO.updateLearningOutcomePolicy(newAssignmentId,destinationSectionId,"false");
                }
            }

        } else {
            for (Map.Entry<String, String> map : assignMap.entrySet()) {
                long oldAssignmentId = Long.parseLong(map.getKey());
                long newAssignmentId = Long.parseLong(map.getValue());
                CourseLearningOutcomes courseLearningOutcomes = this
                        .reviewRubricForAssignment(oldAssignmentId,
                                currentSectionId);
                if (courseLearningOutcomes.getLearningOutcomeCategoryList()
                        .size() > 0) {
                    for (LearningOutcomeCategory categoryObj : courseLearningOutcomes
                            .getLearningOutcomeCategoryList()) {
                        long categoryId = categoryObj.getCategoryId();
                        if(oldAndNewCategories.get(categoryId)!=null)
                        {
                            categoryObj.setCategoryId(oldAndNewCategories
                                    .get(categoryId));
                        }
                        for (LearningOutcome outcomeObj : categoryObj
                                .getOutcomeList()) {
                            long outcomeId = outcomeObj.getOutcomeId();
                            if(oldAndNewOutcomes.get(outcomeId)!=null)
                            {
                                outcomeObj.setOutcomeId(oldAndNewOutcomes
                                        .get(outcomeId));
                            }
                        }
                    }

                    try {
                        this.createRubricForAssignment(newAssignmentId,
                                destinationSectionId, courseLearningOutcomes);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }

    @Override
    public void copyMarathons(long sourceSectionid, long newSectionId, long userID, Map<Long, Long> assignmentIDs) {
        final List<Marathon> marathons;
        try {
            marathons = this.getMarathons(sourceSectionid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!CollectionUtils.isEmpty(marathons)) {
            Collections.reverse(marathons);
            for (final Marathon marathon : marathons) {
                final MarathonInfo marathonInfo = this.getMarathonInfo(marathon.getMarathonId(), sourceSectionid);
                prepareMarathonToCopy(marathonInfo, newSectionId, userID, assignmentIDs);
                long newMarathonID = this.createNewMarathon(marathonInfo);
            }
        }
    }

    @Override
    public List<Marathon> getMarathons(Long sectionId) throws Exception {
        return assignmentCopyDAO.getMarathons(sectionId);
    }

    @Override
    public MarathonInfo getMarathonInfo(long marathonId, long sourceSectionid) {
        return assignmentCopyDAO.getMarathonInfo(marathonId,sourceSectionid);
    }

    @Override
    public long createNewMarathon(MarathonInfo marathonInfo) {
        return assignmentCopyDAO.createNewMarathon(marathonInfo);
    }

    private void prepareMarathonToCopy(final MarathonInfo marathonInfo, final long newSectionID, final long userID,
                                       final Map<Long, Long> assignmentMap) {
        marathonInfo.setUserId(userID);
        marathonInfo.setSectionId(newSectionID);

        for (MarathonBucket bucket : marathonInfo.getBucketList()) {
            Iterator<MarathonBucketAssignment> itr = bucket.getMarathonAssignmentList().iterator();
            while (itr.hasNext()) {
                MarathonBucketAssignment marathonAssignment = itr.next();
                long sourceAssignmentId = marathonAssignment.getAssignmentId();
                if (assignmentMap.containsKey(sourceAssignmentId)) {
                    marathonAssignment.setAssignmentId(assignmentMap.get(sourceAssignmentId));
                } else {
                    itr.remove();
                }
            }
        }
    }

    public void createRubricForAssignment(long assignmentId, long sectionId, CourseLearningOutcomes courseLearningOutcomes) throws Exception{
        if(assignmentId == 0L || sectionId == 0L || courseLearningOutcomes == null){
            throw new UnsupportedOperationException("This API should be used only for OBA" );
        }
        assignmentCopyDAO.createRubricForAssignment(assignmentId, courseLearningOutcomes);
    }

    public CourseLearningOutcomes reviewRubricForAssignment(long assignmentId, long sectionId) {
        if(assignmentId == 0L || sectionId == 0L){
            throw new UnsupportedOperationException("This API should be used only for OBA" );
        }
        return assignmentCopyDAO.reviewRubricForAssignment(assignmentId);
    }


}
