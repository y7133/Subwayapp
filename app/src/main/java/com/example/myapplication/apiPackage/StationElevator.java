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
    public ArrayList<Integer> runStinFlorFr = new ArrayList<Integer>();
    public ArrayList<Integer> runStinFlorTo = new ArrayList<Integer>();

    public StationElevator(String railOprIsttCd, String lnCd, String stinCd) {
        this.railOprIsttCd = railOprIsttCd;
        this.lnCd = lnCd;
        this.stinCd = stinCd;
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setStationElevator() {
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
                        runStinFlorFr.add((Integer) jsonObject.get("runStinFlorFr"));
                        runStinFlorTo.add((Integer) jsonObject.get("runStinFlorTo"));
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

    public void setStationElevator(final LinearLayout layout, final Activity activity) {
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
                        LinearLayout linearLayout=createLayout(activity);

                        TextView location=createTextView(activity,3);
                        TextView exit=createTextView(activity,4);
                        TextView startF=createTextView(activity,4);
                        TextView endF=createTextView(activity,4);

                        startF.setText(jsonObject.isNull("runStinFlorFr")?"정보 없음":jsonObject.getString("runStinFlorFr"));
                        endF.setText(jsonObject.isNull("runStinFlorTo")?"정보 없음":jsonObject.getString("runStinFlorTo"));
                        location.setText( jsonObject.isNull("dtlLoc")?"정보 없음":jsonObject.getString("dtlLoc"));
                        exit.setText(jsonObject.isNull("exitNo")?"정보 없음":jsonObject.getString("exitNo"));

                        linearLayout.addView(location);
                        linearLayout.addView(exit);
                        linearLayout.addView(startF);
                        linearLayout.addView(endF);

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
