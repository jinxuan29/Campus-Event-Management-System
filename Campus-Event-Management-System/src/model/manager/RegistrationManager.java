package model.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import model.EventRegistration;
import model.Service;
import model.Voucher;
import model.observer.RegistrationObserver;
import model.observer.RegistrationSubject;

public class RegistrationManager implements RegistrationSubject {
    private static RegistrationManager instance;
    private final Map<String, EventRegistration> registrations;
    private final List<RegistrationObserver> observers = new ArrayList<>();
    private final String filepath = "Campus-Event-Management-System/database/EventRegistration.txt";

    private RegistrationManager() {
        registrations = new HashMap<>();
        readRegistrationsFromTxt(filepath);
    }

    public static RegistrationManager getInstance() {
        if (instance == null) {
            instance = new RegistrationManager();
        }
        return instance;
    }

    private void readRegistrationsFromTxt(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ServiceManager serviceManager = ServiceManager.getInstance();
            VoucherManager voucherManager = VoucherManager.getInstance();

            while ((line = br.readLine()) != null) {
                // Expected format:
                // regId,userId,eventId,registrationDate,status,paymentAmount,[serviceId|serviceId|...],voucherId
                String[] parts = line.split(",", -1);
                if (parts.length < 8) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String regId = parts[0].trim();
                String userId = parts[1].trim();
                String eventId = parts[2].trim();
                Date regDate = sdf.parse(parts[3].trim());
                String status = parts[4].trim();
                double paymentAmount = Double.parseDouble(parts[5].trim());

                // Parse services list [serviceId|serviceId|...]
                List<Service> services = new ArrayList<>();
                String servicesRaw = parts[6].trim();
                if (!servicesRaw.isEmpty()) {
                    String[] serviceIds = servicesRaw.replaceAll("[\\[\\]]", "").split("\\|");
                    for (String sid : serviceIds) {
                        Service s = serviceManager.getServiceById(sid.trim());
                        if (s != null) {
                            services.add(s);
                        }
                    }
                }

                // Parse voucher
                Voucher voucher = null;
                String voucherId = parts[7].trim();
                if (!voucherId.isEmpty()) {
                    voucher = voucherManager.getVoucherById(voucherId);
                }

                EventRegistration registration = new EventRegistration.EventRegistrationBuilder()
                        .registrationId(regId)
                        .userId(userId)
                        .eventId(eventId)
                        .registrationDate(regDate)
                        .status(status)
                        .paymentAmount(paymentAmount)
                        .applyDiscount(voucher)
                        .build();

                // Add all services
                for (Service s : services) {
                    registration.addService(s);
                }

                registrations.put(regId, registration);
            }
        } catch (Exception e) {
            System.err.println("Error reading registration file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveRegistration(EventRegistration registration) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Convert services to [serv1|serv2|serv3]
            StringBuilder servicesBuilder = new StringBuilder("[");
            List<Service> services = registration.getSelectedServices();
            for (int i = 0; i < services.size(); i++) {
                servicesBuilder.append(services.get(i).getServiceId());
                if (i < services.size() - 1) {
                    servicesBuilder.append("|");
                }
            }
            servicesBuilder.append("]");

            String voucherId = registration.getAppliedDiscount() != null
                    ? registration.getAppliedDiscount().getVoucherId()
                    : "";

            // Write registration line safely
            String line = String.join(",",
                    registration.getRegistrationId(),
                    registration.getUserId(),
                    registration.getEventId(),
                    sdf.format(registration.getRegistrationDate()),
                    registration.getStatus(),
                    String.valueOf(registration.getPaymentAmount()),
                    servicesBuilder.toString(),
                    voucherId);

            writer.write(line + System.lineSeparator());

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Store in map and notify observers
        registrations.put(registration.getRegistrationId(), registration);
        notifyObservers(new ArrayList<>(registrations.values()));
    }

    public void saveAllRegistrationsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, false))) { // overwrite mode
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (EventRegistration reg : registrations.values()) {
                StringBuilder servicesBuilder = new StringBuilder("[");
                List<Service> services = reg.getSelectedServices();
                for (int i = 0; i < services.size(); i++) {
                    servicesBuilder.append(services.get(i).getServiceId());
                    if (i < services.size() - 1)
                        servicesBuilder.append("|");
                }
                servicesBuilder.append("]");

                String voucherId = reg.getAppliedDiscount() != null ? reg.getAppliedDiscount().getVoucherId() : "";

                String line = String.join(",",
                        reg.getRegistrationId(),
                        reg.getUserId(),
                        reg.getEventId(),
                        sdf.format(reg.getRegistrationDate()),
                        reg.getStatus(),
                        String.valueOf(reg.getPaymentAmount()),
                        servicesBuilder.toString(),
                        voucherId);

                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeRegistrationsByUserId(String userId) {
        boolean changed = false;
        Iterator<Map.Entry<String, EventRegistration>> iterator = registrations.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, EventRegistration> entry = iterator.next();
            if (entry.getValue().getUserId().equals(userId)) {
                iterator.remove();
                changed = true;
            }
        }

        if (changed) {
            saveAllRegistrationsToFile();
            registrationUpdated();
        }
    }

    public void removeRegistrationsByEventId(String eventId) {
        boolean changed = false;
        Iterator<Map.Entry<String, EventRegistration>> iterator = registrations.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, EventRegistration> entry = iterator.next();
            if (entry.getValue().getEventId().equals(eventId)) {
                iterator.remove();
                changed = true;
            }
        }

        if (changed) {
            saveAllRegistrationsToFile();
            registrationUpdated();
        }
    }

    public void removeRegistration(String registrationId) {
        if (registrations.remove(registrationId) != null) {
            saveAllRegistrationsToFile();
            registrationUpdated();
        }
    }

    public boolean isUserRegisteredForEvent(String userId, String eventId) {
        return registrations.values().stream()
                .anyMatch(r -> r.getUserId().equals(userId) && r.getEventId().equals(eventId));
    }

    public List<EventRegistration> getRegistrations() {
        return new ArrayList<>(registrations.values());
    }

    public EventRegistration getRegistrationById(String regId) {
        return registrations.get(regId);
    }

    public void registrationUpdated() {
        notifyObservers(getRegistrations());
    }

    // RegistrationSubject methods
    @Override
    public void registerObserver(RegistrationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(RegistrationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(List<EventRegistration> registrations) {
        for (RegistrationObserver observer : observers) {
            observer.updateRegistration(registrations);
        }
    }
}
