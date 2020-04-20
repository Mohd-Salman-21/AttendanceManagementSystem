package dell.example.com.letschat.UtilityClasses;


/**
 * Created by admin on 9/2/2018.
 */

public class Student {
    String sname;
    String sid;

    String spass;


  /*  public Student(String sname, String sid){

    }*/

    public Student(String sname, String sid, String spass) {
        this.sname = sname;
        this.sid = sid;
        this.spass = spass;

    }



    public String getSname()
    { return sname; }

    public String getSid() {
        return sid;
    }


    public String getspass() { return spass; }
}
