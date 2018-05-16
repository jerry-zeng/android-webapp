package com.jerry.android.blogapp.business.login;

import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

public interface IRegisterContract
{
    public interface IRegisterPresenter extends IBasePresenter
    {
        void register(String name, String email, String password);
    }

    public interface IRegisterView extends IBaseView<IRegisterPresenter>
    {
        void onRegister( User user);
    }
}
