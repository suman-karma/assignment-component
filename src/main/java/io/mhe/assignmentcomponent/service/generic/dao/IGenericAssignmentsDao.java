package io.mhe.assignmentcomponent.service.generic.dao;


import io.mhe.assignmentcomponent.basiclti.consumersecret.api.OAuthConsumerDetails;
import io.mhe.assignmentcomponent.service.generic.vo.HMAssignmentCategoryPolicy;
import io.mhe.assignmentcomponent.service.generic.vo.HomeworkManagerCategoryPolicy;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
@Repository
public interface IGenericAssignmentsDao {
	public void auditRestCallFailures(String operation,String jsonData,Exception exception,String url,String method);

    long getCourseIDBySectionID(long sourceSectionId);


    public HMAssignmentCategoryPolicy getPoliciesBySectionAndAssignment(final Long sectionId, final Long assignmentId);

    String getCourseIsbn(long sourceAssignmentId) ;

    String getCourseTimeZoneUsingSectionId(long destinationSectionId);

    public OAuthConsumerDetails getConsumerDetail(String consumerKey) throws Exception;

    public void saveHMMessage(Map<String, Long> idMap, Set<Long> failedDesSectionsId, String copyType, String messageKey,
                              Map<Long, Map<Long, String>> failedSecAndAssignmentMapWithExc, String exceptionDetailsForSectionFailed, Map<String, Boolean> booleanMap)
            throws Exception;
}
