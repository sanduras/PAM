package com.example.laboratorul1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 0;
    private Bitmap photo;
    private Bitmap imageBitmap;
    private CameraManager cameraManager;
    private String cameraID;
    private int check  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            cameraID  = cameraManager.getCameraIdList()[0];
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    public void onClick(View view){
        String CHANNEL_ID = "chanel_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "my_chanel";
            String description = "This is my chanel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
           NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder mBuilder  = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID) ;
                mBuilder.setSmallIcon(R.drawable.notification_icon);
                mBuilder.setContentTitle("My notification");
                mBuilder.setContentText("Hello World!");
                Notification notification = mBuilder.build();
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                nm.notify(2, notification);

            }
        }, 10);
    }


    public void search (View view){
       EditText myinput   = findViewById(R.id.search);
       String keyword = myinput.getText().toString();
       if(!myinput.equals("")){
           openBrowser(keyword);
       }

    }

    public void openBrowser(String keyword){
        Intent intent  = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, keyword);
        startActivity(intent);
    }

    public void open (View view){
        RadioButton front = findViewById(R.id.front);
        RadioButton back = findViewById(R.id.back);
        if(front.isChecked()){
            openFrontCamera();
        }
        if(back.isChecked()){
            openBackCamera();
        }
    }
    public void openFrontCamera(){

       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

       intent.putExtra("android.intent.extras.CAMERA_FACING",1);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }



    public void openBackCamera(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING",0);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

        }
    }

    public void showPhoto(View view){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,90,stream);
        byte[] image = stream.toByteArray();
        Intent intents = new Intent(MainActivity.this, MainActivity2.class);
        intents.putExtra("photo", image);
        startActivity(intents);
    }
    public void turn(View view){
        if(check == 0){
            try{
                cameraManager.setTorchMode(cameraID, true);
                check = 1;

            }catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
       else{
            try{
                cameraManager.setTorchMode(cameraID, false);
                check = 0;

            }catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }




    }





}