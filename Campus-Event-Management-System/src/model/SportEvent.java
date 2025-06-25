package model;

public class SportEvent extends Event {
    private SportEvent(SportEventBuilder builder) {
        super(builder);
    }

    public static class SportEventBuilder extends Event.EventBuilder {
        private final String typeEvent = "SportEvents";

        public SportEventBuilder() {
            super.eventType(typeEvent);
        }

        // can set extra validation or others here
        @Override
        public SportEvent build() {
            return new SportEvent(this);
        }
    }
}
