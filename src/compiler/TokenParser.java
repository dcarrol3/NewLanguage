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

    public TokenParser(ArrayList<Token> tokens){
        labelStat = "label-0";
        statementLine = 0;
        program = new HashMap<>();
        embed = new HashMap<>();
        tokensRef = tokens;
        init(tokens);
    }

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
    public HashMap<Integer,Integer> bracketFinder(ArrayList<Token> tokens){
        ArrayList<String> flags = new ArrayList<>(Arrays.asList("close_bracket","open_bracket"));
        HashMap<Integer,Integer> ret = new HashMap<>();
        Stack<Integer> openBrack = new Stack<>();
        int tokenNum = 0;
        for(Token token: tokens){
            if(flags.contains(token.getType())){
                if(token.getType().equals("open_bracket")){
                    openBrack.push(tokenNum);
                } else{
                    if(openBrack.size() > 0){
                        ret.put(openBrack.pop(),tokenNum);
                    } else{
                        //returns the same if singular close bracket.
                        ret.put(tokenNum,tokenNum);
                    }
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
    public void makeList(int start, int end, String lab){
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
    public boolean Program(ArrayList<Token> tokens){
        return StatementLst(tokens).size() == 0;
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                         HELPER FUNCTION                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //increment the statement list label
    public void incLabel(){
        String num, tmp;
        int numb;
        tmp = labelStat.substring(0,labelStat.indexOf('-')+1);
        num = labelStat.substring(labelStat.indexOf('-')+1,labelStat.length());
        numb = Integer.parseInt(num)+1;
        labelStat = tmp+numb;
    }
}
