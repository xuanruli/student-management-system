
/**
 * Homework 8 Student Management System
 * @author Adam You
 * @author Xuanru Li
 * @author Mian Zhang
 */
 
package test;
import courses.Course;
import roles.Student;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ControllerTest {
    // Output stream to capture the console output for testing purposes
    private ByteArrayOutputStream output;
    
    // This method is executed before each test. It sets up the output stream.
    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    // Test to verify if the updateEnrolledStudents method correctly updates the enrolled students list
	@Test
	void testUpdateEnrolledStudents() {
        // Creating a student and two courses for testing
		Student student = new Student("S001", "Alice", "alice123", "password");
		Course course1 = new Course("C001", "Math", "John Doe", "Monday", "09:00", "11:00", 30);
		Course course2 = new Course("C002", "Science", "Jane Smith", "Wednesday", "10:00", "12:00", 25);
		
        // Enrolling the student in the first course
		course1.getEnrolledStudents().add(student);
        // Asserting that the student is now enrolled in the course
		assertTrue(course1.getEnrolledStudents().contains(student));  
	}
	// Test to check the functionality of dropping a course by a student
	@Test
	void testStudentCourseDrop() {
        // Creating two courses and a student for testing
	    Course course1 = new Course("C001", "Math", "John Doe", "Monday", "09:00", "11:00", 30);
	    Course course2 = new Course("C002", "Science", "Jane Smith", "Wednesday", "10:00", "12:00", 25);

	    Student student = new Student("S001", "Alice", "alice123", "password");

        // Enrolling the student in the first course
	    course1.getEnrolledStudents().add(student);
	    assertTrue(course1.getEnrolledStudents().contains(student));

        // Removing the student from the enrolled students list of the course
	    course1.getEnrolledStudents().remove(student);
	    assertFalse(course1.getEnrolledStudents().contains(student));
	}


}
