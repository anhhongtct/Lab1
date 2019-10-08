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
        ListView theList =findViewById(R.id.theList);
        Button sendButton = findViewById(R.id.button1_chat);
        Button recieveButton = findViewById(R.id.button2_chat);
        EditText chatText = findViewById(R.id.edit_chat);
        theList.setAdapter( myAdapter = new MyListAdapter() );

        sendButton.setOnClickListener(clk -> {
            message.add(new Message(chatText.getText().toString(), true));
            chatText.setText("");
            myAdapter.notifyDataSetChanged();
        });

        recieveButton.setOnClickListener(clk -> {
            message.add(new Message(chatText.getText().toString(), false));
            chatText.setText("");
            myAdapter.notifyDataSetChanged();
        });
    }
    public class Message {
        String mess;
        boolean sendOrRec;
        public Message(String mes, boolean sR) {
            this.mess = mes;
            this.sendOrRec = sR;
        }

        public boolean getSOR (){
            return sendOrRec;
        }

        public String getMessage() {
            return mess;
        }

    }

    public class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return message.size();
        }

        @Override
        public Object getItem(int position) {
            return message.get(position);
        }

        @Override
        public View getView(int position, View messView, ViewGroup parent) {
            View thisRow = messView;
            if(messView == null) {
                if (message.get(position).getSOR()) {
                thisRow = getLayoutInflater().inflate(R.layout.send_row, null);
                    ImageButton sendImg = thisRow.findViewById(R.id.sendImg);
                    TextView sendText = thisRow.findViewById(R.id.sendText);
                    sendText.setText(message.get(position).getMessage());
                }
                else {
                    thisRow = getLayoutInflater().inflate(R.layout.receive_row, null);
                    ImageButton recImg = thisRow.findViewById(R.id.recImg);
                    TextView recText = thisRow.findViewById(R.id.recText);
                    recText.setText(message.get(position).getMessage());
                }
            }
            return thisRow;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
