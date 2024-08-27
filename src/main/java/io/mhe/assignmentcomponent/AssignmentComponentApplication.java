package io.mhe.assignmentcomponent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AssignmentComponentApplication {

public static void main(String[] args) {

	System.setProperty("ENV","QA_STAGING_AWS");
	SpringApplication.run(AssignmentComponentApplication.class, args);
}

}
