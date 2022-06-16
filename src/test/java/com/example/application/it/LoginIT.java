package com.example.application.it;

import com.example.application.it.elements.LoginViewElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import org.junit.Assert;
import org.junit.Test;

public class LoginIT extends AbstractTest {
    public LoginIT() {
        super("");
    }

    // Tests successful login
    @Test
    public void loginAsValidUserSucceeds() {
        // updated for TestBench
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertTrue(loginView.login("user", "userpass"));

        /*
        // Find the LoginForm used on the page
        LoginFormElement form = $(LoginFormElement.class).first();
        // Enter the credentials and log in
        form.getUsernameField().setValue("user");
        form.getPasswordField().setValue("userpass");
        form.getSubmitButton().click();
        // Ensure the login form is no longer visible
        Assert.assertFalse($(LoginFormElement.class).exists());
        */
    }

    // Tests unsuccessful login
    @Test
    public void loginAsInvalidUserFails() {
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertFalse(loginView.login("user", "invalid"));
    }
}