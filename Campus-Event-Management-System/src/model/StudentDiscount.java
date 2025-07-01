package model;

public class StudentDiscount extends Voucher {
    private StudentDiscount(StudentDiscountBuilder builder) {
        super(builder);
    }

    public static class StudentDiscountBuilder extends Voucher.VoucherBuilder<StudentDiscountBuilder> {
        private String type = "StudentDiscount";
        private int discountAmount = 20;
        private boolean percentage = true;

        public StudentDiscountBuilder() {
            super.voucherType(type);
            super.discountAmount(discountAmount);
            super.percentage(percentage);
        }

        @Override
        public StudentDiscount build() {
            return new StudentDiscount(this);
        }
    }
}
