package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.apiPackage.StationTimetable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
//일단 검색 하고 그 검색어가 저장되는 그런식임
//결과를 보려면 위에 파싱이 되어야하는데 그부분부터 계속 막히고 있음
//만약 검색에 대해서 이러한 Search adapter사용시 또다른 fragment나 페이지 필요할수도 있음
public class Menu1Frag extends Fragment {

    int i=0;
    int j=0;
    StationTimetable stationTimetable;
    StationTimetable state;
    EditText editText;
    TextView textView;
    Button btn1;
    Button btn2;
    LinearLayout stationListLayout;
    Button findStationBtn;
    EditText stationKeyText;
    ListView List;
    public Context context;
    private java.util.List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view=inflater.inflate(R.layout.fragment_menu1_frag_growth,container,false);

        //stationListLayout=(LinearLayout)view.findViewById(R.id.stationList);
        findStationBtn=(Button)view.findViewById(R.id.findStationBtn);
        stationKeyText=(EditText)view.findViewById(R.id.stationKey);
        listView = (ListView)view.findViewById(R.id.listView);

        // 리스트를 생성한다.
        list = new ArrayList<String>();

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list,getActivity());


        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        stationKeyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = stationKeyText.getText().toString();
                search(text);
            }
        });
        findStationBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                String stationKey=stationKeyText.getText().toString();
                if(stationKey=="")
                    return;
                ++j;
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                editor.putString(Integer.toString(j),stationKeyText.getText().toString()); // key,value 형식으로 저장
                editor.commit();
                manageStationExelFile f=new manageStationExelFile();
                f.openExel(getActivity());
                final ArrayList<Station> stationList=f.findStation(stationKey);
                ArrayList<StationTimetable> stationlist;
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
                    Date current= Calendar.getInstance().getTime();
                    SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
                    String weekDay = weekdayFormat.format(current);
                    final String day;
                    if(weekDay=="토 ")
                        day="7";
                    else if(weekDay=="일 ")
                        day="9";
                    else
                        day="8";
                    stationTimetable=new StationTimetable(railOprIsttCd,lnCd,stinCd,day);
                    stationTimetable.setStationTimetable();
                    ArrayList<String> trno=stationTimetable.trnNo;
                    ArrayList<Integer>dptTm=stationTimetable.dptTm;
                    ArrayList<String>orgstin=stationTimetable.orgStinCd;

                    //여기서부터 에러
                    locationText.setText(trno.get(0));
                    lineText.setText(dptTm.get(0));
                    stationText.setText(orgstin.get(0));
                    linearLayout.addView(locationText);
                    linearLayout.addView(lineText);
                    linearLayout.addView(stationText);

                    final int index=i;

                    /*linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(),StationTimetable.class);
                            intent.putExtra("railOprIsttCd",stationList.get(index).RAIL_OPR_ISTT_CD);
                            intent.putExtra("lnCd",stationList.get(index).LN_CD);
                            intent.putExtra("stinCd",stationList.get(index).STIN_CD);
                            intent.putExtra("dayCd",day);
                            startActivity(intent);
                        }
                    });*/

                    stationListLayout.addView(linearLayout);
                }

            }
        });
        return view;
    }
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
    private void settingList(){
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
        String inputText = sharedPreferences.getString(Integer.toString(i),"");
        list.add(inputText);
    }

}
