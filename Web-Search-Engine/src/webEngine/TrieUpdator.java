package webEngine;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import textprocessing.*;

public class TrieUpdator implements Runnable {
	private static TrieST<ArrayList<ReferenceCount>> st;
	private static String DirectoryPath = "/Users/nishantupadhyay/GitHubProjects/SearchEngine/Files/ParsedTextFile";
	static {
		st = new TrieST<ArrayList<ReferenceCount>>();
	}
	
	public static void readParsedFilesToUpdateTrie() {
		File folder = new File(DirectoryPath);
		File[] listOfFiles = folder.listFiles();
		
		//looping through all the files to fetch words
		for(int i = 0; i < listOfFiles.length; i++) {
			String fileName = listOfFiles[i].getName();
			String[] words = getWordsInFile(fileName);
			fileName = fileName.replace("_", "/");
		
			//looping through all the words
			for(var word : words) {
				updateTrieWithNewEntry(fileName, word);
			}
		}		
	}
	
	public static void updateTrieWithNewEntry(String fileName, String word) {
		if(word != "" && word != null) {
			if(st.get(word) == null) {
				ArrayList<ReferenceCount> newWebsiteRef = new ArrayList<ReferenceCount>();
				newWebsiteRef.add(new ReferenceCount(fileName, 1));
				st.put(word, newWebsiteRef);
			}
			else {//increasing the reference count
				ArrayList<ReferenceCount> refs = st.get(word);
				for(int k = 0; k < refs.size(); k++) {
					if(refs.get(k).getReferenceName().equals(fileName)) {
						refs.get(k).setReferenceCount(refs.get(k).getCount() + 1);
						break;
					}
				}
			}
		}
	}
	
	public static String[] getWordsInFile(String fileName) {
		In in = new In(DirectoryPath + "/" + fileName);
		String text = in.readAll();
		StringTokenizer tokenizer = new StringTokenizer(text);
		String[] words = new String[text.length()];
		int i =0 ;
		while(tokenizer.hasMoreTokens()) {
			words[i++] = tokenizer.nextToken();
		}
		File f = new File(DirectoryPath + "/" + fileName);
		f.delete();
		return filterWords(words);
	}
	
	public static String[] filterWords(String[] words) {
		String[] stopWords = { "I", "we", "is", "as", "the", "it", "at", "when", "where", "because", "this", "that", "then", "could",
				"should", "shall", "neither", "or", "and", "nor", "there", "their"};
		for(int i = 0; i < words.length; i++) {
			for(int j = 0; j < stopWords.length; j++) {
				if(words[i]!=null && words[i].equals(stopWords[j]))
					words[i] = "";
			}
		}
		return words;
	}
	
	public static ArrayList<ReferenceCount> getReferences(String key){
		return st.get(key);
	}
	
	public static void PrintRankedURLs(String key) {
		ArrayList<ReferenceCount> refs = TrieUpdator.getReferences(key);
		if(refs != null) {
			Collections.sort(refs);
		}
		System.out.println("######################Search Results#####################");
		if(refs != null && refs.size() > 0) {
			for(var item : refs) {
				System.out.println(item.getReferenceName());
			}
		}
		else
			System.out.println("Sorry! The search word is not found!");
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Crawler crawler = new Crawler();
		Thread crawlerThread = new Thread(crawler);
		crawlerThread.start();
		TrieUpdator trie = new TrieUpdator();
		Thread TrieThread = new Thread(trie);
		TrieThread.start();
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Enter the word to search:");
			String key = sc.next();
			TrieUpdator.PrintRankedURLs(key);
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			TrieUpdator.readParsedFilesToUpdateTrie();
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
