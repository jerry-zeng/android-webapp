package com.jerry.android.blogapp.business.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.BaseRecyclerViewAdapter;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.business.blog.BlogDetailActivity;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;

public class ManageCommentsFragment extends BaseFragment implements IManageCommentsContract.IManageCommentsView
{
    private static final String TAG = "ManageCommentsFragment";

    private IManageCommentsContract.IManageCommentsPresenter _presenter;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ManageCommentsAdapter mAdapter;
    private TextView lab_tip;

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        _presenter = new ManageCommentsPresenter( this );
        _presenter.start();
    }

    @Override
    public void onDestroy()
    {
        _presenter.destroy();
        _presenter = null;

        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_manage_comment, null );

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ManageCommentsAdapter( getActivity().getApplicationContext() );
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick( View view, int position )
            {
                Comment comment = mAdapter.getItem( position );
                if(comment != null){
                    Intent intent = new Intent( getActivity().getApplicationContext(), BlogDetailActivity.class );
                    intent.putExtra( "blogId", comment.getBlog_id() );
                    startActivity( intent );
                }
            }
        });
        mAdapter.setOnItemLongClickListener( new BaseRecyclerViewAdapter.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick( View view, int position )
            {
                return false;
            }
        } );

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener( new RecyclerView.OnScrollListener()
        {
            private int lastVisibleItem = 0;

            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy )
            {
                super.onScrolled( recyclerView, dx, dy );
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged( RecyclerView recyclerView, int newState )
            {
                super.onScrollStateChanged( recyclerView, newState );

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()
                        && mAdapter.isShowFooter()){

                    if( !_presenter.isWorking() ){
                        Page page = _presenter.getCurrentPage();
                        if(page != null && page.isHas_next()){
                            _presenter.loadData( page.getPage_index() + 1, Url.PAZE_SIZE );
                        }
                    }
                }
            }
        } );

        onRefresh();

        lab_tip = (TextView)view.findViewById( R.id.lab_tip );

        return view;
    }

    @Override
    public void addDataList( List<Comment> list )
    {
        Debug.log( TAG, "addDataList: " + Integer.toString( list.size() ) );
        mAdapter.addDataList( list );

        Page page = _presenter.getCurrentPage();
        if( page != null && !page.isHas_next() ){
            mAdapter.setShowFooter( false );
        }
    }

    // swipe to reload

    public void onRefresh()
    {
        mAdapter.setShowFooter( true );
        mAdapter.clearData();
        _presenter.loadData(1, Url.PAZE_SIZE);
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
        hideProgress();
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
        hideProgress();
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
