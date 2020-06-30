package dell.example.com.letschat.Student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dell.example.com.letschat.R;

public class student_attendance_sheet extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {


    Spinner department, semester;
    String departmentName, semesterName;
     EditText course;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    String  p1;
    String student_id;
    ArrayList dates = new ArrayList<>();
    DatabaseReference dbAttendance;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_sheet);


        listView = (ListView) findViewById(R.id.list);
         course = findViewById(R.id.student_course_id);

        Bundle bundle = getIntent().getExtras();
        student_id = bundle.getString("sid");

        getSupportActionBar().setTitle(student_id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        dates.clear();
        dates.add("       Date          " + "p1  " + "p2  " + "p3  " + "p4   " + "p5   " + "p6  " + "p7  " + "p8");


        department = findViewById(R.id.studentattendanceSpinnerDepartment);

        department.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department.setAdapter(adapter);


        semester = findViewById(R.id.studentattendanceSpinnerSemester);

        semester.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        semester.setAdapter(adapter2);


//        one =findViewById(R.id.record5);




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.studentattendanceSpinnerDepartment) {
            departmentName = adapterView.getItemAtPosition(i).toString();
//            getCourseId();
        } else if (adapterView.getId() == R.id.studentattendanceSpinnerSemester) {
            semesterName = adapterView.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void GetStudentData(View view)

    {
        dates.clear();
       final String courseName=course.getText().toString().toUpperCase().trim();



        if(TextUtils.isEmpty(courseName))
        {
            Toast.makeText(getApplicationContext(),"Course code cannot be empty",Toast.LENGTH_LONG).show();
        }else
        {
            dbAttendance = ref.child("Attendance").child("Department").child(departmentName).child("Semester").child(semesterName).child(courseName);
            dbAttendance.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    if(dataSnapshot.exists())
                    {

                        dbAttendance = ref.child("Attendance").child("Department").child(departmentName).child("Semester").child(semesterName).child(courseName);
                        dbAttendance.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    p1 = dsp.child(student_id).child("p1").getValue().toString().substring(0, 1);

                                    dates.add(dsp.getKey() + "                                " + p1 + "     ");







                                }
                                list(dates);



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(),"Record not Found, check inputs",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }












    }

//    public void getCourseId()
//    {
//        courses=ref.child("Attendance");
//        courses.child("Department").child(departmentName).child("Semester").child(semesterName).child("CSE-01").child("courseId").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Is better to use a List, because you don't know the size
//                // of the iterator returned by dataSnapshot.getChildren() to
//                // initialize the array
//                List<String> areas = new ArrayList<String>();
//
//                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
//                    String areaName = areaSnapshot.getValue().toString();
//                    areas.add(areaName);
//                }
//
//                course = findViewById(R.id.studentattendanceSpinnerCourse);
//
//
//                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, areas);
//                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                course.setAdapter(areasAdapter);
//                course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        courseName = adapterView.getItemAtPosition(i).toString();
//                        Toast.makeText(getApplicationContext(), "courseId", Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(), "something wrong with courseid", Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//
//    }

    public void list(ArrayList studentlist){
        // Toast.makeText(this,NOP+TOC,Toast.LENGTH_LONG).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentlist);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
//        try {
//
//            average =(float)((NOP*100)/TOC);
//            String avg=Float.toString(average);
//            t.setText("Your Attendance is :"+avg+"%");
//            one.setText("20");
//            if(average>=75)
//                t.setTextColor(Color.GREEN);
//            if(average<75)
//                t.setTextColor(Color.RED);
//        }
//        catch (Exception e){e.printStackTrace();}
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
//
}
