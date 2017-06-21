package com.bhavdip.pupilpresentar.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhavdip.pupilpresentar.R;
import com.bhavdip.pupilpresentar.activity.ShowStudentActivity;
import com.bhavdip.pupilpresentar.model.Student;
import com.bhavdip.pupilpresentar.utility.Utility;

import java.util.ArrayList;
import java.util.List;



public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.NewsViewHolder> {

    private List<Student> students;
    private Context context;

    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;

    }

    @Override
    public StudentAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        holder.name.setText(students.get(position).getFirstName() +" "+students.get(position).getLastName());
        holder.email.setText(students.get(position).getEmail());
        holder.roll_no.setText(students.get(position).getRollno());
        holder.present.setText("P");

        if (students.get(position).getImage()!=null) {
            holder.img_News.setImageBitmap(Utility.getByteArrayAsBitmap(students.get(position).getImage()));
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.img_News.setImageDrawable(context.getResources().getDrawable(R.drawable.img_profile_pic));
            }else{
                holder.img_News.setImageDrawable(context.getResources().getDrawable(R.drawable.img_profile_pic));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowStudentActivity.class);
//                intent.putExtra(ShowProfileActivity.STUDENT, (Parcelable) students.get(position));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView newsesLayout;
        TextView name;
        TextView email;
        TextView roll_no;
        TextView present;
        ImageView img_News;


        public NewsViewHolder(View v) {
            super(v);
            newsesLayout = (CardView) v.findViewById(R.id.card_layout);
            name = (TextView) v.findViewById(R.id.name);
            email = (TextView) v.findViewById(R.id.email);
            roll_no = (TextView) v.findViewById(R.id.roll_no);
            present = (TextView) v.findViewById(R.id.present);
            img_News = (ImageView) v.findViewById(R.id.img_News);
        }
    }
}
