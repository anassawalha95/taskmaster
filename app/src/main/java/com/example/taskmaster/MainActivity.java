package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView welcome_msg=(TextView)findViewById(R.id.welcome_msg);

        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        welcome_msg.setText(spref.getString("Username",""));

    }

    @Override
    protected void onResume() {
        super.onResume();


        TextView welcome_msg=(TextView)findViewById(R.id.welcome_msg);

        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        welcome_msg.setText(spref.getString("Username",""));
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



}