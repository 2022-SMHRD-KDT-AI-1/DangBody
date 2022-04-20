package com.smhrd.DangBody;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.CameraPosition;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_activity extends Fragment implements OnMapReadyCallback {

    public Fragment_activity() {
    }

    private NaverMap naverMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    LocationManager manager;

    double meters;
    TextView textView, timerText, distanceText;
    ImageButton btnStart, btnPause, btnCamera;
    ArrayList<LatLng> myLatLng = new ArrayList<>();
    boolean isWalking = true;
    Marker oldMarker = null;
    CameraPosition cameraPosition;
    //추가(타이머)
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    Button btnWr;

    // 서버 전송용 변수
    String walkTime, distance, currentDay;

    RequestQueue requestQueue;
    StringRequest request;
    Date date;

    SharedPreferences sp;

    //추가 (카메라)
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String TAG = "카메라";
    private String currentPhotoPath;
    static final int GET_GALLERY_IMAGE = 2;

    //추가 스크린샷
    public static final int REQUEST_EXTERNAL_STORAGE = 1;

    boolean timerStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        MapView mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        //추가
        textView = view.findViewById(R.id.textView);
        timerText = view.findViewById(R.id.timerText);
        btnStart = view.findViewById(R.id.btnStart);
        btnPause = view.findViewById(R.id.btnPause);
        btnCamera = view.findViewById(R.id.btnCamera);
        distanceText = view.findViewById(R.id.distanceText);
        btnWr = view.findViewById(R.id.btnWr);
        locationSource = new FusedLocationSource(getActivity(), LOCATION_PERMISSION_REQUEST_CODE);
        sp = getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();
        currentDay = sdf1.format(date);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }
        //카메라 메소드 추가
        init(view);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_open_intent();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTapped(null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("")
                        .setMessage("산책 기록을 저장하시겠습니까?")
                        .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = "http://220.71.97.178:8082/dangbody/WalkService";

                                Toast.makeText(getActivity(), walkTime, Toast.LENGTH_SHORT).show();
               /* Log.d("넘어가라고!!!!!",walkTime);
                Log.d("넘어가라고!!!!!",distance);
                Log.d("넘어가라고!!!!!",currentDay);
                Log.d("넘어가라고!!!!!",sp.getString("user_id","test"));*/

                                request = new StringRequest(
                                        Request.Method.POST,
                                        url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if (response.equals("0")) {
                                                    Toast.makeText(getActivity(), "저장실패", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getActivity(), "저장성공", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }
                                ) {
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();

                                        params.put("user_id", sp.getString("user_id", "test"));
                                        params.put("walk_time", walkTime);
                                        params.put("walk_distance", distance);
                                        params.put("walk_date", currentDay);

                                        Log.d("Main", "user_id:" + sp.getString("user_id", "test"));
                                        Log.d("Main", "walkTime:" + walkTime);
                                        Log.d("Main", "distance:" + distance);
                                        Log.d("Main", "currentDay:" + currentDay);

                                        return params;
                                    }
                                };
                                requestQueue.add(request);
                                takeScreenShot(getView().getRootView(),"result");

                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();

                dialog.show();


            }
        });

        btnWr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalkRecordActivity.class);
                startActivity(intent);
            }
        });


        mapView.getMapAsync(this);


        return view;
    }

    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        // 현재 위치 버튼 안보이게 설정
//        UiSettings uiSettings = naverMap.getUiSettings();
//
//        uiSettings.setLocationButtonEnabled(true);
//
//
//        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        //타이머 추가코드
        timer = new Timer();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLocationService();
                startTapped(null);
            }
        });


    }

    public void startLocationService() {
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "퍼미션 획득실패", Toast.LENGTH_LONG).show();
                return;
            }

            manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            Location location = getLastKnownLocation();
            //Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude(); //위도 - 가로좌표
                double longitude = location.getLongitude(); //경도 - 세로좌표
                String message = "최근 위치 -> Lat:" + latitude + ", Lon:" + longitude;
                textView.setText(message);
            } else {
                Toast.makeText(getActivity(), "location null", Toast.LENGTH_LONG).show();
            }

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();

                    String message = "최근 위치 -> Lat:" + lat + ", Lon:" + lng;
                    textView.setText(message);

                    //카메라 자동이동
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat,lng));
                    naverMap.moveCamera(cameraUpdate);

                    if( isWalking == true ) {
                        //10초마다 저장된다.

                        myLatLng.add(new LatLng(lat, lng));

                        displayMeters();

                        //마커세팅
                        if( oldMarker != null )
                            oldMarker.setMap(null);


                        Marker marker = new Marker();
                        marker.setPosition(new LatLng(lat, lng));
                        marker.setIcon(OverlayImage.fromResource(R.drawable.dogicon));
                        marker.setWidth(80);
                        marker.setHeight(80);
                        marker.setHideCollidedSymbols(true);
                        marker.setMap(naverMap);
                        oldMarker = marker;

                        // 경로 그리기
                        if( myLatLng.size() >= 2 ) {
                            //경로를 다시 그린다.
                            PathOverlay path = new PathOverlay();
                            path.setWidth(20);
                            path.setColor(Color.RED);
                            path.setCoords(
                                    (List) myLatLng
                            );
                            path.setMap(naverMap);
                        }else{
                            Log.d("Main","위치정보리스트가 2미만임.");
                        }
                    }


                    Log.d("NaverMap>>", "latitude: "+ lat +", longitude: "+ lng);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.d("NaverMap>>", "onStatusChanged");
                }

                public void onProviderEnabled(String provider) {
                    Log.d("NaverMap>>", "onProviderEnabled");
                }

                public void onProviderDisabled(String provider) {
                    Log.d("NaverMap>>", "onProviderDisabled");
                }
            };

            //리스너를 이용하여 변경된 위치를 매번 수신함.
