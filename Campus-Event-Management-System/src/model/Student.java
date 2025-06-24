package model;

public class Student extends User {

    private Student(StudentBuilder builder) {
        super(builder);
    }

    public static class StudentBuilder extends UserBuilder {

        private final String role = "STUDENT";

        public StudentBuilder() {
            super.role(this.role);
        }

        public StudentBuilder studentId(String studentId) {
            super.userId(studentId);
            return this;
        }

        // Dont allow changes to other role
        @Override
        public StudentBuilder role(String role) {
            throw new UnsupportedOperationException("Student role cannot be changed from 'STUDENT'");
        }

        @Override
        public Student build() {
            return new Student(this);
        }
    }
}
