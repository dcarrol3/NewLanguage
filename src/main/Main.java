/*
 * Purpose: Compiler and Runtime start
 * Author(s): Doug Carroll, Jeb Johnson, Isidro Perez and Jason Rice
 * Version: 2
 * Date: 4/6/2017
 */

package main;

import compiler.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        PemdasParser pem = new PemdasParser();
        LexicalAnalyzer la = new LexicalAnalyzer();

        ArrayList<Token> tokens = la.tokenizeString(FileHandler.fileToString("exprTest.txt"));
        ArrayList<Token> expr = new ArrayList<>();
        for (Token token: tokens) {
            System.out.println("<" + token.getType() + ", " + token.getKey() + ">");
            if(!token.getType().equals(GrammarDefs.NEW_LINE)){
                expr.add(token);
            }
        }

        System.out.println(pem.expressionAsPostfix(expr));

    }
}
