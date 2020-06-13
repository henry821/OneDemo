package com.sunlive.base;

public interface IBasePresenter<T> {

    void loadDataFromNet();

    void loadDataFromLocal();

    void onPreLoad();

    void onLoading();

    void onLoadSuccess(T t);

    void onLoadFail();

}
