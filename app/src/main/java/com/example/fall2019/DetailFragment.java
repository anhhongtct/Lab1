package com.example.fall2019;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class DetailFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    private int index;

    public void setTablet(boolean tablet) { isTablet = tablet; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result =inflater.inflate(R.layout.detail_menu_2, container, false);

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong("id");
        index = dataFromActivity.getInt("index");

        // Inflate the layout for this fragment

        //show the message
        TextView message = result.findViewById(R.id.message);
        message.setText(dataFromActivity.getString("message"));

        //show the id:
        TextView idView = result.findViewById(R.id.idText);
        idView.setText("ID=" + id);

//        idView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "fjdsfadsafdsa", Toast.LENGTH_SHORT).show();
//            }
//        });

        // get the delete button, and add a click listener:
        Button deleteButton = result.findViewById(R.id.deleteBtn);

        deleteButton.setOnClickListener(clk -> {


                if (isTablet) { //both the list and details are on the screen:
                    ChatRoomActivity parent = (ChatRoomActivity) getActivity();
                    parent.deleteMessageId(index); //this deletes the item and updates the list


                    //now remove the fragment since you deleted it from the database:
                    // this is the object to be removed, so remove(this):
                    parent.getSupportFragmentManager().beginTransaction().remove(DetailFragment.this).commit();
                }
                //for Phone:
                else //You are only looking at the details, you need to go back to the previous list page
                {
                    EmptyActivity parent = (EmptyActivity) getActivity();
                    Intent backToFragmentExample = new Intent();
                    backToFragmentExample.putExtra("index", dataFromActivity.getInt("index"));
                    parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                    parent.finish(); //go back
                }
            });

        return result;
    }
}
