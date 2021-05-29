package com.example.taskmaster;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setTitle("Add New Task");
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        TaskDatabase db = Room.databaseBuilder(getApplicationContext(),
                TaskDatabase.class, "tasks").allowMainThreadQueries().build();

        TaskDao taskDao = db.taskDao();


        Toast toast = Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_LONG);
        Button bt = (Button) findViewById(R.id.save_task);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText title = (EditText) findViewById(R.id.add_task_title);
                EditText body = (EditText) findViewById(R.id.add_task_body);
//
//                Log.d("ttttttttttttttttttttt", "title: "+title.getText().toString());
//                Log.d("tttttttttttttttttttttt", "body: "+body.getText().toString());


                TaskModel task = new TaskModel(title.getText().toString(), body.getText().toString(), "New");

                taskDao.saveNewTask(task);

                toast.show();

                finish();
            }
        });
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



