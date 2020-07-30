package com.example.myapplication.apiPackage;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

//출입구 승강장 이동경로
public class StationMovement {
    private static final String SERVER_URL = "http://openapi.kric.go.kr/openapi/";
    public static final String CLASSIFICATION = "vulnerableUserInfo";
    public static final String INFORMATION = "stationMovement";
    public static final String SERVICE_KEY
            = "$2a$10$1OenS/80qF5SDcJhHeMjy.QlrKmKwncAUhZ5l1SCOoFzuauU6J.xm";
    public static final String FORMAT = "json"; //데이터 포맷

    public String railOprIsttCd;  //철도 운영기관 코드
    public String lnCd;           //선코드
    public String stinCd;         //역코드
    public String nextStinCd;     //이후 역 코드
    public ArrayList<String> edMovePath = new ArrayList<String>();  //도착지
    //public ArrayList<String> elvtSttCd = new ArrayList<String>(); //승강기상태코드
    //public ArrayList<String> elvtTpCd = new ArrayList<String>();  //승강기유형코드
    public ArrayList<Integer> exitMvTpOrdr = new ArrayList<Integer>();  //순서
    public ArrayList<String> imgPath = new ArrayList<String>();     //이미지 경로
    public ArrayList<String> mvContDtl = new ArrayList<String>();   //상세 이동내용
    public ArrayList<Integer> mvPathMgNo = new ArrayList<Integer>();  //이동경로 관리번호
    public ArrayList<String> stMovePath = new ArrayList<String>();  //시작지

    StationMovement(String railOprIsttCd, String lnCd, String stinCd,String nextStinCd) {
        this.railOprIsttCd = railOprIsttCd;
        this.lnCd = lnCd;
        this.stinCd = stinCd;
        this.nextStinCd=nextStinCd;
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setStationMovement() {
        railOprIsttCd = "S1";
        lnCd = "3";
        stinCd = "322";
        nextStinCd="323";

        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd+"&nextStinCd="+nextStinCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("StationMovement","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        edMovePath.add((String) jsonObject.get("edMovePath"));
                        //elvtSttCd.add((String) jsonObject.get("elvtSttCd"));
                        //elvtTpCd.add((String) jsonObject.get("elvtTpCd"));
                        mvContDtl.add((String) jsonObject.get("mvContDtl"));
                        imgPath.add((String) jsonObject.get("imgPath"));
                        mvContDtl.add((String) jsonObject.get("mvContDtl"));
                        mvPathMgNo.add((Integer) jsonObject.get("mvPathMgNo"));
                        stMovePath.add((String) jsonObject.get("stMovePath"));
                        Log.e("hi",stMovePath.get(i));
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
