package com.portfolio.TriviaApp.model;

import java.util.ArrayList;

public class Question {
	private String category;
	private String question;
	private String type;
	private String difficulty;
	private String correctAnswer;
	private ArrayList<String> incorrectAnswers;
	
	public Question(String category, String question, String type, String difficulty, String correctAnswer,
			ArrayList<String> incorrectAnswers) {
		super();
		this.category = category;
		this.question = question;
		this.type = type;
		this.difficulty = difficulty;
		this.correctAnswer = correctAnswer;
		this.incorrectAnswers = incorrectAnswers;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public ArrayList<String> getIncorrectAnswers() {
		return incorrectAnswers;
	}
	public void setIncorrectAnswers(ArrayList<String> incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "Question [category=" + category + ", question=" + question + ", type=" + type + ", difficulty="
				+ difficulty + ", correctAnswer=" + correctAnswer + ", incorrectAnswers=" + incorrectAnswers + "]";
	}
	
}