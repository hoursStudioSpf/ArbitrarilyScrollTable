package com.spf.arb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spf.arb.R;


/**
 * Description: Customized TextView for ArbScroller
 * Author: ShiPeifeng
 * Date: 16/9/14.
 */
public class ArbTextView extends ViewGroup{

    private Context mContext;

    private int firstBgColor;
    private int secBgColor;

    private int paddingLeft = 0;
    private int paddingTop = 0;
    private int paddingRight = 0;
    private int paddingBottom = 0;

    private ArbRelativeLayout outerLL;
    private TextView innerTxt;

    int width = 200;
    int height = 100;

    int blockCor;
    int blockHeight;

    int btmDrawableId;
    int btmDrawableHeight = 0;
    int specialBgCor;

    //Color block at bottom
    View block;
    public boolean isShownBlock = false;
    //Image at bottom
    ImageView btmImg;
    public boolean isShownBtmImg = false;

    public ArbTextView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ArbTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public void init() {
        outerLL = (ArbRelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.arb_table_text, null);
        innerTxt = (TextView) outerLL.findViewById(R.id.inner_txt);
    }

    public TextView getText() {
        return innerTxt;
    }

    public void setOuterPadding(int l, int t, int r, int b) {
        this.paddingLeft = l;
        this.paddingTop = t;
        this.paddingRight = r;
        this.paddingBottom = b;
    }



    public void setBgColor(int fistCor, int secCor) {
        this.firstBgColor = fistCor;
        this.secBgColor = secCor;
    }

    public void switchBottomImg(boolean open, int... idHeightBgCor) {
        if (open && idHeightBgCor.length == 3) {
            btmDrawableId = idHeightBgCor[0];
            btmDrawableHeight = idHeightBgCor[1];
            specialBgCor = idHeightBgCor[2];
        } else {
            btmDrawableHeight = 0;
            specialBgCor = idHeightBgCor[0];
        }
        if (open) {
            RelativeLayout.LayoutParams bottomImgLp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, btmDrawableHeight);
            bottomImgLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            btmImg = new ImageView(mContext);
            btmImg.setImageResource(btmDrawableId);
            btmImg.setLayoutParams(bottomImgLp);
            btmImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            outerLL.addViewInLayout(btmImg, -1, btmImg.getLayoutParams());
            isShownBtmImg = true;
        } else {
            //remove if included
            if (null != btmImg && btmImg.getParent() == outerLL) {
                outerLL.removeView(btmImg);
            }
            isShownBtmImg = false;
        }
        innerTxt.setBackgroundColor(specialBgCor);
        invalidate();
    }

    public void switchBottomBlock(boolean open, int... corAndHeight) {
        if (open && corAndHeight.length == 2) {
            blockCor = corAndHeight[0];
            blockHeight = corAndHeight[1];
        } else {
            blockHeight = 0;
        }
        //remove if included
        if (null != block && block.getRootView() == outerLL) {
            outerLL.removeView(block);
        }
        isShownBlock = false;
        invalidate();
    }



    public void generate() {
        outerLL.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        innerTxt.setWidth((width - paddingLeft - paddingRight) > 0 ? (width - paddingLeft - paddingRight) : 190);
        innerTxt.setHeight((height - paddingTop - paddingBottom) > 0 ? (height - paddingTop - paddingBottom) : 90);
        outerLL.setBackgroundColor(firstBgColor);
        innerTxt.setBackgroundColor(secBgColor);

        if (blockHeight > 0) {
            RelativeLayout.LayoutParams bottomLp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, blockHeight);
            bottomLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            block = new View(mContext);
            block.setBackgroundColor(blockCor);
            block.setLayoutParams(bottomLp);
            outerLL.addView(block);
            isShownBlock = true;
        }
        if (btmDrawableHeight > 0) {

            RelativeLayout.LayoutParams bottomImgLp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, btmDrawableHeight);
            bottomImgLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            btmImg = new ImageView(mContext);
            btmImg.setImageResource(btmDrawableId);
            btmImg.setLayoutParams(bottomImgLp);
            btmImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            outerLL.addView(btmImg);
            innerTxt.setBackgroundColor(specialBgCor);
            isShownBtmImg = true;
        }

        this.addView(outerLL);
    }

    public void setBtmCorBlock(int cor, int height) {
        this.blockCor = cor;
        this.blockHeight = height;
    }

    public void setBtmDrawable(int resId, int height, int specialBgCor) {
        this.btmDrawableId = resId;
        this.btmDrawableHeight = height;
        this.specialBgCor = specialBgCor;
    }

    public void setWH(int w, int h) {
        width = w;
        height = h;
        LayoutParams lp = new LayoutParams(w, h);
        outerLL.setLayoutParams(lp);
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
        View view = getChildAt(0);
        view.layout(0, 0, width, height);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
