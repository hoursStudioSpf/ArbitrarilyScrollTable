package com.spf.arb.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.spf.arb.R;
import com.spf.arb.data.StudentsList;
import static com.spf.arb.util.DisplayUtil.convertDIP2PX;


/**
 * Description: Customized table list, support scroll horizontally & vertically with the horizontal
 *          header & vertical header pinned.
 * Author: ShiPeifeng
 * Date: 16/9/12.
 */
public class ArbitrarilyScrollView extends FrameLayout implements View.OnClickListener, GestureDetector.OnGestureListener{

    Context mContext;
    LayoutInflater mInflater;

    private float table1Top = 0.0f;
    private float table1TopAddHeader = 0.0f;

    private float table1BottomCutHeader = 0.0f;
    private float table1Bottom = 0.0f;

    private float table2Top = 0.0f;
    private float table2TopAddHeader = 0.0f;

    private final int TABLE_NAME_INDEX_1 = -0x0011;
    private int TABLE_NAME_INDEX_2 = -0x0111;

    private ArbAdapter adapter;

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

    ViewGroup.LayoutParams verticalLp;
    ViewGroup.LayoutParams verticalBlockLp;
    ViewGroup.LayoutParams horTitleLp;
    ViewGroup.LayoutParams tableNameLp;

    //Vertical Header layout
    ArbLinearLayout verticalLinear;
    ArbTextView verticalHeaderText;

    ArbVerticalScroller verticalScroller;

    ArbFrameLayout innerFrame;

    ArbLinearLayout innerLinear;
    ArbTableLayout tableLayout_1;
    ArbTableLayout tableLayout_2;
    ArbTableRow tableRow1 = null;

    private int sortColumnIndex = 1;

    private OnHorHeaderClickListener horHeaderClick;
    private OnTableNameClickListener tNameClick;
    private OnRowClickListener rowClick;
    private OnLoadListener onLoad;

    int headerViewHeight;
    View headerView;
    LinearLayout.LayoutParams headerViewLp;

    public final String TAG = "ArbS";

    String[] header1;
    String[] header2;

    int vPaddingLeft;

    boolean titleNeedClick = true;

    boolean itemNeedClick = true;

    boolean needSort = true;

    float[] measuredTitleWidthAry;

    int lastX;
    int lastY;

    int leftMostEnd = 0;
    int rightMostEnd = 0;
    boolean isLeftMost = true;//初始化的时候 在最左端
    boolean isRightMost = false;
    int wholeViewWidth;

    GestureDetector gestureDetector;


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
            lastX = l;
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
            lastX = l;
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
            lastX = l;
            headerScroller1.setOnScrollListener(null);
            headerScroller2.setOnScrollListener(null);
            headerScroller1.smoothScrollTo(l, t);
            headerScroller2.smoothScrollTo(l, t);
            headerScroller1.setOnScrollListener(horScroller1Listener);
            headerScroller2.setOnScrollListener(horScroller2Listener);
            if (leftMostEnd == l) {
                isLeftMost = true;
            } else {
                isLeftMost = false;
            }
            if (rightMostEnd - wholeViewWidth == l) {
                isRightMost = true;
            } else {
                isRightMost = false;
            }

        }
    };

    public void setAdapter(ArbAdapter arbAdapter) {
        this.adapter = arbAdapter;
        adapter.mObserver = this;
        init(false);
    }

    public ArbAdapter getAdapter() {
        return adapter;
    }

    public void setVerticalHeaderPadding(int paddingLeft){
        this.vPaddingLeft = paddingLeft;
    }

    public void enableClick(boolean titleClickEnable, boolean itemClickEnable) {
        this.titleNeedClick = titleClickEnable;
        this.itemNeedClick = itemClickEnable;
    }

    public void enableSortTitle(boolean needSort) {
        this.needSort = needSort;
    }

    int hTitleCorRes;
    int itemCorRes1;
    int itemCorRes2;
    int blockCorRes;
    int specialCorRes1;
    int specialCorRes2;
    int dividerCorRes;

    public void setBackgroundCorRes(int hTitleCor, int itemCor1, int itemCor2, int blockCor,
                                    int specialCor1, int specialCor2, int divCor) {
        this.hTitleCorRes = hTitleCor;
        this.itemCorRes1 = itemCor1;
        this.itemCorRes2 = itemCor2;
        this.blockCorRes = blockCor;
        this.specialCorRes1 = specialCor1;
        this.specialCorRes2 = specialCor2;
        this.dividerCorRes = divCor;
    }

    int horTitleTxtCor;
    int itemTxtCor;
    int itemPlayingCor;
