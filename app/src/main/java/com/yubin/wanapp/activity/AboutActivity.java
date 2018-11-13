package com.yubin.wanapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.callback.APKDownloadListener;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.blankj.utilcode.util.ToastUtils;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.detail.DetailActivity;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.util.TDevice;
import com.yubin.wanapp.view.BaseDialog;
import com.yubin.wanapp.view.ItemView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appname)
    ItemView appname;
    @BindView(R.id.app_version)
    ItemView appVersion;
    @BindView(R.id.app_update)
    ItemView appUpdate;
    @BindView(R.id.github_iv)
    ImageView githubIv;
    @BindView(R.id.github_tv)
    TextView githubTv;
    @BindView(R.id.github_ll)
    LinearLayout githubLl;
    private DownloadBuilder builder;

    public static void show(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, AboutActivity.class));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        setTitle(R.string.action_about);
        appname.setSubTypeTitleVal(getResources().getString(R.string.app_name));
        appVersion.setSubTypeTitleVal(TDevice.getVersionName());
    }

    @Override
    protected void initData() {
        super.initData();
    }


    @OnClick({R.id.app_update,R.id.github_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_update:
                checkUpdate();
                break;
            case R.id.github_ll:
                Intent intent = new Intent(activityInstance, DetailActivity.class);
                intent.putExtra(DetailActivity.URL, "https://github.com/robinying/WanApp");
                intent.putExtra(DetailActivity.TITLE, getResources().getString(R.string.app_name));
                intent.putExtra(DetailActivity.ID, -1);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkUpdate() {
        builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl("https://www.baidu.com")
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        Toast.makeText(activityInstance, "request successful", Toast.LENGTH_SHORT).show();
                        return crateUIData();
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                        Toast.makeText(activityInstance, "request failed", Toast.LENGTH_SHORT).show();

                    }
                });
        builder.setApkDownloadListener(new APKDownloadListener() {
            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadSuccess(File file) {
                ToastUtils.showShort(file.getPath());
            }

            @Override
            public void onDownloadFail() {

            }
        });
        builder.setCustomVersionDialogListener(createCustomDialog());
        builder.executeMission(activityInstance);
    }

    private CustomVersionDialogListener createCustomDialog() {
        return (context, versionBundle) -> {
            BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.custom_dialog_layout);
            TextView textView = baseDialog.findViewById(R.id.tv_msg);
            textView.setText(versionBundle.getContent());
            baseDialog.setCanceledOnTouchOutside(true);
            return baseDialog;
        };
    }

    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private UIData crateUIData() {
        UIData uiData = UIData.create();
        uiData.setTitle(getString(R.string.update_title));
        uiData.setDownloadUrl("https://download.fir.im/apps/5be5229f959d692aa2c65d24/install?download_token=e08b0d121f7a1e29152ad72d78a9a82c");
        uiData.setContent(getString(R.string.updatecontent));
        return uiData;
    }

}
