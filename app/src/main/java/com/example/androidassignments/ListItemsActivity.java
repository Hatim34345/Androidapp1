package com.example.androidassignments;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class ListItemsActivity extends AppCompatActivity {

    private ImageButton imageButtonLaunchCamera;
    private Switch switchButton;
    private CheckBox checkboxFinishActivity;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        imageButtonLaunchCamera = findViewById(R.id.imageButton);
        switchButton = findViewById(R.id.switchButton);
        checkboxFinishActivity = findViewById(R.id.checkboxFinishActivity);

        // Initialize the camera launcher
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle extras = result.getData().getExtras();
                            if (extras != null) {
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                if (imageBitmap != null) {
                                    // Set the captured image as the background of the ImageButton
                                    imageButtonLaunchCamera.setImageBitmap(imageBitmap);
                                }
                            }
                        }
                    }
                }
        );

        // Set an OnCheckedChangeListener for the Switch
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast("Switch is On", Toast.LENGTH_SHORT);
                } else {
                    showToast("Switch is Off", Toast.LENGTH_LONG);
                }
            }
        });

        // Set an OnCheckedChangeListener for the CheckBox
        checkboxFinishActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Show a dialog when the CheckBox state changes
                if (isChecked) {
                    showFinishActivityDialog();
                }
            }
        });

        imageButtonLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(  );
                resultIntent.putExtra("Response", "Here is my response");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                launchCamera();
            }
        });
    }

    private void launchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(takePictureIntent);
        }
    }

    // Create a method to show a Toast message
    private void showToast(String message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    // Show a dialog to confirm if the user wants to finish the activity
    private void showFinishActivityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to finish the activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish the activity if the user selects "Yes"
                        finish();
                        showToast("Activity finished", Toast.LENGTH_SHORT);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Uncheck the CheckBox if the user selects "No"
                        checkboxFinishActivity.setChecked(false);
                    }
                })
                .show();
    }
}
