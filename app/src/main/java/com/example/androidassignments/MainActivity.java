package com.example.androidassignments;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button startListItemsButton;
    private static final String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "onCreate");

        // Initialize the button and set its click listener
        startListItemsButton = findViewById(R.id.buttonStartListItems);
        startListItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start ListItemsActivity with startActivityForResult
                startListItemsActivity();
            }
        });
    }

    private void startListItemsActivity() {
        Intent intent = new Intent(this, ListItemsActivity.class);
        startActivityForResult(intent, 10); // Use 10 as the request code
    }
    private void showToast(String message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    // Implement the onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                // Check if "Response" extra data is present in the Intent
                String messagePassed = data.getStringExtra("Response");
                if (messagePassed != null) {
                    // Display a Toast with the message
                    showToast("ListItemsActivity passed: " + messagePassed, Toast.LENGTH_SHORT);
                }
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("MainActivity", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("MainActivity", "onRestoreInstanceState");
    }
}
