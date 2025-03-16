package files;
import java.io.IOException;
import java.util.List;
import courses.Course;
import roles.Student;
import roles.Admin;
import roles.Professor;
import files.FileInfoReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class FileInfoReader {
	//read course information
    public List<Course> readCourseInfo(String filePath) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) { //stop when empty line
                String[] details = line.split(";");
                Course course = new Course(details[0].trim(), details[1].trim(), 
                                           details[2].trim(), details[3].trim(), 
                                           details[4].trim(), details[5].trim(), 
                                           Integer.parseInt(details[6].trim()));
                courses.add(course); //add new course into courses arrayList
            }
        }
        return courses;
    }
    
    
    public List<Student> readStudentInfo(String filePath) throws IOException {
    	//read student information
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(";");
                String studentId = details[0].trim();
                String name = details[1].trim();
                String username = details[2].trim();
                String password = details[3].trim();
                Student student = new Student(studentId, name, username, password);
                
                //read course grade part
                if (details.length > 4) {
                    // Process course grades
                    String[] courseGrades = details[4].split(", ");
                    for (String courseGradePair : courseGrades) {
                        String[] courseGrade = courseGradePair.trim().split(":"); //split it into course and grade
                        if (courseGrade.length == 2) {
                            String courseId = courseGrade[0].trim();
                            String grade = courseGrade[1].trim();
                            student.addCourseGrade(courseId, grade);
                        }
                    }
                }

                students.add(student);
            }
        }
        return students;
    }
    
    
    public List<Admin> readAdminInfo(String filePath) throws IOException {
        List<Admin> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(";");
                if (details.length == 4) {
                    String adminId = details[0].trim();
                    String name = details[1].trim();
                    String username = details[2].trim();
                    String password = details[3].trim();

                    Admin admin = new Admin(adminId, name, username, password);
                    admins.add(admin);
                } else {
                    System.out.println("Invalid line in adminInfo.txt: " + line);
                }
            }
        }
        return admins;
    }

    
    public List<Professor> readProfessorInfo(String filePath) throws IOException {
        List<Professor> professors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(";");
                if (details.length == 4) {
                    String name = details[0].trim();
                    String professorId = details[1].trim();
                    String username = details[2].trim();
                    String password = details[3].trim();

                    Professor professor = new Professor(name, professorId, username, password);
                    professors.add(professor);
                } else {
                    System.out.println("Invalid line in professorInfo.txt: " + line);
                }
            }
        }
        return professors;
    }
    
    
    

}
