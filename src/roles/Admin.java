/**
 * Homework 8 Student Management System
 * @author Adam You
 * @author Xuanru Li
 * @author Mian Zhang
 */

package roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import courses.Course;

/**
 * Represents an administrator in the student management system.
 * Inherits common attributes and methods from the User class.
 */
public class Admin extends User {
	private List<Course> availableCourses;
	private List<Professor> profs;
    private List<Student> students;
	
    /**
     * Constructor for the Admin class.
     * Initializes the students list as an empty ArrayList.
     */
    public Admin(String id, String name, String username, String password) {
        super(id, name, username, password);
        this.students = new ArrayList<>(); 
    }
    
    // Getter and setter methods 
    public List<Course> getAvailableCourses() {
		return availableCourses;
	}
	public void setAvailableCourses(List<Course> availableCourses) {
		this.availableCourses = availableCourses;
	}
	public List<Professor> getProfs() {
		return profs;
	}
	public void setProfs(List<Professor> profs) {
		this.profs = profs;
	}
    public List<Student> getStudents(){
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * Views all available courses in the system.
     * @param availableCourses The list of available courses.
     */
	public void viewAllCourses(List<Course> availableCourses) {
    	for (Course course : availableCourses) {
    		System.out.println(course);
    	}
    }
    
    /**
     * Adds a new course to the system.
     * Checks for existing course ID and professor availablity before adding.
     */
    public void addCourse(String courseId, String courseName, String startTime, String endTime, String courseDate, int capacity, String lecturerId, Scanner scanner) {
        if (findCourseById(courseId) != null) {
            System.out.println("the course with ID already exists.");
            return;
        }
        // Check if professor is exist, if not, add the professor into professor list
        Professor lecturer = findProfessorById(lecturerId);
        if (lecturer == null) {
        	System.out.println("The professor is not in the system, please add this professor first.");
            lecturer = addProfessor(scanner); 
        }
        if (lecturer == null) {
        	return; // if add professor fail or quit during adding, return
        }
        
        Course newCourse = new Course(courseId, courseName, lecturer.getName(), courseDate, startTime, endTime, capacity);
        
        // Check time conflict
        if (hasTimeConflict(newCourse, availableCourses)) {
            System.out.println("Unable to add new course due to time conflict: " + newCourse);
            return;
        }
        // If no time conflict and other issues, add the course
        availableCourses.add(newCourse);
        System.out.println("Successfully added the course: " + newCourse);
        }   
    
    
    /**
     * Deletes a course from the system based on the course ID.
     * @param scanner Scanner for user input.
     */
    public void deleteCourse(Scanner scanner) {
        System.out.println("Please enter the ID of the course to be deleted, or 'q' to quit:");
        String courseId = scanner.nextLine();
        if ("q".equals(courseId)) {
            return; // Allow the user to quit the operation
        }

        Iterator<Course> iterator = availableCourses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.getCourseId().equals(courseId)) {
                iterator.remove(); // Remove the course safely using iterator
                System.out.println("Successfully deleted the course with ID: " + courseId);
                return;
            }
        }

