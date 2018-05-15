package com.jerry.android.blogapp.business.manage;

import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;

public class ManageCommentsFragment extends BaseFragment implements IManageCommentsContract.IManageCommentsView
{
    private IManageCommentsContract.IManageCommentsPresenter _presenter;


    @Override
    public void addDataList( List<Comment> list )
    {

    }

    @Override
    public void onDeleteComment( String commentId )
    {

    }

    @Override
    public void showLoadFailMsg()
    {

    }

    @Override
    public void setPresenter( IManageCommentsContract.IManageCommentsPresenter presenter )
    {
        this._presenter = presenter;
    }

    @Override
    public void showError( String msg )
    {
        showToast( msg );
    }

    @Override
    public void showProgress()
    {

    }

    @Override
    public void hideProgress()
    {

    }
}
