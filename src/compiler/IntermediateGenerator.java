/*
 * Purpose: Generates intermediate code from the parsed high level code.
 * Author(s): Doug Carroll
 * Version: 1
 * Date: 4/15/2017
 */

package compiler;


import java.util.ArrayList;

public class IntermediateGenerator {
    public final String INTER_CODE_DIR = "data/";

    // Generate intermediate code from parsed code
    public void createCode(ArrayList<Operation> operations, String filename){
        StringBuilder intermCode = new StringBuilder("");
        for(Operation operation : operations){
            String assemblyOp = "";
            switch(operation.getType()){
                case ASSIGNMENT:
                    assemblyOp = getAssignmentLine(operation.getVariable(), operation.getValue1());
                    break;
                case IF:
                case LOOP:
                case ADDITION:
                case SUBTRACTION:
                case MULTIPLICATION:
                case DIVISION:
                case MODULUS:
                case EQUAL_TO:
                case NOT_EQUAL_TO:
                case GREATER_THAN:
                case LESS_THAN:
                case GREATER_THAN_EQUAL_TO:
                case LESS_THAN_EQUAL_TO:
                case TRUE:
                case FALSE:
                case AND:
                default:
            }
            intermCode.append(assemblyOp);
            intermCode.append("\n"); // Every operation must be on a new line
        }
        FileHandler.stringToFile(INTER_CODE_DIR + filename, intermCode.toString());
    }

    private String getAssignmentLine(String variable, String value){
        // TODO - make "add" a constant once isidro uploads runtime defs
        return "add" + "," + variable + ",0," + value;
    }

}
