package com.example.johnssmarthomeapp;


import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import java.io.File;

public class MainActivity3 extends AppCompatActivity {

    //INPUT FOR URL FOR SERVER CONNECTION
    final String serverURL = "http://192.168.86.247:5000/";

    // Initialize Variables
    private String action, actionID, absPath;
    private TextView txtViewIntro;
    private Button btnRecord, btnUpload, btn1stRecord;
    private VideoView videoViewRecord;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private static int cameraPermissionCode = 100;
    private static int writePermissionCode = 101;
    private static int readPermissionCode = 102;
    private static int internetPermissionCode = 103;
    private static int networkPermissionCode = 104;
    Uri videoPathURI;

    private boolean connectedToServer;

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Get Data From Previous Screen
        Intent intent3 = getIntent();
        action = intent3.getStringExtra("action");
        actionID = intent3.getStringExtra(("actionID"));

        // Initialize
        txtViewIntro = findViewById(R.id.txtViewIntro3);
        videoViewRecord = findViewById(R.id.videoViewRecorded);

        //Change Intro
        txtViewIntro.setText("Practice performing the hand gesture for " + action);

        // Check if there is camera and ask for permission
        isCameraPresent();

        // Get result from camera and show user
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Log.i(TAG, "onActivityResult: STARTED!");
                    videoPathURI = result.getData().getData();
                    videoViewRecord.setVideoURI(videoPathURI);
                    videoViewRecord.setVisibility(View.VISIBLE);
                    videoViewRecord.start();

                    // Loop
                    videoViewRecord.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.setLooping(true);
                        }
                    });

                    // Get Video Abs Path
                    Uri savedPathURI = result.getData().getData();
                    absPath = getVideoRealPathFromURI(videoViewRecord.getContext(),savedPathURI);
                    Log.i(TAG, "onActivityResult: " + absPath);

//                    File file = new File(absPath);
//                    String videoName = file.getName();
//                    videoName = videoName.substring(0,videoName.lastIndexOf("."));
//                    String onlyPath = file.getParentFile().getAbsolutePath();
//                    String ext = file.getAbsolutePath();
//                    ext = ext.substring(ext.lastIndexOf("."));
//                    String newPath = onlyPath + "/" + actionID + ext;
//                    File newFile = new File(newPath);
//                    boolean rename = file.renameTo(newFile);


                    // Show Upload & Record Button
                    btnUpload.setVisibility(View.VISIBLE);
                    btnRecord.setVisibility(View.VISIBLE);
                    btn1stRecord.setVisibility(View.INVISIBLE);

                    // Change Intro & Button
                    txtViewIntro.setText("Would you like to upload your hand gesture?");
                    btnRecord.setText("Record Again");
                }
            }
        });

        // Set Button
        btnUpload = findViewById(R.id.btnUpload);
        btnRecord = findViewById(R.id.btnRecord);
        btn1stRecord = findViewById(R.id.btn1stRecord);

        // 1st Record Button
        btn1stRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(intent);
                }
            }
        });

        // Record Button
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i(TAG, "onClick: Clicked");
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(intent);
                }
            }
        });

        // Upload Button
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermissions();

                // Sending to Server
                Context sending = videoViewRecord.getContext();
                okHttpClient = new OkHttpClient();
                String dummyText = actionID;
                File file = new File(absPath);
                RequestBody formbody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("actionID",actionID).addFormDataPart("file",file.getName(),
                        RequestBody.create(file,MediaType.parse("video/mp4"))).build();

                Request request = new Request.Builder().url(serverURL).post(formbody).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                connectedToServer = false;
                                Toast.makeText(MainActivity3.this, "Could Not Connect To Server, Reset Application or Try Again", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        connectedToServer = true;
                    }
                });
                if (connectedToServer) {
                    Toast.makeText(MainActivity3.this, "Gesture " + actionID.toString() + " has been uploaded to the server", Toast.LENGTH_LONG).show();
                    // Return to screen 1
                    Intent myIntent = new Intent(MainActivity3.this,MainActivity.class);
                    startActivity(myIntent);
                }
            }
        });

    }

    private boolean isCameraPresent (){
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            getCameraPermission();
            return true;
        } else {
            return false;
        }
    }

    private void getCameraPermission () {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            Log.i(TAG, "getCameraPermission: Camera Permission Denied at 1st");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, cameraPermissionCode);
        }
    }

    private void getPermissions () {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            Log.i(TAG, "getCameraPermission: Write External Permission Denied at 1st");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, writePermissionCode);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            Log.i(TAG, "getCameraPermission: Read External Permission Denied at 1st");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, readPermissionCode);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            Log.i(TAG, "getCameraPermission: Internet Permission Denied at 1st");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, internetPermissionCode);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED) {
            Log.i(TAG, "getCameraPermission: Access Network Permission Denied at 1st");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, networkPermissionCode);
        }
    }

    private String getVideoRealPathFromURI(Context context, Uri contentUri) {
        Cursor cur = null;
        try {
            String[] proj = { MediaStore.Video.Media.DATA };
            cur = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cur.moveToFirst();
            return cur.getString(column_index);
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
    }
}