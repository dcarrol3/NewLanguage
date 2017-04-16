/*
 * Purpose: Compiler and Runtime start
 * Author(s): Doug Carroll, Jeb Johnson, Isidro Perez and Jason Rice
 * Version: 2
 * Date: 4/6/2017
 */

package main;

import compiler.*;
import runtime.Runtime;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        PemdasParser pem = new PemdasParser();
        LexicalAnalyzer la = new LexicalAnalyzer();

        ArrayList<Token> tokens = la.tokenizeString(FileHandler.fileToString("exprTest.txt"));

        System.out.println("\n\n /////////////////LEXICAL////////////////////");
        ArrayList<Token> expr = new ArrayList<>();
        for (Token token: tokens) {
            System.out.println("<" + token.getType() + ", " + token.getKey() + ">");
            if(!token.getType().equals(GrammarDefs.NEW_LINE)){
                expr.add(token);
            }
        }

        System.out.println("\n\n /////////////////PARSER////////////////////");
        TokenParser tk = new TokenParser(tokens);



        System.out.println("\n\n /////////////////INTERMEDIATE////////////////////");
        ArrayList<Operation> ops = pem.parseExpression(tokens);
        for (Operation op: ops) {
            System.out.println(op.getType() + " "
                    + op.getVariable() + " "
                    + op.getValue1() + " "
                    + op.getValue2());
        }
        IntermediateGenerator icg = new IntermediateGenerator();

        // Create runtime file
        String lastVar = ops.get(ops.size() - 1).getVariable(); // Get last variable for printing
        ops.add(new Operation(Operation.OperationType.PRINT, lastVar));
        icg.generateCode(ops, "test.txt");



        System.out.println("\n\n /////////////////RUNTIME////////////////////");
        System.out.println(FileHandler.fileToString("./data/test.txt") + "\n");
        System.out.println("Output:");
        Runtime runtime = new Runtime("./data/test.txt");
        try {
            runtime.run();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
