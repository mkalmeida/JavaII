package com.utfpr.javaII;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UsingNIO2 {

    private static final String MY_FILE = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "UTFPR" + File.separator + "JavaII" + File.separator + "file-using-nio2.txt";

    public UsingNIO2() throws IOException {

        writeFile();
        readFile();
    }

    private void writeFile() throws IOException {

        final Path path = Paths.get(MY_FILE);
        Files.writeString(path, "Ola Mundo NIO2");

        System.out.println("DADOS GRAVADOS COM SUCESSO");

    }

    private void readFile() throws IOException{

        final Path path = Paths.get(MY_FILE);
        Files.readAllLines(path).forEach(System.out::println);

    }

    public static void main(String[] args) throws IOException {
        new UsingNIO2();

    }
}
