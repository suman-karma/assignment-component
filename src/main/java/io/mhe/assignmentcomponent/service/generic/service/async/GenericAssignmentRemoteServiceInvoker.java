package io.mhe.assignmentcomponent.service.generic.service.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mhe.assignmentcomponent.basiclti.consumersecret.api.OAuthConsumerDetails;
import io.mhe.assignmentcomponent.basiclti.util.OAuthUtil;
import io.mhe.assignmentcomponent.common.util.GenUtil;
import io.mhe.assignmentcomponent.dao.IAssignmentCopyDAO;
import io.mhe.assignmentcomponent.service.IIntegrationRestService;
import io.mhe.assignmentcomponent.service.generic.constants.GenericAssignmentConstants;
import io.mhe.assignmentcomponent.service.generic.constants.RestConstant;
import io.mhe.assignmentcomponent.service.generic.dao.IGenericAssignmentsDao;
import io.mhe.assignmentcomponent.service.generic.service.api.IGenericAssignmentBusinessService;
import io.mhe.assignmentcomponent.vo.GenericAssignmentTransactionVO;
import io.mhe.assignmentcomponent.vo.RestTransferTO;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static io.mhe.assignmentcomponent.service.generic.constants.GenericAssignmentConstants.*;

@Configuration
@EnableAsync
public class GenericAssignmentRemoteServiceInvoker implements
		IGenericAssignmentRemoteServiceInvoker {

	private final Logger logger = LoggerFactory.getLogger(GenericAssignmentRemoteServiceInvoker.class);

	public static final long THOUSAND = 1000;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	IGenericAssignmentsDao genericAssignmentsDao;

	@Autowired
	IAssignmentCopyDAO assignmentCopyDAO;

	@Autowired
	IIntegrationRestService iIntegrationRestService;


	public boolean sendRestCallForGenericAssignment(Object obj, String consumer, String baseUrl, String mode,
													Class<?> clazz, HttpMethod httpMethod, String consumerKey) {
		boolean success = false;
		if (clazz.isInstance(obj)) {
			ResponseEntity response = null;
			RestTransferTO restTO = null;
			try {
				restTO = getRestTO(baseUrl, "", mode, httpMethod, obj, consumer, consumerKey);
				restTO.setResponseType(clazz);

				response = iIntegrationRestService.callRestURL(restTO);

				if (response != null && response.getStatusCode().value() == HttpStatus.SC_OK) {
					success = true;
				} else {
					logger.error("The response status is not ok hence throwing an exception");
					throw new Exception("Generic Assignment Rest Call failed with request : " + restTO + " response : " + response);
				}

				logger.info("Rest response {} and response code {}", response, response.getStatusCode());
			} catch (Exception e) {
				logger.error("In exception block : Details {}", restTO);
				logger.error("Exception while invoking mode " + mode + e.getMessage(), e);

				handleException(obj, mode, response, e);
			} finally {
				MDC.remove("trackId");
			}
		} else {
			logger.error("Object is not instance of class type passed.");
		}
		return success;
	}


	private void handleException(Object obj, String mode,
								 ResponseEntity response, Exception e) {
		try {
			/**
			 * Soft delete connect assignment in case of copy/share flow. We are marking
			 * assignment as deleted in assignment table. For rest mode we will skip this.
			 */
			if (obj instanceof GenericAssignmentTransactionVO) {
				// Delete assignment.
				GenericAssignmentTransactionVO txVo = (GenericAssignmentTransactionVO) obj;
				logger.error("Marking broken assignment with Id {} deleted in mode {} connect.", new Object[] { txVo.getDestinationAssignmentId(), mode });
				logger.error("Rest call failed in mode : {} for request : {} with response : {} and Exception : {}", new Object[] { mode, txVo, response, GenUtil.getStackTrace(e) });
				if (WS_REQUEST_COPY_MODE.equals(mode)) {
					insertRemoteCallFailureMessages(txVo, e, "generic assignment copy failed", COPY_FEAILURE_MESSAGE_KEY);
					logger.error("generic assignment call failed for copy");
					assignmentCopyDAO.deleteMultipleAssignments(Arrays.asList( txVo.getDestinationAssignmentId()));
				} else if (WS_REQUEST_SHARE_MODE.equals(mode)) {
					logger.error("generic assignment call failed for share");
					throw new Exception("AssignmentShareFailed", e);
				}
			}
		} catch (Exception ex) {
			logger.error("Exception while logging rest call failures ", ex);
		}
	}


	private void insertRemoteCallFailureMessages(GenericAssignmentTransactionVO txVo,Exception e,String message,String messageKey){
		try {
			Map<Long, Map<Long, String>> failedSecAndAssignmentMapWithExc = new HashMap<Long, Map<Long, String>>();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();
			if (failedSecAndAssignmentMapWithExc.containsKey(txVo.getDestinationSectionId())) {
				failedSecAndAssignmentMapWithExc.get(txVo.getDestinationSectionId())
						.put(txVo.getSourceAssignmentId(), stackTrace);
			} else {

				Map<Long, String> failedAssignmentWithExc = new HashMap<Long, String>();
				failedAssignmentWithExc.put(txVo.getSourceAssignmentId(), stackTrace);
				failedSecAndAssignmentMapWithExc.put(txVo.getDestinationSectionId(), failedAssignmentWithExc);
			}
			Map<String, Long> idMap = new HashMap<String, Long>();
			idMap.put("srcSectionId", txVo.getSourceSectionId());
			idMap.put("jobIdSource", 0l);
			idMap.put("jobIdDest", 0l);
			Map<String, Boolean> booleanMap = new HashMap<String, Boolean>();
			booleanMap.put("failureNotify", Boolean.TRUE);
			booleanMap.put("isMarathon",Boolean.FALSE);
			Set<Long> sectionIds = new HashSet<Long>();
			sectionIds.add(txVo.getDestinationSectionId());
			genericAssignmentsDao.saveHMMessage(idMap, sectionIds, message, messageKey,
					failedSecAndAssignmentMapWithExc, "", booleanMap);

		} catch (Exception ex) {
			logger.error("saving to hm_messages failed ", ex);
		}
	}


	public RestTransferTO getRestTO(String baseUrl, String relativeUrl, String mode, HttpMethod httpMethod,
									Object obj, String consumer, String consumerKey) throws Exception {
		RestTransferTO restTO = new RestTransferTO();
		Map<String, Object> parameters = new HashMap<String, Object>();

		MDC.put("trackId", "Generic_Assignment");
		parameters.put(ONE_SPACE, ONE_SPACE);
		restTO.setParameters(parameters);
		if (GenUtil.isBlankString(relativeUrl)) {
			relativeUrl = "/" + mode;
			restTO.setRestRelativeURL(relativeUrl);
		} else {
			restTO.setRestRelativeURL(relativeUrl + "/" + mode);
		}
		restTO.setHttpMethod(httpMethod);
		restTO.setHttpSecurityType(RestConstant.HTTP_AUTH_SECURITY_TYPE);

		restTO.setContentTypee(MediaType.APPLICATION_JSON);

		Map<String, String> paramsMap = null;
		if (obj instanceof GenericAssignmentTransactionVO) {
			GenericAssignmentTransactionVO txVo = (GenericAssignmentTransactionVO) obj;
			if (txVo != null) {
				paramsMap = new HashMap<String, String>();
				paramsMap.put(GenericAssignmentConstants.SOURCE_COURSE_ID, String.valueOf(txVo.getSourceCourseId()));
				paramsMap.put(GenericAssignmentConstants.SOURCE_SECTION_ID, String.valueOf(txVo.getSourceSectionId()));
				paramsMap.put(GenericAssignmentConstants.SOURCE_ASSIGNMENT_ID, String.valueOf(txVo.getSourceAssignmentId()));
				paramsMap.put(GenericAssignmentConstants.DESTINATION_COURSE_ID, String.valueOf(txVo.getDestinationCourseId()));
				paramsMap.put(GenericAssignmentConstants.DESTINATION_SECTION_ID, String.valueOf(txVo.getDestinationSectionId()));
				paramsMap.put(GenericAssignmentConstants.DESTINATION_ASSIGNMENT_ID, String.valueOf(txVo.getDestinationAssignmentId()));
				paramsMap.put(GenericAssignmentConstants.PRIMARY_INSTRUCTOR, txVo.getPrimaryInstructorId());
				paramsMap.put(GenericAssignmentConstants.SECONDARY_INSTRUCTOR_ID, txVo.getSecondaryInstructorId());
				paramsMap.put(GenericAssignmentConstants.COURSE_TIME_ZONE, txVo.getCourseTimeZone());
				paramsMap.put(GenericAssignmentConstants.COURSE_TIME_ZONE_OFFSET, txVo.getCourseTimeZoneOffset());
				paramsMap.put(GenericAssignmentConstants.ISBN, txVo.getIsbn());
				paramsMap.put(GenericAssignmentConstants.ASSIGNMENT_START_DATE, txVo.getAssignmentStartDate());
				paramsMap.put(GenericAssignmentConstants.ASSIGNMENT_DUE_DATE, String.valueOf(txVo.getAssignmentDueDate()));
				paramsMap.put(GenericAssignmentConstants.MESSAGE, mode);
			}
		}

	// Added baseUrl to restTO and commented restService.setBaseURL
        restTO.setBaseUrl(baseUrl);
        restTO.setPopulateHeaderParams(true);

		Map<String, String> headerParams = getHeaderParams(httpMethod.name(), baseUrl, relativeUrl, consumer, consumerKey, paramsMap);
		String requestJson = getJsonString(paramsMap);
        restTO.setRequestType(requestJson);
        restTO.setHeaderParams(headerParams);

        logger.info("Before calling Rest service value set in RestTransferTO object : {}, action {} and base URL {}",
				new Object[] { restTO, mode, baseUrl });

        return restTO;
	}

	public Map<String, String> getHeaderParams(String httpMethod, String baseUrl, String relativeUrl, String consumer,
											   String consumerKey, Map<String, String> requestParamsMap) throws Exception {
		Map<String, String> headerParams = new HashMap<String, String>();
		// below part is included to send oAuth compliant request in copy/share flow
		OAuthConsumerDetails oAuthDetails = genericAssignmentsDao.getConsumerDetail(consumer);
		if (oAuthDetails != null) {
			/**
			 * We should not be needing this, but for Muzzy Lane the consumer_key has
			 * different values in APP_PROPERTIES AND OAPI_OAUTH_CONFIG tables, the former
			 * is used to make LTI calls and the latter is used to identify the LMS. Not
			 * setting this will throw a forbidden error from muzzy lane side because they
			 * expect the consumerKey as mentioned in APP_PROPERTIES but we fetched the
			 * OAuth details from OAPI_OAUTH_CONFIG
			 */
			if (GenericAssignmentConstants.MUZZY_LANE.equalsIgnoreCase(consumer)) {
				oAuthDetails.setConsumerKey(consumerKey);
			}

			long timeStamp = System.currentTimeMillis() / THOUSAND;
			String nonce = UUID.randomUUID().toString().replaceAll("-", "");
			requestParamsMap.put(RestConstant.OAUTH_CONSUMER_KEY, consumerKey);
			requestParamsMap.put(RestConstant.OAUTH_SIGNATURE_METHOD, oAuthDetails.getAlgorithm());
			requestParamsMap.put(RestConstant.OAUTH_TIMESTAMP, timeStamp + "");
			requestParamsMap.put(RestConstant.OAUTH_NONCE, nonce);
			requestParamsMap.put(RestConstant.OAUTH_VERSION, oAuthDetails.getVersion());

			String oAuthSignature = OAuthUtil.generateSign(httpMethod, consumerKey, oAuthDetails.getSecret(), requestParamsMap, baseUrl + relativeUrl);
			logger.debug("generated OAuth signature : {}", oAuthSignature);

			String oauthHeader = GenericAssignmentConstants.getAuthValue(consumerKey, oAuthSignature, nonce, String.valueOf(timeStamp), oAuthDetails.getAlgorithm(),
					oAuthDetails.getVersion());

			logger.debug("oAuth header value : {}", oauthHeader);
			headerParams.put(RestConstant.AUTHORIZATION_HEADER_KEY, oauthHeader);
		}
		return headerParams;
	}

	public <T> String getJsonString(T value) {
		String json = null;
		try {
			json = mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			logger.warn("JsonProcessingException Json:" + value, e);
		} catch (Exception e) {
			logger.error("Exception Json:" + value, e);
		}
		return json;
	}
}
