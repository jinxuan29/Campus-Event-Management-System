package model.observer;

import java.util.List;

import model.EventRegistration;

public interface RegistrationObserver {
    void update(List<EventRegistration> registration);
}
