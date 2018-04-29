package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import structures.Bag;

/*
 * by Mark Hawes
 * 5/1/17
 * 
 * ReadFile class uses a scanner to scan all info from the questionbank and saves it accordingly
 */

public class ReadFile {
	Scanner input;
	String splitter;
	ArrayList<String[]> data = new ArrayList<String[]>();
	// Using bags because it the test needs to grab a random question, meaning the random grab of bag
	// makes it the best for this situation
	Bag<String[]> oop = new Bag<String[]>();
	Bag<String[]> generics = new Bag<String[]>();
	Bag<String[]> complexity = new Bag<String[]>();
	Bag<String[]> lists = new Bag<String[]>();
	Bag<String[]> recursion = new Bag<String[]>();
	Bag<String[]> bags = new Bag<String[]>();
	Bag<String[]> sortedList = new Bag<String[]>();
	Bag<String[]> binarySearch = new Bag<String[]>();
	Bag<String[]> sorting = new Bag<String[]>();
	Bag<String[]> stacksAndQueues = new Bag<String[]>();
	Bag<String[]> hashing = new Bag<String[]>();
	Bag<String[]> trees = new Bag<String[]>();
	Bag<String[]> heaps = new Bag<String[]>();
	Bag<String[]> graphs = new Bag<String[]>();
	

	// default constructor, builds an ArrayList from specified document
	// creates an ArrayList of string arrays
	public ReadFile(String fileName) throws FileNotFoundException{
		File file = new File(fileName);
		splitter = "::";
		input = new Scanner(file);
		
		while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] fields = line.split(splitter);
            if(fields.length == 7)
            	addToCase(fields[3], fields);
        }
	}
	
	// fixes type of question to remove odd characters
	public void fixType(){
		for(int i = 0; i < data.size(); i++){
			String[] temp = data.get(i);
			if(temp[0].contains("/")){
				temp[0] = temp[0].replace("/", "").toLowerCase();
				data.remove(i);
				data.add(i, temp);
			}// end if
		}// end for
	}
	
	// simplifies data, then checks all data and sorts into proper sections
	public void seperate(){
		for(int i = 0; i < data.size();i++){
			String[] check = (String[])data.get(i);
			addToCase(check[3], check);
		}
	}
	
	// uses a string to check where array needs to be moved to
	private void addToCase(String toCheck, String[] in){
		switch(toCheck.replace(" ", "").toLowerCase()){
		case "ooprogramming": oop.add(in); break;
		case "generics": generics.add(in); break;
		case "complexity&efficiency": complexity.add(in); break;
		case "list": case "lists": lists.add(in); break;
		case "recursion": recursion.add(in); break;
		case "bags&sets":bags.add(in); break;
		case "sortedlist": sortedList.add(in); break;
		case "binarysearch/iterators": binarySearch.add(in); break;
		case "sorting": sorting.add(in); break;
		case "stacks&queues": stacksAndQueues.add(in); break;
		case "assocarrays/hashing": hashing.add(in); break;
		case "trees": trees.add(in); break;
		case "heaps/balancedsearchtrees": heaps.add(in); break;
		case "graphs": graphs.add(in); break;
		}
	}
	
	// gets specified line
	public String[] getLine(int line){
		return data.get(line);
	}
	
	// creates a string based on specified line
	public String buildLine(int line){
		StringBuilder build = new StringBuilder();
		
		for(String test: data.get(line))
			build.append(test + " ");
		
		return build.toString();
	}
	
	// begin methods to get specific data
	
	// get question from specified line
	public String getQuestion(int line){
		String[] text = getLine(line);
		return text[1];
	}
	
	// get answer from specified line
	public String getAns(int line){
		String[] text = getLine(line);
		return text[2];
	}
	
	// get question type from specified line
	public String getType(int line){
		String[] text = getLine(line);
		return text[0];
	}
	
	// get category from specified line
	public String getCat(int line){
		String[] text = getLine(line);
		return text[3];
	}
	
	// get sub-topic from specified line
	public String getSub(int line){
		String[] text = getLine(line);
		return text[4];
	}
	
	// get initials from specified line
	public String getInitials(int line){
		String[] text = getLine(line);
		return text[5];
	}
	
	// returns data from given line's question
	public String getDate(int line){
		String[] text = getLine(line);
		return text[6];
	}
}
