package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.dao.IAssignmentCopyDAO;
import io.mhe.assignmentcomponent.sqs.util.AmazonSQSConstants;
import io.mhe.assignmentcomponent.sqs.util.AmazonSQSHelper;
import io.mhe.assignmentcomponent.vo.*;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AssignmentCopyService  implements IAssignmentCopyService{
    private final Logger logger = LoggerFactory.getLogger(AssignmentCopyService.class);

    @Autowired
    private IAssignmentCopyDAO assignmentCopyDAO;

    @Autowired
    private IIntegrationRestService iIntegrationRestService;


    @Autowired
    private AmazonSQSHelper amazonSQSHelper;


    @Override
    public void copyAssignment(CopyAssignmentTO srcAssignment, long oldSectionID, long newSectionID, long[] origCategoryIds, long[] newCategoryIds, long newCourseId, long newSectionId, HashMap modulesMap, Map assignMap) throws Exception {
        logger.error("*************** copyAssignment ");
        this.copyAssignmentsToNewSection(srcAssignment,  oldSectionID,
                newSectionID,
                origCategoryIds,
                newCategoryIds,
                newCourseId,
                newSectionId,
                modulesMap,
                assignMap);
    }

    public void copyAssignmentsToNewSection(
            CopyAssignmentTO srcAssignment, long oldSectionID,
                                            long newSectionID,
                                            long[] origCategoryIds,
                                            long[] newCategoryIds,
                                            long newCourseId,
                                            long newSectionId,
                                            HashMap modulesMap,
                                            Map assignMap) throws Exception {}

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

    /*
    @Override
    public GroupAssignment getGroupAssignmentById(long assignmentId, long sectionId) {
        return assignmentCopyDAO.getGroupAssignmentById(assignmentId,sectionId);
    }

    @Override
    public void copyGroupAssignmentPropertiesForCopyAssignment(CopyAssignment[] ca) {
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
    public Assignment getAssignment(long assignmentId) {
        return null;
    }

    @Override
    public Product getProduct(String type) {
        return null;
    }
    */

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

        long actNID = 0l;
        logger.info(" adding activities... existingActivities {}", existingActivities);
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



/*




    @Override
    public ActivityItem[] getActivityItemsByAssignmentId(long assignmentId) {
        return assignmentCopyDAO.getActivityItemsByAssignmentId(assignmentId);
    }



    @Override
    public void addActivityAndALAInfoForAssignment(Assignment assignmentObj) {
        Activity activity = AssignmentUtility.prepareActivityWithActivityItems(assignmentObj);
        boolean flag = false;
        ProductTemplate productTemplate = null;
        try {
            Product product = this.getProduct(assignmentObj.getType().getValue());
            if (product != null)
            {
                productTemplate = product.getProductTemplate();
            }
        } catch (Exception e) {
            logger.error("exception in addActivityAndALAInfoForAssignment" + e);
        }
        logger.debug("addActivityAndALAInfoForAssignment assignmentObj.getType()"+assignmentObj.getType()+" Product type: "+  productTemplate);
        // Have Removed Check for LabSmat/LearnSmart and other assignment types which are configured through Products
        // table and making the check at template level
        if (AssignmentType.VIDEO.equals(assignmentObj.getType()) || AssignmentType.ALE.equals(assignmentObj.getType())
                || AssignmentType.URLBased.equals(assignmentObj.getType())
                || AssignmentType.FILEATTACH.equals(assignmentObj.getType())
                || AssignmentType.GROUP.equals(assignmentObj.getType())
                || AssignmentType.WRITING.equals(assignmentObj.getType())
                || AssignmentType.BLOG.equals(assignmentObj.getType())
                || AssignmentType.MUZZY_LANE.equals(assignmentObj.getType())
                || AssignmentType.DISCUSSION.equals(assignmentObj.getType())
                || ProductVariables.PROVIDER_GENERIC.equals(assignmentObj.getProvider())
                || (StringUtils.isNotEmpty(assignmentObj.getNativeAlaId())
                && assignmentObj.getNativeAlaId().startsWith(ProductVariables.PROVIDER_GENERIC))
                || (ProductTemplate.BASIC.equals(productTemplate) || ProductTemplate.ADVANCED.equals(productTemplate)
                || ProductTemplate.DEFAULT.equals(productTemplate))) {

            try {
                flag = addActivitiesAndItemsForAssignment(activity, assignmentObj.getID());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // writing to SQS start
            long sectionID = assignmentObj.getCurrentSectionId();
            //method parameters - long assignmentId, long sectionId, long studentId, String transactionType, int attemptNo, String source
            try {
                amazonSQSHelper.writeToSQSQueue(assignmentObj.getID(), sectionID, 0,
                        AmazonSQSConstants.ACTIVITY_TYPE_SKILL_CATEGORY, 0,
                        "AlaManagerBusinessService -> addActivityAndALAInfoForAssignment()", null);
            } catch(Exception e) {
                logger.error("Error in writing to Amazon SQS inside addActivityAndALAInfoForAssignment", e);
            }
            // writing to SQS done

        }
       // void?? return flag;
    }
     */

}
