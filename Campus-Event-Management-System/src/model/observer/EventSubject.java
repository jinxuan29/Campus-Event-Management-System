package model.observer;

import java.util.List;
import model.Event;

public interface EventSubject {

    void registerObserver(EventObserver observer);

    void removeObserver(EventObserver observer);

    void notifyObservers(List<Event> events);
}
