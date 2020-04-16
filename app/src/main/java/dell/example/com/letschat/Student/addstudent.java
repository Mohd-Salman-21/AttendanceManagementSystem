package dell.example.com.letschat.Student;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dell.example.com.letschat.R;
import dell.example.com.letschat.UtilityClasses.Student;


public class addstudent extends AppCompatActivity {
    EditText Sname;
    EditText Sid,spassword;
    String sname,sid,departmentName,semesterName,spass;
    Spinner classes,department,semester;
    DatabaseReference databaseStudent;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);

        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");

        Sname =  (EditText) findViewById(R.id.editText1);
        Sid =  (EditText) findViewById(R.id.editText3);
        //classes = (Spinner) findViewById(R.id.spinner3);
        department=findViewById(R.id.spinnerDepartment);
        semester=findViewById(R.id.spinnerSemester);
        spassword = (EditText) findViewById(R.id.editText4);
        mToolbar=(Toolbar)findViewById(R.id.ftoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add/Remove Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       /* addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                addTeacher();
            }
        });*/
    }

    public void addStudent(View v){


        if (!(TextUtils.isEmpty(Sid.getText().toString()))) {
            //String id = databaseStudent.push().getKey();
            sname = Sname.getText().toString();
            sid = Sid.getText().toString();
            departmentName = department.getSelectedItem().toString();
            semesterName=semester.getSelectedItem().toString();
            spass = spassword.getText().toString();

            Student student =new Student(sname ,sid,spass,semesterName,departmentName );
            databaseStudent.child("Department").child(departmentName).child("Semester").child(semesterName).child(sid).setValue(student);
            Toast.makeText(getApplicationContext(),"student added successfully", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getApplicationContext(),"fields cannot be empty", Toast.LENGTH_LONG).show();
        }
    }
    public void removeStudent(View v){
        if (!TextUtils.isEmpty(Sid.getText().toString())) {
            sid = Sid.getText().toString();
            databaseStudent.child(sid).setValue(null);
            Toast.makeText(getApplicationContext(),"teacher removed successfully", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getApplicationContext(),"id cannot be empty", Toast.LENGTH_LONG).show();
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
