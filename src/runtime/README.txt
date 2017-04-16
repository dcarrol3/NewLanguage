Instructions regarding the runtime

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

To start the runtime use the following code
The runtime object takes the path of the parsed code

Runtime runtime = new Runtime("./src/parsed.txt");
runtime.run();

Make sure parsed.txt is comma separated, no spaces

Sample:

add,t0,6,7
eq,t1,t0,5
print,t1

OUTPUT: false