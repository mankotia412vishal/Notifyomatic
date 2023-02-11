package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class DashBoard extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    boolean isPermissionGranted;
    MapView mapView;
    BottomNavigationView nav;
    GoogleMap mGoogleMap;
    FloatingActionButton fab;
    private int GPS_REQUEST_CODE = 9001;
    private FusedLocationProviderClient mLocationClient;
    EditText locSearch;
    ImageView searchIcon;
    private GoogleMap mMap;
//FloatingActionButton b;

    //Logout Button ka haii sab


//        TextView txtFullName, txtEmail, txtMobile;
//        String rtvFullName, rtvEmail, rtvMobile, loggedEmail;
//        private FirebaseFirestore db;
//        private FirebaseAuth mAuth;
//        private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
//        nav = findViewById(R.id.nav);
        getSupportActionBar().hide();// It hides Tittle bar
//Logout button ka haii sab

//        db = FirebaseFirestore.getInstance();



//        mAuth = FirebaseAuth.getInstance();
//        btnLogout = findViewById(R.id.btnLogout);
//
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mAuth.signOut();
//                Intent intent = new Intent(MainActivity.this, Login.class);
//                startActivity(intent);
//                finish();
//                Toast.makeText(MainActivity.this, "Logout Successful !", Toast.LENGTH_SHORT).show();
//
//            }
//        });


        mapView = findViewById(R.id.map_view);
        fab = findViewById(R.id.fab);
        locSearch = findViewById(R.id.et_search);
        searchIcon = findViewById(R.id.search_icon);
//        b=findViewById(R.id.floatingActionButton);

        checkMyPermission();


        initMap();



//        if (isPermissionGranted){
//            mapView.getMapAsync(this);
//            mapView.onCreate(savedInstanceState);

        mLocationClient = new FusedLocationProviderClient(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrLoc();

            }
        });


        searchIcon.setOnClickListener(this::geoLocate);
    }

    private void geoLocate(View view) {
        String locationName = locSearch.getText().toString();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
            if (addressList.size() > 0) {
                Address address = addressList.get(0);

                getoLocation(address.getLatitude(), address.getLongitude());

                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude())));


                Toast.makeText(this, address.getLocality(), Toast.LENGTH_SHORT).show();

            }

        } catch (IOException e) {

        }
    }

    private void initMap() {
        if (isPermissionGranted) {
            if (isGPSenable()) {
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                supportMapFragment.getMapAsync(this);


            }
        }

    }

    private boolean isGPSenable() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnable) {
            return true;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS Permission");
            builder.setTitle("GPS is Required for this  app to work . Please enable GPS");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, GPS_REQUEST_CODE);


            });
            builder.setCancelable(false);
            AlertDialog alertDialog = builder
                    .show();
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    private void getCurrLoc() {

        mLocationClient.getLastLocation().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Location location = task.getResult();
                getoLocation(location.getLatitude(), location.getLongitude());

            }
        });
    }

    private void getoLocation(double latitude, double longitude) {
        LatLng LaLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LaLng, 20);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(DashBoard.this, "Vishal", Toast.LENGTH_SHORT).show();
//                mGoogleMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE);
//            }
//        });

    }

    private void checkMyPermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(DashBoard.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                isPermissionGranted = true;

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);


                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mMap=googleMap;


//        LatLng bonn = new LatLng(18.4636219, 73.8682037);
//        mMap.addMarker(new MarkerOptions().position(bonn).title("Marker in Vishwakarma Institue of Technology "));
//        float zoomLevel = 15.0f; //This goes up to 21
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bonn, zoomLevel
//        ));

//        enableUserLocation();

//        mMap.setOnMapLongClickListener(true);
        LatLng sydney = new LatLng(18.4636219, 73.8682037);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Vishwakarma Institue of Technology "));
        float zoomLevel = 21.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        mGoogleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_REQUEST_CODE) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (providerEnable) {
                Toast.makeText(this, "GPS is enable ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS is not enabled", Toast.LENGTH_SHORT).show();
            }

        }

//        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//
//                switch (item.getItemId()){
//
//                    /*
////                    case R.id.profile:
////                        Toast.makeText(DashBoard.this, "Profile", Toast.LENGTH_SHORT).show();
////
////                        startActivity(new Intent(getApplicationContext(),UserProfile.class));
////                        overridePendingTransition(0,0);
////
//////                        break;
////                    case R.id.home:
////                        Toast.makeText(DashBoard.this, "Home", Toast.LENGTH_SHORT).show();
//////                        skip.setOnClickListener(new View.OnClickListener() {
//////                            @Override
//////                            public void onClick(View view) {
//////                        mediaPlayer.pause();
////                        startActivity(new Intent(getApplicationContext(),DashBoard.class));
////                        overridePendingTransition(0,0);
////                        finish();
//////                                mediaPlayer.pause();
//////
//////                                finish();
//////                            }
//////                        });
////
////                        break;
//*/
//                    case R.id.manage:
//                        Toast.makeText(DashBoard.this, "Manage", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(), User.class));
//                        overridePendingTransition(0,0);
//                        finish();
//                        break;
//
//                    case R.id.explore:
//                        Toast.makeText(DashBoard.this, "Explore", Toast.LENGTH_SHORT).show();
//                        break;
//
//
//                    default:
//
//                }
//                return true;
//            }
//        });

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(DashBoard.this, Categories.class));
        finish();

    }
}