// TODO 正在场上打的球员颜色
//    todo (entity.on_court == 1)//NBA正在打

    public void setTxtCor(int horTitletTxtCor, int itemTxtCor, int itemPlayingCor) {
        this.horTitleTxtCor = horTitletTxtCor;
        this.itemTxtCor = itemTxtCor;
        this.itemPlayingCor = itemPlayingCor;
    }

    public void setTeamCor(int teamCor1, int teamCor2){
        this.teamCor1 = teamCor1;
        this.teamCor2 = teamCor2;
    }

    int teamCor1 = Color.TRANSPARENT;
    int teamCor2 = Color.TRANSPARENT;
    int teamSortCor1 = Color.TRANSPARENT;
    int teamSortCor2 = Color.TRANSPARENT;
    int teamSortDrawableId1 = -1;
    int teamSortDrawableId2 = -1;
    public void setTeamSpecials(int teamCor1, int teamCor2, int teamSortCor1, int teamSortCor2,
                                int teamSortDrawableId1, int teamSortDrawableId2) {
        this.teamCor1 = teamCor1;
        this.teamCor2 = teamCor2;
        this.teamSortCor1 = teamSortCor1;
        this.teamSortCor2 = teamSortCor2;
        this.teamSortDrawableId1 = teamSortDrawableId1;
        this.teamSortDrawableId2 = teamSortDrawableId2;
    }

    private void measureColWidth() {
        if (null == header1) {
            return;
        }
        Paint measurePaint = new Paint();
        measurePaint.setTextSize(convertDIP2PX(mContext, 12));
        measuredTitleWidthAry = new float[header1.length];
        int margin = convertDIP2PX(mContext, 15);//左右各留白7.5DP 共15DP
        for (int i = 0; i < header1.length; i++) {
            float width = measurePaint.measureText(header1[i]);
            measuredTitleWidthAry[i] = (width + margin) > 250 ? 250 : (width + margin);
        }
    }

    /**
     * @param reset 是否是刷新重置界面
     * */
    public void init(boolean reset) {
        gestureDetector = new GestureDetector(mContext, this);
        verticalLp = new ViewGroup.LayoutParams(adapter.vTitleWidth, adapter.vTitleHeight + 1);
        horTitleLp = new ViewGroup.LayoutParams(adapter.hTitleWidth, adapter.hTitleHeight);
        tableNameLp = new ViewGroup.LayoutParams(adapter.tableNameWidth, adapter.tableNameHeight);
        int sortHeight = convertDIP2PX(mContext, 7);

        if (null == adapter) {
            Log.e(TAG, "adapter null");
            return;
        }

        if (null != onLoad) {
            onLoad.onLoadStart();
        }

        verticalScroller = (ArbVerticalScroller) mInflater.inflate(R.layout.arb_scroll, null);
        horScroller = (ArbHorScroller) verticalScroller.findViewById(R.id.hor_scroller);
        headerLinear = (ArbLinearLayout) verticalScroller.findViewById(R.id.header);
        //TODO 处理滑动到边界的问题
//        horScroller.computeScroll();

        if (null != headerLinear && null != headerView) {
            headerLinear.addViewInLayout(headerView, -1, headerViewLp);
        }

        innerFrame = (ArbFrameLayout) verticalScroller.findViewById(R.id.arb_root_frame);
        innerLinear = (ArbLinearLayout) verticalScroller.findViewById(R.id.arb_root_linear);
        tableLayout_1 = new ArbTableLayout(mContext);
        tableLayout_2 = new ArbTableLayout(mContext);
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

        header1 = adapter.getHeader1();
        header2 = adapter.getHeader2();
        measureColWidth();
        if (null == header1 || null == header2) {
            return;
        }
        //The horizontal header of the first table
        for (int i = 0; i < header1.length; i++) {

            horHeaderText = new ArbTextView(mContext);
            horHeaderText.setBgColorRes(dividerCorRes, hTitleCorRes);
            horHeaderText.getText().setText(header1[i]);
            horHeaderText.getText().setTextColor(horTitleTxtCor);
            horHeaderText.getText().setTextSize(12);
            horHeaderText.setOuterPadding(0, 1, 0, 1);
            if (i == 1 && teamSortDrawableId1 > 0 && !reset) {
                horHeaderText.setBtmDrawable(teamSortDrawableId1, sortHeight, teamSortCor1);
            }
            horHeaderText.generate();
            if (titleNeedClick) {
                Tag horHeaderTag = new Tag();
                horHeaderTag.tableIndex = 0;//The first table
                horHeaderTag.rowIndex = 0;
                horHeaderTag.colIndex = i;
                horHeaderTag.name = header1[i];
                horHeaderText.setTag(horHeaderTag);
                horHeaderText.setOnClickListener(this);
            }
            if (i == 0 ) {//表名
                horHeaderText.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                horHeaderText.getText().setPadding(vPaddingLeft, 0, 0, 0);
                horHeaderText.setWH(adapter.tableNameWidth, adapter.tableNameHeight);
                horLinear1.addViewInLayout(horHeaderText, -1, tableNameLp);
            } else {//从index为1开始
                horHeaderText.getText().setGravity(Gravity.CENTER);
                horHeaderText.setWH((int) measuredTitleWidthAry[i], adapter.hTitleHeight);
                horLinear1.addViewInLayout(horHeaderText, -1,
                        new ViewGroup.LayoutParams((int) measuredTitleWidthAry[i], adapter.hTitleHeight));
        }

            hideHorHeaderText = new ArbTextView(mContext);
            hideHorHeaderText.setBgColorRes(dividerCorRes, hTitleCorRes);
            hideHorHeaderText.getText().setText(header1[i]);
            hideHorHeaderText.getText().setTextColor(horTitleTxtCor);
            hideHorHeaderText.getText().setTextSize(12);
            hideHorHeaderText.setOuterPadding(0, 1, 0, 1);
            if (i == 1 && teamSortDrawableId1 > 0 && !reset) {
                hideHorHeaderText.setBtmDrawable(teamSortDrawableId1, sortHeight, teamSortCor1);
            }
            hideHorHeaderText.generate();
            if (titleNeedClick) {
                Tag hideHorHeaderTag = new Tag();
                hideHorHeaderTag.tableIndex = 0;//The first table
                hideHorHeaderTag.rowIndex = 0;
                hideHorHeaderTag.colIndex = i;
                hideHorHeaderTag.name = header1[i];
                hideHorHeaderText.setTag(hideHorHeaderTag);
                hideHorHeaderText.setOnClickListener(this);
            }
            if (i == 0 ) {
                hideHorHeaderText.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                hideHorHeaderText.setWH(adapter.tableNameWidth, adapter.tableNameHeight);
                hideHorHeaderText.getText().setPadding(vPaddingLeft, 0, 0, 0);
                hideHorLinear1.addViewInLayout(hideHorHeaderText, -1, tableNameLp);
            } else {
                //从index为1开始
                hideHorHeaderText.getText().setGravity(Gravity.CENTER);
                hideHorHeaderText.setWH((int) measuredTitleWidthAry[i], adapter.hTitleHeight);
                hideHorLinear1.addViewInLayout(hideHorHeaderText, -1,
                        new ViewGroup.LayoutParams((int) measuredTitleWidthAry[i], adapter.hTitleHeight));
            }
        }
        headerScroller1.addViewInLayout(hideHorLinear1, -1, hideHorLinear1.getLayoutParams());
        innerLinear.addViewInLayout(horLinear1, -1, horLinear1.getLayoutParams());

        if (header1.length > 0) {
            ArbTextView verticalText = new ArbTextView(mContext);
            verticalText.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            verticalText.setBgColorRes(dividerCorRes, hTitleCorRes);
            verticalText.setOuterPadding(0, 1, 2, 1);
            verticalText.setBtmCorBlock(teamCor1, 10);
            verticalText.getText().setText(header1[0]);
            verticalText.getText().setTextColor(horTitleTxtCor);
            verticalText.getText().setTextSize(12);
            verticalText.getText().setPadding(vPaddingLeft, 0, 0, 0);
            verticalText.generate();
            if (titleNeedClick) {
                Tag verticalTag = new Tag();
                verticalTag.tableIndex = 0;
                verticalTag.colIndex = verticalTag.rowIndex = TABLE_NAME_INDEX_1;
                verticalTag.name = header1[0];
                verticalText.setTag(verticalTag);
                verticalText.setOnClickListener(this);
            }
            verticalText.setWH(adapter.tableNameWidth, adapter.tableNameHeight + 1);
            verticalLinear.addViewInLayout(verticalText, -1,
                    new RelativeLayout.LayoutParams(adapter.tableNameWidth, adapter.tableNameHeight));

            tableName1 = new ArbTextView(mContext);
            tableName1.setWH(adapter.tableNameWidth, adapter.tableNameHeight + 1);
            tableName1.setBgColorRes(dividerCorRes, hTitleCorRes);
            tableName1.setOuterPadding(1, 1, 2, 2);
            tableName1.setBtmCorBlock(teamCor1, 10);
            tableName1.getText().setText(header1[0]);
            tableName1.getText().setTextColor(horTitleTxtCor);
            tableName1.getText().setTextSize(12);
            tableName1.getText().setPadding(vPaddingLeft, 0, 0, 1);
            tableName1.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tableName1.generate();

            if (titleNeedClick) {
                Tag tableName1Tag = new Tag();
                tableName1Tag.tableIndex = 0;
                tableName1Tag.rowIndex = tableName1Tag.colIndex = TABLE_NAME_INDEX_1;
                tableName1Tag.name = header1[0];
                tableName1.setTag(tableName1Tag);
                tableName1.setOnClickListener(this);
            }
            tableName1.setVisibility(INVISIBLE);
        }


        Tag tableRowTag;
        Tag verticalHeaderTag;
        int table1Length = adapter.getTableRowCount1();
        String tableText;
        for (int i = 0; i < table1Length; i++) {

            tableRow1 = new ArbTableRow(mContext);
            tableRow1.setPadding(0, 0, 0, 1);

            for (int j = 0; j < adapter.getTableColCount1(i); j++) {
                tableText = adapter.getColItem1(i, j);
                tv1 = new TextView(mContext);
                tv1.setBackgroundResource(itemCorRes1);
                tv1.setText(tableText);

                if (i < adapter.table1DataSize && adapter.hightLight(0, i, j)) {
                    tv1.setTextColor(itemPlayingCor);
                } else {
                    tv1.setTextColor(itemTxtCor);
                }
                tv1.setTextSize(12);

                if (0 == j) {
                    verticalHeaderText = new ArbTextView(mContext);
                    verticalHeaderText.getText().setGravity(Gravity.CENTER);
                    verticalHeaderText.setBgColorRes(dividerCorRes, itemCorRes1);
                    verticalHeaderText.setOuterPadding(0, 0, 2, 1);
                    verticalHeaderText.getText().setText(tableText);
                    verticalHeaderText.getText().setTextSize(12);
                    if (i < adapter.table1DataSize && adapter.hightLight(0, i, j)) {
                        verticalHeaderText.getText().setTextColor(itemPlayingCor);
                    } else {
                        verticalHeaderText.getText().setTextColor(itemTxtCor);
                    }
                    verticalHeaderText.generate();
                    if (itemNeedClick) {
                        verticalHeaderTag = new Tag();
                        verticalHeaderTag.tableIndex = 0;
                        verticalHeaderTag.name = tableText;
                        verticalHeaderTag.colIndex = 0;
                        verticalHeaderTag.rowIndex = i;
                        if (i < adapter.table1DataSize) {
                            verticalHeaderTag.innerTag = adapter.getTag(0, i, j);
                            verticalHeaderText.setTag(verticalHeaderTag);
                            verticalHeaderText.setOnClickListener(this);
                        }
                    }
                    verticalHeaderText.setWH(adapter.vTitleWidth, adapter.vTitleHeight + 1);
                    verticalHeaderText.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    verticalHeaderText.getText().setPadding(vPaddingLeft, 0, 0, 0);
                    verticalLinear.addViewInLayout(verticalHeaderText, -1, verticalLp);

                    tv1.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    tv1.setWidth(adapter.vTitleWidth);
                    tv1.setHeight(adapter.vTitleHeight);
                    tv1.setPadding(vPaddingLeft, 0, 0, 0);
                    tableRow1.addViewInLayout(tv1, -1, new TableRow.LayoutParams(adapter.vTitleWidth, adapter.vTitleHeight));
                } else {
                    tv1.setGravity(Gravity.CENTER);
                    tv1.setHeight(adapter.itemTxtHeight);
                    tv1.setWidth((int) measuredTitleWidthAry[j]);
                    tableRow1.addViewInLayout(tv1, -1, new TableRow.LayoutParams((int) measuredTitleWidthAry[j], adapter.itemTxtHeight));
                }

                if (itemNeedClick) {
                    tableRowTag = new Tag();
                    tableRowTag.tableIndex = 0;
                    tableRowTag.colIndex = 0;
                    tableRowTag.rowIndex = i;
                    tableRowTag.name = "row";
                    if (i < adapter.table1DataSize) {
                        tableRowTag.innerTag = adapter.getTag(0, i, j);
                        tableRow1.setTag(tableRowTag);
                        tableRow1.setOnClickListener(this);
                    }
                }
            }
            tableRow1.setBackgroundResource(dividerCorRes);
            tableLayout_1.addViewInLayout(tableRow1, -1, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, adapter.itemTxtHeight));
        }
        innerLinear.addViewInLayout(tableLayout_1, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        View blankView = new View(mContext);
        blankView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, adapter.blankHeight));
        blankView.setBackgroundResource(blockCorRes);
        innerLinear.addViewInLayout(blankView, -1, blankView.getLayoutParams());

        View blankVerticalHeaderView = new View(mContext);
        blankVerticalHeaderView.setLayoutParams(new ViewGroup.LayoutParams(adapter.vTitleWidth, adapter.blankHeight));
        blankVerticalHeaderView.setBackgroundResource(blockCorRes);
        verticalLinear.addViewInLayout(blankVerticalHeaderView, -1, blankVerticalHeaderView.getLayoutParams());

        Tag horHeaderTag2;
        Tag hideHorHeaderTag2;
        //The horizontal header of the first table
        for (int i = 0; i < header2.length; i++) {

            horHeaderText = new ArbTextView(mContext);
            horHeaderText.setBgColorRes(dividerCorRes, hTitleCorRes);
            if (i == 1 && teamSortDrawableId2 > 0 && !reset) {
                horHeaderText.setBtmDrawable(teamSortDrawableId2, sortHeight, teamSortCor2);
            }
            horHeaderText.getText().setText(header2[i]);
            horHeaderText.getText().setTextColor(horTitleTxtCor);
            horHeaderText.getText().setTextSize(12);
            horHeaderText.setOuterPadding(0, 1, 0, 1);
            horHeaderText.generate();
            if (titleNeedClick) {
                horHeaderTag2 = new Tag();
                //set row index 0
                horHeaderTag2.rowIndex = 0;
                horHeaderTag2.colIndex = i;
                horHeaderTag2.name = header2[i];
                horHeaderTag2.tableIndex = 1;
                horHeaderText.setTag(horHeaderTag2);
                horHeaderText.setOnClickListener(this);
            }
            if (i == 0) {
                horHeaderText.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                horHeaderText.setWH(adapter.tableNameWidth, adapter.tableNameHeight);
                horHeaderText.getText().setPadding(vPaddingLeft, 1, 0, 0);
                horLinear2.addViewInLayout(horHeaderText, -1, tableNameLp);
            } else {
                horHeaderText.getText().setGravity(Gravity.CENTER);
                horHeaderText.getText().setPadding(0, 1, 0, 0);
                horHeaderText.setWH((int) measuredTitleWidthAry[i], adapter.hTitleHeight);
                horLinear2.addViewInLayout(horHeaderText, -1,
                        new ViewGroup.LayoutParams((int) measuredTitleWidthAry[i], adapter.hTitleHeight));
            }

            hideHorHeaderText = new ArbTextView(mContext);
            hideHorHeaderText.setBgColorRes(dividerCorRes, hTitleCorRes);
            if (i == 1 && teamSortDrawableId2 > 0 && !reset) {
                hideHorHeaderText.setBtmDrawable(teamSortDrawableId2, sortHeight, teamSortCor2);
            }
            hideHorHeaderText.getText().setText(header2[i]);
            hideHorHeaderText.getText().setTextColor(horTitleTxtCor);
            hideHorHeaderText.getText().setTextSize(12);
            hideHorHeaderText.setOuterPadding(0, 1, 0, 1);
            hideHorHeaderText.generate();
            if (titleNeedClick) {
                hideHorHeaderTag2 = new Tag();
                //set row index 0
                hideHorHeaderTag2.rowIndex = 0;
                hideHorHeaderTag2.colIndex = i;
                hideHorHeaderTag2.name = header2[i];
                hideHorHeaderTag2.tableIndex = 1;
                hideHorHeaderText.setTag(hideHorHeaderTag2);
                hideHorHeaderText.setOnClickListener(this);
            }
            if (i == 0) {
                hideHorHeaderText.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                hideHorHeaderText.setWH(adapter.tableNameWidth, adapter.tableNameHeight);
                hideHorHeaderText.getText().setPadding(vPaddingLeft, 1, 0, 0);
                hideHorLinear2.addViewInLayout(hideHorHeaderText, -1, tableNameLp);
            } else {
                hideHorHeaderText.getText().setGravity(Gravity.CENTER);
                hideHorHeaderText.getText().setPadding(0, 1, 0, 0);
                hideHorHeaderText.setWH((int) measuredTitleWidthAry[i], adapter.hTitleHeight);
                hideHorLinear2.addViewInLayout(hideHorHeaderText, -1,
                        new ViewGroup.LayoutParams((int) measuredTitleWidthAry[i], adapter.hTitleHeight));
            }
        }
        headerScroller2.addViewInLayout(hideHorLinear2, -1, hideHorLinear2.getLayoutParams());
        innerLinear.addViewInLayout(horLinear2, -1, horLinear2.getLayoutParams());

        if (header2.length > 0) {

            ArbTextView verticalText = new ArbTextView(mContext);
            verticalText.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            verticalText.setBgColorRes(dividerCorRes, hTitleCorRes);
            verticalText.setOuterPadding(0, 1, 2, 1);
            verticalText.setBtmCorBlock(teamCor2, 10);
            verticalText.getText().setText(header2[0]);
            verticalText.getText().setTextColor(horTitleTxtCor);
            verticalText.getText().setTextSize(12);
            verticalText.getText().setPadding(vPaddingLeft, 0, 0, 0);
            verticalText.setWH(adapter.tableNameWidth, adapter.tableNameHeight);
            verticalText.generate();

            if (titleNeedClick) {
                Tag verticalTag = new Tag();
                verticalTag.rowIndex = verticalTag.colIndex = TABLE_NAME_INDEX_2;
                verticalTag.name = header2[0];
                verticalTag.tableIndex = 1;
                verticalText.setTag(verticalTag);
                verticalText.setOnClickListener(this);
            }
            verticalLinear.addViewInLayout(verticalText, -1, tableNameLp);

            tableName2 = new ArbTextView(mContext);
            tableName2.setBgColorRes(dividerCorRes, hTitleCorRes);
            tableName2.setBtmCorBlock(teamCor2, 10);
            tableName2.setOuterPadding(1, 1, 2, 2);
            tableName2.getText().setText(header2[0]);
            tableName2.getText().setTextColor(horTitleTxtCor);
            tableName2.getText().setTextSize(12);
            tableName2.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tableName2.setWH(adapter.tableNameWidth, adapter.tableNameHeight);
            tableName2.getText().setPadding(vPaddingLeft, 0, 0, 0);
            tableName2.generate();

            if (titleNeedClick) {
                Tag tableName2Tag = new Tag();
                tableName2Tag.tableIndex = 1;
                tableName2Tag.rowIndex = tableName2Tag.colIndex = TABLE_NAME_INDEX_2;
                tableName2Tag.name = header2[0];
                tableName2.setTag(tableName2Tag);
                tableName2.setOnClickListener(this);
            }
        }

        ArbTableRow tableRow2 = null;
        TextView tv2;
        Tag tableRowTag2;
        Tag verticalHeaderTag2;
        int table2Length = adapter.getTableRowCount2();
        String tableText2;

        for (int i = 0; i < table2Length; i++) {
            tableRow2 = new ArbTableRow(mContext);
            //TODO bg setting
            for (int j = 0; j < adapter.getTableColCount2(i); j++) {
                tv2 = new TextView(mContext);
                tableText2 = adapter.getColItem2(i, j);
                tv2.setWidth(adapter.itemTxtWidth);
                tv2.setHeight(adapter.itemTxtHeight);
                tv2.setBackgroundResource(itemCorRes1);
                tv2.setText(tableText2);
                tv2.setTextColor(itemTxtCor);
                tv2.setTextSize(12);
                if (0 == j) {
                    verticalHeaderText = new ArbTextView(mContext);
                    verticalHeaderText.setBgColorRes(dividerCorRes, itemCorRes1);
                    verticalHeaderText.setBgColorRes(dividerCorRes, itemCorRes1);
                    verticalHeaderText.setOuterPadding(0, 0, 2, 1);
                    verticalHeaderText.getText().setText(tableText2);
                    verticalHeaderText.getText().setTextColor(itemTxtCor);
                    if (i < adapter.table2DataSize && adapter.hightLight(1, i, j)) {
                        verticalHeaderText.getText().setTextColor(itemPlayingCor);
                    } else {
                        verticalHeaderText.getText().setTextColor(itemTxtCor);
                    }
                    verticalHeaderText.getText().setTextSize(12);
                    verticalHeaderText.getText().setPadding(vPaddingLeft, 0, 0, 0);
                    verticalHeaderText.generate();
                    if (itemNeedClick) {
                        verticalHeaderTag2 = new Tag();
                        verticalHeaderTag2.tableIndex = 1;
                        verticalHeaderTag2.name = tableText2;
                        verticalHeaderTag2.rowIndex = i;
                        verticalHeaderTag2.colIndex = 0;
                        if (i < adapter.table2DataSize) {
                            verticalHeaderTag2.innerTag = adapter.getTag(1, i, j);
                            verticalHeaderText.setTag(verticalHeaderTag2);
                            verticalHeaderText.setOnClickListener(this);
                        }
                    }
                    verticalHeaderText.setWH(adapter.vTitleWidth, adapter.vTitleHeight + 1);
                    verticalHeaderText.getText().setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    verticalLinear.addViewInLayout(verticalHeaderText, -1, verticalLp);

                    tv2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    tv2.setWidth(adapter.vTitleWidth);
                    tv2.setWidth(adapter.vTitleHeight);
                    tv2.setPadding(vPaddingLeft, 0, 0, 0);
                    tableRow2.addViewInLayout(tv2, -1, new TableRow.LayoutParams(adapter.vTitleWidth, adapter.vTitleHeight));
                } else {
                    tv2.setGravity(Gravity.CENTER);
                    tv2.setHeight(adapter.itemTxtHeight);
                    tv2.setWidth((int) measuredTitleWidthAry[j]);
                    tableRow2.addViewInLayout(tv2, -1, new TableRow.LayoutParams((int) measuredTitleWidthAry[j], adapter.itemTxtHeight));
                }
                if (itemNeedClick) {
                    tableRowTag2 = new Tag();
                    tableRowTag2.tableIndex = 0;
                    tableRowTag2.colIndex = 0;
                    tableRowTag2.rowIndex = i;
                    tableRowTag2.name = "row";
                    if (i < adapter.table2DataSize) {
                        tableRowTag2.innerTag = adapter.getTag(1, i, j);
                        tableRow2.setTag(tableRowTag2);
                        tableRow2.setOnClickListener(this);
                    }
                }
            }
            tableRow2.setBackgroundResource(dividerCorRes);
            tableRow2.setPadding(0, 0, 0, 1);
            tableLayout_2.addViewInLayout(tableRow2, -1, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
        innerLinear.addViewInLayout(tableLayout_2, -1, tableLayout_2.getLayoutParams());
        innerFrame.addViewInLayout(verticalLinear, -1, verticalLinear.getLayoutParams());

        headerScroller1.setOnScrollListener(horScroller1Listener);
        headerScroller1.setVisibility(INVISIBLE);

        this.addViewInLayout(verticalScroller, -1, verticalScroller.getLayoutParams());
        this.addViewInLayout(headerScroller1, -1, headerScroller1.getLayoutParams());
        this.addViewInLayout(headerScroller2, -1, headerScroller2.getLayoutParams());

        if (null != tableName1) {
            this.addViewInLayout(tableName1, -1, tableNameLp);
        }
        if (null != tableName2) {
            this.addViewInLayout(tableName2, -1, tableNameLp);
            tableName2.setVisibility(INVISIBLE);
        }

        headerScroller2.setOnScrollListener(horScroller2Listener);
        headerScroller2.setVisibility(INVISIBLE);

        horScroller.setOnScrollListener(dataScrollerListener);

        calculateTableBottoms();


        verticalScroller.setOnScrollListener(verticalScrollListener);
        if (null != onLoad) {
            onLoad.onLoadComplete();
        }

        if (reset) {
            horScroller.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    horScroller.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    horScroller.smoothScrollTo(lastX, 0);
                    verticalScroller.smoothScrollTo(0, lastY);
                }
            });
        }

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wholeViewWidth = wm.getDefaultDisplay().getWidth();
        innerLinear.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                innerLinear.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                leftMostEnd = 0;
                rightMostEnd = innerLinear.getWidth();
            }
        });
    }

    ArbVerticalScroller.OnScrollListener verticalScrollListener = new ArbVerticalScroller.OnScrollListener() {
        @Override
        public void onScroll(int l, int t, int oldl, int oldt) {
            lastY = t;
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
                    } else {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                            if (tableName1.getY() != -tableName1.getHeight()) {
                                tableName1.layout(0, -tableName1.getHeight(), tableName1.getWidth(), 0);
                                headerScroller1.layout(0, -headerScroller1.getHeight(), headerScroller1.getWidth(), 0);
                            }
                        }
                    }
                }
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                    if (tableName1.getY() != 0) {
                        tableName1.layout(0, 0, tableName1.getWidth(), tableName1.getHeight());
                        headerScroller1.layout(0, 0, headerScroller1.getWidth(), headerScroller1.getHeight());
                    }
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
        Log.e("SPF", "headerViewHeight--->" + headerViewHeight);
        headerViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerViewHeight);
        headerView = header;
    }

    private void calculateTableBottoms() {

        table1Top = headerViewHeight;
        table1TopAddHeader = adapter.hTitleHeight + table1Top;

        table1Bottom = adapter.getTableRowCount1()* (adapter.itemTxtHeight + 1) + adapter.hTitleHeight + headerViewHeight;
        table1BottomCutHeader = table1Bottom - adapter.hTitleHeight;

        table2Top = table1Bottom + adapter.blankHeight;
        table2TopAddHeader = table2Top + adapter.hTitleHeight;
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
        if (clickTag.colIndex > 0 && clickTag.rowIndex == 0) {
            if (clickTag.colIndex == sortColumnIndex) {//click at last column
                return;
            }
        }
        adapter.OnItemClicked(v);
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
                ((ArbTextView)horLinear1.getChildAt(sortColumnIndex)).switchBottomImg(false, -1, hTitleCorRes);
                ((ArbTextView)hideHorLinear1.getChildAt(sortColumnIndex)).switchBottomImg(false, -1, hTitleCorRes);
                ((ArbTextView)horLinear2.getChildAt(sortColumnIndex)).switchBottomImg(false, -1, hTitleCorRes);
                ((ArbTextView)hideHorLinear2.getChildAt(sortColumnIndex)).switchBottomImg(false, -1, hTitleCorRes);
                //sync column record
                sortColumnIndex = clickColIndex;
                int sortHeight = convertDIP2PX(mContext, 7);
                //show icon of new column
                ((ArbTextView)horLinear1.getChildAt(clickColIndex)).switchBottomImg(true, teamSortDrawableId1, sortHeight, teamSortCor1);
                ((ArbTextView)hideHorLinear1.getChildAt(clickColIndex)).switchBottomImg(true, teamSortDrawableId1, sortHeight, teamSortCor1);
                ((ArbTextView)horLinear2.getChildAt(clickColIndex)).switchBottomImg(true, teamSortDrawableId1, sortHeight, teamSortCor2);
                ((ArbTextView)hideHorLinear2.getChildAt(clickColIndex)).switchBottomImg(true, teamSortDrawableId1, sortHeight, teamSortCor2);
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

    public void updateView() {
        updateVerticalHeader();
        updateText();
    }

    public void resetView() {
        this.removeAllViews();
        System.gc();
        init(true);
    }

    public void resortRows(StudentsList.SortIndex sortIndex) {
        int table1Count = tableLayout_1.getChildCount();
        ArbTableRow[] rows = new ArbTableRow[table1Count];
        for (int i = 0; i < table1Count; i++) {
            rows[i] = (ArbTableRow) tableLayout_1.getChildAt(i);
        }
        tableLayout_1.removeAllViewsInLayout();
        for (int i = 0; i < table1Count; i++) {
            if (i < sortIndex.fstIndex.length) {
                tableLayout_1.addView(rows[sortIndex.fstIndex[i]]);
            } else {
                tableLayout_1.addView(rows[i]);
            }
        }

        int table2Count = tableLayout_2.getChildCount();
        ArbTableRow[] rows2 = new ArbTableRow[table2Count];
        for (int i = 0; i < table2Count; i++) {
            rows2[i] = (ArbTableRow) tableLayout_2.getChildAt(i);
        }
        tableLayout_2.removeAllViewsInLayout();
        for (int i = 0; i < table2Count; i++) {
            if (i < sortIndex.secIndex.length) {
                tableLayout_2.addView(rows2[sortIndex.secIndex[i]]);
            } else {
                tableLayout_2.addView(rows2[i]);
            }
        }

        int verticalHeaderCount = verticalLinear.getChildCount();
        View[] verticalHeaders = new View[verticalHeaderCount];
        for (int i = 0; i < verticalHeaderCount; i++) {
            verticalHeaders[i] = verticalLinear.getChildAt(i);
        }
        verticalLinear.removeAllViewsInLayout();
        for (int i = 0; i < verticalHeaderCount; i++) {
            if (i == 0 || i == table1Count + 1 || i == table1Count + 2) {
                verticalLinear.addView(verticalHeaders[i]);
            } else if (0 < i && i < table1Count + 1) {
                verticalLinear.addView(verticalHeaders[sortIndex.fstIndex[i - 1] + 1]);
            } else if (i > table1Count + 2) {

                verticalLinear.addView(verticalHeaders[table1Count + 3 + sortIndex.secIndex[i - (table1Count + 3)]]);
            }
        }
    }

    public void updateVerticalHeader() {
        if (null != verticalLinear) {
//            verticalLinear.getChildAt()
            int count = verticalLinear.getChildCount();
            View viewT;
            for (int i = 0; i < count; i++) {
                viewT = verticalLinear.getChildAt(i);
                if (0 < i && i< adapter.table1DataSize) {//表一 除去第一行
                    if (viewT instanceof ArbTextView) {
                        if (adapter.hightLight(0, i - 1, 0)) {
                            ((ArbTextView)viewT).getText().setTextColor(itemPlayingCor);
                        } else {
                            ((ArbTextView)viewT).getText().setTextColor(itemTxtCor);
                        }
                    }
                }
                if (adapter.table1DataSize + 4 < i && i < adapter.table1DataSize + 4 + adapter.table2DataSize) {//表二
                    if (viewT instanceof ArbTextView) {
                        if (adapter.hightLight(1, i - 1 - (adapter.table1DataSize + 4), 0)) {
                            ((ArbTextView)viewT).getText().setTextColor(itemPlayingCor);
                        } else {
                            ((ArbTextView)viewT).getText().setTextColor(itemTxtCor);
                        }
                    }
                }
            }
        }
    }

    public void updateText() {
        ArbTableRow row;
        TextView itemTxt;
        if (null != tableLayout_1) {
            int table1RowCount = tableLayout_1.getChildCount();
            for (int i = 0; i < table1RowCount; i++) {
                row = (ArbTableRow) tableLayout_1.getChildAt(i);
                for (int j = 0; j < row.getChildCount(); j ++) {
                    itemTxt = (TextView) row.getChildAt(j);
                    itemTxt.setText(adapter.getColItem1(i, j));
                    if (i< adapter.table1DataSize) {
                        if (adapter.hightLight(0, i, j)) {
                            itemTxt.setTextColor(itemPlayingCor);
                        } else {
                            itemTxt.setTextColor(itemTxtCor);
                        }
                    }
                }
            }
        }

        if (null != tableLayout_2) {
            int table2RowCount = tableLayout_2.getChildCount();
            for (int i = 0; i < table2RowCount; i++) {
                row = (ArbTableRow) tableLayout_2.getChildAt(i);
                for (int j = 0; j < row.getChildCount(); j ++) {
                    itemTxt = (TextView) row.getChildAt(j);
                    itemTxt.setText(adapter.getColItem2(i, j));
                    if (i < adapter.table2DataSize) {
                        if (adapter.hightLight(1, i, j)) {
                            itemTxt.setTextColor(itemPlayingCor);
                        } else {
                            itemTxt.setTextColor(itemTxtCor);
                        }
                    }

                }
            }
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (isLeftMost && distanceX < 0 && Math.abs(distanceX) > Math.abs(distanceY)) {
            return true;
        } else if (isRightMost && distanceX > 0 && Math.abs(distanceX) > Math.abs(distanceY)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    public class Tag{
        public int tableIndex;
        public int rowIndex;
        public int colIndex;
        public int pid;//player id
        public int tid;//team id
        public String name;
        public Object innerTag;

        @Override
        public String toString() {
            return "Tag{" +
                    "tableIndex=" + tableIndex +
                    ", rowIndex=" + rowIndex +
                    ", colIndex=" + colIndex +
                    ", pid=" + pid +
                    ", tid=" + tid +
                    ", name='" + name + '\'' +
                    ", innerTag=" + innerTag +
                    '}';
        }
    }

    public void setHeaderClick(OnHorHeaderClickListener click) {
        this.horHeaderClick = click;
    }
    public void setTableNameClick(OnTableNameClickListener click) {
        this.tNameClick = click;
    }
    public void setRowClick(OnRowClickListener click) {
        this.rowClick = click;
    }
    public void setOnLoadListener(OnLoadListener onLoad) {
        this.onLoad = onLoad;
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

    public interface OnLoadListener{
        void onLoadStart();
        void onLoadComplete();
    }
}
