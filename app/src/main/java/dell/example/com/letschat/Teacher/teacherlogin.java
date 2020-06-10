package dell.example.com.letschat.Teacher;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import dell.example.com.letschat.Attendance.takeAttendance;
import dell.example.com.letschat.Attendance.teacher_attendanceSheet;
import dell.example.com.letschat.LoginActivity;
import dell.example.com.letschat.R;

public class teacherlogin extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String item;
    String message;
    Toolbar mToolbar;
    DatabaseReference dbadmin;
    DatabaseReference ref;
    private static long back_pressed;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlogin);
        //Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);


        //to get username from login page
        Bundle bundle1 = getIntent().getExtras();
        message = bundle1.getString("message");



        getSupportActionBar().setTitle(message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ref = FirebaseDatabase.getInstance().getReference();
//        // Spinner click listener
//        spinner2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
//
//        // Spinner Drop down elements
//        List<String> categories = new ArrayList<String>();
//        categories.add("IT-A");
//        categories.add("IT-B");
//        categories.add("IT-C");
//        categories.add("IT-D");
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner2.setAdapter(dataAdapter);
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
            Intent logout=new Intent(teacherlogin.this,LoginActivity.class);

            startActivity(logout);
            return true;
        }else if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void takeAttendanceButton(View v){
        Bundle basket= new Bundle();

        basket.putString("tid", message);


        Intent intent = new Intent(this, takeAttendance.class);
        intent.putExtras(basket);
        startActivity(intent);
    }
    public void  previous_records(View v){
        Bundle basket= new Bundle();
        basket.putString("class_selected", item);
        basket.putString("tid", message);


        Intent intent = new Intent(this, teacher_attendanceSheet.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void changepasswordTeacher(View view) {
        dbadmin = ref.child("Teacher").child("Logins").child(message);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Type your new password");
        final LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.changepassword, null);
        final EditText password = (EditText) add_menu_layout.findViewById(R.id.newpassword);
        alertDialog.setView(add_menu_layout);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(password.getText().toString())) {
                    dbadmin.setValue(password.getText().toString());
                    Toast.makeText(teacherlogin.this, "Successfully Changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(teacherlogin.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
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