//            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, locationListener);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);


//            //LocationListener를 이용하여 변경된 위치를 매번 수신함.
//            GPSListener gpsListener = new GPSListener();
//            long minTime = 3000; //3초 타임아웃
//            float minDistance = 0; //0미터 오차허용범위  10미터~30미터
//            manager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    minTime,
//                    minDistance,
//                    gpsListener
//            );

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getActivity(), "GPS좌표 요청함.", Toast.LENGTH_LONG).show();
    }

    private Location getLastKnownLocation() {
        manager = (LocationManager) getActivity().getApplicationContext().getSystemService(getActivity().LOCATION_SERVICE);
        List<String> providers = manager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            Location l = manager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    //GPS로 위치받아오기
    class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Toast.makeText(getActivity(), "GPS실행!", Toast.LENGTH_SHORT).show();

            String message = "내 위치 -> Lat:" + latitude + ", Lon:" + longitude;
            Log.d("Fragment_activity", message);

            //카메라 자동이동
            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
            naverMap.moveCamera(cameraUpdate);

            textView.setText(message);
            distance = String.format("%.1f", meters);
//                    거리구하기
//           Projection projection = naverMap.getProjection();
//            double distance = projection.getDistance(latitude,longitude);
//            double distance = Projection.getDistance(new LatLng(37.5041435, 126.7566533), new LatLng(37.5047083, 126.7752313));

            //이거 확인해죠!!!
            //if( MainActivity.this.isWalking == true ) {
            if (isWalking == true) {
                //10초마다 저장된다.

                //MainActivity.this.myLatLng.add(new LatLng(latitude, longitude));
                myLatLng.add(new LatLng(latitude, longitude));

                displayMeters();


                //마커세팅
                if (oldMarker != null)
                    oldMarker.setMap(null);


                Marker marker = new Marker();
                marker.setPosition(new LatLng(latitude, longitude));
                marker.setIcon(OverlayImage.fromResource(R.drawable.dogicon));
                marker.setWidth(80);
                marker.setHeight(80);
                marker.setHideCollidedSymbols(true);

                marker.setMap(naverMap);
                oldMarker = marker;

                // 경로 그리기
                if (myLatLng.size() >= 2) {
                    //   if( MainActivity.this.myLatLng.size() >= 2 ) {
                    //경로를 다시 그린다.
                    PathOverlay path = new PathOverlay();
                    path.setWidth(20);
                    path.setColor(Color.RED);
                    path.setCoords(
                            (List) myLatLng
                            //     (List) MainActivity.this.myLatLng
                    );
                    path.setMap(naverMap);
                } else {
                    Log.d("Main", "위치정보리스트가 2미만임.");
                }
            }

        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }

    // 위치 권한설정
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }


    //거리계산
    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    // 거리출력
    public void displayMeters() {


        if (this.myLatLng.size() >= 2) {
            LatLng pos1 = this.myLatLng.get(0);
            LatLng pos2 = this.myLatLng.get(this.myLatLng.size() - 1);

            meters = distance(pos1.latitude, pos1.longitude,
                    pos2.latitude, pos2.longitude, 'K');
//            meters = (double)Math.round((meters*10)/10);

            Log.d("Main", meters + " Kilometers\n");
            distanceText.setText(String.format("%.1f", meters));
        } else {
            Log.d("Main", "displayMeters 2개 이하입니다.");
        }

    }

    //타이머 메소드 추가


    public void startTapped(View view) {
        if (timerStarted == false) {
            timerStarted = true;
            startTimer();
        }

    }

    public void stopTapped(View view) {
        if (timerStarted == true) {
            timerStarted = false;
            timerTask.cancel();
        }
    }


    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                        walkTime = timerText.getText().toString();
                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }


    @NonNull
    private String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    @NonNull
    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }

    //타이머 끝


    //카메라 시작

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Uri picturePhotoURI = Uri.fromFile(new File(currentPhotoPath));

