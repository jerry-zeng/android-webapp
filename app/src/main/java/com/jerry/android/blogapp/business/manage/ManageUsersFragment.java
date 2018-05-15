package com.jerry.android.blogapp.business.manage;

import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;

public class ManageUsersFragment extends BaseFragment implements IMangeUsersContract.IManageUsersView
{
    private IMangeUsersContract.IManageUsersPresenter _presenter;


    @Override
    public void addDataList( List<User> list )
    {

    }

    @Override
    public void showLoadFailMsg()
    {

    }

    @Override
    public void setPresenter( IMangeUsersContract.IManageUsersPresenter presenter )
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
