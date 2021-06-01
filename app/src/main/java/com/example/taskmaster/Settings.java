
package com.example.taskmaster;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button bt = (Button) findViewById(R.id.Save_username);
        EditText userNameText= (EditText) findViewById(R.id.username);
        setTitle("Settings");
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AuthUserAttribute name =
                        new AuthUserAttribute(AuthUserAttributeKey.name(), userNameText.getText().toString());
                Amplify.Auth.updateUserAttribute(name,
                        result -> Log.i("AuthDemo", "Updated user attribute = " + result.toString()),
                        error -> Log.e("AuthDemo", "Failed to update user attribute.", error)
                );
//                SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                SharedPreferences.Editor editor = spref.edit();
//                editor.putString("Username", userNameText.getText().toString()+"\'s tasks");
//                editor.apply();
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