package com.salt_code.student_source.controller;

import com.salt_code.student_source.aggregate.Student;
import com.salt_code.student_source.data.StudentDatabase;
import com.salt_code.student_source.dto.CreateStudentRequest;
import com.salt_code.student_source.dto.UpdateStudentRequest;
import com.salt_code.student_source.events.StudentEnrolled;
import com.salt_code.student_source.events.StudentUnEnrolled;
import com.salt_code.student_source.events.StudentUpdated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentsController {

    private static final String CONTEXT_ROOT = "/api/students/";
    private final StudentDatabase studentDatabase;

    public StudentsController(StudentDatabase studentDatabase) {
        this.studentDatabase = studentDatabase;
    }

    @GetMapping
    public List<Student> listStudents() {
        return studentDatabase.getStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentView(@PathVariable UUID id) {
        return studentDatabase.getStudentView(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<Student> getStudent(@PathVariable UUID id) {
        return studentDatabase.getStudent(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createStudent(@RequestBody CreateStudentRequest request) {

        var event = request.toStudentCreated();
        studentDatabase.Append(event);

        return ResponseEntity.created(URI.create(CONTEXT_ROOT + event.getStudentID())).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable UUID id, @RequestBody UpdateStudentRequest request) {

        var event = new StudentUpdated(
                id,
                request.fullName(),
                request.email()
        );
        studentDatabase.Append(event);

        return getStudentView(id);
    }

    @PatchMapping("/{id}/enroll")
    public ResponseEntity<Student> enrollStudent(@PathVariable UUID id, @RequestBody String course) {

        var event = new StudentEnrolled(id, course);
        studentDatabase.Append(event);

        return getStudentView(id);
    }

    @PatchMapping("/{id}/unenroll")
    public ResponseEntity<Student> unEnrollStudent(@PathVariable UUID id, @RequestBody String course) {

        var event = new StudentUnEnrolled(id, course);
        studentDatabase.Append(event);

        return getStudentView(id);
    }
}
