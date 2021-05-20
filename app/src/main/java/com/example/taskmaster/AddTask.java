package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        TaskDatabase db = Room.databaseBuilder(getApplicationContext(),
                TaskDatabase.class, "tasks").allowMainThreadQueries().build();

        TaskDao taskDao = db.taskDao();





        Toast toast = Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_LONG);
        Button bt = (Button) findViewById(R.id.save_task);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText title=(EditText) findViewById(R.id.add_task_title);
                EditText body=(EditText) findViewById(R.id.add_task_body);

                Log.d("ttttttttttttttttttttt", "title: "+title.getText().toString());
                Log.d("tttttttttttttttttttttt", "body: "+body.getText().toString());


                TaskModel task =new TaskModel(title.getText().toString(),body.getText().toString(),"New");

                taskDao.saveNewTask(task);

                toast.show();

                finish();
            }
        });
    }

}

