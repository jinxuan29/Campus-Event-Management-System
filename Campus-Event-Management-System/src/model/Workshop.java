package model;

public class Workshop extends Event {
    private Workshop(WorkshopBuilder builder) {
        super(builder);
    }

    public static class WorkshopBuilder extends Event.EventBuilder {
        private final String typeEvent = "Workshops";

        public WorkshopBuilder() {
            super.eventType(typeEvent);
        }

        // Dont allow changes to other event type

        @Override
        public Workshop build() {
            return new Workshop(this);
        }
    }
}
