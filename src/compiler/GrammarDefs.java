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

}
