package com.salt_code.student_source.data;

import com.salt_code.student_source.aggregate.Student;
import com.salt_code.student_source.events.Event;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StudentDatabase {

    private final HashMap<UUID, ?> studentEvents = new HashMap<>();
    private final HashMap<UUID, Student> students = new HashMap<>();

    public int StreamsCount() {
        return 0;
    }

    public int StreamLength(UUID id) {
        return 0;
    }

    public void Append(Event event) {}

    public Optional<Student> getStudent(UUID studentId) {
        return Optional.empty();
    }

    public Optional<Student> getStudentView(UUID studentId) {
        return Optional.empty();
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students.values());
    }
}
