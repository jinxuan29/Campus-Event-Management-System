
package model;

import java.util.Date;

public class Event {
    private final String eventName;
    private final Date eventDate;
    private final String eventVenue;
    private final String eventType;
    private final int eventCapacity;
    private final int registrationFee;

    protected Event(EventBuilder builder) {
        this.eventName = builder.eventName;
        this.eventDate = builder.eventDate;
        this.eventVenue = builder.eventVenue;
        this.eventType = builder.eventType;
        this.eventCapacity = builder.eventCapacity;
        this.registrationFee = builder.registrationFee;
    }

    public static class EventBuilder {
        protected String eventName;
        protected Date eventDate;
        protected String eventVenue;
        protected String eventType;
        protected int eventCapacity;
        protected int registrationFee;

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
