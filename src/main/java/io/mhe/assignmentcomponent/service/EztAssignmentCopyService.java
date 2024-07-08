package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.dao.IAssignmentCopyDAO;
import io.mhe.assignmentcomponent.vo.*;
import io.mhe.assignmentcomponent.vo.AssignmentTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
                                            Map assignMap) throws Exception {
        try {
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

            // ezt

            iIntegrationRestService.copyXWorkFlow(new
                    CopyAssignmentTO[] { srcAssignment }); // ezt call to do
            logger.error("####################### in copyAssignmentsToNewSection after set 2  srcAssignment {}",srcAssignment);
            if ("failed".equals(srcAssignment.getCopyEZTStatus())) {
                assignmentCopyDAO.deleteMultipleAssignments(Arrays.asList(srcAssignment.getNewAssignmentId()));
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

            logger.error("####################### in copyAssignmentsToNewSection completed");
        } catch (Exception e) {
            assignmentCopyDAO.deleteMultipleAssignments(Arrays.asList(srcAssignment.getNewAssignmentId()));
            throw e;
        }
    }




}
