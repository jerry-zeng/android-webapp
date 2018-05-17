package com.jerry.android.blogapp.business.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.user.UserDetailActivity;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;

public class ManageUsersFragment extends BaseFragment implements IMangeUsersContract.IManageUsersView
{
    private static final String TAG = "ManageUsersFragment";

    private IMangeUsersContract.IManageUsersPresenter _presenter;


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
    public void addDataList( List<User> list )
    {

    }

    @Override
    public void showLoadFailMsg()
    {
        Debug.log( TAG, "load data list failed" );
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


    private void OnClickItem()
    {
        String userId = "001526130925642d9fc4d674154402484fa79870530cb46000";

        Intent intent = new Intent( getContext(), UserDetailActivity.class );
        intent.putExtra( "userId", userId );
        startActivity( intent );
    }
}
