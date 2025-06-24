package view;

import javax.swing.*;
import model.PageModel;
import view.builder.PageDirector;
import view.builder.implementation.HomePageBuilder;
import view.builder.interfaces.PageBuilder;

public class HomePageView {

    private JPanel panel;

    public HomePageView() {
        // use the homepagebuilder to build customize home page
        PageBuilder builder = new HomePageBuilder();
        PageDirector director = new PageDirector(builder);
        PageModel model = director.constructPage("Campus Event Management System");

        this.panel = model.getMainPanel();
    }

    public JPanel getPanel() {
        return panel;
    }
}
