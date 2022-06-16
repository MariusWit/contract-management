package com.example.application.views.classic;

import com.example.application.security.SecurityService;
import com.example.application.views.classic.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

// AppLayout is a Vaadin layout with a header and a responsive drawer.
public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    // Autowire the SecurityService and save it in a field.
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassNames("text-l", "m-m");

        // Create a logout button that calls the logout() method in the service.
        Button logout = new Button("Log out", e -> securityService.logout());

        // DrawerToggle is a menu button that toggles the visibility of the sidebar.
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        // Centers the components in the header along the vertical axis.
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        // Call header.expand(logo) to make the logo take up all the extra space in the layout.
        // This pushes the logout button to the far right.
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        // Adds the header layout to the application layout’s nav bar, the section at the top of the screen.
        addToNavbar(header); 

    }

    private void createDrawer() {
        // Creates a RouterLink with the text "List" and ListView.class as the destination view.
        RouterLink listLink = new RouterLink("List", ListView.class);
        // Sets setHighlightCondition(HighlightConditions.sameLocation()) to avoid highlighting
        // the link for partial route matches. (Technically, every route starts with an empty route,
        // so without this, it would always show up as active, even though the user is not on the view.)
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        // Wraps the link in a VerticalLayout and adds it to the `AppLayout’s drawer.
        addToDrawer(new VerticalLayout( 
            listLink,
            new RouterLink("Dashboard", DashboardView.class)
        ));
    }
}