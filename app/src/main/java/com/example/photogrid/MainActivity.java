package com.example.photogrid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.example.photogrid.DownlodPhoto.PhotoCat;
import com.example.photogrid.DownlodPhoto.PhotoServices;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    public int image_id;
    TextView Gallery;
    Uri imageUi;


    private static final int REQUSET_IMAGE_CHAPTER = 101;
    private  static  final int ACITON_PICK_Image=100;
    Dialog dialog;
    TextView view1;
    TextView saveImage;
    public  FileOutputStream outStream = null;
    BitmapDrawable bitmapDrawable;
    public Bitmap bitmap1;
    TextView downPhoto;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView3);
        imageView1 = findViewById(R.id.imageView4);
        imageView2 = findViewById(R.id.imageView5);
        imageView3 = findViewById(R.id.imageView6);
        imageView4 = findViewById(R.id.imageView7);
        imageView5 = findViewById(R.id.imageView8);
        imageView6 = findViewById(R.id.imageView9);
        imageView7 = findViewById(R.id.imageView10);
        imageView8 = findViewById(R.id.imageView11);


        // view1=findViewById(R.id.take_photo);
        imageView.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);


    }

    public void GetDialog(View view) {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_photo);
        view1 = dialog.findViewById(R.id.take_photo);
        Gallery=dialog.findViewById(R.id.From_Gallery);
        saveImage=dialog.findViewById(R.id.save_image);
        downPhoto=dialog.findViewById(R.id.DownlodPhoto);
        dialog.show();
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                // Here you check any applicition hndlad it
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, REQUSET_IMAGE_CHAPTER);
                }
                dialog.hide();

            }


        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACITON_PICK_Image);
                dialog.hide();

            }
        });

        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (image_id == 1) {
                    bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                    bitmap1 = bitmapDrawable.getBitmap();
                }
                else if (image_id == 2) {
                    bitmapDrawable = (BitmapDrawable) imageView1.getDrawable();
                    bitmap1 = bitmapDrawable.getBitmap();
                }
                else if (image_id == 3) {
                    bitmapDrawable = (BitmapDrawable) imageView2.getDrawable();
                    bitmap1 = bitmapDrawable.getBitmap();
                }
                else if (image_id == 4) {
                    bitmapDrawable = (BitmapDrawable) imageView3.getDrawable();
                    bitmap1 = bitmapDrawable.getBitmap();
                }
                else if (image_id == 5) {
                    bitmapDrawable = (BitmapDrawable) imageView4.getDrawable();
                    bitmap1 = bitmapDrawable.getBitmap();
                }
                else if (image_id == 6) {
                    bitmapDrawable = (BitmapDrawable) imageView5.getDrawable();
                    bitmap1 = bitmapDrawable.getBitmap();
                }
                else if (image_id == 7) {
                    bitmapDrawable = (BitmapDrawable) imageView6.getDrawable();
                    bitmap1 = bitmapDrawable.getBitmap();
                }
                else if (image_id == 8) {
                    bitmapDrawable = (BitmapDrawable) imageView7.getDrawable();
                    bitmap1 = bitmapDrawable.getBitmap();
                }
                else if (image_id == 9) {
                    bitmapDrawable = (BitmapDrawable) imageView8.getDrawable();
                    bitmap1 = bitmapDrawable.getBitmap();
                }
                else
                     Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/ImagesFromApp");
                dir.mkdirs();
                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(outFile));
                sendBroadcast(intent);
                dialog.hide();
                Toast.makeText(getApplicationContext(),"Image was saved",Toast.LENGTH_SHORT).show();

            }
        });

        downPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchPhotoUrl();
                loadimage(url);

            }
        });



    }
    public void loadimage(String ur){


        if (image_id == 1)
            Picasso.with(this).load(url).into(imageView);
        else if (image_id == 2)
            Picasso.with(this).load(url).into(imageView1);
        else if (image_id == 3)
            Picasso.with(this).load(url).into(imageView2);
        else if (image_id == 4)
            Picasso.with(this).load(url).into(imageView3);
        else if (image_id == 5)
            Picasso.with(this).load(url).into(imageView4);
        else if (image_id == 6)
            Picasso.with(this).load(url).into(imageView5);
        else if (image_id == 7)
            Picasso.with(this).load(url).into(imageView6);
        else if (image_id == 8)
            Picasso.with(this).load(url).into(imageView7);
        else if (image_id == 9)
            Picasso.with(this).load(url).into(imageView8);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode== REQUSET_IMAGE_CHAPTER) {
            Bundle bundle = data.getExtras(); //A mapping from String keys to various Parcelable values.
            Bitmap bitmap = (Bitmap) bundle.get("data");
            if (image_id == 1)
                imageView.setImageBitmap(bitmap);
            else if (image_id == 2)
                imageView1.setImageBitmap(bitmap);
            else if (image_id == 3)
                imageView2.setImageBitmap(bitmap);
            else if (image_id == 4)
                imageView3.setImageBitmap(bitmap);
            else if (image_id == 5)
                imageView4.setImageBitmap(bitmap);
            else if (image_id == 6)
                imageView5.setImageBitmap(bitmap);
            else if (image_id == 7)
                imageView6.setImageBitmap(bitmap);
            else if (image_id == 8)
                imageView7.setImageBitmap(bitmap);
            else if (image_id == 9)
                imageView8.setImageBitmap(bitmap);

        }
        else if(requestCode==ACITON_PICK_Image){
            imageUi= data.getData();
            if (image_id == 1)
                imageView.setImageURI(imageUi);
            else if (image_id == 2)
                imageView1.setImageURI(imageUi);

            else if (image_id == 3)
                imageView2.setImageURI(imageUi);

            else if (image_id == 4)
                imageView3.setImageURI(imageUi);

            else if (image_id == 5)
                imageView4.setImageURI(imageUi);

            else if (image_id == 6)
                imageView5.setImageURI(imageUi);

            else if (image_id == 7)
                imageView6.setImageURI(imageUi);

            else if (image_id == 8)
                imageView7.setImageURI(imageUi);

            else if (image_id == 9)
                imageView8.setImageURI(imageUi);


        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imageView3:
                image_id = 1;
                GetDialog(view);
                break;
            case R.id.imageView4:
                image_id = 2;
                GetDialog(view);
                break;
            case R.id.imageView5:
                image_id = 3;
                GetDialog(view);
                break;
            case R.id.imageView6:
                image_id = 4;
                GetDialog(view);
                break;
            case R.id.imageView7:
                image_id = 5;
                GetDialog(view);
                break;
            case R.id.imageView8:
                image_id = 6;
                GetDialog(view);
                break;
            case R.id.imageView9:
                image_id = 7;
                GetDialog(view);
                break;
            case R.id.imageView10:
                image_id = 8;
                GetDialog(view);
                break;
            case R.id.imageView11:
                image_id = 9;
                GetDialog(view);
                break;


        }
    }


    private void fetchPhotoUrl() {
        //Genrate the services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        PhotoServices service = retrofit.create(PhotoServices.class);



        //Run the requst
        service.get("98016e3196ac4dcfb257801bf774bceb").enqueue(new Callback<List<PhotoCat>>() {
            @Override
            public void onResponse(Call<List<PhotoCat>> call, Response<List<PhotoCat>> response) {
                 url = response.body().get(0).getmUrl();
            }

            @Override
            public void onFailure(Call<List<PhotoCat>> call, Throwable t) {

            }
        });



    }
























}