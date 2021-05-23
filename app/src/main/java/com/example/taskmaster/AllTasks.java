package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity implements ViewAdapter.OnTaskListener{
    public List<TaskModel> tasks;
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        setTitle("All Tasks");
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);



        tasks= new ArrayList<>();


        TaskModel task1 =new TaskModel("buy groceries ","Lorem ipsum , ","New");
        TaskModel task2 =new TaskModel("buy new car "," imperdiet a, . ","New");
        TaskModel task3 =new TaskModel("Invest in bitcoin ","   Integer tincidunt.","New");

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ViewAdapter adapter = new ViewAdapter(tasks,this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linear=  new LinearLayoutManager(this);
        linear.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onTaskClick(int position) {
       // Log.d("note clicked"," "+position);
//        TextView titleDetail= (TextView) findViewById(R.id.titleDetail);
//        String title=tasks.get(position).getTitle();
//        titleDetail.setText(title);
//
//        TextView bodyDetail= (TextView) findViewById(R.id.bodyDetail);
//        String body=tasks.get(position).getBody();
//        titleDetail.setText(body);
       // Log.d("note clicked","clicked");

        Intent intent =new Intent(this, TaskDetail.class);
        intent.putExtra("title",this.tasks.get(position).getTitle());
        intent.putExtra("body",this.tasks.get(position).getBody());
        intent.putExtra("status",this.tasks.get(position).getState());
        startActivity(intent);
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