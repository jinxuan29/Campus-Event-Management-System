package model;

public class Service {
    private String serviceId;
    private String serviceName;
    private double serviceFee;

    public Service(String serviceId, String serviceName, double serviceFee) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceFee = serviceFee;
    }

    // Getters
    public String getServiceId() { return serviceId; }
    public String getServiceName() { return serviceName; }  
    public double getServiceFee() { return serviceFee; }
    
}
