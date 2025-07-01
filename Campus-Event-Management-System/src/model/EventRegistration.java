package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventRegistration {
    private String registrationId;
    private String userId;
    private String eventId;
    private Date registrationDate;
    private double paymentAmount;
    private String status;
    private List<Service> selectedServices = new ArrayList<>();
    private Voucher appliedDiscount;

    public EventRegistration(EventRegistrationBuilder builder) {
        this.registrationId = builder.registrationId;
        this.userId = builder.userId;
        this.eventId = builder.eventId;
        this.registrationDate = builder.registrationDate;
        this.paymentAmount = builder.paymentAmount;
        this.status = builder.status;
        this.selectedServices = builder.selectedServices;
        this.appliedDiscount = builder.appliedDiscount;
    }

    public static class EventRegistrationBuilder {
        private String registrationId;
        private String userId;
        private String eventId;
        private Date registrationDate = new Date();
        private double paymentAmount;
        private String status = "SUCCEED";
        private List<Service> selectedServices = new ArrayList<>();
        private Voucher appliedDiscount;

        public EventRegistrationBuilder registrationId(String registrationId) {
            this.registrationId = registrationId;
            return this;
        }

        public EventRegistrationBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public EventRegistrationBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public EventRegistrationBuilder registrationDate(Date registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public EventRegistrationBuilder paymentAmount(double paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        public EventRegistrationBuilder status(String status) {
            this.status = status;
            return this;
        }

        public EventRegistrationBuilder addService(Service service) {
            this.selectedServices.add(service);
            return this;
        }

        public EventRegistrationBuilder applyDiscount(Voucher discount) {
            this.appliedDiscount = discount;
            return this;
        }

        public EventRegistration build() {
            if (registrationId == null) {
                this.registrationId = "REG" + System.currentTimeMillis();
            }
            return new EventRegistration(this);
        }
    }

    // Getters
    public String getRegistrationId() {
        return registrationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public String getStatus() {
        return status;
    }

    public List<Service> getSelectedServices() {
        return selectedServices;
    }

    public void addService(Service service) {
        selectedServices.add(service);
    }

    public Voucher getAppliedDiscount() {
        return appliedDiscount;
    }
}
