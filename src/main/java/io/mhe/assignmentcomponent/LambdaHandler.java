package io.mhe.assignmentcomponent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mhe.sqs.transferObject.CopyPayload;
import com.mhe.sqs.transferObject.MessageTO;
import com.mhe.sqs.transferObject.Payload;
import com.mhe.sqs.utils.ObjectMapperUtility;
import io.mhe.assignmentcomponent.service.AssignmentCopyService;
import io.mhe.assignmentcomponent.service.EztAssignmentCopyService;
import io.mhe.assignmentcomponent.service.NonEztAssignmentCopyService;
import io.mhe.assignmentcomponent.vo.CopyAssignmentEvent;
import io.mhe.assignmentcomponent.vo.CopyAssignmentTO;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;


public class LambdaHandler implements RequestHandler<SQSEvent, Void> {
    private final Logger logger = LoggerFactory.getLogger(LambdaHandler.class);
    AnnotationConfigApplicationContext ctx = null;
   // private static final ObjectMapper objectMapper = new ObjectMapper();
    /*
    This method is not used with sqs
     */
    /*public void handleRequestStream(InputStream input, OutputStream output, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        CopyAssignmentEvent copyEvent = objectMapper.readValue(input, CopyAssignmentEvent.class);

        logger.log("##### Event in lambda" + copyEvent);
        try{
            AssignmentCopyService assignmentCopyService = null;
            init();
            if("ASSESMENT".equals(copyEvent.getType())){
                 assignmentCopyService = (EztAssignmentCopyService)ctx.getBean("ASSESMENT");
            } else {
                 assignmentCopyService = (NonEztAssignmentCopyService)ctx.getBean("NONASSESMENT");
            }

            assignmentCopyService.copyAssignmentsToNewSection(copyEvent,  copyEvent.getOldSectionID(),
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

    }*/



    private void init() {
        logger.info("context initialization.. !");
        System.setProperty("ENV","QA_STAGING_AWS");
        this.ctx = new AnnotationConfigApplicationContext(LambdaHandler.class.getPackage().getName());
        ctx.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        logger.info("##### Event in lambda SQSEvent " + sqsEvent);

        for(SQSEvent.SQSMessage msg : sqsEvent.getRecords()){
            String messageBody = new String(msg.getBody());
            MessageTO message = ObjectMapperUtility.getObject(messageBody, MessageTO.class);
            String decoded = new String(Base64.decodeBase64(message.getMessageBody().getBytes()));
            CopyPayload payLoad = ObjectMapperUtility.getObject(decoded, CopyPayload.class);
            com.mhe.sqs.transferObject.CopyAssignment copyEvent = payLoad.getCopyAssignmentEvent();
            logger.info("##### Event in lambda CopyAssignmentEvent" + copyEvent);
            try{
                AssignmentCopyService assignmentCopyService = null;
                init();
                if("ASSESMENT".equals(copyEvent.getType())){
                    assignmentCopyService = (EztAssignmentCopyService)ctx.getBean("ASSESMENT");
                } else {
                    assignmentCopyService = (NonEztAssignmentCopyService)ctx.getBean("NONASSESMENT");
                }

                CopyAssignmentTO copyAssignemnt = new CopyAssignmentTO(copyEvent.getAssignmentId(),copyEvent.getNewAssignmentId(),
                        copyEvent.getNewPrimaryInstructorId(), copyEvent.getNativeAlaId(),copyEvent.getNewNativeAlaId(),
                        copyEvent.getTitle(), copyEvent.getNewTitle(),copyEvent.getCourseId(),
                        copyEvent.getNewCourseId(),

                        copyEvent.getSectionId(),copyEvent.getNewSectionId(),

                        copyEvent.getProvider(),copyEvent.getType(),copyEvent.getCopyEZTStatus(),
                        copyEvent.getError(),copyEvent.getWeight(),copyEvent.getAssignmetnLineItemIds(),
                        copyEvent.getParentAssignmentStatus());

                assignmentCopyService.copyAssignmentsToNewSection(copyAssignemnt,  copyEvent.getSectionId(),
                        copyEvent.getNewSectionId(),
                        copyEvent.getOrigCategoryIds(),
                        copyEvent.getNewCategoryIds(),
                        copyEvent.getNewCourseId(),
                            copyEvent.getNewSectionId(),
                        copyEvent.getModulesMap(),
                        new HashMap(),
                        copyEvent.getCoursePrimaryInstructorId(),
                        copyEvent.getOldAndNewCategories(),
                        copyEvent.getOldAndNewOutcomes(),
                        copyEvent.isMarathon());

            }catch(Exception e){
                logger.error("Exception while copying assignment {}",copyEvent);
                e.printStackTrace();
            }
        }
        return null;
    }
}