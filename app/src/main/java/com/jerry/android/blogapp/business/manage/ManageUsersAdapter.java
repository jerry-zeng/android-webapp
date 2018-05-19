package com.jerry.android.blogapp.business.manage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.BaseRecyclerViewAdapter;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.core.Tools;

public class ManageUsersAdapter extends BaseRecyclerViewAdapter<User>
{
    public ManageUsersAdapter( Context context){
        super(context);
    }

    @Override
    protected ItemViewHolder onCreateCustomViewHolder( ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from( _context ).inflate( R.layout.item_manage_user, parent, false );
        return new MyItemViewHolder( view );
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
    {
        Debug.log( "ManageUsersAdapter", "onBindViewHolder: " + Integer.toString( position ) );
        if(holder instanceof MyItemViewHolder ){
            MyItemViewHolder vh = (MyItemViewHolder)holder;

            User data = getItem( position );
            if(data != null){
                vh.lab_name.setText( data.getName() );
                vh.lab_email.setText( data.getEmail() );
                vh.lab_last_login.setText( Tools.formatDateShort( (long)data.getLast_login() ) );
            }
        }
    }

    private class MyItemViewHolder extends ItemViewHolder
    {
        public TextView lab_name;
        public TextView lab_email;
        public TextView lab_last_login;

        public MyItemViewHolder( View itemView )
        {
            super( itemView );

            lab_name = (TextView)itemView.findViewById( R.id.lab_name );
            lab_email = (TextView)itemView.findViewById( R.id.lab_email );
            lab_last_login = (TextView)itemView.findViewById( R.id.lab_last_login );
        }
    }
}
