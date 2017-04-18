package compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

/*
 * Purpose: Holds information about each operation for use by the intermediate code generator.
 * Author(s): Jason Rice
 * Version: 3
 * Date: 4/17/2017
 */
public class TokenParser {
    private int statementLine;
    private String labelStat;
    private HashMap<String,ArrayList<ArrayList<Token>>> program;
    private HashMap<Integer,Integer> embed;
    private ArrayList<Token> tokensRef;

    //Constructor given a set of tokens
    public TokenParser(ArrayList<Token> tokens){
        labelStat = "label-0";
        statementLine = 0;
        program = new HashMap<>();
        embed = new HashMap<>();
        tokensRef = tokens;
        init(tokens);
    }

    //Default constructor
    public TokenParser(){
        labelStat = "label-0";
        statementLine = 0;
        program = new HashMap<>();
        embed = new HashMap<>();
    }

    //Get the final output of the program.
    public HashMap<String,ArrayList<ArrayList<Token>>> getResults(){
        return this.program;
    }

    //give the output of the program given a set of tokens.
    public HashMap<String,ArrayList<ArrayList<Token>>> parseTokens(ArrayList<Token> tokens){
        tokensRef = tokens;
        init(tokens);
        return this.program;
    }

    //Controller for the program
    private void init(ArrayList<Token> tokens){
        embed = bracketFinder(tokens);
        System.out.println("\n<---------------First Pass---------------->");
        for(int key : embed.keySet()){
            System.out.println("Brackets at: "+key+", "+embed.get(key));
        }

        System.out.println("\n<--------------Second Pass--------------->");
        makeList(-1,-1, "program");
       for(String tl:program.keySet()){
           System.out.print(tl + " [");
            ArrayList<ArrayList<Token>> tStart = program.get(tl);
            for(ArrayList<Token> tok: tStart){
                for(Token tek: tok){
                    System.out.print(tek.getKey()+" ");
                }
            }
           System.out.print("\n ]\n\n");
        }
    }

    //Runs through the tokens getting the locations of the brackets
    private HashMap<Integer,Integer> bracketFinder(ArrayList<Token> tokens){
        HashMap<Integer,Integer> ret = new HashMap<>();
        Stack<Integer> openBrack = new Stack<>();
        int tokenNum = 0;
        for(Token token: tokens){
            if(token.getType().equals("open_bracket")){
                openBrack.push(tokenNum);
            } else if(token.getType().equals("close_bracket")){
                if(openBrack.size() > 0){
                    ret.put(openBrack.pop(),tokenNum);
                } else{
                    ret.put(tokenNum,tokenNum);
                }
            }
            tokenNum++;
        }
        if(openBrack.size() > 0){
            System.out.println("Error: Bracket opened, with no close bracket");
        }
        for(int t : ret.keySet()){
            if(t == ret.get(t)){
                ret.remove(t);
                System.out.println("Error: Bracket closed, with no open bracket");
            }
        }
        return ret;
    }

