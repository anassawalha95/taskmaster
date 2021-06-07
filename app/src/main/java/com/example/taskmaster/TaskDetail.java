package com.example.taskmaster;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.io.File;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        setTitle("Task Details");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView titleDetail= (TextView) findViewById(R.id.titleDetail);
        titleDetail.setText(getIntent().getExtras().getString("title") );

        TextView bodyDetail= (TextView) findViewById(R.id.bodyDetail);
        bodyDetail.setText(getIntent().getExtras().getString("body") );

        TextView statusDetail= (TextView) findViewById(R.id.statusDetail);
        statusDetail.setText("Status: "+getIntent().getExtras().getString("status") );

        ImageView imageView= findViewById(R.id.imageView2);
        Button downloadButton= findViewById(R.id.download);

        boolean isFile= (boolean) getIntent().getExtras().get("isFile");
        Log.d("isFile", "isFile " + isFile);
        if(isFile){

            downloadButton.setVisibility(View.GONE);
            downloadButton.setVisibility(View.INVISIBLE);

            File f = (File) getIntent().getExtras().get("file");
            Log.i("taskdetails", "Successfully downloaded: " + f.getAbsolutePath());

            Picasso.get().load(f.getAbsoluteFile()).into(imageView);

        }else{

            imageView.setVisibility(View.GONE);
            imageView.setVisibility(View.INVISIBLE);
            downloadButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    if(ContextCompat.checkSelfPermission(TaskDetail.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
                        String[] permissions = { Manifest.permission.INTERNET};
                    ActivityCompat.requestPermissions(TaskDetail.this, permissions,2000);
                    }

                    Uri uri= Uri.parse(getIntent().getExtras().get("file").toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
//                    DownloadManager.Request request = new DownloadManager.Request(uri);
//                    request.allowScanningByMediaScanner();
//                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);  // This will show notification on top when downloading the file.
//                    request.setTitle("Downloading a file"); // Title for notification.
//                    request.setVisibleInDownloadsUi(true);
//                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());  // Storage directory path
//                    ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request); // This will start downloading
//                    Log.i("taskdetails", "Successfully downloaded: " + uri);

                }
            });


        }

//setvisalbitye visiability gone

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