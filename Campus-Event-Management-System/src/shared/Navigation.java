package shared;

import javax.swing.*;
import view.CampusEventManagementSystemFrame;

public class Navigation {
    private static CampusEventManagementSystemFrame mainFrame;

    // Initialize with the main application frame
    public static void initialize(CampusEventManagementSystemFrame frame) {
        mainFrame = frame;
    }

    public static void navigateTo(String pageName) {
        if (mainFrame == null) {
            throw new IllegalStateException("Navigation not initialized with MainApplicationFrame");
        }

        try {
            System.out.println(pageName);
            mainFrame.showPage(pageName);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Page not found: " + pageName,
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Optional: Add navigation history tracking
    public static void navigateBack() {
        // Implement if you want back navigation
    }
}