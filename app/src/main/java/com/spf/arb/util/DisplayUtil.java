package com.spf.arb.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 用于转换界面尺寸的工具类
 */
public class DisplayUtil {

    public static int getScreenWidth(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.heightPixels;
    }
    
    public static int convertDIP2PX(Context context, float dip) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    // 转换px为dip
    public static int convertPX2DIP(Context context, float px) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

}
