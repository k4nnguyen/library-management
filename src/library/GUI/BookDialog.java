package library.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BookDialog extends JDialog {

    private JTextField titleField;
    private JTextField authorField;
    private JComboBox<String> categoryComboBox;
    private JTextField yearField;
    private JTextField customCategoryField;
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

        // Custom category field, hidden until "Khác" selected
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        // placeholder label for alignment
        JLabel customLabel = new JLabel("");
        formPanel.add(customLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        customCategoryField = new JTextField();
        customCategoryField.setVisible(false);
        formPanel.add(customCategoryField, gbc);

        // Listen for selection changes to show/hide custom field
        categoryComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String sel = (String) categoryComboBox.getSelectedItem();
                    boolean isOther = "Khác".equals(sel);
                    customCategoryField.setVisible(isOther);
                    customLabel.setText(isOther ? "Nhập thể loại:" : "");
                    // revalidate/repaint so layout updates
                    customCategoryField.getParent().revalidate();
                    customCategoryField.getParent().repaint();
                    if (isOther) {
                        customCategoryField.requestFocusInWindow();
                    }
                }
            }
        });

        // Year (moved down so it doesn't overlap the custom category row)
        addLabelAndField(formPanel, "Năm XB:", yearField = new JTextField(), gbc, 4);

        // Quantity (moved down one row)
        gbc.gridx = 0;
        gbc.gridy = 5;
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
        String sel = (String) categoryComboBox.getSelectedItem();
        if ("Khác".equals(sel)) {
            String custom = customCategoryField.getText().trim();
            return custom.isEmpty() ? sel : custom;
        }
        return sel;
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
        // If category matches one of the options, select it; otherwise select "Khác" and fill custom field
        boolean matched = false;
        for (int i = 0; i < categoryComboBox.getItemCount(); i++) {
            if (categoryComboBox.getItemAt(i).equals(category)) {
                categoryComboBox.setSelectedIndex(i);
                matched = true;
                break;
            }
        }
        if (!matched) {
            categoryComboBox.setSelectedItem("Khác");
            customCategoryField.setText(category == null ? "" : category);
            customCategoryField.setVisible(true);
        } else {
            customCategoryField.setText("");
            customCategoryField.setVisible(false);
        }
        yearField.setText(year);
        quantitySpinner.setValue(quantity);
    }
}
