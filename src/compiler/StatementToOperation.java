/*
 * Purpose: Breaks down statements to each of its operations
 * Author(s): Doug Carroll
 * Version: 2
 * Date: 4/19/2017
 */

package compiler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StatementToOperation {

    public ArrayList<Operation> convertProgram(HashMap<String,ArrayList<ArrayList<Token>>> program){
        ArrayList<ArrayList<Token>> mainStatementList = program.get("program");
        ArrayList<Operation> ops = new ArrayList<>();
        boolean statementFinished = true;
        ArrayList<Token> tempStatement = new ArrayList<>();

        // Main program first
        for(ArrayList<Token> statement : mainStatementList){
            // Remove new lines from if and loop
            if(!statementFinished || hasNewLinesBeforeBracket(statement)) {
                statementFinished = false;
                tempStatement.addAll(statement); // Include current statement to previous one
                if (statement.get(0).getType().equals(GrammarDefs.OPEN_BRACKET)) {
                    statementFinished = true;
                    ops.addAll(convertToOperation(tempStatement));
                    tempStatement.clear(); // Clear it
                }
            } else {
                ops.addAll(convertToOperation(statement));
            }
        }
        ops.add(new Operation(Operation.OperationType.END)); // End of program

        // Add all labeled blocks
        Iterator it = program.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry labeledStatements = (HashMap.Entry)it.next();
            // Don't put main in here
            if(!labeledStatements.getKey().toString().equals("program")) {
                ops.add(new Operation(Operation.OperationType.LABEL, labeledStatements.getKey().toString()));
                for (ArrayList<Token> labeledStatement : (ArrayList<ArrayList<Token>>) labeledStatements.getValue()) {
                    // Remove new lines from if and loop
                    if (!statementFinished || hasNewLinesBeforeBracket(labeledStatement)) {
                        statementFinished = false;
                        tempStatement.addAll(labeledStatement); // Include current statement to previous one
                        if (labeledStatement.get(0).getType().equals(GrammarDefs.OPEN_BRACKET)) {
                            statementFinished = true;
                            ops.addAll(convertToOperation(tempStatement));
                            tempStatement.clear(); // Clear it
                        }
                    } else {
                        ops.addAll(convertToOperation(labeledStatement));
                    }
                }
                ops.add(new Operation(Operation.OperationType.JUMP_RETURN));
            }
        }

        return ops;
    }

    // helper to ignore all newlines in if's and loops before the bracket
    private boolean hasNewLinesBeforeBracket(ArrayList<Token> statement){
        boolean newLineEnd = false;

        // Make sure it is a loop or if
        Token token = statement.get(0);
        if(token.getKey().equals(GrammarDefs.IF) || token.getKey().equals(GrammarDefs.LOOP)){
            Token last = statement.get(statement.size() - 2); // Get second to last token
            // See if it's a newline
            if(!last.getType().equals(GrammarDefs.CLOSE_BRACKET)){
                newLineEnd = true;
            }
        }

        return newLineEnd;
    }

    // Converts a statement made of tokens to series of operations
    private ArrayList<Operation> convertToOperation(ArrayList<Token> statement){
        ArrayList<Operation> ops = new ArrayList<>();
        boolean typeFound = false;

        // Get statement type
        Iterator<Token> it = statement.iterator();
        while(!typeFound && it.hasNext()) {
            Token token = it.next();
            switch(token.getKey()) {
                case GrammarDefs.ASSIGNMENT_VAL:
                    ops = parseAssignment(statement);
                    typeFound = true;
                    break;
                case GrammarDefs.IF:
                    ops = parseIf(statement);
                    typeFound = true;
                    break;
                case GrammarDefs.LOOP:
                    ops = parseLoop(statement);
                    typeFound = true;
                    break;
                case GrammarDefs.PRINT:
                    ops = parsePrint(statement);
                    typeFound = true;
                    break;
                default:
                    break;
            }
        }

        return ops;
    }

    private ArrayList<Operation> parsePrint(ArrayList<Token> statement){
        ArrayList<Operation> ops = new ArrayList<>();
        PemdasParser pem = new PemdasParser();
        ArrayList<Token> expression = new ArrayList<>(statement.subList(1, statement.size() - 1));

        ops.addAll(pem.parseExpression(expression));
        String exprVar = statement.get(1).getKey(); // Get the straight value if no ops are needed
        if(!ops.isEmpty())
            exprVar = ops.get(ops.size() - 1).getVariable();
        ops.add(new Operation(Operation.OperationType.PRINT, exprVar));

        return ops;

    }

    // Parse a loop statement into operations
    private ArrayList<Operation> parseLoop(ArrayList<Token> statement){
        ArrayList<Operation> ops = new ArrayList<>();
        PemdasParser pem = new PemdasParser();

        // Get the comma position
        int comma = 0;
        int bracket = 0;
        for(int i = 0; i < statement.size(); i++){
            if(statement.get(i).getType().equals(GrammarDefs.COMMA)){
                comma = i;
            }
            else if(statement.get(i).getType().equals(GrammarDefs.OPEN_BRACKET)){
                bracket = i;
            }
        }

        ArrayList<Token> expression1 = new ArrayList<>(statement.subList(3, comma));
        ArrayList<Token> expression2 = new ArrayList<>(statement.subList(comma + 1, bracket));

        String variable = statement.get(1).getKey();

        // Start iterator
        ops.addAll(pem.parseExpression(expression1));
        String exprVar1 = statement.get(3).getKey(); // Get the straight value if no ops are needed
        if(!ops.isEmpty())
            exprVar1 = ops.get(ops.size() - 1).getVariable();

        // End iterator
        ops.addAll(pem.parseExpression(expression2));
        String exprVar2 = statement.get(comma + 1).getKey();
        if(expression2.size() != 1 && !ops.isEmpty())
            exprVar2 = ops.get(ops.size() - 1).getVariable();

        String label = statement.get(bracket + 1).getKey();

        // Add the LOOP operation
        ops.add(new Operation(Operation.OperationType.LOOP, variable, exprVar1, exprVar2, label));

        return ops;

    }

    // Parse an if statement into operations
    private ArrayList<Operation> parseIf(ArrayList<Token> statement){
        ArrayList<Operation> ops = new ArrayList<>();
        PemdasParser pem = new PemdasParser();
        // Get the conditional inside the if statement

        // Get the first open_bracket position
        int bracket = -1;
        int elseBracket = -1;
        for(int i = 0; i < statement.size(); i++){
            if(statement.get(i).getType().equals(GrammarDefs.OPEN_BRACKET)){
                if(bracket == -1){
                    bracket = i;
                } else {
                    elseBracket = i;
                }

            }

        }

        ArrayList<Token> condition = new ArrayList<>(statement.subList(1, bracket));
        ops.addAll(pem.parseExpression(condition)); // Get operations for the condition

        // Get the last variable used in the conditional
        String exprVar = ops.get(ops.size() - 1).getVariable();
        // Get the labels
        String ifLabel = statement.get(bracket + 1).getKey();
        String elseLabel = null;
        if(elseBracket != -1)
            elseLabel = statement.get(elseBracket + 1).getKey();

        // Add the IF operation
        ops.add(new Operation(Operation.OperationType.IF, ifLabel, exprVar, elseLabel));

        return ops;

    }

    // Parse assignment statement into a list of operations
    private ArrayList<Operation> parseAssignment(ArrayList<Token> statement){
        ArrayList<Operation> ops = new ArrayList<>();
        PemdasParser pem = new PemdasParser();

        ArrayList<Token> expression = new ArrayList<>(statement.subList(2, statement.size() - 1));
        String variable = statement.get(0).getKey(); // Get the identifier
        ops.addAll(pem.parseExpression(expression)); // Get operations for the expression

        // Get the last variable used in the expression
        String exprVar = statement.get(2).getKey(); // If a single value
        if(!ops.isEmpty())
            exprVar = ops.get(ops.size() - 1).getVariable();

        // Add the assignment operation
        ops.add(new Operation(Operation.OperationType.ASSIGNMENT, variable, exprVar));

        return ops;

    }
}
