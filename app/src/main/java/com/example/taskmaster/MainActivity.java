package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity  {


    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    public void renderAddTaskView(View view) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }

    public void renderAllTasksView(View view) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);
    }




}