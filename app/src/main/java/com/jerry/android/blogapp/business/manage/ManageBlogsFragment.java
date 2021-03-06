package com.jerry.android.blogapp.business.manage;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.BaseRecyclerViewAdapter;
import com.jerry.android.blogapp.business.MainActivity;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.business.edit.BlogEditActivity;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;

public class ManageBlogsFragment extends BaseFragment implements IManageBlogsContract.IManageBlogsView, SwipeRefreshLayout.OnRefreshListener
{
    private static final String TAG = "ManageBlogsFragment";

    private IManageBlogsContract.IManageBlogsPresenter _presenter;

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ManageBlogsAdapter mAdapter;

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        _presenter = new ManageBlogsPresenter( this );
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

        mAdapter = new ManageBlogsAdapter( getActivity().getApplicationContext() );
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick( View view, int position )
            {
                Blog blog = mAdapter.getItem( position );
                if(blog != null){
                    Intent intent = new Intent( getActivity().getApplicationContext(), BlogEditActivity.class );
                    intent.putExtra( "mode", "edit" );
                    //intent.putExtra( "blogId", blog.getId() );
                    intent.putExtra( "blog", blog );
                    startActivityForResult( intent, MainActivity.REQUEST_EDIT_BLOG );
                }
            }
        });
        mAdapter.setOnItemLongClickListener( new BaseRecyclerViewAdapter.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick( View view, int position )
            {
                return showPopup( view, position );
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

    private boolean showPopup(View item, final int position)
    {
        View popupView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.popup, null, false);
        Button btn_delete = (Button) popupView.findViewById(R.id.btn_delete);

        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效

        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(item, item.getWidth()/2 - popupView.getWidth()/2, 0);

        //设置popupWindow里的按钮的事件
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Blog blog = mAdapter.getItem( position );
                if(blog != null){
                    if( !_presenter.isWorking() ){
                        _presenter.delete(blog.getId());
                    }
                }
                popWindow.dismiss();
            }
        });

        return true;
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult( requestCode, resultCode, data );

        if( resultCode == 2 ){
            if(requestCode == MainActivity.REQUEST_EDIT_BLOG ){
                onRefresh();
            }
        }
    }

    @Override
    public void addDataList( List<Blog> list )
    {
        Debug.log( TAG, "addDataList: " + Integer.toString( list.size() ) );
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
    public void onDeleteBlog( String blogId )
    {
        Debug.log( TAG, "deleted blog " + blogId );

        int deletePos = -1;

        List<Blog> dataList = mAdapter.getData();
        for( int i = 0; i < dataList.size(); i++ ){
            if(dataList.get(i).getId().equals( blogId )){
                deletePos = i;
                break;
            }
        }
        if(deletePos > -1){
            mAdapter.removeData( deletePos );
        }
    }

    @Override
    public void showLoadFailMsg()
    {
        Debug.log( TAG, "load data list failed" );
        hideProgress();
    }

    @Override
    public void setPresenter( IManageBlogsContract.IManageBlogsPresenter presenter )
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
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void hideProgress()
    {
        mSwipeRefreshWidget.setRefreshing(false);
    }
}
