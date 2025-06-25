
package model;

import java.util.Date;

public class Event {
    private String eventId;
    private String eventName;
    private Date eventDate;
    private String eventVenue;
    private String eventType;
    private int eventCapacity;
    private int registrationFee;

    protected Event(EventBuilder builder) {
        this.eventId = builder.eventId;
        this.eventName = builder.eventName;
        this.eventDate = builder.eventDate;
        this.eventVenue = builder.eventVenue;
        this.eventType = builder.eventType;
        this.eventCapacity = builder.eventCapacity;
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

    public void setRegistrationFee(int registrationFee) {
        this.registrationFee = registrationFee;
    }

    public static class EventBuilder {
        protected String eventId;
        protected String eventName;
        protected Date eventDate;
        protected String eventVenue;
        protected String eventType;
        protected int eventCapacity;
        protected int registrationFee;

        public EventBuilder(Event event) {
            this.eventId = event.getEventId();
            this.eventName = event.getEventName();
            this.eventDate = event.getEventDate();
            this.eventVenue = event.getEventVenue();
            this.eventType = event.getEventType();
            this.eventCapacity = event.getEventCapacity();
            this.registrationFee = event.getRegistrationFee();
        }

        public EventBuilder() {
        }

        public EventBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public EventBuilder eventName(String name) {
            this.eventName = name;
            return this;
        }

        public EventBuilder eventDate(Date date) {
            this.eventDate = date;
            return this;
        }

        public EventBuilder eventVenue(String venue) {
            this.eventVenue = venue;
            return this;
        }

        public EventBuilder eventType(String type) {
            this.eventType = type;
            return this;
        }

        public EventBuilder eventCapacity(int capacity) {
            this.eventCapacity = capacity;
            return this;
        }

        public EventBuilder registrationFee(int fee) {
            this.registrationFee = fee;
            return this;
        }

        public Event build() {
            return new Event(this);
        };
    }
}
