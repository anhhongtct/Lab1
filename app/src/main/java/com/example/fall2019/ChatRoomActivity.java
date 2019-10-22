package com.example.fall2019;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity{
    ArrayList<Message> message = new ArrayList<>();
    BaseAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        //List view
        ListView theList =findViewById(R.id.theList);
        // set the adapter for list view
        theList.setAdapter( myAdapter = new MyListAdapter());

        //Send button
        Button sendButton = findViewById(R.id.button1_chat);
        //Recieve button
        Button recieveButton = findViewById(R.id.button2_chat);
        //Chat text
        EditText chatText = findViewById(R.id.edit_chat);


        //Get the database
        MyDatabaseOpenHelper opener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db =  opener.getWritableDatabase();

        //query all the results from the database:
        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_MESSAGE, MyDatabaseOpenHelper.COL_SEND_OR_REC};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        //find the column indices:
        int messageColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE);
        int SendColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_SEND_OR_REC);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String mess = results.getString(messageColumnIndex);
            String send = results.getString(SendColIndex);
            boolean sor = send.equals("true") ? true : false;
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
             message.add(new Message(mess, sor, id));
             myAdapter.notifyDataSetChanged();
        }

        ContentValues newValues = new ContentValues();
        sendButton.setOnClickListener(clk -> {
            if(!TextUtils.isEmpty(chatText.getText())) {
                newValues.put(MyDatabaseOpenHelper.COL_MESSAGE, chatText.getText().toString());
                newValues.put(MyDatabaseOpenHelper.COL_SEND_OR_REC, true);
                long id = db.insert(MyDatabaseOpenHelper.TABLE_NAME,MyDatabaseOpenHelper.COL_MESSAGE, newValues);
                message.add(new Message(chatText.getText().toString(), true, id));
                myAdapter.notifyDataSetChanged();
                chatText.setText("");
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if(imm.isAcceptingText()) { // verify if the soft keyboard is open
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        myAdapter.notifyDataSetChanged();
        recieveButton.setOnClickListener(clk -> {
            if (!TextUtils.isEmpty(chatText.getText())) {
                newValues.put(MyDatabaseOpenHelper.COL_MESSAGE, chatText.getText().toString());
                newValues.put(MyDatabaseOpenHelper.COL_SEND_OR_REC, false);
                long id = db.insert(MyDatabaseOpenHelper.TABLE_NAME, MyDatabaseOpenHelper.COL_MESSAGE, newValues);
                message.add(new Message(chatText.getText().toString(), false, id));
                myAdapter.notifyDataSetChanged();
                chatText.setText("");
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if(imm.isAcceptingText()) { // verify if the soft keyboard is open
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });


    }


    public class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {return message.size();
        }

        @Override
        public Message getItem(int position) {
            return message.get(position);
        }

        @Override
        public View getView(int position, View messView, ViewGroup parent) {
            View thisRow = messView;
            if (getItem(position).getSOR()) {
                thisRow = getLayoutInflater().inflate(R.layout.send_row, null);
                ImageButton sendImg = thisRow.findViewById(R.id.sendImg);
                TextView sendText = thisRow.findViewById(R.id.sendText);
                sendText.setText(getItem(position).getMessage());
            }
            else if(!getItem(position).getSOR()) {
                thisRow = getLayoutInflater().inflate(R.layout.receive_row, null);
                ImageButton recImg = thisRow.findViewById(R.id.recImg);
                TextView recText = thisRow.findViewById(R.id.recText);
                recText.setText(getItem(position).getMessage());
            }
            return thisRow;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }
    }


}
