package com.yubin.wanapp.interf;

import android.app.ProgressDialog;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public interface IDialogController {
    public abstract void hideWaitDialog();

    public abstract ProgressDialog showWaitDialog();

    public abstract ProgressDialog showWaitDialog(int resid);

    public abstract ProgressDialog showWaitDialog(String text);
}
