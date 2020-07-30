package com.example.myapplication.apiPackage;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

//교통약자 역사 내 엘리베이터 이동동선
public class TrafficWeekInfo {
    private static final String SERVER_URL = "http://openapi.kric.go.kr/openapi/";
    public static final String CLASSIFICATION = "trafficWeekInfo";
    public static final String INFORMATION = "stinElevatorMovement";
    public static final String SERVICE_KEY
            = "$2a$10$1OenS/80qF5SDcJhHeMjy.QlrKmKwncAUhZ5l1SCOoFzuauU6J.xm";
    public static final String FORMAT = "json"; //데이터 포맷

    public String railOprIsttCd;  //철도 운영기관 코드
    public String lnCd;           //선코드
    public String stinCd;         //역코드
    public ArrayList<Integer> mvPathMgNo = new ArrayList<Integer>();       //이동경로 관리 번호
    public ArrayList<String> mvPathDvCd = new ArrayList<String>();       //이동경로 구분 코드
    public ArrayList<String> mvPathDvNm = new ArrayList<String>();       //이동경로 구분
    public ArrayList<Integer> mvTpOrdr = new ArrayList<Integer>();         //이동 유형 순서
    //public ArrayList<String> mvDst = new ArrayList<String>();            //이동거리
    public ArrayList<String> mvContDtl = new ArrayList<String>();        //상세 이동내용

    TrafficWeekInfo(String railOprIsttCd, String lnCd, String stinCd) {
        this.railOprIsttCd = railOprIsttCd;
        this.lnCd = lnCd;
        this.stinCd = stinCd;
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setTrafficWeekInfo() {
        railOprIsttCd = "S1";
        lnCd = "3";
        stinCd = "322";

        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("TrafficWeekInfo","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mvPathMgNo.add((Integer) jsonObject.get("mvPathMgNo"));
                        mvPathDvCd.add((String) jsonObject.get("mvPathDvCd"));
                        mvPathDvNm.add((String) jsonObject.get("mvPathDvNm"));
                        mvTpOrdr.add((Integer) jsonObject.get("mvTpOrdr"));
                        //mvDst.add((String) jsonObject.get("mvDst"));
                        mvContDtl.add((String) jsonObject.get("mvContDtl"));
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