package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Event;
import model.Service;

public class RegistrationPageView extends PageView {
    private JComboBox<Event> eventCombo;
    private JList<Service> servicesList;
    private JButton registerButton;
    private JTextArea billArea;

    public RegistrationPageView() {
        super("Event Registration");
    }

    @Override
    protected JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Event selection
        eventCombo = new JComboBox<>();
        panel.add(new JLabel("Select Event:"), BorderLayout.NORTH);
        panel.add(eventCombo, BorderLayout.NORTH);

        // Services selection
        servicesList = new JList<>();
        servicesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        panel.add(new JScrollPane(servicesList), BorderLayout.CENTER);

        // Register button
        registerButton = new JButton("Register");
        panel.add(registerButton, BorderLayout.SOUTH);

        // Bill display
        billArea = new JTextArea(10, 30);
        billArea.setEditable(false);
        panel.add(new JScrollPane(billArea), BorderLayout.EAST);

        return panel;
    }

    public void displayEvents(List<Event> events) {
        eventCombo.removeAllItems();
        events.forEach(eventCombo::addItem);
    }

    public void displayServices(List<Service> services) {
        servicesList.setListData(services.toArray(new Service[0]));
    }

    public void displayBill(Bill bill) {
        billArea.setText(bill.getBreakdown());
    }

    public Event getSelectedEvent() {
        return (Event) eventCombo.getSelectedItem();
    }

    public List<Service> getSelectedServices() {
        return servicesList.getSelectedValuesList();
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}
