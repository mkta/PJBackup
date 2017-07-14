package com.brandpath.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by bartoszt on 17.03.2017.
 */
public class ResourceReader {

    public static String resourceFileAsString(String filename) {

        ClassLoader classLoader = ResourceReader.class.getClassLoader();
        String pathS = classLoader.getResource(filename).getFile();

        pathS = System.getProperty( "os.name" ).contains( "indow" ) ? pathS.substring(1) : pathS;


        System.out.println("Resource path is: " + pathS);
        Path path = Paths.get(pathS);

        try {
            return new String(Files.readAllBytes(path));
        }
        catch (IOException io) {
            throw  new RuntimeException(io);
        }
    }
}
