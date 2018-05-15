package com.jerry.android.blogapp.business.blog;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

public interface IBlogDetailContract
{
    public interface IBlogDetailPresenter extends IBasePresenter
    {
        void loadBlogDetail(String blogId);
        void sendComment(String content);
    }

    public interface IBlogDetailView extends IBaseView<IBlogDetailPresenter>
    {
        void showBlogDetail( Blog blog );
        void addComment( Comment comment );
    }
}
