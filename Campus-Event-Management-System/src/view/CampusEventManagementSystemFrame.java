package view;

import controller.EventPageController;
import controller.RegistrationPageController;
import controller.ReportsPageController;
import controller.UserPageController;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.manager.EventManager;
import model.manager.RegistrationManager;
import model.manager.UserManager;

public class CampusEventManagementSystemFrame extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public CampusEventManagementSystemFrame() {
        setTitle("Campus Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Initialize card layout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        initializeUI();
        add(cardPanel);
    }

    private void initializeUI() {
        // HOME page
        HomePageView homePageView = new HomePageView();
        registerPage("HOME", homePageView.getMainPanel());

        // EVENTS page
        EventPageView eventPageView = new EventPageView();
        EventPageController eventController = new EventPageController(eventPageView);
        registerPage("EVENTS", eventPageView.getMainPanel());

        // REGISTRATION page
        RegistrationPageView registrationView = new RegistrationPageView();

        RegistrationPageController registrationPageController = new controller.RegistrationPageController(
                registrationView);

        registerPage("REGISTRATIONS", registrationView.getMainPanel());

        // REPORT page
        ReportsPageView reportView = new ReportsPageView();
        ReportsPageController reportsPageController = new ReportsPageController(reportView);
        registerPage("REPORTS", reportView.getMainPanel());

        // USER page
        UserPageView userView = new UserPageView();
        UserPageController userPageController = new UserPageController(userView);
        registerPage("USERS", userView.getMainPanel());

        // Observers
        EventManager eventManager = EventManager.getInstance();
        eventManager.registerObserver(eventPageView);
        eventManager.registerObserver(registrationView);
        eventManager.registerObserver(reportView);

        RegistrationManager registrationManager = RegistrationManager.getInstance();
        registrationManager.registerObserver(eventManager);
        registrationManager.registerObserver(reportView);

        UserManager userManager = UserManager.getInstance();
        userManager.registerObserver(userView);
        userManager.registerObserver(reportView);

        eventManager.eventsUpdated();
        registrationManager.registrationUpdated();
        userManager.usersUpdated();
    }

    public void registerPage(String name, JPanel panel) {
        panel.setName(name);
        cardPanel.add(panel, name);
        System.out.println("Registered page: " + name);
    }

    public void showPage(String pageName) {
        if (!pageExists(pageName)) {
            throw new IllegalArgumentException("Page not found: " + pageName);
        }
        cardLayout.show(cardPanel, pageName);
    }

    private boolean pageExists(String pageName) {
        for (Component comp : cardPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(pageName)) {
                return true;
            }
        }
        return false;
    }

}
