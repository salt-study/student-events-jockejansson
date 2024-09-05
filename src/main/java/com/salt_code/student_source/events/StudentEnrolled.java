package com.salt_code.student_source.events;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public final class StudentEnrolled extends Event {

    private final String courseName;

    public StudentEnrolled(UUID studentID, String courseName) {
        super(studentID);
        this.courseName = courseName;
    }
}
