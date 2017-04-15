package compiler;

import java.util.ArrayList;

/*
    Before this can be complete we need to change the words in the lexer and discuss approach to reading.
    The idea is to go through erasing tokens as they have been verified and returning true if 0 tokens
    remain at the end. We may want to keep a count of statements so we can return which line number the mistake is on
    similar we may want to add statement lists to another array for the intermediate part.
 */
public class TokenParser {
    private int statementLine = 0;

    public TokenParser(ArrayList<Token> tokens){
        System.out.println(Program(tokens));
    }

    //Program --> StatementLst
    public boolean Program(ArrayList<Token> tokens){
        if(StatementLst(tokens).size() == 0){
            return true;
        } else{
            return false;
        }
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
                System.out.println("Error - Not a proper statement. Line:"+statementLine);
            }
        }
        return tokens;
    }

    //Statement --> “” | Assignment | If | Loop
    public ArrayList<Token> Statement(ArrayList<Token> tokens){

        return tokens;
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
}
