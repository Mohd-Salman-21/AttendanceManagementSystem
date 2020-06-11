package dell.example.com.letschat.Attendance;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dell.example.com.letschat.R;


public class takeAttendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // written by salman


    Spinner department;
    Spinner semester, courseId, courseSpinner;
    String attenDepartmentName, attenCourseId;
    String attenSemesterName;
    DatabaseReference dbAttendance, courses;

    DatabaseReference dbStudent, dbuser;

    // ends here

    String teacher_id;
    String class_selected;
    Spinner period, courseSelected;
    String periodno;
    ArrayList<String> selectedItems;
    ArrayList<String> nonselectedItems;
    Toolbar mToolbar;

    ArrayList<String> ul;
    ListView listView;
    private ArrayAdapter adapter;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Usernames = new ArrayList<>();
    List<String> areas = new ArrayList<String>();

    DatabaseReference ref;

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);


        getSupportActionBar().setTitle("Take Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ref = FirebaseDatabase.getInstance().getReference();
        dbAttendance = ref.child("Attendance");
        dbStudent = ref.child("Student");
        dbuser = ref.child("Student");
        courses = ref.child("Teacher");





        //to get class name from teacherlogin
        Bundle bundle1 = getIntent().getExtras();
        teacher_id = bundle1.getString("tid");


        department = findViewById(R.id.attendanceSpinnerDepartment);

        department.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department.setAdapter(adapter);


        semester = findViewById(R.id.attendanceSpinnerSemester);

        semester.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        semester.setAdapter(adapter2);


        // ArrayList Userlist;
        selectedItems = new ArrayList<String>();


    }


    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if (parent.getId() == R.id.attendanceSpinnerDepartment) {
            attenDepartmentName = parent.getItemAtPosition(position).toString();


            courseSelection();

        } else if (parent.getId() == R.id.attendanceSpinnerSemester) {
            attenSemesterName = parent.getItemAtPosition(position).toString();

        }
//       }else if(parent.getId()==R.id.attendanceSpinnerCourse)
//       {
//           attenCourseId=parent.getItemAtPosition(position).toString();
//           Toast.makeText(getApplicationContext(),"courseId",Toast.LENGTH_LONG).show();
//       }


        // Showing selected spinner item
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "nothng selected", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void courseSelection() {


        courses.child("Department").child(attenDepartmentName).child(teacher_id).child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
//                 List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.getValue(String.class);
                    areas.add(areaName);
                }

                courseSpinner = findViewById(R.id.attendanceSpinnerCourse);


                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseSpinner.setAdapter(areasAdapter);
                courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        attenCourseId = adapterView.getItemAtPosition(i).toString();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something wrong with courseid", Toast.LENGTH_LONG).show();

            }
        });


    }

    public void CreateAttendance(View v) {


        //Toast.makeText(getApplicationContext(),date, Toast.LENGTH_LONG).show();
        //dbStudent.child("Department").child("CSE").child("Semester").child("Eighth");

        dbStudent.child("Department").child(attenDepartmentName).child("Semester").child(attenSemesterName).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sid, P1 = "-";
                Attendance_sheet a = new Attendance_sheet(P1);
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    sid = dsp.child("sid").getValue().toString();//add result into array list
                    dbAttendance.child("Department").child(attenDepartmentName).child("Semester").child(attenSemesterName).child(attenCourseId).child(date).child(sid).setValue(a);
                }
                Toast.makeText(getApplicationContext(), "successfully created " + date + " db", Toast.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });


        dbuser.child("Department").child(attenDepartmentName).child("Semester").child(attenSemesterName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // Result will be holded Here

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString()); //add result into array list
                    Usernames.add(dsp.child("sname").getValue().toString());


                }
                showfn(Userlist);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });


    }

    public void showfn(ArrayList<String> userlist) {


        nonselectedItems = userlist;
        //create an instance of ListView
        ListView chl = (ListView) findViewById(R.id.checkable_list);
        //set multiple selection mode
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //supply data itmes to ListView
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.checkable_list_layout, R.id.txt_title, userlist);
        chl.setAdapter(aa);
        //set OnItemClickListener
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
                else
                    selectedItems.add(selectedItem); //add selected item to the list of selected items

            }

        });


    }

    public void showSelectedItems(View view) {
        String selItems = "";


        dbAttendance = ref.child("Attendance").child("Department").child(attenDepartmentName).child("Semester").child(attenSemesterName).child(attenCourseId).child(date);


        for (String item : selectedItems) {
            Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
            nonselectedItems.remove(item);
            dbAttendance.child(item).child("p1").setValue("P");
            if (selItems == "")
                selItems = item;
            else
                selItems += "/" + item;
        }
        // Toast.makeText(this, selItems, Toast.LENGTH_LONG).show();


        //for making absent
        for (String item : nonselectedItems) {
            Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
            dbAttendance.child(item).child("p1").setValue("A");
            //Toast.makeText(this, "absentees:" + nonselectedItems, Toast.LENGTH_LONG).show();

        }
    }





