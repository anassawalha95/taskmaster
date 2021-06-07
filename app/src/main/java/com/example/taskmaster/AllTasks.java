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

import java.io.File;
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

                       Log.d("tasks tasks", "onCreate: "+task);

                            this.tasks.add(task);
                    }

                    recyclerView =  findViewById(R.id.recyclerView);
                    ViewAdapter adapter = new ViewAdapter(this.tasks,this);
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager linear=  new LinearLayoutManager(this);
                    linear.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(linear);
                    recyclerView.setAdapter(adapter);


                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );




    }


    @Override
    public void onTaskClick(int position) {

        if(checkFileType(this.tasks.get(position).getFile())) {
            Amplify.Storage.downloadFile(
                    this.tasks.get(position).getFile(),
                    new File(getApplicationContext().getFilesDir() +"/"+ this.tasks.get(position).getFile()),
                    result -> {
                        Intent intent =new Intent(this, TaskDetail.class);
                        intent.putExtra("title",this.tasks.get(position).getTitle());
                        intent.putExtra("body",this.tasks.get(position).getDescription());
                        intent.putExtra("status",this.tasks.get(position).getStatus().toString());
                        intent.putExtra("isFile", true);
                        intent.putExtra("file", result.getFile());
                        startActivity(intent);
//                        Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getAbsolutePath());
//                        Log.i("MyAmplifyApp", "Successfully downloaded file: " +getApplicationContext().getFilesDir() +"/"+ this.tasks.get(position).getFile());
                    },
                    error -> Log.e("MyAmplifyApp",  "Download Failure", error)
            );

        }
        else{
            Amplify.Storage.getUrl(
                    this.tasks.get(position).getFile(),
                    result -> {
                        Intent intent =new Intent(this, TaskDetail.class);
                        intent.putExtra("title",this.tasks.get(position).getTitle());
                        intent.putExtra("body",this.tasks.get(position).getDescription());
                        intent.putExtra("status",this.tasks.get(position).getStatus().toString());
                        intent.putExtra("isFile", false);
                        intent.putExtra("file", result.getUrl());
                        startActivity(intent);
//                        Log.i("MyAmplifyApp", "Successfully generated: " + result.getUrl());

                    },
                    error -> Log.e("MyAmplifyApp", "URL generation failure", error)
            );
        }

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

    protected boolean checkFileType(String fileName){

        String[] okFileExtensions = new String[] {
                "jpg",
                "png",
                "gif",
                "jpeg"
        };

        for (String extension: okFileExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

}
