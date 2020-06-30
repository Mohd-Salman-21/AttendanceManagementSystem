package dell.example.com.letschat.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dell.example.com.letschat.R;

public class AddCourse extends AppCompatActivity {

    DatabaseReference dbCourse;
    String courseId;
    String confirmcourseId;

    EditText editTextCourseId;
    EditText editTextconfirmCourseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setTitle("Add Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextCourseId=findViewById(R.id.course_id);
        editTextconfirmCourseId=findViewById(R.id.confirm_course_id);

        dbCourse=FirebaseDatabase.getInstance().getReference();

    }

    public void addCourse(View view)
    {
          courseId=editTextCourseId.getText().toString().toUpperCase().trim();
          confirmcourseId=editTextconfirmCourseId.getText().toString().toUpperCase().trim();

        if(TextUtils.isEmpty(courseId))
        {
            Toast.makeText(getApplicationContext(),"Course code Required", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(confirmcourseId))
        {

            Toast.makeText(getApplicationContext(),"Confirm Course code Required", Toast.LENGTH_LONG).show();
        }

      else if(courseId.equals(confirmcourseId)){

            dbCourse.child("Courses").child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        Toast.makeText(getApplicationContext(), "Course already exists", Toast.LENGTH_LONG).show();
                    }else
                    {
                        dbCourse.child("Courses").child(courseId).setValue(courseId);
                        Toast.makeText(getApplicationContext(), "Course Added successfully", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else
        {

            Toast.makeText(getApplicationContext(),"Course code and Confirm Course code doesn't match", Toast.LENGTH_LONG).show();
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
