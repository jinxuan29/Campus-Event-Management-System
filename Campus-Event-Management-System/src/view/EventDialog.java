package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Event;

public class EventDialog extends JDialog {
    private JTextField nameField;
    private JTextField dateField;
    private JTextField venueField;
    private JComboBox<String> typeCombo;
    private JTextField capacityField;
    private JTextField feeField;
    private JButton confirmButton;

    public EventDialog(String title, Event existingEvent) {
        setTitle(title);
        setModal(true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        nameField = new JTextField();
        dateField = new JTextField();
        venueField = new JTextField();
        typeCombo = new JComboBox<>(new String[] { "seminar", "workshop", "cultural", "sports" });
        capacityField = new JTextField();
        feeField = new JTextField();

        // If editing existing event, populate fields
        if (existingEvent != null) {
            nameField.setText(existingEvent.getEventName());
            dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(existingEvent.getEventDate()));
            venueField.setText(existingEvent.getEventVenue());
            typeCombo.setSelectedItem(existingEvent.getEventType());
            capacityField.setText(String.valueOf(existingEvent.getEventCapacity()));
            feeField.setText(String.valueOf(existingEvent.getRegistrationFee()));
        }

        formPanel.add(new JLabel("Event Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Venue:"));
        formPanel.add(venueField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeCombo);
        formPanel.add(new JLabel("Capacity:"));
        formPanel.add(capacityField);
        formPanel.add(new JLabel("Fee:"));
        formPanel.add(feeField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setConfirmActionListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    public Event getEventFromFields() throws ParseException, NumberFormatException {
        String name = nameField.getText().trim();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText().trim());
        String venue = venueField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();
        int capacity = Integer.parseInt(capacityField.getText().trim());
        int fee = Integer.parseInt(feeField.getText().trim());

        return new Event.EventBuilder()
                .eventName(name)
                .eventDate(date)
                .eventVenue(venue)
                .eventType(type)
                .eventCapacity(capacity)
                .registrationFee(fee)
                .build();
    }
}