package dell.example.com.letschat.Student;

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
import java.util.Date;

import dell.example.com.letschat.LoginActivity;
import dell.example.com.letschat.R;

public class studentlogin extends AppCompatActivity {
    String message;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    DatabaseReference dbadmin;
    DatabaseReference ref;
    Toolbar mToolbar;

    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);

        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");

        ref = FirebaseDatabase.getInstance().getReference();

        getSupportActionBar().setTitle(message);





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
            Intent logout=new Intent(studentlogin.this,LoginActivity.class);

            startActivity(logout);
          return  true;}

        return super.onOptionsItemSelected(item);
    }
    public void viewAttendance(View v){
        Bundle basket = new Bundle();
        basket.putString("sid", message);

        Intent intent = new Intent(this, student_attendance_sheet.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void CourseRecord(View v)
    {
        Bundle basket = new Bundle();
        basket.putString("sid", message);

        Intent intent = new Intent(this, CourseRecord.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void logoutStudent(View view) {
        Intent logoutStudent=new Intent(studentlogin.this,LoginActivity.class);
        logoutStudent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logoutStudent);
    }


    public void changepasswordTeacher(View view) {
        dbadmin = ref.child("Student").child("Logins").child(message);

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
                    Toast.makeText(studentlogin.this, "Successfully Changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(studentlogin.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
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
