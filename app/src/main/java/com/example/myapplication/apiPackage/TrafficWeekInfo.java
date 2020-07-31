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

    public TrafficWeekInfo(String railOprIsttCd, String lnCd, String stinCd) {
        this.railOprIsttCd = railOprIsttCd;
        this.lnCd = lnCd;
        this.stinCd = stinCd;
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setTrafficWeekInfo() {
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

    public void setTrafficWeekInfo(final Activity activity, final LinearLayout layout1, final LinearLayout layout2) {
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
                        if("1".equals(jsonObject.get("mvPathDvCd"))){
                            TextView textView=createTextView(activity,1, (String) jsonObject.get("mvContDtl"));
                            layout1.addView(textView);
                        }
                        else if("3".equals(jsonObject.get("mvPathDvCd"))){
                            TextView textView=createTextView(activity,1, (String) jsonObject.get("mvContDtl"));
                            layout2.addView(textView);
                        }
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
    private TextView createTextView(Activity activity, float weight, String content){
        TextView textView=new TextView(activity);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = weight;
        textView.setLayoutParams(params);
        textView.setText(content);
        return textView;
    }
}