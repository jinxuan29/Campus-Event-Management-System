package model;

public class SportEvent extends Event {
    private SportEvent(SportEventBuilder builder) {
        super(builder);
    }

    public static class SportEventBuilder extends Event.EventBuilder<SportEventBuilder> {
        private static final String typeEvent = "sport";

        public SportEventBuilder() {
            super.eventType(typeEvent);
        }

        @Override
        protected SportEventBuilder self() {
            return this;
        }

        @Override
        public SportEvent build() {
            return new SportEvent(this);
        }
    }
}
