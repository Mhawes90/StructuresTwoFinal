package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import structures.Bag;

/*
 * by Mark Hawes 
 * 5/1/17
 * 
 * FileIO only creates a file, automatically saves to Desktop
 */

public class FileIO {
	private BufferedWriter output;
	private String userSource = System.getProperty("user.home");
	private String desktop = "/Desktop/";
	private String fileName;
	private int[] catIncorrect = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private String[] categories = {"OO Programming", "Generics", "Complexity & Efficiency", "Lists",
			"Recursion", "Bags & Sets", "SortedList", "Binary Search / Iterators", "Sorting",
			"Stacks & Queues", "Assoc arrays / Hashing", "Trees", "Heaps", "Graphs"};
	
	// default constructor creates the initial file. Uses built in Java tools to get user
	// directory base location and then creates a file onto users desktop
	public FileIO() throws IOException{
		fileName = checkFileName("FinalScore.txt");
		output = new BufferedWriter(new FileWriter(userSource + desktop + "FinalScore.txt"));
	}
	
	// constructor for when you want to specify file name
	// but don't have anything to put into it
	public FileIO(String inputName) throws IOException{
		// borrowed from Zack Albers -- made formatting easier
		Date date = new Date();
		String time = String.valueOf(new Timestamp(date.getTime())).replace(" ", "_").replace(":", "_");
		fileName = inputName + time + ".txt";
		output = new BufferedWriter(new FileWriter(userSource + desktop + fileName));
	}
	
	// constructor for when you already have a string to write to the file
	// still needs to be closed
	public FileIO(String inputName, ArrayList<String> data, ArrayList<String> answer, double score, String saveTo) throws IOException{
		Date date = new Date();
		String time = String.valueOf(new Timestamp(date.getTime())).replace(" ", "_").replace(":", "_");
		fileName = inputName.replace(" ", "") + time + ".txt";
		if(saveTo.equals("default"))
			output = new BufferedWriter(new FileWriter(fileName));
		else
			output = new BufferedWriter(new FileWriter(userSource + saveTo + fileName));
		
		while(data.size() > 0){
			String[] fields = data.remove(0).split("::");
			//chart(fields[1], fields[4]);
			write("Question " + fields[0] + " " + fields[2]);
			write("Your answer: " + answer.remove(0) + " || your answer was " + fields[4] + 
					" || correct answer: " + fields[3]);
		}
		output.newLine();
		write("Total score: " + score);
		//printResults();
		output.close();
	}
	
	private void chart(String in, String answer){
		//System.out.println(answer);
		if(answer == "incorrect")
			switch(in.replace(" ", "").toLowerCase()){
			case "ooprogramming":
				catIncorrect[0] += 1;
				break;
			case "generics":
				catIncorrect[1] += 1;
				break;
			case "complexity&efficiency":
				catIncorrect[2] += 1;
				break;
			case "lists":
				catIncorrect[3] += 1;
				break;
			case "recursion":
				catIncorrect[4] += 1;
				break;
			case "bags&sets":
				catIncorrect[5] += 1;
				break;
			case "sortedlist":
				catIncorrect[6] += 1;
				break;
			case "binarysearch/iterators":
				catIncorrect[7] += 1;
				break;
			case "sorting":
				catIncorrect[8] += 1;
				break;
			case "stacks&queues":
				catIncorrect[9] += 1;
				break;
			case "assocarrays/hashing":
				catIncorrect[10] += 1;
				break;
			case "trees":
				catIncorrect[11] += 1;
				break;
			case "heaps/balancedsearchtrees":
				catIncorrect[12] += 1;
				break;
			case "graphs":
				catIncorrect[13] += 1;
				break;
			default:
				break;
			}
	}
	
	private void printResults() throws IOException {
		write("Categories of your incorrect responses: ");
		for (int i = 0; i < categories.length; i++) {
			output.write(categories[i] + ":");
			for (int j = 0; j < catIncorrect[i]; j++) {
				output.write("*");
			}
			output.newLine();
		}
	}
	
	// gets what the file name is currently
	public String fileName(){
		return fileName;
	}
	
	// writes an array of strings to file
	public void write(String[] toWrite) throws IOException{
		for(String in: toWrite)
			write(in);
	}
	
	// writes given string to file, then moves to next line
	public void write(String toWrite) throws IOException{
		output.write(toWrite);
		output.newLine();
	}
	
	// closes buffered writer
	public void close() throws IOException{
		output.close();
	}
	
	// checks to see if file exists already, and if it does then creates a new name
	// doesn't work
	private String checkFileName(String inputName){
		String tempFile =  inputName;
		File varFile = new File(userSource + desktop + tempFile);
		int i = 1;
		while(varFile.exists()){
			tempFile = tempFile.substring(0, tempFile.length() - 3) + "(" + i + ")" + ".txt";
			varFile = new File(userSource + desktop + tempFile);
		}
		return tempFile;
	}
}
