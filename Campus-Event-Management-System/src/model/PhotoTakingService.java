package model;

public class PhotoTakingService extends Service {
    private PhotoTakingService(Builder builder) {
        super(builder);
    }

    public static class Builder extends ServiceBuilder<Builder> {
        public Builder() {
            this.serviceType("PhotoTakingService");
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public PhotoTakingService build() {
            return new PhotoTakingService(this);
        }
    }
}
