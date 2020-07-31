package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.apiPackage.StationTimetable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.write.Label;

public class Menu1Frag extends Fragment {
    LinearLayout stationLayout;

    StationTimetable stationTimetable;
    manageStationExelFile m;

    ArrayList<Station> station;

    String time_s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu1_frag_growth, container, false);

        stationLayout= (LinearLayout) view.findViewById(R.id.stationList2);
        m=new manageStationExelFile();
        m.openExel(getActivity(),"store");

        station=m.findStation();

        for(int i=0;i<station.size();i++){
            stationTimetable = createTimeTable(i);
            TextView locationText=new TextView(view.getContext());
            TextView lineText=new TextView(view.getContext());
            TextView timeText=new TextView(view.getContext());
            LinearLayout linearLayout=new LinearLayout(view.getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setPadding(0,10,0,10);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;

            locationText.setLayoutParams(params);
            lineText.setLayoutParams(params);
            timeText.setLayoutParams(params);

            locationText.setText(station.get(i).LN_NM);
            lineText.setText(station.get(i).STIN_NM);
            stationTimetable.setStationTimetable(timeText,time_s);

            linearLayout.addView(locationText);
            linearLayout.addView(lineText);
            linearLayout.addView(timeText);


            final int index=i;

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),StationInfoActivity.class);
                    intent.putExtra("railOprIsttCd",station.get(index).RAIL_OPR_ISTT_CD);
                    intent.putExtra("lnCd",station.get(index).LN_CD);
                    intent.putExtra("stinCd",station.get(index).STIN_CD);
                    intent.putExtra("stinNm",station.get(index).STIN_NM);
                    startActivity(intent);
                }
            });

            stationLayout.addView(linearLayout);
        }

        return view;
    }

    private StationTimetable createTimeTable(int i){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE");
        SimpleDateFormat timeFormat=new SimpleDateFormat("HHmmss");

        String week_s=weekdayFormat.format(date);
        time_s=timeFormat.format(date);

        String dayCd;
        if(week_s.equals("토")){
            dayCd="7";
        }
        else if(week_s.equals("일")){
            dayCd="9";
        }
        else{
            dayCd="8";
        }

        return new StationTimetable(station.get(i).RAIL_OPR_ISTT_CD,station.get(i).LN_CD,station.get(i).STIN_CD,dayCd);

    }



}