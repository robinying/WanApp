package com.yubin.wanapp.activity.detail;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.BaseFragment;
import com.yubin.wanapp.data.FavoriteArticleDetailDataDao;
import com.yubin.wanapp.data.UserManager;
import com.yubin.wanapp.data.model.FavoriteArticleDataImpl;

/**
 * author : Yubin.Ying
 * time : 2018/11/6
 */
public class WebviewFragment extends BaseFragment implements DetailContract.View {

    private FrameLayout webViewContainer;
    private Toolbar toolbar;
    private String url;
    private String title;
    private long id;
    private int userId;

    private AgentWeb agentWeb;
    private boolean favorite = false;
    private DetailContract.Presenter mPresenter;

    public WebviewFragment(){

    }

    public static WebviewFragment newInstance(){
        return  new WebviewFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        toolbar = root.findViewById(R.id.toolBar);
        DetailActivity activity = (DetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        webViewContainer = root.findViewById(R.id.webview_container);
        setHasOptionsMenu(true);
        getActivity().setTitle(title);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getActivity().getIntent();
        url = intent.getStringExtra(DetailActivity.URL);
        title = intent.getStringExtra(DetailActivity.TITLE);
        id = intent.getLongExtra(DetailActivity.ID, -1);
        userId = UserManager.getUserManager().getUserId();
        loadUrl(url);
        new DetailPresenter(this, FavoriteArticleDataImpl.getInstance());
        mPresenter.updateFavArticle();

    }

    @Override
    public void onResume() {
        if (agentWeb!=null){
            agentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (agentWeb != null) {
            agentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (agentWeb != null) {
            agentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    private void loadUrl(String url){
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(webViewContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);

        WebView webView = agentWeb.getWebCreator().getWebView();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_more:
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.action_details_sheet, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
                AppCompatTextView tvFavorite = (AppCompatTextView)view.findViewById(R.id.text_view_favorite);
                Drawable offDrawable = getResources().getDrawable(R.drawable.ic_star_off);
                offDrawable.setBounds(0, 0, offDrawable.getMinimumWidth(), offDrawable.getMinimumHeight()); //设置边界
                Drawable onDrawable = getResources().getDrawable(R.drawable.ic_star_on);
                onDrawable.setBounds(0, 0, offDrawable.getMinimumWidth(), offDrawable.getMinimumHeight()); //设置边界
                favorite = UserManager.getUserManager().isFavorite(id);
                tvFavorite.setCompoundDrawables(favorite ? onDrawable : offDrawable, null, null, null);
                tvFavorite.setText(favorite ? R.string.detail_uncollect_article:R.string.detail_collect_article);
                tvFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(favorite){
                            mPresenter.uncollectArticle(userId,id);
                        }else{
                            mPresenter.collectArticle(userId,id);
                        }
                        bottomSheetDialog.dismiss();

                    }
                });
                AppCompatTextView tvCopy = (AppCompatTextView) view.findViewById(R.id.text_view_copy_link);
                tvCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        copyLink();
                        bottomSheetDialog.dismiss();
                    }
                });
                AppCompatTextView tvShare = (AppCompatTextView) view.findViewById(R.id.text_view_share);
                tvShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        share();
                        bottomSheetDialog.dismiss();
                    }
                });
                AppCompatTextView tvBrowser = (AppCompatTextView) view.findViewById(R.id.text_view_browser);
                tvBrowser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openInBrowser();
                        bottomSheetDialog.dismiss();
                    }
                });
                break;
        }
        return true;
    }

    public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        return agentWeb.handleKeyEvent(keyCode, event);
    }

    private void openInBrowser() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
        }catch (ActivityNotFoundException e){
            ToastUtils.showShort(R.string.no_browser_found);
        }
    }

    private void share() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND).setType("text/plain");
            String shareText = title + "\n" + url;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
        }catch (ActivityNotFoundException e){
            ToastUtils.showShort(R.string.no_activity_found);
        }
    }

    private void copyLink() {
        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("text", Html.fromHtml(url).toString());
        manager.setPrimaryClip(data);
        ToastUtils.showShort(R.string.detail_copy_the_link);

    }

    @Override
    public void showCollectStatus(boolean isSuccess) {
        if(isSuccess){
            mPresenter.updateFavArticle();
        }
        ToastUtils.showShort(isSuccess ? R.string.collect_article_success : R.string.collect_article_error);
    }

    @Override
    public void showUnCollectStatus(boolean isSuccess) {
        if (isSuccess) {
            UserManager.getUserManager().deleteFav(id);
        }
        ToastUtils.showShort(isSuccess ? R.string.uncollect_article_success : R.string.uncollect_article_error);

    }

    @Override
    public boolean isActive() {
        return isResumed();
    }

    @Override
    public void saveFavoriteState(boolean isFavorite) {
        favorite = isFavorite;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
