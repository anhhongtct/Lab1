package com.example.fall2019;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);

        EditText edit1 = findViewById(R.id.edit_form1);
        edit1.setHint(mPrefs.getString("ReserveName", ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        EditText edit1 = findViewById(R.id.edit_form1);
         SharedPreferences mPrefs = getSharedPreferences("Email", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("ReserveName", edit1.getText().toString());
        editor.commit();

    }

}
