package com.jerry.android.blogapp.business;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jerry.android.blogapp.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<TData> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_FOOTER = 1;

    public interface OnItemClickListener
    {
        void onItemClick( View view, int position);
    }
    public interface OnItemLongClickListener
    {
        boolean onItemLongClick(View view, int position);
    }

    protected Context _context;
    protected List<TData> _data;
    protected boolean mShowFooter = true;
    protected OnItemClickListener _onItemClickListener;
    protected OnItemLongClickListener _onItemLongClickListener;

    public BaseRecyclerViewAdapter(Context context)
    {
        this._context = context;
        this._data = new ArrayList<>();
    }

    public void setOnItemClickListener( OnItemClickListener listener)
    {
        this._onItemClickListener = listener;
    }
    public void setOnItemLongClickListener( OnItemLongClickListener listener)
    {
        this._onItemLongClickListener = listener;
    }

    public void setDataList(List<TData> data)
    {
        _data = data;
        notifyDataSetChanged();
    }

    public void clearData()
    {
        _data.clear();
        notifyDataSetChanged();
    }

    public void addData(TData data)
    {
        _data.add( data );
        notifyDataSetChanged();
    }

    public void addDataList(List<TData> data)
    {
        _data.addAll( data );
        notifyDataSetChanged();
    }

    public void removeData(int position)
    {
        _data.remove( position );
        notifyDataSetChanged();
    }

    public TData getItem(int position)
    {
        return _data != null? _data.get( position ) : null;
    }

    public void setShowFooter(boolean value)
    {
        this.mShowFooter = value;
    }
    public boolean isShowFooter()
    {
        return this.mShowFooter;
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
        if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from( _context ).inflate( R.layout.footer, null );
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            return new FooterViewHolder( view );
        }
        else if(viewType == TYPE_ITEM){
            return onCreateCustomViewHolder( parent, viewType );
        }
        return null;
    }

    protected abstract ItemViewHolder onCreateCustomViewHolder( ViewGroup parent, int viewType );


    protected class FooterViewHolder extends RecyclerView.ViewHolder
    {
        public FooterViewHolder( View itemView )
        {
            super( itemView );
        }
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ItemViewHolder( View itemView )
        {
            super( itemView );

            itemView.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View view )
                {
                    if(_onItemClickListener != null)
                        _onItemClickListener.onItemClick( view, getPosition() );
                }
            } );

            itemView.setOnLongClickListener( new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick( View view )
                {
                    if(_onItemLongClickListener != null)
                        return _onItemLongClickListener.onItemLongClick(view, getPosition());
                    return false;
                }
            } );
        }
    }
}

