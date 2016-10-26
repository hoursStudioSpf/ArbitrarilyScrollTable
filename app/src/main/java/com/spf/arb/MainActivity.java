package com.spf.arb;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spf.arb.data.StudentAdapter;
import com.spf.arb.data.StudentsList;
import com.spf.arb.util.DisplayUtil;
import com.spf.arb.view.ArbitrarilyScrollView;

import static com.spf.arb.R.id.scroller;


public class MainActivity extends Activity implements View.OnClickListener{

    ArbitrarilyScrollView dataList;
    StudentsList studentsList;
    StudentAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        dataList = (ArbitrarilyScrollView) findViewById(scroller);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(Color.parseColor("#FF00FF"));
        TextView tv1 = new TextView(this);
        tv1.setWidth(200);
        tv1.setText("这是Head");
        TextView tv2 = new TextView(this);
        tv2.setWidth(200);
        tv2.setText("这是Head");
        TextView tv3 = new TextView(this);
        tv3.setWidth(200);
        tv3.setText("这是Head");
        TextView tv4 = new TextView(this);
        tv4.setWidth(200);
        tv4.setText("这是Head");
        Button btn = new Button(this);
        btn.setTag(1);
        btn.setWidth(200);
        btn.setText("数据刷新");
        btn.setOnClickListener(this);
        ll.addView(tv1);
        ll.addView(tv2);
        ll.addView(tv3);
        ll.addView(tv4);
        ll.addView(btn);
        //ADD head first
        dataList.addHeader(ll);

        initDataListConfig();
    }

    private void initDataListConfig() {
        dataList.setVerticalHeaderPadding(DisplayUtil.convertDIP2PX(this, 8));
        int itemResId1 = R.color.itemCor;
        int titleResId = R.color.titleCor;
        int blockResId = R.color.blockCor;
        int dividerCor = R.color.dividerCor;

        dataList.setBackgroundCorRes(titleResId, itemResId1, titleResId, blockResId, -1, -1, dividerCor);
        int teamSortCor1 = Color.parseColor("#FF4081");
        int teamSortCor2 = Color.parseColor("#3F51B5");
        int teamSortArrowId = R.drawable.group_down;
        dataList.setTeamSpecials(Color.TRANSPARENT, Color.TRANSPARENT, teamSortCor1, teamSortCor2, teamSortArrowId, teamSortArrowId);
        int itemTxtCor = R.color.textCor;
        int highLightTxtCor = R.color.highLight;

        dataList.setTxtCor(itemTxtCor, itemTxtCor, highLightTxtCor);
        dataList.enableClick(true, true);
        dataList.enableSortTitle(true);
        initAdapter();
    }

    private void initAdapter() {
        studentsList = new StudentsList();
        dataAdapter = new StudentAdapter(this, studentsList);
        String tableName1 = "三年级一班";
        String tableName2 = "三年级二班";
        dataAdapter.setTableName(tableName1, tableName2);

        int txtWidth = DisplayUtil.convertDIP2PX(this, 34);
        int txtHeight = DisplayUtil.convertDIP2PX(this, 36);

        int vTitleWidth = DisplayUtil.convertDIP2PX(this, 100);
        int vTitleHeight = DisplayUtil.convertDIP2PX(this, 36);

        int hTitleWidth = DisplayUtil.convertDIP2PX(this, 34);
        int hTitleHeight = DisplayUtil.convertDIP2PX(this, 23);

        int tableNameW = DisplayUtil.convertDIP2PX(this, 100);
        int tableNameH = DisplayUtil.convertDIP2PX(this, 23);

        int blockHeight = DisplayUtil.convertDIP2PX(this, 10);

        dataAdapter.setTableLW(txtWidth, txtHeight, vTitleWidth, vTitleHeight, hTitleWidth, hTitleHeight,
                tableNameW, tableNameH, blockHeight);
        dataList.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
//        int tag = (int) v.getTag();
//        if (1 == tag) {
//            refreshH.sendEmptyMessage(0);
//        }
        dataAdapter.setData(new StudentsList());
        //学生顺序不变用notifyDataSetChange
        dataAdapter.notifyDataSetChange();
        //重新排序后用resortRows
//        dataAdapter.resortRows();
    }

    int i = 0;
    Handler refreshH = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            studentsList.studentsList1.get(0).data1 = "" + i;
            if (i < 100) {
                i++;
            } else {
                i -= 100;
            }
            Log.e("SPF", "--->" + studentsList.studentsList1.get(0).data1);
            dataAdapter.setData(studentsList);
            dataAdapter.notifyDataSetChange();
            refreshH.sendEmptyMessageDelayed(0, 1000);
        }
    };
}
