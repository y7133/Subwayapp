package com.example.myapplication.apiPackage;

import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class StationTimetable {
    private static final String SERVER_URL = "http://openapi.kric.go.kr/openapi/";
    public static final String CLASSIFICATION = "convenientInfo";
    public static final String INFORMATION = "stationTimetable";
    public static final String SERVICE_KEY
            = "$2a$10$z2J0KoVJvhjPavvleYId6eUFObq6DU62bZQ8/tVpuwFRjjaICLdUS";
    public static final String FORMAT = "json"; //데이터 포맷

    public String railOprIsttCd;  //철도 운영기관 코드
    public String lnCd;           //선코드
    public String stinCd;         //역코드
    public String dayCd;          //요일코드(8:평일 7:토요일 9:휴일)
    public String dayNm;          //요일명

    public ArrayList<Integer> arvTm = new ArrayList<Integer >();   //도착시간
    public ArrayList<Integer> dptTm = new ArrayList<Integer>();   //출발시간
    public ArrayList<String> orgStinCd = new ArrayList<String>(); //시발 역 코드
    public ArrayList<String> tmnStinCd=new ArrayList<String>(); //종착역코드
    public ArrayList<String > trnNo=new ArrayList<String>();    //열차번호

    public StationTimetable(String railOprIsttCd, String lnCd, String stinCd, String dayCd) {
        this.railOprIsttCd = railOprIsttCd;
        this.lnCd = lnCd;
        this.stinCd = stinCd;
        this.dayCd=dayCd;

        if(dayCd=="8")
            dayNm="평일";
        else if(dayCd=="7")
            dayNm="토요일";
        else if(dayCd=="9")
            dayNm="휴일";
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setStationTimetable() {
        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd+"&dayCd="+dayCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("StationTimetable","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        arvTm.add(jsonObject.getInt("arvTm"));
                        dptTm.add(jsonObject.getInt("dptTm"));
                        orgStinCd.add(jsonObject.getString("orgStinCd"));
                        tmnStinCd.add(jsonObject.getString("tmnStinCd"));
                        trnNo.add(jsonObject.getString("trnNo"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public boolean getUseSynchronousMode(){
                return false;
            }
        });

    }

    public void setStationTimetable(TextView textView) {
        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd+"&dayCd="+dayCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("StationTimetable","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        arvTm.add(jsonObject.getInt("arvTm"));
                        dptTm.add(jsonObject.getInt("dptTm"));
                        orgStinCd.add(jsonObject.getString("orgStinCd"));
                        tmnStinCd.add(jsonObject.getString("tmnStinCd"));
                        trnNo.add(jsonObject.getString("trnNo"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public boolean getUseSynchronousMode(){
                return false;
            }
        });

    }

}
