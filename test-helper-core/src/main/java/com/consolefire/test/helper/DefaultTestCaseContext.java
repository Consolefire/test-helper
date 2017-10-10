package com.consolefire.test.helper;

import java.util.HashSet;
import java.util.Set;

public class DefaultTestCaseContext {
    private static volatile DefaultTestCaseContext context;

    private final Set<Class<?>> classesUnderTest = new HashSet<>();
    
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
