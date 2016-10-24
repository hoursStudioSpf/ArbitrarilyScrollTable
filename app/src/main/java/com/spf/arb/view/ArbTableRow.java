package com.spf.arb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

/**
 * Description: TODO
 * Author: ShiPeifeng
 * Date: 16/9/22.
 */
public class ArbTableRow extends TableRow{
    public ArbTableRow(Context context) {
        super(context);
    }

    public ArbTableRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
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
