package com.example.myapplication.apiPackage;

import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

//역사별 정보
public class StationInfo {
    private static final String SERVER_URL = "http://openapi.kric.go.kr/openapi/";
    public static final String CLASSIFICATION = "convenientInfo";
    public static final String INFORMATION = "stationInfo";
    public static final String SERVICE_KEY
            = "$2a$10$z2J0KoVJvhjPavvleYId6eUFObq6DU62bZQ8/tVpuwFRjjaICLdUS";
    public static final String FORMAT = "json"; //데이터 포맷

    public String railOprIsttCd;  //철도 운영기관 코드
    public String lnCd;           //선코드
    public String stinCd;         //역코드
    public String stinNm;         //역명

    public String lonmAdr = new String();   //지번주소
    public String roadNmAdr = new String(); //도로명 주소
    public String stinNmEng = new String(); //역명_영문

    public StationInfo(String railOprIsttCd, String lnCd, String stinCd, String stinNm) {
        this.railOprIsttCd = railOprIsttCd;
        this.lnCd = lnCd;
        this.stinCd = stinCd;
        this.stinNm=stinNm;
    }

    AsyncHttpClient client=new AsyncHttpClient();


    //textView에 정보 입력
    public void setStationInfo(final TextView stinNmEngT, final TextView lonmAdrT, final TextView roadNmAdrT) {
        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd+"&stinNm="+stinNm;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("StationInfo","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    lonmAdr=(String) jsonObject.get("lonmAdr");
                    roadNmAdr=(String) jsonObject.get("roadNmAdr");
                    stinNmEng=(String) jsonObject.get("stinNmEng");

                    //text삽입
                    //=====================================
                    lonmAdrT.setText(lonmAdr);
                    roadNmAdrT.setText(roadNmAdr);
                    stinNmEngT.setText(stinNmEng);
                    //=====================================

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

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                super.onPostProcessResponse(instance, response);


            }
        });
    }

}
