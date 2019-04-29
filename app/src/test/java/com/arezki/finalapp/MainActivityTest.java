package com.arezki.finalapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    MainActivity mActivity=new MainActivity();
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
    }
    @Test
    public void url()
    {
        mActivity.myWebview.loadUrl("http://192.168.137.211:8000/index.html");
    }

    @Test
    public void onBackPressedTest() {

        assertTrue(
                mActivity.myWebview.canGoBack()
            );
      
    }
}