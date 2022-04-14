package com.smhrd.DangBody;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommnityAdapter extends RecyclerView.Adapter<CommnityAdapter.ViewHolder>{

    ArrayList<CommunityVO> items = new ArrayList<CommunityVO>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemview = inflater.inflate(R.layout.community_item, parent, false);

        return new ViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CommunityVO item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProfile;
        ImageView imgPost;
        TextView tvNick;
        TextView tvContent;

        public ViewHolder(View itemView){
            super(itemView);

            imgProfile = itemView.findViewById(R.id.imgProfile);
            imgPost = itemView.findViewById(R.id.imgPost);
            tvNick = itemView.findViewById(R.id.tvNick);
            tvContent = itemView.findViewById(R.id.tvContent);
        }

        public void setItem(CommunityVO item){

            imgProfile.setImageResource(R.drawable.dang1);
            imgPost.setImageResource(R.drawable.dang1);
            tvNick.setText(item.getNick());
            tvContent.setText(item.getContent());

        }
    }

    public void addItem(CommunityVO item){
        items.add(item);
    }

    public void setItems(ArrayList<CommunityVO> items){
        this.items = items;
    }

    public CommunityVO getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, CommunityVO item){
        items.set(position, item);
    }


}
