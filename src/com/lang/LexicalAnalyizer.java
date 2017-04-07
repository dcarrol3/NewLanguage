package com.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LexicalAnalyizer {

    private final String GRAMMAR_FILE = "grammar/grammar.json";

    public ArrayList<Token> tokenizeString(String file_string){
        ArrayList<String> strVec;
        ArrayList<Token> vec = new ArrayList<>();
        ArrayList<String> delimiters = getDelimitersFromJson();
        boolean comment_flag = false;
        boolean multiLine_comment_flag = false; // TODO - Handle multiline comments

        strVec = splitByDelimiters(file_string, delimiters);

        for(String elem : strVec){
            Token token = matchTokenToType(elem);

            // Comment handling
            if(token.getType().equals(GrammarDefs.COMMENT)){
                comment_flag = true;
            }
            else if(token.getType().equals(GrammarDefs.NEW_LINE)){
                comment_flag = false;
            }

            // Add to token list if it's not a comment
            // Will also NOT add new lines
            else if(!comment_flag){
                vec.add(token);
            }
        }

        return vec;
    }


    private ArrayList<String> splitByDelimiters(String str, ArrayList<String> delimiters){
        ArrayList<String> strVec = new ArrayList<>();
        String line;
        boolean comment_flag;

        BufferedReader bufReader = new BufferedReader(new StringReader(str));

        try {
            while ((line = bufReader.readLine()) != null) {
                ArrayList<String> tokens = splitLineByDelimiter(line);
                removeEmptyStrings(tokens);
                strVec.addAll(tokens);
                strVec.add("\n"); // Add the new line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strVec;
    }

    private ArrayList<String> splitLineByDelimiter(String line){
        ArrayList<String> delimiters = getDelimitersFromJson();
        String str = line;

        for (String delimiter : delimiters) {
            int position = str.indexOf(delimiter);
            int prev = 0;
            String checked = str;
            String afterSplit = "";
            while(position >= 0){
                int endPos = position + (delimiter.length() - 1);
                String d = checked.substring(position, endPos + 1); // delimiter itself
                String before = checked.substring(prev, position); // Get everything before the delimiter
                checked = checked.substring(endPos + 1); // Set the line to everything after delimiter

                afterSplit += before + " " + d + " "; // Split these delimiters by spaces

                prev = endPos + 1; // Record ending position of last delimiter find
                position = checked.indexOf(delimiter, prev); // look at position after that delimiter

            }
            afterSplit += checked; // Add end of line
            str = afterSplit;
        }

        return new ArrayList<String>(Arrays.asList(str.split("\\s+"))); // Split by space
    }

    private Token matchTokenToType(String token) {
        Token res_token = new Token();
        String grammarStr = FileHandler.fileToString(GRAMMAR_FILE);
        JSONParser parser = new JSONParser();

        try {
            JSONArray grammar = (JSONArray) parser.parse(grammarStr);
            // Number
            if(is_number(token)){
                res_token.setKey(token);
                res_token.setType(GrammarDefs.WHOLE_NUMBER);
            }
            // Match with json
            else {
                Iterator i = grammar.iterator();
                while(i.hasNext()){
                    JSONObject elem = (JSONObject) i.next();
                    if (elem.get(GrammarDefs.JSON_TOKEN).equals(token)) {
                        res_token.setKey((String) elem.get(GrammarDefs.JSON_TOKEN));
                        res_token.setType((String) elem.get(GrammarDefs.JSON_TYPE));
                    }
                }

                // Nothing else? Check for variable
                if (res_token.isEmpty() && validIdentifier(token)) {
                    res_token.setKey(token);
                    res_token.setType(GrammarDefs.IDENTIFIER);
                }
                // Must be some error
                else if(res_token.isEmpty()){
                    res_token.setKey(token);
                    res_token.setType(GrammarDefs.ERROR);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return res_token;
    }

    private boolean is_number(String str){
        return str.matches("^-?\\d+$");
    }

    private boolean validIdentifier(String token){
        return !token.equals("") && !is_number(String.valueOf(token.charAt(0)));
    }

    private ArrayList<String> getDelimitersFromJson(){
        String grammarStr = FileHandler.fileToString(GRAMMAR_FILE);
        JSONParser parser = new JSONParser();
        ArrayList<String> delimiters = new ArrayList<>();

        try {
            JSONArray grammar = (JSONArray) parser.parse(grammarStr);
            Iterator i = grammar.iterator();
            while(i.hasNext()){
                JSONObject elem = (JSONObject) i.next();
                if((boolean) elem.get(GrammarDefs.JSON_DELIMITER)){
                    delimiters.add((String) elem.get(GrammarDefs.JSON_TOKEN));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return delimiters;
    }

    private void removeEmptyStrings(ArrayList<String> list){
        list.removeAll(Arrays.asList(""));
    }

    private boolean isComment(String delimiter){
        return delimiter.equals("#");
    }

}
