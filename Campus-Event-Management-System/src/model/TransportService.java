package model;

public class TransportService extends Service {
    private TransportService(Builder builder) {
        super(builder);
    }

    public static class Builder extends ServiceBuilder<Builder> {
        public Builder() {
            this.serviceType("TransportService");
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public TransportService build() {
            return new TransportService(this);
        }
    }
}
