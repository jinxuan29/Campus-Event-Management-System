package model.observer;

import java.util.List;
import model.Event;

public interface EventObserver {
    void updateEvent(List<Event> events);
}
