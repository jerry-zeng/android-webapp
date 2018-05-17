package com.jerry.android.blogapp.business.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.business.utils.ImageLoader;
import com.jerry.android.blogapp.framework.BaseSwipeBackActivity;

public class UserDetailActivity extends BaseSwipeBackActivity implements IUserDetailContract.IUserView
{
    private static final String TAG = "UserDetailActivity";

    private IUserDetailContract.IUserPresenter _presenter;

    private ImageView icon;
    private TextView txtID;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtLastLogin;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_user );

        icon = (ImageView)findViewById( R.id.icon );
        txtID = (TextView)findViewById( R.id.txt_id );
        txtName = (TextView)findViewById( R.id.txt_name );
        txtEmail = (TextView)findViewById( R.id.txt_email );
        txtLastLogin = (TextView)findViewById( R.id.txt_last_login );

        _presenter = new UserDetailPresenter( this );
        _presenter.start();

        // get user id from previous Activity.
        Intent intent = getIntent();
        //String userId = intent.getStringExtra( "userId" );
        //_presenter.loadUserDetail( userId );

        User user = (User)intent.getSerializableExtra( "user" );
        showUserDetail( user );
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

        ImageLoader.display( this, icon, user.getImage() );
        txtID.setText( "ID: " + user.getId() );
        txtName.setText( "Name: " + user.getName() );
        txtEmail.setText( "Email: " + user.getEmail() );
        txtLastLogin.setText( "Last Login: " + Float.toString( user.getLast_login() )  );
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
