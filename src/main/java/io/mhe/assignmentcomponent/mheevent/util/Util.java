package io.mhe.assignmentcomponent.mheevent.util;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    private final static Logger logger = LoggerFactory.getLogger(Util.class);

    /**
     * @param key
     * @param defaultValue
     * @return Integer Value from LAMBDA configuration
     */
    public static int getConfigValue(String key, int defaultValue) {
        String configValue = System.getenv(key);
        try {
            return Integer.parseInt(configValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * @param key
     * @param defaultValue
     * @return String Value from LAMBDA configuration
     */
    public static String getConfigValue(String key, String defaultValue) {
        String configValue = System.getenv(key);
        if (StringUtils.isEmpty(configValue))
            return defaultValue;
        else
            return configValue;

    }


    /**
     * @param key
     * @return String Value from LAMBDA configuration
     */
    public static String getConfigValue(String key) {
        return System.getenv(key);
    }


    /**
     * @param key
     * @param defaultValue
     * @return Integer Value from LAMBDA configuration
     */
    public static int getConfigValueAsInteger(String key) {
        int defaultValue = 0;
        String configValue = System.getenv(key);
        try {
            return Integer.parseInt(configValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static List<String> getAssignmentTypeFromConfig() {
        String assignmentTypesToFg = System.getenv("ASSIGNMENT_TYPES_TO_FG");
        List<String> assignmentTypesList = new ArrayList<String>();
        List<String> assignmentTypesConfigured = Arrays.asList(assignmentTypesToFg.split(","));
        for(String assignmentType : assignmentTypesConfigured){
            if (EnumUtils.getEnum(AssignmentType.class, assignmentType) != null) {
                assignmentTypesList.add(assignmentType);
            }else{
                logger.error("Unknown assignment type : {}",assignmentType);
            }
        }
        logger.debug("assignmentTypesList from Config {}", assignmentTypesList);
        return assignmentTypesList;
    }

}
