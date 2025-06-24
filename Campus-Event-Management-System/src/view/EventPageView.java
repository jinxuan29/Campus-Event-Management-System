package view;

import javax.swing.JPanel;
import model.PageModel;
import view.builder.PageDirector;
import view.builder.implementation.event.EventPageBuilder;
import view.builder.interfaces.PageBuilder;

public class EventPageView {

    private JPanel panel;

    public EventPageView() {
        // use eventpagebuilder for building this page
        PageBuilder builder = new EventPageBuilder();
        PageDirector director = new PageDirector(builder);
        PageModel model = director.constructPage("Campus Event Management System");
        this.panel = model.getMainPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

}
