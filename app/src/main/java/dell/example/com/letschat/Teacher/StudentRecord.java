package dell.example.com.letschat.Teacher;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dell.example.com.letschat.R;

public class StudentRecord extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    Spinner semester,department,courseId;
    String semesterName,departmentName,courseIdName;
    ArrayList dates = new ArrayList<>();
    String teacher_id,enroll,p1
            ;
    public  int absent=0,present=0,total=0;
    float percent=(float) 0.0;
    ListView listView;
    EditText date,editText;

    TextView one,two,three,four;




    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent,courses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_record);

        getSupportActionBar().setTitle("Student Record");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle1 = getIntent().getExtras();

        teacher_id = bundle1.getString("tid");


         editText=findViewById(R.id.studentRecordenroll);
        department = findViewById(R.id.studendRecordSpinnerDepartment);

        department.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department.setAdapter(adapter);


        semester = findViewById(R.id.studentRecordSpinnerSemester);

        semester.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        semester.setAdapter(adapter2);



         one=findViewById(R.id.record5teacher);
         two=findViewById(R.id.record6teacher);

         three=findViewById(R.id.record7teacher);

         four=findViewById(R.id.record8teacher);






    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.studendRecordSpinnerDepartment)
        {
            departmentName=adapterView.getItemAtPosition(i).toString();
            courseSelectionStudentRecord();
        }else if(adapterView.getId()==R.id.studentRecordSpinnerSemester)
        {
            semesterName=adapterView.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    public void courseSelectionStudentRecord() {

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

                courseId = findViewById(R.id.studentRecordSpinnerCourse);


                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, areas);
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


    public void studentRecordCalculate(View v) {

            present=total=absent=0;
            percent=(float)0.0;
        enroll = editText.getText().toString().toUpperCase();


        dbAttendance = ref.child("Attendance").child("Department").child(departmentName).child("Semester").child(semesterName).child(courseIdName);
        dbAttendance.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    p1 = dsp.child(enroll).child("p1").getValue().toString().substring(0, 1);

                    if(p1.equals("A")){
                        absent++;
                    }else
                        present++;

                }

                total = absent + present;

                String onem=Integer.toString(total);
                one.setText(onem);
                one.setTextColor(Color.BLACK);
//
                String twom=Integer.toString(present);
                two.setText(twom);
                two.setTextColor(Color.GREEN);

                String threem=Integer.toString(absent);
                three.setText(threem);
                three.setTextColor(Color.RED);

                percent = (float)((present*100)/total);
                String fourm = Float.toString(percent);
                four.setText(fourm+" %");
                if(percent>=75)
                    four.setTextColor(Color.GREEN);
                if(percent<75)
                    four.setTextColor(Color.RED);


                //  Toast.makeText(getApplicationContext(), dates.toString(), Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });


                }





                    @Override
                    public boolean onOptionsItemSelected (MenuItem item){
                        if (item.getItemId() == android.R.id.home) {
                            finish();
                        }
                        return super.onOptionsItemSelected(item);
                    }


                }

