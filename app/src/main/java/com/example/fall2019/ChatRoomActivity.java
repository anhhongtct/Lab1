package com.example.fall2019;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity{
    ArrayList<String> message = new ArrayList<>();
    boolean sendOrRec;
    BaseAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView theList =findViewById(R.id.theList);
        Button sendButton = findViewById(R.id.button1_chat);
        Button recieveButton = findViewById(R.id.button2_chat);
        EditText chatText = findViewById(R.id.edit_chat);
        theList.setAdapter( myAdapter = new Message() );

        sendButton.setOnClickListener(clk -> message.add(chatText.getText().toString());

    }





    public class Message extends BaseAdapter {
        @Override
        public int getCount() {
            return message.size();
        }

        @Override
        public Object getItem(int position) {
            return message.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate()
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
