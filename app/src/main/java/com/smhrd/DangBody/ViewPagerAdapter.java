package com.smhrd.DangBody;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.image_slide, R.drawable.image_slide2, R.drawable.image_slide3};
    View view;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }


    public int getCount() {
        return images.length;
    }


    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;

    }


    @NonNull

    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.imageslide1,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView3);
        imageView.setImageResource(images[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return view;
    }


    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
