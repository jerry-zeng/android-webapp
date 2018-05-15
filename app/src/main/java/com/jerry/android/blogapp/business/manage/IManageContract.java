package com.jerry.android.blogapp.business.manage;

import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

public interface IManageContract
{
    public interface IManagePresenter extends IBasePresenter
    {
        void loadData(int type, int page);
    }

    public interface IManageView extends IBaseView<IManagePresenter>
    {
        void addDataList( Object list );

        void showProgress();

        void hideProgress();

        void showLoadFailMsg();
    }
}
