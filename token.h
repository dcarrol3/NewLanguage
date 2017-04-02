/*
 * Holds a token key/value pair
 */
#ifndef INC_502LANG_TOKEN_H
#define INC_502LANG_TOKEN_H

#include <string>

using namespace std;

class Token {
public:
    string key = "";
    string type = "";
    bool isEmpty(){return key == "" || type == "";};
};


#endif //INC_502LANG_TOKEN_H
