package controller;

import javax.swing.*;
import model.User;
import model.manager.UserManager;
import view.UserDialog;
import view.UserPageView;

public class UserPageController {

    private final UserPageView view;
    private final UserManager userManager;

    public UserPageController(UserPageView view) {
        this.view = view;
        this.userManager = UserManager.getInstance();

        userManager.registerObserver(view);
        view.updateUser(userManager.getUsers());

        setupListeners();
    }

    private void setupListeners() {
        view.getCreateButton().addActionListener(e -> createUser());
        view.getUpdateButton().addActionListener(e -> updateUser());
        view.getDeleteButton().addActionListener(e -> deleteUser());
    }

    private void createUser() {
        UserDialog dialog = new UserDialog(null, "Create User", null);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            User newUser = dialog.getUser();
            if (userManager.getUserFromId(newUser.getUserId()) != null) {
                JOptionPane.showMessageDialog(view, "User ID already exists.");
                return;
            }
            userManager.addUser(newUser);
            JOptionPane.showMessageDialog(view, "User created.");
        }
    }

    private void updateUser() {
        String userId = view.getSelectedUserId();
        if (userId == null) {
            JOptionPane.showMessageDialog(view, "Please select a user to update.");
            return;
        }

        User existingUser = userManager.getUserFromId(userId);
        UserDialog dialog = new UserDialog(null, "Update User", existingUser);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            User updatedUser = dialog.getUser();
            userManager.updateUser(userId, updatedUser);
            JOptionPane.showMessageDialog(view, "User updated.");
        }
    }

    private void deleteUser() {
        String userId = view.getSelectedUserId();
        if (userId == null) {
            JOptionPane.showMessageDialog(view, "Please select a user to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this user?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userManager.removeUser(userId);
            JOptionPane.showMessageDialog(view, "User deleted.");
        }
    }
}
