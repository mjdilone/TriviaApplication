package com.portfolio.TriviaApp.views;

import javax.annotation.PostConstruct;

import com.portfolio.TriviaApp.Constants;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(Constants.routeCategory)
//@CssImport("./styles/shared-styles.css")
//@CssImport(Constants.cssImportAnimate)
//@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@StyleSheet("frontend://triviaApp.css")
@StyleSheet("frontend://shared-styles.css")
@StyleSheet("frontend://animate.css")
@HtmlImport("shadow-dom-styles.html")
@Theme(value = Lumo.class)
public class CategoryView extends VerticalLayout{
	
	private static final long serialVersionUID = 1L;
	
	public CategoryView() {
		addClassNames("centered-content","category");
		this.addClassName("offCenterRight");
	}
	
	@PostConstruct
	public void init() {
		HorizontalLayout categoryLayout = new HorizontalLayout();
		Label categoryH1 = new Label("Choose a Category");
        categoryH1.addClassNames("animated","categoryHeader");
        categoryLayout.add(categoryH1);
       
        ComboBox<String> questionsCategory = new ComboBox<String>();
		questionsCategory.setAllowCustomValue(false);
        questionsCategory.setItems(Constants.categoriesList);
        questionsCategory.setWidth("350px");
        questionsCategory.addValueChangeListener(valueChangeEvent -> {
        	if(valueChangeEvent == null) {
        		System.out.println("no value selected");
        	}else {
        		System.out.println("Selected Category- " + valueChangeEvent.getValue());
    			VaadinSession.getCurrent().setAttribute("questionsCategory",valueChangeEvent.getValue());
        	}
        });
        
        Button goButton = new Button("GO!");
		goButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		goButton.addClickListener(goEvent -> {
			this.getUI().ifPresent(ui -> ui.navigate("question"));
		});
		
		add(categoryLayout,questionsCategory);
		add(goButton);
	}
}