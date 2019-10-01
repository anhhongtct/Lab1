package com.example.fall2019;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        EditText edit1 = findViewById(R.id.edit_form1);
        Button button1 = findViewById(R.id.button_form);
        SharedPreferences mPrefs = getSharedPreferences("Email", MODE_PRIVATE);
        edit1.setHint(mPrefs.getString("Email", ""));

        if (button1 != null) {
            button1.setOnClickListener(clk -> {
                Intent loginIntent = new Intent(this, ProfileActivity.class);
                loginIntent.putExtra("email", edit1.getText().toString());
                startActivity(loginIntent);
            }
            );
        };
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

        @Override
    protected void onPause() {
        super.onPause();
        EditText edit1 = findViewById(R.id.edit_form1);
        SharedPreferences mPrefs = getSharedPreferences("Email", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("Email", edit1.getText().toString());
        editor.commit();

    }

}
