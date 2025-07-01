package model;

public class User {

    private String userId;
    private String name;
    private String email;
    private String phone;
    private String role;

    protected User(Builder<?> builder) {
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.role = builder.role;
    }

    // Getter methods
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    // Setter methods
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return userId + "," + name + "," + email + "," + phone;
    }

    // Generic Builder class for extensibility
    public static class Builder<T extends Builder<T>> {

        protected String userId;
        protected String name;
        protected String email;
        protected String phone;
        protected String role;

        protected T self() {
            return (T) this;
        }

        public T userId(String userId) {
            this.userId = userId;
            return self();
        }

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T email(String email) {
            this.email = email;
            return self();
        }

        public T phone(String phone) {
            this.phone = phone;
            return self();
        }

        public T role(String role) {
            this.role = role;
            return self();
        }

        public User build() {
            return new User(this);
        }
    }
}
