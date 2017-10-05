/**
 * 
 */


package com.consolefire.test.helper.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sabuj.das
 *
 */
@Getter
@Setter
public class PojoWithNoDefaultArgument {

    private int value;

    public PojoWithNoDefaultArgument(int value) {
        this.value = value;
    }
    
    
    
}
