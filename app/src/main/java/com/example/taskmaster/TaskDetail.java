package com.example.taskmaster;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        setTitle("Task Details");

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView titleDetail= (TextView) findViewById(R.id.titleDetail);

        titleDetail.setText(getIntent().getExtras().getString("title") );

        TextView bodyDetail= (TextView) findViewById(R.id.bodyDetail);

        bodyDetail.setText(getIntent().getExtras().getString("body") );

        TextView statusDetail= (TextView) findViewById(R.id.statusDetail);

        statusDetail.setText("Status: "+getIntent().getExtras().getString("status") );
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}