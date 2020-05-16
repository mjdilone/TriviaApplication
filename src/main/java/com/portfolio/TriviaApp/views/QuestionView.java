package com.portfolio.TriviaApp.views;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.portfolio.TriviaApp.Constants;
import com.portfolio.TriviaApp.Utils;
import com.portfolio.TriviaApp.controller.QuestionController;
import com.portfolio.TriviaApp.model.Question;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(Constants.routeQuestion)
//@CssImport("./styles/shared-styles.css")
//@CssImport(Constants.cssImportAnimate)
//@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
//@CssImport(Constants.cssImportTriviaApp)
//@Theme(value = Lumo.class)
@StyleSheet("frontend://triviaApp.css")
@StyleSheet("frontend://shared-styles.css")
@StyleSheet("frontend://animate.css")
@HtmlImport("shadow-dom-styles.html")
public class QuestionView extends VerticalLayout {
	private static final long serialVersionUID = -9173194680925998171L;
	
	private boolean tutorialIsClosed = false;
	private boolean isFirstInit = true;
	
	//values for fetching questions
	private String chosenCategory;
	private String chosenAmount;
	private String chosenDifficulty;
	
	@Autowired
	QuestionController controller;
	
	private int questionCounter = 0;
	private int guessCounter = Constants.guessCounterStartValue;
	
	//View Variables
	private HashMap<String, Integer> map = Constants.getCategoriesMap();
	
	private ArrayList<Question> questionsList; //TODO rename printQuestions(), sounds like a void method
	
	private HorizontalLayout questionLayout;	
	private Label questionLabel;
	
	private HorizontalLayout answerLayout;
	private ListBox<String> answers;
	private ArrayList<String> answersList;
	
	private Label correctLabel;
	
	private VerticalLayout footerLayout;
	private ProgressBar progressBar;
	
	private Label guessesLabel;
	
	//buttons
	private Button nextButton;
	private Button failureButton;
	
	private HorizontalLayout guessLayout;
	private Icon guessIcon;
	
	Label failureLabel;
	
	//game logic variables
	private boolean gameIsFirstAttempt;
	private VerticalLayout gameStarSpendLayout;
	private Button gameStarSpendButton;
	private Boolean gameStarSpendButtonIsAllowed;
	private int gameConsecutiveCorrectCounter;
	
	public QuestionView() {
		this.setAlignItems(Alignment.CENTER);
	}
	
