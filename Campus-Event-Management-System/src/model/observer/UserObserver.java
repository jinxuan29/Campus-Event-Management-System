package model.observer;

import java.util.List;
import model.User;

public interface UserObserver {
    void updateUser(List<User> events);
}
