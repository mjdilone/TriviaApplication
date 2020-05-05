package com.portfolio.TriviaApp.views;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;

@Tag
(Tag.DIV)
public class RouteNotFoundError extends VerticalLayout implements HasErrorParameter<NotFoundException> {

	private static final long serialVersionUID = 5697259760318762372L;

	@Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) 
	{
        
		setAlignItems(Alignment.CENTER);
        Label label = new Label("The API for this service is currently down please try using this application at a later time");
        add(label);
        label.setHeight("300px");
        return HttpServletResponse.SC_NOT_FOUND;
    }

	
}