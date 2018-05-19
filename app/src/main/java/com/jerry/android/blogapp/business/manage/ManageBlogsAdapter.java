package com.jerry.android.blogapp.business.manage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.BaseRecyclerViewAdapter;
import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.core.Tools;

public class ManageBlogsAdapter extends BaseRecyclerViewAdapter<Blog>
{
    public ManageBlogsAdapter( Context context )
    {
        super(context);
    }

    @Override
    protected ItemViewHolder onCreateCustomViewHolder( ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from( _context ).inflate( R.layout.item_manage_blog, parent, false );
        return new MyItemViewHolder( view );
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
    {
        Debug.log( "ManageBlogsAdapter", "onBindViewHolder: " + Integer.toString( position ) );
        if(holder instanceof MyItemViewHolder ){
            MyItemViewHolder vh = (MyItemViewHolder)holder;

            Blog data = getItem( position );
            if(data != null){
                vh.lab_title.setText( data.getTitle() );
                vh.lab_author.setText( data.getUser_name() );
                vh.lab_time.setText( Tools.formatDateShort( (long)data.getCreated_at() ) );
            }
        }
    }

    private class MyItemViewHolder extends ItemViewHolder
    {
        public TextView lab_title;
        public TextView lab_author;
        public TextView lab_time;

        public MyItemViewHolder( View itemView )
        {
            super( itemView );

            lab_title = (TextView)itemView.findViewById( R.id.lab_title );
            lab_author = (TextView)itemView.findViewById( R.id.lab_author );
            lab_time = (TextView)itemView.findViewById( R.id.lab_time );
        }
    }
}
