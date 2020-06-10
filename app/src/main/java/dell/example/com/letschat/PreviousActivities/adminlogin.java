package dell.example.com.letschat.PreviousActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dell.example.com.letschat.Admin.AddCourse;
import dell.example.com.letschat.Attendance.admin_attendanceSheet;
import dell.example.com.letschat.LoginActivity;
import dell.example.com.letschat.R;
import dell.example.com.letschat.RemoveCourse;
import dell.example.com.letschat.Student.RemoveStudent;
import dell.example.com.letschat.Student.addstudent;
import dell.example.com.letschat.Teacher.AddCoursesTeacher;
import dell.example.com.letschat.Teacher.RemoveCoursesTeacher;
import dell.example.com.letschat.Teacher.RemoveTeacher;
import dell.example.com.letschat.Teacher.addteacher;

public class adminlogin extends AppCompatActivity {

    DatabaseReference ref;
    DatabaseReference dbStudent;
    DatabaseReference dbAttendance;
    DatabaseReference attendacne;
    DatabaseReference dbadmin;
    Toolbar mToolbar;
    private static long back_pressed;

    ArrayList Studentlist = new ArrayList<>();

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

//        mToolbar = (Toolbar) findViewById(R.id.ftoolbar);
//        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Admin");

        ref = FirebaseDatabase.getInstance().getReference();
        dbStudent = ref.child("Student").child("Department").child("CSE").child("Semester").child("Eighth");
        dbAttendance = ref.child("attendance");
        attendacne=ref.child("Attendance");






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adminlogin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id=item.getItemId();
       if(id==R.id.logout){
           Intent logout=new Intent(adminlogin.this,LoginActivity.class);

           startActivity(logout);
           return true;
       }

        return super.onOptionsItemSelected(item);
    }

    public void AddCourseButton(View v)
    {
        Intent intent=new Intent(this,AddCourse.class);
        startActivity(intent);
    }
    public void RemoveCourseButton(View v)
    {
        Intent intent=new Intent(this,RemoveCourse.class);
        startActivity(intent);
    }
    public void AddTeacherButton(View v){
        Intent intent = new Intent(this, addteacher.class);
        startActivity(intent);
    }

    public void RemoveTeacherButton(View v)
    {
        Intent intent=new Intent(this,RemoveTeacher.class);
        startActivity(intent);
    }
    public void AddStudentButton(View v){
        Intent intent = new Intent(this, addstudent.class);
        startActivity(intent);
    }

    public void RemoveStudentButton(View v)
    {
        Intent intent=new Intent(this,RemoveStudent.class);
        startActivity(intent);
    }

    public void attendanceRecord(View v){
        Intent intent = new Intent(this, admin_attendanceSheet.class);
        startActivity(intent);
    }

    public void addCourseToTeacher(View v)
    {
        Intent intent=new Intent(this,AddCoursesTeacher.class);
        startActivity(intent);
    }

    public void removeCourseToTeacher(View v)
    {
        Intent intent=new Intent(this,RemoveCoursesTeacher.class);
        startActivity(intent);
    }










    public void logout(View v) {

        Intent logout=new Intent(adminlogin.this,LoginActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);

    }

    public void changepassword(View view) {
        dbadmin=ref.child("Admin").child("Logins").child("Admin");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Type your new password");
        final LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.changepassword, null);
        final EditText password=(EditText)add_menu_layout.findViewById(R.id.newpassword);
        alertDialog.setView(add_menu_layout);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(password.getText().toString()))
                {
                    dbadmin.setValue(password.getText().toString());
                    Toast.makeText(adminlogin.this, "Successfully Changed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(adminlogin.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
                }



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
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

}

/*firebaseDatabase.getReference("parent")
                            .orderByChild("childNode")
                            .startAt("[a-zA-Z0-9]*")
                            .endAt(searchString)


*/