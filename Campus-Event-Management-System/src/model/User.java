package model;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String role;

    protected User(UserBuilder userMaker) {
        this.userId = userMaker.userId;
        this.name = userMaker.name;
        this.email = userMaker.email;
        this.phone = userMaker.phone;
        this.role = userMaker.role;
    }

    @Override
    public String toString() {
        return userId + "," + name + "," + email + "," + phone;
    }

    // UserBuilder class
    public static class UserBuilder {
        private String userId;
        private String name;
        private String email;
        private String phone;
        private String role;

        public UserBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder role(String role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
