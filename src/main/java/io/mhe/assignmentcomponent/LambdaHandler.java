package io.mhe.assignmentcomponent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class LambdaHandler implements RequestHandler<Map<String,String>, Void>{


    public Void handleRequest(Map<String, String> event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("EVENT TYPE: " + event.getClass());
        logger.log("This is the lambda to copy assignemnts in a section... ");





        return null;

    }
}