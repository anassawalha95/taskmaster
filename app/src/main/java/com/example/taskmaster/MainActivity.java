package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ViewAdapter.OnTaskListener {

    public List <TaskModel> tasks;
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Welcome To Task Master");


        TextView welcome_msg=(TextView)findViewById(R.id.welcome_msg);

        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        welcome_msg.setText("{"+ spref.getString("Username","username")+"}'s Tasks");

        TaskDatabase db = Room.databaseBuilder(getApplicationContext(),
                TaskDatabase.class, "tasks").allowMainThreadQueries().build();

        TaskDao taskDao = db.taskDao();

        tasks = taskDao.getall();


//        for(int i = 0; i<tasks.size();i++){
//            if (tasks.get(i).getBody().length() > 30)
//            {
//
//                tasks.get(i).setBody(tasks.get(i).getBody().substring(0, 30));
//            }
//
//        }


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

        TaskDatabase db = Room.databaseBuilder(getApplicationContext(),
                TaskDatabase.class, "tasks").allowMainThreadQueries().build();

        TaskDao taskDao = db.taskDao();
        tasks = taskDao.getall();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ViewAdapter adapter = new ViewAdapter(tasks,this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linear=  new LinearLayoutManager(this);
        linear.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adapter);


      

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