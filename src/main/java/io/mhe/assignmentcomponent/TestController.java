package io.mhe.assignmentcomponent;

import com.google.gson.Gson;
import io.mhe.assignmentcomponent.service.AssignmentCopyService;
import io.mhe.assignmentcomponent.service.IAssignmentCopyService;
import io.mhe.assignmentcomponent.service.IIntegrationRestService;
import io.mhe.assignmentcomponent.vo.AssignmentTO;
import io.mhe.assignmentcomponent.vo.CopyAssignmentEvent;
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
        //CopyAssignmentTO srcAssignment = new CopyAssignmentTO(2148122255l,0,"117070","13570164090672413",null,"Proctored_test","Proctored_test", 516184258,516194522, 516184259,516194523, "EZTestOnline", "ASSESMENT",null,null,10.0f,new ArrayList<Long>(), "publish");
        //long oldSectionID = 516184259l;
        //long newSectionID = 516194523l;
        //long[] origCategoryIds = {};
        //long[] newCategoryIds = {};
       // long newCourseId = 516194522l;
        long newSectionId = 516194523l;
        HashMap modulesMap = new HashMap();
        Map assignMap = new HashMap();
        modulesMap.put("2154626681","2154659051");
        modulesMap.put("2154626680", "2154659050");

        String eventJson = "{\"srcAssignment\":{\"assignmentId\":2148122255,\"newAssignmentId\":0,\"newPrimaryInstructorId\":\"117070\",\"nativeAlaId\":\"13570164090672413\",\"title\":\"Proctored_test\",\"newTitle\":\"Proctored_test\",\"courseId\":516184258,\"newCourseId\":516194522,\"sectionId\":516184259,\"newSectionId\":516194523,\"provider\":\"EZTestOnline\",\"type\":\"ASSESMENT\",\"weight\":10.0,\"assignmetnLineItemIds\":[],\"parentAssignmentStatus\":\"publish\"},\"oldSectionID\":516184259,\"newSectionID\":516194523,\"newCourseId\":516194522,\"newSectionId\":516194523,\"modulesMap\":{\"2154626681\":\"2154659051\",\"2154626680\":\"2154659050\"},\"assignMap\":{}}";

        CopyAssignmentEvent copyEvent = new CopyAssignmentEvent();
        copyEvent.setSrcAssignment(new CopyAssignmentTO(2148122255l,0,"117070","13570164090672413",null,"Proctored_test","Proctored_test", 516184258,516194522, 516184259,516194523, "EZTestOnline", "ASSESMENT",null,null,10.0f,new ArrayList<Long>(), "publish"));
        copyEvent.setOldSectionID(516184259l);
        copyEvent.setNewSectionID(516194523l);
        //copyEvent.setNewCourseId(516194522l);
        //copyEvent.setNewSectionId(516194523l);
        copyEvent.setAssignMap(assignMap);
        copyEvent.setModulesMap(modulesMap);

        Gson gson = new Gson();
        logger.info("----------- copy enent -----------");
        logger.info(gson.toJson(copyEvent));
        logger.info(" object from json. "+ (CopyAssignmentEvent) gson.fromJson(eventJson,CopyAssignmentEvent.class));
        logger.info("-----------xxxxxxxxx -----------");
        // call the service.
        try{
            //iIntegrationRestService.copyXWorkFlow(new CopyAssignmentTO[] {srcAssignment} );
            assignmentCopyService.copyAssignment(copyEvent.getSrcAssignment(),  copyEvent.getOldSectionID(),
                    copyEvent.getNewSectionID(),
                    copyEvent.getOrigCategoryIds(),
                    copyEvent.getNewCategoryIds(),
                    copyEvent.getNewCourseId(),
                    copyEvent.getNewSectionID(),
                    copyEvent.getModulesMap(),
                    copyEvent.getAssignMap());

        }catch(Exception e){
            logger.error("Exception while copying assignment ", e);
        }



    }


}
