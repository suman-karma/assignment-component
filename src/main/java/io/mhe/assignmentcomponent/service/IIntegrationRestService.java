package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.vo.AssignmentTO;
import io.mhe.assignmentcomponent.vo.CopyAssignmentTO;
import org.springframework.stereotype.Service;

@Service
public interface IIntegrationRestService {
    public void copyXWorkFlow(
            CopyAssignmentTO[] assignments) throws Exception;

    public String pullRegistrationMultiple(AssignmentTO assignmentTO) throws Exception;

    public String testRest() throws Exception;

}
