/*
 * Purpose: Handles converting expressions to the correct order of operations
 * Author(s): Doug Carroll
 * Version: 1
 * Date: 4/15/2017
 */

package compiler;

import compiler.lib.Expression;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class PemdasParser {

    // Temp variable for operations
    public static final String TEMP = "$t";
    public static final String ASSIGN = "$a";
    private static int assignNum = 0;


    // ----Syntax specific to the expression lib----
    // Some are similar to Javier syntax, though repeated here for modularity
    // Operations
    public static final String EXPR_ADD = "+";
    public static final String EXPR_SUB = "-";
    public static final String EXPR_MULTI = "*";
    public static final String EXPR_DIV = "/";
    public static final String EXPR_MOD = "%";

    // Conditions
    public static final String EXPR_AND = "&&";
    public static final String EXPR_OR = "||";
    public static final String EXPR_NOT_EQUAL = "!=";
    public static final String EXPR_TRUE = "TRUE";
    public static final String EXPR_FALSE = "FALSE";
    public static final String EXPR_GT = ">";
    public static final String EXPR_LT = "<";
    public static final String EXPR_GTE = ">=";
    public static final String EXPR_LTE = "<=";
    public static final String EXPR_EQUALS = "==";



    // Simplifies and orders a (potentially complex) mathematical/conditional expression
    public ArrayList<Operation> parseExpression(ArrayList<Token> expr){
        ArrayList<Operation> ops = new ArrayList<>();
        if(expr.size() > 1) {
            Expression expression = buildExpression(expr);
            ops = postfixToOperations(expression.toRPN());
        }
        else{
            ops.add(new Operation(Operation.OperationType.ASSIGNMENT, ASSIGN + assignNum, expr.get(0).getKey()));
            assignNum++;
        }
        return ops;
    }

    // Build expression using the expression tokens
    private Expression buildExpression(ArrayList<Token> exprTokens){
        Expression expression;
        StringBuilder exprStr = new StringBuilder("");
        for(Token token : exprTokens){
            exprStr.append(convertKey(token.getKey()));
        }
        expression = new Expression(exprStr.toString());
        expression = appendVariables(expression, exprTokens);
        return expression;
    }

    // Let expression lib know about each variable
    private Expression appendVariables(Expression expression, ArrayList<Token> exprTokens){
        for (Token token: exprTokens) {
            if(token.getType().equals(GrammarDefs.IDENTIFIER)){
                expression.with(token.getKey(), token.getKey());
            }

        }
        return expression;
    }

    // Convert our syntax to expression libs syntax
    private String convertKey(String key){
        String res;
        switch(key) {
            case GrammarDefs.ADD_TOKEN:
                res = EXPR_ADD;
                break;
            case GrammarDefs.SUB_TOKEN:
                res = EXPR_SUB;
                break;
            case GrammarDefs.MULTI_TOKEN:
                res = EXPR_MULTI;
                break;
            case GrammarDefs.DIV_TOKEN:
                res = EXPR_DIV;
                break;
            case GrammarDefs.MOD_TOKEN:
                res = EXPR_MOD;
                break;
            case GrammarDefs.GT_TOKEN:
                res = EXPR_GT;
                break;
            case GrammarDefs.LT_TOKEN:
                res = EXPR_LT;
                break;
            case GrammarDefs.GTE_TOKEN:
                res = EXPR_GTE;
                break;
            case GrammarDefs.LTE_TOKEN:
                res = EXPR_LTE;
                break;
            case GrammarDefs.EQUALS_TOKEN:
                res = EXPR_EQUALS;
                break;
            case GrammarDefs.NOT_EQUAL_TOKEN:
                res = EXPR_NOT_EQUAL;
                break;
            case GrammarDefs.AND_TOKEN:
                res = EXPR_AND;
                break;
            case GrammarDefs.OR_TOKEN:
                res = EXPR_OR;
                break;
            case GrammarDefs.TRUE_TOKEN:
                res = EXPR_TRUE;
                break;
            case GrammarDefs.FALSE_TOKEN:
                res = EXPR_FALSE;
                break;
            default:
                res = key;
        }
        return res;
    }

    // Convert the postfix string to an array of operations
    private ArrayList<Operation> postfixToOperations(String postfix){
        String[] postfixArr = postfix.split(" ");
        ArrayList<Operation> ops = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        int tempCount = 0;

        for(int i = 0; i < postfixArr.length; i++){
            // If number or variable, add it to the stack
            if(is_number(postfixArr[i]) || validIdentifier(postfixArr[i])){
                stack.push(postfixArr[i]);
            }
            // We have an operation
            else{
                String val2 = stack.pop();
                String val1 = stack.pop();
                Operation op = new Operation();
                op.setValue1(val1);
                op.setValue2(val2);
                op.setVariable(TEMP + tempCount);
                op.setType(getOpType(postfixArr[i]));
                ops.add(op);
                stack.push(TEMP + tempCount);
                tempCount++;
            }
        }

        return ops;

    }

    // Get the correct operation type
    private Operation.OperationType getOpType(String operation){
        Operation.OperationType op;
        switch(operation){
            case EXPR_ADD:
                op = Operation.OperationType.ADDITION;
                break;
            case EXPR_SUB:
                op = Operation.OperationType.SUBTRACTION;
                break;
            case EXPR_MULTI:
                op = Operation.OperationType.MULTIPLICATION;
                break;
            case EXPR_DIV:
                op = Operation.OperationType.DIVISION;
                break;
            case EXPR_MOD:
                op = Operation.OperationType.MODULUS;
                break;
            case EXPR_GT:
                op = Operation.OperationType.GREATER_THAN;
                break;
            case EXPR_LT:
                op = Operation.OperationType.LESS_THAN;
                break;
            case EXPR_GTE:
                op = Operation.OperationType.GREATER_THAN_EQUAL_TO;
                break;
            case EXPR_LTE:
                op = Operation.OperationType.LESS_THAN_EQUAL_TO;
                break;
            case EXPR_EQUALS:
                op = Operation.OperationType.EQUAL_TO;
                break;
            case EXPR_NOT_EQUAL:
                op = Operation.OperationType.NOT_EQUAL_TO;
                break;
            case EXPR_AND:
                op = Operation.OperationType.AND;
                break;
            case EXPR_OR:
                op = Operation.OperationType.OR;
                break;
            case EXPR_TRUE:
                op = Operation.OperationType.TRUE;
                break;
            case EXPR_FALSE:
                op = Operation.OperationType.FALSE;
                break;
            default:
                op = Operation.OperationType.ERROR;
        }

        return op;
    }

    private boolean is_number(String str){
        return str.matches("^-?\\d+$");
    }
    private boolean is_char(char val) {
        return((val >= 'a' && val <= 'z') || (val >= 'A' && val <= 'Z'));
    }
    private boolean validIdentifier(String token){
        return !token.equals("") && is_char(token.charAt(0));
    }


}
