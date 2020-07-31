package com.example.myapplication.apiPackage;

import android.app.Activity;
import android.graphics.Paint;
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

    public SubwayRouteInfo(String lnCd, String mreaWideCd) {
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

    public void findSubwayRouteInfo(final LinearLayout pathLayout, final Activity activity, final String stationNm1, final String stationNm2) {

        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&lnCd=" + lnCd + "&mreaWideCd=" + mreaWideCd;

        client.get(url, new JsonHttpResponseHandler() {
            int s;
            int e;

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

                        if(stationNm1.equals(jsonObject.getString("stinNm"))){
                            s=i;
                        }
                        else if(stationNm2.equals(jsonObject.getString("stinNm"))){
                            e=i;
                        }
                    }

                    if(s<e){
                        String con= stinNm.get(stinNm.size()-1) + " 방향";
                        TextView endStation=createTextView(activity,1,con);
                        endStation.setPaintFlags(endStation.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                        endStation.setTextSize(30);
                        pathLayout.addView(endStation);

                        for(int i=s;i<=e;i++){
                            String content=stinNm.get(i);
                            TextView textView=createTextView(activity,1,content);
                            pathLayout.addView(textView);
                        }
                    }
                    else{
                        String con= stinNm.get(0) + " 방향";
                        TextView endStation=createTextView(activity,1,con);
                        endStation.setPaintFlags(endStation.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                        endStation.setTextSize(30);
                        pathLayout.addView(endStation);

                        Log.e(String.valueOf(s),String.valueOf(e));
                        for(int i=s ; i>=e; i--){
                            String content = stinNm.get(i);
                            TextView textView = createTextView(activity, 1, content);
                            pathLayout.addView(textView);
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

    private TextView createTextView(Activity activity, float weight, String content){
        TextView textView=new TextView(activity);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //params.weight = weight;
        textView.setLayoutParams(params);
        textView.setText(content);
        return textView;
    }

}
