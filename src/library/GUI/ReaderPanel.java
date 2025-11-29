package library.GUI;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import library.Manager.dataManager;
import library.Manager.userManager;
import library.Model.Reader;

public class ReaderPanel extends JPanel {

    private JTable readerTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchOption;
    private List<Reader> readers;
    private userManager userMgr;

    // Phone pattern matches User.PHONE_REGEX = "\\d{10}"
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{10}");

    public ReaderPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Quản Lý Độc Giả");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 100, 200));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        String[] options = {"Mã ĐG", "Họ Tên", "Năm Sinh", "Giới Tính", "SĐT", "Địa Chỉ"};
        searchOption = new JComboBox<>(options);
        searchField = new JTextField(16);
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(searchOption);
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "Mã ĐG", "Họ Tên", "Ngày Sinh", "Giới Tính", "SĐT", "Địa Chỉ" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        readerTable = new JTable(tableModel);
        readerTable.setRowHeight(25);
        readerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        readerTable.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(readerTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = createButton("Thêm", new Color(0, 150, 0));
        addButton.addActionListener(e -> showAddReaderDialog());

        JButton editButton = createButton("Sửa", new Color(255, 140, 0));
        editButton.addActionListener(e -> showEditReaderDialog());

        JButton deleteButton = createButton("Xóa", new Color(200, 0, 0));
        deleteButton.addActionListener(e -> deleteReader());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // init manager and load data
        userMgr = new userManager();
        readers = userMgr.getAllReaders();
        populateTable();

        // search action with filter option
        searchButton.addActionListener(e -> {
            String q = searchField.getText().trim();
            String opt = (String) searchOption.getSelectedItem();
            if (q.isEmpty()) {
                populateTable();
                return;
            }
            tableModel.setRowCount(0);
            String qLower = q.toLowerCase();
            for (Reader r : readers) {
                switch (opt) {
                    case "Mã ĐG":
                        if (r.getUserID() != null && r.getUserID().toLowerCase().contains(qLower))
                            tableModel.addRow(rowFor(r));
                        break;
                    case "Họ Tên":
                        if (r.getName() != null && r.getName().toLowerCase().contains(qLower))
                            tableModel.addRow(rowFor(r));
                        break;
                    case "Năm Sinh":
                        if (r.getDob() != null) {
                            // if user entered a year number, compare year; otherwise compare full date string
                            try {
                                int year = Integer.parseInt(q);
                                if (r.getDob().getYear() == year) tableModel.addRow(rowFor(r));
                            } catch (NumberFormatException ex) {
                                if (r.getDob().toString().toLowerCase().contains(qLower)) tableModel.addRow(rowFor(r));
                            }
                        }
                        break;
                    case "Giới Tính":
                        if (r.getGender() != null && r.getGender().toLowerCase().contains(qLower))
                            tableModel.addRow(rowFor(r));
                        break;
                    case "SĐT":
                        if (r.getPhoneNumber() != null && r.getPhoneNumber().contains(q))
                            tableModel.addRow(rowFor(r));
                        break;
                    case "Địa Chỉ":
                        if (r.getAddress() != null && r.getAddress().toLowerCase().contains(qLower))
                            tableModel.addRow(rowFor(r));
                        break;
                    default:
                        if ((r.getName() != null && r.getName().toLowerCase().contains(qLower)) ||
                            (r.getUserID() != null && r.getUserID().toLowerCase().contains(qLower))) {
                            tableModel.addRow(rowFor(r));
                        }
                }
            }
        });
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        for (Reader r : readers) {
            tableModel.addRow(new Object[] {
                    r.getUserID(),
                    r.getName(),
                    (r.getDob() == null) ? "" : r.getDob().toString(),
                    (r.getGender() == null) ? "" : r.getGender(),
                    r.getPhoneNumber(),
                    r.getAddress()
            });
        }
    }

