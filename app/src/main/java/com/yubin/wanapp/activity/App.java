package com.yubin.wanapp.activity;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

import com.yubin.wanapp.BuildConfig;
import com.yubin.wanapp.data.DaoMaster;
import com.yubin.wanapp.data.DaoSession;
import com.yubin.wanapp.util.BasePreference;
import com.yubin.wanapp.util.ConstantUtil;
import com.yubin.wanapp.util.ContextUtils;
import com.yubin.wanapp.util.greendao.GreenDaoOpenHelper;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * author : Yubin.Ying
 * time : 2018/11/1
 */
public class App extends Application {
    private static App context;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initDao();
        if (BasePreference.getBoolean(ConstantUtil.NIGHT_MODE,false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        ContextUtils.init(context);
    }

    public static App getContext(){
        return context;
    }

    private void initDao() {
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
//        SQLiteDatabase.loadLibs(this);
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        GreenDaoOpenHelper helper = new GreenDaoOpenHelper(this,  "wanapp.db", null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