    //Break up the tokens into a statement list
    private void makeList(int start, int end, String lab){
        int tokenCount = 0;
        String currLabel = labelStat;
        ArrayList<ArrayList<Token>> statementList = new ArrayList<>();
        ArrayList<Token> statement = new ArrayList<>();
        if(start == -1){
            start = 0;
        } else{
            tokenCount = start;
        }
        if(end == -1){
            end = tokensRef.size();
        }
        if(lab != null){
            currLabel = lab;
        }

        for(int y = start; y < end; y++){

            String tokenType = tokensRef.get(y).getType();

                if(tokenType.equals("open_bracket")){
                    statement.add(tokensRef.get(y));
                    Token tok = new Token();
                    tok.setType("label");
                    incLabel();
                    tok.setKey(labelStat);
                    statement.add(tok);
                    makeList(tokenCount+1,embed.get(tokenCount), null);
                    y = embed.get(tokenCount)-1;
                    tokenCount += (embed.get(tokenCount)-tokenCount)-1;
                } else if(tokenType.equals("semi-colon") || tokenType.equals("new_line")){
                    statement.add(tokensRef.get(y));
                    statementList.add(statement);
                    statement = new ArrayList<>();
                } else{
                    statement.add(tokensRef.get(y));
                }
            tokenCount++;

        }
        program.put(currLabel,statementList);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                         GRAMMAR CHECKING                                                ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Program --> StatementLst
    private boolean Program(){
        boolean isGC = true;
        if(program.size() > 0){
            for(String tl:program.keySet()){
                ArrayList<ArrayList<Token>> al = program.get(tl);
                ArrayList<ArrayList<Token>> ret = StatementLst(al);
                if(ret.size() != 0){
                    isGC = false;
                    printList(ret);
                }
            }

        } else{
            isGC = false;
        }
        return isGC;
    }

    //StatementLst --> Statement | Statement StatementLst
    private ArrayList<ArrayList<Token>> StatementLst(ArrayList<ArrayList<Token>> sl){
        ArrayList<ArrayList<Token>> ret = new ArrayList<>();
        if(sl.size() > 0) {
            for (ArrayList<Token> statement : sl) {
                ArrayList<Token> hold = Statement(statement);
                if (hold.size() != 0) {
                    ret.add(hold);
                }
            }
        }
        return ret;
    }

    //Statement --> “new_line” | Assignment | If | Loop
    private ArrayList<Token> Statement(ArrayList<Token> tokens){
        ArrayList<Token> ret = new ArrayList<>();
        if(tokens.size() > 0){
            Token tok = tokens.get(0);
            String tt = tok.getType();
            switch(tt){
                case "data_type":
                    ret = Assignment(tokens);
                    break;
                case "identifier":
                    ret = Assignment(tokens);
                    break;
                case "new_line":
                    break;
                case "keyword":
                    String val = tok.getKey();
                    if(val.equals("if")){
                        ret = If(tokens);
                    } else if(val.equals("loop")){
                        ret = Loop(tokens);
                    }
                    break;
            }
        }
        return ret;
    }

    //Number --> Digit | Digit Number
    private Token Number(Token token){
        try{
            int t = Integer.parseInt(token.getKey());
            if(token.getKey().length() > 1){
                for(int y = 0; y < token.getKey().length(); y++){
                    if(!Digit(token.getKey().charAt(y))){
                        return token;
                    }
                }
            }
        } catch(Exception ex){
            return token;
        }
        return new Token();
    }

    //Digit --> ‘0’ | ‘1’ | ‘2’ | ‘3’ | ‘4’ | ‘5’ | ‘6’ | ‘7’ | ‘8’ | ‘9’
    private boolean Digit(char ch){
        return ch >= 48 && ch <= 57;
    }

    //Letter --> ‘a’..’z’ | ‘A’..’Z’
    private boolean Letter(char ch){
        return (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122);
    }

    //Identifier --> Letter | Letter Number | Letter Identifier
    private Token Identifier(Token token){
        return token;
    }

    //Assignment --> Identifier “=” Expr
    private ArrayList<Token> Assignment(ArrayList<Token> tokens){
        return tokens;
    }

    //If --> “if” Condition “{“ Statement-Lst “}” | If Else
    private ArrayList<Token> If(ArrayList<Token> tokens){
        return tokens;
    }

    //Else --> “else” “{“ Statement-Lst “}”
    private ArrayList<Token> Else(ArrayList<Token> tokens){
        return tokens;
    }

    /*
    Condition --> Expr “==” Expr | Expr “>=” Expr | Expr “<=” Expr | Expr “>” Expr
        | Expr “<” Expr | Condition “and” Condition | Condition “or” Condition | “true” | “false”
     */
    private ArrayList<Token> Condition(ArrayList<Token> tokens){
        return tokens;
    }

    //Iterator --> Expr “,” Expr
    private ArrayList<Token> Iterator(ArrayList<Token> tokens){
        return tokens;
    }

    //Loop --> “loop” Loop-Assignment “{“ Statement-Lst “}”
    private ArrayList<Token> Loop(ArrayList<Token> tokens){
        return tokens;
    }

    //Loop-Assignment --> Identifier “=” Iterator
    private ArrayList<Token> LoopAssignment(ArrayList<Token> tokens){
        return tokens;
    }

    /*
    Expression --> Expression “+” Expression | Expression “-” Expression | Expression “*” Expression |
        Expression “/” Expression | Expression “%” Expression | “(“ Expression “)” | Number
     */
    private ArrayList<Token> Expression(ArrayList<Token> tokens){
        return tokens;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                         HELPER FUNCTION                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //increment the statement list label
    private void incLabel(){
        String num, tmp;
        int numb;
        tmp = labelStat.substring(0,labelStat.indexOf('-')+1);
        num = labelStat.substring(labelStat.indexOf('-')+1,labelStat.length());
        numb = Integer.parseInt(num)+1;
        labelStat = tmp+numb;
    }

    //print the statement out
    private void printList(ArrayList<ArrayList<Token>> pl){
        for(ArrayList<Token> one: pl){
            System.out.print("Error: ");
            for(Token two: one){
                System.out.print(two.getKey() + " ");
            }
        }
    }
}
