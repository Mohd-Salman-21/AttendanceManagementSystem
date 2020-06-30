package dell.example.com.letschat.Teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import dell.example.com.letschat.adapter.ClassRecordAdapter;

public class ClassRecord extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    RecyclerView recyclerView;


    Spinner semester,department,courseId;
    String semesterName,departmentName,courseIdName;
    Object object;



    public   int absent=0,present=0,total=0;
    float percent=(float) 0.0;
    ListView listView;
    EditText date,editText;

    TextView one,two,three,four;
    boolean flag=true;


     ArrayList pres=new ArrayList<>();
     ArrayList abs=new ArrayList<>();





    String teacher_id,class_selected,p1;



    ArrayList Userlist = new ArrayList<>();
    ArrayList Studentlist = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent,courses,dbTeacher;
    String required_date;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_record);


        getSupportActionBar().setTitle("CLASS RECORD");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        Bundle bundle1 = getIntent().getExtras();
//        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");




        department = findViewById(R.id.classRecordSpinnerDepartment);

        department.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department.setAdapter(adapter);


        semester = findViewById(R.id.classRecordSpinnerSemester);

        semester.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.semester, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        semester.setAdapter(adapter2);




//
//        one=findViewById(R.id.record5_class_record);
//        two=findViewById(R.id.record6_class_record);
//
//        three=findViewById(R.id.record7_class_record);
//
//        four=findViewById(R.id.record8_class_record);


































    }


    public void onStart(ArrayList arrayList) {


        recyclerView=findViewById(R.id.classRecordRecyclerView);


        ClassRecordAdapter classRecordAdapter=new ClassRecordAdapter(getApplicationContext(),arrayList,departmentName,semesterName,courseIdName);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(classRecordAdapter);

//
//        total = absent + present;
//
//        String onem=Integer.toString(total);
//        one.setText(onem);
//        one.setTextColor(Color.BLACK);
////
//        String twom=Integer.toString(present);
//        two.setText(twom);
//        two.setTextColor(Color.GREEN);
//
//        String threem=Integer.toString(absent);
//        three.setText(threem);
//        three.setTextColor(Color.RED);
//
//        percent = (float)((present*100)/total);
//        String fourm = Float.toString(percent);
//        four.setText(fourm+" %");
//        if(percent>=75)
//            four.setTextColor(Color.GREEN);
//        if(percent<75)
//            four.setTextColor(Color.RED);




    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.classRecordSpinnerDepartment)
        {
            departmentName=adapterView.getItemAtPosition(i).toString();
              courseSelectionClassRecord();

        }else if(adapterView.getId()==R.id.classRecordSpinnerSemester)
        {
            semesterName=adapterView.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void checkTeacher(View view)
    {

        dbTeacher=ref.child("Teacher").child("Department").child(departmentName).child(teacher_id);
        dbTeacher.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                   viewlistClassRecord();
                }else{
                    Toast.makeText(getApplicationContext(),"Teacher is not present in selected department",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void courseSelectionClassRecord() {





            courses = ref.child("Teacher");
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

                    courseId = findViewById(R.id.classRecordSpinnerCourse);


                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, areas);
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







    public void viewlistClassRecord() {





        Userlist.clear();

        dbAttendance = ref.child("Attendance").child("Department").child(departmentName).child("Semester").child(semesterName).child(courseIdName);
        dbAttendance.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
              if(dataSnapshot.exists()){

                  setContentView(R.layout.activity_class_record_display);

                  dbStudent = ref.child("Student").child("Department").child(departmentName).child("Semester").child(semesterName);
                  dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          // Result will be holded Here
                          for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                              Userlist.add(dsp.child("sid").getValue().toString()); //add result into array list

                          }
                          display_listClassRecord(Userlist);


                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {
                          Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                      }

                  });


              }else
              {
                  Toast.makeText(getApplicationContext(),"Record not found: Double check with inputs",Toast.LENGTH_LONG).show();
              }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });











    }

    public void display_listClassRecord(final ArrayList userlist) {


        onStart(userlist);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }








}
