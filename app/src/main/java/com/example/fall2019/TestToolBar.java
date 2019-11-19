package com.example.fall2019;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

public class TestToolBar extends AppCompatActivity {
    String overflowText = "You clicked on the overflow menu";
    Toolbar tBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        tBar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(tBar);

        /*Snackbar code:
        Snackbar sb = Snackbar.make(tBar, "Hello world", Snackbar.LENGTH_LONG)
        .setAction("Action text", e -> Log.e("Menu Example", "Clicked Undo"));
        sb.show();*/


//        Button alertDialogButton = (Button)findViewById(R.id.insert);
//        alertDialogButton.setOnClickListener( clik ->   alertExample()  );

        //Show the toast immediately:
        Toast.makeText(this, "Welcome to Menu Example", Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);


	    /* slide 15 material:
	    MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView sView = (SearchView)searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }  });

	    */

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.search_item:
                Toast.makeText(this,overflowText, Toast.LENGTH_LONG).show();
                break;
            case R.id.icon1:
                Toast.makeText(this,"This is the initial message", Toast.LENGTH_LONG).show();
                break;
            case R.id.icon2:
                alertExample();
                break;
            case R.id.icon3:
                Snackbar snackbar = Snackbar.make(tBar,"Go back", Snackbar.LENGTH_LONG)
                    .setAction("Go back?", e-> finish());
                snackbar.show();
               // Toast.makeText(this,"asfsd",Toast.LENGTH_LONG).show();
            break;
        }
        return true;
    }

    public void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.view_extra_stuff, null);
        EditText et = (EditText)middle.findViewById(R.id.view_edit_text);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Message")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        overflowText = et.getText().toString();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }
};
