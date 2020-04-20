package dell.example.com.letschat.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dell.example.com.letschat.R;
import dell.example.com.letschat.UtilityClasses.Course;

public class AddCourse extends AppCompatActivity {

    DatabaseReference dbCourse;
    String courseId;
    String courseName;
    EditText editTextCourseId,editTextCourseName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        editTextCourseId=findViewById(R.id.course_id);
        editTextCourseName=findViewById(R.id.course_name);

        dbCourse=FirebaseDatabase.getInstance().getReference();

    }

    public void addCourse(View view)
    {
          courseId=editTextCourseId.getText().toString();
          courseName=editTextCourseName.getText().toString();
        if(TextUtils.isEmpty(courseId))
        {
            Toast.makeText(getApplicationContext(),"Course Id Required", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(courseName))
        {
            Toast.makeText(getApplicationContext(),"Course Name Required", Toast.LENGTH_LONG).show();
        }
      else {
            Course course = new Course(courseId, courseName);

            dbCourse.child("Courses").child(courseId).setValue(course);
            Toast.makeText(getApplicationContext(), "Course Added successfully", Toast.LENGTH_LONG).show();
        }




    }

    public void removeCourse(View view)
    {
        courseId=editTextCourseId.getText().toString();
        dbCourse.child("Attendance").child(courseId).setValue(null);
        Toast.makeText(getApplicationContext(), "Course Removed successfully", Toast.LENGTH_LONG).show();
    }


}
