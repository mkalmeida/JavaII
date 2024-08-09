package com.utfpr.javaII;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class UsingNIO {

    private static final String MY_FILE = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "UTFPR" + File.separator + "JavaII" + File.separator + "file-using-nio.txt";

    public UsingNIO() throws IOException {

        writeFile();
        readFile();
    }

    private void writeFile() throws IOException {

        final RandomAccessFile file = new RandomAccessFile(MY_FILE, "rw");
        final FileChannel channel = file.getChannel();

        channel.write(ByteBuffer.wrap("Ola mundo NIO".getBytes(StandardCharsets.UTF_8)));
        channel.close();
        file.close();

        System.out.println("DADOS GRAVADOS COM SUCESSO");

    }

    private void readFile() throws IOException{

        final RandomAccessFile file = new RandomAccessFile(MY_FILE, "rw");
        final FileChannel channel = file.getChannel();

        final long fileSize = channel.size();

        final ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);

        channel.read(buffer);
        buffer.flip();

        for (int i = 0; i < fileSize ; i++){
            System.out.print((char) buffer.get());
        }

        channel.close();
        file.close();


    }

    public static void main(String[] args) throws IOException {
        new UsingNIO();
    }
}
