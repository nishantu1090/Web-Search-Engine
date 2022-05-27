package webEngine;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {
	
	
	public static void parseHTML(String url) {
		String text= "";
		try {
			Document doc = Jsoup.connect(url).get();
			Element body = doc.body();
		    Elements paragraphs = body.getElementsByTag("p");
		    for (Element paragraph : paragraphs) {
		    	text += paragraph.text() + " ";
		    }
		      
		    TextFileWriter.writeTextFile(url, text);
		    return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		parseHTML("http://www.javatpoint.com");
	}

}
