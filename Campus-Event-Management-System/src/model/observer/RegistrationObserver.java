package model.observer;

import java.util.List;
import model.EventRegistration;

public interface RegistrationObserver {
    void updateRegistration(List<EventRegistration> registration);
}
