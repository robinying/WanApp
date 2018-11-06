package com.yubin.wanapp.data;

import com.google.gson.annotations.SerializedName;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author : Yubin.Ying
 * time : 2018/11/1
 */
@Entity
public class LoginDetailData {
    /**
     * data : {"chapterTops":[],"collectIds":[],"email":"","icon":"","id":12373,"password":"12345678","token":"","type":0,"username":"robintest"}
     * errorCode : 0
     * errorMsg :
     */

    /**
     * chapterTops : []
     * collectIds : []
     * email :
     * icon :
     * id : 12373
     * password : 12345678
     * token :
     * type : 0
     * username : robintest
     */

    @SerializedName("email")
    private String email;
    @SerializedName("icon")
    private String icon;
    @SerializedName("id")
    private int id;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;
    @SerializedName("type")
    private int type;
    @SerializedName("username")
    private String username;
    @Transient
    @SerializedName("chapterTops")
    private List<Integer> chapterTops;
    @Transient
    @SerializedName("collectIds")
    private List<Integer> collectIds;

    @Generated(hash = 383158423)
    public LoginDetailData(String email, String icon, int id, String password, String token, int type, String username) {
        this.email = email;
        this.icon = icon;
        this.id = id;
        this.password = password;
        this.token = token;
        this.type = type;
        this.username = username;
    }

    @Generated(hash = 1600546930)
    public LoginDetailData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getChapterTops() {
        return chapterTops;
    }

    public void setChapterTops(List<Integer> chapterTops) {
        this.chapterTops = chapterTops;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }
}
