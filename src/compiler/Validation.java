/*
 * Purpose: Validates Javier syntax.
 * Author(s): Jeb Johnson
 * Version: 7
 * Date: 4/30/2017
 */
package compiler;

import java.util.ArrayList;
import java.util.List;

public class Validation {

    private int line_counter;
    private int statement_index;
    private List<String> error_list = new ArrayList<>();

    private ArrayList<Token> statements = new ArrayList<>();

    /*
    ========================================================================================================
    ========================================================================================================
    */

    public Validation(ArrayList<Token> tokens) {

        this.line_counter = 0;
        this.statements = tokens;
        this.error_list.add("Errors found:");
    }

    /*
    =======================================================================================================
    Checks all source code is valid
    ======================================================================================================
    */

    public boolean validate() {

        boolean flag = true;
        line_counter++;

        for (statement_index = 0; statement_index < statements.size(); statement_index++) {
            check_keyword(statements.get(statement_index));
        }

        if (error_list.size() > 1){
            flag = false;
            for (String str: error_list) {

                System.out.println(str);

            }
        }

        return flag;
    }

    /*
    ========================================================================================================
    Identifies each statement by the first token
    ========================================================================================================
    */

    private boolean check_keyword(Token keyword) {

        boolean flag = true;
        String error_type = "";

        String word = "";

        if (keyword.getType().equals("keyword")) {
            word = keyword.getKey();
        } else {

            word = keyword.getType();
        }

        switch (word) {

            case GrammarDefs.IDENTIFIER:
                flag = is_valid_assignment();
                if (!flag) {
                    error_type = GrammarDefs.IDENTIFIER + " error Line " + line_counter;
                }
                break;

            case GrammarDefs.LOOP:
                flag = is_valid_loop_assignment();
                if (!flag) {
                    error_type = GrammarDefs.LOOP + " error Line " + line_counter;
                }
                break;

            case GrammarDefs.IF:
                flag = is_valid_boolean_expression();
                if (!flag)
                    error_type = GrammarDefs.IF + " error Line " + line_counter;
                break;

            case GrammarDefs.PRINT:
                flag = is_valid_print();
                if (!flag)
                    error_type = GrammarDefs.PRINT + " error Line " + line_counter;
                break;

            case GrammarDefs.ELSE:
                flag = is_valid_else();
                if (!flag)
                    error_type = GrammarDefs.ELSE + " error Line " + line_counter;
                break;

            case GrammarDefs.NEW_LINE:
                line_counter++;
                break;

            default:
                error_type = "keyword" + " error Line " + line_counter;
                break;
        }

        if (!flag) {
            error_list.add(error_type);
        }

        return flag;
    }

    /*
    =======================================================================================================
    checks for a valid else conditional statement
    =======================================================================================================
    */

    private boolean is_valid_else() {

        boolean flag = false;

        statement_index++;

        if (statements.get(statement_index).getType().equals(GrammarDefs.OPEN_BRACKET) &&
                !statements.get(statement_index + 1).getType().equals(GrammarDefs.OPEN_BRACKET))

            flag = true;

        return flag;
    }

    /*
    =======================================================================================================
    Checks if the print statement is valid
    =======================================================================================================
    */

    private boolean is_valid_print() {

        boolean flag;
        statement_index++;

        flag = is_numerical_token(statements.get(statement_index).getType());

        return flag;
    }

    /*
    =======================================================================================================
    checks an assignment is valid
    =======================================================================================================
    */

    private boolean is_valid_assignment() {

        boolean flag = true;
        statement_index++;

        while (flag && (!statements.get(statement_index).getType().equals(GrammarDefs.NEW_LINE) &&
                !statements.get(statement_index).getType().equals(GrammarDefs.SEMI_COLON))) {

            if (statements.get(statement_index).getType().equals(GrammarDefs.ASSIGNMENT)) {

                statement_index++;

                flag = is_valid_after_assign_val(statements.get(statement_index));

            } else if (is_numerical_token(statements.get(statement_index).getType())) {

                statement_index++;
                flag = is_valid_numerical_successor(statements.get(statement_index).getType());


            } else if (is_operation(statements.get(statement_index).getKey())) {

                statement_index++;

                flag = is_valid_operator_successor(statements.get(statement_index).getType());


            } else if(statements.get(statement_index).getKey().equals("")) {

                statement_index++;

            } else if (statements.get(statement_index).getType().equals(GrammarDefs.COMMA)) {

                statement_index++;

            } else {

                break;
            }


        }

        return flag;
    }

