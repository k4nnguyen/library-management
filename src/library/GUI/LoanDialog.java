package library.GUI;

import javax.swing.*;
import java.awt.*;

public class LoanDialog extends JDialog {

    private JTextField readerIdField;
    private JTextField bookIdField;
    private JTextField borrowDateField;
    private JTextField expireDateField;
    private boolean succeeded;

    public LoanDialog(Frame parent, String title) {
        super(parent, title, true);
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 300);
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

        addLabelAndField(formPanel, "Mã Độc Giả:", readerIdField = new JTextField(), gbc, 0);
        addLabelAndField(formPanel, "Mã Sách:", bookIdField = new JTextField(), gbc, 1);
        addLabelAndField(formPanel, "Ngày Mượn (YYYY-MM-DD):", borrowDateField = new JTextField(), gbc, 2);
        addLabelAndField(formPanel, "Hạn Trả (YYYY-MM-DD):", expireDateField = new JTextField(), gbc, 3);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton saveButton = new JButton("Lưu");
        saveButton.setBackground(new Color(0, 150, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            if (validateFields()) {
                succeeded = true;
                dispose();
            }
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

    private boolean validateFields() {
        if (readerIdField.getText().trim().isEmpty() || bookIdField.getText().trim().isEmpty() ||
                borrowDateField.getText().trim().isEmpty() || expireDateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getReaderId() {
        return readerIdField.getText();
    }

    public String getBookId() {
        return bookIdField.getText();
    }

    public String getBorrowDate() {
        return borrowDateField.getText();
    }

    public String getExpireDate() {
        return expireDateField.getText();
    }
}
