package com.jerry.android.blogapp.business.blogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.BaseRecyclerViewAdapter;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.business.blog.BlogDetailActivity;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;


public class BlogsFragment extends BaseFragment implements IBlogsContract.IBlogsView, SwipeRefreshLayout.OnRefreshListener
{
    private static final String TAG = "BlogsFragment";

    private IBlogsContract.IBlogsPresenter _presenter;

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private BlogsAdapter mAdapter;


    @Override
    public void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        _presenter = new BlogsPresenter( this );
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
        View view = inflater.inflate( R.layout.fragment_blogs, null );

        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new BlogsAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick( View view, int position )
            {
                Blog blog = mAdapter.getItem( position );
                if(blog != null){
                    Intent intent = new Intent( getActivity().getApplicationContext(), BlogDetailActivity.class );
                    //intent.putExtra( "blogId", blog.getId() );
                    intent.putExtra( "blog", blog );
                    startActivity( intent );
                }
            }
        });

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
                        if( page != null && page.isHas_next() ){
                            _presenter.loadData( page.getPage_index() + 1, Url.PAZE_SIZE );
                        }
                    }
                }
            }
        } );

        onRefresh();

        return view;
    }

    @Override
    public void addDataList( List<Blog> list )
    {
        mAdapter.addDataList( list );

        Page page = _presenter.getCurrentPage();
        if( page != null && !page.isHas_next() ){
            mAdapter.setShowFooter( false );
        }
    }

    // swipe to reload
    @Override
    public void onRefresh()
    {
        mAdapter.setShowFooter( true );
        mAdapter.clearData();
        _presenter.loadData(1, Url.PAZE_SIZE);
    }


    @Override
    public void showLoadFailMsg()
    {
        hideProgress();
    }

    @Override
    public void setPresenter( IBlogsContract.IBlogsPresenter presenter )
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
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void hideProgress()
    {
        mSwipeRefreshWidget.setRefreshing(false);
    }

}
