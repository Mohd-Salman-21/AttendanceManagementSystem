package dell.example.com.letschat.Teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dell.example.com.letschat.LoginActivity;
import dell.example.com.letschat.R;

public class teacherlogin extends AppCompatActivity{
    String item;
    String message;

    DatabaseReference dbadmin;

   private FirebaseAuth mAuth;
    DatabaseReference ref;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlogin);


        mAuth=FirebaseAuth.getInstance();

        //to get username from login page
        Bundle bundle1 = getIntent().getExtras();
        message = bundle1.getString("message");


        getSupportActionBar().setTitle(message);


        ref = FirebaseDatabase.getInstance().getReference();

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


    public void studentRecord(View v)
    {
        Bundle basket= new Bundle();

        basket.putString("tid", message);

        Intent intent = new Intent(this, StudentRecord.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void classRecord(View v)
    {
        Bundle basket= new Bundle();

        basket.putString("tid", message);
        Intent intent = new Intent(this, ClassRecord.class);
        intent.putExtras(basket);
        startActivity(intent);












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
        final EditText confirmPassword=add_menu_layout.findViewById(R.id.confirmpassword);
        alertDialog.setView(add_menu_layout);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(password.getText().toString().trim()))
                {
                    Toast.makeText(teacherlogin.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                }else  if(TextUtils.isEmpty(confirmPassword.getText().toString().trim()))
                {
                    Toast.makeText(teacherlogin.this, "Confirm Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(confirmPassword.getText().toString().trim().equals(password.getText().toString().trim())){
                    dbadmin.setValue(password.getText().toString().trim());
                    Toast.makeText(teacherlogin.this, "Successfully Changed", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(teacherlogin.this, "Password and confirm password doesnt match", Toast.LENGTH_SHORT).show();
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
