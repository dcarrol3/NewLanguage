package compiler;

/*
 * Purpose: Holds information about each operation for use by the intermediate code generator.
 * Author(s): Doug Carroll
 * Version: 1
 * Date: 4/15/2017
 */

public class Operation {
    public enum OperationType {
        ASSIGNMENT,
        IF,
        LOOP,
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
        MODULUS,
        EQUAL_TO,
        NOT_EQUAL_TO,
        GREATER_THAN,
        LESS_THAN,
        GREATER_THAN_EQUAL_TO,
        LESS_THAN_EQUAL_TO,
        AND,
        OR,
        TRUE,
        FALSE,
        ERROR
    }

    private OperationType type;
    private String variable;
    private String value1;
    private String value2;

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getVariable(){
        return variable;
    }

    public void setVariable(String var){
        this.variable = var;
    }
}
