package dell.example.com.letschat.Attendance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;

import dell.example.com.letschat.R;


public class teacher_attendanceSheet extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{


    // Added by me

    Spinner semester,department,courseId;
    String semesterName,departmentName,courseIdName;









    ListView listView;
    String teacher_id,class_selected;


    EditText date;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Studentlist = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent,courses;
    String required_date;
    android.support.v7.widget.Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_sheet);

        listView = (ListView) findViewById(R.id.list);

        getSupportActionBar().setTitle("Previous Record");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle1 = getIntent().getExtras();
//        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");




        department = findViewById(R.id.teacherattendanceSpinnerDepartment);

        department.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department.setAdapter(adapter);


        semester = findViewById(R.id.teacherattendanceSpinnerSemester);

        semester.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        semester.setAdapter(adapter2);
































    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           if(adapterView.getId()==R.id.teacherattendanceSpinnerDepartment)
           {
               departmentName=adapterView.getItemAtPosition(i).toString();
               courseSelection();
           }else if(adapterView.getId()==R.id.teacherattendanceSpinnerSemester)
           {
               semesterName=adapterView.getItemAtPosition(i).toString();
           }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void courseSelection() {

      courses=ref.child("Teacher");
        courses.child("Department").child(departmentName).child(teacher_id).child("courses").addValueEventListener(new ValueEventListener() {
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

                courseId = findViewById(R.id.teacherattendanceSpinnerCourse);


                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseId.setAdapter(areasAdapter);
                courseId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        courseIdName = adapterView.getItemAtPosition(i).toString();
                        Toast.makeText(getApplicationContext(), "courseId", Toast.LENGTH_LONG).show();

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




    public void viewlist(View v) {

        Userlist.clear();
        dbStudent = ref.child("Student").child("Department").child(departmentName).child("Semester").child(semesterName);
        dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString()); //add result into array list
                }
                display_list(Userlist);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void display_list(final ArrayList userlist) {

        Studentlist.clear();
        required_date = date.getText().toString();
        dbAttendance = ref.child("Attendance").child("Department").child(departmentName).child("Semester").child(semesterName).child(courseIdName);
        Studentlist.add("      Enrollment             ");
        for (Object sid : userlist) {
            dbAttendance.child(required_date).child(sid.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Result will be holded Here

                    //DataSnapshot dsp=dataSnapshot.child(sid.toString());
                    for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                        String p1 = dsp.getValue().toString();
                        //Toast.makeText(getApplicationContext(),p1,Toast.LENGTH_LONG).show();
                        if((p1.equals("A"))||(p1.equals("P"))){
                            Studentlist.add(dataSnapshot.getKey().toString() + "            " + p1.substring(0,1));
                        }
                    }
                  /*  String p2=dataSnapshot.child(sid.toString()).child("p2").getValue().toString().substring(0,1);
                    String p3=dataSnapshot.child(sid.toString()).child("p3").getValue().toString().substring(0,1);
                    String p4=dataSnapshot.child(sid.toString()).child("p4").getValue().toString().substring(0,1);
                    String p5=dataSnapshot.child(sid.toString()).child("p5").getValue().toString().substring(0,1);
                    String p6=dataSnapshot.child(sid.toString()).child("p6").getValue().toString().substring(0,1);
                    String p7=dataSnapshot.child(sid.toString()).child("p7").getValue().toString().substring(0,1);
                    String p8=dataSnapshot.child(sid.toString()).child("p8").getValue().toString().substring(0,1);
                */
                    //  Studentlist.add(dataSnapshot.getKey().toString() + "    " + p1); //add result into array list


                    //Toast.makeText(getApplicationContext(),Studentlist.toString(), Toast.LENGTH_LONG).show();
                    list(Studentlist);

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }

            });


        }

    }
    public void list(ArrayList studentlist){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentlist);
        // Assign adapter to ListView
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