    /*
    =======================================================================================================
    checks if the next character is allowed to come after an assignment value
    =======================================================================================================
    */

    private boolean is_valid_after_assign_val(Token token) {

        boolean flag = false;

        switch (token.getType()) {

            case GrammarDefs.OPEN_PAREN:
                flag = true;
                break;

            case GrammarDefs.IDENTIFIER:
                flag = true;
                break;
            case GrammarDefs.WHOLE_NUMBER:
                flag = true;
                break;

            default:
                System.out.println("");
                break;
        }

        return flag;
    }

    /*
    =======================================================================================================
    Used when conditional ends on open bracket and checks for double bracket
    =======================================================================================================
    */

    private boolean check_double_open_bracket() {

        String temp1 = statements.get(statement_index).getKey();
        String temp2 = statements.get(statement_index + 1).getKey();

        return !temp1.equals(temp2);
    }

    /*
    =======================================================================================================
    Checks if a loop statement has a valid identifier, equals token and iterator
    =======================================================================================================
    */

    private boolean is_valid_loop_assignment() {

        boolean flag1;
        boolean flag2;
        boolean flag3;
        boolean flag4;

        statement_index++;
        flag1 = is_identifier(statements.get(statement_index).getType());
        statement_index++;
        flag2 = is_equals_token(statements.get(statement_index).getKey());
        flag3 = is_valid_iterator();
        flag4 = check_double_open_bracket();

        return flag1 && flag2 && flag3 && flag4;
    }

    /*
    =======================================================================================================
    Checks if an iterator is two valid expressions
    =======================================================================================================
    */

    private boolean is_valid_iterator() {

        String[] temp1, temp2;

        statement_index++;
        temp1 = get_code_segment(GrammarDefs.COMMA).clone();
        statement_index++;
        temp2 = get_code_segment(GrammarDefs.OPEN_BRACKET).clone();

        return is_valid_expression(temp1) && is_valid_expression(temp2);
    }

    /*
    ======================================================================================================
    This code will divide a single line of code into two lines of code. The program know where to split the
    code by using a breakpoint specified by the user.
    ======================================================================================================
    */

    private String[] get_code_segment(String stop_point) {

        List<String> temp = new ArrayList<String>();
        int i = 0;

        while (!statements.get(statement_index).getType().equals(stop_point)) {

            temp.add(statements.get(statement_index).getType());
            statement_index++;

        }

        Object[] objArray = temp.toArray();
        String[] str = new String[temp.size()];
        for (Object obj : objArray) {
            str[i] = (String) obj;
            i++;
        }

        return str;
    }

    /*
    =====================================================================================================
    Checks if a conditional statement is valid
    =====================================================================================================
    */

    private boolean is_valid_boolean_expression() {

        boolean flag = true;
        statement_index++;
        int tempindex;
        Token prev_token = statements.get(statement_index);
        Token current_token = statements.get(++statement_index);

        while (flag && !current_token.getType().equals(GrammarDefs.OPEN_BRACKET)) {

            if (is_conditional_value(prev_token) &&  !is_valid_condVal_successor(current_token)) {

                flag = false;

            } else if (is_conditional_token(prev_token) && !is_valid_cond_successor(current_token)) {

                flag = false;


            }

            prev_token = statements.get(statement_index);
            statement_index++;
            current_token = statements.get(statement_index);

        }

        tempindex = statement_index;
        prev_token = statements.get(tempindex);
        tempindex++;
        current_token = statements.get(tempindex);



        if (current_token.getType().equals(prev_token.getType())) {

            flag = false;
            line_counter++;
        }

        return flag;
    }

    /*
    ===================================================================================================
    Checks if a conditional token has a valid successor token
    ===================================================================================================
    */

    private boolean is_valid_cond_successor(Token token) {

        boolean flag = false;

        switch (token.getType()) {

            case GrammarDefs.IDENTIFIER:
                flag = true;
                break;

            case GrammarDefs.WHOLE_NUMBER:
                flag = true;
                break;

            case GrammarDefs.TRUE_TOKEN:
                flag = true;
                break;

            case GrammarDefs.FALSE_TOKEN:
                flag = true;
                break;

            default:
                System.out.println("Failed conditional successor test\n Line:" + line_counter + "\n");
                break;
        }

        return flag;
    }

