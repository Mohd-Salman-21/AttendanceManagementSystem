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

public class RemoveStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText Sid;
    String sid,removeDepartmentName,removeSemesterName;
    Spinner removeDepartment,removeSemester;
    DatabaseReference databaseStudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_student);

        databaseStudent=FirebaseDatabase.getInstance().getReference().child("Student");


        Sid =  (EditText) findViewById(R.id.enrollmentRemoveStudent);
        removeDepartment=findViewById(R.id.spinnerDepartmentRemoveStudent);


        removeDepartment.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        removeDepartment.setAdapter(adapter);



        removeSemester=findViewById(R.id.spinnerSemesterRemoveStudent);
        removeSemester.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
         // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         // Apply the adapter to the spinner
        removeSemester.setAdapter(adapter2);


        getSupportActionBar().setTitle("Remove Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if(parent.getId()==R.id.spinnerDepartmentRemoveStudent) {
            removeDepartmentName = parent.getItemAtPosition(position).toString();
        }
        else if(parent.getId()==R.id.spinnerSemesterRemoveStudent) {
            removeSemesterName = parent.getItemAtPosition(position).toString();
        }



        // Showing selected spinner item
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void removeStudent(View v){




        if (!TextUtils.isEmpty(Sid.getText().toString())) {
            sid = Sid.getText().toString().toLowerCase().trim();

            databaseStudent.child("Department").child(removeDepartmentName).child("Semester").child(removeSemesterName).child(sid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        databaseStudent.child("Department").child(removeDepartmentName).child("Semester").child(removeSemesterName).child(sid).setValue(null);
                        databaseStudent.child("Logins").child(sid).setValue(null);
                        Toast.makeText(getApplicationContext(),"Student removed successfully", Toast.LENGTH_LONG).show();

                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Student doesn't exist", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


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
