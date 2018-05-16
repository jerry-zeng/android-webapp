package com.jerry.android.blogapp.business.manage;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;

public class ManageCommentsFragment extends BaseFragment implements IManageCommentsContract.IManageCommentsView
{
    private static final String TAG = "ManageCommentsFragment";

    private IManageCommentsContract.IManageCommentsPresenter _presenter;


    @Override
    public void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

    }

    @Override
    public void onDestroy()
    {
        _presenter.destroy();
        _presenter = null;

        super.onDestroy();
    }

    @Override
    public void addDataList( List<Comment> list )
    {

    }

    @Override
    public void onDeleteComment( String commentId )
    {
        Debug.log( TAG, "deleted comment " + commentId );
    }

    @Override
    public void showLoadFailMsg()
    {
        Debug.log( TAG, "load data list failed" );
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
