package com.utfpr.javaII;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UsingIO {

	private static final String MY_FILE = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "UTFPR" + File.separator + "JavaII" + File.separator + "file-using-io.txt";

	public UsingIO() throws IOException {

		writeFile();
		readFile();
	}

	private void writeFile() throws IOException {

		final OutputStream output = new FileOutputStream(MY_FILE);
		File file = new File(MY_FILE);

		output.write("Ola Mundo IO".getBytes(StandardCharsets.UTF_8));
		output.close();

		System.out.println("DADOS GRAVADOS COM SUCESSO");
		System.out.println("Caminho absoluto: " + file.getAbsolutePath());

	}

	private void readFile() throws IOException{

		// 1 byte = 8 bits
		// 1 char UTF-8 = 1 bytes
		// 1 char UTF-16 = 2 bytes

/*		try(final InputStream input = new FileInputStream(MY_FILE)){
			int content;
			while((content = input.read()) != -1){
				System.out.print((char) content);
			}
		} catch (IOException e){
			e.printStackTrace();
		}	*/

		try(final FileReader reader = new FileReader(MY_FILE)){
			int content = reader.read();
			while(content != -1){
				System.out.print((char) content);
				content = reader.read();
			}
		} catch (IOException e){
			e.printStackTrace();
		}






	}

	public static void main(String[] args) throws IOException {
		new UsingIO();
	}

}
