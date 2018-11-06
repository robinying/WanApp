package com.yubin.wanapp.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.blankj.utilcode.util.SizeUtils;
import com.yubin.wanapp.activity.App;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public class ToastUtil {
    private static Toast mToast;

    static {
        mToast = null;
    }

    public static void destory() {
        if (mToast != null) {
            mToast = null;
        }
    }

    public static void makeText(Activity activity, final String msg) {
        makeText(activity, msg, Gravity.BOTTOM);
    }

    public static void makeText(Activity activity, final String msg, final int gravity) {
        if (activity == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(App.getContext(), (CharSequence) msg, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(msg);
                    if (msg.length() < 20) {
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    } else {
                        mToast.setDuration(Toast.LENGTH_LONG);
                    }
                }
                mToast.setGravity(gravity, 0, 0);
                mToast.show();
//                OkToastUtils.mToast = null;
            }
        });
    }

    @Deprecated
    public static void makeText(Context context, CharSequence msg) {
        makeText(context, msg, Gravity.BOTTOM);
    }

    public static void makeText(Context context, CharSequence msg, int gravity) {
        if (msg == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setGravity(gravity, 0, 0);
        mToast.show();
//        OkToastUtils.mToast = null;
    }

    public static void makeText(CharSequence text) {
        makeText(text, Gravity.BOTTOM);
    }

    public static void makeText(CharSequence text, int gravity) {
        if (text == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        show(gravity);
//        OkToastUtils.mToast = null;
    }


    public static void makeText(int msgRes) {
        makeText(msgRes, Gravity.BOTTOM);
    }

    public static void makeText(int msgRes, int gravity) {
        if (msgRes == 0) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), msgRes, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msgRes);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        show(gravity);
//        OkToastUtils.mToast = null;
    }

    public static void makeText(Activity activity, int msgRes) {
        makeText(activity, msgRes, Gravity.BOTTOM);
    }

    public static void makeText(final Activity activity, final int messageRes, final int gravity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                CharSequence message = activity.getResources().getString(messageRes);
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                if (mToast == null) {
                    mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(message);
                    if (message.length() < 20)
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    else
                        mToast.setDuration(Toast.LENGTH_LONG);
                }
                show(gravity);
            }
        });
    }

    private static void show(int gravity) {
        mToast.setGravity(gravity, 0, SizeUtils.dp2px(64));
        mToast.show();
    }
}
