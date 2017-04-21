package compiler;

/**
 * Created by jebjohnson on 4/20/17.
 */
public class Validation {


    GrammarDefs definitions = new GrammarDefs();


    private int line_counter;
    /*
    =============================================================
    =============================================================
    */
    private boolean is_newline(String token){

        boolean flag = false;

        if (token == definitions.NEW_LINE){

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

        if (token == definitions.IDENTIFIER) {

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

        if (token == definitions.WHOLE_NUMBER) {

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

        if (token == definitions.OPEN_BRACKET) {

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

        if (token == definitions.CLOSE_BRACKET) {

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

        if (token == definitions.LABEL) {

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

        if (token == definitions.ASSIGNMENT) {

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

        if (token == definitions.ASSIGNMENT_VAL) {

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

        if (token == definitions.IF) {

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

        if (token == definitions.LOOP) {

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

        if (token == definitions.PRINT) {

            flag = true;
        }

        return flag;
    }



}
























