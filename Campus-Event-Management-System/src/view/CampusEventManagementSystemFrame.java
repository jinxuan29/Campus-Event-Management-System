package view;

import controller.EventPageController;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.EventManager;

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
        // Read Database
        EventManager eventManager = new EventManager("Campus-Event-Management-System/database/Event.txt");

        // HOME page
        HomePageView homePageView = new HomePageView();
        registerPage("HOME", homePageView.getMainPanel());

        // EVENTS page
        EventPageView eventPageView = new EventPageView();
        EventPageController eventController = new EventPageController(eventManager, eventPageView);
        registerPage("EVENTS", eventPageView.getMainPanel());

        // REGISTRATION page
        RegistrationPageView registrationView = new RegistrationPageView();
        RegistrationController registrationController = new RegistrationController(eventManager, registrationView, currentUser);
        registerPage("REGISTRATIONS", registrationView.getMainPanel());
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
