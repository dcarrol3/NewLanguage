/*
 * Purpose: Some common grammar definitions for the compiler.
 * Author(s): Doug Carroll and Jason Rice
 * Version: 3
 * Date: 4/12/2017
 */

package compiler;

public final class GrammarDefs {

    public static final String JSON_TOKEN = "token";
    public static final String JSON_TYPE = "type";
    public static final String JSON_DELIMITER = "delimit";
    public static final String OPERATOR = "operator";

    // Variable names type
    public static final String IDENTIFIER = "identifier";

    // Number
    public static final String WHOLE_NUMBER = "number";

    // Error
    public static final String ERROR = "error";

    // Comment
    public static final String COMMENT = "comment";
    public static final String MULTI_LINE_COMMENT_S = "ml_start_comment";
    public static final String MULTI_LINE_COMMENT_E = "ml_end_comment";

    // New Line
    public static final String NEW_LINE = "new_line";

    // Other
    public static final String COMMA = "comma";
    public static final String OPEN_BRACKET = "open_bracket";
    public static final String CLOSE_BRACKET = "close_bracket";
    public static final String OPEN_PAREN = "open_paren";
    public static final String CLOSE_PAREN = "close_paren";

    // Label
    public static final String LABEL = "label";

    // Assignment
    public static final String ASSIGNMENT = "assignment";
    public static final String ASSIGNMENT_VAL = "=";

    // Keywords
    public static final String IF = "if";
    public static final String LOOP = "loop";
    public static final String PRINT = "print";
    public static final String ELSE = "else";


    public static final String OPEN_PAREN_TOKEN = "(";
    public static final String CLOSE_PAREN_TOKEN = ")";

    // Conditional tokens
    public static final String AND_TOKEN = "and";
    public static final String OR_TOKEN = "or";
    public static final String NOT_EQUAL_TOKEN = "!=";
    public static final String TRUE_TOKEN = "true";
    public static final String FALSE_TOKEN = "false";
    public static final String EQUALS_TOKEN = "==";
    public static final String GT_TOKEN = ">";
    public static final String LT_TOKEN = "<";
    public static final String GTE_TOKEN = ">=";
    public static final String LTE_TOKEN = "<=";

    // Operations
    public static final String ADD_TOKEN = "+";
    public static final String SUB_TOKEN = "-";
    public static final String MULTI_TOKEN = "*";
    public static final String DIV_TOKEN = "/";
    public static final String MOD_TOKEN = "%";








}
