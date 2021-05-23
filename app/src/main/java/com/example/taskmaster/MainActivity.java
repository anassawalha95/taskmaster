package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewAdapter.OnTaskListener {

    public List<TaskModel> tasks;
    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Welcome To Task Master");


        TextView welcome_msg=(TextView)findViewById(R.id.welcome_msg);

        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        welcome_msg.setText("{"+ spref.getString("Username","username")+"}'s Tasks");


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
    protected void onResume() {
        super.onResume();


        TextView welcome_msg=(TextView)findViewById(R.id.welcome_msg);

        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        welcome_msg.setText("{"+ spref.getString("Username","username")+"}'s Tasks");
    }

    public void renderAddTaskView(View view) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }

    public void renderAllTasksView(View view) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);
    }

    public void renderSettingView(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void renderTaskDetailsView(View view) {
        Intent intent = new Intent(this, TaskDetail.class);
        startActivity(intent);
    }


    @Override
    public void onTaskClick(int position) {
        Log.d("note clicked"," "+position);
//        TextView titleDetail= (TextView) findViewById(R.id.titleDetail);
//        String title=tasks.get(position).getTitle();
//        titleDetail.setText(title);
//
//        TextView bodyDetail= (TextView) findViewById(R.id.bodyDetail);
//        String body=tasks.get(position).getBody();
//        titleDetail.setText(body);
        Log.d("note clicked","clicked");

        Intent intent =new Intent(this, TaskDetail.class);
        intent.putExtra("title",this.tasks.get(position).getTitle());
        intent.putExtra("body",this.tasks.get(position).getBody());
        intent.putExtra("status",this.tasks.get(position).getState());
        startActivity(intent);
    }
}