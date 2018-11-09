package com.yubin.wanapp.util.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yubin.wanapp.data.ArticleDetailDataDao;
import com.yubin.wanapp.data.DaoMaster;
import com.yubin.wanapp.data.FavoriteArticleDetailDataDao;
import com.yubin.wanapp.data.LoginDetailData;
import com.yubin.wanapp.data.LoginDetailDataDao;

import org.greenrobot.greendao.database.Database;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public class GreenDaoOpenHelper extends DaoMaster.OpenHelper {
    public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, LoginDetailDataDao.class, ArticleDetailDataDao.class, FavoriteArticleDetailDataDao.class);
    }
}
