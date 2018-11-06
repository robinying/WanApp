package com.yubin.wanapp.data;

import android.app.Application;

import com.yubin.wanapp.activity.App;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public class UserManager {

    private static volatile UserManager instance;
    private static Application application;

    private LoginDetailDataDao userDao;

    private LoginDetailData loginDetailData;


    public static UserManager getUserManager() {
        if (UserManager.instance == null) {
            synchronized (UserManager.class) {
                if (UserManager.instance == null) {
                    UserManager.instance = new UserManager();
                    UserManager.application = App.getContext();
                }
            }
        }
        return UserManager.instance;
    }

    private void initDao() {
        if (userDao == null) {
            userDao = App.getContext().getDaoSession().getLoginDetailDataDao();
        }
    }

    public boolean isLogin() {
        LoginDetailData userInfo = getUserInfo();
        if (userInfo == null) {
            return false;
        }
        return true;
    }

    private LoginDetailData getUserInfo(){
        if(loginDetailData !=null){
            return  loginDetailData;
        }
        initDao();
        List<LoginDetailData> userList=userDao.loadAll();
        if (userList != null && userList.size() > 0) {
            return loginDetailData = userList.get(0);
        }
        return null;
    }

    public String getUserName(){
        LoginDetailData userInfo = getUserInfo();
        if(userInfo !=null){
            return userInfo.getUsername();
        }
        return null;
    }

    public Integer getUserId(){
        LoginDetailData userInfo = getUserInfo();
        if(userInfo !=null){
            return userInfo.getId();
        }
        return null;
    }

    public String getPassword(){
        LoginDetailData userInfo = getUserInfo();
        if(userInfo !=null){
            return userInfo.getPassword();
        }
        return null;
    }

    public void clearUserData(){
        loginDetailData = null;
        initDao();
        userDao.deleteAll();
    }
}
