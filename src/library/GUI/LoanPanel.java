package library.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LoanPanel extends JPanel {

    private JTable loanTable;
    private DefaultTableModel tableModel;

    public LoanPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel titleLabel = new JLabel("Quản Lý Mượn Trả");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 100, 200));
        add(titleLabel, BorderLayout.NORTH);

        // Tabs for Borrow and Return
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        tabbedPane.addTab("Danh Sách Phiếu Mượn", createListPanel());
        tabbedPane.addTab("Tạo Phiếu Mượn", createBorrowPanel());
        tabbedPane.addTab("Trả Sách", createReturnPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = { "Mã PM", "Độc Giả", "Sách", "Ngày Mượn", "Hạn Trả", "Trạng Thái" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loanTable = new JTable(tableModel);
        loanTable.setRowHeight(25);
        panel.add(new JScrollPane(loanTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Phiếu Mượn"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        JTextField readerIdField = new JTextField(20);
        JTextField bookIdField = new JTextField(20);
        JTextField borrowDateField = new JTextField(20);
        JTextField expireDateField = new JTextField(20);

        // Add components
        addLabelAndField(formPanel, "Mã Độc Giả:", readerIdField, gbc, 0);
        addLabelAndField(formPanel, "Mã Sách:", bookIdField, gbc, 1);
        addLabelAndField(formPanel, "Ngày Mượn (YYYY-MM-DD):", borrowDateField, gbc, 2);
        addLabelAndField(formPanel, "Hạn Trả (YYYY-MM-DD):", expireDateField, gbc, 3);

        // Button
        JButton saveButton = new JButton("Lưu Phiếu Mượn");
        saveButton.setBackground(new Color(0, 150, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(saveButton, gbc);

        // Action
        saveButton.addActionListener(e -> {
            if (readerIdField.getText().trim().isEmpty() || bookIdField.getText().trim().isEmpty() ||
                    borrowDateField.getText().trim().isEmpty() || expireDateField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                tableModel.addRow(new Object[] {
                        "Mới", // Auto-generated ID
                        readerIdField.getText(),
                        bookIdField.getText(),
                        borrowDateField.getText(),
                        expireDateField.getText(),
                        "Đang mượn"
                });
                JOptionPane.showMessageDialog(this, "Tạo phiếu mượn thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                // Clear fields
                readerIdField.setText("");
                bookIdField.setText("");
                borrowDateField.setText("");
                expireDateField.setText("");
            }
        });

        panel.add(formPanel);
        return panel;
    }

    private void addLabelAndField(JPanel panel, String label, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    private JPanel createReturnPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        JTextField loanIdField = new JTextField(15);
        JButton returnButton = new JButton("Xác Nhận Trả Sách");
        returnButton.setBackground(new Color(0, 150, 0));
        returnButton.setForeground(Color.WHITE);

        formPanel.add(new JLabel("Nhập Mã Phiếu Mượn:"));
        formPanel.add(loanIdField);
        formPanel.add(new JLabel("")); // Spacer
        formPanel.add(returnButton);

        returnButton.addActionListener(e -> {
            String loanId = loanIdField.getText().trim();
            if (!loanId.isEmpty()) {
                // Logic to update return date in DB would go here
                JOptionPane.showMessageDialog(this, "Đã trả sách cho phiếu mượn: " + loanId, "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                loanIdField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel);
        return panel;
    }
}
