package model.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.CateringService;
import model.PhotoTakingService;
import model.Service;
import model.TransportService;

public class ServiceManager {
    private static ServiceManager instance;
    private final List<Service> services = new ArrayList<>();

    private ServiceManager() {
        loadServicesFromFile("Campus-Event-Management-System/database/Services.txt");
    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    private void loadServicesFromFile(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("service_id"))
                    continue;

                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double fee = Double.parseDouble(parts[2].trim());
                    String type = parts[3].trim();

                    Service service;

                    switch (type.toLowerCase()) {
                        case "transport":
                            service = new TransportService.Builder()
                                    .serviceId(id)
                                    .serviceName(name)
                                    .serviceFee(fee)
                                    .build();
                            break;
                        case "catering":
                            service = new CateringService.Builder()
                                    .serviceId(id)
                                    .serviceName(name)
                                    .serviceFee(fee)
                                    .build();
                            break;
                        case "phototaking":
                            service = new PhotoTakingService.Builder()
                                    .serviceId(id)
                                    .serviceName(name)
                                    .serviceFee(fee)
                                    .build();
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported voucher type: " + type);
                    }

                    services.add(service);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading services: " + e.getMessage());
        }
    }

    public List<Service> getAllServices() {
        return services;
    }

    public int getTotalServiceCount() {
        return services.size();
    }

    public Service getServiceById(String serviceId) {
        return services.stream()
                .filter(s -> s.getServiceId().equalsIgnoreCase(serviceId))
                .findFirst()
                .orElse(null);
    }
}
