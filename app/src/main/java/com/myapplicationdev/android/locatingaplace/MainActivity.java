package com.myapplicationdev.android.locatingaplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    Button btn1, btn2, btn3;
    private GoogleMap map;
    ArrayList<String> al;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        al = new ArrayList<String>();
        al.add(new String("North"));
        al.add(new String("West"));
        al.add(new String("East"));

        aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,al);
        spinner.setAdapter(aa);



        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        {

                            Toast.makeText(MainActivity.this, marker.getTitle() , Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                });

                UiSettings ui = map.getUiSettings();

                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }



                LatLng poi_sg = new LatLng(1.3521, 103.8198);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_sg,
                        11));

                LatLng poi_Jurong = new LatLng(1.3400, 103.7041);
                Marker rp = map.addMarker(new
                        MarkerOptions()
                        .position(poi_Jurong)
                        .title("HQ-West")
                        .snippet("Blk 555 Jurong West st 42")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


                LatLng poi_north = new LatLng(1.446392, 103.780655);
                Marker north = map.addMarker(new
                        MarkerOptions()
                        .position(poi_north)
                        .title("HQ-North")
                        .snippet("Blk 333 Admiralty ave 3")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                LatLng poi_east = new LatLng(1.3499986, 103.9499962);
                Marker central = map.addMarker(new
                        MarkerOptions()
                        .position(poi_east)
                        .title("HQ-East")
                        .snippet("Blk 408 Tampines Ave 2")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


            }
        });

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LatLng poi_north = new LatLng(1.446392, 103.780655);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north,
                        15));
                }

        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng poi_Jurong = new LatLng(1.3400, 103.7041);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Jurong,
                        15));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng poi_central = new LatLng(1.3499986, 103.9499962);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central,
                        15));
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String getPlace = spinner.getItemAtPosition(position).toString();
                if(getPlace.equals("North")&&map!=null){
                    LatLng poi_north = new LatLng(1.446392, 103.780655);

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north,
                            15));
                }else if(getPlace.equals("West")&&map!=null){
                    LatLng poi_Jurong = new LatLng(1.3400, 103.7041);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Jurong,
                            15));
                }else if(getPlace.equals("East")&&map!=null){
                    LatLng poi_central = new LatLng(1.3499986, 103.9499962);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central,
                            15));
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
