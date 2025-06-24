package view.builder;

import java.awt.*;
import javax.swing.*;
import model.PageModel;
import view.builder.interfaces.PageBuilder;

// Original By Defaul Page Design
public class ImplPageBuilder extends JPanel implements PageBuilder {

    protected PageModel pageModel;

    public ImplPageBuilder() {
        this.pageModel = new PageModel();
    }

    @Override
    public void setTitle(String title) {
        pageModel.setTitle(title);
    }

    @Override
    public void buildHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel(pageModel.getTitle());
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.add(label);
        pageModel.setHeaderPanel(header);
    }

    @Override
    public void buildContent() {
        JPanel content = new JPanel();
        pageModel.setContentPanel(content);
    }

    @Override
    public void buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("© 2025 OOAD Group Assignment");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.add(label);
        pageModel.setFooterPanel(footer);
        pageModel.setFooter("© 2025 OOAD Group Assignment");
    }

    @Override
    public PageModel getPageModel() {
        // Assemble all panels into mainPanel
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        main.add(pageModel.getHeaderPanel(), BorderLayout.NORTH);
        main.add(pageModel.getContentPanel(), BorderLayout.CENTER);
        main.add(pageModel.getFooterPanel(), BorderLayout.SOUTH);

        pageModel.setMainPanel(main);
        return this.pageModel;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

}
