package model;

public class Staff extends User {

    private Staff(Builder builder) {
        super(builder);
    }

    public static class Builder extends User.Builder<Builder> {

        private final String role = "STAFF";

        public Builder() {
            super.role(role); // Set role on creation
        }

        public Builder staffId(String staffId) {
            return this.userId(staffId); // Map to userId
        }

        // Disallow manual role changes
        @Override
        public Builder role(String role) {
            throw new UnsupportedOperationException("Staff role cannot be changed from 'STAFF'");
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Staff build() {
            return new Staff(this);
        }
    }
}
