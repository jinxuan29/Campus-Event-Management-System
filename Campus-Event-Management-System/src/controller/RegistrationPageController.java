package controller;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import model.*;
import model.manager.EventManager;
import model.manager.RegistrationManager;
import model.manager.ServiceManager;
import model.manager.UserManager;
import model.manager.VoucherManager;
import view.RegistrationPageView;
import view.UserRegistrationDialog;
import view.VoucherServiceDialog;

public class RegistrationPageController {
    private final RegistrationPageView eventView;
    private final EventManager eventManager;
    private final UserManager userManager;
    private Event selectedEvent;

    public RegistrationPageController(RegistrationPageView eventView) {
        this.eventManager = EventManager.getInstance();
        this.eventView = eventView;
        this.userManager = UserManager.getInstance();

        // Register view as observer
        // this.eventManager.registerObserver(eventView);

        setupEventListeners();
        loadEvents();
    }

    private void setupEventListeners() {
        eventView.getRegisterButton().addActionListener(e -> handleRegistrationButtonClick());
    }

    private void handleRegistrationButtonClick() {
        selectedEvent = eventView.getSelectedEvent();
        if (selectedEvent == null) {
            showErrorMessage("Please select an event first.");
            return;
        }

        // Check remaining capacity by counting registrations
        RegistrationManager regManager = RegistrationManager.getInstance();
        long registrationsCount = regManager.getRegistrations().stream()
                .filter(reg -> reg.getEventId().equals(selectedEvent.getEventId()))
                .count();

        int remainingCapacity = selectedEvent.getEventCapacity() - (int) registrationsCount;
        if (remainingCapacity <= 0) {
            showErrorMessage("This event is already full.");
            return;
        }

        UserRegistrationDialog dialog = new UserRegistrationDialog(
                "Register for " + selectedEvent.getEventName());

        dialog.setConfirmActionListener(createConfirmActionListener(dialog));
        dialog.setVisible(true);
    }

    private ActionListener createConfirmActionListener(UserRegistrationDialog dialog) {
        return e -> {
            try {
                if (dialog.isExistingUser()) {
                    handleExistingUserRegistration(dialog);
                } else {
                    handleNewUserRegistration(dialog);
                }
                dialog.dispose();
            } catch (Exception ex) {
                showErrorMessage(dialog, ex.getMessage());
            }
        };
    }

