package com.example.application.views.designer;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

/**
 * A Designer generated component for the contact-form template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("contact-form")
@JsModule("./src/views/contact-form.ts")
public class ContactForm extends LitTemplate {
    CrmService service;

    @Id("firstName")
    private TextField firstName;
    @Id("lastName")
    private TextField lastName;
    @Id("email")
    private EmailField email;
    @Id("status")
    private ComboBox<Status> status;
    @Id("company")
    private ComboBox<Company> company;
    @Id("save")
    private Button save;
    @Id("delete")
    private Button delete;
    @Id("close")
    private Button close;

    // BeanValidationBinder is a Binder that is aware of bean validation annotations.
    // By passing it in the Contact.class, you define the type of object you are binding to.
    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    /**
     * Creates a new ContactForm.
     */
    // dds CrmService as a parameter. The Spring framework will inject it here.
    public ContactForm(CrmService service) {
        // You can initialise any data required for the connected UI components here.
        this.service = service;

        // Find and fetch all companies and statuses.
        List<Company> companies = service.findAllCompanies();
        List<Status> statuses = service.findAllStatuses();

        // bindInstanceFields() matches fields in Contact and ContactForm based on their names.
        binder.bindInstanceFields(this);

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);
    }

    // Add getter for the save button.
    public Button getSave() {
        return save;
    }

    // Add getter for the delete button.
    public Button getDelete() {
        return delete;
    }

    // Add getter for the close button.
    public Button getClose() {
        return close;
    }

}
