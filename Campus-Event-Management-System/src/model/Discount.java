package model;

public abstract class Discount {
    protected String discountId;
    protected String description;
    protected double discountValue;

    public abstract double applyDiscount(double originalAmount);

    public String getDiscountId() {
        return discountId;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public static class EarlyBirdDiscount extends Discount {
        public EarlyBirdDiscount() {
            this.discountId = "DISC-EB";
            this.description = "Early Bird (10%)";
            this.discountValue = 0.10;
        }

        @Override
        public double applyDiscount(double originalAmount) {
            return originalAmount * discountValue;
        }
    }

    public static class GroupDiscount extends Discount {
        public GroupDiscount() {
            this.discountId = "DISC-GRP";
            this.description = "Group (15%)";
            this.discountValue = 0.15;
        }

        @Override
        public double applyDiscount(double originalAmount) {
            return originalAmount * discountValue;
        }
    }
}
