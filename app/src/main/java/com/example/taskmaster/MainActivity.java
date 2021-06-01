package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements ViewAdapter.OnTaskListener {

    public List <Task> tasks;
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Welcome To Task Master");

        TextView welcome_msg=(TextView)findViewById(R.id.welcome_msg);

//        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());

            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }






        Amplify.Auth.fetchAuthSession(
                result ->{
                    if(!result.isSignedIn()) {
                        Amplify.Auth.signInWithWebUI(
                                this,
                                results -> Log.i("AuthQuickStart failed1", results.toString()),
                                error -> Log.e("AuthQuickStart failed2", error.toString())
                        );
                    }else{

                        Map<String, String> welcome_msg_text= null;
                        try {
                            welcome_msg_text = AWSMobileClient.getInstance().getUserAttributes();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (welcome_msg_text.get("name")!=null){
                            welcome_msg.setText(welcome_msg_text.get("name"));
                        }else
                        {
                            welcome_msg.setText(AWSMobileClient.getInstance().getUsername());

                        }


                    }
                   },
                error -> Log.e("AuthQuickStart ", error.toString())
        );




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



        TextView welcome_msg=(TextView)findViewById(R.id.welcome_msg);

//        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        welcome_msg.setText(spref.getString("Username",""));


        Amplify.Auth.fetchAuthSession(
                result ->{
                    if(!result.isSignedIn()) {
                        Amplify.Auth.signInWithWebUI(
                                this,
                                results -> Log.i("AuthQuickStart failed1", results.toString()),
                                error -> Log.e("AuthQuickStart failed2", error.toString())
                        );
                    }else{

                        Map<String, String> welcome_msg_text= null;
                        try {
                            welcome_msg_text = AWSMobileClient.getInstance().getUserAttributes();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (welcome_msg_text.get("name")!=null){
                            welcome_msg.setText(welcome_msg_text.get("name"));
                        }else
                        {
                            welcome_msg.setText(AWSMobileClient.getInstance().getUsername());

                        }


                    }
                },
                error -> Log.e("AuthQuickStart ", error.toString())
        );





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

    public void logout(View view) {
        Amplify.Auth.signOut(
                () -> Log.i("AuthQuickstart", "Signed out successfully"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }


}