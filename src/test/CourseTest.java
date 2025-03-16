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
import java.util.ArrayList;
import java.util.List;


import courses.Course;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class CourseTest {
      private Course course1;
	  private Course course2;
	  private Course course3;
	  private Course course4;
	  private Course course5;

   @BeforeEach
   void setUp() throws Exception {
   course1 = new Course("C1", "Course 1", "Lecturer 1", "Monday", "09:00", "11:00", 30);
   course2 = new Course("C2", "Course 2", "Lecturer 2", "Monday", "10:00", "12:00", 25);
   course3 = new Course("C3", "Course 3", "Lecturer 3", "Tuesday", "13:00", "15:00", 20);
   course4 = new Course("C4", "Course 4", "Lecturer 4", "Monday", "14:00", "16:00", 15);
   course5 = new Course("C5", "Course 5", "Lecturer 5", "Wednesday", "09:00", "10:00", 40);
   
	  }

   @Test
   void testViewAllCourses() {
       List<Course> courses = new ArrayList<>();
		
       // Create some sample courses
       Course course1 = new Course("C1", "Course 1", "Lecturer 1", "Monday", "09:00", "11:00", 30);
       Course course2 = new Course("C2", "Course 2", "Lecturer 2", "Tuesday", "10:00", "12:00", 25);
       Course course3 = new Course("C3", "Course 3", "Lecturer 3", "Wednesday", "13:00", "15:00", 20);
       
       // Add the courses to the list
       courses.add(course1);
       courses.add(course2);
       courses.add(course3);
       
       // Redirect the standard output to capture the printed content
       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
       PrintStream originalOut = System.out;
       System.setOut(new PrintStream(outputStream));
       
       // Call the static viewAllCourses method with the list of courses
       Course.viewAllCourses(courses);
       
       // Reset the standard output
       System.setOut(originalOut);
       
       // Check the printed content
       String expectedOutput = course1.toString() + "\n" + course2.toString() + "\n" + course3.toString() + "\n";
       String printedOutput = outputStream.toString();
       
       assertEquals(expectedOutput, printedOutput);

       // Test for an empty list
       List<Course> emptyList = new ArrayList<>();
       outputStream = new ByteArrayOutputStream();
       System.setOut(new PrintStream(outputStream));
       Course.viewAllCourses(emptyList);
       System.setOut(originalOut);
       expectedOutput = "";
       printedOutput = outputStream.toString();
       assertEquals(expectedOutput, printedOutput);

       // Test for a single course in the list
       List<Course> singleCourseList = new ArrayList<>();
       singleCourseList.add(course1);
       outputStream = new ByteArrayOutputStream();
       System.setOut(new PrintStream(outputStream));
       Course.viewAllCourses(singleCourseList);
       System.setOut(originalOut);
       expectedOutput = course1.toString() + "\n";
       printedOutput = outputStream.toString();
       assertEquals(expectedOutput, printedOutput);

       // Test for a list with no time conflicts
       List<Course> noTimeConflictList = new ArrayList<>();
       noTimeConflictList.add(course1);
       noTimeConflictList.add(course2);
       noTimeConflictList.add(course3);
       outputStream = new ByteArrayOutputStream();
       System.setOut(new PrintStream(outputStream));
       Course.viewAllCourses(noTimeConflictList);
       System.setOut(originalOut);
       expectedOutput = course1.toString() + "\n" + course2.toString() + "\n" + course3.toString() + "\n";
       printedOutput = outputStream.toString();
       assertEquals(expectedOutput, printedOutput);

       // Test for a list with time conflicts
       List<Course> timeConflictList = new ArrayList<>();
       timeConflictList.add(course1);
       timeConflictList.add(course2);
       outputStream = new ByteArrayOutputStream();
       System.setOut(new PrintStream(outputStream));
       Course.viewAllCourses(timeConflictList);
       System.setOut(originalOut);
       expectedOutput = course1.toString() + "\n" + course2.toString() + "\n";
       printedOutput = outputStream.toString();
       assertEquals(expectedOutput, printedOutput);
   }

	

	@Test
    void testHasTimeConflict() {
        // Test for a time conflict (expect true)
        boolean conflict1 = course1.hasTimeConflict(course2);
        assertEquals(true, conflict1);

        // Test for no time conflict (expect false)
        boolean conflict2 = course1.hasTimeConflict(course3);
        assertEquals(false, conflict2);

        // Test for a time conflict on the same day (expect true)
        boolean conflict3 = course1.hasTimeConflict(course4);
        assertEquals(false, conflict3);

        // Test for a time conflict on a different day (expect false)
        boolean conflict4 = course1.hasTimeConflict(course5);
        assertEquals(false, conflict4);

        // Test for courses with the same start and end times (expect true)
        boolean conflict5 = course1.hasTimeConflict(course2);
        assertEquals(true, conflict5);
    }

    @Test
    void testToString() {
        // Test case 1: Regular course with no enrolled students
        Course course1 = new Course("C1", "Course 1", "Lecturer 1", "Monday", "09:00", "11:00", 30);
        String expectedOutput1 = "C1|Course 1, 09:00-11:00 on Monday, with course capacity: 30, students: 0, lecturer: Lecturer 1";
        assertEquals(expectedOutput1, course1.toString());

        // Test case 2: Course with maximum capacity
        Course course2 = new Course("C2", "Course 2", "Lecturer 2", "Tuesday", "10:00", "12:00", 25);
        String expectedOutput2 = "C2|Course 2, 10:00-12:00 on Tuesday, with course capacity: 25, students: 0, lecturer: Lecturer 2";
        assertEquals(expectedOutput2, course2.toString());

        // Test case 3: Course with a long course name
        Course course3 = new Course("C3", "A Very Long Course Name That Exceeds the Limit", "Lecturer 3", "Wednesday", "13:00", "15:00", 20);
        String expectedOutput3 = "C3|A Very Long Course Name That Exceeds the Limit, 13:00-15:00 on Wednesday, with course capacity: 20, students: 0, lecturer: Lecturer 3";
        assertEquals(expectedOutput3, course3.toString());

        // Test case 4: Course with a long course name
        Course course4 = new Course("C4", "A Very Long Course Name That Exceeds the Limit", "Lecturer 4", "Thursday", "14:00", "16:00", 15);
        String expectedOutput4 = "C4|A Very Long Course Name That Exceeds the Limit, 14:00-16:00 on Thursday, with course capacity: 15, students: 0, lecturer: Lecturer 4";
        assertEquals(expectedOutput4, course4.toString());

        // Test case 5: Course with special characters in the course name
        Course course5 = new Course("C5", "Course $%^&", "Lecturer 5", "Friday", "09:00", "10:00", 40);
        String expectedOutput5 = "C5|Course $%^&, 09:00-10:00 on Friday, with course capacity: 40, students: 0, lecturer: Lecturer 5";
        assertEquals(expectedOutput5, course5.toString());
    }


}
