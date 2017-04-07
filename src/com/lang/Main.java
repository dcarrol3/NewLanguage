package com.lang;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        LexicalAnalyizer la = new LexicalAnalyizer();
        ArrayList<Token> tokens = la.tokenizeString(FileHandler.fileToString("test.txt"));
        for (Token token: tokens) {
            System.out.println("<" + token.getType() + ", " + token.getKey() + ">");
        }
    }
}
