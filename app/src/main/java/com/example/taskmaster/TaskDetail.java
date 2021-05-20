package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView titleDetail= (TextView) findViewById(R.id.titleDetail);

        titleDetail.setText(getIntent().getExtras().getString("title") );

        TextView bodyDetail= (TextView) findViewById(R.id.bodyDetail);

        bodyDetail.setText(getIntent().getExtras().getString("body") );


    }
}