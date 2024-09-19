package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.dao.IAssignmentCopyDAO;
import io.mhe.assignmentcomponent.vo.*;
import io.mhe.assignmentcomponent.vo.AssignmentTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("ASSESMENT")
public class EztAssignmentCopyService extends AssignmentCopyService{
    private final Logger logger = LoggerFactory.getLogger(EztAssignmentCopyService.class);

    @Autowired
    private IAssignmentCopyDAO assignmentCopyDAO;

    @Autowired
    private IIntegrationRestService iIntegrationRestService;


    public void copyAssignmentsToNewSection(CopyAssignmentTO srcAssignment, long oldSectionID,
                                            long newSectionID,
                                            long[] origCategoryIds,
                                            long[] newCategoryIds,
                                            long newCourseId,
                                            long newSectionId,
                                            HashMap modulesMap,
                                            String coursePrimaryInstructorId,
                                            Map<Long, Long> oldAndNewCategories,
                                            Map<Long, Long> oldAndNewOutcomes,
                                            boolean isMarathon) throws Exception {
        try {

            doGenericCopyAssignment( srcAssignment,  oldSectionID, newSectionID, origCategoryIds, newCategoryIds, newCourseId, newSectionId);


            iIntegrationRestService.copyXWorkFlow(new
                    CopyAssignmentTO[] { srcAssignment }); // ezt call to do
            logger.error("####################### in copyAssignmentsToNewSection after set 2  srcAssignment {}",srcAssignment);
            if ("failed".equals(srcAssignment.getCopyEZTStatus())) {
                assignmentCopyDAO.deleteMultipleAssignments(List.of(srcAssignment.getNewAssignmentId()));
                throw new Exception("EZTO copy for the assignment failed");
            }
            if (srcAssignment.getNewNativeAlaId() != null) {
                logger.info("######### srcAssignment {}", srcAssignment);
                this.registerActivityFirstTime(srcAssignment.getNewAssignmentId(), srcAssignment.getNewNativeAlaId(),
                        srcAssignment.getTitle());
                Activity[] activity = this.getActivitiesForAssignment(srcAssignment.getAssignmentId());
                logger.info("######### activity length {} srcAssignment.getAssignmentId() {}", activity.length , srcAssignment.getAssignmentId());
                this.updatePointsAndQuestionsForAssignment(srcAssignment.getNewAssignmentId(),
                        activity[0].getWeight(), activity[0].getQuestions(), activity[0].getAvailableQuestions());

                // Changing the initial sync status from Required to In-Progress because we are going to sync the
                // assignment later using the pullRegistrationMultiple API.
                logger.info("######## srcAssignment.getNewAssignmentId() {} srcAssignment.getNewNativeAlaId() {}", srcAssignment.getNewAssignmentId(), srcAssignment.getNewNativeAlaId());
                this.insertSyncStatusForAssignment(srcAssignment.getNewAssignmentId(),
                        srcAssignment.getNewNativeAlaId(), "In Progress");
                // Inserting record in assignment_parent_status to check the parent status in registration flow.
                try{
                    this.insertParentAssignmentStatusForAssignment(srcAssignment.getNewAssignmentId(), srcAssignment.getAssignmentId(), srcAssignment.getParentAssignmentStatus());
                }catch(Exception ex){
                    logger.error("Error while inserting parent assignment status", ex);
                }

            } else {
                throw new Exception("Assignment doesn't have any nativealaId");
            }

            // ezt
            iIntegrationRestService.pullRegistrationMultiple( new AssignmentTO(srcAssignment.getAssignmentId(),srcAssignment.getNativeAlaId()));

            doRelatedUpdatesPostCopy( srcAssignment,  oldSectionID, newSectionId, modulesMap, coursePrimaryInstructorId,
                    oldAndNewCategories, oldAndNewOutcomes, isMarathon);

            logger.error("####################### in copyAssignmentsToNewSection completed");
        } catch (Exception e) {
            assignmentCopyDAO.deleteMultipleAssignments(List.of(srcAssignment.getNewAssignmentId()));
            throw e;
        }
    }


    public void copyModuleAssignmentMapping(Map<String, String> modulesMap, Map<String, String> assignmentsMap) {
        assignmentCopyDAO.copyModuleAssignmentMapping(modulesMap, assignmentsMap);
    }

}
