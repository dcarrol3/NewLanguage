/*
 * Purpose: Token class to hold token type/value after lexical analysis.
 * Author(s): Doug Carroll
 * Version: 1
 * Date: 4/6/2017
 */

package compiler;


public class Token {

    private String key = "";
    private String type = "";

    public Token(){}

    public Token(String type, String key){
        this.type = type;
        this.key = key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    Boolean isEmpty(){return key.equals("") || type.equals("");}
}