    private void handleExistingUserRegistration(UserRegistrationDialog dialog) {
        String userId = dialog.getUserId();
        User user = userManager.getUserFromId(userId);

        if (user == null) {
            throw new IllegalArgumentException("User ID not found. Please register as new user.");
        }

        // check if user registered before
        boolean alreadyRegistered = RegistrationManager.getInstance()
                .isUserRegisteredForEvent(userId, selectedEvent.getEventId());

        if (alreadyRegistered) {
            JOptionPane.showMessageDialog(
                    eventView,
                    "You have already registered for this event.",
                    "Duplicate Registration",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                eventView,
                String.format("Is this you?\n\nName: %s\nEmail: %s\nRole: %s",
                        user.getName(), user.getEmail(), user.getRole()),
                "Confirm Identity",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            showVoucherAndServiceSelection(user);
        } else {
            JOptionPane.showMessageDialog(
                    eventView,
                    "Please try again or register a new user.",
                    "User Not Confirmed",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleNewUserRegistration(UserRegistrationDialog dialog) {
        User newUser = dialog.getUserFromFields();

        if (userManager.getUserFromId(newUser.getUserId()) != null) {
            throw new IllegalArgumentException("User ID already exists. Please use existing user option.");
        }

        userManager.addUser(newUser);
        JOptionPane.showMessageDialog(
                eventView,
                "Congrats, proceed the registration with existing user.",
                "New User Created",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void completeRegistration(User user, Voucher selectedVoucher, List<Service> selectedServices) {
        double eventFee = selectedEvent.getRegistrationFee();
        double servicesTotalFee = 0.0;
        double discountAmount = 0.0;

        // Sum up all selected services fees
        if (selectedServices != null) {
            for (Service service : selectedServices) {
                servicesTotalFee += service.getServiceFee();
            }
        }

        double totalBeforeDiscount = eventFee + servicesTotalFee;

        // Calculate discount amount based on voucher
        if (selectedVoucher != null) {
            if (selectedVoucher.isPercentage()) {
                discountAmount = totalBeforeDiscount * (selectedVoucher.getDiscountAmount() / 100.0);
            } else {
                discountAmount = selectedVoucher.getDiscountAmount();
            }
            // Ensure discount does not exceed total
            discountAmount = Math.min(discountAmount, totalBeforeDiscount);
        }

        double finalTotal = totalBeforeDiscount - discountAmount;

        // Create EventRegistration
        EventRegistration.EventRegistrationBuilder builder = new EventRegistration.EventRegistrationBuilder()
                .userId(user.getUserId())
                .eventId(selectedEvent.getEventId())
                .paymentAmount(finalTotal);

        // Add selected services
        if (selectedServices != null) {
            for (Service service : selectedServices) {
                builder.addService(service);
            }
        }

        // Apply voucher if any
        if (selectedVoucher != null) {
            builder.applyDiscount(selectedVoucher);
        }

        EventRegistration registration = builder.build();

        // Show success message (already implemented)
        StringBuilder message = new StringBuilder();
        message.append("Registration Successful!\n\n");
        message.append(String.format("User: %s (%s)\n", user.getName(), user.getUserId()));
        message.append(String.format("Event: %s\n", selectedEvent.getEventName()));
        message.append(String.format("Venue: %s\n", selectedEvent.getEventVenue()));
        message.append(
                String.format("Date: %s\n\n", new SimpleDateFormat("yyyy-MM-dd").format(selectedEvent.getEventDate())));

        message.append(String.format("Event Fee: RM%.2f\n", eventFee));

        if (selectedServices != null && !selectedServices.isEmpty()) {
            message.append("Additional Services:\n");
            for (Service s : selectedServices) {
                message.append(String.format(" - %s: RM%.2f\n", s.getServiceName(), s.getServiceFee()));
            }
            message.append(String.format("Services Total: RM%.2f\n", servicesTotalFee));
        }

        if (selectedVoucher != null) {
            message.append(String.format("Voucher Applied: %s (Type: %s)\n", selectedVoucher.getVoucherName(),
                    selectedVoucher.getVoucherType()));
            message.append(String.format("Discount Amount: RM%.2f\n", discountAmount));
        }
        RegistrationManager.getInstance().saveRegistration(registration);

        message.append(String.format("\nTotal Amount Payable: RM%.2f", finalTotal));

        JOptionPane.showMessageDialog(
                eventView,
                message.toString(),
                "Registration Complete",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showVoucherAndServiceSelection(User user) {
        VoucherManager voucherManager = VoucherManager.getInstance();
        ServiceManager serviceManager = ServiceManager.getInstance();

        VoucherServiceDialog dialog = new VoucherServiceDialog(
                voucherManager.getAllVouchers(),
                serviceManager.getAllServices());

        dialog.setVisible(true);

        if (dialog.wasConfirmed()) {
            Voucher selectedVoucher = dialog.getSelectedVoucher();
            List<Service> selectedServices = dialog.getSelectedServices();

            boolean confirmed = showReceiptPreviewAndConfirm(user, selectedVoucher, selectedServices);
            if (confirmed) {
                completeRegistration(user, selectedVoucher, selectedServices);
            } else {
                JOptionPane.showMessageDialog(
                        eventView,
                        "Registration was cancelled.",
                        "Cancelled",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(
                    eventView,
                    "Registration was cancelled.",
                    "Cancelled",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean showReceiptPreviewAndConfirm(User user, Voucher voucher, List<Service> services) {
        double eventFee = selectedEvent.getRegistrationFee();
        double servicesTotalFee = 0.0;
        if (services != null) {
            for (Service s : services) {
                servicesTotalFee += s.getServiceFee();
            }
        }

        double totalBeforeDiscount = eventFee + servicesTotalFee;
        double discountAmount = 0.0;
        if (voucher != null) {
            if (voucher.isPercentage()) {
                discountAmount = totalBeforeDiscount * (voucher.getDiscountAmount() / 100.0);
            } else {
                discountAmount = voucher.getDiscountAmount();
            }
            discountAmount = Math.min(discountAmount, totalBeforeDiscount);
        }

        double finalTotal = totalBeforeDiscount - discountAmount;

        StringBuilder receipt = new StringBuilder();
        receipt.append("Registration Preview\n\n");
        receipt.append(String.format("User: %s (%s)\n", user.getName(), user.getUserId()));
        receipt.append(String.format("Event: %s\n", selectedEvent.getEventName()));
        receipt.append(String.format("Venue: %s\n", selectedEvent.getEventVenue()));
        receipt.append(
                String.format("Date: %s\n\n", new SimpleDateFormat("yyyy-MM-dd").format(selectedEvent.getEventDate())));
        receipt.append(String.format("Event Fee: RM%.2f\n", eventFee));

        if (services != null && !services.isEmpty()) {
            receipt.append("Additional Services:\n");
            for (Service s : services) {
                receipt.append(String.format(" - %s: RM%.2f\n", s.getServiceName(), s.getServiceFee()));
            }
            receipt.append(String.format("Services Total: RM%.2f\n", servicesTotalFee));
        }

        if (voucher != null) {
            receipt.append(String.format("Voucher Applied: %s (Type: %s)\n", voucher.getVoucherName(),
                    voucher.getVoucherType()));
            receipt.append(String.format("Discount Amount: RM%.2f\n", discountAmount));
        }

        receipt.append(String.format("\nTotal Amount Payable: RM%.2f\n\n", finalTotal));
        receipt.append("Confirm registration?");

        int choice = JOptionPane.showConfirmDialog(
                eventView,
                receipt.toString(),
                "Confirm Registration",
                JOptionPane.YES_NO_OPTION);

        return choice == JOptionPane.YES_OPTION;
    }

    private void loadEvents() {
        eventView.displayEvents(eventManager.getEvents());
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                eventView,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showErrorMessage(JDialog parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}