package com.jerry.android.blogapp.business.blogs;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

import java.util.List;

public interface IBlogsContract
{
    public interface IBlogsPresenter extends IBasePresenter
    {
        void loadData(int page, int size);
    }

    public interface IBlogsView extends IBaseView<IBlogsPresenter>
    {
        void addDataList( List<Blog> list );

        void showProgress();

        void hideProgress();

        void showLoadFailMsg();
    }
}
