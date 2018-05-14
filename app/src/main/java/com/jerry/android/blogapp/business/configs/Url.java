package com.jerry.android.blogapp.business.configs;

public class Url
{
    public static final String Host = "139.196.136.157";

    public static final String Api_Blogs = Host + "/api/blogs";
    public static final String Api_Users = Host + "/api/users";
    public static final String Api_Comments = Host + "/api/comments";

    public static final int PAZE_SIZE = 10;

    public static String getBlogPageApi(int pageIndex){
        return Api_Blogs + "?page=" + pageIndex + "&size=" + PAZE_SIZE;
    }
    public static String getBlogPageApi(int pageIndex, int pageSize){
        return Api_Blogs + "?page=" + pageIndex + "&size=" + pageSize;
    }
}
