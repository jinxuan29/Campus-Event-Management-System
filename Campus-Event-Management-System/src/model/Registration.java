package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Registration {
    private String registrationId;
    private String userId;
    private String eventId;
    private Date registrationDate;
    private String status;
    private String userRole;
    private List<Service> selectedServices = new ArrayList<>();
    private Discount appliedDiscount;

    public Registration(RegistrationBuilder builder) {
        this.registrationId = builder.registrationId;
        this.userId = builder.userId;
        this.eventId = builder.eventId;
        this.registrationDate = builder.registrationDate;
        this.status = builder.status;
        this.userRole = builder.userRole;
        this.selectedServices = builder.selectedServices;
        this.appliedDiscount = builder.appliedDiscount;
    }

    public static class RegistrationBuilder {
        private String registrationId;
        private String userId;
        private String eventId;
        private Date registrationDate = new Date();
        private String status = "PENDING";
        private String userRole;
        private List<Service> selectedServices = new ArrayList<>();
        private Discount appliedDiscount;

        public RegistrationBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public RegistrationBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public RegistrationBuilder addService(Service service) {
            this.selectedServices.add(service);
            return this;
        }

        public RegistrationBuilder applyDiscount(Discount discount) {
            this.appliedDiscount = discount;
            return this;
        }

        public Registration build() {
            this.registrationId = "REG" + System.currentTimeMillis();
            return new Registration(this);
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

    public String getStatus() {
        return status;
    }

    public String getUserRole() {
        return userRole;
    }

    public List<Service> getSelectedServices() {
        return selectedServices;
    }

    public void addService(Service service) {
        selectedServices.add(service);
    }

    public Discount getAppliedDiscount() {
        return appliedDiscount;
    }
}
