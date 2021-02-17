package org.techtown.locationmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedro.library.AutoPermissions;

public class MainActivity extends AppCompatActivity {
    //play-services-maps 라이브러리 추가
    private GoogleMap map;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //지도 라이브러리 초기화
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override   //지도를 사용할 준비가 되면 자동 호출
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                //지도 관련 권한 부여, API KEY 생성
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });


        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime, minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);  //내 위치의 지도 보여주기
                        }
                    }
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void showCurrentLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng curPoint = new LatLng(latitude, longitude);   //경도, 위도
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        //해당 위치로 이동, zoom은 확대/축소

        showMyLocationMarker(curPoint); //자신의 위치에 마커 생성
    }

    public void showMyLocationMarker(LatLng curPoint) {
        MarkerOptions myLocationMarker = new MarkerOptions();
        myLocationMarker.position(curPoint);
        myLocationMarker.title("내 위치");
        myLocationMarker.snippet("GPS로 확인한 위치");
        myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));

        map.addMarker(myLocationMarker);

    }
}