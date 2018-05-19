package com.jerry.android.blogapp.business.manage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.BaseRecyclerViewAdapter;
import com.jerry.android.blogapp.business.beans.Comment;

public class ManageCommentsAdapter extends BaseRecyclerViewAdapter<Comment>
{
    public ManageCommentsAdapter( Context context)
    {
        super(context);
    }

    @Override
    protected MyItemViewHolder onCreateCustomViewHolder( ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from( _context ).inflate( R.layout.item_manage_comment, parent, false );
        return new MyItemViewHolder( view );
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
    {
        if(holder instanceof MyItemViewHolder ){
            MyItemViewHolder vh = (MyItemViewHolder)holder;

            Comment data = getItem( position );
            if(data != null){

            }
        }
    }

    private class MyItemViewHolder extends ItemViewHolder
    {

        public MyItemViewHolder( View itemView )
        {
            super( itemView );

        }
    }
}
