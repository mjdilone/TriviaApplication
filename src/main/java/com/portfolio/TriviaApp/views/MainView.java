package com.portfolio.TriviaApp.views;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.portfolio.TriviaApp.service.GreetService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
///triviatesting/frontend
@Route("testing")
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
//@CssImport("./styles/shared-styles.css")
//@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@StyleSheet("frontend://triviaApp.css")
@StyleSheet("frontend://shared-styles.css")
@StyleSheet("frontend://animate.css")
@HtmlImport("shadow-dom-styles.html")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
	
	
	@Autowired 
	GreetService service;
	
    public MainView() {
    	this.setAlignItems(Alignment.CENTER);
    }
    
    @PostConstruct
    protected void init() {
        // Use TextField for standard text input
        TextField textField = new TextField("Your name");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello",
                e -> Notification.show(service.greet(textField.getValue())));

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(textField, button);
    }
}
