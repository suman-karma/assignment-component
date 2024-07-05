package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.common.util.HashingUtil;
import io.mhe.assignmentcomponent.vo.*;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

@Service
public class IntegrationRestService implements IIntegrationRestService {
    private final Logger logger = LoggerFactory.getLogger(IntegrationRestService.class);

    //@Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Value("${eztest.hm.server.url}")
    String eztestUrl ;
    @Value("${eztest.pass.phrase}")
    String eztPassPhase;


    public void copyXWorkFlow(
            CopyAssignmentTO[] assignments) throws Exception {

        // get the max assignmnet copy count from config or set default if absent.
        //
        //String url = "ezt server host "; // get it from env variable or config.

        StringBuffer thePostData = new StringBuffer();
        String xml = getCopyAssignmentXML(assignments);
        thePostData.append("todo=copyx");
        String md5String = HashingUtil.getDigest("transaction_id=" + assignments[0].assignmentId(),
                eztPassPhase);
        thePostData.append("&transaction_id=").append(assignments[0].assignmentId());
        thePostData.append("&key=").append(md5String);
       // thePostData.append("&xml=").append(URLEncoder.encode(xml));
        thePostData.append("&xml=").append(xml);

        logger.info(xml);
        logger.info("### calling ezt copyx {} eztest.pass.phrase {}",eztestUrl+"?"+thePostData.toString() , eztPassPhase);
        String productCreateResponse = restTemplate.getForObject(eztestUrl+"?"+thePostData.toString() ,  String.class);

        logger.info("### respons from ezto : {}", productCreateResponse);
        // check productCreateResponse response from ezto. TO DO

    }

    @Override
    public String pullRegistrationMultiple(AssignmentTO assignmentTO) throws Exception {
        // get the max assignmnet copy count from config or set default if absent.
        //String url = "ezt server host "; // get it from env variable or config.

        String xml = getPullRegistrationXML(assignmentTO);
        StringBuffer thePostData = new StringBuffer();
        thePostData.append("todo=pullRegistrationMultiple");
        // transaction_id can be anything. it is used just for Malcolm's key validation purpose.
        String md5String = HashingUtil.getDigest("todo=pullRegistrationMultiple", eztPassPhase);
        thePostData.append("&key=").append(md5String);
        thePostData.append("&xml=").append(xml);


        HttpEntity<String> request = new HttpEntity<String>(
                "");

        String productCreateResponse = restTemplate.postForEntity(eztestUrl+thePostData.toString() , request, String.class).getBody();
        logger.info("pull registration multiple productCreateResponse {}",productCreateResponse);
        // check productCreateResponse response from ezto. TO DO


        return null;
    }

    @Override
    public String testRest() throws Exception {
        //String productCreateResponse = restTemplate.getForObject("http://localhost:8080/rest?todo=copyx&transaction_id=2148122255&key=aad6ac410ac4ce43807ff5579503b02a&xml=%3Ctransaction+name%3D%22copyx%22+version%3D%223%22+transaction_id%3D%222148122255%22%3E%3Cassignment+id%3D%222148122255%22+original_nativeid%3D%2213570164090672413%22+new_title%3D%22Proctored_test%22+destination_owner%3D%22117070%22+%2F%3E%3C%2Ftransaction%3E" ,  String.class);
        //String productCreateResponse = restTemplate.getForObject("https://ezto-qas.mheducation.com/hm.tpx?todo=copyx&transaction_id=2148122255&key=aad6ac410ac4ce43807ff5579503b02a&xml=%3Ctransaction+name%3D%22copyx%22+version%3D%223%22+transaction_id%3D%222148122255%22%3E%3Cassignment+id%3D%222148122255%22+original_nativeid%3D%2213570164090672413%22+new_title%3D%22Proctored_test%22+destination_owner%3D%22117070%22+%2F%3E%3C%2Ftransaction%3E",  String.class);




        String productCreateResponse = restTemplate.getForObject("https://ezto-qas.mheducation.com/hm.tpx?todo=copyx&transaction_id=2148122255&key=aad6ac410ac4ce43807ff5579503b02a&xml=%3Ctransaction+name%3D%22copyx%22+version%3D%223%22+transaction_id%3D%222148122255%22%3E%3Cassignment+id%3D%222148122255%22+original_nativeid%3D%2213570164090672413%22+new_title%3D%22Proctored_test%22+destination_owner%3D%22117070%22+%2F%3E%3C%2Ftransaction%3E",  String.class);

        logger.info("@@@@@ productCreateResponse {} ",productCreateResponse);
        return productCreateResponse;
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
