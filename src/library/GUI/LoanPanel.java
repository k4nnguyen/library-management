package library.GUI;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import library.Manager.bookManager;
import library.Manager.dataManager;
import library.Manager.loanManager;
import library.Manager.userManager;
import library.Model.Book;
import library.Model.Loan;
import library.Model.Reader;

public class LoanPanel extends JPanel {

    private JTable loanTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> searchOption;
    private JTextField searchField;

    // borrow form combos
    private JComboBox<String> borrowReaderCombo;
    private JComboBox<String> borrowBookCombo;

    // Managers
    private loanManager loanMgr;
    private bookManager bookMgr;
    private userManager userMgr;
    private List<Loan> loans;

    public LoanPanel() {
        initializeManagers();
        initializeUI();
        loadLoans();
    }

    // New constructor to accept centralized managers
    public LoanPanel(library.Manager.loanManager loanMgr, library.Manager.bookManager bookMgr, library.Manager.userManager userMgr) {
        this.loanMgr = loanMgr;
        this.bookMgr = bookMgr;
        this.userMgr = userMgr;
        initializeUI();
        loadLoans();
    }

    private void initializeManagers() {
        loanMgr = new loanManager();
        bookMgr = new bookManager(dataManager.loadBooks());
        userMgr = new userManager();
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

        // refresh borrow form choices when tab selected
        tabbedPane.addChangeListener(e -> {
            int idx = tabbedPane.getSelectedIndex();
            String title = tabbedPane.getTitleAt(idx);
            if ("Tạo Phiếu Mượn".equals(title)) {
                populateBorrowChoices();
            }
        });

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);

        String[] options = { "Mã PM", "Mã ĐG", "Tên Sách", "Mã Sách", "Ngày Mượn", "Ngày Trả", "Trạng Thái" };
        searchOption = new JComboBox<>(options);
        searchField = new JTextField(16);
        JButton searchButton = new JButton("Tìm kiếm");

