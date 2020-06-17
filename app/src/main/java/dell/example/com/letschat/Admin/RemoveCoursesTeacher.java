package dell.example.com.letschat.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dell.example.com.letschat.R;

public class RemoveCoursesTeacher extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText techerId,courseCode;
    String courseCodeName,departmentName;
    Spinner department;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_courses_teacher);


        getSupportActionBar().setTitle("Deallocate Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        techerId=findViewById(R.id.teacher_id_remove_course_teacher);
        courseCode=findViewById(R.id.course_code_removeCourseTeacher);

        databaseReference=FirebaseDatabase.getInstance().getReference();


        department=findViewById(R.id.spinner_department_teacher_remove_course);


        department.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department.setAdapter(adapter);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        departmentName=adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public  void removeCourseToTeacher(View view) {
        String id = techerId.getText().toString();
        courseCodeName = courseCode.getText().toString().toUpperCase();
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(getApplicationContext(), "Teacher id cannot be empty", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(courseCodeName)) {
            Toast.makeText(getApplicationContext(), "Course code cannot be empty", Toast.LENGTH_LONG).show();
        } else {
            //list.add(courseCodeName);
            databaseReference.child("Teacher").child("Department").child(departmentName).child(id).child("courses").child(courseCodeName).setValue(null);
            Toast.makeText(getApplicationContext(), "Course Removed Successfully", Toast.LENGTH_LONG).show();
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
