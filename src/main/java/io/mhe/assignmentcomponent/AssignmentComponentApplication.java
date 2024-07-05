package io.mhe.assignmentcomponent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class AssignmentComponentApplication {

public static void main(String[] args) {
		SpringApplication.run(AssignmentComponentApplication.class, args);
}

}
// (exclude = {DataSourceAutoConfiguration.class })