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
public class SimplePojo {

    private Long id;
    private String name;
    
    /**
     * 
     */
    public SimplePojo() {
        this(1L, "Test");
    }

    public SimplePojo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    
    
}
