package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.example.myapplication.apiPackage.SubwayRouteInfo;

import java.util.ArrayList;

public class Menu4Frag extends Fragment {
    public static Menu4Frag newInstance() {
        return new Menu4Frag();
    }

    LinearLayout startStationLayout;
    LinearLayout endStationLayout;
    Button startStationBtn;
    Button endStationBtn;
    EditText startStationTxt;
    Intent intent;
    EditText endStationTxt;

    RadioGroup radioGroup;

    Button findPathBtn;

    TextView startPointTxt;
    TextView endPointTxt;

    boolean isStartStation;
    boolean isEndStation;

    String mreaWideCd="01";

    static public SubwayRouteInfo subwayRouteInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        final View view = inflater.inflate(R.layout.fragment_menu4_frag_config, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        intent=new Intent(getActivity(),findPath.class);

        isStartStation=false;
        isEndStation=false;

        startStationLayout=(LinearLayout) view.findViewById(R.id.startStationLayout);
        endStationLayout=(LinearLayout)view.findViewById(R.id.endStationLayout);
        startStationBtn=(Button)view.findViewById(R.id.startStationBtn);
        endStationBtn=(Button) view.findViewById(R.id.endStationBtn);
        startStationTxt=(EditText)view.findViewById(R.id.startStationText);
        endStationTxt=(EditText)view.findViewById(R.id.endStationText);
        findPathBtn=(Button)view.findViewById(R.id.findPathBtn);
        startPointTxt=(TextView)view.findViewById(R.id.startPoint);
        endPointTxt=(TextView)view.findViewById(R.id.endPoint);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        startStationBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                startStationLayout.removeAllViews();
                String stationKey=startStationTxt.getText().toString();
                if(stationKey=="")
                    return;

                manageStationExelFile f=new manageStationExelFile();
                f.openExel(getActivity());
                final ArrayList<Station> stationList=f.findStation(stationKey,mreaWideCd);


                for(int i=0;i<stationList.size();i++){
                    TextView stationText=new TextView(view.getContext());
                    final LinearLayout linearLayout=new LinearLayout(view.getContext());
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setPadding(0,10,0,10);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    params.weight = 1.0f;

                    stationText.setLayoutParams(params);

                    stationText.setText(stationList.get(i).STIN_NM);


                    linearLayout.addView(stationText);

                    final int index=i;

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra("sRailOprIsttCd",stationList.get(index).RAIL_OPR_ISTT_CD);
                            intent.putExtra("sLnCd",stationList.get(index).LN_CD);
                            intent.putExtra("sStinCd",stationList.get(index).STIN_CD);
                            intent.putExtra("sStinNm",stationList.get(index).STIN_NM);

                            startPointTxt.setText(stationList.get(index).STIN_NM);
                            isStartStation=true;
                        }
                    });

                    startStationLayout.addView(linearLayout);
                }

            }
        });

        endStationBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                endStationLayout.removeAllViews();
                String stationKey=endStationTxt.getText().toString();
                if(stationKey=="")
                    return;

                manageStationExelFile f=new manageStationExelFile();
                f.openExel(getActivity());
                final ArrayList<Station> stationList=f.findStation(stationKey,mreaWideCd);

                for(int i=0;i<stationList.size();i++){

                    TextView stationText=new TextView(view.getContext());
                    final LinearLayout linearLayout=new LinearLayout(view.getContext());
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setPadding(0,10,0,10);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    params.weight = 1.0f;


                    stationText.setLayoutParams(params);

                    stationText.setText(stationList.get(i).STIN_NM);

                    linearLayout.addView(stationText);

                    final int index=i;

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra("eRailOprIsttCd",stationList.get(index).RAIL_OPR_ISTT_CD);
                            intent.putExtra("eLnCd",stationList.get(index).LN_CD);
                            intent.putExtra("eStinCd",stationList.get(index).STIN_CD);
                            intent.putExtra("eStinNm",stationList.get(index).STIN_NM);

                            endPointTxt.setText(stationList.get(index).STIN_NM);

                            isEndStation=true;
                        }
                    });

                    endStationLayout.addView(linearLayout);
                }

            }
        });

        findPathBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!isEndStation||!isStartStation){
                    Toast.makeText(getContext(),"출발지, 도착지를 제대로 클릭해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                intent.putExtra("mreaWideCd",mreaWideCd);
                startActivity(intent);
            }
        });

        return view;
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.radioButton){
                mreaWideCd="01";
            }
            else if(i == R.id.radioButton2){
                mreaWideCd="02";
            }
            else if(i==R.id.radioButton3){
                mreaWideCd="03";
            }
            else if(i==R.id.radioButton4){
                mreaWideCd="04";
            }
            else if(i==R.id.radioButton5){
                mreaWideCd="05";
            }
            startStationLayout.removeAllViews();
            endStationLayout.removeAllViews();
        }
    };

}