        System.out.println("No course found with the ID: " + courseId);
    }
    
    //helper method on add course
    private boolean hasTimeConflict(Course newCourse, List<Course> availableCourses) {
        for (Course existingCourse : availableCourses) {
            if (existingCourse.hasTimeConflict(newCourse)) {
                System.out.println("The new added course has time conflict with course: " + existingCourse);
                return true;
            }
        }
        return false; 
    }

    /**
     * Adds a new professor to the system.
     * @param scanner Scanner for user input.
     * @return Newly added Professor object.
     */
    private Professor addProfessor(Scanner scanner) {
        String id;
        // check if id exist
        while (true) {
            System.out.println("Please enter professor's Id, or type 'q' to quit:");
            id = scanner.nextLine();
            if ("q".equals(id)) {
                return null;
            }
         // if user id already exist, ask again to collect id
            if (findProfessorById(id) != null) {
                System.out.println("A professor with this ID already exists. Please try a different ID.");
            } else {
                break; 
            }
        }

        System.out.println("Please enter professor's name:");
        String name = scanner.nextLine();

        String username;
        //check if username valid
        while (true) {
            System.out.println("Please enter professor's username, or type 'q' to quit:");
            username = scanner.nextLine();
            if ("q".equals(username)) {
                return null; 
            }
            // if user name already exist, ask again to collect username
            if (findProfessorByUsername(username) != null) {
                System.out.println("This username is already taken. Please try a different username.");
            } else {
                break; 
            }
        }

        System.out.println("Please enter professor's password:");
        String password = scanner.nextLine();

        // if everything ok, add the professor into professor list
        Professor newProfessor = new Professor(name, id, username, password);
        profs.add(newProfessor);
        System.out.println("Successfully added the new professor: " + newProfessor);
        return newProfessor;
    }
    
    /**
     * Deletes a professor from the system based on the professor ID.
     * @param scanner Scanner for user input.
     */
    private void deleteProfessor(Scanner scanner) {
        System.out.println("Please enter the ID of the professor to be deleted, or 'q' to quit:");
        String professorId = scanner.nextLine();
        if ("q".equals(professorId)) {
            return; // Allow the user to quit the operation
        }

        Iterator<Professor> iterator = profs.iterator();
        while (iterator.hasNext()) {
            Professor prof = iterator.next();
            if (prof.getId().equals(professorId)) {
                iterator.remove(); // Remove the professor safely using iterator
                System.out.println("Successfully deleted the professor: " + prof);
                return;
            }
        }

        System.out.println("No professor found with the ID: " + professorId);
    }

    
    //helper functions
    private Course findCourseById(String courseId) {
        for (Course course : availableCourses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    private Professor findProfessorById(String professorId) {
        for (Professor professor : profs) {
            if (professor.getId().equals(professorId)) {
                return professor;
            }
        }
        return null;
    }
    
    private Professor findProfessorByUsername(String username) {
        for (Professor professor : profs) {
            if (professor.getUsername().equals(username)) {
                return professor;
            }
        }
        return null;
    }
    
    
    /**
     * Adds a new student into the system.
     * Checks for duplicate student ID before adding.
     * @param scanner Scanner object for user input.
     */
    private void addStudent(Scanner scanner) {
        String studentId = "";
        while (true) {
            // Prompt for student ID and check for duplicates
            System.out.println("Please enter the student's ID, or type 'q' to quit:");
            studentId = scanner.nextLine();
            if ("q".equals(studentId)) {
                return; // Quit if 'q' is entered
            }
            boolean exists = false;
            // Check if the entered ID already exists in the students list
            for (Student student : this.students) {
                if (student.getId().equals(studentId)) {
                    System.out.println("The ID already exists.");
                    exists = true; // Set flag to true if ID is found
                    break;
                }
            }
            if (!exists) {
                break; // Break the loop if ID is new
            }
        }
        // Collect the rest of the studnet's information
        System.out.println("Please enter student's name, or type 'q' to end:");
        String name = scanner.nextLine();
        if ("q".equals(name)) {
            return;
        }
        
        //collect student username
        String username = "";
        while (true) {
            System.out.println("Please enter a username:");
            username = scanner.nextLine();
            if ("q".equals(username)) {
                return; // 用户选择退出
            }
            boolean usernameExists = false;
            for (Student student : this.students) {
                if (student.getUsername().equals(username)) {
                    System.out.println("This username already exists. Please try a different username.");
                    usernameExists = true;
                    break; //if username exist, break the while loop and ask the username again
                }
            }
            if (!usernameExists) {
                break; // username add successful
            }
        }
        
        //collect password
        System.out.println("Please enter a password:");
        String password = scanner.nextLine();

        // Create a new student object with the collected information
        Student newStudent = new Student(studentId, name, username, password);

        // Prompt for course IDs and grades
        while (true) {
            System.out.println("Please enter ID of a course which this student already took, one at a time:");
            System.out.println("Type 'q' to quit, type 'n' to stop adding.");
            String courseId = scanner.nextLine();

            if ("q".equals(courseId)) {
                return;
            } else if ("n".equals(courseId)) {
                break;
            }

            System.out.println("Please enter the grade, eg, 'A':");
            String grade = scanner.nextLine();

            // Add the course and grade to the student's record
            newStudent.addCourseGrade(courseId, grade);
        }
        // Add the new student to the list
        this.students.add(newStudent);
        System.out.println("Successfully added the new student: " + newStudent.getId() + " " + newStudent.getName());
    }

    /**
     * Removes a student from the system based on student ID.
     * @param scanner Scanner object for user input.
     */
    private void deleteStudent(Scanner scanner) {
        System.out.println("Please enter the ID of the student to be deleted, or 'q' to quit:");
        String studentId = scanner.nextLine();
        if ("q".equals(studentId)) {
            return; 
        }

        // Use an iterator to avoid ConcurrentModificationException
        Iterator<Student> iterator = this.students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId().equals(studentId)) {
                iterator.remove(); // Remove the student safely using iterator
                System.out.println("Successfully deleted the student with ID: " + studentId);
                return;
            }
        }

        // If no student with the given ID is found, print a message
        System.out.println("No student found with the ID: " + studentId);
    }
    
    

    
    /**
     * Overridden method from User class to display the admin menu
     */
    @Override
    public boolean displayMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
        	System.out.println("--------------------------");
            System.out.println("Welcome, " + getName());
            System.out.println("--------------------------");
            System.out.println("1 -- View all courses");
            System.out.println("2 -- Add new courses");
            System.out.println("3 -- Delete courses");
            System.out.println("4 -- Add new professor");
            System.out.println("5 -- Delete professor");
            System.out.println("6 -- Add new student");
            System.out.println("7 -- Delete student");
            System.out.println("8 -- Return to previous menu");
            System.out.println(" ");
            System.out.println("Please enter your option, eg. '1': ");
            
            int profOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (profOption) {
                case 1:
                	viewAllCourses(availableCourses);
                    break;
                case 2:
                	String courseId, courseName, startTime, endTime, courseDate;
                    int capacity;
                    String lecturerId;
                    
                    // Input
                    System.out.println("Please enter the course ID, or type 'q' to end.");
                    courseId = scanner.nextLine();
                    if ("q".equals(courseId)) break;
                    System.out.println("Please enter the course name, or type 'q' to end.");
                    courseName = scanner.nextLine();
                    if ("q".equals(courseName)) break;
                    System.out.println("Please enter the course start time, or type 'q' to end. eg. '19:00'");
                    startTime = scanner.nextLine();
                    if ("q".equals(startTime)) break;
                    System.out.println("Please enter the course end time, or type 'q' to end. eg. '20:00'");
                    endTime = scanner.nextLine();
                    if ("q".equals(endTime)) break;
                    System.out.println("Please enter the course date, or type 'q' to end. eg. 'MW'");
                    courseDate = scanner.nextLine();
                    if ("q".equals(courseDate)) break;
                    System.out.println("Please enter the course capacity, or type 'q' to end. eg. '72'");
                    capacity = Integer.parseInt(scanner.nextLine());
                    if (capacity <= 0) break; 
                    System.out.println("Please enter the course lecturer's id, or type 'q' to end. eg. '001'");
                    lecturerId = scanner.nextLine();
                    if ("q".equals(lecturerId)) break;

                    // Now we have all the information, try to add the course
                    addCourse(courseId, courseName, startTime, endTime, courseDate, capacity, lecturerId, scanner);
                    break;
                case 3:
                	deleteCourse(scanner);
                	break;
                case 4:
                	addProfessor(scanner);
                	break;
                case 5:
                	deleteProfessor(scanner);
                	break;
                case 6:
                    addStudent(scanner);
                	break;
                case 7:
                    deleteStudent(scanner);
                	break;
                case 8:
                    running = false; // Exit the menu
                    return true; // Return to main menu
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 3.");
                    
            }
        }
        return false; // Go back to main menu
    } // End of display menu
    
}



