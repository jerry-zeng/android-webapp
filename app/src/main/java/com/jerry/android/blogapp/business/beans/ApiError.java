package com.jerry.android.blogapp.business.beans;

import java.io.Serializable;

public class ApiError implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String error;
    private String data;
    private String message;

    public String getError()
    {
        return error;
    }

    public void setError( String error )
    {
        this.error = error;
    }

    public String getData()
    {
        return data;
    }

    public void setData( String data )
    {
        this.data = data;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }


    @Override
    public String toString()
    {
        return String.format( "ApiError: error=%s, data=%s, message=%s", getError(), getData(), getMessage() );
    }
}
