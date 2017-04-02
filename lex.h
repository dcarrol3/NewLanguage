

#ifndef INC_502LANG_LEX_H
#define INC_502LANG_LEX_H

#include <string>
#include <vector>
#include "token.h"
#include "file_handler.h"


using namespace std;

#define GRAMMAR_FILE "../grammar/grammar.json"

class LexicalAnalyzer {
public:
    vector<Token> tokenizeString(string file_string);

private:
    FileHandler fh;
    // Split the string by delimiters provided. Delimiters space ; and }
    // would be " ;}"
    vector<string> splitByDelimiters(string str, vector<string>);
    // Create tokens with types assigned
    Token matchTokenToType(string token);
    // Tell if input is a number or not
    bool is_number(const string str);
    // Join vector of strings to one string
    string joinStrings(vector<string>);
    // Get all delimiters from the json file
    vector<string> getDelimitersFromJson();
};


#endif
