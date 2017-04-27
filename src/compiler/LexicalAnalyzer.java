/*
 * Purpose: Lexical Analysis of user code.
 * Author(s): Doug Carroll and Jason Rice
 * Version: 3
 * Date: 4/12/2017
 */

package compiler;

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

public class LexicalAnalyzer {

    private final String GRAMMAR_FILE = "grammar/grammar.json";

    // Creates tokens from the raw code file
    public ArrayList<Token> tokenizeString(String file_string){
        ArrayList<String> strVec;
        ArrayList<Token> vec = new ArrayList<>();
        ArrayList<String> delimiters = getDelimitersFromJson();
        boolean comment_flag = false;
        boolean multiLine_comment_flag = false;

        strVec = splitByDelimiters(file_string, delimiters);
        Token prevToken = new Token();
        Token prevPrevToken = new Token();

        for(String elem : strVec){
            Token token = matchTokenToType(elem);

            // Comment handling
            if(token.getType().equals(GrammarDefs.COMMENT)){
                comment_flag = true;
            }
            else if(token.getType().equals(GrammarDefs.MULTI_LINE_COMMENT_S)){
                multiLine_comment_flag = true;
            }
            else if(token.getType().equals(GrammarDefs.NEW_LINE)){
                comment_flag = false;
            }

            // Add to token list if it's not a comment
            if(!comment_flag && !multiLine_comment_flag){

                // NEGATIVE HANDLER
                // Handle negative numbers
                if(prevToken.getKey().equals(GrammarDefs.SUB_TOKEN)
                        && token.getType().equals(GrammarDefs.WHOLE_NUMBER)
                        && !isOperator(prevPrevToken)){
                    vec.add(new Token(GrammarDefs.OPERATOR, GrammarDefs.SUB_TOKEN));
                    vec.add(token);
                }
                else if(prevToken.getKey().equals(GrammarDefs.SUB_TOKEN)
                        && (token.getType().equals(GrammarDefs.WHOLE_NUMBER))){
                    token.setKey("-" + token.getKey()); // Make number negative
                    vec.add(token); // Number itself
                }
                // Handle negative expressions -(expression)
                else if(token.getType().equals(GrammarDefs.OPEN_PAREN)
                        && prevToken.getKey().equals(GrammarDefs.SUB_TOKEN)
                        && isOperator(prevPrevToken)){
                    vec.add(new Token(GrammarDefs.WHOLE_NUMBER, "-1"));
                    vec.add(new Token(GrammarDefs.OPERATOR, GrammarDefs.MULTI_TOKEN));
                    vec.add(token);
                }
                // Add sub tokens followed by a open (
                else if(token.getType().equals(GrammarDefs.OPEN_PAREN)
                        && prevToken.getKey().equals(GrammarDefs.SUB_TOKEN)){
                    vec.add(new Token(GrammarDefs.OPERATOR, GrammarDefs.SUB_TOKEN));
                    vec.add(token);
                }
                // --
                else if(token.getKey().equals(GrammarDefs.SUB_TOKEN)
                        && prevToken.getKey().equals(GrammarDefs.SUB_TOKEN)){
                    vec.add(token);
                }
                // -x
                else if(token.getType().equals(GrammarDefs.IDENTIFIER)
                        && prevToken.getKey().equals(GrammarDefs.SUB_TOKEN)
                        && isOperator(prevPrevToken)){
                    vec.add(new Token(GrammarDefs.WHOLE_NUMBER, "-1"));
                    vec.add(new Token(GrammarDefs.OPERATOR, GrammarDefs.MULTI_TOKEN));
                    vec.add(token);
                }
                // something-x
                else if(token.getType().equals(GrammarDefs.IDENTIFIER)
                        && prevToken.getKey().equals(GrammarDefs.SUB_TOKEN)){
                    vec.add(new Token(GrammarDefs.OPERATOR, GrammarDefs.SUB_TOKEN));
                    vec.add(token);
                }
                // Add everything but a sub-token
                else if (!token.getKey().equals(GrammarDefs.SUB_TOKEN)){
                    vec.add(token);
                }

            }

            // Check here so the end of multi-line comment is not added
            if(token.getType().equals(GrammarDefs.MULTI_LINE_COMMENT_E)){
                multiLine_comment_flag = false;
            }
            prevPrevToken = prevToken;
            prevToken = token;
        }

        return vec;
    }

    private boolean isOperator(Token token){
        return !token.getType().equals(GrammarDefs.IDENTIFIER)
                && !token.getType().equals(GrammarDefs.WHOLE_NUMBER)
                && !token.getType().equals(GrammarDefs.CLOSE_PAREN);
    }

    // Splits entire code by the grammar delimiters
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

    // Splits each line by the grammar delimiters
    private ArrayList<String> splitLineByDelimiter(String line){
        ArrayList<String> delimiters = getDelimitersFromJson();
        String res_string = "";
        int checks = 1;
        int checksPassed = 0;
        boolean delimiterFound = false;
        ArrayList<String> possibleDelimiters = new ArrayList<>();

        for(int i = 0; i < line.length(); i++){
            delimiterFound = false;
            char currentChar = line.charAt(i);
            for (String delimiter : delimiters) {
                checksPassed = 0;
                // Check based on size of delimiter
                checks = delimiter.length();
                int j;
                for(j = 0; j < checks; j++){
                    if((i + j < line.length())
                            && line.charAt(i + j) == delimiter.charAt(j)){
                        checksPassed++;
                    }
                }
                // We have our delimiter, add it with a space
                if(checks == checksPassed){
                    delimiterFound = true;
                    possibleDelimiters.add(delimiter);
                }
            }

            String largest = getLargestDelimiter(possibleDelimiters);

            if(delimiterFound && largest != null){
                int len = largest.length();
                res_string += " " + line.substring(i, i + len) + " ";
                i = i + (len - 1); // Move to that spot in line
            }
            // If it's not a delimiter, just add it to the result with no space
            else{
                res_string += line.substring(i, i + 1);
            }

            possibleDelimiters.clear(); // Clear possible delimiters before moving on
        }

        return new ArrayList<String>(Arrays.asList(res_string.split("\\s+"))); // Split by space
    }

    // Match each token to a type
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

    private boolean is_char(char val) {
        return((val >= 'a' && val <= 'z') || (val >= 'A' && val <= 'Z'));
    }

    // If a token is a valid variable
    private boolean validIdentifier(String token){
        return !token.equals("") && is_char(token.charAt(0));
    }

    // Grab all delimiters from grammar.json
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

    // Remove redundant empty strings from the token list
    private void removeEmptyStrings(ArrayList<String> list){
        list.removeAll(Arrays.asList(""));
    }

    // Gets the largest delimiter, giving it higher priority
    private String getLargestDelimiter(ArrayList<String> delimiters){
        String largest = null;
        for (String delimiter : delimiters) {
            int len = delimiter.length();
            if(largest == null || len > largest.length()){
                largest = delimiter;
            }
        }

        return largest;
    }

}
