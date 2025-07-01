package model.observer;

import java.util.List;

import model.User;

public interface UserObserver {
    void update(List<User> events);
}
