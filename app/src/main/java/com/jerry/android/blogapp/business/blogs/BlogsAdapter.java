package com.jerry.android.blogapp.business.blogs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.beans.Blog;

import java.util.ArrayList;
import java.util.List;

public class BlogsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context _context;
    private List<Blog> _data;
    private boolean mShowFooter = true;
    private OnItemClickListener _onItemClickListener;


    public BlogsAdapter( Context context )
    {
        this._context = context;
        this._data = new ArrayList<>();
    }

    public void setDataList(List<Blog> data)
    {
        _data = data;
        notifyDataSetChanged();
    }

    public void clearData()
    {
        _data.clear();
        notifyDataSetChanged();
    }

    public void addData(Blog data)
    {
        _data.add( data );
        notifyDataSetChanged();
    }

    public void addDataList(List<Blog> data)
    {
        _data.addAll( data );
        notifyDataSetChanged();
    }


    public Blog getItem(int position)
    {
        return _data != null? _data.get( position ) : null;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if(! isShowFooter() ) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount()
    {
        int begin = isShowFooter() ? 1:0;
        if(_data == null) {
            return begin;
        }
        return _data.size() + begin;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        if(viewType == TYPE_ITEM){
            View view = LayoutInflater.from( _context ).inflate( R.layout.item_blog, parent, false );
            return new ItemViewHolder( view );
        }
        else{
            View view = LayoutInflater.from( _context ).inflate( R.layout.footer, null );
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            return new FooterViewHolder( view );
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
    {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder vh = (ItemViewHolder)holder;
            Blog blog = getItem( position );

            if(blog != null){
                vh.labTitle.setText( blog.getTitle() );
                vh.labSummary.setText( blog.getSummary() );
            }
        }
    }


    public void setShowFooter(boolean value)
    {
        this.mShowFooter = value;
    }
    public boolean isShowFooter()
    {
        return this.mShowFooter;
    }


    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this._onItemClickListener = listener;
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder
    {
        public FooterViewHolder( View itemView )
        {
            super( itemView );
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView labTitle;
        public TextView labSummary;

        public ItemViewHolder( View itemView )
        {
            super( itemView );

            labTitle = (TextView)itemView.findViewById( R.id.lab_title );
            labSummary = (TextView)itemView.findViewById( R.id.lab_summary );

            itemView.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View view )
                {
                    if(_onItemClickListener != null)
                        _onItemClickListener.onItemClick( view, getPosition() );
                }
            } );
        }
    }
}
