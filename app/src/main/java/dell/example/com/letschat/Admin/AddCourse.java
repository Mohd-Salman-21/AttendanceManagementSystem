package dell.example.com.letschat.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dell.example.com.letschat.R;

public class AddCourse extends AppCompatActivity {

    DatabaseReference dbCourse;
    String courseId;
    Spinner courseSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        courseSpinner=findViewById(R.id.course_id);

        dbCourse=FirebaseDatabase.getInstance().getReference("Attendance");

    }

    public void addCourse(View view)
    {
         courseId=courseSpinner.getSelectedItem().toString();
         dbCourse.child(courseId).setValue("Networks");
        Toast.makeText(getApplicationContext(),"Course Added successfully", Toast.LENGTH_LONG).show();

    }

}
