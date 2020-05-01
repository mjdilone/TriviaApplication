package com.portfolio.TriviaApp.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.portfolio.TriviaApp.Constants;
import com.portfolio.TriviaApp.model.Question;
import com.portfolio.TriviaApp.service.QuestionService;

@Controller
public class QuestionController {
	
	@Autowired
	QuestionService service;
	
	public void printQuestions() {
		JSONArray questionsArray = service.fetchQuestions();
		System.out.println(questionsArray.toString());
	}
	
	public ArrayList<Question> printQuestions(int category) {
		return service.fetchQuestions(category);
	}
	
	public ArrayList<Question> fecthQuestions(int category,int amount,String difficulty) {
		return service.fetchQuestions(category,amount,difficulty,Constants.typeMultiple);
	}
	
}