package dell.example.com.letschat.UtilityClasses;


/**
 * Created by admin on 9/2/2018.
 */

public class Student {
    String sname;
    String sid;
    String classes;
    String spass;
    String department;
    String semester;

  /*  public Student(String sname, String sid){

    }*/

    public Student(String sname, String sid, String spass, String semester,String department) {
        this.sname = sname;
        this.sid = sid;
        this.classes = classes;
        this.spass = spass;
        this.department=department;
        this.semester=semester;
    }

    public String getDepartment(){return department;}

    public String getSemester() {return semester;}

    public String getSname() { return sname; }

    public String getSid() {
        return sid;
    }
    public String getClasses() {
        return classes;
    }

    public String getspass() { return spass; }
}
