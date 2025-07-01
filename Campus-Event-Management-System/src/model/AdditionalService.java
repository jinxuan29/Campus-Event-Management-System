package model;

public class AdditionalService {
    private String serviceId;
    private String serviceName;
    private double serviceFee;

    public AdditionalService(String serviceId, String serviceName, double serviceFee) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceFee = serviceFee;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceFee() {
        return serviceFee;
    }
}
