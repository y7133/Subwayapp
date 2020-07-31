package com.example.myapplication;

import android.app.Activity;
import android.util.Log;

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

class Transfer{
    String lineNm;
    String stationNm;
    String tLineNm;
    String nexStation;
    String endStation;
    Transfer(String lineNm,String stationNm, String tLineNm, String nexStation, String  endStation){
        this.lineNm=lineNm;
        this.stationNm=stationNm;
        this.tLineNm=tLineNm;
        this.nexStation=nexStation;
        this.endStation=endStation;
    }
}

public class manageStationExelFile extends AppCompatActivity {
    private InputStream is;
    private Workbook wb;
    private Sheet sheet1;
    private Sheet sheet2;

    public void openExel(Activity activity){
        try {
            is = activity.getBaseContext().getResources().getAssets().open("subway_station.xls");
            wb=Workbook.getWorkbook(is);
            sheet1=wb.getSheet(0);
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }
    public void openExel(Activity activity,int sheetNum){
        try {
            is = activity.getBaseContext().getResources().getAssets().open("transfer.xls");
            wb=Workbook.getWorkbook(is);
            sheet2=wb.getSheet(sheetNum);
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Station> findStation(String keyword){
        if(sheet1==null)
            return null;

        int row=sheet1.getRows();
        ArrayList<Station> stationList=new ArrayList<>();

        Cell cell;
        String temp;

        int i=0;
        do{
            cell=sheet1.getCell(5,i);
            temp=cell.getContents();
            if(temp=="")
                break;;

            if(temp.contains(keyword)){
                Station station=new Station(sheet1.getCell(0,i).getContents(),sheet1.getCell(1,i).getContents(),
                        sheet1.getCell(2,i).getContents(),sheet1.getCell(3,i).getContents(),
                        sheet1.getCell(4,i).getContents(), sheet1.getCell(5,i).getContents());
                stationList.add(station);
            }
            i++;
        }while(i<row);

        return stationList;
    }

    public ArrayList<Transfer> findStation2(String keyword){
        if(sheet2==null)
            return null;

        int row=sheet2.getRows();
        ArrayList<Transfer> stationList=new ArrayList<>();

        Cell cell;
        String temp;

        int i=0;
        do{
            cell=sheet2.getCell(2,i);
            temp=cell.getContents();
            if(temp=="")
                break;;

            if(temp.equals(keyword)){
                Transfer station=new Transfer(sheet2.getCell(1,i).getContents(),
                        sheet2.getCell(2,i).getContents(),
                        sheet2.getCell(4,i).getContents(), sheet2.getCell(5,i).getContents(), sheet2.getCell(6,i).getContents());
                stationList.add(station);
            }
            i++;
        }while(i<row);

        return stationList;
    }

    public ArrayList<Station> findStation(String keyword, String location){
        if(sheet1==null)
            return null;

        int row=sheet1.getRows();
        ArrayList<Station> stationList=new ArrayList<>();

        Cell cell;
        String temp;

        int i=0;
        do{
            cell=sheet1.getCell(5,i);
            temp=cell.getContents();
            if(temp=="")
                break;;

            if(temp.contains(keyword)){
                if(isLocated(location,sheet1.getCell(0,i).getContents())) {
                    Station station = new Station(sheet1.getCell(0, i).getContents(), sheet1.getCell(1, i).getContents(),
                            sheet1.getCell(2, i).getContents(), sheet1.getCell(3, i).getContents(),
                            sheet1.getCell(4, i).getContents(), sheet1.getCell(5, i).getContents());
                    stationList.add(station);
                }
            }
            i++;
        }while(i<row);

        return stationList;
    }

    private boolean isLocated(String mreaWideCd,String railOprIsttCd){
        switch (mreaWideCd){
            case "01":
                if(railOprIsttCd.equals("AR")||railOprIsttCd.endsWith("DX")||railOprIsttCd.equals("EV")||railOprIsttCd.equals("GM")||railOprIsttCd.equals("IC")||
                railOprIsttCd.equals("KR")||railOprIsttCd.equals("SW")||railOprIsttCd.equals("S1")||railOprIsttCd.equals("S5")||railOprIsttCd.equals("S9")||
                        railOprIsttCd.equals("UI")||railOprIsttCd.equals("UL"))
                    return true;
                break;
            case "02":
                if(railOprIsttCd.equals("BG")||railOprIsttCd.equals("BS"))
                    return true;
                break;
            case "03":
                if(railOprIsttCd.equals("DG"))
                    return true;
                break;
            case "04":
                if(railOprIsttCd.equals("GJ"))
                    return true;
                break;
            case "05":
                if(railOprIsttCd.equals("DJ"))
                    return true;
                break;
        }
        Log.e(mreaWideCd,railOprIsttCd);
        return false;
    }

}
