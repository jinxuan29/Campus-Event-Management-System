package view;

import java.awt.*;
import javax.swing.*;

public abstract class PageView extends JPanel {

    private String title;

    protected JPanel mainPanel;
    protected JPanel headerPanel;
    protected JPanel contentPanel;
    protected JPanel footerPanel;

    public PageView(String title) {
        this.title = title;
        setMinimumSize(new Dimension(900, 600));
        initializeCommonUI();
    }

    // initialize common ui elements that is shared
    private void initializeCommonUI() {

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize panels
        headerPanel = createHeaderPanel();
        contentPanel = createContentPanel(); // had to be setup by subclass manually since each page is different
        footerPanel = createFooterPanel();

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // header panel creatation
    protected JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel(this.title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(titleLabel);
        return panel;
    }

    // content panel creatation
    protected JPanel createContentPanel() {
        return new JPanel();
    }

    // footer panel creatation
    protected JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel footerLabel = new JLabel("© 2025 OOAD Group Assignment");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(footerLabel);
        return panel;
    }

    // Common methods for all views page
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}