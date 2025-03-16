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
import files.FileInfoReader;
import courses.Course;
import roles.Student;
import roles.Professor;
import roles.Admin;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;


class FileInfoReaderTest {
	  private FileInfoReader fileInfoReader;
	  private static final String EmptyFile = "Empty.txt"; 
	  private static final String CourseFile = "courseInfo.txt"; 
	  private static final String AdminFile = "adminInfo.txt"; 
	  private static final String StudentFile = "studentInfo.txt"; 
	  private static final String ProfessorFile = "profInfo.txt"; 
	  
	  @BeforeEach
	void setUp() throws Exception {
		fileInfoReader = new FileInfoReader();
	}

	@Test
	void testReadCourseInfo() throws IOException {
	   
		//Testcase 1: Empty file
		List<Course> emptyfile = fileInfoReader.readCourseInfo(EmptyFile);
	    assertEquals(0, emptyfile.size());
	    
	    //Testcase 2: Test with a valid Course file and check course numbers
        List<Course> courses = fileInfoReader.readCourseInfo(CourseFile);
        assertEquals(50, courses.size());
        
        //Testcase 3: Test with non existing file
        IOException exception = assertThrows(IOException.class, () -> {
            List<Course> nonExisting = fileInfoReader.readCourseInfo("path/to/nonexistent/file.txt");
            assertEquals(0, nonExisting.size());
        });
        // Check if the exception message contains the expected text
        assertTrue(exception.getMessage().contains("No such file or directory"));
       
        
        //Testcase 4: Test with non course file
        ArrayIndexOutOfBoundsException exception2 = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
        	List<Course> admins = fileInfoReader.readCourseInfo(AdminFile);
           assertEquals(0, admins.size());
           });
        // Check if the exception message contain
        assertTrue(exception2.getMessage().contains("Index"));
        assertTrue(exception2.getMessage().contains("out of bounds for length"));
	}

	@Test
	void testReadStudentInfo() throws IOException {
	    // Testcase 1: Empty file
	    List<Student> emptyfile = fileInfoReader.readStudentInfo(EmptyFile);
	    assertEquals(0, emptyfile.size());

	    // Testcase 2: Test with a valid Student file and check student count
	    List<Student> students = fileInfoReader.readStudentInfo(StudentFile);
	    assertEquals(2, students.size()); 

	    // Testcase 3: Test with non existing file
	    IOException exception = assertThrows(IOException.class, () -> {
	        List<Student> nonExisting = fileInfoReader.readStudentInfo("path/to/nonexistent/studentfile.txt");
	        assertEquals(0, nonExisting.size());
	    });
	    // Check if the exception message contains the expected text
	    assertTrue(exception.getMessage().contains("No such file or directory"));

        
        //Testcase 4: Test with non student file
        ArrayIndexOutOfBoundsException exception3 = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
        	List<Course> student= fileInfoReader.readCourseInfo(AdminFile);
           assertEquals(0, student.size());
           });
        // Check if the exception message contain
        assertTrue(exception3.getMessage().contains("Index"));
        assertTrue(exception3.getMessage().contains("out of bounds for length"));
	}



	//@Test
	//void testReadAdminInfo() {
	//	fail("Not yet implemented");
	//}
	
	@Test
	void testReadAdminInfo() throws IOException {
	    // Testcase 1: Empty file
	    List<Admin> emptyfile = fileInfoReader.readAdminInfo(EmptyFile);
	    assertEquals(0, emptyfile.size());

	    // Testcase 2: Test with a valid Admin file and check admin count
	    List<Admin> admins = fileInfoReader.readAdminInfo(AdminFile);
	    assertEquals(3, admins.size());

	    // Testcase 3: Test with non-existing file
	    IOException exception = assertThrows(IOException.class, () -> {
	        List<Admin> nonExisting = fileInfoReader.readAdminInfo("path/to/nonexistent/adminfile.txt");
	        assertEquals(0, nonExisting.size());
	    });
	    // Check if the exception message contains the expected text
	    assertTrue(exception.getMessage().contains("No such file or directory"));

        
       
	}

	@Test
	void testReadProfessorInfo() throws IOException {
	    // Testcase 1: Empty file
	    List<Professor> emptyfile = fileInfoReader.readProfessorInfo(EmptyFile);
	    assertEquals(0, emptyfile.size());

	    // Testcase 2: Test with a valid Professor file and check professor count
	    List<Professor> professors = fileInfoReader.readProfessorInfo(ProfessorFile); // Replace ProfessorFile with the actual file path
	    assertEquals(32, professors.size()); // Replace expectedProfessorCount with the expected count

	    // Testcase 3: Test with non-existing file
	    IOException exception = assertThrows(IOException.class, () -> {
	        List<Professor> nonExisting = fileInfoReader.readProfessorInfo("path/to/nonexistent/professorfile.txt");
	        assertEquals(0, nonExisting.size());
	    });
	    // Check if the exception message contains the expected text
	    assertTrue(exception.getMessage().contains("No such file or directory"));
	}
}
