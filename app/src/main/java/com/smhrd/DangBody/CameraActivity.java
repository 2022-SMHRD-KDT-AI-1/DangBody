package com.smhrd.DangBody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smhrd.DangBody.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CameraActivity extends AppCompatActivity {

    private ImageView img;
    private Button btn_capture, btn_gallery, btn_send;
    private ProgressDialog progress;

    private RequestQueue queue;
    private String currentPhotoPath;
    private Bitmap bitmap;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int GET_GALLERY_IMAGE = 2;

    static final String TAG = "카메라";
    private String imageString;
    SharedPreferences sp;
    String pet_name, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        init();
        sp = getSharedPreferences("loginData",MODE_PRIVATE);
        pet_name = "bean";
        id=sp.getString("user_id","사람이름");


        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_open_intent();
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery_open_intent();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(CameraActivity.this);
                progress.setMessage("Uploading...");
                progress.show();

                sendImage();
            }
        });
    }

    //이미지 플라시크로 전송
    private void sendImage() {

        Log.d("이미지전송", "dd");

        //비트맵 이미지를 byte로 변환 -> base64형태로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //base64형태로 변환된 이미지 데이터를 플라스크 서버로 전송
        String flask_url = "http://121.147.52.96:5000/sendFrame";

        StringRequest request = new StringRequest(Request.Method.POST, flask_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();

                        byte[] data = Base64.decode(response, Base64.DEFAULT);

                        Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                        img.setImageBitmap(bm);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(CameraActivity.this, "Some error occurred -> "+error, Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //강아지이름, 이미지 전달
                params.put("user_id",id);
                params.put("image", imageString);
                params.put("pet_name",pet_name);
                return params;
            }
        };

        Log.d("CameraActivity","실행전");
        request.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        queue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri picturePhotoURI = Uri.fromFile(new File(currentPhotoPath));

            getBitmap(picturePhotoURI);
            img.setImageBitmap(bitmap);

            //갤러리에 사진 저장
            saveFile(currentPhotoPath);

        } else if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK) {
            Uri galleryURI = data.getData();
            //img.setImageURI(galleryURI);

            getBitmap(galleryURI);
            img.setImageBitmap(bitmap);
        }

    }

    //Uri에서 bisap
    private void getBitmap(Uri picturePhotoURI) {
        try {
            //서버로 이미지를 전송하기 위한 비트맵 변환하기
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picturePhotoURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //xml에 정의한 view 초기화
    private void init() {
        img = findViewById(R.id.img);
        btn_capture = findViewById(R.id.btn_capture);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_send = findViewById(R.id.btn_send);

        queue = Volley.newRequestQueue(CameraActivity.this);

        requestPermission();
    }

    //카메라, 쓰기, 읽기 권한 체크/요청
    private void requestPermission() {

        //민감한 권한 사용자에게 허용요청
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) { // 저장소에 데이터를 쓰는 권한을 부여받지 않았다면~

            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    //갤러리 띄우기
    private void gallery_open_intent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GET_GALLERY_IMAGE);
    }

    //갤러리 사진 저장 기능
    private void saveFile(String currentPhotoPath) {

        Bitmap bitmap = BitmapFactory.decodeFile( currentPhotoPath );

        ContentValues values = new ContentValues( );

        //실제 앨범에 저장될 이미지이름
        values.put( MediaStore.Images.Media.DISPLAY_NAME, new SimpleDateFormat( "yyyyMMdd_HHmmss", Locale.US ).format( new Date( ) ) + ".jpg" );
        values.put( MediaStore.Images.Media.MIME_TYPE, "image/*" );

        //저장될 경로 -> /내장 메모리/DCIM/ 에 'AndroidQ' 폴더로 지정
        values.put( MediaStore.Images.Media.RELATIVE_PATH, "DCIM/AndroidQ" );

        Uri u = MediaStore.Images.Media.getContentUri( MediaStore.VOLUME_EXTERNAL );
        Uri uri = getContentResolver( ).insert( u, values ); //이미지 Uri를 MediaStore.Images에 저장

        Log.d("CameraApp", u.getPath());
        Log.d("CameraApp", uri.getPath());


        try {
            /*
             ParcelFileDescriptor: 공유 파일 요청 객체
             ContentResolver: 어플리케이션끼리 특정한 데이터를 주고 받을 수 있게 해주는 기술(공용 데이터베이스)
                            ex) 주소록이나 음악 앨범이나 플레이리스트 같은 것에도 접근하는 것이 가능

            getContentResolver(): ContentResolver객체 반환
            */

            ParcelFileDescriptor parcelFileDescriptor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                parcelFileDescriptor = getContentResolver( ).openFileDescriptor( uri, "w", null ); //미디어 파일 열기
            }
            if ( parcelFileDescriptor == null ) return;

            //바이트기반스트림을 이용하여 JPEG파일을 바이트단위로 쪼갠 후 저장
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( );

            //비트맵 형태 이미지 크기 압축
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream );
            byte[] b = byteArrayOutputStream.toByteArray( );
            InputStream inputStream = new ByteArrayInputStream( b );

            ByteArrayOutputStream buffer = new ByteArrayOutputStream( );
            int bufferSize = 1024;
            byte[] buffers = new byte[ bufferSize ];

            int len = 0;
            while ( ( len = inputStream.read( buffers ) ) != -1 ) {
                buffer.write( buffers, 0, len );
            }

            byte[] bs = buffer.toByteArray( );
            FileOutputStream fileOutputStream = new FileOutputStream( parcelFileDescriptor.getFileDescriptor( ) );
            fileOutputStream.write( bs );
            fileOutputStream.close( );
            inputStream.close( );
            parcelFileDescriptor.close( );

            getContentResolver( ).update( uri, values, null, null ); //MediaStore.Images 테이블에 이미지 행 추가 후 업데이트

        } catch ( Exception e ) {
            e.printStackTrace( );
        }

        values.clear( );
        values.put( MediaStore.Images.Media.IS_PENDING, 0 ); //실행하는 기기에서 앱이 IS_PENDING 값을 1로 설정하면 독점 액세스 권한 획득
        getContentResolver( ).update( uri, values, null, null );

    }

    //카메라 호출
    private void camera_open_intent() {
        Log.d("Camera", "카메라실행!");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG, "에러발생!!");
            }

            if (photoFile != null) {
                Uri providerURI = FileProvider.getUriForFile(this, "com.example.DangBody.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    //카메라 촬영 시 임시로 사진을 저장하고 사진위치에 대한 Uri 정보를 가져오는 메소드
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        Log.d(TAG, "사진저장>> "+storageDir.toString());

        currentPhotoPath = image.getAbsolutePath();

        return image;
    }


}

