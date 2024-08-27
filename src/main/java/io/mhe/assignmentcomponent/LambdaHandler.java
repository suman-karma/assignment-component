package io.mhe.assignmentcomponent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.ObjectCodec;
import com.google.gson.Gson;
import io.mhe.assignmentcomponent.service.EztAssignmentCopyService;
import io.mhe.assignmentcomponent.service.IAssignmentCopyService;
import io.mhe.assignmentcomponent.vo.CopyAssignmentEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class LambdaHandler implements RequestStreamHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(LambdaHandler.class);
    AnnotationConfigApplicationContext ctx = null;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        CopyAssignmentEvent copyEvent = objectMapper.readValue(input, CopyAssignmentEvent.class);

        logger.log("##### Event in lambda" + copyEvent);
        try{
            init();
            EztAssignmentCopyService assignmentCopyService = (EztAssignmentCopyService)ctx.getBean("ASSESMENT");
            //iIntegrationRestService.copyXWorkFlow(new CopyAssignmentTO[] {srcAssignment} );
            assignmentCopyService.copyAssignment(copyEvent.getSrcAssignment(),  copyEvent.getOldSectionID(),
                    copyEvent.getNewSectionID(),
                    copyEvent.getOrigCategoryIds(),
                    copyEvent.getNewCategoryIds(),
                    copyEvent.getNewCourseId(),
                    copyEvent.getNewSectionID(),
                    copyEvent.getModulesMap(),
                    copyEvent.getAssignMap(),
                    copyEvent.getCoursePrimaryInstructorId(),
                    copyEvent.getOldAndNewCategories(),
                    copyEvent.getOldAndNewOutcomes(),
                    copyEvent.isMarathon());

        }catch(Exception e){
            logger.log("Exception while copying assignment ");
            e.printStackTrace();
        }

    }

    private void init() {
        LOGGER.info("context initialization.. !");
        System.setProperty("ENV","QA_STAGING_AWS");
        this.ctx = new AnnotationConfigApplicationContext(LambdaHandler.class.getPackage().getName());
        ctx.getAutowireCapableBeanFactory().autowireBean(this);
    }

}