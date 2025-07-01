package model;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    private final Map<String, User> users;

    private UserManager() {
        users = new HashMap<>();
        initializeSampleUsers();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private void initializeSampleUsers() {
        // Sample students
        Student student1 = new Student.Builder()
                .studentId("S1001")
                .name("John Doe")
                .email("john@university.edu")
                .phone("1234567890")
                .build();

        Student student2 = new Student.Builder()
                .studentId("S1002")
                .name("Jane Smith")
                .email("jane@university.edu")
                .phone("9876543210")
                .build();

        // Sample staff (no department field yet)
        Staff staff1 = new Staff.Builder()
                .staffId("E2001")
                .name("Dr. Williams")
                .email("williams@university.edu")
                .phone("5551234567")
                .build();

        Staff staff2 = new Staff.Builder()
                .staffId("E2002")
                .name("Prof. Johnson")
                .email("johnson@university.edu")
                .phone("5557654321")
                .build();

        users.put(student1.getUserId(), student1);
        users.put(student2.getUserId(), student2);
        users.put(staff1.getUserId(), staff1);
        users.put(staff2.getUserId(), staff2);
    }

    public User getUserFromId(String userId) {
        return users.get(userId);
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }
}
