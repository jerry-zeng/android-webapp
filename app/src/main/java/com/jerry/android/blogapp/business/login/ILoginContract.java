package com.jerry.android.blogapp.business.login;

import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

public interface ILoginContract
{
    public interface ILoginPresenter extends IBasePresenter
    {
        void login(String email, String password);
    }

    public interface ILoginView extends IBaseView<ILoginPresenter>
    {
        void onLogin( User user);
    }
}
