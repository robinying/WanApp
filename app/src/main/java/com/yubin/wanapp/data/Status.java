package com.yubin.wanapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * author : Yubin.Ying
 * time : 2018/11/6
 */
public class Status {
    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("errorCode")
    private int errorCode;
    @Expose
    @SerializedName("errorMsg")
    private String errorMsg;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }



    class Data{

    }
}
