package com.jerry.android.blogapp.business.edit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseSwipeBackActivity;

public class BlogEditActivity extends BaseSwipeBackActivity implements IBlogEditContract.IBlogEditView
{
    private static final String TAG = "BlogEditActivity";

    private IBlogEditContract.IBlogEditPresenter _presenter;

    private EditText inputTitle;
    private EditText inputContent;
    private Button btnSubmit;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_blog_edit );

        inputTitle = (EditText)findViewById( R.id.input_title );

        inputContent = (EditText)findViewById( R.id.input_content );
        inputContent.setOnEditorActionListener( new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction( TextView textView, int id, KeyEvent keyEvent )
            {
                if( id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL ){
                    attemptSubmit();
                    return true;
                }
                return false;
            }
        } );

        btnSubmit = (Button)findViewById( R.id.btn_submit );
        btnSubmit.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                attemptSubmit();
            }
        } );

        _presenter = new BlogEditPresenter( this );
        _presenter.start();

        Intent intent = getIntent();
        //String blogId = intent.getStringExtra( "blogId" );
        //_presenter.loadBlogDetail( blogId );

        Blog blog = (Blog)intent.getSerializableExtra( "blog" );
        showBlogDetail( blog );
    }

    @Override
    protected void onDestroy()
    {
        _presenter.destroy();
        _presenter = null;

        super.onDestroy();
    }

    @Override
    public void showBlogDetail( Blog blog )
    {
        Debug.log( TAG, blog.toString() );

        inputTitle.setText( blog.getTitle() );
        inputTitle.setText( blog.getContent() );
    }

    @Override
    public void onSavedBlog( Blog blog )
    {
        Debug.log( TAG, blog.toString() );

        finish();
    }

    @Override
    public void setPresenter( IBlogEditContract.IBlogEditPresenter presenter )
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

    private void attemptSubmit()
    {
        if(_presenter.isWorking())
            return;

        inputTitle.setError( null );
        inputContent.setError( null );

        String title = inputTitle.getText().toString().trim();
        String content = inputContent.getText().toString().trim();

        View focusView = null;

        // Check for a valid password, if the user entered one.
        if( !TextUtils.isEmpty( title ) ){
            inputTitle.setError( getString( R.string.error_field_required ) );
            focusView = inputTitle;
        }

        if( !TextUtils.isEmpty( content ) ){
            inputContent.setError( getString( R.string.error_field_required ) );
            focusView = inputContent;
        }

        if( focusView != null ){
            focusView.requestFocus();
        }
        else{
            String summary = null;
            if( content.length() >= 30 ){
                summary = content.substring( 0, 27 ) + "...";
            }
            else{
                summary = content;
            }

            _presenter.saveBlog( title, summary, content );
        }
    }
}
