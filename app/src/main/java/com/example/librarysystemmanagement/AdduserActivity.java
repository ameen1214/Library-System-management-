package com.example.librarysystemmanagement;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AdduserActivity extends AppCompatActivity {
private  CircularImageView profiliv ;
private FloatingActionButton savebtn ;
private EditText nameET , phoneEt , emailET , datebirth , bookstoken;
private  static  final  int CAMERA_REQUEST_CODE = 100 ;
    private  static  final  int STORAGE_REQUEST_CODE = 101 ;
    private  static  final  int IMAGE_PICK_CAMERA_CODE = 102 ;
    private  static  final  int IMAGE_PICK_GALLERY_CODE = 103 ;
    private  String [] cameraPermissions;
    private  String [] storagePermissions;
    private Uri imageuri ;
    private  String name , phone , email , booktoken , dateof , id;
    private MyDbHelper dbHelper ;
    private boolean isEditMode = false ;
private ActionBar actionBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
    /*  actionBar.setTitle("Add user");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();*/

        datebirth=findViewById(R.id.datebirth);
        emailET=findViewById(R.id.email);
        phoneEt= findViewById(R.id.phone);
        nameET= findViewById(R.id.nameuser);
        profiliv = findViewById(R.id.profilpic) ;
        bookstoken=findViewById(R.id.bookToken);
        savebtn = findViewById(R.id.savebtn);


        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode",false);
    if (isEditMode) {
        id = intent.getStringExtra("ID");
        dateof = intent.getStringExtra("DATEOFBIRTH");
        email = intent.getStringExtra("EMAIL");
        phone = intent.getStringExtra("PHONE");
        name = intent.getStringExtra("NAME");
        imageuri = Uri.parse(intent.getStringExtra("IMAGE"));
        booktoken = intent.getStringExtra("TOKEN");

        nameET.setText(name);
        phoneEt.setText(phone);
        emailET.setText(email);
        bookstoken.setText(booktoken);
        datebirth.setText(dateof);

        if (imageuri.toString().equals("null")){
            profiliv.setImageResource(R.drawable.ic_p);
        } else {
            profiliv.setImageURI(imageuri);
        }

    } else {

    }



         dbHelper = new MyDbHelper(this);
        cameraPermissions= new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        profiliv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              imagePickDialog();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             inputData();
            }
        });
    }

    private  void inputData() {
        name = ""+nameET.getText().toString().trim();
        phone = ""+phoneEt.getText().toString().trim() ;
        email = ""+emailET.getText().toString().trim();
        booktoken = ""+bookstoken.getText().toString().trim();
        dateof= ""+datebirth.getText().toString().trim();

       if (isEditMode){
           dbHelper.UpdateUser(
                   ""+id,
                   ""+name,
                   ""+imageuri,
                   ""+phone,
                   ""+booktoken,
                   ""+dateof,
                   ""+email);
           Toast.makeText(this,"Updated ...",Toast.LENGTH_SHORT).show();

       } else {
           long id = dbHelper.insertUser(
                   ""+name,
                   ""+imageuri,
                   ""+phone,
                   ""+booktoken,
                   ""+dateof,
                   ""+email);
           Toast.makeText(this,"User added with ID"+id,Toast.LENGTH_SHORT).show();
       }



    }


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
            profiliv.setImageURI(imageuri);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}