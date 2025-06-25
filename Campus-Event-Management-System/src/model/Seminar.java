package model;

public class Seminar extends Event {

    private Seminar(SeminarBuilder builder) {
        super(builder);
    }

    public static class SeminarBuilder extends Event.EventBuilder {
        private final String typeEvent = "Seminars";

        public SeminarBuilder() {
            super.eventType(typeEvent);
        }

        // Dont allow changes to other event type

        @Override
        public Seminar build() {
            return new Seminar(this);
        }
    }
}
