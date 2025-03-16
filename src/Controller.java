/**
 * Homework 8 Student Management System
 * @author Adam You
 * @author Xuanru Li
 * @author Mian Zhang
 */

import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import courses.Course;
import roles.Student;
import roles.Admin;
import roles.Professor;
import files.FileInfoReader;

/**
 * This class serves as the main controller for the Students Management System.
 * It initializes the system, loads data from files, and provides a user interface
 * for login and navigation through different user roles like student, professor, and admin.
 */
public class Controller {
    // Static lists to hold different entities across the application
    private static List<Course> courses;
    private static List<Student> students;
    private static List<Admin> admins;
    private static List<Professor> profs;
    
    /**
     * The main method that serves as the entry point of the program.
     * It initializes resources, reads data from files, and runs the main menu loop.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        FileInfoReader fileReader = new FileInfoReader(); // Create a file reader object

        // Attempt to read course information from a file
        try {
            courses = fileReader.readCourseInfo("courseInfo.txt");
        } catch (IOException e) {
            System.out.println("Error reading course information.");
            e.printStackTrace(); // Print the stack trace for the exception
        }

        // Attempt to read student information from a file
        try {
            students = fileReader.readStudentInfo("studentInfo.txt");
        } catch (IOException e) {
            System.out.println("Error reading student information.");
            e.printStackTrace();
        }
        
        // Attempt to read admin information from a file
        try {
            admins = fileReader.readAdminInfo("adminInfo.txt");
        } catch (IOException e) {
            System.out.println("Error reading admin information.");
            e.printStackTrace();
        }
        
        // Attempt to read professor information from a file
        try {
            profs = fileReader.readProfessorInfo("profInfo.txt");
        } catch (IOException e) {
            System.out.println("Error reading admin information.");
            e.printStackTrace();
        }
        
        //update enrolled student list on every course instance 
        updateEnrolledStudents();
        
        Scanner scanner = new Scanner(System.in); // Initialize scanner for user input
        boolean mainMenuRunning = true; // Control variable for the main menu loop
        
        // Main menu loop
        while (mainMenuRunning) {
        // Display the main menu options
        System.out.println("---------------------------------");
        System.out.println("Students Management System");
        System.out.println("---------------------------------");
        System.out.println("1 -- Login as a student");
        System.out.println("2 -- Login as a professor");
        System.out.println("3 -- Login as an admin");
        System.out.println("4 -- Quit the system");
        System.out.print("Please enter your option, eg. '1': ");

        // Try to read the user's menu option choice
        try {
        int option = scanner.nextInt(); // Read the next integer from the scanner
        scanner.nextLine(); // Consume the newline left-over

        // Switch statement to handle the user's menu choice
        switch (option) {
            case 1: // Case for logging in as a student
                System.out.print("Please enter your username, or type 'q' to quit: ");
                String username = scanner.nextLine();
                if ("q".equals(username)) {
                    break; // Exit the case if 'q' is entered
                }
                System.out.print("Please enter your password, or type 'q' to quit: ");
                String password = scanner.nextLine();
                if ("q".equals(password)) {
                    break; // Exit the case if 'q' is entered
                }
                // Validate the student's login credentials
                Student loggedInStudent = validateStudent(username, password);
                if (loggedInStudent != null) {
                	// if log in successful, display student page menu
                	loggedInStudent.setAvailableCourses(courses);
                    boolean returnToMain = loggedInStudent.displayMenu(scanner);  
                    updateStudentInfo(loggedInStudent);
                    updateEnrolledStudents(); // Update enrolled student list before finish
                    if (returnToMain) {
                        continue; // if return to main is true, restart the while loop
                    }
                    
                } else {
                    // log in fail
                    System.out.println("Invalid username or password.");
                }
                break;
                
            case 2: // Case for logging in as a professor
            	System.out.print("Please enter your username, or type 'q' to quit: ");
                String usernameProf = scanner.nextLine();
                if ("q".equals(usernameProf)) {
                    break;
                }
                System.out.print("Please enter your password, or type 'q' to quit: ");
                String passwordProf = scanner.nextLine();
                if ("q".equals(passwordProf)) {
                    break;
                }
                // Validate the professor's login credentials
                Professor loggedInProf = validateProfessor(usernameProf, passwordProf);
                if (loggedInProf != null) {
                    // If login is successful, set the professor's available courses and display their menu
                	loggedInProf.setAvailableCourses(courses);
                    boolean returnToMain = loggedInProf.displayMenu(scanner);
                    if (returnToMain) {
                        continue; // if return to main is true, restart the while loop
                    }
                } else {
                    // log in fail
                    System.out.println("Invalid username or password.");
                }
                break;
            case 3: // Case for logging in as an admin
            	System.out.print("Please enter your username, or type 'q' to quit: ");
                String usernameAdmin = scanner.nextLine();
                if ("q".equals(usernameAdmin)) {
                    break;
                }
                System.out.print("Please enter your password, or type 'q' to quit: ");
                String passwordAdmin = scanner.nextLine();
                if ("q".equals(passwordAdmin)) {
                    break;
                }
                // Validate the admin's login credentials
                Admin loggedInAdmin = validateAdmin(usernameAdmin, passwordAdmin);
                if (loggedInAdmin != null) {
                    // If login is successful, set the admin's available courses and professors, and display their menu
                	loggedInAdmin.setAvailableCourses(courses);
                	loggedInAdmin.setProfs(profs);
                	loggedInAdmin.setStudents(students);
                    boolean returnToMain = loggedInAdmin.displayMenu(scanner);
                    updateCoursesList(loggedInAdmin.getAvailableCourses());
                    updateProfsList(loggedInAdmin.getProfs());
                    updateStudentsList(loggedInAdmin.getStudents());
                    updateEnrolledStudents();
                    System.out.println(students);
                    System.out.println(profs);
                    System.out.println(courses);
                    if (returnToMain) {
                        continue; // if return to main is true, restart the while loop
                    }
                } else {
                    // log in fail
                    System.out.println("Invalid username or password.");
                }
                break;
            case 4: // Case for quitting the system
                System.out.println("Exiting the system...");
                mainMenuRunning = false; // Set control variable to false to exit the loop
                break;
            default: // Default case for invalid menu option
                System.out.println("Invalid option. Please enter 1, 2, 3, or 4.");
                break;
        }

        } catch (InputMismatchException e) {
            scanner.nextLine(); // Consume the invalid input
            System.out.println("Invalid input. Please enter a number."); // Print error message
        }
        }//end of while loop
        
        scanner.close(); // Close the scanner resource
    } //end of main


    /**
     * Validates a student's login credentials against the stored student records.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return A Student object if the login is successful; null otherwise.
     */
    private static Student validateStudent(String username, String password) {
        // Iterate through the list of students to find a match
        for (Student student : students) {
            if (student.login(username, password)) {
                System.out.println("Login successful for: " + username);
                return student; // Return the student object if credentials match
            }
        }
        return null; // Return null if no match is found
    }
    

