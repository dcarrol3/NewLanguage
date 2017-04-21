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

    public boolean is_open_bracket(String token) {

        boolean flag = false;

        if (token == definitions.OPEN_BRACKET) {

            flag = true;

        }

        return flag;
    }






}
























