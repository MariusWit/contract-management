package com.example.application.views.designer;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * A Designer generated component for the main-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("main-view")
@JsModule("./src/views/main-view.ts")
@Route(value = "test")
@AnonymousAllowed
public class MainView extends LitTemplate {
    CrmService service;

    // An object to hold the currently selected contact.
    private Contact currentContact;

    // A Vaadin Binder that uses reflection based on the provided Contact type
    // to resolve bean properties. The Binder automatically adds BeanValidator
    // which validates beans using JSR-303 specification.
    private BeanValidationBinder<Contact> binder;

    // The fields are now connected to the template in the @Id("identifier") annotations.
    // The text field uses the filterText id.
    @Id("filterText")
    private TextField filterText;
    // The button uses the addContactButton id.
    @Id("addContactButton")
    private Button addContactButton;
    // The grid uses the grid id.
    // Adds the bean type as a type parameter to the grid.
    @Id("grid")
    private Grid<Contact> grid;
    // The contact form uses the contactForm id.
    @Id("contactForm")
    private ContactForm contactForm;

    /**
     * Creates a new MainView.
     */
    // Spring passes in the service when the view is created using autowiring.
    public MainView(CrmService service) {
        // You can initialise any data required for the connected UI components here.

        this.service = service;

        // Adds and configures columns in the grid.
        grid.addColumn(Contact::getFirstName).setHeader("First name");
        grid.addColumn(Contact::getLastName).setHeader("Last name");
        grid.addColumn(Contact::getEmail).setHeader("Email");

        // Define custom columns for nested objects.
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");

        // Configures column sizing: all columns are sized based on their content.
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        updateList();

        // Puts text field value changes in lazy mode, so that the database is not queried on each keystroke.
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        // Adds a value-change listener to the text field which tells the grid to update items.
        filterText.addValueChangeListener(e -> updateList());

        // Initiate binding configuration.
        configureBinding();
    }

    // updateList() sets the grid items by calling the service with the value from the filter text field.
    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }

    private void configureBinding() {
        // When a row is selected or deselected, populate or clear the form.
        grid.asSingleSelect().addValueChangeListener(event -> {
            Contact contact = event.getValue();
            if (contact != null) {
                populateForm(contact);
            } else {
                clearForm();
            }
        });

        // Instantiate the binder.
        binder = new BeanValidationBinder<>(Contact.class);
        // Bind the member fields found in the ContactForm object.
        // This process is done automatically because the ContactForm
        // object has member fields that are named identically to the fields found in the Contact bean.
        binder.bindInstanceFields(contactForm);

        // Add a click listener to the delete button of the contact form in which the delete operations are performed.
        contactForm.getDelete().addClickListener(e -> {
            if (this.currentContact != null) {
                // Delete the currently selected contact from the backend, and refresh the grid afterwards.
                service.deleteContact(this.currentContact);
                updateList();
                clearForm();
                Notification.show("Contact deleted.");
            }
        });

        // Add a click listener to the close button of the form
        // in which the form is simply cleared without making any modifications to the contact object.
        contactForm.getClose().addClickListener(e -> {

            clearForm();
        });

        // Add a click listener to the save button of the contact form in which the save operations are performed.
        contactForm.getSave().addClickListener(e -> {
            try {
                if (this.currentContact == null) {
                    this.currentContact = new Contact();
                }
                // Writes changes from the bound form fields to the currentContact
                // object if all validators pass. If any field binding validator fails,
                // no values are written and a ValidationException is thrown.
                binder.writeBean(this.currentContact);
                // Save the currentContact object to the backend, after which update the grid and clear the form.
                service.saveContact(this.currentContact);
                updateList();
                clearForm();
                Notification.show("Contact details stored.");
            } catch (ValidationException validationException) {
                // Show a notification a ValidationException is thrown.
                // This can occur, for example, if an attempt is made to save a contact with a blank email field.
                Notification.show("Please enter a valid contact details.");
            }
        });
    }

    // Clears the form.
    void clearForm() {
        populateForm(null);
    }

    // Populates the form with the provided contact.
    void populateForm(Contact contact) {
        this.currentContact = contact;
        binder.readBean(this.currentContact);
    }
}
