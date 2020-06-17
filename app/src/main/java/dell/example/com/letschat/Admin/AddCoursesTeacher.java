package dell.example.com.letschat.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dell.example.com.letschat.R;

public class AddCoursesTeacher extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    EditText techerId,courseCode;
    String courseCodeName,departmentName;
    Spinner department;
    DatabaseReference databaseReference;


    Spinner courseId;
    String courseIdName;










    String teacher_id,class_selected;


    EditText date;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Studentlist = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent,courses;
    String required_date;


    ArrayList<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses_teacher);
        getSupportActionBar().setTitle("Allocate Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        techerId=findViewById(R.id.teacher_id_course_teacher);

        databaseReference=FirebaseDatabase.getInstance().getReference();


        department=findViewById(R.id.spinner_department_teacher_course);


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
        courseSelectionAdmin();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void courseSelectionAdmin() {

        courses=ref.child("Courses");
        courses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.getValue(String.class);
                    areas.add(areaName);
                }

                courseId = findViewById(R.id.spinner_course_admin);


                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(AddCoursesTeacher.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseId.setAdapter(areasAdapter);
                courseId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        courseIdName = adapterView.getItemAtPosition(i).toString();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something wrong with courseid", Toast.LENGTH_LONG).show();

            }
        });


    }




    public  void addCourseToTeacher(View view) {
        String id = techerId.getText().toString();

        if (TextUtils.isEmpty(id)) {
            Toast.makeText(getApplicationContext(), "Teacher id cannot be empty", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(courseIdName)) {
            Toast.makeText(getApplicationContext(), "Course code cannot be empty", Toast.LENGTH_LONG).show();
        } else {
            //list.add(courseCodeName);
            databaseReference.child("Teacher").child("Department").child(departmentName).child(id).child("courses").child(courseIdName).setValue(courseIdName);
            Toast.makeText(getApplicationContext(), "Course Added Successfully", Toast.LENGTH_LONG).show();
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
