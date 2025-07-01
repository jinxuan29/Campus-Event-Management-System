package controller;

import model.*;
import view.RegistrationPageView;
import java.util.List;

public class RegistrationController {
    private final RegistrationPageView view;
    private final EventManager eventManager;
    private final User currentUser;

    public RegistrationController(EventManager eventManager, RegistrationPageView view, User user) {
        this.eventManager = eventManager;
        this.view = view;
        this.currentUser = user;
        setupEventListeners();
    }

    private void setupEventListeners() {
        view.getRegisterButton().addActionListener(e -> registerForEvent());
    }

    private void registerForEvent() {
        Event selectedEvent = view.getSelectedEvent();
        List<Service> selectedServices = view.getSelectedServices();
        Discount discount = view.getSelectedDiscount();

        double baseFee = selectedEvent.getRegistrationFee();
        double servicesFee = selectedServices.stream().mapToDouble(Service::getServiceFee).sum();
        double discountAmount = discount != null ? discount.applyDiscount(baseFee + servicesFee) : 0;
        double total = baseFee + servicesFee - discountAmount;

        Registration registration = new Registration.RegistrationBuilder()
            .userId(currentUser.getUserId())
            .eventId(selectedEvent.getEventId())
            .baseFee(baseFee)
            .servicesFee(servicesFee)
            .discountAmount(discountAmount)
            .totalAmount(total)
            .build();

        selectedServices.forEach(registration::addService);
        registration.save();

        Bill bill = new Bill(baseFee, servicesFee, discountAmount, total);
        bill.save();

        view.displayBill(bill);
    }
}
