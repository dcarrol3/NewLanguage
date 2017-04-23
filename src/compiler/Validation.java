package compiler;

import java.util.ArrayList;

/**
 * Created by jebjohnson on 4/20/17.
 */
public class Validation {


    private int line_counter;
    private final int ARRAYLIST_SIZE;
    private final int ZERO_INDEX;
    private int statement_index;
    private int list_index;
    private ArrayList<String[]> statement_list = new ArrayList<>();

    /*
    ========================================================================================
    Constructor
    ========================================================================================
    */
    
    public Validation(ArrayList tokens) {

        this.line_counter = 0;
        this.ZERO_INDEX = 0;
        this.list_index = 0;
        this.statement_list.addAll(tokens);
        this.ARRAYLIST_SIZE = tokens.size();
    }

    /*
    ========================================================================================
    Checks all source code is valid
    ========================================================================================
    */


    public boolean validate() {

        boolean flag = true;

        for (list_index = 0; list_index < ARRAYLIST_SIZE; list_index++) {

            statement_index = 0;
            flag = check_keyword(statement_list.get(list_index)[statement_index]);

            if (!flag) {

                System.out.println("Error Line" + line_counter);

            }

        }


        return flag;
    }

    /*
    =======================================================================================
    =======================================================================================
    */

    private boolean check_keyword(String keyword) {

        boolean flag = false;

        switch (keyword) {

            case GrammarDefs.LOOP:
                flag = is_valid_loop_assignment();
                break;

            case GrammarDefs.IF:
                //is_valid_boolean_expression();
                break;

            case GrammarDefs.PRINT:
                //is_valid_print();
                break;

            case GrammarDefs.ELSE:
               // is_valid_else();
                break;

            case GrammarDefs.ASSIGNMENT:
                //is_valid_assignment();
                break;

                default:
                    break;
        }

        return flag;
    }

    /*
    =======================================================================================
    =======================================================================================
    */

    private boolean is_valid_loop_assignment() {

        return is_identifier(statement_list.get(list_index)[statement_index]) &&
                is_equals_token(statement_list.get(list_index)[++statement_index]) && is_valid_iterator();

    }

    /*
    =======================================================================================
    =======================================================================================
    */

    private boolean is_valid_iterator() {

        String[] temp1, temp2;

        statement_index++;
        temp1 = get_code_segment(",").clone();
        statement_index++;
        temp2 = get_code_segment("{").clone();

        return is_valid_expression(temp1) && is_valid_expression(temp2);
    }

    /*
    =======================================================================================
    =======================================================================================
    */

    private String[] get_code_segment(String stop_point) {

        int temp_index = statement_index;
        //int

        while (!statement_list.get(list_index)[temp_index].equals(stop_point)) {

            temp_index++;

        }
        String[] temp = new String[temp_index - statement_index];

        temp_index = 0;

        while (!statement_list.get(list_index)[statement_index].equals(stop_point)) {

            temp[temp_index] = statement_list.get(list_index)[statement_index];

        }


        return temp;
    }

    /*
    ========================================================================================
    Checks if a conditional statement is valid
    ========================================================================================
    */

    private boolean is_valid_boolean_expression(String[] token) {

        boolean flag = true;
        int local_index = 0;
        String prev_token = token[local_index];
        String current_token = token[++local_index];

        if (is_conditional_value(prev_token) &&  !is_valid_condVal_successor(current_token)) {

            flag = false;

        } else if (is_conditional_token(prev_token) && !is_valid_cond_successor(current_token)) {

            flag = false;

        }


        return flag;
    }

    /*
    ========================================================================================
    Checks if a conditional token has a valid successor token
    ========================================================================================
    */

    private boolean is_valid_cond_successor(String token) {

        boolean flag = false;

        switch (token) {

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
    ====================================================================================
    Check if a value holding token has a valid successor token
    ====================================================================================
    */

     private boolean is_valid_condVal_successor(String token) {

         boolean flag = false;

         switch (token) {

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

             default:
                 break;
         }

         return flag;
     }

    /*
    =============================================================
    Checks if the value is a defined conditional token
    =============================================================
    */

    private boolean is_conditional_value(String token) {

        boolean flag = false;

        switch (token) {

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
    =============================================================
    =============================================================
    */

    private boolean is_conditional_token(String token) {

        boolean flag = false;

        switch (token) {

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

            default:
                break;

        }

        return flag;
    }

    /*
    =============================================================
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
    =============================================================
    */

    private boolean is_number(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.WHOLE_NUMBER)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_open_bracket(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.OPEN_BRACKET)) {

            flag = true;

        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_closed_brackets(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.CLOSE_BRACKET)) {

            flag = true;
        }
        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_label(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.LABEL)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_assignment(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.ASSIGNMENT)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_assignment_val(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.ASSIGNMENT_VAL)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_if(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.IF)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_loop(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.LOOP)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_print(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.PRINT)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_and_token(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.AND_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_or_token(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.OR_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_not_equal_token(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.NOT_EQUAL_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_true_token(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.TRUE_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_false_token(String token) {

        boolean flag = false;

        if (token.equals(GrammarDefs.FALSE_TOKEN)) {

            flag = true;
        }
        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_equals_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.EQUALS_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_gt_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.GT_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_gte_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.GTE_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_lt_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.LT_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_lte_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.LTE_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_add_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.ADD_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_sub_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.SUB_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_multi_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.MULTI_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_div_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.DIV_TOKEN)) {

            flag = true;
        }

        return flag;
    }

    /*
    =============================================================
    =============================================================
    */

    private boolean is_mod_token(String token) {

        boolean flag = false;

        if(token.equals(GrammarDefs.MOD_TOKEN)) {

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

        boolean flag;
        String prev_token = token[0];
        int i = 1;

        //checks to ensure there are the same number of open and closed parenthesis
        flag = is_valid_paren_count(token);

        //checks expression does not start or end with an operator
        flag = flag && (!is_operation(token[0]) || !is_operation(token[token.length - 1]));

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
    =============================================================
    */

    private boolean is_valid_numerical_successor(String token) {

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
                break;
        }

        return flag;
    }

    /*
    =============================================================
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

            default:
                break;
        }

        return flag;
    }

     /*
    =============================================================
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
    =============================================================
    */

    private boolean is_operation(String token) {

        boolean flag = false;

        switch (token) {

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























