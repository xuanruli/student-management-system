/**
 * Homework 8 Student Management System
 * @author Adam You
 * @author Xuanru Li
 * @author Mian Zhang
 */
 
 package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import courses.Course;
import roles.Professor;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ProfessorTest {

		
	private Professor professor;
	private List<Course> availableCourses;
	private ByteArrayOutputStream outputStream;
	private PrintStream originalOut;

		
	@BeforeEach
	void setUp() throws Exception {
	   // Create a new Professor instance for testing
	    professor = new Professor("John Smith", "001", "jsmith", "password");

	        // Create a list of available courses
	        availableCourses = new ArrayList<>();
	        availableCourses.add(new Course("CIS101", "Intro to Computer Science", "John Smith", "MW", "10:00", "11:30", 50));
	        availableCourses.add(new Course("CIS202", "Data Structures", "John Smith", "TR", "13:00", "14:30", 40));

	        // Set the available courses for the professor
	        professor.setAvailableCourses(availableCourses);
	    }
	
	  @Test
		void testProfessor() {
			
			String expectedOutput = "CIS101; Intro to Computer Science; John Smith; MW; 10:00 - 11:30; 50\n" +
	                 "CIS202; Data Structures; John Smith; TR; 13:00 - 14:30; 40\n";
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outputStream));
	        
	        // Testcase 1: Create a professor with empty name
	        Professor professor1 = new Professor("", "001", "jsmith", "password");
	        assertNotNull(professor1); // Ensure that the professor object is created

	        // Testcase 2: Create a professor with null ID
	        Professor professor2 = new Professor("John Smith", null, "jsmith", "password");
	        assertNotNull(professor2); // Ensure that the professor object is created

	        // Testcase 3: Create a professor with empty username
	        Professor professor3 = new Professor("John Smith", "001", "", "password");
	        assertNotNull(professor3); // Ensure that the professor object is created

	        // Testcase 4: Create a professor with null password
	        Professor professor4 = new Professor("John Smith", "001", "jsmith", null);
	        assertNotNull(professor4); // Ensure that the professor object is created
	        
	        // Testcase 5: Create a professor with valid information
	        Professor professor5 = new Professor("Alice Johnson", "002", "ajohnson", "securepass");
	        assertNotNull(professor5); // Ensure that the professor object is created
	        assertEquals("Alice Johnson", professor5.getName());
	        assertEquals("002", professor5.getId());
	        assertEquals("ajohnson", professor5.getUsername());
	        assertEquals("securepass", professor5.getPassword());

		}

	   @Test
	    public void testViewAllCourses() {
	        // Create a list of courses
	        List<Course> allCourses = new ArrayList<>();
	        Course course1 = new Course("CIS101", "Introduction to Computer Science", "Prof. Smith", "Mon", "09:00", "10:30", 30);
	        Course course2 = new Course("MATH201", "Calculus I", "Prof. Johnson", "Mon", "10:45", "12:15", 40);
	        allCourses.add(course1);
	        allCourses.add(course2);

	        // Redirect the standard output to capture printed output
	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outContent));

	        // Call the viewAllCourses method and capture the printed output
	        Course.viewAllCourses(allCourses);
	        String expectedOutput = "CIS101|Introduction to Computer Science, 09:00-10:30 on Mon, with course capacity: 30, students: 0, lecturer: Prof. Smith\n" +
	                                "MATH201|Calculus I, 10:45-12:15 on Mon, with course capacity: 40, students: 0, lecturer: Prof. Johnson\n";

	        // Reset the standard output
	        System.setOut(System.out);

	        // Test if the captured output matches the expected output
	        assertEquals(expectedOutput, outContent.toString());
	    }
        @Test
        public void testHasTimeConflictWithOverlap() {
            // Create two courses with overlapping schedules
            Course course1 = new Course("CIS101", "Introduction to Computer Science", "Prof. Smith", "Mon", "09:00", "10:30", 30);
            Course course2 = new Course("MATH201", "Calculus I", "Prof. Johnson", "Mon", "10:15", "11:45", 40);

            // Test the hasTimeConflict method
            assertTrue(course1.hasTimeConflict(course2));
        }

        @Test
        public void testHasTimeConflictWithNoOverlap() {
            // Create two courses with non-overlapping schedules
            Course course1 = new Course("CIS101", "Introduction to Computer Science", "Prof. Smith", "Mon", "09:00", "10:30", 30);
            Course course2 = new Course("MATH201", "Calculus I", "Prof. Johnson", "Mon", "11:00", "12:30", 40);

            // Test the hasTimeConflict method
            assertFalse(course1.hasTimeConflict(course2));
        }

        @Test
        public void testHasTimeConflictWithDifferentDays() {
            // Create two courses on different days
            Course course1 = new Course("CIS101", "Introduction to Computer Science", "Prof. Smith", "Mon", "09:00", "10:30", 30);
            Course course2 = new Course("MATH201", "Calculus I", "Prof. Johnson", "Tue", "09:00", "10:30", 40);

            // Test the hasTimeConflict method
            assertFalse(course1.hasTimeConflict(course2));
 
        
	}      
      
}
	
