package io.mhe.assignmentcomponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AssignmentComponentApplication {
	private static final Logger logger = LoggerFactory.getLogger(AssignmentComponentApplication.class);

public static void main(String[] args) {
	System.setProperty("ENV",System.getenv("ENV"));
	SpringApplication.run(AssignmentComponentApplication.class, args);
}

}
