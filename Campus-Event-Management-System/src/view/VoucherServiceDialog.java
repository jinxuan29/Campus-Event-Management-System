package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.Service;
import model.Voucher;

public class VoucherServiceDialog extends JDialog {
    private JComboBox<Voucher> voucherCombo;
    private List<JCheckBox> serviceCheckboxes;
    private JButton confirmButton;
    private JButton cancelButton;
    private boolean wasConfirmed = false;

    private Voucher selectedVoucher;
    private List<Service> selectedServices;

    public VoucherServiceDialog(List<Voucher> vouchers, List<Service> services) {
        setTitle("Voucher and Services");
        setModal(true);
        setSize(450, 400); // wider frame for longer text
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- CENTER Panel ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel voucherLabel = new JLabel("Select a voucher (optional):");
        voucherCombo = new JComboBox<>(vouchers.toArray(new Voucher[0]));
        voucherCombo.insertItemAt(null, 0); // allow no selection
        voucherCombo.setSelectedIndex(0);

        // Custom renderer for voucher dropdown
        voucherCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Voucher voucher) {
                    String label = voucher.getVoucherName() + " (";
                    if (voucher.isPercentage()) {
                        label += String.format("%.0f%%", voucher.getDiscountAmount());
                    } else {
                        label += String.format("RM%.2f", voucher.getDiscountAmount());
                    }
                    label += ")";
                    setText(label);
                } else if (value == null && index == 0) {
                    setText("No Voucher");
                }

                return this;
            }
        });

        centerPanel.add(voucherLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(voucherCombo);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel serviceLabel = new JLabel("Select additional services (optional):");
        centerPanel.add(serviceLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel serviceCheckBoxPanel = new JPanel();
        serviceCheckBoxPanel.setLayout(new BoxLayout(serviceCheckBoxPanel, BoxLayout.Y_AXIS));
        serviceCheckboxes = new ArrayList<>();
        for (Service service : services) {
            JCheckBox checkBox = new JCheckBox(service.getServiceName() + " (RM" + service.getServiceFee() + ")");
            checkBox.setActionCommand(service.getServiceId());
            checkBox.setHorizontalAlignment(SwingConstants.LEFT); // Align text left
            checkBox.setAlignmentX(Component.LEFT_ALIGNMENT); // Align box itself left
            serviceCheckboxes.add(checkBox);
            serviceCheckBoxPanel.add(checkBox);
        }

        JScrollPane scrollPane = new JScrollPane(serviceCheckBoxPanel);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        centerPanel.add(scrollPane);

        add(centerPanel, BorderLayout.CENTER);

        // --- SOUTH Panel: Confirm and Cancel buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");

        confirmButton.addActionListener(e -> {
            selectedVoucher = (Voucher) voucherCombo.getSelectedItem();
            selectedServices = new ArrayList<>();

            for (JCheckBox cb : serviceCheckboxes) {
                if (cb.isSelected()) {
                    String id = cb.getActionCommand();
                    for (Service service : services) {
                        if (service.getServiceId().equals(id)) {
                            selectedServices.add(service);
                            break;
                        }
                    }
                }
            }
            wasConfirmed = true;
            dispose();
        });

        cancelButton.addActionListener(e -> {
            wasConfirmed = false;
            dispose();
        });

        Dimension buttonSize = new Dimension(110, 30);
        confirmButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Button Logic ---
        confirmButton.addActionListener(e -> {
            selectedVoucher = (Voucher) voucherCombo.getSelectedItem();
            selectedServices = new ArrayList<>();

            for (JCheckBox cb : serviceCheckboxes) {
                if (cb.isSelected()) {
                    String id = cb.getActionCommand();
                    for (Service service : services) {
                        if (service.getServiceId().equals(id)) {
                            selectedServices.add(service);
                            break;
                        }
                    }
                }
            }
            dispose();
        });

        cancelButton.addActionListener(e -> {
            selectedVoucher = null;
            selectedServices = new ArrayList<>();
            dispose();
        });
    }

    public Voucher getSelectedVoucher() {
        return selectedVoucher;
    }

    public List<Service> getSelectedServices() {
        return selectedServices;
    }

    public boolean wasConfirmed() {
        return wasConfirmed;
    }

}
