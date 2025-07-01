package model;

public class CateringService extends Service {
    private CateringService(Builder builder) {
        super(builder);
    }

    public static class Builder extends ServiceBuilder<Builder> {
        public Builder() {
            this.serviceType("CateringService");
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public CateringService build() {
            return new CateringService(this);
        }
    }
}
