package model.observer;

import java.util.List;
import model.EventRegistration;

public interface RegistrationSubject {

    void registerObserver(RegistrationObserver observer);

    void removeObserver(RegistrationObserver observer);

    void notifyObservers(List<EventRegistration> events);

}
