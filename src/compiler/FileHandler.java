/*
 * Purpose: Handle file read/writes for the compiler.
 * Author(s): Doug Carroll
 * Version: 2
 * Date: 4/6/2017
 */

package compiler;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
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
}
