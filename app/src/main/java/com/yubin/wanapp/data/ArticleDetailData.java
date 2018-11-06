package com.yubin.wanapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 *
 */
/*                "apkLink": "",
                        "author": "郭霖",
                        "chapterId": 409,
                        "chapterName": "郭霖",
                        "collect": false,
                        "courseId": 13,
                        "desc": "",
                        "envelopePic": "",
                        "fresh": false,
                        "id": 7451,
                        "link": "https://mp.weixin.qq.com/s/CWJCb3Eh65dRLJp2dL8eog",
                        "niceDate": "1天前",
                        "origin": "",
                        "projectLink": "",
                        "publishTime": 1541001600000,
                        "superChapterId": 408,
                        "superChapterName": "公众号",
                        "tags": [
                        {
                        "name": "公众号",
                        "url": "/wxarticle/list/409/1"
                        }
                        ],
                        "title": "Google推荐的后台任务解决方案",
                        "type": 0,
                        "userId": -1,
                        "visible": 1,
                        "zan": 0*/
@Entity
public class ArticleDetailData {

    @Expose
    @SerializedName("apkLink")
    private String apkLink;
    @Expose
    @SerializedName("author")
    private String author;
    @Expose
    @SerializedName("chapterId")
    private int chapterId;
    @Expose
    @SerializedName("chapterName")
    private String chapterName;
    @Expose
    @SerializedName("collect")
    private boolean collect;
    @Expose
    @SerializedName("courseId")
    private int courseId;
    @Expose
    @SerializedName("desc")
    private String desc;
    @Expose
    @SerializedName("envelopePic")
    private String envelopePic;
    @Expose
    @SerializedName("fresh")
    private boolean fresh;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("link")
    private String link;
    @Expose
    @SerializedName("niceDate")
    private String niceDate;
    @Expose
    @SerializedName("origin")
    private String origin;
    @Expose
    @SerializedName("projectLink")
    private String projectLink;
    @Expose
    @SerializedName("publishTime")
    private long publishTime;
    @Expose
    @SerializedName("superChapterId")
    private int superChapterId;
    @Expose
    @SerializedName("superChapterName")
    private String superChapterName;
    @Transient
    @Expose
    @SerializedName("tags")
    private List<ArticleDetailInnerData> tags;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("type")
    private int type;
    @Expose
    @SerializedName("userId")
    private int userId;
    @Expose
    @SerializedName("visible")
    private int visible;
    @Expose
    @SerializedName("zan")
    private int zan;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Expose
    private int currentPage;

    @Generated(hash = 1257434211)
    public ArticleDetailData(String apkLink, String author, int chapterId,
            String chapterName, boolean collect, int courseId, String desc,
            String envelopePic, boolean fresh, int id, String link, String niceDate,
            String origin, String projectLink, long publishTime, int superChapterId,
            String superChapterName, String title, int type, int userId,
            int visible, int zan, int currentPage) {
        this.apkLink = apkLink;
        this.author = author;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.collect = collect;
        this.courseId = courseId;
        this.desc = desc;
        this.envelopePic = envelopePic;
        this.fresh = fresh;
        this.id = id;
        this.link = link;
        this.niceDate = niceDate;
        this.origin = origin;
        this.projectLink = projectLink;
        this.publishTime = publishTime;
        this.superChapterId = superChapterId;
        this.superChapterName = superChapterName;
        this.title = title;
        this.type = type;
        this.userId = userId;
        this.visible = visible;
        this.zan = zan;
        this.currentPage = currentPage;
    }

    @Generated(hash = 1391405481)
    public ArticleDetailData() {
    }

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
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

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
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

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
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

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public List<ArticleDetailInnerData> getTags() {
        return tags;
    }

    public void setTags(List<ArticleDetailInnerData> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public boolean getCollect() {
        return this.collect;
    }

    public boolean getFresh() {
        return this.fresh;
    }
}
