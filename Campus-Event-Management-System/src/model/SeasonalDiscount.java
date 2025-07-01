package model;

public class SeasonalDiscount extends Voucher {
    private SeasonalDiscount(SeasonalDiscountBuilder builder) {
        super(builder);
    }

    public static class SeasonalDiscountBuilder extends Voucher.VoucherBuilder<SeasonalDiscountBuilder> {
        private String type = "SeasonalDiscount";
        private int discountAmount = 10;
        private boolean percentage = false;

        public SeasonalDiscountBuilder() {
            super.voucherType(type);
            super.discountAmount(discountAmount);
            super.percentage(percentage);
        }

        @Override
        public SeasonalDiscount build() {
            return new SeasonalDiscount(this);
        }
    }
}
