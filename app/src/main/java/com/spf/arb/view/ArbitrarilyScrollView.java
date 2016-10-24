package com.spf.arb.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.spf.arb.R;


/**
 * Description: 自定义横竖滑动、固定表头的表格
 * Author: ShiPeifeng
 * Date: 16/9/12.
 */
public class ArbitrarilyScrollView extends FrameLayout implements View.OnClickListener{

    Context mContext;
    LayoutInflater mInflater;

    private int tableCount = 2;

    //TODO can be setting
    private float horHeaderHeight_1 = 100f;
    private float horHeaderHeight_2 = 100f;

    private float table1Top = 0.0f;
    private float table1TopAddHeader = 0.0f;

    private float table1BottomCutHeader = 0.0f;
    private float table1Bottom = 0.0f;

    private float table2Top = 0.0f;
    private float table2TopAddHeader = 0.0f;

    private final int TABLE_NAME_INDEX_1 = -0x0011;
    private int TABLE_NAME_INDEX_2 = -0x0111;


    ArbTextView tableName1 = null;
    ArbTextView tableName2 = null;

    ArbHorScroller headerScroller1;
    ArbHorScroller headerScroller2;
    ArbHorScroller horScroller;

    ArbLinearLayout headerLinear;

    ArbLinearLayout horLinear1;
    ArbLinearLayout hideHorLinear1;
    ArbLinearLayout horLinear2;
    ArbLinearLayout hideHorLinear2;

    ViewGroup.LayoutParams verticalLp = new ViewGroup.LayoutParams(200, 101);
    ViewGroup.LayoutParams verticalBlockLp = new ViewGroup.LayoutParams(200, 101);
    ViewGroup.LayoutParams horTitleLp = new ViewGroup.LayoutParams(200, 100);

    //Vertical Header layout
    ArbLinearLayout verticalLinear;
    ArbTextView verticalHeaderText;

    private int sortColumnIndex = 1;

    private OnHorHeaderClickListener horHeaderClick;
    private OnTableNameClickListener tNameClick;
    private OnRowClickListener rowClick;

    int headerViewHeight;
    View headerView;
    LinearLayout.LayoutParams headerViewLp;

    String[] header1 = {
            "一班","年龄","性别","身高","体重","三围","爱好","语文","数学","英语","政治","物理","生物",
            "化学","历史"
    };
    String[] header2 = {
            "三班","年龄","性别","身高","体重","三围","爱好","语文","数学","英语","政治","物理","生物",
            "化学","历史"
    };

    String[][] table1 = {
            {"A","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"B","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"C","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"D","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"E","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"F","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"G","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"H","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"I","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"J","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"K","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"L","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"M","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"N","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"O","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"P","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"Q","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"R","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"S","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"T","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"U","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"V","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"W","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"X","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"Y","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"Z","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"@","A0","1","2","3","4","5","6","7","8","9","10","11","12","13"}
    };

