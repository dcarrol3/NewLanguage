/*
 * Purpose: Breaks down statements to each of its operations
 * Author(s): Doug Carroll
 * Version: 1
 * Date: 4/17/2017
 */

package compiler;

import java.util.ArrayList;
import java.util.Collections;

public class StatementToOperation {

    // Converts a statement made of tokens to series of operations
    private ArrayList<Operation> convertToOperation(ArrayList<Token> statement){
        ArrayList<Operation> ops = new ArrayList<>();
        String type;
        boolean typeFound = false;

        // Get statement type
        while(!typeFound && statement.iterator().hasNext()) {
            Token token = statement.iterator().next();
            switch(token.getType()) {
                case GrammarDefs.ASSIGNMENT:
                    typeFound = true;
                    ops = parseAssignment(statement);
                    break;
                case GrammarDefs.IF:
                    ops = parseIf(statement);
                    typeFound = true;
                    break;
                case GrammarDefs.LOOP:
                    typeFound = true;
                    break;
                case GrammarDefs.PRINT:
                    typeFound = true;
                    break;
                default:
                    break;
            }
        }

        return ops;
    }

    private ArrayList<Operation> parseIf(ArrayList<Token> statement){
        ArrayList<Operation> ops = new ArrayList<>();
        PemdasParser pem = new PemdasParser();
        // Get the conditional inside the if statement
        ArrayList<Token> expression = new ArrayList<>(statement.subList(1, statement.size() - 1));
        ops.addAll(pem.parseExpression(expression)); // Get operations for the expression

        // Get the last variable used in the conditional
        String exprVar = ops.get(ops.size() - 1).getVariable();
        String label = "labelTest"; //TODO - Get label from parser

        // Add the IF operation
        ops.add(new Operation(Operation.OperationType.IF, label, exprVar));

        return ops;

    }

    // Parse assignment statement into a list of operations
    private ArrayList<Operation> parseAssignment(ArrayList<Token> statement){
        ArrayList<Operation> ops = new ArrayList<>();
        PemdasParser pem = new PemdasParser();
        // TODO - This assumes the terminating newline/semi-colon is not included
        ArrayList<Token> expression = new ArrayList<>(statement.subList(2, statement.size() - 1));
        String variable = statement.get(0).getKey(); // Get the identifier
        ops.addAll(pem.parseExpression(expression)); // Get operations for the expression

        // Get the last variable used in the expression
        String exprVar = ops.get(ops.size() - 1).getVariable();

        // Add the assignment operation
        ops.add(new Operation(Operation.OperationType.ASSIGNMENT, variable, exprVar));

        return ops;

    }
}
