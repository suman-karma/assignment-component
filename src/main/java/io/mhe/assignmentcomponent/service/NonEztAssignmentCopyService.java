package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.dao.IAssignmentCopyDAO;
import io.mhe.assignmentcomponent.service.generic.constants.GenericAssignmentConstants;
import io.mhe.assignmentcomponent.service.generic.service.api.IGenericAssignmentBusinessService;
import io.mhe.assignmentcomponent.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service("NONASSESMENT")
public class NonEztAssignmentCopyService extends AssignmentCopyService{
    private final Logger logger = LoggerFactory.getLogger(NonEztAssignmentCopyService.class);

    @Autowired
    private IAssignmentCopyDAO assignmentCopyDAO;

    @Autowired
    private IIntegrationRestService iIntegrationRestService;

    @Autowired
    private IGenericAssignmentBusinessService genericAssignmentBusinessService;

    @Value("${MUZZY_LTI_POST_URL}")
    String baseUrl ;
    @Value("${MUZZY_CONSUMER_KEY}")
    String consumerKey;

    public void copyAssignmentsToNewSection(CopyAssignmentTO srcAssignment, long oldSectionID,
                                            long newSectionID,
                                            long[] origCategoryIds,
                                            long[] newCategoryIds,
                                            long newCourseId,
                                            long newSectionId,
                                            HashMap modulesMap,
                                            Map assignMap,
                                            String coursePrimaryInstructorId,
                                            Map<Long, Long> oldAndNewCategories,
                                            Map<Long, Long> oldAndNewOutcomes,
                                            boolean isMarathon) throws Exception {
        try {
            doGenericCopyAssignment( srcAssignment,  oldSectionID, newSectionID, origCategoryIds, newCategoryIds, newCourseId, newSectionId);

            // For group
            if (("Connect".equals(srcAssignment.getProvider())) && "GROUP".equalsIgnoreCase(srcAssignment.getType())) {
                if (srcAssignment.getNewAssignmentId() > 0)
                {
                    CopyAssignmentTO ca = srcAssignment;

                    this.copyGroupAssignmentPropertiesForCopyAssignment(new CopyAssignmentTO[] { ca });
                }
            }

            boolean genericAssignment = ("Generic".equals(srcAssignment.getProvider())
                    || (StringUtils.isNotEmpty(srcAssignment.getNativeAlaId())
                    && srcAssignment.getNativeAlaId().startsWith("Generic"))) ? true
                    : false;

            if ("Connect".equals(srcAssignment.getProvider()) || "TextFlow".equals(srcAssignment.getProvider())
                    || "ale".equalsIgnoreCase(srcAssignment.getProvider())
                    || "MUZZY_LANE".equalsIgnoreCase(srcAssignment.getProvider())
                    || genericAssignment) {

                if (logger.isDebugEnabled()) {
                    logger.debug("connectAssignments type = {} : assignmentId = {} : newAssignmentId = {}",
                            new Object[] {srcAssignment.getType(), srcAssignment.getAssignmentId(), srcAssignment.getNewAssignmentId()});
                }

                if (srcAssignment.getNewAssignmentId() != 0) {
                    Assignment assignment = this.getURLBasedAssignment(srcAssignment.getAssignmentId());
                    assignment.setID(srcAssignment.getNewAssignmentId());
                    if(genericAssignment) {
                        assignment.setProvider(srcAssignment.getProvider());
                        assignment.setNativeAlaId(srcAssignment.getNativeAlaId());
                    }
                    logger.debug("Copied assignment : {}  ", assignment);
                    ActivityItem[] items = null;
                    if ("GROUP".equalsIgnoreCase(assignment.getType())) {
                        assignment.setWeight(srcAssignment.getWeight());
                        Activity[] activity = this.getActivitiesForAssignment(srcAssignment.getAssignmentId());
                        if (activity != null && activity.length > 0) {
                            items = this.getActivityItemsForActivity(activity[0].getID());
                        }

                    }
                    this.addActivityAndALAInfoForAssignment(assignment);
                    if ("GROUP".equalsIgnoreCase(assignment.getType())) {
                        Activity[] activity = this.getActivitiesForAssignment(srcAssignment.getNewAssignmentId());
                        if (activity != null && activity.length > 0) {
                            if (items != null && items.length > 0) {
                                for (int j = 0; j < items.length; j++) {
                                    items[j].setActivityId(activity[0].getID());
                                }
                                this.addActivityItemsToActivity(items, activity[0].getID());
                            }
                        }
                    }
                }
                // for muzzy lane
                if("MUZZY_LANE".equalsIgnoreCase(srcAssignment.getProvider())){
                    ActivityItem[] items = null;
                    Activity[] activity = this.getActivitiesForAssignment(srcAssignment.getAssignmentId());
                    if (activity != null && activity.length > 0) {
                        items = this.getActivityItemsForActivity(activity[0].getID());
                    }

                    Activity[] activity2 = this.getActivitiesForAssignment(srcAssignment.getNewAssignmentId());
                    if (activity2 != null && activity2.length > 0) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("adding items " + items.length + "to activity2 " + activity2[0].getID());
                        }

                        if (items != null && items.length > 0) {
                            for (int k = 0; k < items.length; k++) {
                                items[k].setActivityId(activity2[0].getID());
                            }
                            this.addActivityItemsToActivity(items, activity2[0].getID());
                        }
                    }

                    Assignment copyFrom = this.getURLBasedAssignment(srcAssignment.getAssignmentId());
                    Assignment copyTo = this.getURLBasedAssignment(srcAssignment.getNewAssignmentId());
                    String consumer = "MUZZY_LANE".toLowerCase();
                    //String baseUrl = System.getProperty("MUZZY_LTI_POST_URL");
                    //String consumerKey = System.getProperty("MUZZY_CONSUMER_KEY");
                    if (logger.isDebugEnabled()) {
                        logger.debug("Calling Muzzy Lane for assignment copy on their side");
                        logger.debug("Source Assignment : {}", copyFrom);
                        logger.debug("Destination Assignment : {}", copyTo);
                    }
                    logger.info("Calling Muzzy Lane for assignment copy on their side baseUrl SS: "+System.getProperty("MUZZY_LTI_POST_URL") + " consumerKey SS: "+System.getProperty("MUZZY_CONSUMER_KEY"));
                    logger.info("Calling Muzzy Lane for assignment copy on their side baseUrl : "+baseUrl + " consumerKey : "+consumerKey);
                    boolean genericAssignmentCopied = genericAssignmentBusinessService.prepareAndSendRestCallForGenericAssignment(copyFrom,
                            copyTo, consumer, baseUrl, GenericAssignmentConstants.WS_REQUEST_COPY_MODE, consumerKey);
                    logger.info("default template generic assignment copied status " + genericAssignmentCopied);
                    if (logger.isDebugEnabled()) {
                        logger.debug("default template generic assignment copied status " + genericAssignmentCopied);
                    }
                }
            }

            doRelatedUpdatesPostCopy( srcAssignment,  oldSectionID, newSectionId, modulesMap, assignMap, coursePrimaryInstructorId,
                    oldAndNewCategories, oldAndNewOutcomes, isMarathon);


            logger.error("####################### in copyAssignmentsToNewSection completed");
        } catch (Exception e) {
            assignmentCopyDAO.deleteMultipleAssignments(Arrays.asList(srcAssignment.getNewAssignmentId()));
            throw e;
        }
    }


    public void copyModuleAssignmentMapping(Map<String, String> modulesMap, Map<String, String> assignmentsMap) {
        assignmentCopyDAO.copyModuleAssignmentMapping(modulesMap, assignmentsMap);
    }

}