        searchPanel.add(new JLabel("Tìm theo:"));
        searchPanel.add(searchOption);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "Mã PM", "Mã ĐG", "Tên ĐG", "Mã Sách", "Tên Sách",
                "Ngày Mượn", "Hạn Trả", "Ngày Trả", "Trạng Thái" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loanTable = new JTable(tableModel);
        loanTable.setRowHeight(25);
        loanTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        loanTable.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(loanTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Search action
        searchButton.addActionListener(e -> performSearch());

        return panel;
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        String option = (String) searchOption.getSelectedItem();

        if (query.isEmpty()) {
            loadLoans();
            return;
        }

        try {
            List<Loan> results = null;

            switch (option) {
                case "Mã PM":
                    Loan loan = loanMgr.findLoanById(query);
                    results = loan != null ? List.of(loan) : List.of();
                    break;
                case "Mã ĐG":
                    results = loanMgr.searchByReaderId(query);
                    break;
                case "Tên Sách":
                    results = loanMgr.searchByBookName(query);
                    break;
                case "Mã Sách":
                    try {
                        int bookId = Integer.parseInt(query);
                        results = loanMgr.searchByBookId(bookId);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Mã sách phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                case "Ngày Mượn":
                    try {
                        LocalDate date = LocalDate.parse(query);
                        results = loanMgr.searchByBorrowDate(date);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Ngày phải có định dạng YYYY-MM-DD!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                case "Ngày Trả":
                    try {
                        LocalDate date = LocalDate.parse(query);
                        results = loanMgr.searchByReturnDate(date);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Ngày phải có định dạng YYYY-MM-DD!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                case "Trạng Thái":
                    results = loanMgr.searchByStatus(query);
                    break;
                default:
                    loadLoans();
                    return;
            }

            displayLoans(results);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadLoans() {
        loans = loanMgr.getAllLoans();
        displayLoans(loans);
    }

    private void displayLoans(List<Loan> loansToDisplay) {
        tableModel.setRowCount(0);
        for (Loan loan : loansToDisplay) {
            tableModel.addRow(new Object[] {
                    loan.getLoanId(),
                    loan.getReader().getUserID(),
                    loan.getReader().getName(),
                    loan.getBook().getBookID(),
                    loan.getBook().getBookName(),
                    loan.getBorrowDate().toString(),
                    loan.getExpireDate().toString(),
                    loan.getReturnDate() != null ? loan.getReturnDate().toString() : "",
                    loan.getStatusString()
            });
        }
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
        borrowReaderCombo = new JComboBox<>();
        borrowReaderCombo.setEditable(true);
        // show at most 5 items in the popup so large lists scroll
        borrowReaderCombo.setMaximumRowCount(5);
        borrowBookCombo = new JComboBox<>();
        borrowBookCombo.setEditable(true);
        borrowBookCombo.setMaximumRowCount(5);
        JTextField borrowDateField = new JTextField(20);
        JTextField expireDateField = new JTextField(20);

        // Set default dates
        borrowDateField.setText(LocalDate.now().toString());
        expireDateField.setText(LocalDate.now().plusDays(14).toString());

        // Add components
        addLabelAndField(formPanel, "Mã Độc Giả:", borrowReaderCombo, gbc, 0);
        addLabelAndField(formPanel, "Mã Sách:", borrowBookCombo, gbc, 1);
        addLabelAndField(formPanel, "Ngày Mượn (YYYY-MM-DD):", borrowDateField, gbc, 2);
        addLabelAndField(formPanel, "Hạn Trả (YYYY-MM-DD):", expireDateField, gbc, 3);

        // Button
        JButton saveButton = new JButton("Lưu phiếu mượn");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(saveButton, gbc);

        // Action
        saveButton.addActionListener(e -> createLoan(borrowReaderCombo, borrowBookCombo,
            borrowDateField, expireDateField));

        panel.add(formPanel);
        return panel;
    }

    private void populateBorrowChoices() {
        // ensure managers
        if (userMgr == null) userMgr = new userManager();
        if (bookMgr == null) bookMgr = new bookManager(dataManager.loadBooks());

        // populate reader combo with "ID - Name" entries
        borrowReaderCombo.removeAllItems();
        for (Reader r : userMgr.getAllReaders()) {
            String item = r.getUserID() + " - " + r.getName();
            borrowReaderCombo.addItem(item);
        }

        // populate book combo with "ID - Name" entries
        borrowBookCombo.removeAllItems();
        for (Book b : bookMgr.getAllBooks()) {
            String item = b.getBookID() + " - " + b.getBookName();
            borrowBookCombo.addItem(item);
        }
    }

    private void createLoan(JComboBox<String> readerCombo, JComboBox<String> bookCombo,
            JTextField borrowDateField, JTextField expireDateField) {
        try {
            String readerRaw = (readerCombo.getEditor().getItem() == null) ? "" : readerCombo.getEditor().getItem().toString().trim();
            String bookRaw = (bookCombo.getEditor().getItem() == null) ? "" : bookCombo.getEditor().getItem().toString().trim();

            // If item is in form "ID - Name", extract ID part
            String readerId = readerRaw;
            if (readerRaw.contains(" - ")) {
                readerId = readerRaw.substring(0, readerRaw.indexOf(" - ")).trim();
            }
            String bookIdStr = bookRaw;
            if (bookRaw.contains(" - ")) {
                bookIdStr = bookRaw.substring(0, bookRaw.indexOf(" - ")).trim();
            }
            String borrowDateStr = borrowDateField.getText().trim();
            String expireDateStr = expireDateField.getText().trim();

            // Validation
            if (readerId.isEmpty() || bookIdStr.isEmpty() ||
                    borrowDateStr.isEmpty() || expireDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Find reader (use injected managers)
            if (userMgr == null) userMgr = new userManager();
            Reader reader = (Reader) userMgr.findUserById(readerId);
            if (reader == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy độc giả với mã: " + readerId, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Find book
            int bookId;
            try {
                bookId = Integer.parseInt(bookIdStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Mã sách phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book book = bookMgr.findBookById(bookId);
            if (book == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy sách với mã: " + bookId, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse dates
            LocalDate borrowDate;
            LocalDate expireDate;
            try {
                borrowDate = LocalDate.parse(borrowDateStr);
                expireDate = LocalDate.parse(expireDateStr);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ngày phải có định dạng YYYY-MM-DD!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create loan
            Loan loan = loanMgr.createLoan(reader, book, borrowDate, expireDate);

            // Success
            JOptionPane.showMessageDialog(this,
                    "Tạo phiếu mượn thành công!\nMã phiếu mượn: " + loan.getLoanId(),
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields
            readerCombo.getEditor().setItem("");
            bookCombo.getEditor().setItem("");
            borrowDateField.setText(LocalDate.now().toString());
            expireDateField.setText(LocalDate.now().plusDays(14).toString());

            // Refresh table
            loadLoans();

        } catch (IllegalStateException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tạo phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addLabelAndField(JPanel panel, String label, JComponent field,
            GridBagConstraints gbc, int row) {
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

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Trả Sách"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField loanIdField = new JTextField(20);
        JButton searchButton = new JButton("Tìm phiếu mượn");
        JButton returnButton = new JButton("Xác nhận trả sách");
        returnButton.setEnabled(false);

        JTextArea infoArea = new JTextArea(8, 30);
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(245, 245, 245));
        infoArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(infoArea);

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Mã Phiếu Mượn:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(loanIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(scrollPane, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(returnButton, gbc);

        // Search action
        final Loan[] foundLoan = { null };
        searchButton.addActionListener(e -> {
            String loanId = loanIdField.getText().trim();
            if (loanId.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập mã phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Loan loan = loanMgr.findLoanById(loanId);
                if (loan == null) {
                    JOptionPane.showMessageDialog(this,
                            "Không tìm thấy phiếu mượn: " + loanId, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    infoArea.setText("");
                    returnButton.setEnabled(false);
                    foundLoan[0] = null;
                } else {
                    // Display loan info
                    StringBuilder info = new StringBuilder();
                    info.append("Mã Phiếu Mượn: ").append(loan.getLoanId()).append("\n");
                    info.append("Độc Giả: ").append(loan.getReader().getName())
                            .append(" (").append(loan.getReader().getUserID()).append(")\n");
                    info.append("Sách: ").append(loan.getBook().getBookName())
                            .append(" (ID: ").append(loan.getBook().getBookID()).append(")\n");
                    info.append("Ngày Mượn: ").append(loan.getBorrowDate()).append("\n");
                    info.append("Hạn Trả: ").append(loan.getExpireDate()).append("\n");
                    info.append("Trạng Thái: ").append(loan.getStatusString()).append("\n");
                    if (loan.getReturnDate() != null) {
                        info.append("Ngày Trả: ").append(loan.getReturnDate()).append("\n");
                    }

                    infoArea.setText(info.toString());
                    foundLoan[0] = loan;
                    returnButton.setEnabled(!loan.isReturned());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tìm phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Return action
        returnButton.addActionListener(e -> {
            if (foundLoan[0] == null) {
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xác nhận trả sách cho phiếu mượn: " + foundLoan[0].getLoanId() + "?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    loanMgr.returnLoan(foundLoan[0]);
                    JOptionPane.showMessageDialog(this,
                            "Đã trả sách thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Clear and refresh
                    loanIdField.setText("");
                    infoArea.setText("");
                    returnButton.setEnabled(false);
                    foundLoan[0] = null;
                    loadLoans();

                } catch (IllegalStateException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                            ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Lỗi khi trả sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(formPanel);
        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }
}
