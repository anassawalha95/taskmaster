package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toast toast = Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_LONG);
        Button bt = (Button) findViewById(R.id.save_task);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toast.show();

                finish();
            }
        });
    }
}