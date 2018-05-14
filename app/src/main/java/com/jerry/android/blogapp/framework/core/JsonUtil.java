package com.jerry.android.blogapp.framework.core;


import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil
{
    public static <T> String serialize(T object) {
        return JSON.toJSONString( object );
    }


    public static <T> T deserialize(String json, Class<T> clz){
        return JSON.parseObject( json, clz );
    }

    public static <T> T deserialize(String json, Type type) {
        return JSON.parseObject( json, type );
    }

    public static <T> List<T> deserializeArray( String json, Class<T> clz){
        return JSON.parseArray( json, clz );
    }
}
