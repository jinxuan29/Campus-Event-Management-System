package model;

public class Voucher {
    private String voucherId;
    private String voucherName;
    private String voucherType;
    private double discountAmount;
    private boolean percentage;

    protected Voucher(VoucherBuilder<?> builder) {
        this.voucherId = builder.voucherId;
        this.voucherName = builder.voucherName;
        this.voucherType = builder.voucherType;
        this.discountAmount = builder.discountAmount;
        this.percentage = builder.percentage;
    }

    public static class VoucherBuilder<T extends VoucherBuilder<T>> {
        private String voucherId;
        private String voucherName;
        private String voucherType;
        private double discountAmount;
        private boolean percentage;

        protected T self() {
            return (T) this;
        }

        public T voucherId(String voucherId) {
            this.voucherId = voucherId;
            return self();
        }

        public T voucherName(String voucherName) {
            this.voucherName = voucherName;
            return self();
        }

        public T voucherType(String voucherType) {
            this.voucherType = voucherType;
            return self();
        }

        public T discountAmount(double discountAmount) {
            this.discountAmount = discountAmount;
            return self();
        }

        public T percentage(boolean percentage) {
            this.percentage = percentage;
            return self();
        }

        public Voucher build() {
            return new Voucher(this);
        }

    }

    // Getters
    public String getVoucherId() {
        return voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public boolean isPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        return String.join(",",
                voucherId,
                voucherName,
                voucherType,
                String.valueOf(discountAmount),
                String.valueOf(percentage));
    }
}
