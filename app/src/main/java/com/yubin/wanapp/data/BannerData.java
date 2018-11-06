package com.yubin.wanapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public class BannerData {
    @Expose
    @SerializedName("data")
    private List<BannerDetailData> data;

    @Expose
    @SerializedName("errorCode")
    private int errorCode;
    @Expose
    @SerializedName("errorMsg")
    private String errorMsg;

    public List<BannerDetailData> getData() {
        return data;
    }

    public void setData(List<BannerDetailData> data) {
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


}
