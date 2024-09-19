package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.vo.AssignmentTO;
import io.mhe.assignmentcomponent.vo.CopyAssignmentTO;
import io.mhe.assignmentcomponent.vo.RestTransferTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IIntegrationRestService {
    void copyXWorkFlow(
            CopyAssignmentTO[] assignments) throws Exception;

    String pullRegistrationMultiple(AssignmentTO assignmentTO) throws Exception;

    String testRest() throws Exception;

    <T> ResponseEntity<T> callRestURL(RestTransferTO<T> restTO) throws Exception;

}
