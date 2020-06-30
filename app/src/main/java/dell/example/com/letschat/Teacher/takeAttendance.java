package dell.example.com.letschat.Teacher;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dell.example.com.letschat.R;
import dell.example.com.letschat.UtilityClasses.Attendance_sheet;


public class takeAttendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {




    Spinner department;
    Spinner semester, courseSpinner;
    String attenDepartmentName, attenCourseId, teacher;
    String attenSemesterName;
    DatabaseReference dbAttendance, courses;

    DatabaseReference dbStudent, dbuser,dbTeacher;

    String teacher_id;

    ArrayList<String> selectedItems;
    ArrayList<String> nonselectedItems;
    boolean flag=true;
    boolean flagt=true;
    boolean check=true;




    ArrayList Userlist = new ArrayList<>();

    List<String> areas = new ArrayList<String>();

    DatabaseReference ref;

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);


        getSupportActionBar().setTitle("Take Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ref = FirebaseDatabase.getInstance().getReference();
        dbAttendance = ref.child("Attendance");

        dbStudent = ref.child("Student");
        dbuser = ref.child("Student");
        courses = ref.child("Teacher");





        //to get class name from teacherlogin
        Bundle bundle1 = getIntent().getExtras();
        teacher_id = bundle1.getString("tid");


        department = findViewById(R.id.attendanceSpinnerDepartment);

        department.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department.setAdapter(adapter);


        semester = findViewById(R.id.attendanceSpinnerSemester);

        semester.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        semester.setAdapter(adapter2);


        // ArrayList Userlist;


    }


    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if (parent.getId() == R.id.attendanceSpinnerDepartment) {
            attenDepartmentName = parent.getItemAtPosition(position).toString();
              courseSelectionFirst();

        } else if (parent.getId() == R.id.attendanceSpinnerSemester) {
            attenSemesterName = parent.getItemAtPosition(position).toString();

        }



    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "nothing selected", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void courseSelectionFirst() {
        courses=ref.child("Teacher");


        courses.child("Department").child(attenDepartmentName).child(teacher_id).child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
//                 List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.getValue(String.class);
                    areas.add(areaName);
                }

                courseSpinner = findViewById(R.id.attendanceSpinnerCourse);


                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseSpinner.setAdapter(areasAdapter);
                courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        attenCourseId = adapterView.getItemAtPosition(i).toString();



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

   public void CreateAttendance(View view)
    {
        dbTeacher=ref.child("Teacher").child("Department").child(attenDepartmentName).child(teacher_id);
        dbTeacher.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    gofurther();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Select correct department for teacher first",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void gofurther(){




        selectedItems = new ArrayList<String>();


        dbAttendance = ref.child("Attendance");

        dbAttendance.child("Department").child(attenDepartmentName).child("Semester").child(attenSemesterName).child(attenCourseId).child(date).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Toast.makeText(getApplicationContext(), "Already created " + date + " db", Toast.LENGTH_LONG).show();
                }
                else
                {

                    flag=false;
                    dbStudent.child("Department").child(attenDepartmentName).child("Semester").child(attenSemesterName).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {



                            String sid, P1 = "-";
                            Attendance_sheet a = new Attendance_sheet(P1);

                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                sid = dsp.child("sid").getValue().toString();//add result into array list
                                dbAttendance.child("Department").child(attenDepartmentName).child("Semester").child(attenSemesterName).child(attenCourseId).child(date).child(sid).setValue(a);
                            }
                            Toast.makeText(getApplicationContext(), "successfully created " + date + " db", Toast.LENGTH_LONG).show();
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                        }

                    });


                    dbuser.child("Department").child(attenDepartmentName).child("Semester").child(attenSemesterName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            // Result will be holded Here

                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                Userlist.add(dsp.child("sid").getValue().toString()); //add result into array list



                            }
                            showfn(Userlist);


                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                        }

                    });

                }



            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });



    }



    public void showfn(ArrayList<String> userlist) {


        nonselectedItems = userlist;
        //create an instance of ListView
        ListView chl = (ListView) findViewById(R.id.checkable_list);
        //set multiple selection mode
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //supply data itmes to ListView
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.checkable_list_layout, R.id.txt_title, userlist);
        chl.setAdapter(aa);
        //set OnItemClickListener
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
                else
                    selectedItems.add(selectedItem); //add selected item to the list of selected items

            }

        });




    }

    public void showSelectedItems(View view) {





        if (flag == true) {
            Toast.makeText(getApplicationContext(), "First Create Today's Attendance Sheet", Toast.LENGTH_LONG).show();
        } else {





            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Sure you want to Submit");


            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(final DialogInterface dialog, int which) {



                    String selItems = "";


                    dbAttendance = ref.child("Attendance").child("Department").child(attenDepartmentName).child("Semester").child(attenSemesterName).child(attenCourseId).child(date);


                    for (String item : selectedItems) {
                        Toast.makeText(getApplicationContext(), "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                        nonselectedItems.remove(item);
                        dbAttendance.child(item).child("p1").setValue("P");
                        if (selItems == "")
                            selItems = item;
                        else
                            selItems += "/" + item;
                    }
                    // Toast.makeText(this, selItems, Toast.LENGTH_LONG).show();


                    //for making absent
                    for (String item : nonselectedItems) {
                        Toast.makeText(getApplicationContext(), "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                        dbAttendance.child(item).child("p1").setValue("A");
                        //Toast.makeText(this,"absentees:" + nonselectedItems, Toast.LENGTH_LONG).show();

                    }

                    flag=true;


                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();


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


