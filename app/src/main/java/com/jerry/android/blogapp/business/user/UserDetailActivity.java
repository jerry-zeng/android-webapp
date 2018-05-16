package com.jerry.android.blogapp.business.user;

import android.os.Bundle;

import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseSwipeBackActivity;

public class UserDetailActivity extends BaseSwipeBackActivity implements IUserDetailContract.IUserView
{
    private static final String TAG = "UserDetailActivity";

    private IUserDetailContract.IUserPresenter _presenter;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //setContentView(  );

        _presenter = new UserDetailPresenter( this );
        _presenter.start();

        //TODO: get user id from previous Activity.
        String userId = "001526130925642d9fc4d674154402484fa79870530cb46000";
        _presenter.loadUserDetail( userId );
    }

    @Override
    protected void onDestroy()
    {
        _presenter.destroy();
        _presenter = null;

        super.onDestroy();
    }


    @Override
    public void showUserDetail( User user )
    {
        Debug.log( TAG, user.toString() );
    }

    @Override
    public void showProgress()
    {

    }
    @Override
    public void hideProgress()
    {

    }

    @Override
    public void setPresenter( IUserDetailContract.IUserPresenter presenter )
    {
        this._presenter = presenter;
    }

    @Override
    public void showError( String msg )
    {
        showToast( msg );
    }
}
