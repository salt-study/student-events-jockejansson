package com.salt_code.student_source.events;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public final class StudentCreated extends Event {

    private final String fullName;
    private final String email;
    private final Instant dateOfBirth;

    public StudentCreated(UUID studentID, String fullName, String email, Instant dateOfBirth) {
        super(studentID);
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public UUID getStudentID() {
        return getStreamId();
    }
}
