package com.smhrd.DangBody;

//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {
//    private List<String> list;
//    private ViewGroup viewGroup;
//
//    public HomeAdapter(List<String>list){
//        this.list = list;
//    }
//    public HomeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(android.R.layout.simple_expandable_list_item_1,viewGroup,false);
//        return new HomeViewHolder(view);
//    }
//
//    public void onBindViewHolder(HomeViewHolder viewHolder, int position){
//        String text = list.get(position);
//        viewHolder.title.setText(text);
//    }
//    public int getItemCount(){
//        return list.size();
//    }
//}