    private Object[] rowFor(Reader r) {
        return new Object[] {
            r.getUserID(),
            r.getName(),
            (r.getDob() == null) ? "" : r.getDob().toString(),
            (r.getGender() == null) ? "" : r.getGender(),
            r.getPhoneNumber(),
            r.getAddress()
        };
    }

    private void showAddReaderDialog() {
        ReaderDialog dialog = new ReaderDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Độc Giả Mới");
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            try {
                String name = dialog.getReaderName().trim();
                String dobStr = dialog.getDob().trim();
                String gender = dialog.getGender();
                String phone = dialog.getPhone().trim();
                String address = dialog.getAddress().trim();

                // validate dob
                LocalDate dob = null;
                try {
                    if (!dobStr.isEmpty()) dob = LocalDate.parse(dobStr);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Ngày sinh phải có định dạng YYYY-MM-DD.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // validate phone
                if (!PHONE_PATTERN.matcher(phone).matches()) {
                    JOptionPane.showMessageDialog(this, "SĐT phải là 10 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idNum = dataManager.nextReaderNumber(readers);
                String username = "reader" + idNum;
                String password = "pass123";
                // create via userManager (persists internally)
                Reader r = userMgr.createReader(name, phone, address, username, password, dob, gender);
                // reload list and refresh table
                readers = userMgr.getAllReaders();
                populateTable();

                // notify main frame to refresh stats
                java.awt.Window win = SwingUtilities.getWindowAncestor(this);
                if (win instanceof MainFrame) {
                    ((MainFrame) win).refreshStats();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm độc giả:\n" + ex.toString(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditReaderDialog() {
        int selectedRow = readerTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            Reader target = null;
            for (Reader r : readers) if (r.getUserID().equals(id)) { target = r; break; }
            if (target == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy độc giả.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String name = target.getName();
            String dob = (target.getDob() == null) ? "" : target.getDob().toString();
            String gender = target.getGender();
            String phone = target.getPhoneNumber();
            String address = target.getAddress();

            ReaderDialog dialog = new ReaderDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa Thông Tin Độc Giả");
            dialog.setReaderData(name, dob, gender, phone, address);
            dialog.setVisible(true);

            if (dialog.isSucceeded()) {
                try {
                    String newName = dialog.getReaderName().trim();
                    String newDobStr = dialog.getDob().trim();
                    String newGender = dialog.getGender();
                    String newPhone = dialog.getPhone().trim();
                    String newAddress = dialog.getAddress().trim();

                    LocalDate newDob = null;
                    try {
                        if (!newDobStr.isEmpty()) newDob = LocalDate.parse(newDobStr);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(this, "Ngày sinh phải có định dạng YYYY-MM-DD.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!PHONE_PATTERN.matcher(newPhone).matches()) {
                        JOptionPane.showMessageDialog(this, "SĐT phải là 10 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    target.setName(newName);
                    target.setDob(newDob);
                    target.setGender(newGender);
                    target.setPhoneNumber(newPhone);
                    target.setAddress(newAddress);

                    // persist via manager
                    userMgr.updateUser(target);
                    readers = userMgr.getAllReaders();
                    populateTable();

                    java.awt.Window win = SwingUtilities.getWindowAncestor(this);
                    if (win instanceof MainFrame) {
                        ((MainFrame) win).refreshStats();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi sửa độc giả:\n" + ex.toString(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả để sửa!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteReader() {
        int selectedRow = readerTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa độc giả này?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                Reader target = null;
                for (Reader r : readers) if (r.getUserID().equals(id)) { target = r; break; }
                if (target != null) {
                        try {
                        userMgr.removeUser(target.getUserID());
                        readers = userMgr.getAllReaders();
                        populateTable();

                        java.awt.Window win = SwingUtilities.getWindowAncestor(this);
                        if (win instanceof MainFrame) {
                            ((MainFrame) win).refreshStats();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa độc giả:\n" + ex.toString(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả để xóa!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
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
