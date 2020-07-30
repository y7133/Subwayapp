package com.example.myapplication.apiPackage;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SubwayRouteInfo {
    private static final String SERVER_URL = "http://openapi.kric.go.kr/openapi/";
    public static final String CLASSIFICATION = "trainUseInfo";
    public static final String INFORMATION = "subwayRouteInfo";
    public static final String SERVICE_KEY
            = "$2a$10$z2J0KoVJvhjPavvleYId6eUFObq6DU62bZQ8/tVpuwFRjjaICLdUS";
    public static final String FORMAT = "json"; //데이터 포맷

    public String lnCd;            //선코드
    public String mreaWideCd;      //권역코드
    //public String railOprIsttCd;   //철도운영기관코드

    public ArrayList<String> routNm = new ArrayList<String>();          //노선명
    public ArrayList<String> stinCd = new ArrayList<String>();          //역코드
    public ArrayList<Integer> stinConsOrdr = new ArrayList<Integer>();  //역구성순서
    public ArrayList<String> stinNm = new ArrayList<String>();          //역명

    SubwayRouteInfo(String lnCd, String mreaWideCd) {
        this.lnCd = lnCd;
        this.mreaWideCd=mreaWideCd;
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setSubwayRouteInfo() {

        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&lnCd=" + lnCd + "&mreaWideCd=" + mreaWideCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("SubwayRouteInfo","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        routNm.add(jsonObject.getString("routNm"));
                        stinCd.add( jsonObject.getString("stinCd"));
                        stinNm.add( jsonObject.getString("stinNm"));
                        stinConsOrdr.add( jsonObject.getInt("stinConsOrdr"));
                        Log.e(routNm.get(i),stinNm.get(i));
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
