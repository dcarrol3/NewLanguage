
#include <fstream>
#include <sstream>
#include "file_handler.h"

using namespace std;

string FileHandler::fileToString(string file_path){
    ifstream in_file;
    string str = "Error";
    stringstream strStream;

    in_file.open(file_path);

    if(in_file.is_open()) {
        strStream << in_file.rdbuf();
        str = strStream.str();
    }

    in_file.close();

    return str;
}