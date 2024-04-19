package io.mhe.assignmentcomponent.mheevent.util;

public enum AssignmentType {
    ADAPTIVELEARNINGASSIGNMENT("ADAPTIVELEARNINGASSIGNMENT"),
    ALE("ALE"),
    APRASSIGNMENT("APRASSIGNMENT"),
    ASSESMENT("ASSESMENT"),
    AVALON("AVALON"),
    BLOG("BLOG"),
    DISCUSSION("DISCUSSION"),
    EHRCASE("EHRCASE"),
    EHRCLINIC("EHRCLINIC"),
    FILEATTACH("FILEATTACH"),
    GOREACT("GOREACT"),
    GROUP("GROUP"),
    MUZZY_LANE("MUZZY_LANE"),
    QUEST("QUEST"),
    READER17("READER17"),
    URL_BASED("URL_BASED"),
    VIDEO("VIDEO"),
    VIRTUALLABS("VIRTUALLABS"),
    WRITING("WRITING"),
    WRITING2("WRITING2"),
    ;

    private String assignmentType;

    private AssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    private String getValue() {
        return assignmentType;
    }

}
