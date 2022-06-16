package com.example.application.views.classic.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.example.application.views.classic.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;

// ListView still matches the empty path, but now uses MainLayout as its parent.
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
@PermitAll
// The view extends VerticalLayout, which places all child components vertically.
public class ListView extends VerticalLayout {
    // The Grid component is typed with Contact.
    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterText = new TextField();

    // Creates a field for the form, so you have access to it from other methods later on.
    ContactForm form;

    CrmService service;

    // Autowire CrmService through the constructor. Save it in a field, so you can access it from other methods.
    public ListView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        // The grid configuration is extracted to a separate method to keep the constructor easier to read.
        configureGrid();
        // Change the add() method to call getContent(). The method returns a HorizontalLayout that wraps the form
        // and the grid, showing them next to each other.
        configureForm();

        // Add the toolbar and grid to the VerticalLayout.
        add(getToolbar(), getContent());
        // Call updateList() once you have constructed the view.
        updateList();

        // The closeEditor() call at the end of the constructor:
        //  - sets the form contact to null, clearing out old values;
        //  - hides the form;
        //  - removes the "editing" CSS class from the view.
        closeEditor();


    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        // Use setFlexGrow() to define that the Grid should get two times the space of the form.
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    // Initialize the form with empty company and status lists for now; you add these in the next chapter.
    private void configureForm() {

        // form = new ContactForm(Collections.emptyList(), Collections.emptyList());
        // Use the service to fetch companies and statuses.
        form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
        form.setWidth("25em");

        // The save event listener calls saveContact(). It:
        //  - Uses contactService to save the contact in the event to the database.
        //  - Updates the list.
        //  - Closes the editor.
        form.addListener(ContactForm.SaveEvent.class, this::saveContact);

        // The delete event listener calls deleteContact(). It:
        //  - uses contactService to delete the contact from the database;
        //  - updates the list;
        //  - closes the editor.
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);

        // The close event listener closes the editor.
        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        // Define which properties of Contact the grid should show.
        grid.setColumns("firstName", "lastName", "email");
        // Define custom columns for nested objects.
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        // Configure the columns to automatically adjust their size to fit their contents.
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        // addValueChangeListener() adds a listener to the grid.
        // The Grid component supports multi- and single-selection modes.
        // You only need to select a single Contact, so you can use the asSingleSelect() method.
        // The getValue() method returns the Contact in the selected row, or null if there is no selection.
        grid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        // Configure the search field to fire value-change events only when the user stops typing.
        // This way you avoid unnecessary database calls.
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        // Call updateList() any time the filter changes.
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        // Call addContact() when the user clicks on the "Add contact" button.
        addContactButton.addClickListener(click -> addContact());

        // The toolbar uses a HorizontalLayout to place the TextField and Button next to each other.
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    // editContact() sets the selected contact in the ContactForm and hides or shows the form,
    // depending on the selection. It also sets the "editing" CSS class name when editing.
    public void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    // addContact() clears the grid selection and creates a new Contact.
    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    // updateList() sets the grid items by calling the service with the value from the filter text field.
    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }
}



/*
package com.example.application.views.classic.list;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("list")
@Route(value = "")
public class ListView extends VerticalLayout {

    public ListView() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H2("This place intentionally left empty"));
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
 */
