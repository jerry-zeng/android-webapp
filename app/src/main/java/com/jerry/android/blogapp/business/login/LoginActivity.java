package com.jerry.android.blogapp.business.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.MainActivity;
import com.jerry.android.blogapp.business.MyApplication;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseAppCompatActivity;


public class LoginActivity extends BaseAppCompatActivity implements ILoginContract.ILoginView
{
    private static final String TAG = "LoginActivity";

    private final static int REQUEST_CODE = 1; // 返回的结果码.

    private ILoginContract.ILoginPresenter _presenter;

    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_login );

        // Set up the login form.
        mEmailView = (EditText)findViewById( R.id.input_email );

        mPasswordView = (EditText)findViewById( R.id.input_password );
        mPasswordView.setOnEditorActionListener( new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction( TextView textView, int id, KeyEvent keyEvent )
            {
                if( id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL ){
                    attemptLogin();
                    return true;
                }
                return false;
            }
        } );

        Button mEmailSignInButton = (Button)findViewById( R.id.btn_login );
        mEmailSignInButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                attemptLogin();
            }
        } );

        TextView registerButton = (TextView)findViewById( R.id.btn_register );
        registerButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                attemptRegister();
            }
        } );

        mLoginFormView = findViewById( R.id.login_form );
        mProgressView = findViewById( R.id.login_progress );

        _presenter = new LoginPresenter( this );
        _presenter.start();
    }

    @Override
    protected void onDestroy()
    {
        _presenter.destroy();
        _presenter = null;

        super.onDestroy();
    }


    @Override
    public void onLogin( User user )
    {
        MyApplication.getInstance().setCurrentUser( user );

        Debug.log( TAG, user.toString() );

        Intent intent = new Intent( LoginActivity.this, MainActivity.class );
        startActivity( intent );

        finish();
    }

    @Override
    public void setPresenter( ILoginContract.ILoginPresenter presenter )
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
        showProgress( true );
    }

    @Override
    public void hideProgress()
    {
        showProgress( false );
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress( final boolean show )
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 ){
            int shortAnimTime = getResources().getInteger( android.R.integer.config_shortAnimTime );

            mLoginFormView.setVisibility( show ? View.GONE : View.VISIBLE );
            mLoginFormView.animate().setDuration( shortAnimTime ).alpha(
                    show ? 0 : 1 ).setListener( new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd( Animator animation )
                {
                    mLoginFormView.setVisibility( show ? View.GONE : View.VISIBLE );
                }
            } );

            mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
            mProgressView.animate().setDuration( shortAnimTime ).alpha(
                    show ? 1 : 0 ).setListener( new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd( Animator animation )
                {
                    mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
                }
            } );
        }
        else{
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
            mLoginFormView.setVisibility( show ? View.GONE : View.VISIBLE );
        }
    }

    private void attemptRegister()
    {
        Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
        startActivityForResult( intent, REQUEST_CODE );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult( requestCode, resultCode, data );

        if( resultCode == 2 ){
            if(requestCode == REQUEST_CODE){
                String email = data.getStringExtra("email");
                String password = data.getStringExtra("password");
                mEmailView.setText( email );
                mPasswordView.setText( password );
            }
        }
    }

    private void attemptLogin()
    {
        if( _presenter.isWorking() ){
            return;
        }

        // Reset errors.
        mEmailView.setError( null );
        mPasswordView.setError( null );

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        View focusView = null;

        // Check for a valid password, if the user entered one.
        if( !TextUtils.isEmpty( password ) && !isPasswordValid( password ) ){
            mPasswordView.setError( getString( R.string.error_invalid_password ) );
            focusView = mPasswordView;
        }

        // Check for a valid email address.
        if( TextUtils.isEmpty( email ) ){
            mEmailView.setError( getString( R.string.error_field_required ) );
            focusView = mEmailView;
        }
        else if( !isEmailValid( email ) ){
            mEmailView.setError( getString( R.string.error_invalid_email ) );
            focusView = mEmailView;
        }

        if( focusView != null ){
            focusView.requestFocus();
        }
        else{
            _presenter.login( email, password );
        }
    }

    private boolean isEmailValid( String email )
    {
        return email.contains( "@" );
    }

    private boolean isPasswordValid( String password )
    {
        int length = password.length();
        return length >= 4 && length <= 16;
    }


}
