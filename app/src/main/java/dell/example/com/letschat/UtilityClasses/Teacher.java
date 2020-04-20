package dell.example.com.letschat.UtilityClasses;

/**
 * Created by admin on 8/2/2018.
 */

public class Teacher {
    String tname;
    String tid;
    String tpass;
    String courses;

  /*  public Teacher(String tname, String tid, EditText subject, Spinner classes){

    }*/

    public Teacher(String tname, String tid, String  courses) {
        this.tname = tname;
        this.tid = tid;
        this.courses=courses;
    }

    public String  getCourses(){
        return  courses;
    }
    public String getTname() {
        return tname;
    }

    public String getTid() {
        return tid;
    }




}
