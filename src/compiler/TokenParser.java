package compiler;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Purpose: Holds information about each operation for use by the intermediate code generator.
 * Author(s): Jason Rice
 * Version: 2
 * Date: 4/14/2017
 */

public class TokenParser {
    private int statementLine = 0;
    private String labelStat = "label-0";
    ArrayList<ArrayList<ArrayList<Token>>> parsedTokens;

    public TokenParser(ArrayList<Token> tokens){
       ArrayList<ArrayList<Token>> temp = makeList(tokens);

        //This is just for testing.
        for(ArrayList<Token> tok: temp){
            for(Token tek: tok){
                System.out.print(tek.getKey()+" ");
            }
            System.out.print("\n<---end statement--->\n");
        }
    }

    //Program --> StatementLst
    public boolean Program(ArrayList<Token> tokens){
        return StatementLst(tokens).size() == 0;
    }

    //Break up the tokens into a statement list
    public ArrayList<ArrayList<Token>> makeList(ArrayList<Token> tokens){
        ArrayList<ArrayList<Token>> statementList = new ArrayList<>();
        ArrayList<Token> statement = new ArrayList<>();
        int brackCount = 0;
        boolean openBrack = false;
        ArrayList<String> checkTypes = new ArrayList<>(
                Arrays.asList("close_bracket","open_bracket","new_line","semi-colon"));

        //loop through the tokens making a list of statements for further checking.
        for(Token token: tokens){
            String tmp = token.getType();

            if(!checkTypes.contains(tmp)){
                statement.add(token);
            } else{
                if(tmp.equals("open_bracket")){
                    openBrack = true;
                    brackCount++;
                    statement.add(token);
                } else if(tmp.equals("close_bracket")){
                    brackCount--;
                    if(brackCount == 0){
                        openBrack = false;
                    }else if(brackCount < 0){
                        brackCount = 0;
                    }
                    statement.add(token);
                } else{
                    if(openBrack){
                        statement.add(token);
                    } else{
                        statementList.add(statement);
                        statement = new ArrayList<>();
                        openBrack = false;
                        brackCount = 0;
                    }
                }
            }

        }
        return statementList;
    }

    //StatementLst --> Statement | Statement StatementLst
    public ArrayList<Token> StatementLst(ArrayList<Token> tokens){
        if(Statement(tokens).size() == 0){
            return tokens;
        } else{
            statementLine++;
            if(StatementLst(tokens).size() == 0){
                return tokens;
            } else{
                System.out.println("Error - Not a proper statement. Line:"+(statementLine-1));
            }
        }
        return tokens;
    }

    //Statement --> “new_line” | Assignment | If | Loop
    public ArrayList<Token> Statement(ArrayList<Token> tokens){
        ArrayList<Token> tok = tokens;
        switch (tok.get(0).getType()){
            case("new_line"):
                tok.remove(0);
                return tokens;
            case("identifier"):
                if(tok.get(1).getType() == "assignment"){

                } else{
                    System.out.println("Error - Identifier "+tok.get(0).getKey()+" has no assignment.");
                }
            default:
                return tokens;
        }
    }

    //Number --> Digit | Digit Number
    public Token Number(Token token){
        return token;
    }

    //Digit --> ‘0’ | ‘1’ | ‘2’ | ‘3’ | ‘4’ | ‘5’ | ‘6’ | ‘7’ | ‘8’ | ‘9’
    public Token Digit(Token token){
        return token;
    }

    //Letter --> ‘a’..’z’ | ‘A’..’Z’
    public Token Letter(Token token){
        return token;
    }

    //Identifier --> Letter | Letter Number | Letter Identifier
    public Token Identifier(Token token){
        return token;
    }

    //Assignment --> Identifier “=” Expr
    public ArrayList<Token> Assignment(ArrayList<Token> tokens){
        return tokens;
    }

    //If --> “if” Condition “{“ Statement-Lst “}” | If Else
    public ArrayList<Token> If(ArrayList<Token> tokens){
        return tokens;
    }

    //Else --> “else” “{“ Statement-Lst “}”
    public ArrayList<Token> Else(ArrayList<Token> tokens){
        return tokens;
    }

    /*
    Condition --> Expr “==” Expr | Expr “>=” Expr | Expr “<=” Expr | Expr “>” Expr
        | Expr “<” Expr | Condition “and” Condition | Condition “or” Condition | “true” | “false”
     */
    public ArrayList<Token> Condition(ArrayList<Token> tokens){
        return tokens;
    }

    //Iterator --> Expr “,” Expr
    public ArrayList<Token> Iterator(ArrayList<Token> tokens){
        return tokens;
    }

    //Loop --> “loop” Loop-Assignment “{“ Statement-Lst “}”
    public ArrayList<Token> Loop(ArrayList<Token> tokens){
        return tokens;
    }

    //Loop-Assignment --> Identifier “=” Iterator
    public ArrayList<Token> LoopAssignment(ArrayList<Token> tokens){
        return tokens;
    }

    /*
    Expression --> Expression “+” Expression | Expression “-” Expression | Expression “*” Expression |
        Expression “/” Expression | Expression “%” Expression | “(“ Expression “)” | Number
     */
    public ArrayList<Token> Expression(ArrayList<Token> tokens){
        return tokens;
    }

    //increment the statement list label
    public void incLabel(){
        int temp = Integer.parseInt(this.labelStat.substring(6))+1;
        this.labelStat = this.labelStat.substring(0,6)+temp;
        System.out.println(this.labelStat);
    }
}
