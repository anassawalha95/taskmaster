package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;


public class AllTasks extends AppCompatActivity implements ViewAdapter.OnTaskListener{

    public List<Task> tasks;
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
        Amplify.DataStore.query(Task.class,
                tasks -> {
                    while (tasks.hasNext()) {
                        Task task = tasks.next();

                        if (task.getTitle() != null) {

                            this.tasks.add(task);
                        }

                    }
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );




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



        tasks= new ArrayList<>();
        Amplify.DataStore.query(Task.class,
                tasks -> {
                    while (tasks.hasNext()) {
                        Task task = tasks.next();

                        if (task.getTitle() != null) {

                            this.tasks.add(task);
                        }

                    }
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );




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
        Intent intent =new Intent(this, TaskDetail.class);
        intent.putExtra("title",this.tasks.get(position).getTitle());
        intent.putExtra("body",this.tasks.get(position).getDescription());
        intent.putExtra("status",this.tasks.get(position).getStatus().toString());
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