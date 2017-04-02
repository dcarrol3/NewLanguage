cmake_minimum_required(VERSION 3.6)
project(502Lang)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp file_handler.cpp lex.cpp)
add_executable(502Lang ${SOURCE_FILES})