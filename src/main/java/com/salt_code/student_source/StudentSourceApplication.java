package com.salt_code.student_source;

import com.salt_code.student_source.data.StudentDatabase;
import com.salt_code.student_source.events.StudentCreated;
import com.salt_code.student_source.events.StudentEnrolled;
import com.salt_code.student_source.events.StudentUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.util.UUID;

@SpringBootApplication
public class StudentSourceApplication {

	private static final Logger logger = LoggerFactory.getLogger(StudentSourceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StudentSourceApplication.class, args);
		logger.info("Go to http://localhost:8081/swagger-ui.html to see the endpoints");
	}

}
