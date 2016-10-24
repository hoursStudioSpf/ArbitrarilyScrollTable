package com.spf.arb;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spf.arb.view.ArbitrarilyScrollView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ArbitrarilyScrollView scroller = (ArbitrarilyScrollView) findViewById(R.id.scroller);


        TextView tv1 = new TextView(this);
        tv1.setBackgroundColor(Color.RED);
        tv1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SPF", "tv1 clicked!!!!!!!!");
            }
        });
        scroller.addHeader(tv1);
        scroller.init();
    }



}
