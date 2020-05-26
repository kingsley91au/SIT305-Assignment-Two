package com.example.assignment_two;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private ImageView ProfileImage;
    private ImageView ProfileAdd;
    private Button StartBtn;
    private Button NewBtn;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private EditText InputName;
    public static DataSave NProfile = new DataSave();
    public static final String NameText = "Name";
    public static final String ImageUri = "imageUri";
    public String name ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ProfileImage = findViewById( R.id.profile_image );
        ProfileAdd = findViewById( R.id.profile_ID );
        StartBtn = findViewById( R.id.signUp_direct );
        NewBtn = findViewById(R.id.NewBtn);
        InputName = findViewById( R.id.inputName );

        //Load the stored name and image
        LoadName();
        LoadImage();

        // Check whether the SharedPreferences stored name data, if so, show button, if not, set button as transparent
        String hasName = InputName.getText().toString().trim();
        if ( !hasName.isEmpty()) {
            NewBtn.setBackgroundResource(R.drawable.button_radius);
            NewBtn.setText("New User");
            NewBtn.setClickable(true);
        }

        //Set up the dialog
        final AlertDialog.Builder dialog = new AlertDialog.Builder( MainActivity.this );
        dialog.setTitle( "Name Missing:" );
        dialog.setMessage( "Please put your name!" );
        dialog.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        } );
        final AlertDialog alertDialog = dialog.create();


        //Set up click on the profile image
        ProfileImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        } );
        ProfileAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        } );

        //Set up the click button, if is empty, load the dialog, if not, save the name and image, then go to the list_view activity
        StartBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = InputName.getText().toString().toLowerCase().trim();
                if(Name.isEmpty()){
                    alertDialog.show();
                }
                else{
                    NProfile.name = Name;
                    saveName();
                    saveImage();
                    Intent intent = new Intent( getApplicationContext(), list_view.class );
                    startActivity( intent );
                }
            }
        } );
        //Clear all the SharePreferences data
        NewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearData();
            }
        });
    }



    private void openGallery() {
        //Open the Albums
        Intent gallery = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI );
        //pick image mode
        startActivityForResult( gallery, PICK_IMAGE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        //if user clicked a image and clicked ok, then the imageView will show the image that just selected
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            ProfileImage.setImageURI( imageUri );
            NProfile.imageUri = imageUri;
            //Covert the URI to BITMAP
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                NProfile.bitmap = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveName() {
        //Use SharedPreferences function
        SharedPreferences NameSharedPreferences = getSharedPreferences( NameText, MODE_PRIVATE );
        SharedPreferences.Editor NameEditor = NameSharedPreferences.edit();
        //Set up SharedPreferences title and the content
        NameEditor.putString("Name", InputName.getText().toString().trim());
        NameEditor.apply();
    }

    public void saveImage(){
        //Load the bitmap image that we need to save
        Bitmap bitmap = NProfile.bitmap;
        //Create SharedPreferences function
        SharedPreferences ImageSharedPreferences=getSharedPreferences(ImageUri, MODE_PRIVATE);
        SharedPreferences.Editor editor=ImageSharedPreferences.edit();
        //Use ByteArrayOutputStream function
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        //Save the image as Base64 format
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        String headPicBase64=new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT));
        editor.putString(ImageUri,headPicBase64);
        editor.commit();
    }
    public void LoadName() {
        //Use SharedPreferences function
        SharedPreferences NameSharedPreferences = getSharedPreferences(NameText, MODE_PRIVATE);
        name = NameSharedPreferences.getString("Name", "");
        InputName.setText( name );
        //Check whether the name is empty, if so set the button text: start, if not: Continue using "+ name
        if(InputName.getText().toString().trim().isEmpty()){
            StartBtn.setText( "START" );
        }
        else{
            StartBtn.setText( "Continue using "+ name );
        }
    }
    public void LoadImage(){
        SharedPreferences ImageSharedPreferences= getSharedPreferences(ImageUri, MODE_PRIVATE);
        // read the string Bitmap
        String image = ImageSharedPreferences.getString(ImageUri, "");
        // Use Base64 convert to ByteArrayInputStream
        byte[] byteArray = Base64.decode(image,Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        // use ByteArrayInputStream generate bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        // set the imageView
        ProfileImage.setImageBitmap(bitmap);
        NProfile.bitmap = bitmap;
    }
    public void ClearData(){
        //clear the Image from SharedPreferences
        this.getSharedPreferences(ImageUri, MODE_PRIVATE).edit().clear().commit();
        //clear the name from SharedPreferences
        this.getSharedPreferences(NameText, MODE_PRIVATE).edit().clear().commit();
        //clear the recyclerview from SharedPreferences
        this.getSharedPreferences( "shared preferences", MODE_PRIVATE).edit().clear().commit();
        //Close the app
        System.exit(0);

    }

}

