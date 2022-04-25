package com.smhrd.DangBody;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DangbodyGallery extends AppCompatActivity {

    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangbody_gallery);

        img1 = findViewById(R.id.dangbodtGallery1);
        String imageStr = "http://e1.project-jupyter.ddns.net/lab/workspaces/auto-G/tree/content/yolov5/runs/detect/bean/dog_data4.jpg";
        final Bitmap[] bitmap = new Bitmap[1];

        Glide.with(this)
                .load(imageStr)

                .into(img1);


//        Thread uThread = new Thread(){
//            public void run(){
//
//                try{
//                    URL url1 = new URL("http://e1.project-jupyter.ddns.net/lab/tree/content/yolov5/runs/detect/bean/dog_data3.jpg");
//
//                    HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
//
//                    conn.setDoInput(true);
//                    conn.connect();
//
//                    InputStream is = conn.getInputStream();
//                    bitmap[0] = BitmapFactory.decodeStream(is);
//
//                }catch (MalformedURLException e){
//                    e.printStackTrace();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        uThread.start();
//
//        try{
//            uThread.join();
//            img1.setImageBitmap(bitmap[0]);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }

   }



    }