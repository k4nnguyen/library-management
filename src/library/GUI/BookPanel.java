package library.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookPanel extends JPanel {

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public BookPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Quản Lý Sách");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 100, 200));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(searchButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "Năm XB", "Số Lượng" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(25);
        bookTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        bookTable.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = createButton("Thêm", new Color(0, 150, 0));
        addButton.addActionListener(e -> showAddBookDialog());

        JButton editButton = createButton("Sửa", new Color(255, 140, 0));
        editButton.addActionListener(e -> showEditBookDialog());

        JButton deleteButton = createButton("Xóa", new Color(200, 0, 0));
        deleteButton.addActionListener(e -> deleteBook());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showAddBookDialog() {
        BookDialog dialog = new BookDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Sách Mới");
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            tableModel.addRow(new Object[] {
                    "Mới", // Placeholder for ID until reload
                    dialog.getBookTitle(),
                    dialog.getAuthor(),
                    dialog.getCategory(),
                    dialog.getYear(),
                    dialog.getQuantity()
            });
        }
    }

    private void showEditBookDialog() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            String title = (String) tableModel.getValueAt(selectedRow, 1);
            String author = (String) tableModel.getValueAt(selectedRow, 2);
            String category = (String) tableModel.getValueAt(selectedRow, 3);
            String year = (String) tableModel.getValueAt(selectedRow, 4);
            int quantity = Integer.parseInt(tableModel.getValueAt(selectedRow, 5).toString());

            BookDialog dialog = new BookDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa Thông Tin Sách");
            dialog.setBookData(title, author, category, year, quantity);
            dialog.setVisible(true);

            if (dialog.isSucceeded()) {
                tableModel.setValueAt(dialog.getBookTitle(), selectedRow, 1);
                tableModel.setValueAt(dialog.getAuthor(), selectedRow, 2);
                tableModel.setValueAt(dialog.getCategory(), selectedRow, 3);
                tableModel.setValueAt(dialog.getYear(), selectedRow, 4);
                tableModel.setValueAt(dialog.getQuantity(), selectedRow, 5);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sách này?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(80, 30));
        return button;
    }
}
