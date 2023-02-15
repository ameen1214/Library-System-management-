package com.example.librarysystemmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddBookActivity extends AppCompatActivity {
    private CircularImageView bookiv ;
    private FloatingActionButton savebook ;
    private EditText nameBO , auteurBO , tokenBO ;
    private  static  final  int CAMERA_REQUEST_CODE = 100 ;
    private  static  final  int STORAGE_REQUEST_CODE = 101 ;
    private  static  final  int IMAGE_PICK_CAMERA_CODE = 102 ;
    private  static  final  int IMAGE_PICK_GALLERY_CODE = 103 ;
    private  String [] cameraPermissions;
    private  String [] storagePermissions;
    private Uri imageuri ;
    private  String nameB ,id, auteurB , tokenB  ;
    private MydbHelperBOOK mydbHelperBOOK ;
    private boolean isEditMode = false ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        bookiv=findViewById(R.id.bookpic);
        savebook=findViewById(R.id.savebook);
        nameBO=findViewById(R.id.namebook);
        auteurBO=findViewById(R.id.Auteur);
        tokenBO=findViewById(R.id.token);
        mydbHelperBOOK = new MydbHelperBOOK(this);

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditModee",false);
        if (isEditMode) {
            id = intent.getStringExtra("ID");
            tokenB = intent.getStringExtra("TOKEN");
            auteurB = intent.getStringExtra("AUTHOR");
            nameB = intent.getStringExtra("NAME");
            imageuri = Uri.parse(intent.getStringExtra("IMAGE"));

            nameBO.setText(nameB);
            auteurBO.setText(auteurB);
            tokenBO.setText(tokenB);

            if (imageuri.toString().equals("null")){
                bookiv.setImageResource(R.drawable.ic_boook);
            } else {
                bookiv.setImageURI(imageuri);
            }

        } else{

        }



        cameraPermissions= new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        bookiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });
        savebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });
    }
    private  void inputData() {
        nameB = ""+nameBO.getText().toString().trim();
        auteurB = ""+auteurBO.getText().toString().trim() ;
        tokenB = ""+tokenBO.getText().toString().trim();


        if (isEditMode){
        mydbHelperBOOK.UpdateBook(
                ""+id,
                ""+nameB,
                ""+imageuri,
                ""+auteurB,
                ""+tokenB);
        Toast.makeText(this,"Updated ...",Toast.LENGTH_SHORT).show();

    } else {




        long id = mydbHelperBOOK.insertBook(
                ""+nameB,
                ""+imageuri,
                ""+auteurB,
                ""+tokenB);

        Toast.makeText(this,"Book added with ID"+id,Toast.LENGTH_SHORT).show();
    } }




    private  boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE
        )==(PackageManager.PERMISSION_GRANTED);
        return  result;
    }
    private  boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA
        )==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE
        )==(PackageManager.PERMISSION_GRANTED);
        return  result && result1 ;
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE);
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);
    }
    private void imagePickDialog() {
        String [] options = {"Camera"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image from");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        PickFromCamera();
                    }
                }
                else if (which==1) {
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        PickFromGallery();
                    }
                }
            }


        });
        builder.create().show();
    }
    private void PickFromCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image Title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image Description");
        imageuri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
        startActivityForResult(cameraintent,IMAGE_PICK_CAMERA_CODE);
    }
    private void PickFromGallery(){
        Intent galleryintent = new Intent(Intent.ACTION_PICK);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length >0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        PickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "The Camera and the Storage are required",
                                Toast.LENGTH_SHORT).show();
                    }
                } }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length >0) {
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        PickFromGallery();
                    }
                    else {
                        Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show();
                    }

                } break;
            } } }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            bookiv.setImageURI(imageuri);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



}