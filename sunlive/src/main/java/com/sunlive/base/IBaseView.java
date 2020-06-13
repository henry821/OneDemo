package com.sunlive.base;

public interface IBaseView<T> {

    void showData(T t);

    void showLoadingView();

    void hideLoadingView();

}
