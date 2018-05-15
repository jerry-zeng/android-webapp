package com.jerry.android.blogapp;

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
}