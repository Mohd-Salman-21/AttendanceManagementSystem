package dell.example.com.letschat.Admin;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import dell.example.com.letschat.UtilityClasses.Teacher;


public class addteacher extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText Tname;
    EditText Tid;
    EditText tpassword;
    String tname,tid,tpass,departmentNameAddTeacher;

    DatabaseReference databaseTeacher;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteacher);

        databaseTeacher = FirebaseDatabase.getInstance().getReference();

        Tname=findViewById(R.id.teacherNameAddTeacher);
        Tid =  (EditText) findViewById(R.id.teacherIdAddTeacher);
        tpassword =  (EditText) findViewById(R.id.passwordAddTeacher);

        getSupportActionBar().setTitle("Add Teacher");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Spinner spinner=findViewById(R.id.spinnerDepartmentAddTeacher);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);








    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

            departmentNameAddTeacher = parent.getItemAtPosition(position).toString();





        // Showing selected spinner item
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void addTeacher(View v){
        tname = Tname.getText().toString();
        tid = Tid.getText().toString();
        tpass = tpassword.getText().toString();
         if(TextUtils.isEmpty(Tname.getText().toString()))
         {
             Toast.makeText(getApplicationContext(),"Teacher name cannot be empty", Toast.LENGTH_LONG).show();
         }else if(TextUtils.isEmpty(Tid.getText().toString()))
         {
             Toast.makeText(getApplicationContext(),"Teacher id cannot be empty", Toast.LENGTH_LONG).show();

         }else if(TextUtils.isEmpty(tpassword.getText().toString())){
             Toast.makeText(getApplicationContext(),"Password cannot be empty", Toast.LENGTH_LONG).show();
         }
          else{

            Teacher teacher =new Teacher(tname.toUpperCase() ,tid.toUpperCase()  ,"Select Course");
            databaseTeacher.child("Teacher").child("Department").child(departmentNameAddTeacher).child(tid).setValue(teacher);
            databaseTeacher.child("Teacher").child("Logins").child(tid.toUpperCase()).setValue(tpass);
            Toast.makeText(getApplicationContext(),"Teacher added successfully", Toast.LENGTH_LONG).show();
            finish();

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
