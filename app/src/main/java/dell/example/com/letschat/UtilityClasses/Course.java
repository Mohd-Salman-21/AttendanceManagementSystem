package dell.example.com.letschat.UtilityClasses;

public class Course {

    String courseId;
    String courseName;


    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;

    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}
