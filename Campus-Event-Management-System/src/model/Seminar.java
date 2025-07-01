package model;

public class Seminar extends Event {

    private Seminar(SeminarBuilder builder) {
        super(builder);
    }

    public static class SeminarBuilder extends EventBuilder<SeminarBuilder> {
        private final String typeEvent = "Seminar";

        public SeminarBuilder() {
            eventType(typeEvent);
        }

        @Override
        protected SeminarBuilder self() {
            return this;
        }

        @Override
        public Seminar build() {
            return new Seminar(this);
        }
    }
}
