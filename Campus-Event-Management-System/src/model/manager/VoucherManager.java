package model.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class VoucherManager {
    private static VoucherManager instance;
    private final List<Voucher> vouchers = new ArrayList<>();

    private VoucherManager() {
        loadVouchersFromFile("Campus-Event-Management-System/database/Voucher.txt");
    }

    public static VoucherManager getInstance() {
        if (instance == null) {
            instance = new VoucherManager();
        }
        return instance;
    }

    private void loadVouchersFromFile(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("voucher_id"))
                    continue;

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String type = parts[2].trim().toLowerCase();
                    double discount = Double.parseDouble(parts[3].trim());
                    boolean isPercentage = Boolean.parseBoolean(parts[4].trim());

                    Voucher voucher = switch (type) {
                        case "studentdiscount" -> new StudentDiscount.StudentDiscountBuilder()
                                .voucherId(id)
                                .voucherName(name)
                                .discountAmount(discount)
                                .percentage(isPercentage)
                                .build();

                        case "seasonaldiscount" -> new SeasonalDiscount.SeasonalDiscountBuilder()
                                .voucherId(id)
                                .voucherName(name)
                                .discountAmount(discount)
                                .percentage(isPercentage)
                                .build();

                        default -> throw new IllegalArgumentException("Unsupported voucher type: " + type);
                    };

                    vouchers.add(voucher);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading vouchers: " + e.getMessage());
        }
    }

    public int getTotalVoucherCount() {
        return vouchers.size();
    }

    public List<Voucher> getAllVouchers() {
        return vouchers;
    }

    public Voucher getVoucherById(String voucherId) {
        return vouchers.stream()
                .filter(s -> s.getVoucherId().equalsIgnoreCase(voucherId))
                .findFirst()
                .orElse(null);
    }
}
