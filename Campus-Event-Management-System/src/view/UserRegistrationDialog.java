package view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.Student;
import model.User;

public class UserRegistrationDialog extends JDialog {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Step 1: Existing or new user
    private JPanel userTypePanel;
    private JRadioButton existingUserRadio;
    private JRadioButton newUserRadio;

    // Step 2: Form fields
    private JPanel formPanel;
    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JButton confirmButton;
    private JButton backButton;
    private JRadioButton studentRadio;
    private JRadioButton staffRadio;

    public UserRegistrationDialog(String title) {
        setTitle(title);
        setModal(true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create card layout for multi-step process
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Step 1: Existing or new user
        userTypePanel = createUserTypePanel();
        mainPanel.add(userTypePanel, "userType");

        // Step 2: Form (will be populated based on choice)
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.add(formPanel, "form");

        // Navigation buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "userType");
            backButton.setVisible(false);
            confirmButton.setVisible(false);
        });
        backButton.setVisible(false);

        confirmButton = new JButton("Confirm");
        confirmButton.setVisible(false);

        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createUserTypePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel questionLabel = new JLabel("Is this an existing user?");
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup group = new ButtonGroup();
        existingUserRadio = new JRadioButton("Yes, existing user");
        newUserRadio = new JRadioButton("No, new user");
        existingUserRadio.setAlignmentX(Component.CENTER_ALIGNMENT);
        newUserRadio.setAlignmentX(Component.CENTER_ALIGNMENT);
        group.add(existingUserRadio);
        group.add(newUserRadio);

        panel.add(questionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(existingUserRadio);
        panel.add(newUserRadio);

        // Action listeners for radio buttons
        ActionListener radioListener = e -> {
            if (existingUserRadio.isSelected() || newUserRadio.isSelected()) {
                showAppropriateForm();
                cardLayout.show(mainPanel, "form");
                confirmButton.setVisible(true);
                backButton.setVisible(true);
            }
        };

        existingUserRadio.addActionListener(radioListener);
        newUserRadio.addActionListener(radioListener);

        return panel;
    }

    private void showAppropriateForm() {
        formPanel.removeAll();

        // Common ID field
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.add(new JLabel("User ID:"));
        idField = new JTextField(20);
        idPanel.add(idField);
        formPanel.add(idPanel);

        if (newUserRadio.isSelected()) {
            // Additional fields for new users
            JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            namePanel.add(new JLabel("Name:"));
            nameField = new JTextField(20);
            namePanel.add(nameField);
            formPanel.add(namePanel);

            JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            emailPanel.add(new JLabel("Email:"));
            emailField = new JTextField(20);
            emailPanel.add(emailField);
            formPanel.add(emailPanel);

            JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            phonePanel.add(new JLabel("Phone:"));
            phoneField = new JTextField(20);
            phonePanel.add(phoneField);
            formPanel.add(phonePanel);

            // Role selection for new users
            JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            rolePanel.add(new JLabel("Role:"));
            this.studentRadio = new JRadioButton("Student");
            this.staffRadio = new JRadioButton("Staff");
            ButtonGroup roleGroup = new ButtonGroup();
            roleGroup.add(studentRadio);
            roleGroup.add(staffRadio);
            rolePanel.add(studentRadio);
            rolePanel.add(staffRadio);
            formPanel.add(rolePanel);
        }

        formPanel.revalidate();
        formPanel.repaint();
    }

    public void setConfirmActionListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    public User getUserFromFields() throws IllegalArgumentException {
        String id = idField.getText().trim();

        if (id.isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }

        if (existingUserRadio.isSelected()) {
            return null; // Caller should look up user by ID
        } else {
            // Validate new user fields
            if (nameField.getText().trim().isEmpty() ||
                    emailField.getText().trim().isEmpty() ||
                    phoneField.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled");
            }

            String name = nameField.getText().trim().replace(",", "");
            String email = emailField.getText().trim().replace(",", "");
            String phone = phoneField.getText().trim().replace(",", "");

            if (!email.contains("@") || !email.contains(".")) {
                throw new IllegalArgumentException("Invalid email format");
            }

            if (!phone.matches("\\d+")) {
                throw new IllegalArgumentException("Phone number must contain only digits");
            }

            if (studentRadio.isSelected()) {
                return new Student.Builder()
                        .studentId(id)
                        .name(name)
                        .email(email)
                        .phone(phone)
                        .build();
            } else if (staffRadio.isSelected()) {
                return new model.Staff.Builder()
                        .staffId(id)
                        .name(name)
                        .email(email)
                        .phone(phone)
                        .build();
            } else {
                throw new IllegalArgumentException("Please select a role (Student or Staff)");
            }

        }
    }

    public boolean isExistingUser() {
        return existingUserRadio.isSelected();
    }

    public String getUserId() {
        return idField.getText().trim();
    }
}