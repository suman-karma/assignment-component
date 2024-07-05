package io.mhe.assignmentcomponent;

import io.mhe.assignmentcomponent.service.AssignmentCopyService;
import io.mhe.assignmentcomponent.service.IAssignmentCopyService;
import io.mhe.assignmentcomponent.service.IIntegrationRestService;
import io.mhe.assignmentcomponent.vo.AssignmentTO;
import io.mhe.assignmentcomponent.vo.CopyAssignmentTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    @Qualifier("ASSESMENT")
    IAssignmentCopyService assignmentCopyService;

    @Autowired
    Environment environment;

    @Autowired
    private IIntegrationRestService iIntegrationRestService;

    @GetMapping("/rest")
    public String getInfoRest() {
        logger.info(" from app.properties app.prop.name {}",environment.getProperty("app.prop.name"));
        logger.info(" from env.properties {}",environment.getProperty("env.prop.name")); // getting null
        logger.info("System. getenv(String name) : {}",System. getenv("env.prop.name") );
        return "rest call in local ....";
    }

    @GetMapping("/hello")
    public String getInfo() {
        try {
            //getActivities();
            initCopy();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        logger.error("$$$$$$$$$$$$$$$$$$$$$$$$$ done ");
        return "copy assignment being tested..";

    }

    public void getActivities() throws Exception{
        Object obj = assignmentCopyService.getActivitiesForAssignment(150018l);
    }


    public void initCopy(){
        // prepare data. from json.
        CopyAssignmentTO srcAssignment = new CopyAssignmentTO(2148122255l,0,"117070","13570164090672413",null,"Proctored_test","Proctored_test", 516184258,516194522, 516184259,516194523, "EZTestOnline", "ASSESMENT",null,null,10.0f,new ArrayList<Long>(), "publish");
        long oldSectionID = 516184259l;
        long newSectionID = 516194523l;
        long[] origCategoryIds = {};
        long[] newCategoryIds = {};
        long newCourseId = 516194522l;
        long newSectionId = 516194523l;
        HashMap modulesMap = new HashMap();
        Map assignMap = new HashMap();;
        modulesMap.put("2154626681","2154659051");
        modulesMap.put("2154626680", "2154659050");

        // call the service.
        try{
            //iIntegrationRestService.copyXWorkFlow(new CopyAssignmentTO[] {srcAssignment} );
            assignmentCopyService.copyAssignment(srcAssignment,  oldSectionID,
                    newSectionID,
                    origCategoryIds,
                    newCategoryIds,
                    newCourseId,
                    newSectionId,
                    modulesMap,
                    assignMap);

        }catch(Exception e){
            logger.error("Exception while copying assignment ", e);
        }



    }


}
