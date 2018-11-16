package com.littleyellow.listhelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.littleyellow.listhelper.adapter.Parameters;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);

        List<String> data = new ArrayList<>();
        for(int i=0;i<20;i++){
            data.add("..."+i+"...");
        }
        adapter = new TestAdapter(data);
        adapter.setParameters(Parameters.newBuilder()
        .isLoop(true)
        .showCount(3.5)
        .build());
        recyclerview.setAdapter(adapter);
    }
}
