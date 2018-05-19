package com.jerry.android.blogapp.framework.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class HttpEngine
{
    private static final String TAG = "HttpEngine";

    private static HttpEngine _instance;

    public synchronized static HttpEngine getInstance(){
        if(_instance == null)
            _instance = new HttpEngine();
        return _instance;
    }

    private OkHttpClient _httpClient;
    private Handler _deliver;

    private HttpEngine(){
        _httpClient = new OkHttpClient();
        _httpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        _httpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        _httpClient.setReadTimeout(30, TimeUnit.SECONDS);
        //cookie enabled
        _httpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));

        _deliver = new Handler( Looper.getMainLooper());
    }

    public interface HttpCallback
    {
        void onSuccess( String json );
        void onFailure( String reason );
    }

    private void _get( String url, final HttpCallback callback){
        if(callback == null) return;

        final Request request = new Request.Builder()
                                    .url( url )
                                    .build();
        _sendRequest( callback, request );
    }

    private void _post( String url, Map<String, String> params, HttpCallback callback)
    {
        if(callback == null) return;

        FormEncodingBuilder builder = new FormEncodingBuilder();
        for( Map.Entry<String,String> kvs : params.entrySet() ){
            builder.add( kvs.getKey(), kvs.getValue() );
        }

        final Request request = new Request.Builder()
                                    .url( url )
                                    .post( builder.build() )
                                    .build();
        _sendRequest( callback, request );
    }

    private void _sendRequest(final HttpCallback callback, Request request){
        _httpClient.newCall( request ).enqueue( new Callback()
        {
            @Override
            public void onFailure( Request request, IOException e )
            {
                _handleFailure( callback, e.getMessage() );
            }

            @Override
            public void onResponse( Response response ) throws IOException
            {
                if(response.isSuccessful()){
                    String json = response.body().string();
                    _handleSuccess( callback, json );
                }
                else{
                    String reason = Integer.toString( response.code() );
                    _handleFailure( callback, reason );
                }
            }
        } );
    }

    private void _handleSuccess( final HttpCallback callback, final String json){
        _deliver.post( new Runnable()
        {
            @Override
            public void run()
            {
                if(callback != null){
                    callback.onSuccess( json );
                }
            }
        } );
    }
    private void _handleFailure( final HttpCallback callback, final String reason){
        _deliver.post( new Runnable()
        {
            @Override
            public void run()
            {
                if(callback != null){
                    callback.onFailure( reason );
                }
            }
        } );
    }


    public static void Get(String url, HttpCallback callback)
    {
        getInstance()._get( url, callback );
        Log.i( TAG, "Get: " + url );
    }

    public static void Post( String url, Map<String, String> params, HttpCallback callback)
    {
        getInstance()._post( url, params, callback );
        Log.i( TAG, "Post: " + url );
    }

}
