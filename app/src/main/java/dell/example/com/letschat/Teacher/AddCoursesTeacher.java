package dell.example.com.letschat.Teacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import dell.example.com.letschat.R;

public class AddCoursesTeacher extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    EditText techerId,courseCode;
    String courseCodeName,departmentName;
    Spinner department;
    DatabaseReference databaseReference;


    ArrayList<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses_teacher);


        techerId=findViewById(R.id.teacher_id_course_teacher);
        courseCode=findViewById(R.id.course_code_addCourseTeacher);

        databaseReference=FirebaseDatabase.getInstance().getReference();


        department=findViewById(R.id.spinner_department_teacher_course);


        department.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department.setAdapter(adapter);


    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        departmentName=adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public  void addCourseToTeacher(View view)
    {
        String id=techerId.getText().toString();
        courseCodeName=courseCode.getText().toString();
        list.add(courseCodeName);
        databaseReference.child("Teacher").child("Department").child(departmentName).child(id).child("courses").setValue(list);
        Toast.makeText(getApplicationContext(),"Course Added Successfully",Toast.LENGTH_LONG).show();
    }


}
