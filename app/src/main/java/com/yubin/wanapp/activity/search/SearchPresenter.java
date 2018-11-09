package com.yubin.wanapp.activity.search;

import com.yubin.wanapp.data.ArticleData;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.HotkeyData;
import com.yubin.wanapp.data.model.GetArticlesData;
import com.yubin.wanapp.retrofit.RetrofitClient;
import com.yubin.wanapp.retrofit.RetrofitService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * author : Yubin.Ying
 * time : 2018/11/8
 */
public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private GetArticlesData getArticlesData;

    public SearchPresenter(SearchContract.View view, GetArticlesData getArticlesData){
        mView = view;
        mView.setPresenter(this);
        this.getArticlesData = getArticlesData;
    }
    @Override
    public void getQueryList(int page, String key) {
        getArticlesData.queryArticles(page ,key,true,false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> value) {
                        if(value!=null&& value.size()>0) {
                            mView.showQueryArticles(value);
                            mView.showEmptyView(false);
                        }else {
                            mView.showEmptyView(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showEmptyView(true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getHotkey() {
        Observable<List<HotkeyData.HotKeyDetail>> list = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getHotkey()
                .filter(new Predicate<HotkeyData>() {
                    @Override
                    public boolean test(HotkeyData hotkeyData) throws Exception {
                        return hotkeyData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<HotkeyData, ObservableSource<List<HotkeyData.HotKeyDetail>>>() {
                    @Override
                    public ObservableSource<List<HotkeyData.HotKeyDetail>> apply(HotkeyData hotkeyData) throws Exception {
                        return Observable.just(hotkeyData.getData());
                    }
                });
        list.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<HotkeyData.HotKeyDetail>>() {

                    @Override
                    public void onNext(List<HotkeyData.HotKeyDetail> value) {
                        mView.showHotkey(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
