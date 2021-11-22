package webEngine;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import textprocessing.*;

public class TrieInitializer {
	
	private static TrieST<ArrayList<ReferenceCount>> st;
	
	static {
		st = new TrieST<ArrayList<ReferenceCount>>();
	}
	
	public static void createTrie() {
		
		File folder = new File("/Users/nishant-mac/Classes/ACC/Project/ACC-Project/Web-Search-Engine/Text-Files-Short");
		File[] listOfFiles = folder.listFiles();
	    
		for(int i = 0; i < listOfFiles.length; i++) {
			
			String fileName = listOfFiles[i].getName();
			System.out.println(fileName);
			
			String[] words = getWordsInFile(fileName);
			
			for(var word : words) {
				
				if(word != "" && word != null) {
					if(st.get(word) == null) {
						ArrayList<ReferenceCount> newRef = new ArrayList<ReferenceCount>();
						newRef.add(new ReferenceCount(fileName, 1));
						st.put(word, newRef);
					}
					else {
						ArrayList<ReferenceCount> refs = st.get(word);
						int isFound = 0;
						for(int k = 0; k < refs.size(); k++) {
							
							if(refs.get(k).getReferenceName().equals(fileName)) {
								refs.get(k).setReferenceCount(refs.get(k).getCount() + 1);
								isFound = 1;
								break;
							}
						}
						
						if(isFound == 0) {
							ArrayList<ReferenceCount> referencedPages = st.get(word);
							referencedPages.add(new ReferenceCount(fileName,1));
							st.put(word, referencedPages);
						}
						
					}
						
				}
			}
		}		
		
		
	}
	
	public static String[] getWordsInFile(String fileName) {
		In in = new In("/Users/nishant-mac/Classes/ACC/Project/ACC-Project/Web-Search-Engine/Text-Files-Short/" + fileName);
		String text = in.readAll();
		StringTokenizer tokenizer = new StringTokenizer(text);
		
		String[] words = new String[text.length()];
		int i =0 ;
		while(tokenizer.hasMoreTokens()) {
			words[i++] = tokenizer.nextToken();
		}
		
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TrieInitializer.createTrie();
		ArrayList<ReferenceCount> refs = TrieInitializer.getReferences("Standards");
		
		Collections.sort(refs);
		System.out.println("Found the word in the following files");
		for(var item : refs) {
			System.out.println(item.getReferenceName() + " " + item.Count);
		}
		
		
		
	}

}
