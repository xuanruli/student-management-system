package roles;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import courses.Course;

public class Student extends User {
    private Map<String, String> courseGrades;
    private List<Course> availableCourses;

    public Student(String id, String name, String username, String password) {
        super(id, name, username, password);
        this.courseGrades = new HashMap<>();
    }

    //generate getter and setter
    public void setAvailableCourses(List<Course> courses) {
        this.availableCourses = courses;
    }
    public List<Course> getAvailableCourses() {
		return availableCourses;
	}
    public Map<String, String> getCourseGrades() {
		return courseGrades;
	}
	//set grade for specific course
	public void setCourseGrades(Map<String, String> courseGrades) {
		this.courseGrades = courseGrades;
	}
	
	// add course and grade for new course
    public void addCourseGrade(String courseId, String grade) {
    	courseGrades.put(courseId, grade);
    }

    // Method to get the grade for a specific course
    public String getGradeForCourse(String courseId) {
        return courseGrades.get(courseId);
    }
    
    // view all courses
    public void viewAllCourses(List<Course> availableCourses) {
    	for (Course course : availableCourses) {
    		System.out.println(course);
    	}
    }
    
    //add course
    public void addCourse(String courseId, List<Course> availableCourses) {
    	//check if course in the course list
        Course courseToAdd = findCourseById(availableCourses, courseId);
        if (courseToAdd == null) {
            System.out.println("The course ID you entered does not exist.");
            return;
        }
        //check if course in the current enrolled list
        if (courseGrades.containsKey(courseId)) {
            System.out.println("You are already enrolled in this course.");
            return;
        } 
        //check time conflict
        for (String enrolledCourseId : courseGrades.keySet()) { //iterate the enrolled map
            Course enrolledCourse = findCourseById(availableCourses, enrolledCourseId); //check if this course
            if (enrolledCourse != null && enrolledCourse.hasTimeConflict(courseToAdd)) {
                System.out.println("The course you selected has time conflict with " + enrolledCourseId + ".");
                return;
            }
        }

        //add the course into courseGrades
        courseGrades.put(courseId, ""); // add the course with empty grade
        System.out.println("Course added successfully");
    }
       
   
    // view enrolled course 
    public void viewEnrolledCourses() {
    	System.out.println("Enrolled Courses:");
        for (Map.Entry<String, String> entry : this.courseGrades.entrySet()) {
            String courseId = entry.getKey();
            Course course = findCourseById(this.availableCourses, courseId);
            if (course != null) {
                System.out.println(course);
            } else {
                System.out.println("Course ID  not found.");
            }
        }
    }
    
    // drop course
    public void dropCourse(String courseId) {
        // Check if the course is in the current enrolled list
    	// Here we don't need to check if course in the course list, because if it's not in enrolled list, it's also not in course list
        if (!courseGrades.containsKey(courseId)) {
            System.out.println("The course isn't in your schedule.");
            return;
        }
        // Remove the course from courseGrades
        courseGrades.remove(courseId);
        System.out.println("Course dropped successfully");
    }

   //view grades
    public void viewGrades() {
        System.out.println("Here are the courses you already taken, with your grade in a letter format");
        for (Map.Entry<String, String> entry : this.courseGrades.entrySet()) {
            String courseId = entry.getKey();
            String grade = entry.getValue();
            Course course = findCourseById(this.availableCourses, courseId);
            
            if (course != null) {
                String courseName = course.getCourseName(); // 
                System.out.println("Grade of " + courseId + " " + courseName + ": " + grade);
            } else {
                System.out.println("Course ID: Details not found.");
            }
        }
    }

    
    //helper function on add course, find course by string course ID
    private Course findCourseById(List<Course> allCourses, String courseId) {
        for (Course course : allCourses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }
    
    //helper function
    public boolean checkCourseEnrollment(String courseId) {
        return courseGrades.containsKey(courseId);
    }

    @Override
    public boolean displayMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
        	System.out.println("--------------------------");
            System.out.println("Welcome, " + getName());
            System.out.println("--------------------------");
            System.out.println("1 -- View all courses");
            System.out.println("2 -- Add courses to your list");
            System.out.println("3 -- View enrolled courses");
            System.out.println("4 -- Drop courses in your list");
            System.out.println("5 -- View grades");
            System.out.println("6 -- Return to previous menu");
            System.out.println("Please enter your option, eg. '1': ");
            
            int studentOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (studentOption) {
                case 1:
                    viewAllCourses(availableCourses);
                    break;
                case 2:
                	while (true) {
                		System.out.println("Please select the course ID you want to add to your list, eg. 'CIT590'");
                		System.out.println("Or enter 'q' to return to previous menu");
                		String courseId = scanner.nextLine();
                		if ("q".equals(courseId)) {
                			break; // exit while while loop and break switch, go back to student menu
                		}
                		addCourse(courseId,availableCourses);
                	}
                    break;
                case 3:
                	viewEnrolledCourses();
                    break;
                case 4:
                	while (true) {
                		System.out.println(" ");
                        System.out.println("The courses in your list:");
                        viewEnrolledCourses();  // print all the course on the student's schedule
                        System.out.println(" ");
                        System.out.println("Please enter the ID of the course you want to drop, eg. 'CIT591': ");
                        System.out.println("Or enter 'q' to return to the previous menu: ");
                        String courseId = scanner.nextLine();
                        if ("q".equals(courseId)) {
                            break; // exit while while loop and break switch, go back to student menu
                        }
                        dropCourse(courseId); 
                    }
                    break;
                case 5:
                    viewGrades();
                    break;
                case 6:
                    running = false; // Exit the menu
                    return true; // return to main menu
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 6.");
                    
            }
        }
        return false; //go back to main menu
    }
    
    @Override
    public String toString() {
    	return getId() +" "+ getName(); 
    }
    
}
