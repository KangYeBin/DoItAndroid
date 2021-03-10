package org.techtown.doitmission27;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    MarkerOptions myMarker = null, friendMarker1 = null, friendMarker2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Main", "onCreate 호출됨");

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                try {
                    map.setMyLocationEnabled(true);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });

        MapsInitializer.initialize(this);
        requestMyLocation();

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void requestMyLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        long minTime = 1000;
        float minDistance = 0;
        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);  //현재 위치 보여주기
                            Log.d("Main", "onLocationChanged GPS 호출됨");
                        }
                    });

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                showCurrentLocation(lastLocation);
            }

            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
                            addPictures(location);
                        }
                    }
            );

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void showCurrentLocation(Location location) {
        Log.d("Main", "showCurrentLocation 호출됨 myMarker : " + myMarker);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        try {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            if (myMarker == null) {
                Log.d("Main", "myMarker 생성됨");
                myMarker = new MarkerOptions();
                myMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
                myMarker.title("내 위치\n");
                myMarker.snippet("GPS로 확인한 위치");
                myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background));
                map.addMarker(myMarker);
            } else {
                myMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void addPictures(Location location) {
        int pictureResId = R.drawable.ic_launcher_foreground;
        String msg = "김성수\n" + "010-7788-1234";

        if (friendMarker1 == null) {
            Log.d("Main", "friendMarker1 생성됨");
            friendMarker1 = new MarkerOptions();
            friendMarker1.position(new LatLng(location.getLatitude()+3000, location.getLongitude()+3000));
            friendMarker1.title("친구 1\n");
            friendMarker1.snippet(msg);
            friendMarker1.icon(BitmapDescriptorFactory.fromResource(pictureResId));
            map.addMarker(friendMarker1);
        } else {
            friendMarker1.position(new LatLng(location.getLatitude()+3000, location.getLongitude()+3000));
        }

        pictureResId = R.drawable.ic_launcher_foreground;
        msg = "이현수\n" + "010-5512-4321";


        if (friendMarker2 == null) {
            Log.d("Main", "friendMarker2 생성됨");
            friendMarker2 = new MarkerOptions();
            friendMarker2.position(new LatLng(location.getLatitude()+2000, location.getLongitude()-1000));
            friendMarker2.title("친구 2\n");
            friendMarker2.snippet(msg);
            friendMarker2.icon(BitmapDescriptorFactory.fromResource(pictureResId));
            map.addMarker(friendMarker2);
        } else {
            friendMarker2.position(new LatLng(location.getLatitude()+2000, location.getLongitude()-1000));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this, "permissions denied : " + strings.length, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this, "permissions granted : " + strings.length, Toast.LENGTH_SHORT).show();
    }
}