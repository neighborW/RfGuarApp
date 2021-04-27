package com.rifeng.p2p.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

public class MyEditText  extends AppCompatEditText {


    private long lastTime = 0;
    public MyEditText(Context context) {
        super(context);
    }
    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //把光标位置固定在最末
        this.setSelection(this.getText().length());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //屏蔽双击事件
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTime < 500) {
                    lastTime = currentTime;
                    return true;
                } else {
                    lastTime = currentTime;
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
