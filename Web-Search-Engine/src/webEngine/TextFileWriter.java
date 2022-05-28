package webEngine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileWriter {

	private static String DirectoryPath = "/Users/nishantupadhyay/GitHubProjects/SearchEngine/Files/ParsedTextFile";
	public static void writeTextFile(String fileName, String text) {
		fileName = fileName.split("//")[1];
		fileName = fileName.replace("/", "_");
	    try {
        	BufferedWriter  writer = null;
        	writer = new BufferedWriter( new FileWriter(DirectoryPath + "/"+fileName));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return;
    }
}
