package io.mhe.assignmentcomponent.service.generic.service.async;

import io.mhe.assignmentcomponent.basiclti.consumersecret.api.OAuthConsumerDetails;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IGenericAssignmentRemoteServiceInvoker {
	boolean sendRestCallForGenericAssignment(Object object, String consumer, String baseUrl, String mode,
                                             Class<?> type, HttpMethod method, String consumerKey);


}