    /**
     * Validates a professor's login credentials against the stored professor records.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return A Professor object if the login is successful; null otherwise.
     */
    private static Professor validateProfessor(String username, String password) {
        // Iterate through the list of professors to find a match
        for (Professor professor : profs) {
            if (professor.login(username, password)) {
                return professor; // Return the professor object if credentials match
            }
        }
        return null; // Return null if no match is found
    }

    /**
     * Validates an admin's login credentials against the stored admin records.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return An Admin object if the login is successful; null otherwise.
     */
    private static Admin validateAdmin(String username, String password) {
        // Iterate through the list of admins to find a match
        for (Admin admin : admins) {
            if (admin.login(username, password)) {
                return admin; // Return the admin object if credentials match
            }
        }
        return null; // Return null if no match is found
    }
    
    /**
     * Updates the list of enrolled students for each course.
     * This method should be called whenever there is a change in student course enrollments.
     */
    public static void updateEnrolledStudents() {
        // Iterate through each course to update its list of enrolled students
        for (Course course : courses) {
            List<Student> enrolledStudents = new ArrayList<>();
            // Iterate through each student to check if they are enrolled in the course
            for (Student student : students) {
                if (student.checkCourseEnrollment(course.getCourseId())) {
                    enrolledStudents.add(student); // Add the student to the list if enrolled
                }
            }
            course.setEnrolledStudents(enrolledStudents); // Update the course's list of enrolled students
        }
    }
    
    //helper function to update course list after calling display menu
    public static void updateCoursesList(List<Course> updatedCourses) {
        courses = updatedCourses; 
    }
    
    public static void updateProfsList(List<Professor> updatedProfs) {
        profs = updatedProfs; 
    }
    
    public static void updateStudentsList(List<Student> updatedStudents) {
        students = updatedStudents; 
    }
    
    public static void updateStudentInfo(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(updatedStudent.getId())) {
                students.set(i, updatedStudent); // update specific student info after adding/deleting courses
                break;
            }
        }
    }


    
} // End of Controller class
