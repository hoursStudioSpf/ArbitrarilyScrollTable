package com.spf.arb.view;

import android.view.View;

import com.spf.arb.data.StudentsList;

/**
 * Description: Adapter for ArbScroller
 * Author: ShiPeifeng
 * Date: 16/9/23.
 */
public abstract class ArbAdapter<T> {

    public int table1DataSize;
    public int table2DataSize;

    public String tableName1;
    public String tableName2;

    public int itemTxtWidth;
    public int itemTxtHeight;

    public int vTitleWidth;
    public int vTitleHeight;

    public int hTitleWidth;
    public int hTitleHeight;

    public int tableNameWidth;
    public int tableNameHeight;

    public int blankHeight;

    public ArbitrarilyScrollView mObserver;

    public abstract void setData(T obj);

    public abstract String[] getHeader1();
    public abstract String[] getHeader2();

    public abstract int getTableRowCount1();
    public abstract int getTableColCount1(int rowIndex);

    public abstract int getTableRowCount2();
    public abstract int getTableColCount2(int rowIndex);

    public abstract Object getRowItem1(int rowIndex);
    public abstract String getColItem1(int rowIndex, int colIndex);

    public abstract Object getRowItem2(int rowIndex);
    public abstract String getColItem2(int rowIndex, int colIndex);

    public abstract boolean hightLight(int tableIndex, int rowIndex, int colIndex);

    public void notifyDataSetChange() {
        mObserver.updateView();
    }

    public void notifyDataSetChangeWithViewReset() {
        mObserver.resetView();
    }

    public void resortRows(StudentsList.SortIndex sortIndex) {
        mObserver.resortRows(sortIndex);
    }

    public void setTableName(String str1, String str2) {
        this.tableName1 = str1;
        this.tableName2 = str2;
    }

    /**
     * @param itemWidth 表格数据item的宽
     * @param itemHeight 表格数据item的高
     * @param vTWidth 表格水平表头的宽
     * @param vTHeight 表格水平表头的高
     * @param hTWidth 表格竖直表头的宽
     * @param hTHeight 表格竖直表头的高
     * @param tableNameW 表名的宽
     * @param tableNameH 表名的高
     * @param blankHeight 空白的高度
     * */
    public void setTableLW(int itemWidth, int itemHeight, int vTWidth, int vTHeight, int hTWidth,
                           int hTHeight, int tableNameW, int tableNameH, int blankHeight) {
        this.itemTxtWidth = itemWidth;
        this.itemTxtHeight = itemHeight;
        this.vTitleWidth = vTWidth;
        this.vTitleHeight = vTHeight;
        this.hTitleWidth = hTWidth;
        this.hTitleHeight = hTHeight;
        this.tableNameWidth = tableNameW;
        this.tableNameHeight = tableNameH;
        this.blankHeight = blankHeight;
    }

    public abstract Object getTag(int tableIndex, int rowIndex, int colIndex);

    public abstract void OnItemClicked(View view);

}
