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
        PRINT,
        JUMP,
        JUMP_RETURN,
        END,
        LABEL,
        ERROR
    }

    private OperationType type;
    private String variable;
    private String value1;
    private String value2;

    public String getLoopLabel() {
        return loopLabel;
    }

    public void setLoopLabel(String loopLabel) {
        this.loopLabel = loopLabel;
    }

    private String loopLabel;

    public Operation(){};

    public Operation(OperationType type){
        this.type = type;
    }

    public Operation(OperationType type, String variable){
        this.type = type;
        this.variable = variable;
    }

    public Operation(OperationType type, String variable, String value1){
        this.type = type;
        this.variable = variable;
        this.value1 = value1;
    }

    public Operation(OperationType type, String variable, String value1, String value2){
        this.type = type;
        this.variable = variable;
        this.value1 = value1;
        this.value2 = value2;
    }

    public Operation(OperationType type, String variable, String value1, String value2, String loopLabel){
        this.type = type;
        this.variable = variable;
        this.value1 = value1;
        this.value2 = value2;
        this.loopLabel = loopLabel;
    }

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
