package com.portfolio.TriviaApp.views;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.portfolio.TriviaApp.Constants;
import com.portfolio.TriviaApp.Utils;
import com.portfolio.TriviaApp.controller.QuestionController;
import com.portfolio.TriviaApp.model.Question;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(Constants.routeQuestion)
@CssImport("./styles/shared-styles.css")
@CssImport(Constants.cssImportAnimate)
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@CssImport(Constants.cssImportTriviaApp)
@Theme(value = Lumo.class)
public class QuestionView extends VerticalLayout {
	private static final long serialVersionUID = -9173194680925998171L;
	
	private boolean tutorialIsClosed = false;
	
	//values for fetching questions
	private String chosenCategory;
	private String chosenAmount;
	private String chosenDifficulty;
	
	@Autowired
	QuestionController controller;
	
	private int questionCounter = 0;
	private int guessCounter = Constants.guessCounterLimit;
	
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
	
	//buttons
	private Button nextButton;
	private Button failureButton;
	Label failureLabel;
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
		questionLayout = new HorizontalLayout();
		questionLayout.setAlignItems(Alignment.CENTER);
		questionLabel = new Label(Utils.replaceHtml(questionsList.get(questionCounter).getQuestion()));
		questionLabel.addClassName("question");
		questionLayout.add(questionLabel);
		
		add(questionLayout);
		
		//answer TODO randomize the answersList, clean up the logic here
		answerLayout = new HorizontalLayout();
		answers = new ListBox<>();
		answers.getElement().getClassList().add("answers");
		answersList = questionsList.get(questionCounter).getIncorrectAnswers();
		answersList.add(questionsList.get(questionCounter).getCorrectAnswer());
		Collections.shuffle(answersList);
		answers.setItems(answersList);
		correctLabel = new Label();
		correctLabel.setVisible(false);
		correctLabel.addClassNames("correctLabel");
		
		//change listener for answers, this checks if you pick correctly
		answers.addValueChangeListener(valueChangeEvent -> {
			if(guessCounter>0) {
				if(valueChangeEvent.getValue().equalsIgnoreCase(questionsList.get(questionCounter).getCorrectAnswer())) {
					correctLabel.setText("Correct!");
					correctLabel.setVisible(true);
					nextButton.setVisible(true);
				}else {
					correctLabel.setText("Wrong!");
					correctLabel.setVisible(true);
					guessCounter--;
					if(guessCounter <= 0) {
						add(failureLabel);
						add(failureButton);
					}
				}
			}else {
				this.getUI().ifPresent(ui -> ui.navigate(Constants.routeCategory));
			}
		});
		
		answerLayout.add(answers);
		add(answerLayout);
		
		//shows if the correct answer was chosen
		add(correctLabel);
		
		//footer
		footerLayout = new VerticalLayout();
		footerLayout.setAlignItems(Alignment.CENTER);
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
		footerLayout.add(nextButton,progressBar);
		
		add(footerLayout);
		
		//notification
		if(!tutorialIsClosed) {
			Label tutorialMessageQuestionLabel = new Label(Constants.tutorialQuestion);
	    	Button tutorialCloseButton = new Button(new Icon(VaadinIcon.CLOSE));
			Notification tutorialMessageQuestion = new Notification(tutorialMessageQuestionLabel,tutorialCloseButton);
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