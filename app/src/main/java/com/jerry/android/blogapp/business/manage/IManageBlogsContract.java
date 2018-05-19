package com.jerry.android.blogapp.business.manage;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

import java.util.List;

public interface IManageBlogsContract
{
    public interface IManageBlogsPresenter extends IBasePresenter
    {
        void loadData(int page, int size);
        void delete(String blogId);
        Page getCurrentPage();
    }

    public interface IManageBlogsView extends IBaseView<IManageBlogsPresenter>
    {
        void addDataList( List<Blog> list );
        void onDeleteBlog(String blogId);

        void showLoadFailMsg();
    }
}
