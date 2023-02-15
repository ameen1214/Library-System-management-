package com.example.librarysystemmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ActivityMaps extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    List<Address> listgeoCoder;
    private static final int LOCATION_PERMISSION_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(islocationPermissionGranted()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
            mapFragment.getMapAsync(this);

            try {
                listgeoCoder = new Geocoder(this).getFromLocationName(
                        "821 Av. Sainte-Croix, Saint-Laurent, QC H4L 3X9, Canada", 1
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
            double logtude = listgeoCoder.get(0).getLongitude();
            double latitude = listgeoCoder.get(0).getLatitude();

            Log.i("GOOGLE_MAP_TAG", "Address has longitude :   " + String.valueOf(logtude) +
                    " Latitude :  " + String.valueOf(latitude));


        } else {
            requestLocationPermission();
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
   mMap = googleMap;
   LatLng sydney = new LatLng(-34,151);
   mMap.addMarker(new MarkerOptions().position(sydney).title(""));
   mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED){
           mMap.setMyLocationEnabled(true); }
    }


    private boolean islocationPermissionGranted (){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            return false;
        }
    }
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_CODE);
    }
}