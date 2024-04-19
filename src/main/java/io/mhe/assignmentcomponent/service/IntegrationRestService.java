package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.common.util.HashingUtil;
import io.mhe.assignmentcomponent.vo.*;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

@Service
public class IntegrationRestService implements IIntegrationRestService {
    @Autowired
    RestTemplate restTemplate;

    public void copyXWorkFlow(
            CopyAssignmentTO[] assignments) throws Exception {

        // get the max assignmnet copy count from config or set default if absent.
        //
        String url = "ezt server host "; // get it from env variable or config.

        StringBuffer thePostData = new StringBuffer();
        String xml = getCopyAssignmentXML(assignments);
        thePostData.append("todo=copyx");
        String md5String = HashingUtil.getDigest("transaction_id=" + assignments[0].assignmentId(),
                ""); // Configuration.getSystemValue("eztest.pass.phrase") get from env variable.
        thePostData.append("&transaction_id=").append(assignments[0].assignmentId());
        thePostData.append("&key=").append(md5String);
        thePostData.append("&xml=").append(URLEncoder.encode(xml));

        HttpEntity<String> request = new HttpEntity<String>(
                "");

        String productCreateResponse = restTemplate.postForEntity(url+thePostData.toString() , request, String.class).getBody();

        // check productCreateResponse response from ezto. TO DO

    }

    @Override
    public String pullRegistrationMultiple(AssignmentTO assignmentTO) throws Exception {
        // get the max assignmnet copy count from config or set default if absent.
        String url = "ezt server host "; // get it from env variable or config.

        String xml = getPullRegistrationXML(assignmentTO);
        StringBuffer thePostData = new StringBuffer();
        thePostData.append("todo=pullRegistrationMultiple");
        // transaction_id can be anything. it is used just for Malcolm's key validation purpose.
        String md5String = HashingUtil.getDigest("todo=pullRegistrationMultiple", "pass phase"); // Configuration.getSystemValue("eztest.pass.phrase") get from env variable.
        thePostData.append("&key=").append(md5String);
        thePostData.append("&xml=").append(xml);


        HttpEntity<String> request = new HttpEntity<String>(
                "");

        String productCreateResponse = restTemplate.postForEntity(url+thePostData.toString() , request, String.class).getBody();

        // check productCreateResponse response from ezto. TO DO


        return null;
    }


    private String getCopyAssignmentXML(
            CopyAssignmentTO[] assignments) throws Exception {
        Element txNode = new Element("transaction");
        txNode.setAttribute("name", "copyx");
        txNode.setAttribute("version", "3");
        txNode.setAttribute("transaction_id", "" + assignments[0].assignmentId());

        for (int i = 0; i < assignments.length; i++) {
            if (assignments[i].nativeAlaId() != null && !"".equals(assignments[i].nativeAlaId())) {
                Element assign = new Element("assignment");
                assign.setAttribute("id", "" + assignments[i].assignmentId());
                assign.setAttribute("original_nativeid", assignments[i].nativeAlaId());
                assign.setAttribute("new_title", assignments[i].newTitle());
                // adding user_id of the user where the assignment is going to be copied.
                assign.setAttribute("destination_owner", assignments[i].newPrimaryInstructorId());
                txNode.addContent(assign);
            }
        }

        XMLOutputter xmlOutputter = new XMLOutputter();
        return xmlOutputter.outputString(txNode);
    }

    private String getPullRegistrationXML(AssignmentTO assignmentTO) throws Exception {
        Element txNode = new Element("pullRegistrationMultiple");

        if (assignmentTO.nativeAlaId() != null && !"".equals(assignmentTO.nativeAlaId())) {
            Element assign = new Element("assignment");
            Element ezid = new Element("ezid");
            ezid.setText(assignmentTO.nativeAlaId());
            Element connectid = new Element("connectid");
            connectid.setText("" + assignmentTO.id());

            assign.addContent(ezid);
            assign.addContent(connectid);
            txNode.addContent(assign);
        }

        XMLOutputter xmlOutputter = new XMLOutputter();
        return xmlOutputter.outputString(txNode);

    }

}
