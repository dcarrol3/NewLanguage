package com.lang;


public class Token {

    private String key = "";
    private String type = "";

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
