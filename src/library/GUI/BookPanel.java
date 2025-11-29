package library.GUI;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import library.Manager.bookManager;
import library.Manager.dataManager;
import library.Model.Book;

public class BookPanel extends JPanel {

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private bookManager manager;

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
        // search options: ID, Tên, Tác giả, Thể loại
        String[] options = {"ID", "Tên", "Tác giả", "Thể loại"};
        JComboBox<String> searchOption = new JComboBox<>(options);
        searchField = new JTextField(18);
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(searchOption);
        searchPanel.add(searchField);
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

        // load data and populate
        List<Book> loaded = dataManager.loadBooks();
        manager = new bookManager(loaded);
        populateTable();

        // wire search button after manager is ready
        searchButton.addActionListener(e -> {
            String q = searchField.getText().trim();
            String opt = (String) searchOption.getSelectedItem();
            if (q.isEmpty()) {
                populateTable();
                return;
            }
            switch (opt) {
                case "ID":
                    try {
                        int id = Integer.parseInt(q);
                        Book found = manager.findBookById(id);
                        if (found != null) populateTable(List.of(found));
                        else populateTable(List.of());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "ID phải là số nguyên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Tên":
                    populateTable(manager.searchByName(q));
                    break;
                case "Tác giả":
                    populateTable(manager.searchByAuthor(q));
                    break;
                case "Thể loại":
                    populateTable(manager.searchByGenre(q));
                    break;
                default:
                    populateTable();
            }
        });
    }

    private void populateTable() {
        // clear
        tableModel.setRowCount(0);
        for (Book b : manager.getAllBooks()) {
            tableModel.addRow(new Object[] {
                String.format("%02d", b.getBookID()),
                b.getBookName(),
                b.getAuthor(),
                b.getGenre(),
                String.valueOf(b.getPublishYear()),
                String.valueOf(b.getQuantity())
            });
        }
    }

    private void populateTable(List<Book> list) {
        tableModel.setRowCount(0);
        for (Book b : list) {
            tableModel.addRow(new Object[] {
                String.format("%02d", b.getBookID()),
                b.getBookName(),
                b.getAuthor(),
                b.getGenre(),
                String.valueOf(b.getPublishYear()),
                String.valueOf(b.getQuantity())
            });
        }
    }

    private void showAddBookDialog() {
        BookDialog dialog = new BookDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Sách Mới");
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            // add to manager and persist
            String title = dialog.getBookTitle();
            String author = dialog.getAuthor();
            String genre = dialog.getCategory();
            int year;
            try {
                year = Integer.parseInt(dialog.getYear().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Năm xuất bản không hợp lệ. Vui lòng nhập một số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (year <= 0 || year > 2025) {
                JOptionPane.showMessageDialog(this, "Năm xuất bản phải là số dương và không lớn hơn 2025.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int quantity = dialog.getQuantity();
            // Use a default book length/pages since dialog doesn't provide it
            int length = 100;
            manager.addBook(title, genre, author, length, year, quantity);
            dataManager.saveBooks(manager.getBooks());
            populateTable();
            // notify main frame to refresh stats
            java.awt.Window win = SwingUtilities.getWindowAncestor(this);
            if (win instanceof MainFrame) {
                ((MainFrame) win).refreshStats();
            }
        }
    }

    private void showEditBookDialog() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            String idStr = (String) tableModel.getValueAt(selectedRow, 0);
            int id = Integer.parseInt(idStr);
            Book existing = manager.findBookById(id);
            if (existing == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sách tương ứng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String title = existing.getBookName();
            String author = existing.getAuthor();
            String category = existing.getGenre();
            String year = String.valueOf(existing.getPublishYear());
            int quantity = existing.getQuantity();

            BookDialog dialog = new BookDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa Thông Tin Sách");
            dialog.setBookData(title, author, category, year, quantity);
            dialog.setVisible(true);

            if (dialog.isSucceeded()) {
                int newYear;
                try {
                    newYear = Integer.parseInt(dialog.getYear().trim());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Năm xuất bản không hợp lệ. Vui lòng nhập một số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (newYear <= 0 || newYear > 2025) {
                    JOptionPane.showMessageDialog(this, "Năm xuất bản phải là số dương và không lớn hơn 2025.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int newQuantity = dialog.getQuantity();
                // keep existing length
                int length = existing.getBookLength();
                manager.updateBook(id, dialog.getBookTitle(), dialog.getCategory(), dialog.getAuthor(), length, newYear, newQuantity);
                dataManager.saveBooks(manager.getBooks());
                populateTable();
                // notify main frame to refresh stats
                java.awt.Window win = SwingUtilities.getWindowAncestor(this);
                if (win instanceof MainFrame) {
                    ((MainFrame) win).refreshStats();
                }
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
                String idStr = (String) tableModel.getValueAt(selectedRow, 0);
                int id = Integer.parseInt(idStr);
                try {
                    manager.removeBook(id);
                    dataManager.saveBooks(manager.getBooks());
                    populateTable();
                    // notify main frame to refresh stats
                    java.awt.Window win = SwingUtilities.getWindowAncestor(this);
                    if (win instanceof MainFrame) {
                        ((MainFrame) win).refreshStats();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
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
