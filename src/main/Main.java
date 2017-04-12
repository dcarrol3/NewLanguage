/*
 * Purpose: Compiler and Runtime start
 * Author(s): Doug Carroll, Jeb Johnson, Isidro Perez and Jason Rice
 * Version: 2
 * Date: 4/6/2017
 */

package main;

import compiler.FileHandler;
import compiler.LexicalAnalyzer;
import compiler.Token;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        LexicalAnalyzer la = new LexicalAnalyzer();

        ArrayList<Token> tokens = la.tokenizeString(FileHandler.fileToString("test.txt"));
        for (Token token: tokens) {
            System.out.println("<" + token.getType() + ", " + token.getKey() + ">");
        }
    }
}
