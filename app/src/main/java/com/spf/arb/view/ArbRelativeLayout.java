package com.spf.arb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Description: TODO
 * Author: ShiPeifeng
 * Date: 16/9/22.
 */
public class ArbRelativeLayout extends RelativeLayout {
    public ArbRelativeLayout(Context context) {
        super(context);
    }

    public ArbRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArbRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        return super.addViewInLayout(child, index, params);
    }

    @Override
    public ViewGroup.LayoutParams getLayoutParams() {
        ViewGroup.LayoutParams lp = super.getLayoutParams();
        if (null == lp) {
            return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return lp;
    }
}
