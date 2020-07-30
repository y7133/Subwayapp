package com.example.myapplication.apiPackage;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

//환승 이동경로
//https://data.kric.go.kr/rips/M_01_02/detail.do?id=307&service=vulnerableUserInfo&operation=transferMovement&keywords=%ed%99%98%ec%8a%b9&lcd=&mcd=
public class TransferMovement {
    private static final String SERVER_URL = "http://openapi.kric.go.kr/openapi/";
    public static final String CLASSIFICATION = "vulnerableUserInfo";
    public static final String INFORMATION = "transferMovement";
    public static final String SERVICE_KEY
            = "$2a$10$1OenS/80qF5SDcJhHeMjy.QlrKmKwncAUhZ5l1SCOoFzuauU6J.xm";
    public static final String FORMAT = "json"; //데이터 포맷

    public String lnCd;           //선코드
    public String stinCd;         //역코드
    public String railOprIsttCd;
    public String chthTgtLn;
    public String chtnNextStinCd;
    public String prevStinCd;

    public ArrayList<Integer> chtnMvTpOrdr = new ArrayList<Integer>();  //환승이동 유형 순서
    public ArrayList<String> edMovePath = new ArrayList<String>();      //도착지
    //public ArrayList<String> elvtSttCd = new ArrayList<String>();       //승강기 상태코드
    //public ArrayList<String> elvtTpCd = new ArrayList<String>();        //승강기 유형 코드
    public ArrayList<String> imgPath = new ArrayList<String>();         //이미지 경로
    public ArrayList<String> mvContDtl = new ArrayList<String>();       //상세 이동내용
    public ArrayList<Integer> mvPathMgNo = new ArrayList<Integer>();    //이동경로 관리번호
    public ArrayList<String> stMovePath = new ArrayList<String>();      //시작지

    TransferMovement(String railOprIsttCd, String lnCd, String stinCd, String chthTgtLn, String chtnNextStinCd, String prevStinCd) {
        this.lnCd = lnCd;
        this.stinCd = stinCd;
        this.railOprIsttCd=railOprIsttCd;
        this.chthTgtLn=chthTgtLn;
        this.chtnNextStinCd=chtnNextStinCd;
        this.prevStinCd=prevStinCd;
    }

    AsyncHttpClient client=new AsyncHttpClient();

    public void setTransferMovement() {
        railOprIsttCd = "S1";
        lnCd = "3";
        stinCd = "331";
        chthTgtLn="4";
        chtnNextStinCd="424";
        prevStinCd="422";

        final String url = SERVER_URL + CLASSIFICATION + "/" + INFORMATION +
                "?serviceKey=" + SERVICE_KEY + "&format=" + FORMAT +
                "&railOprIsttCd=" + railOprIsttCd + "&lnCd=" + lnCd + "&stinCd=" + stinCd +
                "&chthTgtLn="+chthTgtLn+"&chtnNextStinCd="+chtnNextStinCd+"&prevStinCd="+prevStinCd;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("TransferMovement","success connect");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        chtnMvTpOrdr.add((Integer) jsonObject.get("chtnMvTpOrdr"));
                        edMovePath.add((String) jsonObject.get("edMovePath"));
                        //elvtSttCd.add((String) jsonObject.get("elvtSttCd"));
                        //elvtTpCd.add((String) jsonObject.get("elvtTpCd"));
                        imgPath.add((String) jsonObject.get("imgPath"));
                        mvContDtl.add((String) jsonObject.get("mvContDtl"));
                        mvPathMgNo.add((Integer) jsonObject.get("mvPathMgNo"));
                        stMovePath.add((String) jsonObject.get("stMovePath"));
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
