package com.yubin.wanapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.yubin.wanapp.R;
import com.yubin.wanapp.util.ImageLoader;

/**
 * author : Yubin.Ying
 * time : 2018/11/9
 */
public class ItemView extends RelativeLayout {
    private View rootView;
    private ImageView iv;
    private TextView mTvTitle;
    private View mLineTop, mLineBottom;
    private ViewStub vsRight, vsRight2;
    private View vsView;

    private TextView mType2Tv;
    private ClearEditText mTypeCet;
    private ImageView ivArrow;



    private boolean isShowImg, isShowTopLine, isShowBottomLine;
    private String mTitle, mStubTypeTitle, mHint;
    private int topLineMarginLeft;
    private int mStubTypeTitleColor;
    private int mStubType;

    Drawable mImgDrawable;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//        if (isInEditMode()) {
//            return;
//        }
        rootView = LayoutInflater.from(context).inflate(R.layout.widget_item, this);
        iv = (ImageView) rootView.findViewById(R.id.iv);
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        vsRight = (ViewStub) rootView.findViewById(R.id.vs_right);
        vsRight2 = (ViewStub) rootView.findViewById(R.id.vs_right_2);
        mLineTop = rootView.findViewById(R.id.v_line_top);
        mLineBottom = rootView.findViewById(R.id.v_line_bottom);


        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ItemView, defStyleAttr, 0);
        isShowImg = typedArray.getBoolean(R.styleable.ItemView_show_img, true);
        isShowTopLine = typedArray.getBoolean(R.styleable.ItemView_show_top_line, true);
        isShowBottomLine = typedArray.getBoolean(R.styleable.ItemView_show_bottom_line, true);
        topLineMarginLeft = (int) typedArray.getDimension(R.styleable.ItemView_top_line_margin_left, 0);
        mImgDrawable = typedArray.getDrawable(R.styleable.ItemView_img_src);
        mTitle = typedArray.getString(R.styleable.ItemView_itemtitle);
        mStubTypeTitle = typedArray.getString(R.styleable.ItemView_stub_type_title);
        mHint = typedArray.getString(R.styleable.ItemView_itemhint);
        mStubTypeTitleColor = typedArray.getColor(R.styleable.ItemView_stub_type_title_color, getResources().getColor(R.color.gray));
        mStubType = typedArray.getInt(R.styleable.ItemView_stub_type, 0);
        typedArray.recycle();

        setUI(context);
    }

    private void setUI(Context context) {

        if (mImgDrawable != null) {
            isShowImg = true;
            setImg(mImgDrawable);
        }
        if (!TextUtils.isEmpty(mTitle)) {
            setTitle(mTitle);
        }

        setShowTopLine(isShowTopLine);
        setShowBottomLine(isShowBottomLine);


        switch (mStubType) {
            case 0: //右箭头
                setStubLayoutId(R.layout.widget_arrows_right_gray);
                break;
            case 2: //右箭头 右文本
                setStubLayoutId(R.layout.widget_item_title_arrows_right_gray);
                ivArrow = (ImageView) vsView.findViewById(R.id.iv_arrow);
                mType2Tv = (TextView) vsView.findViewById(R.id.tv_info);
                mType2Tv.setText(mStubTypeTitle);
                mType2Tv.setTextColor(mStubTypeTitleColor);
                break;
            case 3: //右箭头 左文本
                setStubLayoutId(R.layout.widget_item_keyval);
                ivArrow = (ImageView) vsView.findViewById(R.id.iv_arrow);
                mType2Tv = (TextView) vsView.findViewById(R.id.tv_info);
                mType2Tv.setText(mStubTypeTitle);
                mType2Tv.setTextColor(mStubTypeTitleColor);
                break;
            case 4: //左文本
                setStubLayoutId(R.layout.widget_item_keyval_no_arrowl);

                mType2Tv = (TextView) vsView.findViewById(R.id.tv_info);
                mType2Tv.setText(mStubTypeTitle);
                mType2Tv.setTextColor(mStubTypeTitleColor);
                break;
            case 5: //编辑框
                setStubLayoutId(R.layout.widget_item_edit);
                mTypeCet = (ClearEditText) vsView.findViewById(R.id.cet_info);
                mTypeCet.setHint(mHint);
                break;

            case 6: //右文本
                setStubLayoutId(R.layout.widget_item_keyval_no_arrowl);
                mType2Tv = (TextView) vsView.findViewById(R.id.tv_info);
                mType2Tv.setText(mStubTypeTitle);
                mType2Tv.setTextColor(mStubTypeTitleColor);
                mType2Tv.setGravity(Gravity.RIGHT | Gravity.END);
                break;
        }
    }

    public void setImg(Drawable drawable) {
        if (iv != null && drawable != null) {
            iv.setImageDrawable(drawable);
            iv.setVisibility(VISIBLE);
        }
    }

    public void setImg(int imgRes) {
        if (iv != null) {
            iv.setImageResource(imgRes);
            iv.setVisibility(VISIBLE);
        }
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView   imageView
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl, int placeholder, RequestManager imgLoader) {
        ImageLoader.loadImage(imgLoader, imageView, imageUrl, placeholder);
    }

    public void setShowTopLine(boolean isShown) {
        isShowTopLine = isShown;
        if (topLineMarginLeft != 0) {
            LayoutParams layoutParams = (LayoutParams) mLineTop.getLayoutParams();
            layoutParams.setMargins(topLineMarginLeft, 0, 0, 0);
        }

        setViewVisiable(mLineTop, isShown);
    }

    public void setShowBottomLine(boolean isShown) {
        isShowBottomLine = isShown;

        setViewVisiable(mLineBottom, isShown);
    }

    private void setViewVisiable(View v, boolean isShown) {
        if (v != null) {
            v.setVisibility(isShown ? VISIBLE : INVISIBLE);
        }
    }

    public void setTitle(String title) {
        if (mTvTitle != null) {
            mTvTitle.setVisibility(VISIBLE);
            mTvTitle.setText(title);
        }
    }

    /**
     * ni_stub_type为title nival_left_no nival_left_arrow nikeyvalfightno时，设置子标题
     *
     * @param val
     */
    public void setSubTypeTitleVal(String val) {
        if ((mStubType == 2 || mStubType == 3 || mStubType == 4 || mStubType == 6) && mType2Tv != null) {
            mType2Tv.setText(val);
        }
    }


    /**
     * ni_stub_type为 niedit时，设置edittext 值
     *
     * @param val
     */
    public void setEtVal(String val) {
        if (mStubType == 5 && mTypeCet != null) {
            mTypeCet.setText(val);
        }
    }

    /**
     * ni_stub_type为 niedit时，设置edittext hint
     *
     * @param val
     */
    public void setEtHint(String val) {
        if (mStubType == 5 && mTypeCet != null) {
            mHint = val;
            mTypeCet.setHint(mHint);
        }
    }

    public void setEtInputIdCard() {
        setEtInputLimit("1234567890Xx");
    }

    public void setEtInputNumber() {
        setEtInputLimit("1234567890");
    }

    /**
     * 限制输入框可输入字符
     *
     * @param allowDigits 允许输入的字符
     */
    public void setEtInputLimit(String allowDigits) {
        if (mStubType == 5 && mTypeCet != null) {
            mTypeCet.setKeyListener(DigitsKeyListener.getInstance(allowDigits));
        }
    }

    /**
     * ni_stub_type 为niedit时，设置光标于末尾
     *
     * @param index
     */
    public void setEtCursorLast(int index) {
        if (mStubType == 5 && mTypeCet != null) {
            mTypeCet.setSelection(index);
        }
    }

    /**
     * ni_stub_type 为niedit时，设置获取焦点（光标）
     */
    public void etRequestFocus() {
        if (mStubType == 5 && mTypeCet != null) {
            mTypeCet.requestFocus();
        }
    }

    public void setEtEnable(boolean enable) {
        if (mStubType == 5 && mTypeCet != null) {
            mTypeCet.setEnabled(enable);
            mTypeCet.setFocusable(enable);
            mTypeCet.setFocusableInTouchMode(enable);
        }
    }

    /**
     * 获取 Edittext 内容
     * <br> 注：仅当ni_stub_type="niedit" 有效
     *
     * @return
     */
    public String getCetVal() {
        if (mTypeCet != null) {
            return mTypeCet.getText().toString();
        }
        return "";
    }

    public void setCetType(int type) {
        if (mTypeCet != null) {
            mTypeCet.setInputType(type);
        }
    }

    public EditText getCet() {
        if (mTypeCet != null) {
            return mTypeCet;
        }
        return null;
    }

    public String getStubTitle() {
        if (mType2Tv != null) {
            return mType2Tv.getText().toString();
        }
        return null;
    }

    public void hideArrow() {
        setViewVisiable(ivArrow, false);
    }

    public void showArrow() {
        setViewVisiable(ivArrow, true);
    }


    /**
     * 添加Edittext 文本监听
     * <br> 注：仅当ni_stub_type="niedit" 有效
     *
     * @param watcher
     */
    public void addCetTextChangedListener(TextWatcher watcher) {
        if (mTypeCet != null && watcher != null) {
            mTypeCet.addTextChangedListener(watcher);
        }
    }

    public void setStubLayoutId(int layoutResource) {
        vsRight.setLayoutResource(layoutResource);
        vsView = vsRight.inflate();
    }

    public void setStubLayoutId2(int layoutResource) {
        vsRight2.setLayoutResource(layoutResource);
        vsView = vsRight2.inflate();
    }
}
