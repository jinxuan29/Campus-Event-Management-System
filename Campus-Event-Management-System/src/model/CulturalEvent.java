package model;

public class CulturalEvent extends Event {
    private CulturalEvent(CulturalEventBuilder builder) {
        super(builder);
    }

    public static class CulturalEventBuilder extends Event.EventBuilder<CulturalEventBuilder> {
        private static final String typeEvent = "Cultural";

        public CulturalEventBuilder() {
            super.eventType(typeEvent);
        }

        @Override
        protected CulturalEventBuilder self() {
            return this;
        }

        @Override
        public CulturalEvent build() {
            return new CulturalEvent(this);
        }
    }
}
