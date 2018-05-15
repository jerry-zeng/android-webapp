package com.jerry.android.blogapp.framework;

public interface IBaseView<T>
{
    void setPresenter(T presenter);

    void showError(String msg);

    void showProgress();

    void hideProgress();
}