     /*
    ==================================================================================================
    Check if a value holding token has a valid successor token
    ==================================================================================================
    */

    private boolean is_valid_condVal_successor(Token token) {

        boolean flag = false;

        switch (token.getKey()) {

            case GrammarDefs.CLOSED_PAREN:
                flag = true;
                break;

            case GrammarDefs.AND_TOKEN:
                flag = true;
                break;

            case GrammarDefs.OR_TOKEN:
                flag = true;
                break;

            case GrammarDefs.NOT_EQUAL_TOKEN:
                flag = true;
                break;

            case GrammarDefs.EQUALS_TOKEN:
                flag = true;
                break;

            case GrammarDefs.GT_TOKEN:
                flag = true;
                break;

            case GrammarDefs.GTE_TOKEN:
                flag = true;
                break;

            case GrammarDefs.LT_TOKEN:
                flag = true;
                break;

            case GrammarDefs.LTE_TOKEN:
                flag = true;
                break;

            case GrammarDefs.MOD_TOKEN:
                flag = true;
                break;

            default:
                break;
        }

        return flag;
    }

    /*
    ===================================================================================================
    Checks if the value is a defined conditional token
    ===================================================================================================
    */

    private boolean is_conditional_value(Token token) {

        boolean flag = false;

        switch (token.getType()) {

            case GrammarDefs.IDENTIFIER:
                flag = true;
                break;

            case GrammarDefs.WHOLE_NUMBER:
                flag = true;
                break;

            case GrammarDefs.TRUE_TOKEN:
                flag = true;
                break;

            case GrammarDefs.FALSE_TOKEN:
                flag = true;
                break;

            default:
                break;

        }

        return flag;
    }

    /*
    =====================================================================================================
    =====================================================================================================
    */

    private boolean is_conditional_token(Token token) {

        boolean flag = false;

        switch (token.getType()) {

            case "compares":
                flag = true;

            default:
                break;

        }

        return flag;
    }

    /*
    =============================================================
    checks if token is a new line token
    =============================================================
    */

    private boolean is_newline(String token){

        boolean flag = false;

        if (token.equals(GrammarDefs.NEW_LINE)) {

            flag = true;
            line_counter += 1;
        }

        return flag;
    }

    /*
    =============================================================
    checks if token is an identifier
    =============================================================
    */

    private boolean is_identifier(String token){

        boolean flag = false;

        if (token.equals(GrammarDefs.IDENTIFIER)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    checks if token is a number
    =============================================================
    */

    private boolean is_number(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.WHOLE_NUMBER) || token.equals(GrammarDefs.IDENTIFIER)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    checks if token is an equals token
    =============================================================
    */

    private boolean is_equals_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.ASSIGNMENT_VAL)) {

            flag = true;
        }

