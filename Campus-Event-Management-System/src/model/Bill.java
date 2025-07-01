package model;

public class Bill {
    private double baseFee;
    private double servicesFee;
    private double discountAmount;
    private double totalBeforeDiscount;
    private double netPayable;

    public Bill(double baseFee, double servicesFee, double discountAmount, double netPayable) {
        this.baseFee = baseFee;
        this.servicesFee = servicesFee;
        this.discountAmount = discountAmount;
        this.totalBeforeDiscount = baseFee + servicesFee;
        this.netPayable = netPayable;
    }

    public double getBaseFee() { return baseFee; }
    public double getServicesFee() { return servicesFee; }
    public double getDiscountAmount() { return discountAmount; }
    public double getTotalBeforeDiscount() { return totalBeforeDiscount; }
    public double getNetPayable() { return netPayable; }

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

    public void save() {
    }
}
