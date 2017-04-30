/*
 * Purpose: Compiler and Runtime start
 * Author(s): Doug Carroll, Jeb Johnson, Isidro Perez and Jason Rice
 * Version: 2
 * Date: 4/6/2017
 */

package main;

import compiler.*;
import runtime.JavierRuntime;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static final int OPTION = 0;
    public static final int FILENAME = 1;
    public static final String DATA_PATH = "./data/";
    public static final String UNCOMPILED_EXT = ".javier";
    public static final String COMPILED_EXT = ".jav";
    public static final String COMPILE = "compile";
    public static final String RUN = "run";

    public static void compile(String filename){
        LexicalAnalyzer la = new LexicalAnalyzer();
        StatementToOperation sto = new StatementToOperation();
        IntermediateGenerator icg = new IntermediateGenerator();

        // Lexical analysis and parsing
        // TODO - add validation here
        ArrayList<Token> tokens = la.tokenizeString(FileHandler.fileToString(DATA_PATH + filename + UNCOMPILED_EXT));
        TokenParser tk = new TokenParser(tokens);

        // Convert to intermediate code and PEMDAS it
        ArrayList<Operation> ops = sto.convertProgram(tk.getResults());
        icg.generateCode(ops, DATA_PATH + filename + COMPILED_EXT);
    }

    public static void run(String filename){
        JavierRuntime runtime = new JavierRuntime(DATA_PATH + filename + COMPILED_EXT);
        System.out.println("");
        runtime.run();
    }

    public static void main(String[] args) throws IOException {

        if(args.length < 2){
            System.out.println("Option and Filename required.\n" +
                    "Example: Javier.jar compile myFileName");
        } else {
            String option = args[OPTION];
            String filename = args[FILENAME];

            // Run or Compile the program
            switch(option){
                case COMPILE:
                    if(FileHandler.fileExists(DATA_PATH + filename + UNCOMPILED_EXT)) {
                        compile(filename);
                        System.out.println("\nCompilation finished.");
                    } else {
                        System.out.println("File not found: " + filename + UNCOMPILED_EXT);
                    }
                    break;

                case RUN:
                    if(FileHandler.fileExists(DATA_PATH + filename + COMPILED_EXT)) {
                        run(filename);
                        System.out.println("\nExecution finished.");
                    }
                    else
                        System.out.println("Compiled file not found: " + filename + COMPILED_EXT);
                    break;

                default:
                    System.out.println("Invalid option. Please use compile or run.");
            }
        }
    }
}
