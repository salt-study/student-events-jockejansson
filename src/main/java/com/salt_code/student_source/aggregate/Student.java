package com.salt_code.student_source.aggregate;

import com.salt_code.student_source.events.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Getter @Setter
public class Student {
    private UUID id;
    private String fullName;
    private String email;
    private final Set<String> enrolledCourses = new HashSet<>();
    private Instant dateOfBirth;

    public void Apply(StudentCreated studentCreated) {}
}
