package library.GUI;

import javax.swing.*;
import java.awt.*;

public class ReaderDialog extends JDialog {

    private JTextField nameField;
    private JTextField dobField;
    private JComboBox<String> genderComboBox;
    private JTextField phoneField;
    private JTextField addressField;
    private boolean succeeded;

    public ReaderDialog(Frame parent, String title) {
        super(parent, title, true);
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 350);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Name
        addLabelAndField(formPanel, "Họ Tên:", nameField = new JTextField(), gbc, 0);

        // DOB
        addLabelAndField(formPanel, "Ngày Sinh:", dobField = new JTextField(), gbc, 1);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Giới Tính:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        String[] genders = { "Nam", "Nữ", "Khác" };
        genderComboBox = new JComboBox<>(genders);
        genderComboBox.setBackground(Color.WHITE);
        formPanel.add(genderComboBox, gbc);

        // Phone
        addLabelAndField(formPanel, "SĐT:", phoneField = new JTextField(), gbc, 3);

        // Address
        addLabelAndField(formPanel, "Địa Chỉ:", addressField = new JTextField(), gbc, 4);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton saveButton = new JButton("Lưu");
        saveButton.setBackground(new Color(0, 150, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            succeeded = true;
            dispose();
        });

        JButton cancelButton = new JButton("Hủy");
        cancelButton.setBackground(new Color(200, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> {
            succeeded = false;
            dispose();
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addLabelAndField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    // Getters
    public String getReaderName() {
        return nameField.getText();
    }

    public String getDob() {
        return dobField.getText();
    }

    public String getGender() {
        return (String) genderComboBox.getSelectedItem();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    // Setters
    public void setReaderData(String name, String dob, String gender, String phone, String address) {
        nameField.setText(name);
        dobField.setText(dob);
        genderComboBox.setSelectedItem(gender);
        phoneField.setText(phone);
        addressField.setText(address);
    }
}
