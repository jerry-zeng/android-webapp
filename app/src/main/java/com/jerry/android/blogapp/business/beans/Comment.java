package com.jerry.android.blogapp.business.beans;

import java.io.Serializable;

public class Comment implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String id;
    private String blog_id;
    private String blog_title;
    private String user_id;
    private String user_name;
    private String user_image;
    private String content;
    private float created_at;

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getBlog_id()
    {
        return blog_id;
    }

    public void setBlog_id( String blog_id )
    {
        this.blog_id = blog_id;
    }

    public String getBlog_title()
    {
        return blog_title;
    }

    public void setBlog_title( String blog_title )
    {
        this.blog_title = blog_title;
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


    @Override
    public String toString()
    {
        return String.format("Comment: id=%s, author=%s, blog_title=%s, content=%s", getId(), getUser_name(), getBlog_title(), getContent());
    }
}
