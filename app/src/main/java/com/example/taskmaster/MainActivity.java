package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements ViewAdapter.OnTaskListener {

    public List <Task> tasks;
    RecyclerView recyclerView ;
    ViewAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> taskModule) {
                        if (!taskModule.isSuccessful()) {
                            Log.w("FCM Token ..", "Fetching FCM registration token failed", taskModule.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = taskModule.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("FCM Token ..", token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        setTitle("Welcome To Task Master");

        TextView welcome_msg=(TextView)findViewById(R.id.welcome_msg);

//        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
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




        this.tasks= new ArrayList<>();
        Amplify.DataStore.query(Task.class,
                tasks -> {
                    while (tasks.hasNext()) {
                        Task task = tasks.next();

                        Log.d("tasks tasks", "onCreate: "+task);
                            this.tasks.add(task);


                    }

                    recyclerView =  findViewById(R.id.recyclerView);
                    adapter= new ViewAdapter(this.tasks,this);
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
    protected void onResume() {
        super.onResume();



        TextView welcome_msg=(TextView)findViewById(R.id.welcome_msg);



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






        Amplify.DataStore.query(Task.class,
                tasks -> {
                    while (tasks.hasNext()) {
                        Task task = tasks.next();
                        Log.d("tasks tasks", "onCreate: "+task);

                        if(  !this.tasks.contains(task))
                            this.tasks.add(task);
                    }

                    adapter.notifyItemChanged(0, this.tasks.size());
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }

    }
    protected boolean checkFileType(String fileName){

        String[] okFileExtensions = new String[] {
                ".jpg",
                ".png",
                ".gif",
                ".jpeg"
        };

        for (String extension: okFileExtensions) {

            Log.d("fileNametoLower", "checkFileType: "+fileName.toLowerCase().endsWith(extension));
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }

        return false;
    }



}