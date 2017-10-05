/**
 * 
 */


package com.consolefire.test.helper;

/**
 * @author sabuj.das
 *
 */
public interface TargetObjectProvider {

    <T> Object getTargetObject(Class<T> clazz);

}
