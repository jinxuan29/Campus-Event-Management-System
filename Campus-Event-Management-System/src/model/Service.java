package model;

public class Service {
    protected String serviceId;
    protected String serviceName;
    protected double serviceFee;
    protected String serviceType;

    protected Service(ServiceBuilder<?> builder) {
        this.serviceId = builder.serviceId;
        this.serviceName = builder.serviceName;
        this.serviceFee = builder.serviceFee;
        this.serviceType = builder.serviceType;
    }

    public static class ServiceBuilder<T extends ServiceBuilder<T>> {
        protected String serviceId;
        protected String serviceName;
        protected double serviceFee;
        protected String serviceType;

        protected T self() {
            return (T) this;
        }

        public T serviceId(String serviceId) {
            this.serviceId = serviceId;
            return self();
        }

        public T serviceName(String serviceName) {
            this.serviceName = serviceName;
            return self();
        }

        public T serviceFee(double serviceFee) {
            this.serviceFee = serviceFee;
            return self();
        }

        public T serviceType(String serviceType) {
            this.serviceType = serviceType;
            return self();
        }

        public Service build() {
            return new Service(this);
        }
    }

    // Getters
    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public String getServiceType() {
        return serviceType;
    }

    @Override
    public String toString() {
        return String.join(",",
                serviceId,
                serviceName,
                String.valueOf(serviceFee),
                serviceType);
    }
}
