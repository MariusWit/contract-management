package com.example.application.views.classic;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

// Map the view to the "login" path.
// LoginView should take up the whole browser window, so do not use MainLayout as the parent.
@Route("login") 
@PageTitle("Login | Vaadin CRM")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	// Instantiate a LoginForm component to capture username and password.
	private final LoginForm login = new LoginForm(); 

	public LoginView(){
		addClassName("login-view");
		// Make LoginView full size and center its content both horizontally and vertically,
		// by calling setAlignItems(`Alignment.CENTER)` and setJustifyContentMode(`JustifyContentMode.CENTER)`.
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		// Set the LoginForm action to "login" to post the login form to Spring Security.
		login.setAction("login"); 

		add(new H1("Vaadin CRM"), login);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// Read query parameters and show an error if a login attempt fails.
		if(beforeEnterEvent.getLocation()  
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
}