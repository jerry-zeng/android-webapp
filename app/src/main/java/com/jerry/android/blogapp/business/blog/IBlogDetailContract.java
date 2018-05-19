package com.jerry.android.blogapp.business.blog;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

import java.util.List;

public interface IBlogDetailContract
{
    public interface IBlogDetailPresenter extends IBasePresenter
    {
        void loadBlogDetail(String blogId);
        void loadBlogComments();
        void sendComment(String content);

        void setCurrentBlog(Blog blog);
    }

    public interface IBlogDetailView extends IBaseView<IBlogDetailPresenter>
    {
        void showBlogDetail( Blog blog );
        void showComments( List<Comment> list);
        void addComment( Comment comment );

        void showLoadFailMsg();
    }
}
