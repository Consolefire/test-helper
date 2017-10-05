package com.consolefire.test.helper;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ReflectionBasedPojoTestRule implements TestRule {

    private final Class<?> targetPojoClass;

    public ReflectionBasedPojoTestRule(Class<?> targetPojoClass) {
        this.targetPojoClass = targetPojoClass;
    }

    public Class<?> getTargetPojoClass() {
        return targetPojoClass;
    }

    public class ReflectionBasedPojoStatement extends Statement {
        private final Statement statement;

        public ReflectionBasedPojoStatement(Statement statement, Class<?> targetPojoClass) {
            this.statement = statement;
        }

        @Override
        public void evaluate() throws Throwable {
            this.statement.evaluate();
        }
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        return new ReflectionBasedPojoStatement(statement, targetPojoClass);
    }

}
