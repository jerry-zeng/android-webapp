package com.jerry.android.blogapp.business.user;

import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

public interface IUserDetailContract
{
    public interface IUserPresenter extends IBasePresenter
    {
        void loadUserDetail(String userId);
    }

    public interface IUserView extends IBaseView<IUserPresenter>
    {
        void showUserDetail( User user);

        void showProgress();

        void hideProgress();
    }
}
