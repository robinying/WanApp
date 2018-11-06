package com.yubin.wanapp.util;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.scwang.smartrefresh.header.material.CircleImageView;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public class ImageLoader {

    private ImageLoader() {
    }

    public static void loadImage(RequestManager loader, ImageView view, String url) {
        loadImage(loader, view, url, 0);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url, int placeholder) {
        loadImage(loader, view, url, placeholder, placeholder);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url, int placeholder, int error) {
        boolean isCenterCrop = false;
        if (view instanceof CircleImageView)
            isCenterCrop = true;
        loadImage(loader, view, url, placeholder, error, isCenterCrop);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url, int placeholder, int error, boolean isCenterCrop) {
        if (TextUtils.isEmpty(url)) {
            view.setImageResource(placeholder);
        } else {
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(placeholder);
            if (isCenterCrop) {
                options = options.centerCrop();
            }
            if (view instanceof CircleImageView) {

//                BitmapRequestBuilder builder = loader.load(url).asBitmap()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(placeholder)
//                        .error(error);


                loader.asBitmap()
                        .apply(options)
                        .load(url)
                        .into(
                                new BitmapImageViewTarget(view) {
                                    @Override
                                    protected void setResource(Bitmap resource) {
                                        RoundedBitmapDrawable circularBitmapDrawable =
                                                RoundedBitmapDrawableFactory.create(view.getResources(), resource);
                                        circularBitmapDrawable.setCircular(true);
                                        view.setImageDrawable(circularBitmapDrawable);
                                    }
                                });
            } else {
                loader.load(url)
                        .apply(options)
                        .into(view);
            }
        }
    }
}
