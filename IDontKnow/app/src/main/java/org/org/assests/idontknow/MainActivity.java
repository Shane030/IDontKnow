package org.org.assests.idontknow;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    Button btn, btn2,btn4, btn_upload,btn_set_val,submit_but,under_but,under_but2;
    ImageView imageView,imageView2;
    Integer quantity, condition, price, serialnumber, room;
    String descrip,building,manufacturer,database;
    EditText quant, cond, pri, serial,roo,desc,build,manu;
    byte[] byteArray, byteHold;
    Bitmap yourSelectedImage;
    SearchView search1;
    WebView webview;
    String url = "https://i.pinimg.com/originals/35/71/12/3571129ed72f5c0f0388594544f9c79d.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        loadImageFromUrl(url);

        database = "";

        webview = (WebView)findViewById(R.id.webview);
        webview.loadUrl(database);


        quant = (EditText)findViewById(R.id.quantity);
        cond = (EditText)findViewById(R.id.condition);
        pri = (EditText)findViewById(R.id.price);
        serial = (EditText)findViewById(R.id.serialnumber);
        roo = (EditText)findViewById(R.id.room);
        desc = (EditText)findViewById(R.id.description);
        manu = (EditText)findViewById(R.id.manufacturer);
        build = (EditText)findViewById(R.id.building);

        submit_but = (Button)findViewById(R.id.submit_but);
        submit_but.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.valueOf(quant.getText().toString());
                condition = Integer.valueOf(cond.getText().toString());
                price = Integer.valueOf(pri.getText().toString());
                serialnumber = Integer.valueOf(serial.getText().toString());
                room = Integer.valueOf(roo.getText().toString());
                descrip = desc.getText().toString();
                building = build.getText().toString();
                manufacturer = manu.getText().toString();
                if(descrip.length()>256 ||building.length()>128||manufacturer.length()>128){
                    setContentView(R.layout.too_long);
                }else if(!quantity.equals(null)||!price.equals(null)||!room.equals(null)||!serialnumber.equals(null)||!condition.equals(null)||!building.equals(null)
                        ||!manufacturer.equals(null)||!descrip.equals(null)){
                    setContentView(R.layout.null_set);
                }
                else{
                    //upload ALLLLLLLL VARIABLES (byteArray,quantity,condition,price,
                    // serialnumber,room,descrip,building,manufacturer)
                    setContentView(R.layout.activity_main);
                }
            }
        });

        under_but = (Button)findViewById(R.id.under_but);
        under_but.setOnClickListener(new OnClickListener(){
           public void onClick(View v){
               setContentView(R.layout.set_database);
           }
        });

        under_but2 = (Button)findViewById(R.id.under_but2);
        under_but2.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                setContentView(R.layout.set_database);
            }
        });

        btn=(Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent i=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(i);
            }
        });
        btn2=(Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                setContentView(R.layout.search_database);
            }
        });
        btn4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.upload_image);
            }
        });
        btn_upload=(Button)findViewById(R.id.button_upload);
        btn_upload.setOnClickListener(btnChoosePhotoPressed);

        btn_set_val=(Button)findViewById(R.id.button_setVal);
        btn_set_val.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.set_vals);
            }
        });

        search1 = (SearchView)findViewById(R.id.searchView);
        search1.getQuery().toString();
    }

    public OnClickListener btnChoosePhotoPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            final int ACTIVITY_SELECT_IMAGE = 1234;
            startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
        }
    };
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    imageView2 = findViewById(R.id.imageView2);
                    imageView2.setImageBitmap(yourSelectedImage);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();

                    //yourSelectedImage to Photo VARBINARY(MAX) NOT NULL

                }
        }
    };
    private void loadImageFromUrl(String url){
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess(){

                    }
                    @Override
                    public void onError(){

                    }
                });
    }

}
