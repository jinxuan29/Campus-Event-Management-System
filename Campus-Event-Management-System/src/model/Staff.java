package model;

public class Staff extends User {
    private Staff(StaffBuilder builder) {
        super(builder);
    }

    public static class StaffBuilder extends UserBuilder {

        private final String role = "STAFF";

        public StaffBuilder() {
            super.role(this.role);
        }

        public StaffBuilder StaffId(String StaffId) {
            super.userId(StaffId);
            return this;
        }

        // Dont allow changes to other role
        @Override
        public StaffBuilder role(String role) {
            throw new UnsupportedOperationException("Staff role cannot be changed from 'STAFF'");
        }

        @Override
        public Staff build() {
            return new Staff(this);
        }
    }
}
