package com.jerry.android.blogapp.business;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.jerry.android.blogapp.business.utils.ImageLoader;
import com.jerry.android.blogapp.framework.BaseAppCompatActivity;

public class MainActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mToolbar = (Toolbar)findViewById( R.id.toolbar );
        setSupportActionBar( mToolbar );

//        FloatingActionButton fab = (FloatingActionButton)findViewById( R.id.fab );
//        fab.setOnClickListener( new View.OnClickListener()
//        {
//            @Override
//            public void onClick( View view )
//            {
//                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
//                        .setAction( "Action", null ).show();
//            }
//        } );

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
            mNavigationView.getMenu().getItem( 1 ).setVisible( true );
        }
        else{
            mNavigationView.getMenu().getItem( 1 ).setVisible( false );
        }

//        if(user != null){
//            ImageView icon = (ImageView)mNavigationView.getHeaderView( 1 );
//            TextView lab_name = (TextView)mNavigationView.getHeaderView( 2 );
//            ImageLoader.display( this, icon, user.getImage() );
//            lab_name.setText( user.getName() );
//        }

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
            showToast( "Show blogs page" );
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
        mToolbar.setVisibility( View.INVISIBLE );
    }

    void focusOnManage()
    {
        mToolbar.setTitle( "Manage" );
        mToolbar.setVisibility( View.VISIBLE );
    }
}