    String[][] table2 = {
            {"a","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"b","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"c","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"d","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"e","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"f","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"g","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"h","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"i","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"j","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"k","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"l","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"m","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"n","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"o","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"p","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"q","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"r","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"s","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"t","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"u","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"v","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"w","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"x","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"y","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"z","0","1","2","3","4","5","6","7","8","9","10","11","12","13"},
            {"@","0","1","2","3","4","5","6","7","8","9","10","11","12","13"}
    };


    public ArbitrarilyScrollView(Context context) {
        super(context);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public ArbitrarilyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }


    ArbHorScroller.OnScrollListener horScroller1Listener = new ArbHorScroller.OnScrollListener() {
        @Override
        public void onScroll(int l, int t, int oldl, int oldt) {
            horScroller.setOnScrollListener(null);
            headerScroller2.setOnScrollListener(null);
            horScroller.smoothScrollTo(l, t);
            headerScroller2.smoothScrollTo(l, t);
            horScroller.setOnScrollListener(dataScrollerListener);
            headerScroller2.setOnScrollListener(horScroller2Listener);
        }
    };



    ArbHorScroller.OnScrollListener horScroller2Listener = new ArbHorScroller.OnScrollListener() {
        @Override
        public void onScroll(int l, int t, int oldl, int oldt) {
            horScroller.setOnScrollListener(null);
            headerScroller1.setOnScrollListener(null);
            horScroller.smoothScrollTo(l, t);
            headerScroller1.smoothScrollTo(l, t);
            horScroller.setOnScrollListener(dataScrollerListener);
            headerScroller1.setOnScrollListener(horScroller1Listener);
        }
    };



    ArbHorScroller.OnScrollListener dataScrollerListener = new ArbHorScroller.OnScrollListener() {
        @Override
        public void onScroll(int l, int t, int oldl, int oldt) {
            headerScroller1.setOnScrollListener(null);
            headerScroller2.setOnScrollListener(null);
            headerScroller1.smoothScrollTo(l, t);
            headerScroller2.smoothScrollTo(l, t);
            headerScroller1.setOnScrollListener(horScroller1Listener);
            headerScroller2.setOnScrollListener(horScroller2Listener);
        }
    };
    //TODO call outside / set Data
    public void init() {
        ArbVerticalScroller verticalScroller = (ArbVerticalScroller) mInflater.inflate(R.layout.arb_scroll, null);
        horScroller = (ArbHorScroller) verticalScroller.findViewById(R.id.hor_scroller);
        headerLinear = (ArbLinearLayout) verticalScroller.findViewById(R.id.header);

        if (null != headerLinear && null != headerView) {
            headerLinear.addViewInLayout(headerView, -1, headerViewLp);
        }

        ArbFrameLayout innerFrame = (ArbFrameLayout) verticalScroller.findViewById(R.id.arb_root_frame);
        ArbLinearLayout innerLinear = (ArbLinearLayout) verticalScroller.findViewById(R.id.arb_root_linear);
        ArbTableLayout tableLayout_1 = new ArbTableLayout(mContext);
        ArbTableLayout tableLayout_2 = new ArbTableLayout(mContext);
        ArbTableRow tableRow1 = null;
        TextView tv1;

        verticalLinear = new ArbLinearLayout(mContext);
        verticalLinear.setOrientation(LinearLayout.VERTICAL);

        //Horizontal Header layout
        headerScroller1 = new ArbHorScroller(mContext);
        headerScroller1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        headerScroller1.setHorizontalScrollBarEnabled(false);

        headerScroller2 = new ArbHorScroller(mContext);
        headerScroller2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        headerScroller2.setHorizontalScrollBarEnabled(false);

        horLinear1 = new ArbLinearLayout(mContext);
        horLinear1.setOrientation(LinearLayout.HORIZONTAL);
        hideHorLinear1 = new ArbLinearLayout(mContext);
        hideHorLinear1.setOrientation(LinearLayout.HORIZONTAL);

        horLinear2 = new ArbLinearLayout(mContext);
        horLinear2.setOrientation(LinearLayout.HORIZONTAL);
        hideHorLinear2 = new ArbLinearLayout(mContext);
        hideHorLinear2.setOrientation(LinearLayout.HORIZONTAL);

        ArbTextView horHeaderText;
        ArbTextView hideHorHeaderText;

        //The horizontal header of the first table
        for (int i = 0; i < header1.length; i++) {

            horHeaderText = new ArbTextView(mContext);
            horHeaderText.getText().setGravity(Gravity.CENTER);
            horHeaderText.setBgColor(Color.DKGRAY, Color.GRAY);
            horHeaderText.getText().setText(header1[i]);
            if (i == 1) {
                horHeaderText.setBtmDrawable(R.drawable.group_down, 20, Color.parseColor("#ff00ff"));
            }
            horHeaderText.generate();
            Tag horHeaderTag = new Tag();
            horHeaderTag.tableIndex = 0;//The first table
            horHeaderTag.rowIndex = 0;
            horHeaderTag.colIndex = i;
            horHeaderTag.name = header1[i];
            horHeaderText.setTag(horHeaderTag);
            horHeaderText.setOnClickListener(this);
            horLinear1.addViewInLayout(horHeaderText, -1, horTitleLp);

            hideHorHeaderText = new ArbTextView(mContext);
            hideHorHeaderText.getText().setGravity(Gravity.CENTER);
            hideHorHeaderText.setBgColor(Color.DKGRAY, Color.GRAY);
            hideHorHeaderText.getText().setText(header1[i]);
            horHeaderText.getText().setText(header1[i]);
            if (i == 1) {
                hideHorHeaderText.setBtmDrawable(R.drawable.group_down, 20, Color.parseColor("#ff00ff"));
            }
            hideHorHeaderText.generate();
            Tag hideHorHeaderTag = new Tag();
            hideHorHeaderTag.tableIndex = 0;//The first table
            hideHorHeaderTag.rowIndex = 0;
            hideHorHeaderTag.colIndex = i;
            hideHorHeaderTag.name = header1[i];
            hideHorHeaderText.setTag(hideHorHeaderTag);
            hideHorHeaderText.setOnClickListener(this);
            hideHorLinear1.addViewInLayout(hideHorHeaderText, -1, horTitleLp);
        }
        headerScroller1.addViewInLayout(hideHorLinear1, -1, hideHorLinear1.getLayoutParams());
        innerLinear.addViewInLayout(horLinear1, -1, horLinear1.getLayoutParams());

        if (header1.length > 0) {
            ArbTextView verticalText = new ArbTextView(mContext);
            verticalText.getText().setGravity(Gravity.CENTER);
            verticalText.setBgColor(Color.GRAY, Color.GRAY);
            verticalText.setOuterPadding(0, 0, 1, 0);
            verticalText.setBtmCorBlock(Color.RED, 10);
            verticalText.getText().setText(header1[0]);
            verticalText.generate();
            Tag verticalTag = new Tag();
            verticalTag.tableIndex = 0;
            verticalTag.colIndex = verticalTag.rowIndex = TABLE_NAME_INDEX_1;
            verticalTag.name = header1[0];
            verticalText.setTag(verticalTag);
            verticalText.setOnClickListener(this);
            verticalLinear.addViewInLayout(verticalText, -1, verticalLp);

            tableName1 = new ArbTextView(mContext);
            tableName1.setWH(200, 100);
            tableName1.setBgColor(Color.DKGRAY, Color.GRAY);
            tableName1.setOuterPadding(0, 0, 1, 0);
            tableName1.setBtmCorBlock(Color.RED, 10);
            tableName1.getText().setText(header1[0]);
            tableName1.getText().setGravity(Gravity.CENTER);
            tableName1.generate();

            Tag tableName1Tag = new Tag();
            tableName1Tag.tableIndex = 0;
            tableName1Tag.rowIndex = tableName1Tag.colIndex = TABLE_NAME_INDEX_1;
            tableName1Tag.name = header1[0];
            tableName1.setTag(tableName1Tag);
            tableName1.setVisibility(INVISIBLE);
            tableName1.setOnClickListener(this);
        }


        Tag tableRowTag;
        Tag verticalHeaderTag;
        for (int i = 0; i < table1.length; i++) {

            tableRow1 = new ArbTableRow(mContext);
            tableRow1.setBackgroundColor(Color.GRAY);
            tableRow1.setPadding(0, 0, 0, 1);

            for (int j = 0; j < table1[i].length; j++) {
                tv1 = new TextView(mContext);
                tv1.setWidth(200);
                tv1.setHeight(100);
                tv1.setBackgroundColor(Color.WHITE);
                tv1.setGravity(Gravity.CENTER);
                tv1.setText(table1[i][j]);
                tableRow1.addViewInLayout(tv1, -1, new TableRow.LayoutParams(200, 100));

                if (0 == j) {
                    verticalHeaderText = new ArbTextView(mContext);
                    verticalHeaderText.getText().setGravity(Gravity.CENTER);
                    verticalHeaderText.setBgColor(Color.DKGRAY, Color.WHITE);
                    verticalHeaderText.setOuterPadding(0, 0, 1, 1);
                    verticalHeaderText.getText().setText(table1[i][j]);
                    verticalHeaderText.generate();
                    verticalHeaderTag = new Tag();
                    verticalHeaderTag.tableIndex = 0;
                    verticalHeaderTag.name = table1[i][j];
                    verticalHeaderTag.colIndex = 0;
                    verticalHeaderTag.rowIndex = i;
                    verticalHeaderText.setTag(verticalHeaderTag);
                    verticalHeaderText.setOnClickListener(this);
                    verticalLinear.addViewInLayout(verticalHeaderText, -1, verticalLp);
                }
            }
            tableRowTag = new Tag();
            tableRowTag.tableIndex = 0;
            tableRowTag.colIndex = 0;
            tableRowTag.rowIndex = i;
            tableRowTag.name = "row";
            tableRow1.setTag(tableRowTag);
            tableRow1.setOnClickListener(this);
            tableLayout_1.addViewInLayout(tableRow1, -1, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        }
//        innerLinear.addViewInLayout(tableLayout_1, -1, tableLayout_1.getLayoutParams());
        innerLinear.addViewInLayout(tableLayout_1, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        View blankView = new View(mContext);
        blankView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 51));
        blankView.setBackgroundColor(Color.DKGRAY);
        innerLinear.addViewInLayout(blankView, -1, blankView.getLayoutParams());

        View blankVerticalHeaderView = new View(mContext);
        blankVerticalHeaderView.setLayoutParams(new ViewGroup.LayoutParams(200, 50));
        blankVerticalHeaderView.setBackgroundColor(Color.DKGRAY);
        verticalLinear.addViewInLayout(blankVerticalHeaderView, -1, blankVerticalHeaderView.getLayoutParams());

        Tag horHeaderTag2;
        Tag hideHorHeaderTag2;
        //The horizontal header of the first table
        for (int i = 0; i < header2.length; i++) {

            horHeaderText = new ArbTextView(mContext);
            horHeaderText.getText().setGravity(Gravity.CENTER);
            horHeaderText.setBgColor(Color.DKGRAY, Color.GRAY);
            if (i == 1) {
                horHeaderText.setBtmDrawable(R.drawable.group_down, 20, Color.parseColor("#00ffff"));
            }
            horHeaderText.getText().setText(header2[i]);
            horHeaderText.generate();
            horHeaderTag2 = new Tag();
            //set row index 0
            horHeaderTag2.rowIndex = 0;
            horHeaderTag2.colIndex = i;
            horHeaderTag2.name = header2[i];
            horHeaderTag2.tableIndex = 1;
            horHeaderText.setTag(horHeaderTag2);
            horHeaderText.setOnClickListener(this);
            horLinear2.addViewInLayout(horHeaderText, -1, horTitleLp);

            hideHorHeaderText = new ArbTextView(mContext);
            hideHorHeaderText.getText().setGravity(Gravity.CENTER);
            hideHorHeaderText.setBgColor(Color.DKGRAY, Color.GRAY);
            if (i == 1) {
                hideHorHeaderText.setBtmDrawable(R.drawable.group_down, 20, Color.parseColor("#00ffff"));
            }
            hideHorHeaderText.getText().setText(header2[i]);
            hideHorHeaderText.generate();
            hideHorHeaderTag2 = new Tag();
            //set row index 0
            hideHorHeaderTag2.rowIndex = 0;
            hideHorHeaderTag2.colIndex = i;
            hideHorHeaderTag2.name = header2[i];
            hideHorHeaderTag2.tableIndex = 1;
            hideHorHeaderText.setTag(hideHorHeaderTag2);
            hideHorHeaderText.setOnClickListener(this);
            hideHorLinear2.addViewInLayout(hideHorHeaderText, -1, horTitleLp);
        }
        headerScroller2.addViewInLayout(hideHorLinear2, -1, hideHorLinear2.getLayoutParams());
        innerLinear.addViewInLayout(horLinear2, -1, horLinear2.getLayoutParams());

        if (header2.length > 0) {

            ArbTextView verticalText = new ArbTextView(mContext);
            verticalText.getText().setGravity(Gravity.CENTER);
            verticalText.setBgColor(Color.DKGRAY, Color.GRAY);
            verticalText.setOuterPadding(0, 0, 1, 0);
            verticalText.setBtmCorBlock(Color.BLUE, 10);
            verticalText.getText().setText(header2[0]);
            verticalText.generate();
            Tag verticalTag = new Tag();
            verticalTag.rowIndex = verticalTag.colIndex = TABLE_NAME_INDEX_2;
            verticalTag.name = header2[0];
            verticalTag.tableIndex = 1;
            verticalText.setTag(verticalTag);
            verticalText.setOnClickListener(this);
            verticalLinear.addViewInLayout(verticalText, -1, verticalLp);

            tableName2 = new ArbTextView(mContext);
            tableName2.setBgColor(Color.DKGRAY, Color.GRAY);
            tableName2.setBtmCorBlock(Color.BLUE, 10);
            tableName2.setOuterPadding(0, 0, 1, 0);
            tableName2.getText().setText(header2[0]);
            tableName2.getText().setGravity(Gravity.CENTER);
            tableName2.generate();

            Tag tableName2Tag = new Tag();
            tableName2Tag.tableIndex = 1;
            tableName2Tag.rowIndex = tableName2Tag.colIndex = TABLE_NAME_INDEX_2;
            tableName2Tag.name = header2[0];
            tableName2.setTag(tableName2Tag);
            tableName2.setOnClickListener(this);
        }

        ArbTableRow tableRow2 = null;
        TextView tv2;
        Tag tableRowTag2;
        Tag verticalHeaderTag2;
        for (int i = 0; i < table2.length; i++) {
            tableRow2 = new ArbTableRow(mContext);
            //TODO bg setting
            for (int j = 0; j < table2[i].length; j++) {
                tv2 = new TextView(mContext);
                tv2.setWidth(200);
                tv2.setHeight(100);
                tv2.setBackgroundColor(Color.WHITE);
                tv2.setGravity(Gravity.CENTER);
                tv2.setText(table2[i][j]);
                tableRow2.addViewInLayout(tv2, -1, new TableRow.LayoutParams(200, 100));
                if (0 == j) {
                    verticalHeaderText = new ArbTextView(mContext);
                    verticalHeaderText.getText().setGravity(Gravity.CENTER);
                    verticalHeaderText.setBgColor(Color.DKGRAY, Color.WHITE);
                    verticalHeaderText.setOuterPadding(0, 0, 1, 1);
                    verticalHeaderText.getText().setText(table2[i][j]);
                    verticalHeaderText.generate();
                    verticalHeaderTag2 = new Tag();
                    verticalHeaderTag2.tableIndex = 1;
                    verticalHeaderTag2.name = table2[i][j];
                    verticalHeaderTag2.rowIndex = i;
                    verticalHeaderTag2.colIndex = 0;
                    verticalHeaderText.setTag(verticalHeaderTag2);
                    verticalHeaderText.setOnClickListener(this);
                    verticalLinear.addViewInLayout(verticalHeaderText, -1, verticalLp);
                }
            }
            tableRow2.setBackgroundColor(Color.DKGRAY);
            tableRow2.setPadding(0, 0, 0, 1);
            tableRowTag2 = new Tag();
            tableRowTag2.tableIndex = 0;
            tableRowTag2.colIndex = 0;
            tableRowTag2.rowIndex = i;
            tableRowTag2.name = "row";
            tableRow2.setTag(tableRowTag2);
            tableRow2.setOnClickListener(this);
            tableLayout_2.addViewInLayout(tableRow2, -1, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
        innerLinear.addViewInLayout(tableLayout_2, -1, tableLayout_2.getLayoutParams());
        innerFrame.addViewInLayout(verticalLinear, -1, verticalLinear.getLayoutParams());

        headerScroller1.setOnScrollListener(horScroller1Listener);
        headerScroller1.setVisibility(INVISIBLE);

        this.addViewInLayout(verticalScroller, -1, verticalScroller.getLayoutParams());
//        tableRow1.requestLayout();
//        tableLayout_1.requestLayout();
//        hideHorLinear2.requestLayout();
//        horLinear2.requestLayout();
//        tableRow2.requestLayout();
//        tableLayout_2.requestLayout();
//        innerLinear.requestLayout();
//        verticalLinear.requestLayout();
//        innerFrame.requestLayout();
//        hideHorLinear1.requestLayout();
//        horLinear1.requestLayout();
//        headerScroller1.requestLayout();
//        headerScroller2.requestLayout();
//        requestLayout();
        this.addViewInLayout(headerScroller1, -1, headerScroller1.getLayoutParams());
        this.addViewInLayout(headerScroller2, -1, headerScroller2.getLayoutParams());

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(200, 100);

        if (null != tableName1) {
            this.addViewInLayout(tableName1, -1, lp);
        }
        if (null != tableName2) {
            this.addViewInLayout(tableName2, -1, lp);
            tableName2.setVisibility(INVISIBLE);
        }

        headerScroller2.setOnScrollListener(horScroller2Listener);
        headerScroller2.setVisibility(INVISIBLE);

        horScroller.setOnScrollListener(dataScrollerListener);

        calculateTableBottoms();


        verticalScroller.setOnScrollListener(verticalScrollListener);
    }

    ArbVerticalScroller.OnScrollListener verticalScrollListener = new ArbVerticalScroller.OnScrollListener() {
        @Override
        public void onScroll(int l, int t, int oldl, int oldt) {

            if (t < table1Top) {
                if (VISIBLE == headerScroller1.getVisibility()) {
                    tableName1.setVisibility(INVISIBLE);
                    headerScroller1.setVisibility(INVISIBLE);
                }
            } else {
                if (INVISIBLE == headerScroller1.getVisibility()) {
                    tableName1.setVisibility(VISIBLE);
                    headerScroller1.setVisibility(VISIBLE);
                }
            }


            if (t >= table1BottomCutHeader) {
                if (null != tableName1) {
                    if (t <= table1Bottom) {
                        float offset = table1BottomCutHeader - t;// this offset here must be negative
                        tableName1.layout(0, (int) offset, tableName1.getWidth(), (int) (tableName1.getHeight() + offset));
                        headerScroller1.layout(0, (int) offset, headerScroller1.getWidth(), (int) (headerScroller1.getHeight() + offset));
                    }
                }
            } else {
                if (tableName1.getY() != 0) {
                    tableName1.layout(0, 0, tableName1.getWidth(), tableName1.getHeight());
                    headerScroller1.layout(0, 0, headerScroller1.getWidth(), headerScroller1.getHeight());
                }
            }

            if (t >= table2Top) {
                if (null != tableName2) {
                    if (tableName2.getVisibility() == INVISIBLE) {
                        tableName2.setVisibility(VISIBLE);
                        headerScroller2.setVisibility(VISIBLE);
                    }
                }
            } else {
                if (tableName2.getVisibility() == VISIBLE) {
                    tableName2.setVisibility(INVISIBLE);
                    headerScroller2.setVisibility(INVISIBLE);
                }
            }
        }
    };


    public void addHeader(View header) {
        headerViewHeight = header.getLayoutParams().height;
        headerViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerViewHeight);
        headerView = header;
    }

    private void calculateTableBottoms() {

        table1Top = headerViewHeight;
        table1TopAddHeader = 101 + table1Top;

        table1Bottom = (table1.length + 1) * 101 + headerViewHeight;
        table1BottomCutHeader = table1Bottom - 101;

        table2Top = table1Bottom + 50;
        table2TopAddHeader = table2Top + 101;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //只支持MeasureSpec.EXACTLY形式布局 其他的暂时不支持
        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onClick(View v) {
        Tag clickTag = (Tag) v.getTag();
        Log.e("SPF", "clickTag--->" + clickTag.toString());
        if (v instanceof ArbTextView) {
            /** click table name */
            boolean isTableName1 = clickTag.tableIndex == 0 && clickTag.colIndex == TABLE_NAME_INDEX_1;
            boolean isTableName2 = clickTag.tableIndex == 1 && clickTag.colIndex == TABLE_NAME_INDEX_2;
            if (isTableName1 || isTableName2) {
                if (null != tNameClick) {
                    tNameClick.onTableNameClick(clickTag);
                    return;
                }
            }

            int clickColIndex = clickTag.colIndex;
            int clickRowIndex = clickTag.rowIndex;

            /** click horizontal header */
            if (clickColIndex > 0 && clickRowIndex == 0) {
                if (clickColIndex == sortColumnIndex) {//click at last column
                    return;
                }
                //hide icon of last column
                ((ArbTextView)horLinear1.getChildAt(sortColumnIndex)).switchBottomImg(false, Color.GRAY);
                ((ArbTextView)hideHorLinear1.getChildAt(sortColumnIndex)).switchBottomImg(false, Color.GRAY);
                ((ArbTextView)horLinear2.getChildAt(sortColumnIndex)).switchBottomImg(false, Color.GRAY);
                ((ArbTextView)hideHorLinear2.getChildAt(sortColumnIndex)).switchBottomImg(false, Color.GRAY);
                //sync column record
                sortColumnIndex = clickColIndex;
                //show icon of new column
                ((ArbTextView)horLinear1.getChildAt(clickColIndex)).switchBottomImg(true, R.drawable.group_down, 20, Color.parseColor("#ff00ff"));
                ((ArbTextView)hideHorLinear1.getChildAt(clickColIndex)).switchBottomImg(true, R.drawable.group_down, 20, Color.parseColor("#ff00ff"));
                ((ArbTextView)horLinear2.getChildAt(clickColIndex)).switchBottomImg(true, R.drawable.group_down, 20, Color.parseColor("#00ffff"));
                ((ArbTextView)hideHorLinear2.getChildAt(clickColIndex)).switchBottomImg(true, R.drawable.group_down, 20, Color.parseColor("#00ffff"));
                if (null != horHeaderClick) {
                    horHeaderClick.onHorHeaderClick(clickTag);
                }
                return;
            }

            /** click vertical header */
            if (clickRowIndex > 0 && clickColIndex == 0) {
                if (null != rowClick) {
                    rowClick.onRowClick(clickTag);
                }
            }
        }
    }

    class Tag{
        int tableIndex;
        int rowIndex;
        int colIndex;
        int pid;//player id
        int tid;//team id
        String name;

        @Override
        public String toString() {
            return "Tag{" +
                    "tableIndex=" + tableIndex +
                    ", rowIndex=" + rowIndex +
                    ", colIndex=" + colIndex +
                    ", pid=" + pid +
                    ", tid=" + tid +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public interface OnHorHeaderClickListener{
        void onHorHeaderClick(Tag tag);
    }

    public interface OnTableNameClickListener{
        void onTableNameClick(Tag tag);
    }

    public interface OnRowClickListener{
        void onRowClick(Tag tag);
    }
}
