package com.yubin.wanapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/13
 */
public class ProjcetListData {



    /**
     * data : {"curPage":1,"datas":[{"apkLink":"","author":"boiyun","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"一款基于鸿洋大神
     * errorCode : 0
     * errorMsg :
     */

    @SerializedName("data")
    private List<ArticleDetailData> data;
    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("errorMsg")
    private String errorMsg;

    public List<ArticleDetailData> getData() {
        return data;
    }

    public void setData(List<ArticleDetailData> data) {
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