//            getBitmap(picturePhotoURI);
            // img.setImageBitmap(bitmap);

            //갤러리에 사진 저장
            saveFile(currentPhotoPath);
//
//            progress = new ProgressDialog(MainActivity.this);
//            progress.setMessage("Uploading...");
//            progress.show();
//
//            sendImage();
        } else if (requestCode == GET_GALLERY_IMAGE && resultCode == getActivity().RESULT_OK) {
            Uri galleryURI = data.getData();
            //img.setImageURI(galleryURI);

//            getBitmap(galleryURI);
            //img.setImageBitmap(bitmap);
        }

    }

    //xml에 정의한 view 초기화
    private void init(View view) {
        btnCamera = view.findViewById(R.id.btnCamera);
//        btnGallery = findViewById(R.id.btnGallery);

//        queue = Volley.newRequestQueue(MainActivity.this);

        requestPermission();
    }

    //카메라, 쓰기, 읽기 권한 체크/요청
    private void requestPermission() {
        //민감한 권한 사용자에게 허용요청
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) { // 저장소에 데이터를 쓰는 권한을 부여받지 않았다면~

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    //갤러리 사진 저장 기능
    private void saveFile(String currentPhotoPath) {

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);

        ContentValues values = new ContentValues();

        //실제 앨범에 저장될 이미지이름
        values.put(MediaStore.Images.Media.DISPLAY_NAME, new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");

        //저장될 경로 -> /내장 메모리/DCIM/ 에 'AndroidQ' 폴더로 지정
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/AndroidQ");

        Uri u = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        Uri uri = getActivity().getContentResolver().insert(u, values); //이미지 Uri를 MediaStore.Images에 저장

        try {
            /*
             ParcelFileDescriptor: 공유 파일 요청 객체
             ContentResolver: 어플리케이션끼리 특정한 데이터를 주고 받을 수 있게 해주는 기술(공용 데이터베이스)
                            ex) 주소록이나 음악 앨범이나 플레이리스트 같은 것에도 접근하는 것이 가능

            getContentResolver(): ContentResolver객체 반환
            */

            ParcelFileDescriptor parcelFileDescriptor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(uri, "w", null); //미디어 파일 열기
            }
            if (parcelFileDescriptor == null) return;

            //바이트기반스트림을 이용하여 JPEG파일을 바이트단위로 쪼갠 후 저장
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            //비트맵 형태 이미지 크기 압축
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] b = byteArrayOutputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(b);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffers = new byte[bufferSize];

            int len = 0;
            while ((len = inputStream.read(buffers)) != -1) {
                buffer.write(buffers, 0, len);
            }

            byte[] bs = buffer.toByteArray();
            FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            fileOutputStream.write(bs);
            fileOutputStream.close();
            inputStream.close();
            parcelFileDescriptor.close();

            getActivity().getContentResolver().update(uri, values, null, null); //MediaStore.Images 테이블에 이미지 행 추가 후 업데이트

        } catch (Exception e) {
            e.printStackTrace();
        }

        values.clear();
        values.put(MediaStore.Images.Media.IS_PENDING, 0); //실행하는 기기에서 앱이 IS_PENDING 값을 1로 설정하면 독점 액세스 권한 획득
        getActivity().getContentResolver().update(uri, values, null, null);

    }

    //카메라 호출
    private void camera_open_intent() {
        Log.d("Camera", "카메라실행!");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG, "에러발생!!");
            }

            if (photoFile != null) {
                //uriToSend = FileProvider.getUriForFile(ctx, "com.mycompany.myfirstapp.fileprovider", f);
                Uri providerURI = FileProvider.getUriForFile(getActivity(), "com.example.DangBody.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    //카메라 촬영 시 임시로 사진을 저장하고 사진위치에 대한 Uri 정보를 가져오는 메소드
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        Log.d(TAG, "사진저장>> " + storageDir.toString());

        currentPhotoPath = image.getAbsolutePath();

        return image;
    }

    //카메라 끝

    //스크린샷 시작

    public static File takeScreenShot(View mapView, String fileName){
        Date date2 = new Date();
        CharSequence format = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date());

        try{

            String dirPath = Environment.getExternalStorageDirectory().toString() + "";
            File fileDir = new File(dirPath);
            if(!fileDir.exists()){
                boolean mkdir = fileDir.mkdir();
            }

            String path = dirPath + "/" + fileName + "-" + format + ".jpeg";

            mapView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(mapView.getDrawingCache());
            mapView.setDrawingCacheEnabled(false);

            File imageFile = new File(path);

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 스크린샷 끝



}