        return flag;
    }


    /*
    =================================================================
    Determines if token sequence is a valid mathematical expression
    =================================================================
    */

    private boolean is_valid_expression(String[] token) {

        boolean flag = true;
        String prev_token = token[0];
        int i = 1;

        //checks expression for invalid combinations
        while (flag && i < token.length) {

            if (is_numerical_token(prev_token) && !is_valid_numerical_successor(token[i])) {

                flag = false;

            } else if (is_operation(prev_token) && !is_valid_operator_successor(token[i])) {

                flag = false;

            } else if (is_open_paren_token(prev_token) && !is_valid_open_par_successor(token[i])) {

                flag = false;

            } else if (is_closed_paren_token(prev_token) && !is_valid_closed_par_successor(token[i])) {

                flag = false;

            }

            prev_token = token[i];
            i++;
        }

        return flag;
    }

    /*
    =============================================================
    checks if the next token is allowed to come after a number
    =============================================================
    */

    private boolean is_valid_numerical_successor(String token) {

        boolean flag = false;

        switch (token) {

            case GrammarDefs.ASSIGNMENT_VAL:
                flag = true;
                break;

            case GrammarDefs.ASSIGNMENT:
                flag = true;
                break;

            case  GrammarDefs.OPERATOR:
                flag = true;
                break;

            case GrammarDefs.OPEN_PAREN:
                flag = true;
                break;

            case GrammarDefs.CLOSED_PAREN:
                flag = true;
                break;

            case GrammarDefs.ADD_TOKEN:
                flag = true;
                break;

            case GrammarDefs.SUB_TOKEN:
                flag = true;
                break;

            case GrammarDefs.DIV_TOKEN:
                flag = true;
                break;

            case GrammarDefs.MULTI_TOKEN:
                flag = true;
                break;

            case GrammarDefs.MOD_TOKEN:
                flag = true;
                break;
            case GrammarDefs.SEMI_COLON:
                flag = true;
                break;

            default:
                break;
        }

        return flag;
    }

    /*
    =============================================================
    checks if token is allowed to follow a parentheses
    =============================================================
    */

    private boolean is_valid_open_par_successor(String token) {

        boolean flag = false;

        switch (token) {

            case GrammarDefs.OPEN_PAREN:
                flag = true;
                break;

            case GrammarDefs.IDENTIFIER:
                flag = true;
                break;

            case GrammarDefs.WHOLE_NUMBER:
                flag = true;
                break;

            case GrammarDefs.MOD_TOKEN:
                flag = true;
                break;

            default:
                break;
        }

        return flag;
    }

     /*
    =============================================================
    Checks if token is allowed to come after a closed parenthses
    =============================================================
    */

    private boolean is_valid_closed_par_successor(String token) {

        boolean flag = false;

        switch (token) {

            case GrammarDefs.CLOSED_PAREN:
                flag = true;
                break;

            case GrammarDefs.ADD_TOKEN:
                flag = true;
                break;

            case GrammarDefs.SUB_TOKEN:
                flag = true;
                break;

            case GrammarDefs.DIV_TOKEN:
                flag = true;
                break;

            case GrammarDefs.MULTI_TOKEN:
                flag = true;
                break;

            case GrammarDefs.MOD_TOKEN:
                flag = true;
                break;

            default:
                System.out.print("Invalid Token line:" + line_counter +"\n");
                break;
        }

        return flag;
    }

    /*
    =============================================================
    checks if token is allowed to follow an operator
    =============================================================
    */

    private boolean is_valid_operator_successor(String token) {

        boolean flag = false;

        switch (token) {

            case GrammarDefs.OPEN_PAREN:
                flag = true;
                break;

            case GrammarDefs.CLOSED_PAREN:
                flag = true;
                break;

            case GrammarDefs.IDENTIFIER:
                flag = true;
                break;

            case GrammarDefs.WHOLE_NUMBER:
                flag = true;
                break;

            default:
                break;
        }

        return flag;
    }

    /*
    =============================================================
    checks if token is a numerical token
    =============================================================
    */

    private boolean is_numerical_token(String token) {

        boolean flag = false;

        if(is_number(token) || is_identifier(token)) {

            flag = true;
        }

        return flag;
    }

     /*
    =============================================================
    Checks if the count of open parentheses
    =============================================================
    */

    private boolean is_valid_paren_count(String[] token) {

        int open_count = 0;
        int closed_count = 0;
        boolean flag = false;

        for (int i = 0; i < token.length; i++) {

            if (token[i].equals(GrammarDefs.OPEN_PAREN)) {

                open_count++;

            } else if (token[i].equals(GrammarDefs.CLOSED_PAREN)) {

                closed_count++;

            }
        }

        if (closed_count == open_count) {

            flag = true;
        }

        return flag;
    }

     /*
    =============================================================
    Checks if token is an closed parentheses
    =============================================================
    */

    private boolean is_closed_paren_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.CLOSED_PAREN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    Checks if token is an open parentheses
    =============================================================
    */

    private boolean is_open_paren_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.OPEN_PAREN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    Checks if the token is an operator
    =============================================================
    */

    private boolean is_operation(String token) {

        boolean flag = false;

        switch (token) {

            case GrammarDefs.OPERATOR:
                flag = true;
                break;

            case GrammarDefs.ADD_TOKEN:
                flag = true;
                break;

            case GrammarDefs.SUB_TOKEN:
                flag = true;
                break;

            case GrammarDefs.DIV_TOKEN:
                flag = true;
                break;

            case GrammarDefs.MULTI_TOKEN:
                flag = true;
                break;

            case GrammarDefs.MOD_TOKEN:
                flag = true;
                break;

            default:
                break;
        }

        return flag;
    }
}