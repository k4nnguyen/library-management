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
        tableModel = new DefaultTableModel(columnNames, 0);
        loanTable = new JTable(tableModel);
        loanTable.setRowHeight(25);
        panel.add(new JScrollPane(loanTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        // Placeholder for borrow form
        panel.add(new JLabel("Form Tạo Phiếu Mượn (Đang phát triển)"));
        return panel;
    }

    private JPanel createReturnPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        // Placeholder for return form
        panel.add(new JLabel("Form Trả Sách (Đang phát triển)"));
        return panel;
    }
}
