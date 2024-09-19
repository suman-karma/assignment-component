package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface IAssignmentCopyService {


    void copyAssignmentsToNewSection(CopyAssignmentTO srcAssignment, long oldSectionID,
                                     long newSectionID,
                                     long[] origCategoryIds,
                                     long[] newCategoryIds,
                                     long newCourseId,
                                     long newSectionId,
                                     HashMap modulesMap,
                                     String coursePrimaryInstructorId,
                                     Map<Long, Long> oldAndNewCategories,
                                     Map<Long, Long> oldAndNewOutcomes,
                                     boolean isMarathon) throws Exception;


    boolean copyHMPublicAssignments(CopyAssignmentTO[] srcAssignmentIds,
                                    long srcGBID, long dstGBId, long[] oldCategoryIds,
                                    long[] newCategoryIds, long newCourseId, long originalCourseId) throws Exception;


    void copySectionAssignmentXref(Map<Long, Long> sectionIdsMap,
                                   Map<Long, Long> assignmentsMap);


    void registerActivityFirstTime(long assignmentId, String nativeAlaId,
                                   String alaTitle) throws Exception;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Activity[] getActivitiesForAssignment(long assignmentId) throws Exception;

    void updatePointsAndQuestionsForAssignment(long assignmentId,
                                               float points, int numQuestions, int availableQuestions);

    void insertSyncStatusForAssignment(long assignmentId,
                                       String nativeAlaId, String status);

    void insertParentAssignmentStatusForAssignment(long assignmentId, long parentAssignmentId, String status) throws Exception;


    long updateActivity(Activity activity, long assignmentId);

    void updateAssignmentUpdatedDate(long assignmentId);

    long addActivityToAssignment(Activity activity, long assignmentId);

    ActivityItem[] getActivityItemsByAssignmentId(long assignmentId);

    void deleteActivityItemsByActivityId(long actNID);

    void addActivityItemsToActivity(ActivityItem[] items, long id);

    boolean addActivitiesAndItemsForAssignment(Activity activity, long assignmentId) throws Exception;


    void copyModuleAssignmentMapping(Map<String, String> modulesMap, Map<String, String> assignmentsMap);

    void copyCategoryAndOutcomeMappingToMultipleAssignment(Map assignmentsMap, long currentSectionId, long destinationSectionId, Map<Long, Long> oldAndNewCategories, Map<Long, Long> oldAndNewOutcomes, long sourceCourseId, long destinationCourseId);

    void copyMarathons(long oldSectionID, long newSectionId, long l, Map<Long, Long> sourceAndNewAssignmentMap);

    List<Marathon> getMarathons(Long sectionId) throws Exception;

    MarathonInfo getMarathonInfo(long marathonId, long sourceSectionid);

    long createNewMarathon(MarathonInfo marathonInfo);

    void createRubricForAssignment(long assignmentId, long sectionId, CourseLearningOutcomes courseLearningOutcomes) throws Exception;

    CourseLearningOutcomes reviewRubricForAssignment(long assignmentId, long sectionId);


    void copyGroupAssignmentPropertiesForCopyAssignment(CopyAssignmentTO[] ca);

    Assignment getURLBasedAssignment(long assignmentId);

    ActivityItem[] getActivityItemsForActivity(long id);

    void addActivityAndALAInfoForAssignment(Assignment assignment);
}
