package com.portfolio.TriviaApp;

import java.util.ArrayList;
import java.util.HashMap;

import com.vaadin.flow.component.notification.Notification.Position;

public class Constants {
	public static final String testCallUrl = "https://opentdb.com/api.php?amount=10&category=15&difficulty=medium&type=multiple";
	
	//constant values
	public static final int  guessCounterStartValue = 5;
	public static final int guessCounterLimit = 8;
	public static final int[] questionsAmountValues = {3,10,15,25}; //TODO this might be able to be chaged to init ArrayList similar to below

	
	public static final String typeMultiple = "multiple";
	public static final int[] questionsAmoutInput = {5,10,15,20};
	//tutorial values
	public static final Position tutorialPosition = Position.TOP_START;
	public static final String tutorialStartMessage = "Choose your category of questions, \n amount of questions to be answered and question difficulty";
//	public static String tutorialQuestionPart = "Click the correct answer \n stars represent how many wrong guesses are left\n click the thunderbolt button when it's"
//			+ "available to spend a star and remove two wrong choices";
	public static String tutorialQuestionPart1 = "Click the correct answer , stars represent how many wrong guesses are left";
	public static String tutorialQuestionPart2 = "click the thunderbolt button when it's available to spend a star and remove two wrong choices";
	
	//view routes
	public static final String routeStart = "";
	public static final String routeCategory = "category";
	public static final String routeQuestion = "question";
	public static final String routeTest = "test";
	
	//css locations
	public static final String cssImportAnimate = "./styles/animate.css";
	public static final String cssImportTriviaApp = "./styles/triviaApp.css";
	
	//session value names
	public static final String sessionCategory = "questionsCategorySelectionBox";
	public static final String sessionAmount = "questionsAmountSelectionBox";
	public static final String sessionDifficulty = "difficultySelectionBox";
	
	public static final HashMap<String,Integer> getCategoriesMap(){
		 HashMap<String,Integer> categoriesMap = new HashMap<String, Integer>();
				 categoriesMap.put("General Knowledge",9);
				 categoriesMap.put("Entertainment:Books",10);
				 categoriesMap.put("Entertainment:Film",11);
				 categoriesMap.put("Entertainment:Music",12);
				 categoriesMap.put("Entertainment:Musicals & Theaters",13);
				 categoriesMap.put("Entertainment:Television",14);
				 categoriesMap.put("Entertainment:Video Games",15);
				 categoriesMap.put("Entertainment:Board Games",16);
				 categoriesMap.put("Science & Nature",17);
				 categoriesMap.put("Science: Computers",18);
				 categoriesMap.put("Science: Mathematics",19);
				 categoriesMap.put("Mythology",20);
				 categoriesMap.put("Sports",21);
				 categoriesMap.put("Geography",22);
				 categoriesMap.put("History",23);					
				 categoriesMap.put("Politics",24);					
				 categoriesMap.put("Art",25);					
				 categoriesMap.put("Celebrities",26);					
				 categoriesMap.put("Animals",27);					
				 categoriesMap.put("Vehicles",28);					
				 categoriesMap.put("Entertainment Comics",29);					
				 categoriesMap.put("Science: Gadgets",30);					
				 categoriesMap.put("Entertainment: Japanese Anime & Manga",31);					
				 categoriesMap.put("Entertainment: Cartoon & Animations",32);					
		 return categoriesMap;
	}
	
	@SuppressWarnings("serial")
	public static final ArrayList<String> categoriesList = new ArrayList<String>() {{
	    add("General Knowledge");
	    add("Entertainment:Books");
	    add("Entertainment:Film");
	    add("Entertainment:Music");
	    add("Entertainment:Musicals & Theaters");
	    add("Entertainment:Television");
	    add("Entertainment:Video Games");
	    add("Science & Nature");
	    add("Science: Computers");
	    add("Science: Mathematics");
	    add("Mythology");
	    add("Sports");
	    add("Geography");
	    add("History");
	    add("Politics");
	    add("Art");
	    add("Celebrities");
	    add("Animals");
	    add("Vehicles");
	    add("Entertainment Comics");
	    add("Science: Gadgets");
	    add("Entertainment: Japanese Anime & Manga");
	    add("Entertainment: Cartoon & Animations");
	}};
	
	public static final ArrayList<String> difficultyList = new ArrayList<String>() {{
		add("Easy");
		add("Medium");
		add("Hard");
	}};
}