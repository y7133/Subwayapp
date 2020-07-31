package com.example.myapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.apiPackage.StationDisabledToilet;
import com.example.myapplication.apiPackage.StationElevator;
import com.example.myapplication.apiPackage.StationGateInfo;
import com.example.myapplication.apiPackage.StationInfo;
import com.example.myapplication.apiPackage.StationToilet;
import com.example.myapplication.apiPackage.TrafficWeekInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.myapplication.R.id.map;

public class StationInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView stationNm;
    TextView stationNmE;
    TextView address1;
    TextView address2;

    StationInfo stationInfo;
    StationToilet stationToilet;
    StationDisabledToilet stationDisabledToilet;
    StationElevator stationElevator;
    StationGateInfo stationGateInfo;
    TrafficWeekInfo trafficWeekInfo;

    String railOprIsttCd;
    String lnCd;
    String stinCd;
    String stinNm;

    LinearLayout stationToiletLayout;
    LinearLayout StationDisabledToiletLayout;
    LinearLayout stationElevatorLayout;
    LinearLayout stationGateLayout;
    LinearLayout moveLayout1;
    LinearLayout moveLayout2;

    GoogleMap mMap;
    private Geocoder geocoder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_info);

        Intent intent=getIntent();
        railOprIsttCd= intent.getExtras().getString("railOprIsttCd");
        lnCd=intent.getExtras().getString("lnCd");
        stinCd=intent.getExtras().getString("stinCd");
        stinNm=intent.getExtras().getString("stinNm");

        stationNm=(TextView)findViewById(R.id.stationNm);
        stationNmE=(TextView)findViewById(R.id.stationNameE);
        address1=(TextView)findViewById(R.id.address1);
        address2=(TextView)findViewById(R.id.address2);


        stationToiletLayout= (LinearLayout) findViewById(R.id.stationToiletLayout);
        StationDisabledToiletLayout=(LinearLayout)findViewById(R.id.stationDisabledToiletLayout);
        stationElevatorLayout=(LinearLayout)findViewById(R.id.elevatorLayout);
        stationGateLayout=(LinearLayout)findViewById(R.id.stationGateLayout);

        moveLayout1=(LinearLayout)findViewById(R.id.moveLayout1);
        moveLayout2=(LinearLayout)findViewById(R.id.moveLayout2);

        stationNm.setText(stinNm);

        stationInfo=new StationInfo(railOprIsttCd,lnCd,stinCd,stinNm);
        stationInfo.setStationInfo(stationNmE,address1,address2);
        stationToilet=new StationToilet(railOprIsttCd,lnCd,stinCd);
        stationToilet.setStationToilet(stationToiletLayout,this);
        stationDisabledToilet=new StationDisabledToilet(railOprIsttCd,lnCd,stinCd);
        stationDisabledToilet.setStationDisabledToilet(StationDisabledToiletLayout,this);
        stationElevator=new StationElevator(railOprIsttCd,lnCd,stinCd);
        stationElevator.setStationElevator(stationElevatorLayout,this);
        stationGateInfo=new StationGateInfo(railOprIsttCd,lnCd,stinCd);
        stationGateInfo.setStationGateInfo(stationGateLayout,this);
        trafficWeekInfo=new TrafficWeekInfo(railOprIsttCd,lnCd,stinCd);
        trafficWeekInfo.setTrafficWeekInfo(this,moveLayout1,moveLayout2);

        setMapView();

    }

    public void setMapView(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });

        String str=stinNm+"역";
        List<Address> addressList = null;
        try {
            // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
            addressList = geocoder.getFromLocationName(
                    str, // 주소
                    10); // 최대 검색 결과 개수
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(addressList.get(0).toString());
        // 콤마를 기준으로 split
        String []splitStr = addressList.get(0).toString().split(",");
        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
        //System.out.println(address);

        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
        String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
        //System.out.println(latitude);
        //System.out.println(longitude);

        // 좌표(위도, 경도) 생성
        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        // 마커 생성
        MarkerOptions mOptions2 = new MarkerOptions();
        mOptions2.title("search result");
        mOptions2.snippet(address);
        mOptions2.position(point);
        // 마커 추가
        mMap.addMarker(mOptions2);
        // 해당 좌표로 화면 줌
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));

    }

}
