package model;

public class Student extends User {

    private Student(Builder builder) {
        super(builder);
    }

    public static class Builder extends User.Builder<Builder> {

        private final String role = "STUDENT";

        public Builder() {
            super.role(this.role);
        }

        public Builder studentId(String studentId) {
            return this.userId(studentId);
        }

        // Prevent changing the role
        @Override
        public Builder role(String role) {
            throw new UnsupportedOperationException("Student role cannot be changed from 'STUDENT'");
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Student build() {
            return new Student(this);
        }
    }
}
