package com.consolefire.test.helper;

import org.junit.Before;

public abstract class BaseTestCaseRunner {

    
    public abstract void doSetUp();
    
    @Before
    public void setUp(){
        doSetUp();
    }
    
}
