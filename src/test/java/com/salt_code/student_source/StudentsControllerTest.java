package com.salt_code.student_source;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.salt_code.student_source.aggregate.Student;
import com.salt_code.student_source.controller.StudentsController;
import com.salt_code.student_source.data.StudentDatabase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.*;

@WebMvcTest(StudentsController.class)
public class StudentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentDatabase studentDatabase;

    private UUID studentId;
    private Student student;
    private final String studentName = "Adam West";
    private final String studentEmail = "adam.west@example.com";
    private final Instant studentBirthDate = Instant.parse("1990-12-03"+"T00:00:00.000Z");

    @BeforeEach
    public void setUp() {
        studentId = UUID.randomUUID();
        student = new Student();
        student.setId(studentId);
        student.setFullName(studentName);
        student.setEmail(studentEmail);
        student.setDateOfBirth(studentBirthDate);
    }

    @Test
    public void listStudents_ShouldReturnListOfStudents() throws Exception {
        // Arrange
        when(studentDatabase.getStudents()).thenReturn(List.of(student));
        // Act & Assert
        mockMvc.perform(get("/api/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(studentId.toString()))
                .andExpect(jsonPath("$[0].fullName").value(studentName))
                .andExpect(jsonPath("$[0].email").value(studentEmail));
    }

    @Test
    public void getStudentView_ShouldReturnStudent() throws Exception {
        // Arrange
        when(studentDatabase.getStudentView(studentId)).thenReturn(Optional.of(student));
        // Act & Assert
        mockMvc.perform(get("/api/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId.toString()))
                .andExpect(jsonPath("$.fullName").value(studentName))
                .andExpect(jsonPath("$.email").value(studentEmail));
    }

    @Test
    public void getStudentView_ShouldReturnNotFoundWhenStudentDoesNotExist() throws Exception {
        // Arrange
        when(studentDatabase.getStudentView(studentId)).thenReturn(Optional.empty());
        // Act & Assert
        mockMvc.perform(get("/api/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}