package webEngine;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {
	
	public static void readURL(String url) {
		
		try {
			Document doc = Jsoup.connect(url).get();
			Element body = doc.body();
		      Elements paragraphs = body.getElementsByTag("p");
		      for (Element paragraph : paragraphs) {
		         System.out.println(paragraph.text());
		      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readURL("http://www.javatpoint.com");
	}

}
