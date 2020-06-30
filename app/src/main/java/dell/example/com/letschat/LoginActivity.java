package dell.example.com.letschat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.List;

import dell.example.com.letschat.Admin.adminlogin;
import dell.example.com.letschat.Student.studentlogin;
import dell.example.com.letschat.Teacher.teacherlogin;


public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText username,password;
    String item;
    String userid,pass;
    DatabaseReference ref;
    String dbpassword;
    Bundle basket;
    ProgressDialog mDialog;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username =  (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.editText2);



        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        List<String> categories = new ArrayList<String>();
        categories.add("Admin");
        categories.add("Teacher");
        categories.add("Student");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(dataAdapter);

    }




    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        item = parent.getItemAtPosition(position).toString();




    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public void onButtonClick(View v) {

        userid = username.getText().toString().trim();
        pass = password.getText().toString().trim();

        if (TextUtils.isEmpty(userid)) {
            Toast.makeText(getApplicationContext(), "User Id cannot be empty", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "password cannot be empty", Toast.LENGTH_LONG).show();
        } else {

            mDialog = new ProgressDialog(this);
            mDialog.setMessage("Please Wait..." + userid);
            mDialog.setTitle("Loading");
            mDialog.show();
            basket = new Bundle();
            basket.putString("message", userid);

            ref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference dbuser = ref.child(item).child("Logins").child(userid);

            dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    try {
                        if (item == "Admin") {
                            mDialog.dismiss();
                            dbpassword = dataSnapshot.getValue(String.class);
                            verify(dbpassword);


                        }
                            dbpassword = dataSnapshot.getValue(String.class);
                            verify(dbpassword);
                            //do what you want with the email

                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    //Toast.makeText(getApplicationContext(),dbpassword, Toast.LENGTH_LONG).show();

    public void verify(String dbpassword){

        if (item == "Teacher" && pass.equals(this.dbpassword)) {

            mDialog.dismiss();

            Intent intent = new Intent(this, teacherlogin.class);
            intent.putExtras(basket);
            startActivity(intent);

        }

        else if (item == "Admin" && pass.equals(this.dbpassword) ) {

            mDialog.dismiss();
            Intent intent = new Intent(this, adminlogin.class);
            intent.putExtras(basket);
            startActivity(intent);

        }
        else if (item == "Student" && pass.equals(this.dbpassword)) {
            mDialog.dismiss();
            Intent intent = new Intent(this, studentlogin.class);
            intent.putExtras(basket);
            startActivity(intent);
        }
        else if(! pass.equals(this.dbpassword)){
            Toast.makeText(getApplicationContext(),"UserId or Password is Incorrect", Toast.LENGTH_LONG).show();
            mDialog.dismiss();

        }


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
