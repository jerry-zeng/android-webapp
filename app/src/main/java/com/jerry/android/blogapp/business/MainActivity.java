package com.jerry.android.blogapp.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.blogs.BlogsFragment;
import com.jerry.android.blogapp.business.edit.BlogEditActivity;
import com.jerry.android.blogapp.business.utils.ImageLoader;
import com.jerry.android.blogapp.framework.BaseAppCompatActivity;

public class MainActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private final static int REQUEST_CREATE_BLOG = 2; // 返回的结果码.

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private FloatingActionButton btnCreateBlog;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mToolbar = (Toolbar)findViewById( R.id.toolbar );
        setSupportActionBar( mToolbar );

        btnCreateBlog = (FloatingActionButton)findViewById( R.id.btn_create_blog );
        btnCreateBlog.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Intent intent = new Intent( getApplicationContext(), BlogEditActivity.class );
                intent.putExtra( "mode", "create" );
                startActivityForResult( intent, REQUEST_CREATE_BLOG );
            }
        } );

        drawerLayout = (DrawerLayout)findViewById( R.id.drawer_layout );

        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawerLayout.addDrawerListener( mDrawerToggle );
        mDrawerToggle.syncState();

        mNavigationView = (NavigationView)findViewById( R.id.nav_view );
        mNavigationView.setNavigationItemSelectedListener( this );

        // hide manage button.
        User user = MyApplication.getInstance().getCurrentUser();
        if( user != null && user.isAdmin() ){
            mNavigationView.getMenu().findItem( R.id.nav_manage ).setVisible( true );
        }
        else{
            mNavigationView.getMenu().findItem( R.id.nav_manage ).setVisible( false );
        }

        if(user != null){
            View headerView = mNavigationView.getHeaderView( 0 );
            ImageView icon = (ImageView)headerView.findViewById( R.id.icon );
            TextView lab_name = (TextView)headerView.findViewById( R.id.lab_name );
            ImageLoader.display( this, icon, user.getImage() );
            lab_name.setText( user.getName() );
        }

        focusOnBlogs();
    }

    @Override
    public void onBackPressed()
    {
        if( drawerLayout.isDrawerOpen( GravityCompat.START ) ){
            drawerLayout.closeDrawer( GravityCompat.START );
        }
        else{
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected( MenuItem item )
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if( id == R.id.nav_blogs ){
            focusOnBlogs();
        }
        else if( id == R.id.nav_manage ){
            User user = MyApplication.getInstance().getCurrentUser();
            if(user != null && user.isAdmin()){
                showToast( "Show manage page" );
            }
            else
            {
                showToast( "Permission denied!" );
            }
        }
        else{
            return true;
        }

        drawerLayout.closeDrawer( GravityCompat.START );

        return true;
    }

    void focusOnBlogs()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new BlogsFragment()).commit();
        mToolbar.setTitle( "Blog" );
        //mToolbar.setVisibility( View.INVISIBLE );
        btnCreateBlog.setVisibility( View.VISIBLE );
    }

    void focusOnManage()
    {
        mToolbar.setTitle( "Manage" );
        //mToolbar.setVisibility( View.VISIBLE );
        btnCreateBlog.setVisibility( View.INVISIBLE );
    }

    
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult( requestCode, resultCode, data );

        if( resultCode == 2 ){
            if(requestCode == REQUEST_CREATE_BLOG ){
                focusOnBlogs();
            }
        }
    }
}
