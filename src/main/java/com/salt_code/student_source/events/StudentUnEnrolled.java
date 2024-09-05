package com.salt_code.student_source.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public final class StudentUnEnrolled extends Event {

    private final String courseName;

    public StudentUnEnrolled(UUID studentID, String courseName) {
        super(studentID);
        this.courseName = courseName;
    }
}
