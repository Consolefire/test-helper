/**
 * 
 */


package com.consolefire.test.helper.pojo;

import com.consolefire.test.helper.GenericPojoTestRunner;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sabuj.das
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PojoWithNoDefaultConstructorTest extends GenericPojoTestRunner<PojoWithNoDefaultConstructor> {

    @Override
    public <T> Object getTargetObject(Class<T> clazz) {
        return new PojoWithNoDefaultConstructor(100);
    }

    
    
}
