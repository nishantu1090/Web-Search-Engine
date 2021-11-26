package webEngine;
import java.util.*;
public class SearchEngine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean shouldExit = false;
		
		
		System.out.println("######################################################################");
		System.out.println("###############                                #######################");
		System.out.println("###############  Welcome to our Search Engine  #######################");
		System.out.println("###############                                #######################");
		System.out.println("######################################################################");
		System.out.println();
		System.out.println("## Hang Tight! We are building the index####");
		System.out.println("## Starting to Crawl the web....");
		Crawler.startCrawling("http://www.javatpoint.com", 0);
		Crawler.parseURLS();
		System.out.println("## Crawling completed!!");
		System.out.println("## Building Index...");
		TrieInitializer.createTrie();
		try {
			System.out.println("Thread sleeping....");
			Thread.sleep(100000);
			TrieInitializer.createTrie();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("## Index Built!!!");
		
		
		do {
			System.out.println("Enter your choice:\n 1. Search the web \n 2. Exit");
			int ch = sc.nextInt();
			
			switch(ch){
				case 1: System.out.println("Enter the word you wish to search");
						String key = sc.next();
						TrieInitializer.GetRankedURLs(key);
						System.out.println();
					break;
				case 2: shouldExit = true;
					break;
			}
			
		}while(shouldExit == false);
	}

}
