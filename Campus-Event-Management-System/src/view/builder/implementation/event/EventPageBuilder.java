package view.builder.implementation.event;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import shared.Navigation;
import view.builder.ImplPageBuilder;

public class EventPageBuilder extends ImplPageBuilder {
    @Override
    public void buildContent() {
        JPanel content = new JPanel(new BorderLayout(20, 20));

        // Top panel: Event table
        // TODO Observer for data changes
        String[] columns = { "Name", "Date", "Venue", "Type", "Capacity", "Fee" };
        Object[][] data = {
                { "AI Workshop", "2025-07-10", "Auditorium", "Workshop", 100, "RM30" },
                { "Cultural Fest", "2025-08-15", "Main Hall", "Cultural Event", 300, "Free" }
        };

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        content.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel: Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton createButton = new JButton("Create Event");
        JButton updateButton = new JButton("Update Event");
        JButton cancelButton = new JButton("Cancel Event");
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(e -> Navigation.navigateTo("HOME"));

        buttonPanel.add(homeButton);
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        content.add(buttonPanel, BorderLayout.SOUTH);

        pageModel.setContentPanel(content);
    }
}
