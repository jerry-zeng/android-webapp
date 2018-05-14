package com.jerry.android.blogapp.business.beans;

import java.io.Serializable;

public class Page implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int page_index;
    private int page_count;
    private int item_count;
    private boolean has_next;
    private boolean has_previous;

    public int getPage_index()
    {
        return page_index;
    }

    public void setPage_index( int page_index )
    {
        this.page_index = page_index;
    }

    public int getPage_count()
    {
        return page_count;
    }

    public void setPage_count( int page_count )
    {
        this.page_count = page_count;
    }

    public int getItem_count()
    {
        return item_count;
    }

    public void setItem_count( int item_count )
    {
        this.item_count = item_count;
    }

    public boolean isHas_next()
    {
        return has_next;
    }

    public void setHas_next( boolean has_next )
    {
        this.has_next = has_next;
    }

    public boolean isHas_previous()
    {
        return has_previous;
    }

    public void setHas_previous( boolean has_previous )
    {
        this.has_previous = has_previous;
    }
}
