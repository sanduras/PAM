package com.example.laboratorul1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity2 extends AppCompatActivity {
   ImageView imageView;
    int imagevalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        byte[] byteArrayExtra = getIntent().getByteArrayExtra("photo");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayExtra, 0 , byteArrayExtra.length);
        // initialise the layout
        imageView = findViewById(R.id.myphoto);
//
//        // check if any value sent from previous activity
//        Bundle bundle = getIntent().getExtras();
//
//        // if bundle is not null then get the image value
//        if (bundle != null) {
//            imagevalue = bundle.getInt("photo");
//        }
        imageView.setImageBitmap(bitmap);
    }
}