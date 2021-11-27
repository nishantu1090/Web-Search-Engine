package webEngine;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Crawler implements Runnable {

	private static Set<String> urls = new HashSet<String>();
	private static int maxDepth = 25;
	private static String regex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
	public static String lastCrawledUrl;
	private static String[] webUrlsToCrawl = new String[5];
	public static Instant startTime;
	static {
		
		startTime = Instant.now();
		webUrlsToCrawl = getArrayOfURLs();
		lastCrawledUrl = webUrlsToCrawl[3];
	}
	public static void startCrawling(String url, int depth) {
		Pattern patternObject = Pattern.compile(regex);
		if(depth < maxDepth) {
			
			try {
				if(isValidUrl(url)) {
					Document doc = Jsoup.connect(url).get();
					urls.add(url);
					
					Elements linksInWebPage = doc.select("a[href]");
					if(linksInWebPage.size() > 1)
						for(Element link : linksInWebPage) {
							
							if (isValidUrl(link.attr("abs:href")) && patternObject.matcher(link.attr("href")).find()) {
								
								if(!isSameDomain(link.attr("abs:href"), url))
								{
									float timeElapsed = Duration.between(startTime, Instant.now()).toMillis();
									if(timeElapsed > 30000)
										return;
									else
										startCrawling(link.attr("abs:href"), ++depth);
								}
								else
									continue;
							}
						}
					else
					{
						Random r = new Random();
						int i = r.nextInt(webUrlsToCrawl.length);
						startCrawling(webUrlsToCrawl[i], ++ depth);
						
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
			lastCrawledUrl = url;
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
				|| url.endsWith(".pdf") || url.contains("#") 
				|| url.contains("mailto:") || url.startsWith("javascript:") || url.endsWith(".gif")
				||url.endsWith(".xml")) {
			return false;
		}
		
		return true;
	}
	
	private static String[] getArrayOfURLs() {
		
		String[] urls = {
				"https://www.google.com/search?q=website+with+links",
				"https://www.google.com/search?q=websites+with+words",
				"https://www.google.com/search?q=search",
				"https://www.oxfordlearnersdictionaries.com/us/wordlists/oxford3000-5000",
				"https://www.javatpoint.com"
		};
		
		return urls;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Crawler crawler = new Crawler();
		Thread crawlerThread = new Thread(crawler);
		crawlerThread.start();
		//parseURLS();

	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Random r = new Random();
		
		while(true) {
			System.out.println("###  Crawling started with url " + lastCrawledUrl+"...");
			startCrawling(lastCrawledUrl, 0);
			if(getUrls().size() != 0)
				parseURLS();
			else {
				int i = r.nextInt(webUrlsToCrawl.length);
				startCrawling(webUrlsToCrawl[i], 0);
			}
			try {
				System.out.println("Crawler Thread going to sleep....");
				Thread.sleep(2000);
				System.out.println("Crawler Thread woke up! Beginning to crawl...");
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
	}

}
