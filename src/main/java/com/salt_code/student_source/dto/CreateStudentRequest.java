package com.salt_code.student_source.dto;

import com.salt_code.student_source.events.StudentCreated;

import java.time.Instant;
import java.util.UUID;

public record CreateStudentRequest(
        String fullName,
        String email,
        Instant dateOfBirth
) {
    public StudentCreated toStudentCreated() {
        return new StudentCreated(
                UUID.randomUUID(),
                fullName,
                email,
                dateOfBirth
        );
    }
}
