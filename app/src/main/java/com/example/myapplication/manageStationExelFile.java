package com.example.myapplication;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

class Station{
    String RAIL_OPR_ISTT_CD;
    String RAIL_OPR_ISTT_NM;
    String LN_CD;
    String LN_NM;
    String  STIN_CD;
    String STIN_NM;

    Station(String RAIL_OPR_ISTT_CD, String RAIL_OPR_ISTT_NM, String LN_CD, String LN_NM, String  STIN_CD,  String STIN_NM){
        this.RAIL_OPR_ISTT_CD=RAIL_OPR_ISTT_CD;
        this.RAIL_OPR_ISTT_NM=RAIL_OPR_ISTT_NM;
        this.LN_CD=LN_CD;
        this.LN_NM=LN_NM;
        this.STIN_CD=STIN_CD;
        this.STIN_NM=STIN_NM;
    }
}

public class manageStationExelFile extends AppCompatActivity {
    private InputStream is;
    private Workbook wb;
    private Sheet sheet;

    public void openExel(Activity activity){
        try {
            is = activity.getBaseContext().getResources().getAssets().open("subway_station.xls");
            wb=Workbook.getWorkbook(is);
            sheet=wb.getSheet(0);
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Station> findStation(String keyword){
        if(sheet==null)
            return null;

        int row=sheet.getRows();
        ArrayList<Station> stationList=new ArrayList<>();

        Cell cell;
        String temp;

        int i=0;
        do{
            cell=sheet.getCell(5,i);
            temp=cell.getContents();
            if(temp=="")
                break;;

            if(temp.contains(keyword)){
                Station station=new Station(sheet.getCell(0,i).getContents(),sheet.getCell(1,i).getContents(),
                        sheet.getCell(2,i).getContents(), sheet.getCell(3,i).getContents(),
                        sheet.getCell(4,i).getContents(), sheet.getCell(5,i).getContents());
                stationList.add(station);
            }
            i++;
        }while(i<row);

        return stationList;
    }

}
