package com.yubin.wanapp.activity.projcet;

import com.yubin.wanapp.data.Projecttree;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/13
 */
public interface ProjectContract {
    interface Presenter extends BasePresenter{
        void getProjectListData();
    }

    interface View extends BaseView<Presenter>{
        void showProjectList(List<Projecttree.ProjectBean> value);
        boolean isActive();
    }
}
