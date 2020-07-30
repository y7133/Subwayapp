package com.example.myapplication.apiPackage;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

//역사별 엘리베이터 현황
public class StationElevator {
    private static final String SERVER_URL = "http://openapi.kric.go.kr/openapi/";
    public static final String CLASSIFICATION = "convenientInfo";
    public static final String INFORMATION = "stationElevator";
    public static final String SERVICE_KEY
            = "$2a$10$z2J0KoVJvhjPavvleYId6eUFObq6DU62bZQ8/tVpuwFRjjaICLdUS";
    public static final String FORMAT = "json"; //데이터 포맷

    public String railOprIsttCd;  //철도 운영기관 코드
    public String lnCd;           //선코드
    public String stinCd;         //역코드

    public ArrayList<String> dtlLoc = new ArrayList<String>();
    //public ArrayList<String > exitNo = new ArrayList<String>();
    public ArrayList<String> grndDvNmFr = new ArrayList<String>();
    public ArrayList<String> grndDvNmTo = new ArrayList<String>();
    //public ArrayList<Integer> rglnPsno = new ArrayList<Integer>();
    //public ArrayList<Integer> rglnWgt = new ArrayList<Integer>();
    //public ArrayList<Integer> runStinFlorFr = new ArrayList<Integer>();
    //public ArrayList<Integer> runStinFlorTo = new ArrayList<Integer>();

    StationElevator(String railOprIsttCd, String lnCd, String stinCd) {
        this.railOprIsttCd = railOprIsttCd;
        this.lnCd = lnCd;
        this.stinCd = stinCd;
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setStationElevator() {
        railOprIsttCd = "S1";
        lnCd = "3";
        stinCd = "322";

        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("StationElevator","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        dtlLoc.add((String) jsonObject.get("dtlLoc"));
                        //exitNo.add((String) jsonObject.get("exitNo"));
                        grndDvNmFr.add((String) jsonObject.get("grndDvNmFr"));
                        grndDvNmTo.add((String) jsonObject.get("grndDvNmTo"));
                        //rglnPsno.add((Integer) jsonObject.get("rglnPsno"));
                        //rglnWgt.add((Integer) jsonObject.get("rglnWgt"));
                        //runStinFlorFr.add((Integer) jsonObject.get("runStinFlorFr"));
                        //runStinFlorTo.add((Integer) jsonObject.get("runStinFlorTo"));
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
