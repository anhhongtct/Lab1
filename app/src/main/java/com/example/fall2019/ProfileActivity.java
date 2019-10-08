package com.example.fall2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form2);
        mImageButton = findViewById(R.id.button_form2);
        EditText emailText = findViewById(R.id.edit_form22);
        Button chatButton = findViewById(R.id.button2_form2);
        chatButton.setOnClickListener(clk -> {
            Intent chatIntent = new Intent(this, ChatRoomActivity.class);
        startActivity(chatIntent); }
        );



        Intent previousMess = getIntent();
        String emailSent = previousMess.getStringExtra("email");
        emailText.setText(emailSent);
        mImageButton.setOnClickListener(v -> {
                    dispatchTakePictureIntent();
                }
        );
        Log.e(ACTIVITY_NAME, "In function:" + "Create");
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME, "In function:" + "OnActivityResult");


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:" + "OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" + "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" + "onDestroy");
    }


}
