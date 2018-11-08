package com.yubin.wanapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.yubin.wanapp.util.ImageLoader;
import com.yubin.wanapp.R;
import com.yubin.wanapp.interf.IDialogController;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public abstract class BaseFragment extends Fragment {
    protected String TAG;
    protected Context mContext;
    protected Fragment mFragment;
    protected FragmentActivity mHolderActivity;

    protected View mRoot;
    protected Bundle mBundle;
    protected LayoutInflater mInflater;
    private Unbinder unbinder;
    private RequestManager mImgLoader;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG,"setUserVisibleHint");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG,"onDetach");
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        Log.e(TAG,"onCreate");
        mFragment = this;
        mHolderActivity=getActivity();
        mBundle = getArguments();
        initBundle(mBundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");

        mRoot = inflater.inflate(getLayoutId(), container, false);
        mInflater = inflater;
        // Do something
        onBindViewBefore(mRoot);
        // Bind view
        unbinder = ButterKnife.bind(this, mRoot);
        // Get savedInstanceState
        if (savedInstanceState != null)
            onRestartInstance(savedInstanceState);
        // Init
        initView(mRoot);
        initData();
        bindEvent();

        return mRoot;
    }

    protected void onBindViewBefore(View root) {
        // ...
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
        if (unbinder != null) {
            unbinder.unbind();
        }
        mBundle = null;
        mFragment = null;
    }

    protected abstract int getLayoutId();

    protected void initBundle(Bundle bundle) {

    }

    protected void initView(View root) {

    }

    protected void initData() {

    }

    protected void bindEvent() {

    }

    protected <T extends View> T findView(int viewId) {
        return (T) mRoot.findViewById(viewId);
    }

    protected <T extends Serializable> T getBundleSerializable(String key) {
        if (mBundle == null) {
            return null;
        }
        return (T) mBundle.getSerializable(key);
    }

    /**
     * 获取一个图片加载管理器
     *
     * @return RequestManager
     */
    public synchronized RequestManager getImgLoader() {
        if (mImgLoader == null)
            mImgLoader = Glide.with(this);
        return mImgLoader;
    }

    /***
     * 从网络中加载数据
     *
     * @param viewId   view的id
     * @param imageUrl 图片地址
     */
    protected void setImageFromNet(int viewId, String imageUrl) {
        setImageFromNet(viewId, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param viewId      view的id
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(int viewId, String imageUrl, int placeholder) {
        ImageView imageView = findView(viewId);
        setImageFromNet(imageView, imageUrl, placeholder);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView imageView
     * @param imageUrl  图片地址
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl) {
        setImageFromNet(imageView, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView   imageView
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl, int placeholder) {
        ImageLoader.loadImage(getImgLoader(), imageView, imageUrl, placeholder);
    }


    protected void setText(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            return;
        }
        textView.setText(text);
    }

    protected void setText(int viewId, String text, String emptyTip) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setText(emptyTip);
            return;
        }
        textView.setText(text);
    }

    protected void setTextEmptyGone(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setText(text);
    }

    protected <T extends View> T setGone(int id) {
        T view = findView(id);
        view.setVisibility(View.GONE);
        return view;
    }

    protected <T extends View> T setVisibility(int id) {
        T view = findView(id);
        view.setVisibility(View.VISIBLE);
        return view;
    }

    protected void setInVisibility(int id) {
        findView(id).setVisibility(View.INVISIBLE);
    }

    protected void onRestartInstance(Bundle bundle) {

    }


    /**
     * 显示加载等待视图,显示"加载中..."
     *
     * @return
     */
    protected ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    /**
     * 显示加载等待视图,显示传入字符串资源值
     *
     * @param resid
     * @return
     */
    protected ProgressDialog showWaitDialog(int resid) {
        FragmentActivity activity = getActivity();
        if (activity instanceof IDialogController) {
            return ((IDialogController) activity).showWaitDialog(resid);
        }
        return null;
    }

    /**
     * 显示加载等待视图,显示传入字符串
     *
     * @param message
     * @return
     */
    protected ProgressDialog showWaitDialog(String message) {
        FragmentActivity activity = getActivity();
        if (activity instanceof IDialogController) {
            return ((IDialogController) activity).showWaitDialog(message);
        }
        return null;
    }

    /**
     * 隐藏加载等待视图
     */
    protected void hideWaitDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof IDialogController) {
            ((IDialogController) activity).hideWaitDialog();
        }
    }
}
