package com.portfolio.TriviaApp.views;
import javax.annotation.PostConstruct;

import com.portfolio.TriviaApp.Constants;
import com.portfolio.TriviaApp.Utils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;




@Route(Constants.routeStart)
//@CssImport("./styles/shared-styles.css")
//@CssImport(Constants.cssImportAnimate)
//@CssImport(Constants.cssImportTriviaApp)
@StyleSheet("frontend://triviaApp.css")
@StyleSheet("frontend://shared-styles.css")
@StyleSheet("frontend://animate.css")
@HtmlImport("shadow-dom-styles.html")
public class StartView extends VerticalLayout {
	private static final long serialVersionUID = 3160388374346480741L;

	private Boolean tutorialIsClosed = false;
	Notification tutorialNotification;
	
	Label triviaLabel;
	
	VerticalLayout inputLayout; 
	HorizontalLayout categoryInputLayout;
	Label categoryLabel;
	ComboBox<String> questionsCategory;
	
	HorizontalLayout questionsAmountInputLayout;
	Label questionsAmountLabel;
	ComboBox<Integer> questionsAmountBox;
	
	HorizontalLayout difficultyInputLayout;
	Label difficultyLabel;
	ComboBox<String> difficultySelectionBox;

	Button goButton;
	
    public StartView() {
		this.setAlignItems(Alignment.CENTER);
    }
    
    @PostConstruct
    public void init() {
    	//tutorial message
        
    	
    	//tutorial
    	if(!tutorialIsClosed) {
        	Label tutorialNotificationLabel = new Label(Constants.tutorialStartMessage);
        	Button tutorialCloseButton = new Button(new Icon(VaadinIcon.CLOSE));
        	tutorialNotification = new Notification(tutorialNotificationLabel,tutorialCloseButton);
        	tutorialNotification.setPosition(Constants.tutorialPosition);
        	tutorialNotification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
        	tutorialNotification.setOpened(true);
        	tutorialCloseButton.addClickListener(tutorialCloseEvent ->{
        		if(tutorialNotification.isOpened()) {
        			tutorialNotification.close();
        		}
        	});
        	tutorialIsClosed = !tutorialIsClosed;
    	}

    	triviaLabel = new Label("Trivia");
    	inputLayout = new VerticalLayout();
    	inputLayout.setAlignItems(Alignment.CENTER);
    	
    	categoryInputLayout = new HorizontalLayout();
    	categoryLabel = new Label("Category:");
    	questionsCategory = new ComboBox<String>();
    	questionsCategory.addClassNames("input","custom-1",".centercb");
		questionsCategory.setAllowCustomValue(false);
        questionsCategory.setItems(Constants.categoriesList);
        questionsCategory.setWidth("350px");
        
        questionsCategory.addValueChangeListener(valueChangeEventCategory -> {
        	if(valueChangeEventCategory == null) {
        		System.out.println("no value selected");
        	}else {
        		System.out.println("Selected Category- " + valueChangeEventCategory.getValue());
    			VaadinSession.getCurrent().setAttribute(Constants.sessionCategory,valueChangeEventCategory.getValue());
        	}
        });
    	categoryInputLayout.add(categoryLabel,questionsCategory);

    	questionsAmountInputLayout = new HorizontalLayout();
    	questionsAmountLabel = new Label("Questions:");
    	questionsAmountBox = new ComboBox<Integer>();
    	questionsAmountBox.setWidth("350px");
        questionsAmountBox.setItems(Utils.toArrayList(Constants.questionsAmountValues));
        questionsAmountBox.addValueChangeListener(valueChangeEventQuestionsAmount -> {
        	if(valueChangeEventQuestionsAmount == null) {
        		System.out.println("no value selected");
        	}else {
        		System.out.println("Selected Questions Amount - " + valueChangeEventQuestionsAmount.getValue());
    			VaadinSession.getCurrent().setAttribute(Constants.sessionAmount,valueChangeEventQuestionsAmount.getValue());
        	}
        });
    	questionsAmountInputLayout.add(questionsAmountLabel,questionsAmountBox);
    	
    	difficultyInputLayout = new HorizontalLayout();
    	difficultyLabel = new Label("Difficulty:");
    	difficultySelectionBox = new ComboBox<String>();
		difficultySelectionBox.setAllowCustomValue(false);
        difficultySelectionBox.setItems(Constants.difficultyList);
        difficultySelectionBox.setWidth("350px");
        difficultySelectionBox.addValueChangeListener(valueChangeEventDifficulty -> {
        	if(valueChangeEventDifficulty == null) {
        		System.out.println("no value selected");
        	}else {
        		System.out.println("Selected Difficulty- " + valueChangeEventDifficulty.getValue());
    			VaadinSession.getCurrent().setAttribute(Constants.sessionDifficulty,valueChangeEventDifficulty.getValue().toLowerCase());
        	}
        });
    	difficultyInputLayout.add(difficultyLabel,difficultySelectionBox);
    	
    	//button to start game
    	goButton = new Button("GO!");
    	goButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    	goButton.addClassName("footer");
    	
//    	goButton.getStyle().set("position", "absolute");
//    	goButton.getStyle().set("bottom", "10px");
//    	goButton.getStyle().set("width", "50%");
    	goButton.addClickListener(goEvent -> {
    		if(tutorialNotification.isOpened()) {
    			tutorialNotification.close();
    		}
			this.getUI().ifPresent(ui -> ui.navigate("question"));
		});
    	
    	//add elements to UI
    	inputLayout.add(categoryInputLayout,questionsAmountInputLayout,difficultyInputLayout);
    	
    	add(triviaLabel);
    	add(inputLayout);
    	add(goButton);
    }
}