package com.yubin.wanapp.util;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * author : Yubin.Ying
 * time : 2018/11/9
 */
public class ContextUtils {

    public ContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        ContextUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
