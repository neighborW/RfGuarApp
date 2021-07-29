package com.rifeng.p2p.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rifeng.p2p.R;

public class PercentRelativeLayout extends RelativeLayout {

    public PercentRelativeLayout(Context context) {
        super(context);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width= View.MeasureSpec.getSize(widthMeasureSpec);
        int height=View.MeasureSpec.getSize(heightMeasureSpec);
        int childCount=this.getChildCount();
        for(int i=0;i<childCount;i++){
            View child=this.getChildAt(i);
            ViewGroup.LayoutParams layoutParams=child.getLayoutParams();
            float widthPercent=0;
            float heightPercent=0;
            if(layoutParams instanceof PercentRelativeLayout.LayoutParams){
                widthPercent=((LayoutParams)layoutParams).getWidthPercent();
                heightPercent=((LayoutParams)layoutParams).getHeightPercent();
            }
            if(widthPercent>0){
                layoutParams.width=(int)(width*widthPercent);
            }
            if(heightPercent>0){
                layoutParams.height=(int)(height*heightPercent);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public RelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }
    public static class LayoutParams extends RelativeLayout.LayoutParams{
        private float widthPercent;
        private float heightPercent;

        public void setHeightPercent(float heightPercent) {
            this.heightPercent = heightPercent;
        }

        public void setWidthPercent(float widthPercent) {
            this.widthPercent = widthPercent;
        }

        public float getWidthPercent() {
            return widthPercent;
        }

        public float getHeightPercent() {
            return heightPercent;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray array=c.obtainStyledAttributes(attrs, R.styleable.PercentRelativeLayout);
            widthPercent=array.getFloat(R.styleable
                    .PercentRelativeLayout_layout_widthPercent,0);
            heightPercent=array.getFloat(R.styleable
                    .PercentRelativeLayout_layout_heightPercent,0);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(RelativeLayout.LayoutParams source) {
            super(source);
        }
    }
}
