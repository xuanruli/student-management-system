package courses;
import roles.Student;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseId;
    private String courseName;
    private String lecturer;
    private String days;
    private String startTime;
    private String endTime;
    private int capacity;
    private List<Student> enrolledStudents;
    
    //constructor
    public Course(String courseId, String courseName, String lecturer, String days,
                  String startTime, String endTime, int capacity) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.lecturer = lecturer;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.enrolledStudents = new ArrayList<>();
    }
    
    //generate setter and getter
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<Student> getEnrolledStudents() {
		return enrolledStudents;
	}

	public void setEnrolledStudents(List<Student> enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}
	
    // View all courses
    public static void viewAllCourses(List<Course> allCourses) {
        for (Course course : allCourses) {
            System.out.println(course);
        }
    }

    // Check if there is a time conflict with another course
    public boolean hasTimeConflict(Course otherCourse) {
        // Implement logic to check for time conflicts
        if (this.days.equals(otherCourse.days)) {
            // If the courses are on the same day, check for time overlap
            if (this.endTime.compareTo(otherCourse.startTime) > 0 &&
                otherCourse.endTime.compareTo(this.startTime) > 0) {
                return true; // Time conflict detected
            }
        }
        return false; // No time conflict
    }
    
    
	 // Implement toString method for printing course information
    @Override
    public String toString() {
        int enrolledCount = enrolledStudents.size();
        return courseId + "|" + courseName + ", " + startTime + "-" + endTime + " on " + days + ", with course capacity: " + capacity + ", students: " + enrolledCount + ", lecturer: " + lecturer;
    }

}