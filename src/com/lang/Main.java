package com.lang;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        LexicalAnalyizer la = new LexicalAnalyizer();

        ArrayList<Token> tokens = la.tokenizeString(FileHandler.fileToString("test.txt"));
        for (Token token: tokens) {
            System.out.println("<" + token.getType() + ", " + token.getKey() + ">");
        }
    }
}
