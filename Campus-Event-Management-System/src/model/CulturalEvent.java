package model;

public class CulturalEvent extends Event {
    private CulturalEvent(CulturalEventBuilder builder) {
        super(builder);
    }

    public static class CulturalEventBuilder extends Event.EventBuilder {
        private final String typeEvent = "CulturalEvents";

        public CulturalEventBuilder() {
            super.eventType(typeEvent);
        }

        // can set extra validation or others here
        @Override
        public CulturalEvent build() {
            return new CulturalEvent(this);
        }
    }
}
