package com.jerry.android.blogapp.business.edit;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

public interface IBlogEditContract
{
    public interface IBlogEditPresenter extends IBasePresenter
    {
        void loadBlogDetail(String blogId);
        void saveBlog(String title, String summary, String content);
    }

    public interface IBlogEditView extends IBaseView<IBlogEditPresenter>
    {
        void showBlogDetail( Blog blog);
        void onSavedBlog(Blog blog);
    }
}
