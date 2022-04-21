package com.smhrd.DangBody;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WalkRecordAdapter extends RecyclerView.Adapter<WalkRecordAdapter.ViewHolder> {
    ArrayList<WalkRecord> items = new ArrayList<WalkRecord>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.activity_walk_records, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        WalkRecord item = items.get(position);

        viewHolder.date.setText(item.getDate());
        viewHolder.time.setText(item.getTime());
        viewHolder.meters.setText(item.getMeters());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(WalkRecord item) {
        items.add(item);
    }

    public void setItems(ArrayList<WalkRecord> items) {
        this.items = items;
    }

    public WalkRecord getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, WalkRecord item) {
        items.set(position, item);
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, meters;

        public ViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.textView);
            time = itemView.findViewById(R.id.time);
            meters = itemView.findViewById(R.id.meters);

        }
    }

}