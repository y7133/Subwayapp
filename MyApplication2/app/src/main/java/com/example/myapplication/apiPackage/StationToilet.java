package com.example.myapplication.apiPackage;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

//역사별 화장실 현황
public class StationToilet {
    private static final String SERVER_URL = "http://openapi.kric.go.kr/openapi/";
    public static final String CLASSIFICATION = "convenientInfo";
    public static final String INFORMATION = "stationToilet";
    public static final String SERVICE_KEY
            = "$2a$10$z2J0KoVJvhjPavvleYId6eUFObq6DU62bZQ8/tVpuwFRjjaICLdUS";
    public static final String FORMAT = "json"; //데이터 포맷

    public String railOprIsttCd;  //철도 운영기관 코드
    public String lnCd;           //선코드
    public String stinCd;         //역코드

    public ArrayList<String> diapExchNum = new ArrayList<String>();  //기저귀교환대개수
    public ArrayList<String> dtlLoc = new ArrayList<String>();       //상세위치
    public ArrayList<String> gateInotDvNm = new ArrayList<String>(); //게이트 내외 구분
    public ArrayList<String> mlFmlDvNm = new ArrayList<String>();    //남녀구분
    public ArrayList<Integer> stinFlor = new ArrayList<Integer>();   //역층

    StationToilet(String railOprIsttCd, String lnCd, String stinCd) {
        this.railOprIsttCd = railOprIsttCd;
        this.lnCd = lnCd;
        this.stinCd = stinCd;
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setStationToilet() {
        railOprIsttCd = "S1";
        lnCd = "3";
        stinCd = "324";

        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("StationToilet","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        diapExchNum.add(jsonObject.isNull("diapExchNum")?"null":jsonObject.getString("diapExchNum"));
                        dtlLoc.add( jsonObject.getString("dtlLoc"));
                        gateInotDvNm.add( jsonObject.getString("gateInotDvNm"));
                        mlFmlDvNm.add( jsonObject.getString("mlFmlDvNm"));
                        stinFlor.add( jsonObject.getInt("stinFlor"));
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
