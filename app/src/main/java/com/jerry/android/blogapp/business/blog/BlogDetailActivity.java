package com.jerry.android.blogapp.business.blog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.business.utils.ImageLoader;
import com.jerry.android.blogapp.framework.BaseSwipeBackActivity;
import com.jerry.android.blogapp.framework.core.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BlogDetailActivity extends BaseSwipeBackActivity implements IBlogDetailContract.IBlogDetailView
{
    private static final String TAG = "BlogDetailActivity";

    private IBlogDetailContract.IBlogDetailPresenter _presenter;

    private TextView labTitle;
    private TextView labContent;
    private TextView labAuthor;

    private EditText inputComment;
    private Button btnSubmit;
    private View mProgressView;

    private LinearLayout layoutComment;
    private List<View> commentItemList;
    private Context mContext;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_blog_detail );

        labTitle = (TextView)findViewById( R.id.lab_title );
        labContent = (TextView)findViewById( R.id.lab_content );
        labAuthor = (TextView)findViewById( R.id.lab_author );
        layoutComment = (LinearLayout)findViewById( R.id.list_comment );
        inputComment = (EditText)findViewById( R.id.input_comment );
        btnSubmit = (Button)findViewById( R.id.btn_comment );
        btnSubmit.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                attemptSubmit();
            }
        } );

        mProgressView = findViewById( R.id.progressBar );


        _presenter = new BlogDetailPresenter( this );
        _presenter.start();

        // get blog id from previous Activity.
        Intent intent = getIntent();
        //String blogId = intent.getStringExtra( "blogId" );
        //_presenter.loadBlogDetail( blogId );

        Blog blog = (Blog)intent.getSerializableExtra( "blog" );
        _presenter.setCurrentBlog( blog );

        showBlogDetail( blog );

        commentItemList = new ArrayList<>();
        mContext = this;
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
        labTitle.setText( blog.getTitle() );
        labContent.setText( blog.getContent() );

        String date = Tools.formatDateShort( (long)blog.getCreated_at() );
        labAuthor.setText( String.format( "%s 发表于 %s", blog.getUser_name(), date ) );

        // load blog comments.
        _presenter.loadBlogComments();
    }

    @Override
    public void showComments( List<Comment> list )
    {
        Collections.sort( list, new Comparator<Comment>()
        {
            @Override
            public int compare( Comment a, Comment b )
            {
                if(a.getCreated_at() < b.getCreated_at())
                    return 1;
                else if(a.getCreated_at() > b.getCreated_at())
                    return -1;
                return 0;
            }
        } );

        for( Comment c : list ){
            addComment( c );
        }
    }

    @Override
    public void addComment( Comment comment )
    {
        Debug.log( TAG, comment.toString() );

        View commentView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, null);
        ImageView icon = (ImageView)commentView.findViewById( R.id.icon );
        TextView lab_name = (TextView)commentView.findViewById( R.id.lab_name );
        TextView lab_time = (TextView)commentView.findViewById( R.id.lab_time );
        TextView lab_content = (TextView)commentView.findViewById( R.id.lab_content );

        ImageLoader.display( mContext, icon, comment.getUser_image() );
        lab_name.setText( comment.getUser_name() );
        lab_time.setText( Tools.formatDateShort( (long)comment.getCreated_at() ) );
        lab_content.setText( comment.getContent() );

        layoutComment.addView( commentView, 0 );
    }

    @Override
    public void showProgress()
    {
        showProgress(true);
    }

    @Override
    public void hideProgress()
    {
        showProgress(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress( final boolean show )
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 ){
            int shortAnimTime = getResources().getInteger( android.R.integer.config_shortAnimTime );

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
        }
    }

    @Override
    public void setPresenter( IBlogDetailContract.IBlogDetailPresenter presenter )
    {
        this._presenter = presenter;
    }

    @Override
    public void showError( String msg )
    {
        showToast( msg );
    }

    @Override
    public void showLoadFailMsg()
    {

    }

    private void attemptSubmit()
    {
        if( _presenter.isWorking() ){
            return;
        }

        // Reset errors.
        inputComment.setError( null );

        String comment = inputComment.getText().toString().trim();

        View focusView = null;

        // Check for a valid password, if the user entered one.
        if( TextUtils.isEmpty( comment ) ){
            inputComment.setError( getString( R.string.error_field_required ) );
            focusView = inputComment;
        }

        if( focusView != null ){
            focusView.requestFocus();
        }
        else{
            _presenter.sendComment( comment );
        }
    }
}
