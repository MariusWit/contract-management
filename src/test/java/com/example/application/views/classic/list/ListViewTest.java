package com.example.application.views.classic.list;

import com.example.application.data.entity.Contact;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// The @RunWith(SpringRunner.class) and @SpringBootTest annotations
// make sure that the Spring Boot application is initialized before
// the tests are run and allow you to use @Autowire in the test.
@RunWith(SpringRunner.class) 
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    // The test verifies that the form logic works by:
    // asserting that the form is initially hidden;
    // selecting the first item in the grid and verifying that:
    //  - the form is visible;
    //  - the form is bound to the correct Contact by ensuring that the right name is visible in the field.
    @Test
    public void formShownWhenContactSelected() {
        Grid<Contact> grid = listView.grid;
        Contact firstContact = getFirstItem(grid);

        ContactForm form = listView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstContact);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstContact.getFirstName(), form.firstName.getValue());
    }

    private Contact getFirstItem(Grid<Contact> grid) {
        return( (ListDataProvider<Contact>) grid.getDataProvider()).getItems().iterator().next();
    }
}