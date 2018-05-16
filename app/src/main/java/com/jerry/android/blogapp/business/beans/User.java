package com.jerry.android.blogapp.business.beans;

import java.io.Serializable;

public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String password;
    private int admin;
    private String email;
    private String image;
    private float created_at;
    private float last_login;

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public int getAdmin()
    {
        return admin;
    }

    public void setAdmin( int admin )
    {
        this.admin = admin;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage( String image )
    {
        this.image = image;
    }

    public float getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at( float created_at )
    {
        this.created_at = created_at;
    }

    public float getLast_login()
    {
        return last_login;
    }

    public void setLast_login( float last_login )
    {
        this.last_login = last_login;
    }


    public boolean isAdmin()
    {
        return admin > 0;
    }

    @Override
    public String toString()
    {
        return String.format( "User: id=%s, name=%s, email=%s", getId(), getName(), getEmail() );
    }
}
