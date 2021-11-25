package webEngine;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Crawler {

	private static Set<String> urls = new HashSet<String>();
	private static int maxDepth = 5;
	private static String regex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
	
	public static void startCrawling(String url, int depth) {
		Pattern patternObject = Pattern.compile(regex);
		if(depth < maxDepth) {
			
			try {
				if(isValidUrl(url)) {
					Document doc = Jsoup.connect(url).get();
					urls.add(url);
					
					Elements linksInWebPage = doc.select("a[href]");
					
					for(Element link : linksInWebPage) {
						
						if (isValidUrl(link.attr("abs:href")) && patternObject.matcher(link.attr("href")).find()) {
							
							if(!isSameDomain(link.attr("abs:href"), url))
								startCrawling(link.attr("abs:href"), ++depth);
							else
								continue;
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
		}
		
		return;
	}
	
	
	public static Set<String> getUrls() {
		
		return urls;
	}
	
	public static void parseURLS() {
		
		for(var url : getUrls()) {
			HTMLParser.readURL(url);
		}
		return;
	}
	private static boolean isSameDomain(String url, String domainlUrl) {
		
		String domainOfNewUrl = url.split("//")[1];
		String domainOfDomainUrl = domainlUrl.split("//")[1];
		domainOfNewUrl = domainOfNewUrl.split("/")[0];
		if(domainOfNewUrl.equals(domainOfDomainUrl))
			return true;
		else
			return false;
	}
	
	private static boolean isValidUrl(String url) {
		if (urls.contains(url)) {
			return false;
		}
		
		
		if (url.endsWith(".jpeg") || url.endsWith(".jpg") || url.endsWith(".png")
				|| url.endsWith(".pdf") || url.contains("#") || url.contains("?")
				|| url.contains("mailto:") || url.startsWith("javascript:") || url.endsWith(".gif")
				||url.endsWith(".xml")) {
			return false;
		}
		
		return true;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		startCrawling("http://www.javatpoint.com", 0);
		//getUrls();
		
		/*for(var url : getUrls()) {
			HTMLParser.readURL(url);
		}*/
		
		parseURLS();

	}

}
