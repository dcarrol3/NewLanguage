#include <iostream>
#include "file_handler.h"
#include "lex.h"

using namespace std;

int main() {
    FileHandler fh;
    string test = fh.fileToString("../test.txt");
    LexicalAnalyzer la;

    cout << "File: " << endl << endl << test << endl << endl;
    vector<Token> testTokenVec = la.tokenizeString(test);

    for(vector<Token>::iterator it = testTokenVec.begin(); it != testTokenVec.end(); ++it){
        cout << "<" << it->key << ", " << it->type << ">" << endl;
    }


    return 0;
}