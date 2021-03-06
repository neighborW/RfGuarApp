package com.rifeng.p2p.uphidescrollview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Scroller;



/**
 * Created by caizhiming on 2016/11/14.
 */
public class XCUpHideScrollView extends ScrollView {
    public XCUpHideScrollView(Context context) {
        this(context,null);
    }

    public XCUpHideScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XCUpHideScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public XCUpHideScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private View mHeaderView;

    public void setHeaderView(View view) {
        mHeaderView = view;
    }

    private View mContentView;

    public void setContentView(View view) {
        this.mContentView = view;
    }

    private View scrollableView;

    public void setContentInnerScrollableView(View scrollableView) {
        this.scrollableView = scrollableView;
    }

    private int maxMoveY;
    private int tabHeight;
    private float mFirstY,mFirstX;
    private Matrix eventMatrix;
    private boolean isIntercept;



    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isIntercept) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mHeaderView == null || mContentView == null) {
            return super.dispatchTouchEvent(event);
        }

        if (maxMoveY == 0) {
            maxMoveY = mHeaderView.getMeasuredHeight();
        }

        isIntercept = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstY = event.getY();
                mFirstX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (scrollableView != null) {
                    float mCurrentY = event.getY();
                    float mCurrentX = event.getX();

                    float detalX = mCurrentX - mFirstX;
                    float detalY = mCurrentY - mFirstY;

                    if (Math.abs(detalY) < Math.abs(detalX)) {
                        break;
                    }


                    if (tabHeight == 0) {
                        tabHeight = mContentView.getMeasuredHeight() - scrollableView.getMeasuredHeight();
                    }
                    eventMatrix = new Matrix();
                    eventMatrix.setTranslate(0, -tabHeight);

                    boolean isDown = mCurrentY > mFirstY;
                    boolean isUp = mCurrentY < mFirstY;

                    if (isUp) {
                        if (getScrollY() >= maxMoveY) {
                            isIntercept = true;
                            event.transform(eventMatrix);
                            return scrollableView.dispatchTouchEvent(event);
                        }
                    } else if (isDown) {
                        if (!ViewUtil.isScrollToTop(scrollableView)) {
                            isIntercept = true;
                            event.transform(eventMatrix);
                            return scrollableView.dispatchTouchEvent(event);
                        }else{
                            if(getScrollY()>=maxMoveY){
                                isIntercept = true;
                                return scrollableView.dispatchTouchEvent(event);
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.dispatchTouchEvent(event);
    }
    private ObservableScrollView.OnScrollChangedListener onScrollChangedListener;
    public void setOnScrollListener(ObservableScrollView.OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(x, y, oldX, oldY);
        }
    }
    private Scroller mScroller;
    //????????????????????????????????????  duration????????????
    public void smoothScrollToSlow(int fx, int fy, int duration) {
        int dx = fx - getScrollX();//mScroller.getFinalX();  ??????view??????????????????
        int dy = fy - getScrollY();  //mScroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration);
    }
    //??????????????????????????????????????????
    public void smoothScrollBySlow(int dx, int dy,int duration) {

        //??????mScroller??????????????????
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy,duration);//scrollView?????????????????????????????????????????????
//        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);  //??????view???????????????
        invalidate();//??????????????????invalidate()????????????computeScroll()?????????????????????????????????????????????????????????????????????
    }
    @Override
    public void computeScroll() {

        //?????????mScroller??????????????????
        if (mScroller.computeScrollOffset()) {

            //????????????View???scrollTo()?????????????????????
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //????????????????????????????????????????????????????????????
            postInvalidate();
        }
        super.computeScroll();
    }
}
