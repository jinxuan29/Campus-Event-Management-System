package controller;

import model.*;
import view.RegistrationPageView;

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

        Registration registration = new Registration.RegistrationBuilder()
            .userId(currentUser.getUserId())
            .eventId(selectedEvent.getEventId())
            .applyDiscount(discount)
            .build();

        selectedServices.forEach(registration::addService);

        Bill bill = calculateBill(selectedEvent, selectedServices, discount);
        view.displayBill(bill);
    }

    private Bill calculateBill(Event event, List<Service> services, Discount discount) {
        double baseFee = event.getRegistrationFee();
        double servicesFee = services.stream().mapToDouble(Service::getServiceFee).sum();
        double discountAmount = discount != null ? 
            discount.applyDiscount(baseFee + servicesFee) : 0;

        return new Bill(baseFee, servicesFee, discountAmount);
    }
}
