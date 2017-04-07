package com.lang;


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
