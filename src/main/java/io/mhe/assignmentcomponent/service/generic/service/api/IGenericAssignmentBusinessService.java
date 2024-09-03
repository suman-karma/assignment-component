package io.mhe.assignmentcomponent.service.generic.service.api;




import io.mhe.assignmentcomponent.service.generic.vo.HomeworkManagerCategoryPolicy;
import io.mhe.assignmentcomponent.vo.Assignment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/* For Generic products,
 * create, edit flow.
 * its a service layer between action and business,manager
 */
@Service
public interface IGenericAssignmentBusinessService {
	boolean prepareAndSendRestCallForGenericAssignment(Assignment sourceAssignment, Assignment destinationAssignment,
													   String consumer, String baseUrl, String mode, String consumerKey)
			throws Exception;

	long getCourseIDBySectionID(long sourceSectionId);

	HomeworkManagerCategoryPolicy getAssignmentCategoryPolicies(long sourceAssignmentId, long sourceSectionId, Object o);

	String getCourseIsbn(long sourceAssignmentId);

	String getCourseTimeZoneUsingSectionId(long destinationSectionId);
}
