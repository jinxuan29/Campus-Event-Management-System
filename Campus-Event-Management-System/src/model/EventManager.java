package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.observer.EventObserver;
import model.observer.EventSubject;

public class EventManager implements EventSubject {

    private List<Event> eventList;
    private List<EventObserver> observers = new ArrayList<>();
    private String filePath;

    public EventManager(String filepath) {
        this.filePath = filepath;
        this.eventList = readEventsFromTxt(filepath);
        System.out.println(observers);
        notifyObservers(eventList);
    }

    public static List<Event> readEventsFromTxt(String filepath) {
        List<Event> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 7)
                    continue;

                String eventId = parts[0];
                String name = parts[1];
                String venue = parts[2];
                String type = parts[3];
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[4]);
                int capacity = Integer.parseInt(parts[5]);
                int fee = Integer.parseInt(parts[6]);

                Event event;
                // create event class by event type in event.txt
                switch (type) {
                    case "seminar" -> event = new Seminar.SeminarBuilder()
                            .eventId(eventId)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();

                    case "workshop" -> event = new Workshop.WorkshopBuilder()
                            .eventId(eventId)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();

                    case "cultural" -> event = new CulturalEvent.CulturalEventBuilder()
                            .eventId(eventId)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();

                    case "sports" -> event = new SportEvent.SportEventBuilder()
                            .eventId(eventId)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();

                    default -> event = new Event.EventBuilder()
                            .eventId(eventId)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .eventType(type)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();
                }

                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    // update latest event into the txt file
    public void saveEventsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Event event : eventList) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(event.getEventDate());

                writer.println(String.join(",",
                        event.getEventId(),
                        event.getEventName(),
                        event.getEventVenue(),
                        event.getEventType(),
                        formattedDate,
                        String.valueOf(event.getEventCapacity()),
                        String.valueOf(event.getRegistrationFee())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getEvents() {
        return eventList;
    }

    // implement observer method
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
        notifyObservers(this.eventList);
    }

    // if event is added
    public void addEvent(Event newEvent) {
        // add id into new event
        String newId = "E" + (eventList.size() + 1);
        newEvent.setEventId(newId);

        eventList.add(newEvent);
        saveEventsToFile();
        eventsUpdated();
    }

    // if event is removed
    public void removeEvent(String eventId) {
        eventList.removeIf(event -> event.getEventId().equals(eventId));
        saveEventsToFile();
        eventsUpdated();
    }

    // if event is updated
    public void updateEvent(String eventId, Event updatedEvent) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getEventId().equals(eventId)) {
                eventList.set(i, updatedEvent);
                saveEventsToFile();
                eventsUpdated();
                return;
            }
        }
        throw new IllegalArgumentException("Event with ID " + eventId + " not found");
    }
}