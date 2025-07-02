package model;

public class Workshop extends Event {
    private Workshop(WorkshopBuilder builder) {
        super(builder);
    }

    public static class WorkshopBuilder extends Event.EventBuilder<WorkshopBuilder> {
        private static final String typeEvent = "Workshop";

        public WorkshopBuilder() {
            super.eventType(typeEvent);
        }

        @Override
        protected WorkshopBuilder self() {
            return this;
        }

        @Override
        public Workshop build() {
            return new Workshop(this);
        }
    }
}
