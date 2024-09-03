package io.mhe.assignmentcomponent.service.generic.service;

import io.mhe.assignmentcomponent.service.NonEztAssignmentCopyService;
import io.mhe.assignmentcomponent.service.generic.dao.IGenericAssignmentsDao;
import io.mhe.assignmentcomponent.service.generic.service.async.IGenericAssignmentRemoteServiceInvoker;
import io.mhe.assignmentcomponent.service.generic.vo.HMAssignmentCategoryPolicy;
import io.mhe.assignmentcomponent.service.generic.vo.HomeworkManagerCategoryPolicy;
import io.mhe.assignmentcomponent.vo.Assignment;
import io.mhe.assignmentcomponent.vo.GenericAssignmentTransactionVO;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import io.mhe.assignmentcomponent.service.generic.service.api.IGenericAssignmentBusinessService;

import java.util.*;

import io.mhe.assignmentcomponent.common.util.DateUtil;

import static io.mhe.assignmentcomponent.service.generic.constants.GenericAssignmentConstants.DATE_FORMAT;


@Service
public class GenericAssignmentBusinessService implements
		IGenericAssignmentBusinessService {

	private final Logger logger = LoggerFactory.getLogger(IGenericAssignmentBusinessService.class);

	@Autowired
	private IGenericAssignmentRemoteServiceInvoker	genericAssignmentRemoteServiceInvoker;

	@Autowired
	IGenericAssignmentsDao genericAssignmentsDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean prepareAndSendRestCallForGenericAssignment(Assignment sourceAssignment, Assignment destinationAssignment,
															  String consumer, String baseUrl, String mode, String consumerKey)
			throws Exception {
		boolean success = false;
		try {
			GenericAssignmentTransactionVO transactionValueObject = getParametersForRestCall(sourceAssignment.getID(),
					destinationAssignment.getID(), sourceAssignment.getParentSectionId(),
					destinationAssignment.getParentSectionId(), sourceAssignment.getPrimary_instructor_id(),
					destinationAssignment.getPrimary_instructor_id(), mode);

			logger.debug("Inside prepareAndSendRestCallForGenericAssignment with transactionValueObject: {}", transactionValueObject);
			success = genericAssignmentRemoteServiceInvoker.sendRestCallForGenericAssignment(transactionValueObject, consumer, baseUrl, mode,
					GenericAssignmentTransactionVO.class, HttpMethod.POST, consumerKey);

		} catch (Exception e) {
			logger.error("Exception in prepareAndSendRestCallForGenericAssignment => " + e.getMessage(), e);
		}
		return success;
	}

	@Override
	public long getCourseIDBySectionID(long sourceSectionId) {
		return genericAssignmentsDao.getCourseIDBySectionID(sourceSectionId);
	}

	@Override
	public HMAssignmentCategoryPolicy getAssignmentCategoryPolicies(long sourceAssignmentId, long sourceSectionId, Object o) {
		return genericAssignmentsDao.getPoliciesBySectionAndAssignment(sourceSectionId,sourceAssignmentId);
	}

    @Override
    public String getCourseIsbn(long sourceAssignmentId) {
        return genericAssignmentsDao.getCourseIsbn(sourceAssignmentId);
    }

	@Override
	public String getCourseTimeZoneUsingSectionId(long destinationSectionId) {
		return genericAssignmentsDao.getCourseTimeZoneUsingSectionId(destinationSectionId);
	}

	public GenericAssignmentTransactionVO getParametersForRestCall(long sourceAssignmentId, long destinationAssignmentId, long sourceSectionId,
																   long destinationSectionId, String primaryInstructorId, String secondaryInstructorId, String mode) {
		GenericAssignmentTransactionVO transactionValueObject = null;

		long sourceCourseId = this.getCourseIDBySectionID(sourceSectionId);
		long destinationCourseId = this.getCourseIDBySectionID(destinationSectionId);

		if(sourceCourseId > 0 && destinationCourseId > 0) {
			transactionValueObject = new GenericAssignmentTransactionVO();

			transactionValueObject.setSourceCourseId(sourceCourseId);
			transactionValueObject.setSourceSectionId(sourceSectionId);
			transactionValueObject.setSourceAssignmentId(sourceAssignmentId);

			transactionValueObject.setDestinationCourseId(destinationCourseId);
			transactionValueObject.setDestinationSectionId(destinationSectionId);
			transactionValueObject.setDestinationAssignmentId(destinationAssignmentId);

			transactionValueObject.setPrimaryInstructorId(primaryInstructorId);
			transactionValueObject.setSecondaryInstructorId(secondaryInstructorId);
			transactionValueObject.setMessage(mode);
			transactionValueObject.setMode(mode);

			// Get course details
			// Get policy for source assignment Id.
			HomeworkManagerCategoryPolicy policiesToDisplay = null;
			policiesToDisplay = this.getAssignmentCategoryPolicies(sourceAssignmentId,
					sourceSectionId, null);
			HashMap policyMap = null;
			if (policiesToDisplay != null) {
				policyMap = policiesToDisplay.getPoliciesInAMap();
				if (policyMap != null) {
					logger.debug("start date:{}", policyMap.get("p_startdate"));
					logger.debug("due date:{}", policyMap.get("p_duedate"));
					String startDate = DateUtil.convertBetweenTimeZoneAndPattern((String) policyMap.get("p_startdate"), DateUtil.DB_TIMEZONE_ID,
							DateUtil.UTC_TIMEZONE_ID, DATE_FORMAT, DateUtil.DATE_FORMAT_IN_UTC_WITH_OFF_SET);
					String dueDate = DateUtil.convertBetweenTimeZoneAndPattern((String) policyMap.get("p_duedate"), DateUtil.DB_TIMEZONE_ID,
							DateUtil.UTC_TIMEZONE_ID, DATE_FORMAT, DateUtil.DATE_FORMAT_IN_UTC_WITH_OFF_SET);
					logger.debug("start date:{}", startDate);
					logger.debug("due date:{}", dueDate);
					transactionValueObject.setAssignmentStartDate(startDate);
					transactionValueObject.setAssignmentDueDate(dueDate);
				}
			}
			String courseIsbn = this.getCourseIsbn(sourceAssignmentId);
			transactionValueObject.setIsbn(courseIsbn);
			String timeZone = this.getCourseTimeZoneUsingSectionId(destinationSectionId);
			String timeZoneOffset = String.valueOf(DateUtil.getRawTimeZoneOffset(timeZone));
			transactionValueObject.setCourseTimeZone(timeZone);
			transactionValueObject.setCourseTimeZoneOffset(timeZoneOffset);
		}

		return transactionValueObject;
	}



}
