package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class Menu3Frag extends Fragment{
    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static Menu3Frag newInstance() {
        return new Menu3Frag();
    }

    LinearLayout stationListLayout;
    Button findStationBtn;
    EditText stationKeyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        final View view = inflater.inflate(R.layout.fragment_menu3_frag_weather, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        stationListLayout=(LinearLayout)view.findViewById(R.id.stationList);
        findStationBtn=(Button)view.findViewById(R.id.findStationBtn);
        stationKeyText=(EditText)view.findViewById(R.id.stationKey);

        findStationBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                stationListLayout.removeAllViews();
                String stationKey=stationKeyText.getText().toString();
                if(stationKey=="")
                    return;

                manageStationExelFile f=new manageStationExelFile();
                f.openExel(getActivity());
                final ArrayList<Station> stationList=f.findStation(stationKey);


                for(int i=0;i<stationList.size();i++){
                    TextView locationText=new TextView(view.getContext());
                    TextView lineText=new TextView(view.getContext());
                    TextView stationText=new TextView(view.getContext());
                    LinearLayout linearLayout=new LinearLayout(view.getContext());
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setPadding(0,10,0,10);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    params.weight = 1.0f;

                    locationText.setLayoutParams(params);
                    lineText.setLayoutParams(params);
                    stationText.setLayoutParams(params);

                    final String railOprIsttCd=stationList.get(i).RAIL_OPR_ISTT_NM;
                    final String lnCd=stationList.get(i).LN_NM;
                    final String stinCd=stationList.get(i).STIN_NM;

                    locationText.setText(stationList.get(i).RAIL_OPR_ISTT_NM);
                    lineText.setText(stationList.get(i).LN_NM);
                    stationText.setText(stationList.get(i).STIN_NM);

                    linearLayout.addView(locationText);
                    linearLayout.addView(lineText);
                    linearLayout.addView(stationText);

                    final int index=i;

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(),StationInfoActivity.class);
                            intent.putExtra("railOprIsttCd",stationList.get(index).RAIL_OPR_ISTT_CD);
                            intent.putExtra("lnCd",stationList.get(index).LN_CD);
                            intent.putExtra("stinCd",stationList.get(index).STIN_CD);
                            intent.putExtra("stinNm",stationList.get(index).STIN_NM);
                            startActivity(intent);
                        }
                    });

                    stationListLayout.addView(linearLayout);
                }

            }
        });
        return view;
    }

    public void onClick(Station station){
        Log.e(station.STIN_NM,station.RAIL_OPR_ISTT_NM);
    }
}
