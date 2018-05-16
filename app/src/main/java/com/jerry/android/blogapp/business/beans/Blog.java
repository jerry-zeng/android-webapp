package com.jerry.android.blogapp.business.beans;

import java.io.Serializable;

public class Blog implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String id;
    private String user_id;
    private String user_name;
    private String user_image;
    private String title;
    private String summary;
    private String content;
    private float created_at;
    private float latest_reply;

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id( String user_id )
    {
        this.user_id = user_id;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name( String user_name )
    {
        this.user_name = user_name;
    }

    public String getUser_image()
    {
        return user_image;
    }

    public void setUser_image( String user_image )
    {
        this.user_image = user_image;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary( String summary )
    {
        this.summary = summary;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public float getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at( float created_at )
    {
        this.created_at = created_at;
    }

    public float getLatest_reply()
    {
        return latest_reply;
    }

    public void setLatest_reply( float latest_reply )
    {
        this.latest_reply = latest_reply;
    }


    @Override
    public String toString()
    {
        return String.format("Blog: id=%s, author=%s, title=%s", getId(), getUser_name(), getTitle());
    }
}
