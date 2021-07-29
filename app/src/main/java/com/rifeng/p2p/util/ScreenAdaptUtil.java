package com.rifeng.p2p.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 屏幕适配器
 */
public class ScreenAdaptUtil {


    private Context context;
    //标准值  初始化时序可以HOOK
    private static final float STANDED_WIDTH = 1080F;
    private static final float STANDED_HEIGHT= 1920F;
    //实际值 , MMKV
    private static float dispayMetricsWidth;
    private static float dispayMetricsHeight;

    private static  ScreenAdaptUtil instance;
    public static ScreenAdaptUtil getInstance(Context context){
        if (instance ==  null){
            instance = new ScreenAdaptUtil(context);
        }
        return instance;
    }
    private ScreenAdaptUtil(Context context){

        this.context = context;
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (dispayMetricsWidth == 0.0f || dispayMetricsHeight == 0.0f){
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            int systemBarHeight = getSystemBarHeight(context);
            if (displayMetrics.widthPixels > displayMetrics.heightPixels){
                //横屏
                dispayMetricsWidth = dispayMetricsHeight;
                dispayMetricsHeight = displayMetrics.widthPixels  - systemBarHeight;
            }
            //竖屏
            else{
                dispayMetricsWidth = (float) displayMetrics.widthPixels;
                dispayMetricsHeight = (float) displayMetrics.heightPixels;
            }
        }
    }

    private static final String DIME_CLASS = "com.android.internal.R$dimen";

    private int getSystemBarHeight(Context context){

        return getValues(context , DIME_CLASS , "system_bar_height",48);
    }

    private int getValues(Context context , String dimeClass , String system_bar_height , int i){


        try {
            Class<?> clz = Class.forName(dimeClass);
            Object o = clz.newInstance();
            Field field = clz.getField(system_bar_height);

            int id = Integer.parseInt(field.get(o).toString());
            return context.getResources().getDimensionPixelSize(id);
        }
         catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    public  float getHorValues(){
        return ((float)dispayMetricsWidth)/STANDED_WIDTH;
    }
    public  float getVerValues(){
        return ((float) dispayMetricsHeight) / (STANDED_HEIGHT - getSystemBarHeight(context));

    }

    public static void setCustomDensity(Activity activity, Application application){

        DisplayMetrics applicationMetrics = application.getResources().getDisplayMetrics();
        float targetDensity = applicationMetrics.widthPixels / 188;
        float targetDensityDpi = 160 * targetDensity;
        applicationMetrics.density = targetDensity;
        applicationMetrics.scaledDensity = targetDensity;
        applicationMetrics.densityDpi =(int) targetDensityDpi;
        DisplayMetrics activityMetrics = activity.getResources().getDisplayMetrics();
        activityMetrics.density = targetDensity;
        activityMetrics.scaledDensity = targetDensity;
        activityMetrics.densityDpi =(int) targetDensityDpi;
    }

    /**
     * dp2px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    public static int getScreenWidth(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.heightPixels - getStatusBarHeight(context);
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result == 0 ? dip2px(context, 25) : result;
    }

}
