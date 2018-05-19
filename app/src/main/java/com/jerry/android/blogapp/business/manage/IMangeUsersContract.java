package com.jerry.android.blogapp.business.manage;

import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

import java.util.List;

public interface IMangeUsersContract
{
    public interface IManageUsersPresenter extends IBasePresenter
    {
        void loadData(int page, int size);
        Page getCurrentPage();
    }

    public interface IManageUsersView extends IBaseView<IManageUsersPresenter>
    {
        void addDataList( List<User> list );

        void showLoadFailMsg();
    }
}
