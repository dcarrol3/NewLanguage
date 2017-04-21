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

    /*
    =============================================================
    =============================================================
    */

    private boolean is_and_token(String token) {

        boolean flag = false;

        if (token == definitions.AND_TOKEN) {

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

        if (token == definitions.OR_TOKEN) {

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

        if (token == definitions.NOT_EQUAL_TOKEN) {

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

        if (token == definitions.TRUE_TOKEN) {

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

        if (token == definitions.FALSE_TOKEN) {

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

        if(token == definitions.EQUALS_TOKEN) {

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

        if(token == definitions.GT_TOKEN) {

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

        if(token == definitions.GTE_TOKEN) {

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

        if(token == definitions.LT_TOKEN) {

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

        if(token == definitions.LTE_TOKEN) {

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

        if(token == definitions.ADD_TOKEN) {

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

        if(token == definitions.SUB_TOKEN) {

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

        if(token == definitions.MULTI_TOKEN) {

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

        if(token == definitions.DIV_TOKEN) {

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

        if(token == definitions.MOD_TOKEN) {

            flag = true;
        }

        return flag;
    }
}























