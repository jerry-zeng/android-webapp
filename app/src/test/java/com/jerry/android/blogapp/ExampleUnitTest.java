package com.jerry.android.blogapp;

import com.jerry.android.blogapp.framework.core.Tools;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        Debug.Log("addition_isCorrect()");
        assertEquals(4, 2 + 2);
    }

    @Test
    public void another_test(){
        Debug.Log("another_test()");
        assertEquals(3, 2 + 1);
    }

    @Test
    public void md5_test(){
        Debug.Log( "md5('admin'): " );
        Debug.Log( Tools.getMd5( "admin" ) );
        assertEquals( "21232f297a57a5a743894a0e4a801fc3", Tools.getMd5( "admin" ) );
    }

    @Test
    public void time_test(){
        Debug.Log( "time: 1526580414" );
        Debug.Log( Tools.formatDateShort( 1526580414 ) );
    }
}