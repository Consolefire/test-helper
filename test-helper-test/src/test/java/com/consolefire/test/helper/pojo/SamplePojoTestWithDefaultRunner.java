/**
 * 
 */


package com.consolefire.test.helper.pojo;

import com.consolefire.test.helper.DefaultPojoTestRunner;
import com.consolefire.test.helper.GenericPojoTestRunner;
import com.consolefire.test.helper.ReflectionBasedPojoTestRule;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sabuj.das
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SamplePojoTestWithDefaultRunner extends DefaultPojoTestRunner {

    @Override
    public ReflectionBasedPojoTestRule getTargetPojoRule() {
        return new ReflectionBasedPojoTestRule(SimplePojo.class);
    }



}
