package com.smhrd.DangBody;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>{

    ArrayList<CommunityVO> items = new ArrayList<CommunityVO>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.community_item, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int position) {

        CommunityVO item = items.get(position);

        viewholder.tvNick.setText(item.getNick());
        viewholder.tvlikes.setText(String.valueOf(item.getLikes()));
        viewholder.tvContent.setText(item.getContent());
        viewholder.imgPost.setImageResource(R.drawable.round);

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPost;
        TextView tvNick, tvContent, tvlikes;

        public ViewHolder(View itemView) {
            super(itemView);

            tvlikes = itemView.findViewById(R.id.tvlikes);
            imgPost = itemView.findViewById(R.id.imgPost);
            tvNick = itemView.findViewById(R.id.tvNick);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
//        public void setItem(CommunityVO item){
//
//            imgProfile.setImageResource(item.getProfile());
//            imgPost.setImageResource(item.getPost());
//            tvNick.setText(item.getNick());
//            tvContent.setText(item.getContent());
//
//        }


//    public void addItem(CommunityVO item){
//        items.add(item);
//    }
//
//    public void setItems(ArrayList<CommunityVO> items){
//        this.items = items;
//    }
//
//    public CommunityVO getItem(int position){
//        return items.get(position);
//    }
//
//    public void setItem(int position, CommunityVO item){
//        items.set(position, item);
//    }
    @Override
    public int getItemCount() {
    return items.size();
}

    public void addItem(CommunityVO item) {
        items.add(item);
    }

    public void setItems(ArrayList<CommunityVO> items) {
        this.items = items;
    }

    public CommunityVO getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, CommunityVO item) {
        items.set(position, item);
    }


}
