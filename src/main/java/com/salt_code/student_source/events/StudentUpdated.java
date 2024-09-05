package com.salt_code.student_source.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public final class StudentUpdated extends Event {

    private final String fullName;
    private final String email;

    public StudentUpdated(UUID studentID, String fullName, String email) {
        super(studentID);
        this.fullName = fullName;
        this.email = email;
    }
}