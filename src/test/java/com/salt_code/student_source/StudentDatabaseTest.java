package com.salt_code.student_source;

import com.salt_code.student_source.aggregate.Student;
import com.salt_code.student_source.data.StudentDatabase;
import com.salt_code.student_source.events.StudentCreated;
import com.salt_code.student_source.events.StudentUpdated;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDatabaseTest {

    private final StudentDatabase studentDatabase = new StudentDatabase();

    @Test
    public void missingStreamId_ShouldCreateNewStream() throws Exception {
        // Arrange
        var initialCount = studentDatabase.StreamsCount();
        var event = new StudentCreated(
                UUID.randomUUID(),
                "Adam West",
                "adam.west@mail.com",
                Instant.parse("1990-12-03"+"T00:00:00.000Z")
        );

        // Act
        studentDatabase.Append(event);

        // Assert
        var newCount = studentDatabase.StreamsCount();
        assertTrue( newCount > initialCount );
    }

    @Test
    public void sameStreamId_ShouldOnlyCreateOneStream() throws Exception {
        // Arrange
        var id = UUID.randomUUID();
        var initialCount = studentDatabase.StreamsCount();
        var firstEvent = new StudentCreated(
                id,
                "Adam West",
                "adam.west@mail.com",
                Instant.parse("1990-12-03"+"T00:00:00.000Z")
        );
        var secondEvent = new StudentUpdated(
                id,
                "Eve West",
                "eve.west@mail.com"
        );

        // Act
        studentDatabase.Append(firstEvent);
        studentDatabase.Append(secondEvent);

        // Assert
        var newCount = studentDatabase.StreamsCount();
        assertEquals(1, newCount - initialCount);
    }

    @Test
    public void differentStreamIds_ShouldCreateDifferentStreams() throws Exception {
        // Arrange
        var initialCount = studentDatabase.StreamsCount();
        var firstEvent = new StudentCreated(
                UUID.randomUUID(),
                "Adam West",
                "adam.west@mail.com",
                Instant.parse("1990-12-03"+"T00:00:00.000Z")
        );
        var secondEvent = new StudentUpdated(
                UUID.randomUUID(),
                "Eve West",
                "eve.west@mail.com"
        );

        // Act
        studentDatabase.Append(firstEvent);
        studentDatabase.Append(secondEvent);

        // Assert
        var newCount = studentDatabase.StreamsCount();
        assertTrue( newCount - initialCount > 1);
    }

    @Test
    public void sameStreamId_ShouldGenerateALongerStream() throws Exception {
        // Arrange
        var id = UUID.randomUUID();
        var firstEvent = new StudentCreated(
                id,
                "Adam West",
                "adam.west@mail.com",
                Instant.parse("1990-12-03"+"T00:00:00.000Z")
        );
        studentDatabase.Append(firstEvent);
        var initialCount = studentDatabase.StreamLength(id);

        var secondEvent = new StudentUpdated(
                id,
                "Eve West",
                "eve.west@mail.com"
        );

        // Act
        studentDatabase.Append(secondEvent);

        // Assert
        var newCount = studentDatabase.StreamLength(id);
        assertTrue( newCount > initialCount);
    }

    @Test
    public void applyCreated_shouldGenerateStudent() throws Exception {
        // Arrange
        var id = UUID.randomUUID();
        var event = new StudentCreated(
                id,
                "Adam West",
                "adam.west@mail.com",
                Instant.parse("1990-12-03"+"T00:00:00.000Z")
        );
        var student = new Student();

        // Act
        student.Apply(event);

        // Assert
        assertEquals( event.getFullName(), student.getFullName() );
    }

    @Test
    public void getStudent_shouldGenerateStudent() throws Exception {
        // Arrange
        var id = UUID.randomUUID();
        var event = new StudentCreated(
                id,
                "Adam West",
                "adam.west@mail.com",
                Instant.parse("1990-12-03"+"T00:00:00.000Z")
        );

        studentDatabase.Append(event);

        // Act
        var student = studentDatabase.getStudent(id).orElse(null);

        // Assert
        assert student != null;
        assertEquals( event.getFullName(), student.getFullName() );
    }
    
    @Test
    public void getStudent_shouldGenerateStudent_fromMultipleEvents() throws Exception {
        // Arrange
        var id = UUID.randomUUID();
        var firstEvent = new StudentCreated(
                id,
                "Adam West",
                "adam.west@mail.com",
                Instant.parse("1990-12-03"+"T00:00:00.000Z")
        );
        var secondEvent = new StudentUpdated(
                id,
                "Eve West",
                "eve.west@mail.com"
        );

        studentDatabase.Append(firstEvent);
        studentDatabase.Append(secondEvent);

        // Act
        var student = studentDatabase.getStudent(id).orElse(null);

        // Assert
        assert student != null;
        assertEquals( secondEvent.getFullName(), student.getFullName() );
    }
}