package com.smhrd.DangBody;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WalkRecordAdapter {

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        TextView textView2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
//            textView2 = itemView.findViewById(R.id.textView2);



        }
    }

}
