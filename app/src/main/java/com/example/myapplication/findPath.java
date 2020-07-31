package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.apiPackage.SubwayRouteInfo;

public class findPath extends AppCompatActivity {

    LinearLayout pathLayout;

    String sRailOprIsttCd;
    String sLnCd;
    String sStinCd;
    String sStinNm;

    String eRailOprIsttCd;
    String eLnCd;
    String eStinCd;
    String eStinNm;

    TextView startStationTxt;
    TextView endStationTxt;

    manageStationExelFile m= new manageStationExelFile();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_path);

        startStationTxt=(TextView)findViewById(R.id.startStation);
        endStationTxt=(TextView)findViewById(R.id.endStation);

        pathLayout=(LinearLayout)findViewById(R.id.pathLayout);

        Intent intent=getIntent();
        sRailOprIsttCd= intent.getExtras().getString("sRailOprIsttCd");
        sLnCd=intent.getExtras().getString("sLnCd");
        sStinCd=intent.getExtras().getString("sStinCd");
        sStinNm=intent.getExtras().getString("sStinNm");

        eRailOprIsttCd= intent.getExtras().getString("eRailOprIsttCd");
        eLnCd=intent.getExtras().getString("eLnCd");
        eStinCd=intent.getExtras().getString("eStinCd");
        eStinNm=intent.getExtras().getString("eStinNm");

        startStationTxt.setText(sStinNm);
        endStationTxt.setText(eStinNm);

        String mreaWideCd=intent.getExtras().getString("mreaWideCd");

        if(mreaWideCd=="01")
            m.openExel(this,0);
        else if(mreaWideCd=="02")
            m.openExel(this,1);
        else if(mreaWideCd=="03")
            m.openExel(this,2);

        SubwayRouteInfo sSubwayRouteInfo=new SubwayRouteInfo(sLnCd,mreaWideCd);
        SubwayRouteInfo eSubwayRouteInfo=new SubwayRouteInfo(eLnCd,mreaWideCd);
        SubwayRouteInfo tSubwayRouteInfo;

        //환승되도록 고쳐야하는데 고칠일 없겟지=================================================
        if(sLnCd.equals(eLnCd)){
            sSubwayRouteInfo.findSubwayRouteInfo(pathLayout,this,sStinNm,eStinNm);
        }
        else{
            TextView textView=createTextView(this,1,"환승 정보는 제공하지 않습니다. 죄송합니다.");
            pathLayout.addView(textView);
        }

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
