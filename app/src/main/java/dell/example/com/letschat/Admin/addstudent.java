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

import dell.example.com.letschat.R;
import dell.example.com.letschat.UtilityClasses.Student;


public class addstudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText Sname;
    EditText Sid,spassword;
    String sname,sid,departmentName,semesterName,spass;
    Spinner spinnerDepartmentAddStudent,spinnerSemesterAddStudent;
    DatabaseReference databaseStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);

        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");

        Sname =  (EditText) findViewById(R.id.editText1);
        Sid =  (EditText) findViewById(R.id.editText3);
        //classes = (Spinner) findViewById(R.id.spinner3);
        spinnerDepartmentAddStudent=findViewById(R.id.spinner_department_add_student);
        spinnerSemesterAddStudent=findViewById(R.id.spinner_semester_add_student);
        spassword = (EditText) findViewById(R.id.editText4);


        getSupportActionBar().setTitle("Add Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerDepartmentAddStudent.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerDepartmentAddStudent.setAdapter(adapter);

        spinnerSemesterAddStudent.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerSemesterAddStudent.setAdapter(adapter2);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
         if(parent.getId()==R.id.spinner_department_add_student){
        departmentName = parent.getItemAtPosition(position).toString();}
         else if(parent.getId()==R.id.spinner_semester_add_student){
             semesterName=parent.getItemAtPosition(position).toString();
         }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public void addStudent(View v){

           if(TextUtils.isEmpty(Sname.getText().toString())){

               Toast.makeText(getApplicationContext(),"Student name cannot be empty", Toast.LENGTH_LONG).show();

        }else if(TextUtils.isEmpty(Sid.getText().toString())){
               Toast.makeText(getApplicationContext(),"Student id cannot be empty", Toast.LENGTH_LONG).show();

           }else if(TextUtils.isEmpty(spassword.getText().toString())){
               Toast.makeText(getApplicationContext(),"Student password cannot be empty", Toast.LENGTH_LONG).show();
           }
           else {
            //String id = databaseStudent.push().getKey();
            sname = Sname.getText().toString().trim();
            sid = Sid.getText().toString().toLowerCase().trim();
            spass = spassword.getText().toString().trim();


               databaseStudent.child("Logins").child(sid).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if(dataSnapshot.exists())
                       {
                           Toast.makeText(getApplicationContext(),"Student already exists",Toast.LENGTH_LONG).show();
                       }
                       else
                       {

                           Student student =new Student(sname ,sid );
                           databaseStudent.child("Department").child(departmentName).child("Semester").child(semesterName).child(sid).setValue(student);
                           databaseStudent.child("Logins").child(sid).setValue(spass);
                           Toast.makeText(getApplicationContext(),"student added successfully", Toast.LENGTH_LONG).show();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });





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
