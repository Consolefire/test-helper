package com.consolefire.test.helper;

public class DefaultTestCaseContext {
    private static volatile DefaultTestCaseContext context;

    private DefaultTestCaseContext() {

    }

    public static DefaultTestCaseContext getContext() {
        if (null == context) {
            synchronized (DefaultTestCaseContext.class) {
                if (null == context) {
                    context = new DefaultTestCaseContext();
                }
            }
        }
        return context;
    }


    public void build(String[] packages, Class<?>[] classes) {
        
    }

}
