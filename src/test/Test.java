package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import structures.HashTable;

/*
 * by Mark Hawes
 * 5/1/17
 * 
 * Test class, runs actual test and asks questions/gets answer input
 */
public class Test {
	FileIO fio;
	ReadFile rf;
	String userName;
	Scanner scan;
	boolean test = true;
	String saveTo;
	private double score; // score is an integer because I like the readability - plus it's a full percentage
	private int correct = 0, incorrect = 0, numQ = 0;
	private ArrayList<String> outputArray = new ArrayList<String>();
	private ArrayList<String> outputAnswers = new ArrayList<String>();
	HashTable table = new HashTable();
	
	
	public void run() throws FileNotFoundException{
		scan = new Scanner(System.in);
		System.out.println("Welcome to the Structures of Programming 2 Final Test!");
		fileLoc();
		getName(scan);
		while(test){
			rf = new ReadFile("questionbank.txt");
			System.out.println("Type begin to start test or quit to exit the program");
			String input = scan.next();
			if(input.toLowerCase().equals("begin") || input.toLowerCase().equals("start")){
				ArrayList<String[]> questions = new ArrayList<>();
				questionList(questions);
				runTest(questions);
				calculateGrade();
				// use of hash table
				System.out.println("Enter a UserID for your results");
				input = scan.next();
				table.addUserToChain(input, String.valueOf(score));
				scoreOutput();
				calculateGrade();
				System.out.println("Your score: " + String.valueOf(score));
				System.out.println("Correct: " + correct);
				System.out.println("Incorrect: " + incorrect);
			}
			else if(input.toLowerCase().equals("quit") || input.toLowerCase().equals("exit")){
				test = false;
			}
		}
	}
	
	// prompt for location to save score file
	private void fileLoc(){
		System.out.println("Would you like to save your score file to 'desktop', 'documents' or 'default'");
		String temp = scan.next();
		switch(temp.toLowerCase()){
		case "desktop": saveTo = "/Desktop/"; break;
		case "documents": saveTo = "/Documents/"; break;
		case "default": saveTo = ""; break;
		default: saveTo = "/Desktop/"; break;
		}
	}
	
	// creates list of questions to ask by grabbing randomly from the bag
	private void questionList(ArrayList<String[]> questions){
		for (int i = 0; i < 3; i++) {
			questions.add((String[]) rf.oop.grab(rf.oop.getCurrentSize()));
			questions.add((String[]) rf.generics.grab(rf.generics.getCurrentSize()));
			questions.add((String[]) rf.complexity.grab(rf.complexity.getCurrentSize()));
			questions.add((String[]) rf.lists.grab(rf.lists.getCurrentSize()));
			questions.add((String[]) rf.recursion.grab(rf.recursion.getCurrentSize()));
			questions.add((String[]) rf.bags.grab(rf.bags.getCurrentSize()));
			questions.add((String[]) rf.sortedList.grab(rf.sortedList.getCurrentSize()));
			questions.add((String[]) rf.binarySearch.grab(rf.binarySearch.getCurrentSize()));
			questions.add((String[]) rf.sorting.grab(rf.sorting.getCurrentSize()));
			questions.add((String[]) rf.stacksAndQueues.grab(rf.stacksAndQueues.getCurrentSize()));
			questions.add((String[]) rf.hashing.grab(rf.hashing.getCurrentSize()));
			questions.add((String[]) rf.trees.grab(rf.trees.getCurrentSize()));
			questions.add((String[]) rf.heaps.grab(rf.heaps.getCurrentSize()));
			questions.add((String[]) rf.graphs.grab(rf.graphs.getCurrentSize()));
		}
	}
	
	// asks questions and finds if correct or incorrect
	public void runTest(ArrayList<String[]> questions){
		numQ = 0;
		correct = 0;
		incorrect = 0;
		String[] current;
		String answer;
		while (questions.size() > 0) {
			numQ++;
			StringBuilder sb = new StringBuilder();
			System.out.println("Question " + numQ);
			current = questions.remove(0);
			System.out.println(current[0] + " " + current[1] + " " + current[5]);

			answer = scan.next().toLowerCase();
			if (current[0].replace(" ", "").toLowerCase().equals("tf")) {
				if (answer.charAt(0) == current[2].replace(" ", "").toLowerCase().charAt(0)) {
					System.out.println("Correct!");
					correct++;
					sb.append(numQ);
					sb.append("::");
					sb.append(current[3]);
					sb.append("::");
					sb.append(current[1]);
					sb.append("::");
					sb.append(current[2]);
					sb.append("::");
					sb.append("correct");
					outputArray.add(sb.toString());
					outputAnswers.add(answer);
				} else {
					System.out.println("Incorrect");
					System.out.println("Correct answer is: " + current[2]);
					incorrect++;
					sb.append(numQ);
					sb.append("::");
					sb.append(current[3]);
					sb.append("::");
					sb.append(current[1]);
					sb.append("::");
					sb.append(current[2]);
					sb.append("::");
					sb.append("incorrect");
					outputArray.add(sb.toString());
					outputAnswers.add(answer);
				} // end nested if else
			} else {
				if (answer.equals(current[2].toLowerCase().replace(" ", ""))) {
					System.out.println("Correct!");
					correct++;
					sb.append(numQ);
					sb.append("::");
					sb.append(current[3]);
					sb.append("::");
					sb.append(current[1]);
					sb.append("::");
					sb.append(current[2]);
					sb.append("::");
					sb.append("correct");
					outputArray.add(sb.toString());
					outputAnswers.add(answer);
				} else {
					System.out.println("Incorrect");
					System.out.println("Correct answer is: " + current[2]);
					incorrect++;
					sb.append(numQ);
					sb.append("::");
					sb.append(current[3]);
					sb.append("::");
					sb.append(current[1]);
					sb.append("::");
					sb.append(current[2]);
					sb.append("::");
					sb.append("incorrect");
					outputArray.add(sb.toString());
					outputAnswers.add(answer);
				} // end nested if else
			} // end outer if else

		}
	}
	
	// gets username
	private void getName(Scanner scan){
		System.out.println("Enter your name: ");
		userName = scan.next();
	}
	
	// saves score to output file
	// also points out current answers or incorrect answers
	private void scoreOutput(){
		try{
			fio = new FileIO(userName, outputArray, outputAnswers, calculateGrade(), saveTo);
			System.out.println("Your score is now saved to " + fio.fileName());
		}catch(IOException e){
			System.out.println("File not created");
		} // end catch
	}
	
	// calculates grade and returns
	private double calculateGrade(){
		score = 0;
		double temp = ((double)correct / 42) * 100;
		score = temp;
		return score;
	}
}

// saves question, answer, and whether it is correct or not
class Ans{
	String[] question;
	String answer;
	boolean correct;
	public Ans(String[] question, String answer, boolean correct){
		this.question = (String[]) question;
		this.answer = answer;
		this.correct = correct;
	}
	
	// returns a string of incorrect or correct, because it's easier to read/fileio out then a boolean
	public String getCorrect(){
		if(correct)
			return "correct";
		else
			return "incorrect";
	}
}
