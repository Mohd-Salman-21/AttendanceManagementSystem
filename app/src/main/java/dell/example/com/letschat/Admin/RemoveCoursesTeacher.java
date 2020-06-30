package dell.example.com.letschat.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class RemoveCoursesTeacher extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText techerId;
    String departmentName;
    Spinner department;
    DatabaseReference databaseReference;
    boolean flag=true;

    Spinner courseId;
    String courseIdName;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    DatabaseReference courses,check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_courses_teacher);


        getSupportActionBar().setTitle("Deallocate Courses From Teacher");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        techerId=findViewById(R.id.teacher_id_remove_course_teacher);


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


    public void courseSelectionAdminRemoveCourse(View view) {


        final String id = techerId.getText().toString().toLowerCase().trim();

        if(TextUtils.isEmpty(id))
        {
            Toast.makeText(getApplicationContext(), "Teacher id cannot be empty", Toast.LENGTH_LONG).show();
        }else {

            check=ref.child("Teacher").child("Department").child(departmentName).child(id);
             check.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     if(dataSnapshot.exists())
                     {
                         courses = ref.child("Teacher").child("Department").child(departmentName).child(id).child("courses");
                         courses.addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot dataSnapshot) {

                                 List<String> areas = new ArrayList<String>();

                                 for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                                     String areaName = areaSnapshot.getValue(String.class);
                                     areas.add(areaName);
                                 }

                                 courseId = findViewById(R.id.spinner_courses_teacher_remove_course);
                                 flag=false;
                                 ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(RemoveCoursesTeacher.this, android.R.layout.simple_spinner_item, areas);
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
                     }else {
                         Toast.makeText(getApplicationContext(), "Teacher doesn't exist", Toast.LENGTH_LONG).show();
                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });



        }

    }


    public  void removeCourseToTeacher(View view) {
        if(flag==true)
        {
            Toast.makeText(getApplicationContext(), "First fetch courses please", Toast.LENGTH_LONG).show();
        }
        else {
            String id = techerId.getText().toString().toLowerCase().trim();

            if (TextUtils.isEmpty(id))
                Toast.makeText(getApplicationContext(), "Teacher id cannot be empty", Toast.LENGTH_LONG).show();

            else {

                databaseReference.child("Teacher").child("Department").child(departmentName).child(id).child("courses").child(courseIdName).setValue(null);
                Toast.makeText(getApplicationContext(), "Course Removed Successfully", Toast.LENGTH_LONG).show();
            }
            flag=true;
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
