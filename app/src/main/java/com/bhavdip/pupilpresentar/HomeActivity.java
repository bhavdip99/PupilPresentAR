package com.bhavdip.pupilpresentar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static android.R.attr.data;

/**
 * Created by bhavdip on 14/6/17.
 */

public class HomeActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {


//    private RecyclerView recyclerView;
    private ListView listView;
    private MyRecyclerViewAdapter adapter;
    String[] data = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

//        recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
        listView = (ListView) findViewById(R.id.home_recycler_view);
        int numberOfColumns = 3;
//        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data){

            @Override
            public View getView(int position,  View convertView,  ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView,parent);
                if (position == 3 || position == 6){
                    view.setBackgroundResource(android.R.color.background_light);
                }
                return view;

            }
        };

        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        listView.setAdapter(arrayAdapter);

    }
    @Override
    public void onItemClick(View view, int position) {
        if (adapter.getItem(position).equals("1")){
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }
}
