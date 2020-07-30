package com.example.myapplication.apiPackage;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

//역사별 출구정보
public class StationGateInfo {
    private static final String SERVER_URL = "http://openapi.kric.go.kr/openapi/";
    public static final String CLASSIFICATION = "convenientInfo";
    public static final String INFORMATION = "stationGateInfo";
    public static final String SERVICE_KEY
            = "$2a$10$z2J0KoVJvhjPavvleYId6eUFObq6DU62bZQ8/tVpuwFRjjaICLdUS";
    public static final String FORMAT = "json"; //데이터 포맷

    public String railOprIsttCd;  //철도 운영기관 코드
    public String lnCd;           //선코드
    public String stinCd;         //역코드

    public ArrayList<String> exitNo = new ArrayList<String>();   //출구 번호
    public ArrayList<String> impFaclNm = new ArrayList<String>(); //주요시설명

    public StationGateInfo(String railOprIsttCd, String lnCd, String stinCd) {
        this.railOprIsttCd = railOprIsttCd;
        this.lnCd = lnCd;
        this.stinCd = stinCd;
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setStationGateInfo() {
        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("StationGateInfo","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        exitNo.add((String) jsonObject.get("exitNo"));
                        impFaclNm.add((String) jsonObject.get("impFaclNm"));
                        Log.e(exitNo.get(i),impFaclNm.get(i));
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

    public void setStationGateInfo(final LinearLayout layout, final Activity activity) {
        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("StationGateInfo","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LinearLayout linearLayout=createLayout(activity);

                        TextView eNum=createTextView(activity,2);
                        TextView impNm=createTextView(activity, (float) 1.5);
                        TextView dst=createTextView(activity,2);
                        TextView tel=createTextView(activity, (float) 1.5);

                        eNum.setText(jsonObject.isNull("exitNo")?"정보 없음":jsonObject.getString("exitNo"));
                        impNm.setText( jsonObject.isNull("impFaclNm")?"정보 없음":jsonObject.getString("impFaclNm"));
                        dst.setText( jsonObject.isNull("dst")?"정보 없음":jsonObject.getString("dst"));
                        tel.setText( jsonObject.isNull("telNo")?"정보 없음":jsonObject.getString("telNo"));

                        linearLayout.addView(eNum);
                        linearLayout.addView(impNm);
                        linearLayout.addView(dst);
                        linearLayout.addView(tel);

                        layout.addView(linearLayout);
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

    private LinearLayout createLayout(Activity activity){
        LinearLayout linearLayout=new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(0,10,0,10);

        return linearLayout;
    }
    private TextView createTextView(Activity activity, float weight){
        TextView textView=new TextView(activity);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = weight;
        textView.setLayoutParams(params);
        return textView;
    }
}
