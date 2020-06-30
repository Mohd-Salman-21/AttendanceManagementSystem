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

public class RemoveTeacher extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    String tid,departmentName;
    EditText Tid;
    Spinner spinner;

    DatabaseReference databaseTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_teacher);

         getSupportActionBar().setTitle("Remove Teacher");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tid=findViewById(R.id.teacherIdRemoveTeacher);

        databaseTeacher=FirebaseDatabase.getInstance().getReference();

        spinner=findViewById(R.id.spinnerDepartmentRemoveTeacher);

         spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);





    }


    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

            departmentName= parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void removeTeacherButton(View v){
        if (!TextUtils.isEmpty(Tid.getText().toString())) {
            tid = Tid.getText().toString().toLowerCase().trim();

            databaseTeacher.child("Teacher").child("Department").child(departmentName).child(tid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        databaseTeacher.child("Teacher").child("Department").child(departmentName).child(tid).setValue(null);
                        databaseTeacher.child("Teacher").child("Logins").child(tid).setValue(null);
                        Toast.makeText(getApplicationContext(),"Teacher removed successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Teacher doesn't exist", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }else {
            Toast.makeText(getApplicationContext(),"Id cannot be empty", Toast.LENGTH_LONG).show();
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
