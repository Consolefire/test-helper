/**
 * 
 */


package com.consolefire.test.helper.impl;

import java.util.HashMap;
import java.util.Map;

import com.consolefire.test.helper.MultiplePojoTestRunner;
import com.consolefire.test.helper.ReflectionBasedPojoTestRule;
import com.consolefire.test.helper.TargetObjectProvider;
import com.consolefire.test.helper.pojo.PojoWithNoDefaultConstructor;
import com.consolefire.test.helper.pojo.SimplePojo;

/**
 * @author sabuj.das
 *
 */
public class SimpleMultiplePojoTestRunner extends MultiplePojoTestRunner {

    /*
     * (non-Javadoc)
     * 
     * @see com.consolefire.test.helper.MultiplePojoTestRunner#buildPojoTestRules()
     */
    @Override
    public Map<Class<?>, ReflectionBasedPojoTestRule> buildPojoTestRules() {
        Map<Class<?>, ReflectionBasedPojoTestRule> rules = new HashMap<>();
        rules.put(SimplePojo.class, new ReflectionBasedPojoTestRule(SimplePojo.class));
        rules.put(PojoWithNoDefaultConstructor.class, new ReflectionBasedPojoTestRule(PojoWithNoDefaultConstructor.class));
        return rules;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.consolefire.test.helper.MultiplePojoTestRunner#buildCustomTargetObjectProviders()
     */
    @Override
    public Map<Class<?>, TargetObjectProvider> buildCustomTargetObjectProviders() {
        Map<Class<?>, TargetObjectProvider> map = new HashMap<>();
        map.put(PojoWithNoDefaultConstructor.class, new TargetObjectProvider() {

            @Override
            public <T> Object getTargetObject(Class<T> clazz) {
                return new PojoWithNoDefaultConstructor(100);
            }
        });
        return map;
    }



}
