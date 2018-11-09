package com.yubin.wanapp.data;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author : Yubin.Ying
 * time : 2018/11/9
 */
@Entity
public class FavoriteArticleDetailData {

    @SerializedName("author")
    private String author;
    @SerializedName("chapterId")
    private int chapterId;
    @SerializedName("chapterName")
    private String chapterName;
    @SerializedName("courseId")
    private int courseId;
    @SerializedName("desc")
    private String desc;
    @SerializedName("envelopePic")
    private String envelopePic;
    @SerializedName("id")
    private int id;
    @SerializedName("link")
    private String link;
    @SerializedName("niceDate")
    private String niceDate;
    @SerializedName("origin")
    private String origin;
    @SerializedName("originId")
    private int originId;
    @SerializedName("publishTime")
    private long publishTime;
    @SerializedName("title")
    private String title;
    @SerializedName("userId")
    private int userId;
    @SerializedName("visible")
    private int visible;
    @SerializedName("zan")
    private int zan;

    @Generated(hash = 1552691282)
    public FavoriteArticleDetailData(String author, int chapterId,
            String chapterName, int courseId, String desc, String envelopePic,
            int id, String link, String niceDate, String origin, int originId,
            long publishTime, String title, int userId, int visible, int zan) {
        this.author = author;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.courseId = courseId;
        this.desc = desc;
        this.envelopePic = envelopePic;
        this.id = id;
        this.link = link;
        this.niceDate = niceDate;
        this.origin = origin;
        this.originId = originId;
        this.publishTime = publishTime;
        this.title = title;
        this.userId = userId;
        this.visible = visible;
        this.zan = zan;
    }

    @Generated(hash = 309231865)
    public FavoriteArticleDetailData() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}
