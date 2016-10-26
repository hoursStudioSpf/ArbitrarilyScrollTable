package com.spf.arb.data;




import java.io.Serializable;

/**
 * Description: student entity
 * Author: ShiPeifeng
 * Date: 16/9/30.
 */
public class StudentsEntity implements Serializable, Comparable, Cloneable{


    public String name = "张三";
    public String data1 = "null";
    public String data2 = "100";
    public String data3 = "--";
    public String data4 = "9";
    public String data5 = "null";
    public String data6 = "test";
    public String data7 = "8";
    public String data8 = "111";
    public String data9 = "1024";
    public String data10 = "-1";
    public String data11 = "0";
    public String data12 = "aaaa";
    public String data13 = "bbb";
    public String data14 = "ccc";

    public StudentsEntity() {
        data1 = "" + (int)(Math.random() * 100);
        data2 = "" + (int)(Math.random() * 100);
        data3 = "" + (int)(Math.random() * 100);
        data4 = "" + (int)(Math.random() * 100);
        data5 = "" + (int)(Math.random() * 100);
        data6 = "" + (int)(Math.random() * 100);
        data7 = "" + (int)(Math.random() * 100);
        data8 = "" + (int)(Math.random() * 100);
        data9 = "" + (int)(Math.random() * 100);
        data10 = "" + (int)(Math.random() * 100);
        data11 = "" + (int)(Math.random() * 100);
        data12 = "" + (int)(Math.random() * 100);
        data13 = "" + (int)(Math.random() * 100);
        data14 = "" + (int)(Math.random() * 100);
        generateAry();
    }

    public String[] dataAry;

    public void generateAry() {
        dataAry = new String[]{data1, data2, data3, data4, data5, data6, data7,
                data8, data9, data10, data11, data12, data13, data14};
    }

    @Override
    public int compareTo(Object another) {
        String data1 = this.data1;
        String data1Another = ((StudentsEntity)another).data1;
        return data1.compareTo(data1Another);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
