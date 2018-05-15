package com.jerry.android.blogapp.business.edit;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

public interface IBlogEditContract
{
    public interface IBlogEditPresenter extends IBasePresenter
    {
        void loadBlogDetail(String userId);
        void saveBlog();
    }

    public interface IBlogEditView extends IBaseView<IBlogEditPresenter>
    {
        void showBlog( Blog blog);
    }
}
