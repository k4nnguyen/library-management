package library.GUI;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import library.Manager.BookManager;
import library.Manager.DataManager;
import library.Manager.LoanManager;
import library.Model.Book;

public class BookPanel extends JPanel {

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private BookManager manager;
    private LoanManager loanMgr;
    private JComboBox<String> genreCombo; // moved to field so we can refresh it dynamically

    public BookPanel() {
        initializeUI();
    }

    // New constructor to accept centralized managers
    public BookPanel(BookManager manager) {
        this(manager, null);
    }

    public BookPanel(BookManager manager, LoanManager loanMgr) {
        this.manager = manager;
        this.loanMgr = loanMgr;
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
        String[] options = { "ID", "Tên", "Tác giả", "Thể Loại" };
        JComboBox<String> searchOption = new JComboBox<>(options);
        searchField = new JTextField(18);
        // genre combo (hidden unless 'Thể Loại' selected)
        genreCombo = new JComboBox<>();
        genreCombo.setVisible(false);
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Tìm theo:"));
        searchPanel.add(searchOption);
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(genreCombo);
        searchPanel.add(searchButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table (add 'Đang Mượn' column to show how many copies currently borrowed)
        String[] columnNames = { "Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "Năm XB", "Đang Mượn", "Số Lượng" };
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

        // load data and populate (use injected manager if provided)
        if (manager == null) {
            List<Book> loaded = DataManager.loadBooks();
            manager = new BookManager(loaded);
        }
        if (loanMgr == null) {
            loanMgr = new LoanManager();
        }
        populateTable();

        // wire search button after manager is ready
        // when search option changes, show/hide the genre combo
        searchOption.addActionListener(ev -> {
            String opt = (String) searchOption.getSelectedItem();
            if ("Thể Loại".equals(opt)) {
                // refresh genres from manager and show combo
                refreshGenreCombo();
                searchField.setVisible(false);
                genreCombo.setVisible(true);
            } else {
                genreCombo.setVisible(false);
                searchField.setVisible(true);
            }
            searchPanel.revalidate();
            searchPanel.repaint();
        });

        searchButton.addActionListener(e -> {
            String opt = (String) searchOption.getSelectedItem();
            if ("Thể Loại".equals(opt)) {
                String genre = (String) genreCombo.getSelectedItem();
                if (genre == null || genre.trim().isEmpty() || "Tất cả".equals(genre)) {
                    populateTable();
                } else {
                    populateTable(manager.searchByGenre(genre));
                }
                return;
            }

            String q = searchField.getText().trim();
            if (q.isEmpty()) {
                populateTable();
                return;
            }
            switch (opt) {
                case "ID":
                    try {
                        int id = Integer.parseInt(q);
                        Book found = manager.findBookById(id);
                        if (found != null)
                            populateTable(List.of(found));
                        else
                            populateTable(List.of());
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
                default:
                    populateTable();
            }
        });
    }

    private void populateTable() {
        // clear
        tableModel.setRowCount(0);
        for (Book b : manager.getAllBooks()) {
            // count active loans for this book
            int borrowed = 0;
            try {
                borrowed = (int) loanMgr.searchByBookId(b.getBookID()).stream().filter(l -> !l.isReturned()).count();
            } catch (Exception ex) {
                borrowed = 0;
            }
            tableModel.addRow(new Object[] {
                    String.format("%02d", b.getBookID()),
                    b.getBookName(),
                    b.getAuthor(),
                    b.getGenre(),
                    String.valueOf(b.getPublishYear()),
                    String.valueOf(borrowed),
                    String.valueOf(b.getQuantity())
            });
        }
    }
    private void populateTable(List<Book> list) {
        tableModel.setRowCount(0);
        for (Book b : list) {
            int borrowed = 0;
            try {
                borrowed = (int) loanMgr.searchByBookId(b.getBookID()).stream().filter(l -> !l.isReturned()).count();
            } catch (Exception ex) {
                borrowed = 0;
            }
            tableModel.addRow(new Object[] {
                    String.format("%02d", b.getBookID()),
                    b.getBookName(),
                    b.getAuthor(),
                    b.getGenre(),
                    String.valueOf(b.getPublishYear()),
                    String.valueOf(borrowed),
                    String.valueOf(b.getQuantity())
            });
        }
    }

    // Refresh the genre combo from current manager data.
    private void refreshGenreCombo() {
        if (genreCombo == null) return;
        genreCombo.removeAllItems();
        List<String> genres = manager.getAllBooks().stream()
                .map(Book::getGenre)
                .filter(g -> g != null && !g.trim().isEmpty())
                .map(String::trim)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        genreCombo.addItem("Tất cả");
        for (String g : genres) genreCombo.addItem(g);
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
                JOptionPane.showMessageDialog(this, "Năm xuất bản không hợp lệ. Vui lòng nhập một số hợp lệ.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (year <= 0 || year > 2025) {
                JOptionPane.showMessageDialog(this, "Năm xuất bản phải là số dương và không lớn hơn 2025.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int quantity = dialog.getQuantity();
            // Use a default book length/pages since dialog doesn't provide it
            int length = 100;
            manager.addBook(title, genre, author, length, year, quantity);
            DataManager.saveBooks(manager.getBooks());
            // refresh UI and genre filter so new genres appear immediately
            refreshGenreCombo();
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
                    JOptionPane.showMessageDialog(this, "Năm xuất bản không hợp lệ. Vui lòng nhập một số hợp lệ.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (newYear <= 0 || newYear > 2025) {
                    JOptionPane.showMessageDialog(this, "Năm xuất bản phải là số dương và không lớn hơn 2025.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int newQuantity = dialog.getQuantity();
                    // Prevent setting quantity lower than current active borrowed count
                    int activeBorrowed = 0;
                    try {
                        if (loanMgr != null) {
                            activeBorrowed = (int) loanMgr.searchByBookId(id).stream().filter(l -> !l.isReturned()).count();
                        }
                    } catch (Exception ex) {
                        activeBorrowed = 0;
                    }
                    if (newQuantity < activeBorrowed) {
                        JOptionPane.showMessageDialog(this,
                                "Không thể giảm số lượng sách xuống " + newQuantity + " vì đang có " + activeBorrowed
                                        + " lượt mượn chưa trả.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                // keep existing length
                int length = existing.getBookLength();
                manager.updateBook(id, dialog.getBookTitle(), dialog.getCategory(), dialog.getAuthor(), length, newYear,
                        newQuantity);
                DataManager.saveBooks(manager.getBooks());
                // refresh genres in case category changed
                refreshGenreCombo();
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
                // check active loans for this book
                int activeLoans = 0;
                try {
                    activeLoans = (int) loanMgr.searchByBookId(id).stream().filter(l -> !l.isReturned()).count();
                } catch (Exception ex) {
                    activeLoans = 0;
                }
                if (activeLoans > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa sách này vì còn " + activeLoans + " lượt mượn đang hoạt động.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    manager.removeBook(id);
                    DataManager.saveBooks(manager.getBooks());
                    // refresh genres in case removal changed available categories
                    refreshGenreCombo();
                    populateTable();
                    // notify main frame to refresh stats
                    java.awt.Window win = SwingUtilities.getWindowAncestor(this);
                    if (win instanceof MainFrame) {
                        ((MainFrame) win).refreshStats();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại: " + ex.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
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

    // Allow external callers (e.g., MainFrame) to request a refresh
    public void refresh() {
        // repopulate table from current manager state
        populateTable();
    }
}
