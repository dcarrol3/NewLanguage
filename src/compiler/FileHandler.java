/*
 * Purpose: Handle file read/writes for the compiler.
 * Author(s): Doug Carroll
 * Version: 4
 * Date: 4/15/2017
 */

package compiler;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {

    public static boolean fileExists(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    // Read from file
    public static String fileToString(String filePath){
        Path path = Paths.get(filePath);
        String res = "";
        try{
            res = new String(Files.readAllBytes(path));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    // Write to a file
    public static void stringToFile(String filePath, String data){
        try(PrintWriter out = new PrintWriter(filePath)){
            out.println(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
