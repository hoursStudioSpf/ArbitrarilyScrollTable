package com.spf.arb.data;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.spf.arb.view.ArbAdapter;
import com.spf.arb.view.ArbitrarilyScrollView;

import java.util.ArrayList;
import java.util.Collection;


public class StudentAdapter extends ArbAdapter<StudentsList> {

    StudentsList studentDataList;
    Context mCxt;
    int tableColCount1;
    int tableColCount2;
    int lastSortIndex = 1;

    ArrayList<StudentsEntity> data1List;
    ArrayList<StudentsEntity> data2List;

    public StudentAdapter(Activity context, StudentsList obj) {
        this.mCxt = context;
        this.studentDataList = obj;
        table1DataSize = studentDataList.studentsList1.size();
        table2DataSize = studentDataList.studentsList2.size();
        tableColCount2 = tableColCount1 = studentDataList.keysMap.size() + 1;//算上第一列表头
        data1List = studentDataList.studentsList1;
        data2List = studentDataList.studentsList2;
    }


    @Override
    public void setData(StudentsList obj) {
        this.studentDataList = obj;
        table1DataSize = studentDataList.studentsList1.size();
        table2DataSize = studentDataList.studentsList2.size();
        tableColCount2 = tableColCount1 = studentDataList.keysMap.size() + 1;//算上第一列表头
        data1List = studentDataList.studentsList1;
        data2List = studentDataList.studentsList2;
    }

    public int getLastSortIndex(){
        return lastSortIndex;
    }

    @Override
    public String[] getHeader1() {
        Collection<String> cols = studentDataList.keysMap.values();
        String[] header1 = new String[cols.size() + 1];
        header1[0] = tableName1;//TODO NEED SET
        String[] headerTemp = new String[cols.size()];
        cols.toArray(headerTemp);
        for (int i = 0; i < headerTemp.length; i++) {
            header1[i + 1] = headerTemp[i];
        }
        return header1;
    }

    @Override
    public String[] getHeader2() {
        Collection<String> cols = studentDataList.keysMap.values();
        String[] header2 = new String[cols.size() + 1];
        header2[0] = tableName2;//TODO NEED SET
        String[] headerTemp = new String[cols.size()];
        cols.toArray(headerTemp);
        for (int i = 0; i < headerTemp.length; i++) {
            header2[i + 1] = headerTemp[i];
        }
        return header2;
    }

    @Override
    public int getTableRowCount1() {
        return table1DataSize;
    }

    @Override
    public int getTableColCount1(int rowIndex) {
        return tableColCount1;
    }

    @Override
    public int getTableRowCount2() {
        return table2DataSize;
    }

    @Override
    public int getTableColCount2(int rowIndex) {
        int colCount = studentDataList.keysMap.size();
        return tableColCount2;
    }

    @Override
    public Object getRowItem1(int rowIndex) {
        return studentDataList.studentsList1.get(rowIndex);
    }

    @Override
    public String getColItem1(int rowIndex, int colIndex) {
        return getItem(true, rowIndex, colIndex);
    }

    @Override
    public Object getRowItem2(int rowIndex) {
        return studentDataList.studentsList2.get(rowIndex);
    }


    @Override
    public String getColItem2(int rowIndex, int colIndex) {
        return getItem(false, rowIndex, colIndex);
    }

    @Override
    public boolean hightLight(int tableIndex, int rowIndex, int colIndex) {
//        if (0 == tableIndex) {
//            pe = (PlayerStatisticEntity) getRowItem1(rowIndex);
//            return pe.on_court == 1;
//        } else if (1 == tableIndex) {
//            pe = (PlayerEntity) getRowItem2(rowIndex);
//        } else {
//            return false;
//        }
//        return pe.on_court == 1;
        return false;
    }

    @Override
    public Object getTag(int tableIndex, int rowIndex, int colIndex) {
        StudentsEntity studentsEntity = null;
        if (tableIndex == 0) {
            studentsEntity = studentDataList.studentsList1.get(rowIndex);
        } else if (tableIndex == 1) {
            studentsEntity = studentDataList.studentsList2.get(rowIndex);
        }
        return studentsEntity;
    }

    @Override
    public void OnItemClicked(View view) {
        ArbitrarilyScrollView.Tag clickTag = (ArbitrarilyScrollView.Tag)view.getTag();
        if (null == clickTag) {
            return;
        }
        Object entityTag = clickTag.innerTag;

        if (null != entityTag && entityTag instanceof StudentsEntity) {
            StudentsEntity stuTag = (StudentsEntity) entityTag;
            Toast.makeText(mCxt, "clicked-->" + stuTag.name + "...", Toast.LENGTH_SHORT).show();
        }

        if (clickTag.colIndex > 0 && clickTag.rowIndex == 0) {//click horizontal title
            StudentsList.SortIndex sortIndex = studentDataList.sortByKey(clickTag.colIndex);
            lastSortIndex = clickTag.colIndex;
            table1DataSize = studentDataList.studentsList1.size();
            table2DataSize = studentDataList.studentsList2.size();
            tableColCount2 = tableColCount1 = studentDataList.keysMap.size() + 1;//算上第一列表头
            data1List = studentDataList.studentsList1;
            data2List = studentDataList.studentsList2;
            this.resortRows(sortIndex);
        }

    }

    private String getItem(boolean getPre, int rowIndex, int colIndex) {
        Collection<String> clos;
        StudentsEntity targetPlayer;
        if (getPre) {//表一
            targetPlayer = data1List.get(rowIndex);
        } else {//表二
            targetPlayer = data2List.get(rowIndex);
        }
        if (colIndex == 0) {
            return targetPlayer.name;
        } else {
            return targetPlayer.dataAry[colIndex - 1];
        }
    }

}