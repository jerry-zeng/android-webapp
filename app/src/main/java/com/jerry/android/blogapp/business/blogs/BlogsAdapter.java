package com.jerry.android.blogapp.business.blogs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.BaseRecyclerViewAdapter;
import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.utils.Debug;

import java.util.ArrayList;
import java.util.List;

public class BlogsAdapter extends BaseRecyclerViewAdapter<Blog>
{
    public BlogsAdapter( Context context )
    {
        super(context);
    }

    @Override
    protected ItemViewHolder onCreateCustomViewHolder( ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from( _context ).inflate( R.layout.item_blog, parent, false );
        return new MyItemViewHolder( view );
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
    {
        if(holder instanceof MyItemViewHolder){
            MyItemViewHolder vh = (MyItemViewHolder)holder;

            Blog data = getItem( position );
            if(data != null){
                vh.labTitle.setText( data.getTitle() );
                vh.labSummary.setText( data.getSummary() );
            }
        }
    }

    private class MyItemViewHolder extends ItemViewHolder
    {
        public TextView labTitle;
        public TextView labSummary;

        public MyItemViewHolder( View itemView )
        {
            super( itemView );

            labTitle = (TextView)itemView.findViewById( R.id.lab_title );
            labSummary = (TextView)itemView.findViewById( R.id.lab_summary );
        }
    }
}
