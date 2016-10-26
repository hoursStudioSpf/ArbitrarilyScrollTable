package com.spf.arb.data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: Data list of students
 * Author: ShiPeifeng
 * Date: 16/9/30.
 */
public class StudentsList implements Serializable{

    /** 初始集合 */
    public ArrayList<StudentsEntity> studentsList1;
    public ArrayList<StudentsEntity> studentsList2;

    /** title 缩写、中文键值对 */
    public Map<String, String> keysMap;

    public StudentsList() {
        studentsList1 = new ArrayList<>();
        studentsList2 = new ArrayList<>();
        StudentsEntity s1 = new StudentsEntity();
        s1.name = "A";
        s1.data1 = "9";
        s1.data2 = "8";
        s1.data3 = "7";
        s1.data4 = "6";
        s1.data5 = "5";
        s1.data6 = "4";
        s1.data7 = "3";
        s1.data8 = "2";
        s1.data9 = "1";
        s1.data10 = "0";
        s1.data11 = "0";
        s1.data12 = "0";
        s1.data13 = "0";
        s1.data14 = "0";
        s1.generateAry();
        StudentsEntity s2 = new StudentsEntity();
        s2.name = "B";
        s2.data1 = "9";
        s2.data2 = "8";
        s2.data3 = "7";
        s2.data4 = "6";
        s2.data5 = "5";
        s2.data6 = "4";
        s2.data7 = "3";
        s2.data8 = "2";
        s2.data9 = "1";
        s2.data10 = "0";
        s2.data11 = "0";
        s2.data12 = "0";
        s2.data13 = "0";
        s2.data14 = "0";
        s2.generateAry();
        StudentsEntity s3 = new StudentsEntity();
        s3.name = "C";
        s3.data1 = "9";
        s3.data2 = "8";
        s3.data3 = "7";
        s3.data4 = "6";
        s3.data5 = "5";
        s3.data6 = "4";
        s3.data7 = "3";
        s3.data8 = "2";
        s3.data9 = "1";
        s3.data10 = "0";
        s3.data11 = "0";
        s3.data12 = "0";
        s3.data13 = "0";
        s3.data14 = "0";
        s3.generateAry();
        StudentsEntity s4 = new StudentsEntity();
        s4.name="D";
        StudentsEntity s5 = new StudentsEntity();
        s5.name="E";
        StudentsEntity s6 = new StudentsEntity();
        s6.name="F";
        StudentsEntity s7 = new StudentsEntity();
        s7.name="G";
        StudentsEntity s8 = new StudentsEntity();
        s8.name="H";
        StudentsEntity s9 = new StudentsEntity();
        s9.name="I";
        StudentsEntity s10 = new StudentsEntity();
        s10.name="J";
        StudentsEntity s11 = new StudentsEntity();
        s11.name="K";
        StudentsEntity s12 = new StudentsEntity();
        s12.name="L";
        StudentsEntity s13 = new StudentsEntity();
        s13.name="M";
        StudentsEntity s14 = new StudentsEntity();
        s14.name="N";
        StudentsEntity s15 = new StudentsEntity();
        s15.name="O";
        StudentsEntity s16 = new StudentsEntity();
        s16.name="P";

        studentsList1.add(s1);
        studentsList1.add(s2);
        studentsList1.add(s3);
        studentsList1.add(s4);
        studentsList1.add(s5);
        studentsList1.add(s6);
        studentsList1.add(s7);
        studentsList1.add(s8);
        studentsList2.add(s9);
        studentsList2.add(s10);
        studentsList2.add(s11);
        studentsList2.add(s12);
        studentsList2.add(s13);
        studentsList2.add(s14);
        studentsList2.add(s15);
        studentsList2.add(s16);
        for (int i = 0; i < 10; i++) {
            try {
                studentsList2.add((StudentsEntity) s16.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }


        keysMap = new LinkedHashMap<>();
        keysMap.put("1", "学号");
        keysMap.put("2", "性别");
        keysMap.put("3", "身高");
        keysMap.put("4", "体重");
        keysMap.put("5", "三围");
        keysMap.put("6", "这是超长列");
        keysMap.put("7", "短");
        keysMap.put("8", "人生观");
        keysMap.put("9", "价值观");
        keysMap.put("10", "世界观");
        keysMap.put("11", "100米");
        keysMap.put("12", "属性A");
        keysMap.put("13", "属性B");
        keysMap.put("14", "属性C");
    }

    public SortIndex sortByKey(int keyIndex) {

        ArrayList<StudentsEntity> fstList = (ArrayList<StudentsEntity>) studentsList1.clone();
        ArrayList<StudentsEntity> secList = (ArrayList<StudentsEntity>) studentsList2.clone();
        switch (keyIndex) {
            case 1:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data1());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data1());
                }
                break;
            case 2:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data2());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data2());
                }
                break;
            case 3:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data3());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data3());
                }
                break;
            case 4:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data4());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data4());
                }
                break;
            case 5:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data5());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data5());
                }
                break;
            case 6:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data6());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data6());
                }
                break;
            case 7:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data7());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data7());
                }
                break;
            case 8:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data8());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data8());
                }
                break;
            case 9:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data9());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data9());
                }
                break;
            case 10:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data10());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data10());
                }
                break;
            case 11:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data11());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data11());
                }
                break;
            case 12:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data12());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data12());
                }
                break;
            case 13:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data13());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data13());
                }
                break;
            case 14:
                if (null != studentsList1) {
                    Collections.sort(studentsList1, new StudentComparator.Comparator_data14());
                }
                if (null != studentsList2) {
                    Collections.sort(studentsList2, new StudentComparator.Comparator_data14());
                }
                break;
        }

        int[] fstChangeAry = new int[studentsList1.size()];
        for (int i = 0; i < studentsList1.size(); i++) {
            for (int j = 0; j < fstList.size(); j++) {
                if (fstList.get(j).equals(studentsList1.get(i))) {
                    fstChangeAry[i] = j;
                }
            }
        }

        int[] secChangeAry = new int[studentsList2.size()];
        for (int i = 0; i < studentsList2.size(); i++) {
            for (int j = 0; j < secList.size(); j++) {
                if (secList.get(j).equals(studentsList2.get(i))) {
                    secChangeAry[i] = j;
                }
            }
        }

        SortIndex sortIndex = new SortIndex();
        sortIndex.fstIndex = fstChangeAry;
        sortIndex.secIndex = secChangeAry;
        return sortIndex;
    }


    public static class SortIndex{
        public int[] fstIndex;
        public int[] secIndex;
    }
}
