package com.spf.arb.view;

/**
 * Description: Adapter for ArbScroller
 * Author: ShiPeifeng
 * Date: 16/9/23.
 */
public abstract class ArbAdapter<T> {

    public abstract void setData(T obj);

    public abstract String[] getHeader1();
    public abstract String[] getHeader2();

    public abstract int getTableRowCount1();
    public abstract int getTableColCount1(int rowIndex);

    public abstract int getTableRowCount2();
    public abstract int getTableColCount2(int rowIndex);

    public abstract void setTableName(String str1, String str2);

    public abstract Object getRowItem1(int rowIndex);
    public abstract String getColItem1(int rowIndex, int colIndex);

    public abstract Object getRowItem2(int rowIndex);
    public abstract String getColItem2(int rowIndex, int colIndex);
}
