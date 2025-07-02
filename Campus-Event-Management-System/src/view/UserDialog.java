package view;

import model.Staff;
import model.Student;
import model.User;

import javax.swing.*;
import java.awt.*;

public class UserDialog extends JDialog {

    private final JTextField idField = new JTextField(15);
    private final JTextField nameField = new JTextField(15);
    private final JTextField emailField = new JTextField(15);
    private final JTextField phoneField = new JTextField(15);
    private final JComboBox<String> roleBox = new JComboBox<>(new String[] { "STUDENT", "STAFF" });

    private boolean confirmed = false;

    public UserDialog(JFrame parent, String title, User user) {
        super(parent, title, true);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("User ID:"));
        add(idField);
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Phone:"));
        add(phoneField);
        add(new JLabel("Role:"));
        add(roleBox);

        if (user != null) {
            idField.setText(user.getUserId());
            idField.setEnabled(false);
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
            phoneField.setText(user.getPhone());
            roleBox.setSelectedItem(user.getRole());
        }

        JButton confirm = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        confirm.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        cancel.addActionListener(e -> setVisible(false));
        add(confirm);
        add(cancel);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public User getUser() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String role = (String) roleBox.getSelectedItem();

        if ("STUDENT".equals(role)) {
            return new Student.Builder().studentId(id).name(name).email(email).phone(phone).build();
        } else {
            return new Staff.Builder().staffId(id).name(name).email(email).phone(phone).build();
        }
    }
}
