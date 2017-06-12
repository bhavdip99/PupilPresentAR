package com.bhavdip.pupilpresentar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavdip.pupilpresentar.R;
import com.bhavdip.pupilpresentar.adapter.NewsAdapter;
import com.bhavdip.pupilpresentar.adapter.StudentAdapter;
import com.bhavdip.pupilpresentar.dbsqlite.StudentModel;
import com.bhavdip.pupilpresentar.model.Student;

import java.util.ArrayList;
import java.util.List;


public class StudentFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        context = getContext();

        try {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.newsListView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            ArrayList<Student> students = new ArrayList<Student>();
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            //swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

            StudentModel studentModel = new StudentModel(context);
            students = studentModel.getStudentList();


            StudentAdapter studentAdapter = new StudentAdapter(getActivity(), students);

            recyclerView.setAdapter(studentAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
