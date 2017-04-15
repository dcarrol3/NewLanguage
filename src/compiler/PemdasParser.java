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

public class PemdasParser {

    public static final String EXPR_AND = "&&";
    public static final String EXPR_OR = "||";
    public static final String EXPR_NOT_EQUAL = "!=";
    public static final String EXPR_TRUE = "TRUE";
    public static final String EXPR_FALSE = "FALSE";


    // Simplifies and orders a (potentially complex) mathematical/conditional expression
    public String expressionAsPostfix(ArrayList<Token> expr){
        Expression expression = buildExpression(expr);
        return expression.toRPN();
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
        String res = "";
        switch(key) {
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

}
