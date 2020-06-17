package dell.example.com.letschat.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dell.example.com.letschat.R;

public class AddCourse extends AppCompatActivity {

    DatabaseReference dbCourse;
    String courseId;

    EditText editTextCourseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setTitle("Add Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextCourseId=findViewById(R.id.course_id);

        dbCourse=FirebaseDatabase.getInstance().getReference();

    }

    public void addCourse(View view)
    {
          courseId=editTextCourseId.getText().toString().toUpperCase().trim();

        if(TextUtils.isEmpty(courseId))
        {
            Toast.makeText(getApplicationContext(),"Course Id Required", Toast.LENGTH_LONG).show();
        }

      else {


            dbCourse.child("Courses").child(courseId).setValue(courseId);
            Toast.makeText(getApplicationContext(), "Course Added successfully", Toast.LENGTH_LONG).show();
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
