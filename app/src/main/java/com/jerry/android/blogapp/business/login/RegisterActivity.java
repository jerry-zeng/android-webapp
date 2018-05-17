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
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseAppCompatActivity;

public class RegisterActivity extends BaseAppCompatActivity implements IRegisterContract.IRegisterView
{
    private static final String TAG = "RegisterActivity";

    private IRegisterContract.IRegisterPresenter _presenter;

    private EditText mNameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordView2;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_register );

        mNameView = (EditText)findViewById( R.id.input_name );
        mEmailView = (EditText)findViewById( R.id.input_email );

        mPasswordView = (EditText)findViewById( R.id.input_password );

        mPasswordView2 = (EditText)findViewById( R.id.input_password2 );
        mPasswordView2.setOnEditorActionListener( new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction( TextView textView, int id, KeyEvent keyEvent )
            {
                if( id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL ){
                    attemptRegister();
                    return true;
                }
                return false;
            }
        } );

        Button mRegisterButton = (Button)findViewById( R.id.btn_register );
        mRegisterButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                attemptRegister();
            }
        } );

        mLoginFormView = findViewById( R.id.login_form );
        mProgressView = findViewById( R.id.login_progress );

        _presenter = new RegisterPresenter( this );
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
    public void onRegister( User user )
    {
        Debug.log( TAG, user.toString() );

        Intent intent = new Intent();
        intent.putExtra( "email", user.getEmail() );
        intent.putExtra( "password", "" );
        setResult( 2, intent );

        finish();
    }

    @Override
    public void setPresenter( IRegisterContract.IRegisterPresenter presenter )
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
        if( _presenter.isWorking() ){
            return;
        }

        // Reset errors.
        mNameView.setError( null );
        mEmailView.setError( null );
        mPasswordView.setError( null );
        mPasswordView2.setError( null );

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString().trim();
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        String password2 = mPasswordView2.getText().toString().trim();

        View focusView = null;

        if( !isNameValid( name ) ){
            mNameView.setError( getString( R.string.error_field_required ) );
            focusView = mNameView;
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

        // Check for a valid password, if the user entered one.
        if( !TextUtils.isEmpty( password ) && !isPasswordValid( password ) ){
            mPasswordView.setError( getString( R.string.error_invalid_password ) );
            focusView = mPasswordView;
        }

        if( !password2.equals( password ) ){
            mPasswordView2.setError( getString( R.string.error_diff_password ) );
            focusView = mPasswordView2;
        }

        if( focusView != null ){
            focusView.requestFocus();
        }
        else{
            _presenter.register( name, email, password );
        }
    }

    private boolean isNameValid(String name)
    {
        int length = name.length();
        return length >= 2 && length <= 10;
    }

    private boolean isEmailValid( String email )
    {
        //TODO: Replace this with your own logic
        return email.contains( "@" );
    }

    private boolean isPasswordValid( String password )
    {
        //TODO: Replace this with your own logic
        int length = password.length();
        return length >= 4 && length <= 16;
    }
}
