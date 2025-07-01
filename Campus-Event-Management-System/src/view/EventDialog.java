package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import model.CulturalEvent;
import model.Event;
import model.Seminar;
import model.SportEvent;
import model.Workshop;

public class EventDialog extends JDialog {
    private JTextField nameField;
    private JTextField yearField;
    private JTextField monthField;
    private JTextField dayField;
    private JTextField venueField;
    private JComboBox<String> typeCombo;
    private JTextField currentCapacityField;
    private JTextField capacityField;
    private JTextField feeField;
    private JButton confirmButton;

    public EventDialog(String title, Event existingEvent) {
        setTitle(title);
        setModal(true);
        setLayout(new BorderLayout());
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));

        nameField = new JTextField();
        yearField = new JTextField();
        yearField.setColumns(4);
        monthField = new JTextField();
        monthField.setColumns(2);
        dayField = new JTextField();
        dayField.setColumns(2);
        venueField = new JTextField();
        typeCombo = new JComboBox<>(new String[] { "seminar", "workshop", "cultural", "sports" });
        currentCapacityField = new JTextField();
        currentCapacityField.setEditable(false);
        currentCapacityField.setEnabled(false);
        capacityField = new JTextField();
        feeField = new JTextField();

        if (existingEvent != null) {
            nameField.setText(existingEvent.getEventName());
            Date d = existingEvent.getEventDate();
            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
            SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
            yearField.setText(sdfYear.format(d));
            monthField.setText(sdfMonth.format(d));
            dayField.setText(sdfDay.format(d));
            venueField.setText(existingEvent.getEventVenue());
            typeCombo.setSelectedItem(existingEvent.getEventType());
            currentCapacityField.setText(String.valueOf(existingEvent.getCurrentCapacity()));
            capacityField.setText(String.valueOf(existingEvent.getEventCapacity()));
            feeField.setText(String.valueOf(existingEvent.getRegistrationFee()));
        } else {
            currentCapacityField.setText("0");
        }

        formPanel.add(new JLabel("Event Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Date (YYYY - MM - DD):"));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        datePanel.add(yearField);
        datePanel.add(new JLabel(" - "));
        datePanel.add(monthField);
        datePanel.add(new JLabel(" - "));
        datePanel.add(dayField);
        formPanel.add(datePanel);

        formPanel.add(new JLabel("Venue:"));
        formPanel.add(venueField);

        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeCombo);

        formPanel.add(new JLabel("Current Registrations (Read Only):"));
        formPanel.add(currentCapacityField);

        formPanel.add(new JLabel("Capacity:"));
        formPanel.add(capacityField);

        formPanel.add(new JLabel("Fee:"));
        formPanel.add(feeField);

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
        if (nameField.getText().trim().isEmpty() ||
                yearField.getText().trim().isEmpty() ||
                monthField.getText().trim().isEmpty() ||
                dayField.getText().trim().isEmpty() ||
                venueField.getText().trim().isEmpty() ||
                capacityField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        String name = nameField.getText().trim();
        String yearStr = yearField.getText().trim();
        String monthStr = monthField.getText().trim();
        String dayStr = dayField.getText().trim();

        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        int day = Integer.parseInt(dayStr);

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day must be between 1 and 31");
        }

        String dateStr = String.format("%04d-%02d-%02d", year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date date = sdf.parse(dateStr);
        Date today = sdf.parse(sdf.format(new Date()));

        if (date.before(today)) {
            throw new IllegalArgumentException("Date cannot be before today");
        }

        String venue = venueField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();
        int capacity = Integer.parseInt(capacityField.getText().trim());

        int currentCapacity = 0;
        String currentCapText = currentCapacityField.getText().trim();
        if (!currentCapText.isEmpty()) {
            currentCapacity = Integer.parseInt(currentCapText);
        }
        if (currentCapacity < 0 || currentCapacity > capacity) {
            throw new IllegalArgumentException("Current capacity must be between 0 and total capacity");
        }

        String feeText = feeField.getText();
        int fee = 0;
        if (feeText != null && !feeText.trim().isEmpty()) {
            fee = Integer.parseInt(feeText.trim());
        }

        switch (type) {
            case "seminar":
                return new Seminar.SeminarBuilder()
                        .eventName(name)
                        .eventDate(date)
                        .eventVenue(venue)
                        .currentCapacity(currentCapacity)
                        .eventCapacity(capacity)
                        .registrationFee(fee)
                        .build();
            case "workshop":
                return new Workshop.WorkshopBuilder()
                        .eventName(name)
                        .eventDate(date)
                        .eventVenue(venue)
                        .currentCapacity(currentCapacity)
                        .eventCapacity(capacity)
                        .registrationFee(fee)
                        .build();
            case "cultural":
                return new CulturalEvent.CulturalEventBuilder()
                        .eventName(name)
                        .eventDate(date)
                        .eventVenue(venue)
                        .currentCapacity(currentCapacity)
                        .eventCapacity(capacity)
                        .registrationFee(fee)
                        .build();
            case "sports":
                return new SportEvent.SportEventBuilder()
                        .eventName(name)
                        .eventDate(date)
                        .eventVenue(venue)
                        .currentCapacity(currentCapacity)
                        .eventCapacity(capacity)
                        .registrationFee(fee)
                        .build();
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
}
