package com.smhrd.DangBody;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;



public class EventAdapter extends ArrayAdapter<com.smhrd.DangBody.Event>
{
    CheckBox checkbox_todo;
    View view;



    public EventAdapter(@NonNull Context context, List<com.smhrd.DangBody.Event> events)
    {

        super(context, 0, events);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        com.smhrd.DangBody.Event event = getItem(position);



        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

            TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

            String eventTitle = event.getName() +" "+ CalendarUtils.formattedTime(event.getTime());
            eventCellTV.setText(eventTitle);

//        체크박스
        checkbox_todo = convertView.findViewById(R.id.checkbox_todo);
//
        checkbox_todo.setChecked(false);

        checkbox_todo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCheckboxClicked(view);
//                checkbox_todo.getText().toString();
            }
        });



        return convertView;
    }

    public void onCheckboxClicked(View view){

        if (checkbox_todo.isChecked()) {
            // TODO : CheckBox is checked.
            Toast.makeText(getContext(), "완료", Toast.LENGTH_LONG).show();
            checkbox_todo.setChecked(true);
        } else {
            // TODO : CheckBox is unchecked.
            checkbox_todo.setChecked(false);
        }



    }

}

