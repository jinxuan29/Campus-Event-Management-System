package model.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Staff;
import model.Student;
import model.User;
import model.observer.UserObserver;
import model.observer.UserSubject;

public class UserManager implements UserSubject {
    private static UserManager instance;
    private final Map<String, User> users;
    private final List<UserObserver> observers = new ArrayList<>();
    private final String filepath = "Campus-Event-Management-System/database/User.txt";

    private UserManager() {
        users = new HashMap<>();
        readUsersFromTxt(this.filepath);
        notifyObservers(new ArrayList<>(users.values()));
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private void readUsersFromTxt(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length != 5) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String id = parts[0].trim();
                String name = parts[1].trim();
                String email = parts[2].trim();
                String phone = parts[3].trim();
                String role = parts[4].trim().toUpperCase();

                User user = createUser(id, name, email, phone, role);
                if (user != null) {
                    users.put(id, user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }
    }

    private User createUser(String id, String name, String email, String phone, String role) {
        switch (role) {
            case "STUDENT":
                return new Student.Builder()
                        .studentId(id)
                        .name(name)
                        .email(email)
                        .phone(phone)
                        .build();
            case "STAFF":
                return new Staff.Builder()
                        .staffId(id)
                        .name(name)
                        .email(email)
                        .phone(phone)
                        .build();
            default:
                System.out.println("Unknown role for user: " + id);
                return null;
        }
    }

    private void writeUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (User user : users.values()) {
                writer.write(user.getUserId() + "," +
                        user.getName() + "," +
                        user.getEmail() + "," +
                        user.getPhone() + "," +
                        user.getRole().toUpperCase());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing users to file: " + e.getMessage());
        }
    }

    public User getUserFromId(String userId) {
        return users.get(userId);
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
        writeUsersToFile();
        usersUpdated();
    }

    public void removeUser(String userId) {
        if (users.remove(userId) != null) {
            writeUsersToFile();
            usersUpdated();
        }
    }

    public void updateUser(String userId, User updatedUser) {
        if (users.containsKey(userId)) {
            users.put(userId, updatedUser);
            writeUsersToFile();
            usersUpdated();
        } else {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    // Observer pattern implementation
    @Override
    public void registerObserver(UserObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(UserObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(List<User> users) {
        for (UserObserver observer : observers) {
            observer.update(users);
        }
    }

    public void usersUpdated() {
        notifyObservers(getUsers());
    }
}