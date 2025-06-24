package model;

import javax.swing.JPanel;

public class PageModel {
    private String title;
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel footerPanel;
    private String footer;

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for mainPanel
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    // Getter and Setter for headerPanel
    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    public void setHeaderPanel(JPanel headerPanel) {
        this.headerPanel = headerPanel;
    }

    // Getter and Setter for contentPanel
    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    // Getter and Setter for footerPanel
    public JPanel getFooterPanel() {
        return footerPanel;
    }

    public void setFooterPanel(JPanel footerPanel) {
        this.footerPanel = footerPanel;
    }

    // Getter and Setter for footer
    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }
}
