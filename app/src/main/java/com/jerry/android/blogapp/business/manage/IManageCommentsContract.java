package com.jerry.android.blogapp.business.manage;

import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.framework.IBasePresenter;
import com.jerry.android.blogapp.framework.IBaseView;

import java.util.List;

public interface IManageCommentsContract
{
    public interface IManageCommentsPresenter extends IBasePresenter
    {
        void loadData(int page, int size);
        void delete(String commentId);
    }

    public interface IManageCommentsView extends IBaseView<IManageCommentsPresenter>
    {
        void addDataList( List<Comment> list );
        void onDeleteComment(String commentId);

        void showLoadFailMsg();
    }
}
