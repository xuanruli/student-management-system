
/**
 * Homework 8 Student Management System
 * @author Adam You
 * @author Xuanru Li
 * @author Mian Zhang
 */
 
 package test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;
import roles.Admin;
import roles.Professor;

class AdminTest {
    private Admin admin;
    private List<Course> availableCourses;
    private List<Professor> profs;

    @BeforeEach
    void setUp() {
        admin = new Admin("A001", "Admin Name", "adminuser", "adminpass");
        availableCourses = new ArrayList<>();
        profs = new ArrayList<>();
        admin.setAvailableCourses(availableCourses);
        admin.setProfs(profs);
    }

    
    // Test Admin(String id, String name, String username, String password) 
    @Test
    void testAdminWithValidData() {
        // Ensure that the Admin object is created with valid data
        assertEquals("A001", admin.getId());
        assertEquals("Admin Name", admin.getName());
        assertEquals("adminuser", admin.getUsername());
        assertEquals("adminpass", admin.getPassword());
    }

    @Test
    void testAdminWithEmptyName() {
        // Ensure that the Admin object is created even if the name is empty
        Admin adminWithEmptyName = new Admin("A002", "", "adminuser2", "adminpass2");
        assertNotNull(adminWithEmptyName);
    }

    @Test
    void testAdminWithEmptyUsername() {
        // Ensure that the Admin object is created even if the username is empty
        Admin adminWithEmptyUsername = new Admin("A003", "Admin Name", "", "adminpass3");
        assertNotNull(adminWithEmptyUsername);
    }

    @Test
    void testAdminWithEmptyPassword() {
        // Ensure that the Admin object is created even if the password is empty
        Admin adminWithEmptyPassword = new Admin("A004", "Admin Name", "adminuser4", "");
        assertNotNull(adminWithEmptyPassword);
    }

    @Test
    void testAdminWithNullValues() {
        // Ensure that the Admin object is created even if some values are null
        Admin adminWithNullValues = new Admin(null, null, null, null);
        assertNotNull(adminWithNullValues);
    }
    
    @Test
    void testAddCourseWithValidData() {
        Scanner scanner = new Scanner("CIS101\nIntroduction to Computer Science\n09:00\n10:30\nMon\n30\nP001\n");
        admin.addCourse("CIS101", "Introduction to Computer Science", "09:00", "10:30", "Mon", 30, "P001", scanner);
        assertEquals(1, availableCourses.size());
    }

    @Test
    void testAddCourseWithExistingCourseId() {
        availableCourses.add(new Course("CIS101", "Existing Course", "Prof. Smith", "Mon", "09:00", "10:30", 30));
        Scanner scanner = new Scanner("CIS101\nIntroduction to Computer Science\n09:00\n10:30\nMon\n30\nP001\n");
        admin.addCourse("CIS101", "Introduction to Computer Science", "09:00", "10:30", "Mon", 30, "P001", scanner);
        assertEquals(1, availableCourses.size()); // Course should not be added again
    }

    @Test
    void testAddCourseWithTimeConflict() {
        availableCourses.add(new Course("CIS101", "Existing Course", "Prof. Smith", "Mon", "09:00", "10:30", 30));
        Scanner scanner = new Scanner("CIS102\nIntroduction to Computer Science\n09:30\n10:45\nMon\n30\nP001\n");
        admin.addCourse("CIS102", "Introduction to Computer Science", "09:30", "10:45", "Mon", 30, "P001", scanner);
        assertEquals(1, availableCourses.size()); // Course with time conflict should not be added
    }

    @Test
    void testAddCourseWithNonExistingProfessor() {
        Scanner scanner = new Scanner("CIS101\nIntroduction to Computer Science\n09:00\n10:30\nMon\n30\nP001\n");
        admin.addCourse("CIS101", "Introduction to Computer Science", "09:00", "10:30", "Mon", 30, "P001", scanner);
        assertEquals(1, availableCourses.size());
        assertEquals(1, profs.size());
    }

    @Test
    void testDeleteCourseWithValidCourseId() {
        availableCourses.add(new Course("CIS101", "Introduction to Computer Science", "Prof. Smith", "Mon", "09:00", "10:30", 30));
        Scanner scanner = new Scanner("CIS101\n");
        admin.deleteCourse(scanner);
        assertEquals(0, availableCourses.size());
    }

    @Test
    void testDeleteCourseWithNonExistingCourseId() {
        Scanner scanner = new Scanner("CIS101\n");
        admin.deleteCourse(scanner);
        assertEquals(0, availableCourses.size()); // No course should be deleted
    }

    @Test
    void testDeleteCourseWithMultipleCourses() {
        availableCourses.add(new Course("CIS101", "Course 1", "Prof. Smith", "Mon", "09:00", "10:30", 30));
        availableCourses.add(new Course("MATH201", "Course 2", "Prof. Johnson", "Tue", "10:00", "11:30", 40));
        Scanner scanner = new Scanner("CIS101\n");
        admin.deleteCourse(scanner);
        assertEquals(1, availableCourses.size()); // Only one course should be deleted
    }

    @Test
    void testDeleteCourseWithInvalidInput() {
        availableCourses.add(new Course("CIS101", "Introduction to Computer Science", "Prof. Smith", "Mon", "09:00", "10:30", 30));
        Scanner scanner = new Scanner("InvalidCourse\n");
        admin.deleteCourse(scanner);
        assertEquals(1, availableCourses.size()); // No course should be deleted
    }
}
