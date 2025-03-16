package roles;

import courses.Course;
import java.util.List;
import java.util.Scanner;

public class Professor extends User {
    private List<Course> availableCourses;
    private List<Student> students;

    public Professor(String name, String id, String username, String password) {
    	// notice here the professor name and id is reverse compared to user, due to format of txt file
        super(id, name, username, password); 
    }
    
    //getter and setter
    public List<Course> getAvailableCourses() {
		return availableCourses;
	}
	public void setAvailableCourses(List<Course> availableCourses) {
		this.availableCourses = availableCourses;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	//view professor's course
	public void viewCoursesTaught() {
        // Logic to view the courses the professor teaches
    	String lecturerName = getName();
        for (Course course : this.availableCourses) {
        	if (lecturerName.equals(course.getLecturer())) {
        		System.out.println(course);
        	} 
        }
    }
    
	
	public void viewCourseRoster(String courseId) {
        Course course = null;
        // first, find the corresponding course from course list
        for (Course c : this.availableCourses) {
            if (c.getCourseId().equals(courseId)) {
                course = c;
                break;
            }
        }
        System.out.println("students in your course" + course.getCourseId() + ":");
        
        // second, print student list based on enrolled list variable in student class
        List<Student> enrolledStudents = course.getEnrolledStudents();
        //make sure the list is not empty
        if (enrolledStudents.isEmpty()) {
            System.out.println("No students are enrolled in " + courseId + ".");
        } else {
            for (Student student : enrolledStudents) {
                System.out.println(student); // use student's toString
        }
    }
	}

    @Override
    public boolean displayMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
        	System.out.println("--------------------------");
            System.out.println("Welcome, " + getName());
            System.out.println("--------------------------");
            System.out.println("1 -- View given courses");
            System.out.println("2 -- View student list of given course");
            System.out.println("3 -- Return to previous menu");
            System.out.println(" ");
            System.out.println("Please enter your option, eg. '1': ");
            
            int profOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (profOption) {
                case 1:
                    viewCoursesTaught();
                    break;
                case 2:
                		System.out.println("-------The course List-------");
                		viewCoursesTaught();
                		System.out.println("Please select the course ID, eg. 'CIS519'");
                		
                		String courseId = scanner.nextLine();
                		if ("q".equals(courseId)) {
                			break; // exit while while loop and break switch, go back to student menu
                		}
                		viewCourseRoster(courseId);
                    break;
                case 3:
                    running = false; // Exit the menu
                    return true; // return to main menu
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 3.");
                    
            }
        }
        return false; //go back to main menu
    }


    @Override
    public String toString() {
        return getId() + "; " + getName() + "; " + getUsername() + "; " + getPassword();
    }

   
}

