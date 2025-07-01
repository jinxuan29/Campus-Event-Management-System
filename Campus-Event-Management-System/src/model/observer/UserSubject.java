package model.observer;

import java.util.List;
import model.User;

public interface UserSubject {

    void registerObserver(UserObserver observer);

    void removeObserver(UserObserver observer);

    void notifyObservers(List<User> users);
}
