package view;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
        // Add all pages to the card panel
        registerPage("HOME", new HomePageView().getMainPanel());
        registerPage("EVENTS", new EventPageView().getMainPanel());
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
