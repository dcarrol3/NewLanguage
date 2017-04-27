Instructions regarding the javierRuntime

Current keywords are stored in Constants.java, the following operations are included

ADD = "add";
SUBTRACT = "sub";
DIVIDE = "div";
MULTIPLY = "multi";
EQUALS = "eq";
NOT_EQUAL = "neq";
GREATER_THAN = "gt";
LESS_THAN = "lt";
LESS_THAN_EQUAL_TO = "ltq";
GREATER_THAN_EQUAL_TO = "gtq";
AND = "and";
OR = "or";
PRINT = "print";
IF = "if";
JUMP = "jump";
MOD = "mod"'

To start the javierRuntime use the following code
The javierRuntime object takes the path of the parsed code

JavierRuntime javierRuntime = new JavierRuntime("./src/runtime/parsed.txt");
javierRuntime.run();

Make sure parsed.txt is comma separated, no spaces

Sample:

add,t0,6,7
eq,t1,t0,5
print,t1

OUTPUT: false