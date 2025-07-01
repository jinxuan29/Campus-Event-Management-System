package model;

public class Bill {
    private double baseFee;
    private double servicesFee;
    private double discountAmount;
    private double totalBeforeDiscount;
    private double netPayable;

    public Bill(double baseFee, double servicesFee, Voucher voucher) {
        this.baseFee = baseFee;
        this.servicesFee = servicesFee;
        this.totalBeforeDiscount = baseFee + servicesFee;

        if (voucher != null) {
            if (voucher.isPercentage()) {
                this.discountAmount = totalBeforeDiscount * (voucher.getDiscountAmount() / 100.0);
            } else {
                this.discountAmount = voucher.getDiscountAmount();
            }
            this.discountAmount = Math.min(discountAmount, totalBeforeDiscount); // clamp
        } else {
            this.discountAmount = 0.0;
        }

        this.netPayable = totalBeforeDiscount - discountAmount;
    }

    // Getters stay the same...

    public String getBreakdown() {
        return String.format("""
                Base Fee: RM%.2f
                Additional Services: RM%.2f
                ----------------------------
                Subtotal: RM%.2f
                Discount: -RM%.2f
                ----------------------------
                NET PAYABLE: RM%.2f
                """, baseFee, servicesFee, totalBeforeDiscount, discountAmount, netPayable);
    }
}
