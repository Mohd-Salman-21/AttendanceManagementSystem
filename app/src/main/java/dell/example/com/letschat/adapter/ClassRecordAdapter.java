package dell.example.com.letschat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dell.example.com.letschat.R;

public class ClassRecordAdapter extends RecyclerView.Adapter<ClassRecordAdapter.RecordCustomViewHolder> {

    Context mContext;
    ArrayList<String> mArraList;
    ArrayList<Integer> pres;
    ArrayList <Integer>abs;
    String p1;
    String department,semester,course;


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent,courses;


    static int j=0;

    float percent;
    ArrayList<GradientDrawable> mGradientDrawable;


    public ClassRecordAdapter(Context mContext, ArrayList<String> mArraList,String department,String semester,String course) {
        this.mContext = mContext;
        this.mArraList = mArraList;
       this.department=department;
       this.semester=semester;
       this.course=course;

        mGradientDrawable=new ArrayList<>();

        fillGradientList(mContext);
    }




    @NonNull
    @Override
    public RecordCustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       final View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_view,viewGroup,false);
       return new RecordCustomViewHolder(view);
    }


    private void fillGradientList(Context context)
    {
        mGradientDrawable.add(getTempGradientDrawable(ContextCompat.getColor(context,R.color.gradient_1_start),ContextCompat.getColor(context,R.color.gradient_1_end)));
        mGradientDrawable.add(getTempGradientDrawable(ContextCompat.getColor(context,R.color.gradient_2_start),ContextCompat.getColor(context,R.color.gradient_2_end)));
        mGradientDrawable.add(getTempGradientDrawable(ContextCompat.getColor(context,R.color.gradient_3_start),ContextCompat.getColor(context,R.color.gradient_3_end)));
        mGradientDrawable.add(getTempGradientDrawable(ContextCompat.getColor(context,R.color.gradient_4_start),ContextCompat.getColor(context,R.color.gradient_4_end)));
    }


    private GradientDrawable getTempGradientDrawable(int startColor,int endColor)
    {
        GradientDrawable drawable=new GradientDrawable(GradientDrawable.Orientation.BL_TR,new int[]{startColor,endColor});
        drawable.setDither(true);
        drawable.setGradientCenter(drawable.getIntrinsicWidth()/8,drawable.getIntrinsicHeight()/2);
        drawable.setCornerRadius(20);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setUseLevel(true);
        return  drawable;
    }

    @Override
    public void onBindViewHolder(final RecordCustomViewHolder viewHolder, int i) {

        String topicName=mArraList.get(i);
         viewHolder.textView.setText(topicName);
        viewHolder.textView.setTextColor(Color.GREEN);


        final String sid=mArraList.get(i);






     //   present=absent=total=0;
        percent=(float)0.0;



        dbAttendance = ref.child("Attendance").child("Department").child(department).child("Semester").child(semester).child(course);


            dbAttendance.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    int absent=0,present=0;
                    for(DataSnapshot dsp : dataSnapshot.getChildren()) {

                        p1 = dsp.child(sid.toString()).child("p1").getValue().toString().substring(0, 1);

                        if(p1.equals("A"))
                            absent++;
                        else
                            present++;
                    }


             int total=present+absent;
                    String twom=Integer.toString(present);
                    viewHolder.one.setText(twom);
                    viewHolder.one.setTextColor(Color.GREEN);

                    String threem=Integer.toString(absent);
                    viewHolder.two.setText(threem);
                    viewHolder.two.setTextColor(Color.RED);

                    percent = (float)((present*100)/total);
                    String fourm = Float.toString(percent);
                    viewHolder.three.setText(fourm+" %");
                    if(percent>=75)
                        viewHolder.three.setTextColor(Color.GREEN);
                    if(percent<75)
                        viewHolder.three.setTextColor(Color.RED);



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                   // Toast.makeText(ClassRecordAdapter.this, "something went wrong", Toast.LENGTH_LONG).show();
                }



            });







      int  total=0;








        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN)
        {

            viewHolder.relativeLayout.setBackground(mGradientDrawable.get(i%4));
        }else {

            viewHolder.relativeLayout.setBackgroundDrawable(mGradientDrawable.get(i%4));
        }


    }

    @Override
    public int getItemCount() {
        return mArraList.size();
    }

    class RecordCustomViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
       private TextView one ,two,three,four;
        private TextView textView2;
        private RelativeLayout relativeLayout;

        public RecordCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.record5_class_record);
            one=itemView.findViewById(R.id.record6_class_record);
            two=itemView.findViewById(R.id.record7_class_record);
            three=itemView.findViewById(R.id.record8_class_record);

            relativeLayout=itemView.findViewById(R.id.card_realtiveLayout);
        }


    }




}
