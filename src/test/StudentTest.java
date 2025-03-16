
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
import roles.Student;

import java.util.ArrayList;
import java.util.List;


class StudentTest {
    private Student student;
    private List<Course> courses;

    @BeforeEach
    void setUp() {
        // Initialize a new student and a list of available courses
        student = new Student("1", "John Doe", "johndoe", "password");
        courses = new ArrayList<>();

        Course course1 = new Course("CIT590", "Introduction to Programming", "Dr. Smith", "MWF", "10:00 AM", "11:30 AM", 30);
        Course course2 = new Course("CIT591", "Data Structures", "Prof. Johnson", "TTh", "1:00 PM", "2:30 PM", 25);
        Course course3 = new Course("CIT592", "Java", "Prof. Zhang", "MWF", "10:00 AM", "11:30 AM", 3);
        Course course4 = new Course("ENG101", "English Composition", "Prof. Davis", "MWF", "9:00 AM", "10:30 AM", 20);
        Course course5 = new Course("MATH200", "Calculus I", "Prof. Wilson", "TTh", "3:00 PM", "4:30 PM", 35);
        Course course6 = new Course("CHEM101", "Chemistry 101", "Dr. Brown", "MWF", "1:00 PM", "2:30 PM", 25);
        Course course7 = new Course("PHYS200", "Physics II", "Prof. White", "TTh", "10:00 AM", "11:30 AM", 30);

        // Add the sample courses to the list of available courses
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        courses.add(course6);
        courses.add(course7);
        

        // Set the available courses for the student
        student.setAvailableCourses(courses);
    }

    @Test
    void testAddCourseGrade() {
        
    	// Test Case 1: Add a grade for a course
        student.addCourseGrade("CIT590", "A");
        // Check if the grade has been added successfully
        assertEquals("A", student.getGradeForCourse("CIT590"));
        
        
        // Test Case 2: Add a grade for an invalid course
        // Ensure that adding a grade for an invalid course returns null
        String initialGrade = student.getGradeForCourse("InvalidCourse");
        String finalGrade = student.getGradeForCourse("InvalidCourse");
        assertEquals(initialGrade, finalGrade);
        
        // Test Case 3: Attempt to add a grade with an invalid grade value
        // Ensure that the course grade is not modified
        String initialGrade2 = student.getGradeForCourse("CIT591");
        assertEquals(null, initialGrade2);
    }
    
    
    @Test
    void testAddCourse() {
        
    	// Test Case 1: Test adding a course to the student's schedule
        student.addCourse("CIT590", courses);
        // Check if the student is enrolled in the added course
        assertTrue(student.checkCourseEnrollment("CIT590"));
     
        
        // Test Case 2: Course Already Enrolled 
        // Enroll the student in a course
        student.addCourse("CIT591", courses);
        // Try adding the same course again
        student.addCourse("CIT591", courses);
        // Check if the student is still enrolled in the course (should not be added again)
        assertEquals(7, student.getAvailableCourses().size());
        
        // Test Case 3: Test Add Course Non Existent
        // Try adding a course that does not exist in the available courses
        student.addCourse("CIT123", courses);
        // Check that the student is not enrolled in any courses
        assertEquals(7, student.getAvailableCourses().size());
        
        
        // Test Case 4: Test Course with time conflict 
        // Enroll the student in a course that has a time conflict with another course
        student.addCourse("CIT590", courses);
        // Try adding a course with a time conflict
        student.addCourse("CIT592", courses);
        // Check that the student is not enrolled in the conflicting course
        assertFalse(student.checkCourseEnrollment("CIT592"));
   
    }
    

    @Test
    void testDropCourse() {
    	//Test Case 1: Test Drop Course With Enrolled Course
    	// Add a course to the student's schedule
        student.addCourse("CIT590", courses);
        // Drop the added course
        student.dropCourse("CIT590");
        // Check if the course has been dropped successfully
        assertFalse(student.checkCourseEnrollment("CIT590"));
        
        
        // //Test Case 2: Test Drop Course With Enrolled Course Count
        // Add a course to the student's schedule
        student.addCourse("CIT590", courses);
        // Drop the added course
        student.dropCourse("CIT590");
        // Check if the course has been dropped successfully
        assertFalse(student.checkCourseEnrollment("CIT590"));
        
        
        //Test Case 3:DropCourseWithNonEnrolledCourse
        // Attempt to drop a course that is not in the student's schedule
        student.dropCourse("NonEnrolledCourse");
        // Ensure that no courses have been dropped
        assertTrue(student.getCourseGrades().isEmpty());
        
        
        //Test Case 4: Test Drop Course With Multiple Courses
        student.addCourse("ENG101", courses);
        student.addCourse("MATH200", courses);
        student.addCourse("CHEM101", courses);
        //Drop one of the enrolled courses
        student.dropCourse("ENG101");
        // Check if the dropped course has been removed, and others are still enrolled
        assertFalse(student.checkCourseEnrollment("ENG101"));
        assertTrue(student.checkCourseEnrollment("MATH200"));
        assertTrue(student.checkCourseEnrollment("CHEM101"));
    }
   
    

    @Test
    void testCheckCourseEnrollment() {
        // Test Case 1: Add a course to the student's schedule
        student.addCourse("CIT590", courses);
        // Check if the student is enrolled in the added course
        assertTrue(student.checkCourseEnrollment("CIT590"));
        
        
        // Test Case 2: Check if the student is not enrolled in a course they haven't added
        assertFalse(student.checkCourseEnrollment("CIT591"));
        
        
        // Test Case 3: Add multiple courses to the student's schedule
        student.addCourse("CIT590", courses);
        student.addCourse("CIT591", courses);
        // Check if the student is enrolled in both courses
        assertTrue(student.checkCourseEnrollment("CIT590"));
        assertTrue(student.checkCourseEnrollment("CIT591"));
        
        // Test Case 4:Check if the student is not enrolled in any course initially
        assertFalse(student.checkCourseEnrollment("CIT600"));
        assertFalse(student.checkCourseEnrollment("ANTH590"));
        
    }
}
