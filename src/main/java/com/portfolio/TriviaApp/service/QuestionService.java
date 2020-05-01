package com.portfolio.TriviaApp.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.portfolio.TriviaApp.Constants;
import com.portfolio.TriviaApp.model.Question;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class QuestionService {
		private OkHttpClient client;
		private Response response;
		
		int amount;
		int category;
		String difficulty;
		String type;
		
		public ArrayList<Question> fetchQuestions(int category, int amount,String diificulty,String type) {
			client = new OkHttpClient();
			
			String callUrl = "https://opentdb.com/api.php?amount=" + amount +  "&category=" + category + "&difficulty=" + diificulty +  "&type=" + type;
			Request request = new Request.Builder().url(callUrl).build();
			
			try {
				response = client.newCall(request).execute();
				return generateQuestions(new JSONObject(response.body().string()).getJSONArray("results"));
			} catch (Exception e) {
				System.out.println("a problem has occured in fetchQuestions");
				System.out.println("example URL:  https://opentdb.com/api.php?amount=15&category=15&difficulty=hard&type=multiple" );
				System.out.println("call url: " + callUrl);
			}
			return null;
		}
		
		public JSONArray fetchQuestions() {
			client = new OkHttpClient();
			
			String callUrl = Constants.testCallUrl;
			System.out.println("call url: " + callUrl);
			
			Request request = new Request.Builder().url(callUrl).build();
			
			try {
				response = client.newCall(request).execute();
				return new JSONObject(response.body().string()).getJSONArray("results");
			} catch (Exception e) {
				System.out.println("a problem has occured in fetchQuestions");
			}
			return null;
		}
		
		public ArrayList<Question> fetchQuestions(int category) {
			client = new OkHttpClient();
			String callUrl = "https://opentdb.com/api.php?amount=10&category=" + category + "&difficulty=medium&type=multiple";
			Request request = new Request.Builder().url(callUrl).build();
			
			try {
				response = client.newCall(request).execute();
				return  generateQuestions(new JSONObject(response.body().string()).getJSONArray("results"));
			} catch (Exception e) {
				System.out.println("a problem has occured in fetchQuestions");
			}
			return null;
		}
	
		
		
		
		public ArrayList<Question> generateQuestions(JSONArray jsonArray){
			ArrayList<Question> questions = new ArrayList<>();
			
			for(int i =0;i<=jsonArray.length()-1;i++) {
				String category = jsonArray.getJSONObject(i).getString("category");
				String type = jsonArray.getJSONObject(i).getString("type");
				String difficulty = jsonArray.getJSONObject(i).getString("difficulty");
				String question = jsonArray.getJSONObject(i).getString("question");
				String correctAnswer = jsonArray.getJSONObject(i).getString("correct_answer");
				
				List<Object> incorrectAnswersList = jsonArray.getJSONObject(i).getJSONArray("incorrect_answers").toList();
				ArrayList<String> incorrectAnswers = new ArrayList<String>();
				for(Object incorrectAnswer : incorrectAnswersList) {
					incorrectAnswers.add(incorrectAnswer.toString());
				}
				questions.add(new Question(category, question, type, difficulty, correctAnswer, incorrectAnswers));
			}
			return questions;
		}
		
	
		
		
		
}