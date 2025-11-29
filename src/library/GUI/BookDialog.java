package library.GUI;

import javax.swing.*;
import java.awt.*;

public class BookDialog extends JDialog {

    private JTextField titleField;
    private JTextField authorField;
    private JComboBox<String> categoryComboBox;
    private JTextField yearField;
    private JSpinner quantitySpinner;
    private boolean succeeded;

    public BookDialog(Frame parent, String title) {
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

        // Title
        addLabelAndField(formPanel, "Tên Sách:", titleField = new JTextField(), gbc, 0);

        // Author
        addLabelAndField(formPanel, "Tác Giả:", authorField = new JTextField(), gbc, 1);

        // Category
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Thể Loại:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        String[] categories = { "Khoa học", "Văn học", "Kinh tế", "Công nghệ", "Khác" };
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setBackground(Color.WHITE);
        formPanel.add(categoryComboBox, gbc);

        // Year
        addLabelAndField(formPanel, "Năm XB:", yearField = new JTextField(), gbc, 3);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Số Lượng:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
        formPanel.add(quantitySpinner, gbc);

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

    // Getters for data retrieval
    public String getBookTitle() {
        return titleField.getText();
    }

    public String getAuthor() {
        return authorField.getText();
    }

    public String getCategory() {
        return (String) categoryComboBox.getSelectedItem();
    }

    public String getYear() {
        return yearField.getText();
    }

    public int getQuantity() {
        return (int) quantitySpinner.getValue();
    }

    // Setters for editing
    public void setBookData(String title, String author, String category, String year, int quantity) {
        titleField.setText(title);
        authorField.setText(author);
        categoryComboBox.setSelectedItem(category);
        yearField.setText(year);
        quantitySpinner.setValue(quantity);
    }
}
