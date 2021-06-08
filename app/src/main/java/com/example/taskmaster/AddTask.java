package com.example.taskmaster;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Status;
import com.amplifyframework.datastore.generated.model.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Intent.ACTION_GET_CONTENT;

public class AddTask extends AppCompatActivity {

   public File file ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setTitle("Add New Task");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Toast toast = Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_LONG);
        Button bt = (Button) findViewById(R.id.save_task);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getFile()!=null)
                uploadFile(getFile(), getFileName());
                EditText title = (EditText) findViewById(R.id.add_task_title);
                EditText body = (EditText) findViewById(R.id.add_task_body);
                Task item = Task.builder()
                        .title( title.getText().toString())
                        .status(Status.New)
                        .description(body.getText().toString())
                        .file(getFileName())
                        .build();
                toast.show();

                Amplify.DataStore.save(item,
                        success -> Log.i("Tutorial", "Saved item: " + success.item().getTitle() +" "+success.item().getDescription()),
                                   error -> Log.e("Tutorial", "Could not save item to DataStore", error)
                );

                finish();
            }
        });

        Button addFileBTN = (Button) findViewById(R.id.addFile);
        addFileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileFromStorage();
            }
        });

        try {
            Log.d("intent.getData()", "intent.getData()" + getIntent().hasExtra("intentFilterFile"));
            if (getIntent().hasExtra("intentFilterFile")) {
                Intent intent = (Intent) getIntent().getExtras().get("intentFilterFile");
                Bundle bundle = intent.getExtras();
                Uri dataa = (Uri) bundle.get(intent.EXTRA_STREAM);
                Log.d("intent.getData()", "intent.getData()" + dataa);
                // Uri dataa = intent.getData();
                Cursor returnCursor = getContentResolver().query(dataa, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                String fileName = returnCursor.getString(nameIndex);
                Log.i("fileNameqcqw", "fileName fileName" + fileName);
                File file = new File(getApplicationContext().getFilesDir(), fileName);
                InputStream inputStream = getContentResolver().openInputStream(dataa);
                OutputStream outputStream = new FileOutputStream(file);
                try {
                    //resultData.setType(  getContentResolver().getType(resultData.getData()));
                    InputStream i =  getContentResolver().openInputStream(dataa);
                    OutputStream o = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = i.read (buf)) >0){
                        o.write(buf, 0, len);
                    }
                    inputStream.close();
                    outputStream.close();
                    setFile(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }catch (FileNotFoundException e) {
                e.printStackTrace();
        }




//        try {
//            try {
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.addPlugin(new AWSApiPlugin());
//            Amplify.addPlugin(new AWSS3StoragePlugin());
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//
//            Amplify.configure(getApplicationContext());
//
//            Log.i("Tutorial", "Initialized Amplify");
//        } catch (AmplifyException e) {
//            Log.e("Tutorial", "Could not initialize Amplify", e);
//        }
//
//            //  Log.d(" image image", "image "+ intent.getType().contains("image/"));
//            if (getIntent().getType().contains("image/")) {
//                try {
//                    Intent intent= this.getIntent();
//                    Bundle bundle = intent.getExtras();
//                    Uri dataa = (Uri)bundle.get(intent.EXTRA_STREAM);
//                    Log.d("intent.getData()", "intent.getData()"+dataa);
//                   // Uri dataa = intent.getData();
//                    Cursor returnCursor = getContentResolver().query(dataa, null, null, null, null);
//                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                    returnCursor.moveToFirst();
//                    String fileName = returnCursor.getString(nameIndex);
//                    Log.i("fileNameqcqw", "fileName fileName"+fileName);
//                    File file = new File(getApplicationContext().getFilesDir(), fileName);
//                    InputStream inputStream =  getContentResolver().openInputStream(dataa);
//                    OutputStream outputStream = new FileOutputStream(file);
//                    setFile(file);
//
//                    //       Intent i= new Intent(this,AddTask.class);
////                    intent.putExtra("intentFilterFile", intent.getData());
////                    startActivity(intent);
//
//                }catch (Exception e)
//                {
//                    System.out.println(e);
//                }
//
//            }
//        }catch (Exception e){
//            System.out.println(e);
//        }
    }

    public void uploadFile(File file, String fileName) {
            Amplify.Storage.uploadFile(
                    fileName,
                    file,
                    result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
            Toast.makeText(this," supported after upload file "+Build.VERSION.SDK_INT,Toast.LENGTH_LONG).show();
    }


    public void getFileFromStorage(){
        Intent intent= new Intent(ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 2000);

    }

    public String getFileName(){
        if(getFile()==null)
            return "";
        return this.file.getName();
    }

    public File getFile(){

        return this.file;
    }
    public void setFile(File file){

         this.file=file;
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


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        Log.d("resultCode", "resultCode "+resultCode);

        if (requestCode == 2000 && resultCode==RESULT_OK ) {
             if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            ){
                 Uri returnUri = resultData.getData();
                 Log.d("returnUri", "returnUri"+returnUri);
                 Log.d("resultCode", "resultCode "+resultCode);
                 Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                 int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                 returnCursor.moveToFirst();
                 String fileName = returnCursor.getString(nameIndex);
                 File file = new File(getApplicationContext().getFilesDir(), fileName);

                try {
                  //resultData.setType(  getContentResolver().getType(resultData.getData()));
                    InputStream inputStream =  getContentResolver().openInputStream(resultData.getData());
                    OutputStream outputStream = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read (buf)) >0){
                        outputStream.write(buf, 0, len);
                    }
                    inputStream.close();
                    outputStream.close();
                    setFile(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else{

                String[] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions,2000);
            }
        }
    }
}



