/*
 * Purpose: runtime which reads/runs a given text file containing parsed code
 * Author(s): Isidro Perez
 * Version: 1
 * Date: 4/16/2017
 */

package runtime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Runtime {
    private HashMap<String, String> symbolTable;
    private String fileName;

    // set the symbol table to be empty when runtime is instantiated
    public Runtime(String fileName) {
        this.fileName = fileName;
        this.symbolTable = new HashMap<>();
    }

    // add all labels and their line number to the Symbol Table
    private void addLinesStatements(String[] lines) {
        for(int i = 0; i < lines.length; i++) {
            // a label will not have a ',' character in it
            if(lines[i].indexOf(',') == -1) {
                symbolTable.put(lines[i], String.valueOf(i));
            }
        }
    }

    // read the file that was parsed
    public void run() throws IOException {
        readFile();
    }

    // go through each line of the file
    // the first word on each line determines the statement
    private void readLines(String[] lines) {
        for(int i = 0; i < lines.length; i++) {
            String[] statements = lines[i].split(",");
            switch(statements[0]) {
                case Constants.ADD:
                    add(statements);
                    break;
                case Constants.PRINT:
                    print(statements);
                    break;
                case Constants.SUBTRACT:
                    sub(statements);
                    break;
                case Constants.MULTIPLY:
                    multi(statements);
                    break;
                case Constants.DIVIDE:
                    div(statements);
                    break;
                case Constants.GREATER_THAN:
                    greaterThan(statements);
                    break;
                case Constants.LESS_THAN:
                    lessThan(statements);
                    break;
                case Constants.EQUALS:
                    equals(statements);
                    break;
                case Constants.NOT_EQUAL:
                    notEquals(statements);
                    break;
                case Constants.LESS_THAN_EQUAL_TO:
                    lessThanEqualTo(statements);
                    break;
                case Constants.GREATER_THAN_EQUAL_TO:
                    greaterThanEqualTo(statements);
                    break;
                case Constants.AND:
                    and(statements);
                    break;
                case Constants.OR:
                    or(statements);
                    break;
                case Constants.IF:
                    if(!Boolean.valueOf(symbolTable.get(statements[1]))) {
                        i = Integer.valueOf(symbolTable.get(statements[2]));
                    }
                    break;
                case Constants.JUMP:
                        i = Integer.valueOf(symbolTable.get(statements[1]));
                    break;
                default:
                    break;
            }
        }
    }

    private void and(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Boolean.parseBoolean(contains(statements[2])) && Boolean.parseBoolean(contains(statements[3]))));
    }

    private void or(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Boolean.parseBoolean(contains(statements[2])) || Boolean.parseBoolean(contains(statements[3]))));
    }

    // <=
    private void greaterThanEqualTo(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) >= Integer.parseInt(contains(statements[3]))));
    }

    // >=
    private void lessThanEqualTo(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) <= Integer.parseInt(contains(statements[3]))));

    }

    // !=
    private void notEquals(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) != Integer.parseInt(contains(statements[3]))));
    }

    // ==
    private void equals(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) == Integer.parseInt(contains(statements[3]))));
    }

    // <
    private void lessThan(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) < Integer.parseInt(contains(statements[3]))));
    }

    // >
    private void greaterThan(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) > Integer.parseInt(contains(statements[3]))));
    }

    // /
    private void div(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) / Integer.parseInt(contains(statements[3]))));
    }

    // console print
    private void print(String[] statements) {
        String value = contains(statements[1]);
        System.out.println(value);
    }

    // *
    private void multi(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) * Integer.parseInt(contains(statements[3]))));
    }

    // -
    private void sub(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) - Integer.parseInt(contains(statements[3]))));
    }

    // +
    private void add(String[] statements) {
        symbolTable.put(statements[1],
                String.valueOf(Integer.parseInt(contains(statements[2])) + Integer.parseInt( contains(statements[3]))));
    }

    // if one of the arguments is a variable, grab its value
    // else return the argument
    private String contains(String statement) {
        return symbolTable.getOrDefault(statement, statement);
    }

    // read the file
    private void readFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // split the file by new line character
        String[] lines = sb.toString().split("\\n");

        // put all of the labels in the symbol table
        addLinesStatements(lines);

        // read the file from top to bottom
        readLines(lines);
    }
}
