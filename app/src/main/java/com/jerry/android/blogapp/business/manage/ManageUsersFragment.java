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

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.BaseRecyclerViewAdapter;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.user.UserDetailActivity;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;

public class ManageUsersFragment extends BaseFragment implements IMangeUsersContract.IManageUsersView
{
    private static final String TAG = "ManageUsersFragment";

    private IMangeUsersContract.IManageUsersPresenter _presenter;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ManageUsersAdapter mAdapter;

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        _presenter = new ManageUsersPresenter( this );
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
        View view = inflater.inflate( R.layout.fragment_manage_blog, null );

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ManageUsersAdapter( getActivity().getApplicationContext() );
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick( View view, int position )
            {
                User user = mAdapter.getItem( position );
                if(user != null){
                    Intent intent = new Intent( getActivity().getApplicationContext(), UserDetailActivity.class );
                    intent.putExtra( "user", user );
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

        return view;
    }

    @Override
    public void addDataList( List<User> list )
    {
        mAdapter.addDataList( list );
    }

    @Override
    public void showLoadFailMsg()
    {
        Debug.log( TAG, "load data list failed" );
        hideProgress();
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
        mAdapter.setShowFooter(false);
    }

    // swipe to reload

    public void onRefresh()
    {
        mAdapter.clearData();
        _presenter.loadData(1, Url.PAZE_SIZE);
    }
}
