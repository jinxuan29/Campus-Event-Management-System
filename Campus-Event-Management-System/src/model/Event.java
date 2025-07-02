package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private String eventId;
    private String eventName;
    private Date eventDate;
    private String eventVenue;
    private String eventType;
    private int eventCapacity;
    private int currentCapacity;
    private int registrationFee;

    protected Event(EventBuilder builder) {
        this.eventId = builder.eventId;
        this.eventName = builder.eventName;
        this.eventDate = builder.eventDate;
        this.eventVenue = builder.eventVenue;
        this.eventType = builder.eventType;
        this.eventCapacity = builder.eventCapacity;
        this.currentCapacity = builder.currentCapacity;
        this.registrationFee = builder.registrationFee;
    }

    // Getters and Setters
    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public String getEventType() {
        return eventType;
    }

    public int getEventCapacity() {
        return eventCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public int getRegistrationFee() {
        return registrationFee;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventCapacity(int eventCapacity) {
        this.eventCapacity = eventCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public void setRegistrationFee(int registrationFee) {
        this.registrationFee = registrationFee;
    }

    public String getDetails() {
        return String.format(
                "Event ID: %s\nName: %s\nDate: %s\nVenue: %s\nType: %s\nCapacity: %d/%d\nFee: RM%d",
                eventId,
                eventName,
                new SimpleDateFormat("yyyy-MM-dd").format(eventDate),
                eventVenue,
                eventType,
                currentCapacity,
                eventCapacity,
                registrationFee);
    }

    @Override
    public String toString() {
        return eventName;
    }

    public static class EventBuilder<T extends EventBuilder<T>> {
        private String eventId;
        private String eventName;
        private Date eventDate;
        private String eventVenue;
        private String eventType;
        private int currentCapacity = 0;
        private int eventCapacity;
        private int registrationFee;

        public EventBuilder() {
        }

        public EventBuilder(Event event) {
            this.eventId = event.getEventId();
            this.eventName = event.getEventName();
            this.eventDate = event.getEventDate();
            this.eventVenue = event.getEventVenue();
            this.eventType = event.getEventType();
            this.eventCapacity = event.getEventCapacity();
            this.currentCapacity = event.getCurrentCapacity();
            this.registrationFee = event.getRegistrationFee();
        }

        protected T self() {
            return (T) this;
        }

        public T eventId(String eventId) {
            this.eventId = eventId;
            return self();
        }

        public T eventName(String name) {
            this.eventName = name;
            return self();
        }

        public T eventDate(Date date) {
            this.eventDate = date;
            return self();
        }

        public T eventVenue(String venue) {
            this.eventVenue = venue;
            return self();
        }

        public T eventType(String type) {
            this.eventType = type;
            return self();
        }

        public T eventCapacity(int capacity) {
            this.eventCapacity = capacity;
            return self();
        }

        public T currentCapacity(int currentCapacity) { // New builder method
            this.currentCapacity = currentCapacity;
            return self();
        }

        public T registrationFee(int fee) {
            this.registrationFee = fee;
            return self();
        }

        public Event build() {
            return new Event(this);
        }
    }
}