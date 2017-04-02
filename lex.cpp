
#include "lex.h"
#include "json/json.hpp"
#include "grammar/grammar_defs.h"
#include <iostream>
#include <sstream>
#include <iterator>


using namespace std;
using json = nlohmann::json;

vector<Token> LexicalAnalyzer::tokenizeString(string file_string) {
    vector<string> strVec;
    vector<Token> vec;
    vector<string> delimiters = getDelimitersFromJson();

    strVec = splitByDelimiters(file_string, delimiters);

    for(string& elem : strVec){
        vec.push_back(matchTokenToType(elem));
    }

    return vec;
}

vector<string> LexicalAnalyzer::splitByDelimiters(string str, vector<string> delimiters){
    vector<string> strVec;
    stringstream stringStream(str);
    string line;

    while(getline(stringStream, line))
    {
        size_t prev = 0, position;
        while ((position = line.find_first_of(joinStrings(delimiters), prev)) != string::npos)
        {
            if (position > prev)
                strVec.push_back(line.substr(prev, position - prev));

            strVec.push_back(line.substr(position,1)); // Includes delimiter

            prev = position + 1;
        }
        // Add rest of the line
        if (prev < line.length())
            strVec.push_back(line.substr(prev, string::npos));
    }

    return strVec;
}

Token LexicalAnalyzer::matchTokenToType(string token){
    Token * res_token = new Token();
    string grammarStr = fh.fileToString(GRAMMAR_FILE);
    json grammar = json::parse(grammarStr);

    // Number
    if(is_number(token)){
        res_token->key = token;
        res_token->type = WHOLE_NUMBER;
    }
    // Match with json
    else {
        for (json &elem : grammar) {
            if (elem[JSON_TOKEN] == token) {
                res_token->key = elem[JSON_TOKEN];
                res_token->type = elem[JSON_TYPE];
            }
        }

        // Nothing else? Check for variable
        string first =  string(1, token.at(0)); // First letter can't be a variable
        if (res_token->isEmpty() && !is_number(first)) {
            res_token->key = token;
            res_token->type = IDENTIFIER;
        }
        // Must be some error
        else if(res_token->isEmpty()){
            res_token->key = token;
            res_token->type = ERROR;
        }
    }

    return *res_token;

}

bool LexicalAnalyzer::is_number(const string str){
    string::const_iterator it = str.begin();
    while(it != str.end() && isdigit(*it))
        ++it;
    return !str.empty() && it == str.end();
}

string LexicalAnalyzer::joinStrings(vector<string> strArray){
    string str;
    for(string& elem : strArray){
        str.append(elem);
    }
    return str;
}

vector<string> LexicalAnalyzer::getDelimitersFromJson(){
    json grammars = json::parse(fh.fileToString(GRAMMAR_FILE));
    vector<string> delimiters;

    for(json& grammar : grammars){
        if(grammar[JSON_DELIMITER]){
            delimiters.push_back(grammar[JSON_TOKEN]);
        }
    }

    return delimiters;
}


