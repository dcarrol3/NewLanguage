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
        LESS_THAN_EQUAL_TO
    }

    private OperationType type;
    private String variable;
    private int value1;
    private int value2;

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public String getVariable(){
        return variable;
    }

    public void setVariable(String var){
        this.variable = var;
    }
}