	//post construct is needed here, autowired beans are not injected before a constructor is called
	@PostConstruct
	public void init() {
		removeAll(); //TODO should be a better way of doing this
		checkChosenValues(chosenCategory,chosenAmount,chosenDifficulty);
		if(questionsList == null) {
			questionsList = controller.fecthQuestions(map.get(chosenCategory), Integer.parseInt(chosenAmount), chosenDifficulty);
		}
		
		//questions
		try {
			questionLayout = new HorizontalLayout();
			
			questionLayout.setAlignItems(Alignment.CENTER);
			questionLabel = new Label(Utils.replaceHtml(questionsList.get(questionCounter).getQuestion()));
			questionLabel.addClassName("question");
			questionLayout.add(questionLabel);
			
			//add guess layout
			guessLayout = new HorizontalLayout();
			for(int i = 0;i< guessCounter;i++) {
				guessLayout.add((new Icon("vaadin", "star")));
			}
			
			add(questionLayout);
			add(guessLayout);
			
			gameIsFirstAttempt = true;
			
			if(isFirstInit) {
				gameConsecutiveCorrectCounter = 0;
				gameStarSpendButtonIsAllowed = true;
				allowSpendButton();
				isFirstInit = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException();			
		}
		
		try {
			answerLayout = new HorizontalLayout();
			answers = new ListBox<>();
			answersList = new ArrayList<String>(questionsList.get(questionCounter).getIncorrectAnswers());
			answersList.add(questionsList.get(questionCounter).getCorrectAnswer());
			System.out.println("Correct Answer: " + questionsList.get(questionCounter).getCorrectAnswer());//TODO put this in the logger instead
			Collections.shuffle(answersList);
			for(String string : answersList) {
				string = Utils.replaceHtml(string);
			}
			answers.setItems(answersList);
			correctLabel = new Label();
			correctLabel.setVisible(false);
			correctLabel.addClassNames("correctLabel");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException();		
		}
		
		//change listener for answers, this checks if you pick correctly
		answers.addValueChangeListener(valueChangeEvent -> {
			if(guessCounter>0) {
				if(valueChangeEvent.getValue().equalsIgnoreCase(questionsList.get(questionCounter).getCorrectAnswer())) {
					
					//testing
					System.out.println("consecutive " + gameConsecutiveCorrectCounter);
					if(gameIsFirstAttempt) {
						raiseGuessCounter();
						raiseConsecutiveCounter();
						checkForStarSpendButton();
					}
//					remove(guessLayout);
//					guessLayout = new HorizontalLayout();
					guessLayout.removeAll();
					for(int i = 0;i< guessCounter;i++) {
						guessLayout.add((new Icon("vaadin", "star")));
					}
//					questionLayout.add(guessLayout);
//					add(guessLayout);
					
					
					correctLabel.setText("Correct!");
					correctLabel.setVisible(true);
					nextButton.setVisible(true);
				}else {
					gameIsFirstAttempt=false;
					guessCounter--;
					
//					remove(guessLayout);
//					guessLayout = new HorizontalLayout();
					guessLayout.removeAll();
					for(int i = 0;i< guessCounter;i++) {
						guessLayout.add((new Icon("vaadin", "star")));
					}
//					questionLayout.add(guessLayout);
//					add(guessLayout);
					
					correctLabel.setText("Wrong!");
					correctLabel.setVisible(true);
					
					if(guessCounter <= 0) {
						add(failureLabel);
						add(failureButton);
					}
				}
			}else {
				this.getUI().ifPresent(ui -> ui.navigate(Constants.routeCategory));
			}
		});
		
		try {
			answerLayout.add(answers);
			add(answerLayout);
			
			//shows if the correct answer was chosen
			add(correctLabel);
			
			//footer
			footerLayout = new VerticalLayout();
			
			footerLayout.setAlignItems(Alignment.CENTER);
			
			//star spender layout
			gameStarSpendLayout = new VerticalLayout();
			
			
			//spend a star to remove two wrong anwsers from the list
			gameStarSpendButton.addClickListener(gameStarSpendClickEvent -> {
				if(gameStarSpendButtonIsAllowed) {
					decreaseGuessCounter();
					
					//resseting the guess layout
					guessLayout.removeAll();
					for(int i = 0;i< guessCounter;i++) {
						guessLayout.add((new Icon("vaadin", "star")));
					}
					
					//removing two incorrect answers
					HashMap<Integer,String> incorrectAnswersMap = new HashMap<>(); 
					for(String incorrectAnswer : questionsList.get(questionCounter).getIncorrectAnswers()) {
						incorrectAnswersMap.put(incorrectAnswer.hashCode(),incorrectAnswer);
					}
					System.out.println(incorrectAnswersMap.toString());
					
					for(int i=0,j=0 ; i<=2 && j<answersList.size();j++) {
						if(incorrectAnswersMap.containsValue(answersList.get(j))){
							answersList.remove(j);
							answers.setItems(answersList);
							i++;
						}
					}
				}
				unAllowSpendButton();
				gameIsFirstAttempt = false;
			});
			
			footerLayout.addClassName("position-bottom");
			
			//timer bar
			progressBar = new ProgressBar();
			progressBar.setValue((double)questionCounter/(double)questionsList.size());
			
			nextButton = new Button("NEXT!");
			nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			nextButton.addClickListener(nextButtonEvent -> {
				if(questionCounter < questionsList.size()-1) {
					questionCounter++;
					this.init();
				}else {
					this.getUI().ifPresent(ui -> ui.navigate(Constants.routeStart));
				}
			});
			
			failureLabel = new Label("You have used up all your guesses!");
			failureButton = new Button("Back to Selection");
			failureButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			failureButton.addClickListener(failureButtonEvent ->{
				this.getUI().ifPresent(ui -> ui.navigate(Constants.routeStart));
			});
			
			nextButton.setVisible(false);
			gameStarSpendLayout.add(gameStarSpendButton);
			footerLayout.add(nextButton,gameStarSpendLayout,progressBar);
			
			add(footerLayout);
			
			//notification
			if(!tutorialIsClosed) {
				Label tutorialMessageQuestionLabelPart1 = new Label(Constants.tutorialQuestionPart1);
				Label tutorialMessageQuestionLabelPart2 = new Label(Constants.tutorialQuestionPart2);
				
				Button tutorialCloseButton = new Button(new Icon(VaadinIcon.CLOSE));
				//TODO try to fit this into one componenet or method
				Notification tutorialMessageQuestion = new Notification(
						tutorialMessageQuestionLabelPart1
						,new HtmlComponent(Tag.BR)
						,new HtmlComponent(Tag.BR)
						,tutorialMessageQuestionLabelPart2
						,new HtmlComponent(Tag.BR)
						,tutorialCloseButton);
				
				
				tutorialMessageQuestion.setPosition(Constants.tutorialPosition);
				tutorialMessageQuestion.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
				tutorialMessageQuestion.setOpened(true);
				tutorialCloseButton.addClickListener(tutorialCloseEvent ->{
					if(tutorialMessageQuestion.isOpened()) {
						tutorialMessageQuestion.close();
					}
				});
				tutorialIsClosed = !tutorialIsClosed;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException();		}

	}

	private void checkForStarSpendButton() {
		if(gameConsecutiveCorrectCounter >= 2) {
			allowSpendButton();
			gameConsecutiveCorrectCounter = 0;
		}
		
	}

	private void raiseConsecutiveCounter() {
		if(gameConsecutiveCorrectCounter < 2) {
			gameConsecutiveCorrectCounter ++;
		}
	}

	private void allowSpendButton() {
		gameStarSpendButtonIsAllowed = true;
		gameStarSpendButton = new Button(new Icon("vaadin","bolt"));
		gameStarSpendButton.addThemeVariants(ButtonVariant.LUMO_LARGE, ButtonVariant.LUMO_PRIMARY);
	}

	private void unAllowSpendButton() {
		gameStarSpendButtonIsAllowed = false;
		gameStarSpendButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
		gameStarSpendButton = new Button(new Icon("vaadin","bolt"));
		
	}
	
	

	private void decreaseGuessCounter() {
		if(guessCounter > 1) {
			guessCounter--;
		}
		
	}

	private void raiseGuessCounter() {
		if(guessCounter < Constants.guessCounterLimit) {
			guessCounter++;
		}
	}

	private void checkChosenValues(String category,String amount, String difficulty) {
		
			if(chosenCategory == null) {
				chosenCategory = VaadinSession.getCurrent().getAttribute(Constants.sessionCategory).toString();
			}
			
			if(chosenAmount == null) {
				chosenAmount = VaadinSession.getCurrent().getAttribute(Constants.sessionAmount).toString();
			}
			
			if(chosenDifficulty == null) {
				chosenDifficulty = VaadinSession.getCurrent().getAttribute(Constants.sessionDifficulty).toString();
			}
	}
}