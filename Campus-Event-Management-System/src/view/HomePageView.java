package view;

import java.awt.*;
import javax.swing.*;
import shared.Navigation;

public class HomePageView extends PageView {
    public HomePageView() {
        super("Campus Event Management System"); // set up the title
    }

    @Override
    protected JPanel createContentPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        // Add custom buttons
        String[] categories = { "EVENTS", "REGISTRATIONS", "REPORTS", "SETTINGS" };
        Color[] colors = {
                new Color(76, 175, 80),
                new Color(33, 150, 243),
                new Color(255, 152, 0),
                new Color(156, 39, 176)
        };

        for (int i = 0; i < categories.length; i++) {
            String category = categories[i];
            JButton btn = new JButton(category);
            btn.setBackground(colors[i]);
            btn.setForeground(Color.WHITE);
            btn.addActionListener(e -> {
                Navigation.navigateTo(category);
            });
            panel.add(btn);
        }

        return panel;
    }

}