//    public void addtoreport(View v) throws IOException, BiffException {
//
//
//        Workbook workbook=null;
//        WritableWorkbook wb=null;
//        WritableSheet s=null;
//        try {
//            workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eattendance/" + attenCourseId+"_month_"+date.substring(3,5)+ ".xls"));
//            wb = createWorkbook(attenCourseId+"_month_"+date.substring(3,5),workbook);
//            s = wb.getSheet(0);
//
//        }
//        catch (Exception e){
//            //Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
//            File wbfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eattendance/" + attenCourseId + ".xls");
//            wb = createWorkbook(class_selected+"_month_"+date.substring(3,5));
//            // workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eattendance/" + class_selected + ".xls"));
//            s = createSheet(wb, "month_", 0);//to create month's sheet
//        }
//
//
//
//
//
//
//
//
//        int i = s.getColumns();
//        if(i==0){
//            try {
//                //for header
//                Label newCell=new Label(0,0,"Student_id");
//                Label newCell2=new Label(1,0,"Student_name");
//                WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
//                WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
//                //center align the cells' contents
//                headerFormat.setAlignment(Alignment.CENTRE);
//                newCell.setCellFormat(headerFormat);
//                newCell2.setCellFormat(headerFormat);
//                s.addCell(newCell);
//                s.addCell(newCell2);
//            } catch (WriteException e) {
//                e.printStackTrace();
//            }
//            for (Object item : Userlist) {
//                int j = s.getRows();
//                String name=Usernames.get(j-1).toString();
//
//                Label label = new Label(0, j, item.toString());
//                Label label2 = new Label(1, j, name);
//
//                try {
//                    s.addCell(label);
//                    s.addCell(label2);
//
//
//                } catch (WriteException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//
//        }
//        i=s.getColumns();
//        // Toast.makeText(this, i  , Toast.LENGTH_LONG).show();
//        int j=1;
//        try {
//            Label newCell=new Label(i,0, date);
//            WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
//            WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
//            //center align the cells' contents
//            headerFormat.setAlignment(Alignment.CENTRE);
//            newCell.setCellFormat(headerFormat);
//            s.addCell(newCell);
//
//        } catch (WriteException e) {
//            e.printStackTrace();
//        }
//        for (Object item : Userlist) {
//
//
//            Label label2;
//            // Label label2;
//
//            if (selectedItems.contains(item)) {
//                label2=new Label(i,j,"P");
//                //Toast.makeText(this, item.toString() + "  present :", Toast.LENGTH_LONG).show();
//
//
//            } else {
//                label2=new Label(i,j,"A");
//                //Toast.makeText(this, item.toString() + "  absent :", Toast.LENGTH_LONG).show();
//
//            }
//            j++;
//            try {
//
//
//                s.addCell(label2);
//
//
//
//            } catch (Exception e) {
//                Toast.makeText(this, "Unable to create sheet", Toast.LENGTH_LONG).show();
//                e.printStackTrace();
//
//            }
//
//
//        }
//        //for making consolidate report
//        Date today = new Date();
//
//        String tomorrow =new SimpleDateFormat("dd-MM-yyyy").format(new Date(today.getTime() + (1000 * 60 * 60 * 24)));// new Date(today.getTime() + (1000 * 60 * 60 * 24));
//        if(tomorrow.substring(0,2).equals("01")){
//
//            int row =s.getRows();
//            int col=s.getColumns();
//            String xx="";
//            int nop,tc;//to remove two xtra columns
//
//            for(i = 0; i<row; i++)
//            {
//                nop=0;
//                tc=-2;
//                for (int c=0;c<col;c++)
//                {
//                    Cell z=s.getCell(c,i);
//
//                    xx=z.getContents();
//                    if(xx.equals("P"))
//                        nop++;
//                    if(!xx.isEmpty()||!xx.equals("")) {
//                        tc++;
//                    }
//
//                }
//                xx=xx+"\n";
//                Label label = new Label(col, i,""+nop);
//
//                Label label2 = new Label(col+1,i,nop*100/tc+"%");
//                try {
//                    if(i==0) {
//                        label = new Label(col, i, "Total=" + tc);
//                        label2 = new Label(col+1, i, "percentage");
//
//                    }
//                    s.addCell(label);
//                    s.addCell(label2);
//                } catch (WriteException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//
//        try {
//            wb.write();
//            wb.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (WriteException e) {
//            e.printStackTrace();
//        }
//
//        Toast.makeText(this,"sheet  created successfully",Toast.LENGTH_LONG).show();
//
//    }
//




//    public WritableWorkbook createWorkbook(String fileName, Workbook workbook){
//        //exports must use a temp file while writing to avoid memory hogging
//        WorkbookSettings wbSettings = new WorkbookSettings();
//        wbSettings.setUseTemporaryFileDuringWrite(true);
//
//        //get the sdcard's directory
//        File sdCard = Environment.getExternalStorageDirectory();
//        //add on the your app's path
//        File dir = new File(sdCard.getAbsolutePath() + "/eattendance");
//        //make them in case they're not there
//        dir.mkdirs();
//        //create a standard java.io.File object for the Workbook to use
//        File wbfile = new File(dir,fileName+".xls");
//
//        WritableWorkbook wb = null;
//
//        try{
//            //create a new WritableWorkbook using the java.io.File and
//            //WorkbookSettings from above
//            wb = Workbook.createWorkbook(wbfile,workbook/*wbSettings*/);
//        }/*catch(IOException ex){
//          //  Log.e(TAG,ex.getStackTrace().toString());
//          //  Log.e(TAG, ex.getMessage());
//        }*/ catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return wb;
//    }
//
//    public WritableWorkbook createWorkbook(String fileName)  {
//
//        WorkbookSettings wbSettings = new WorkbookSettings();
//        wbSettings.setUseTemporaryFileDuringWrite(true);
//
//
//        File sdCard = Environment.getExternalStorageDirectory();
//
//        File dir = new File(sdCard.getAbsolutePath() + "/eattendance");
//
//        dir.mkdirs();
//
//        File wbfile = new File(dir,fileName+".xls");
//
//        WritableWorkbook wb = null;
//
//        try{
//
//            wb = Workbook.createWorkbook(wbfile,wbSettings);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return wb;
//    }
//
//    public WritableSheet createSheet(WritableWorkbook wb, String sheetName, int sheetIndex){
//        //create a new WritableSheet and return it
//
//        return wb.createSheet(sheetName, sheetIndex);
//
//    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


