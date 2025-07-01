package model.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.CulturalEvent;
import model.Event;
import model.EventRegistration;
import model.Seminar;
import model.SportEvent;
import model.Workshop;
import model.observer.EventObserver;
import model.observer.EventSubject;
import model.observer.RegistrationObserver;

public class EventManager implements EventSubject, RegistrationObserver {
    private static EventManager instance;
    private Map<String, Event> eventMap;
    private List<EventObserver> observers = new ArrayList<>();
    private final String filepath = "Campus-Event-Management-System/database/Event.txt";

    private EventManager() {
        this.eventMap = new HashMap<>();
        loadEventsFromFile();
        notifyObservers(new ArrayList<>(eventMap.values()));
    }

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    private void loadEventsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 8)
                    continue;

                String eventId = parts[0];
                String name = parts[1];
                String venue = parts[2];
                String type = parts[3].toLowerCase();
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[4]);
                int currentCapacity = Integer.parseInt(parts[5]);
                int capacity = Integer.parseInt(parts[6]);
                int fee = Integer.parseInt(parts[7]);

                Event event = switch (type) {
                    case "seminar" -> new Seminar.SeminarBuilder()
                            .eventId(eventId)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .currentCapacity(currentCapacity)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();
                    case "workshop" -> new Workshop.WorkshopBuilder()
                            .eventId(eventId)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .currentCapacity(currentCapacity)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();
                    case "cultural" -> new CulturalEvent.CulturalEventBuilder()
                            .eventId(eventId)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .currentCapacity(currentCapacity)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();
                    case "sport" -> new SportEvent.SportEventBuilder()
                            .eventId(eventId)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .currentCapacity(currentCapacity)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();
                    default -> throw new IllegalArgumentException("Unknown event type: " + type);
                };

                eventMap.put(eventId, event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveEventsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            for (Event event : eventMap.values()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(event.getEventDate());

                writer.println(String.join(",",
                        event.getEventId(),
                        event.getEventName(),
                        event.getEventVenue(),
                        event.getEventType(),
                        formattedDate,
                        String.valueOf(event.getCurrentCapacity()),
                        String.valueOf(event.getEventCapacity()),
                        String.valueOf(event.getRegistrationFee())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRegistration(EventRegistration registration) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("database/EventRegistration.txt", true))) {
            writer.println(String.join(",",
                    registration.getRegistrationId(),
                    registration.getUserId(),
                    registration.getEventId(),
                    new SimpleDateFormat("yyyy-MM-dd").format(registration.getRegistrationDate()),
                    registration.getStatus()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getEvents() {
        return new ArrayList<>(eventMap.values());
    }

    public Event getEventById(String eventId) {
        return eventMap.get(eventId);
    }

    @Override
    public void registerObserver(EventObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(EventObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(List<Event> events) {
        for (EventObserver observer : observers) {
            observer.update(events);
        }
    }

    public void eventsUpdated() {
        notifyObservers(getEvents());
    }

    public void addEvent(Event newEvent) {
        String newId = "E" + (eventMap.size() + 1);
        newEvent.setEventId(newId);
        eventMap.put(newId, newEvent);
        saveEventsToFile();
        eventsUpdated();
    }

    public void removeEvent(String eventId) {
        if (eventMap.remove(eventId) != null) {
            saveEventsToFile();
            eventsUpdated();
        }
    }

    public void updateEvent(String eventId, Event updatedEvent) {
        if (eventMap.containsKey(eventId)) {
            eventMap.put(eventId, updatedEvent);
            saveEventsToFile();
            eventsUpdated();
        } else {
            throw new IllegalArgumentException("Event with ID " + eventId + " not found");
        }
    }

    @Override
    public void update(List<EventRegistration> registrations) {
        updateCurrentCapacitiesFromRegistrations(registrations);
    }

    private void updateCurrentCapacitiesFromRegistrations(List<EventRegistration> registrations) {
        // Count registrations per eventId
        Map<String, Integer> registrationCounts = new HashMap<>();
        for (EventRegistration reg : registrations) {
            String eventId = reg.getEventId();
            registrationCounts.put(eventId, registrationCounts.getOrDefault(eventId, 0) + 1);
        }

        // Update currentCapacity for each event based on registration count (cap at
        // eventCapacity)
        boolean changed = false;
        for (Event event : eventMap.values()) {
            int count = registrationCounts.getOrDefault(event.getEventId(), 0);
            int newCurrentCapacity = Math.min(count, event.getEventCapacity());

            if (event.getCurrentCapacity() != newCurrentCapacity) {
                event.setCurrentCapacity(newCurrentCapacity);
                changed = true;
            }
        }

        if (changed) {
            saveEventsToFile();
            eventsUpdated();
        }
    }

}