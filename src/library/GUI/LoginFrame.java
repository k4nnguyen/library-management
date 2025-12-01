package library.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel messageLabel;

    public LoginFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hệ Thống Quản Lý Thư Viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 320);
        setLocationRelativeTo(null); // Center window
        setResizable(false);

        createLoginPanel();
    }

    private void createLoginPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);


        JLabel headerLabel = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(new Color(0, 100, 200));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));


        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);


        formPanel.add(new JLabel("Tên đăng nhập:"));
        usernameField = new JTextField();
        usernameField.setToolTipText("Nhập tên đăng nhập");
        formPanel.add(usernameField);


        formPanel.add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        passwordField.setToolTipText("Nhập mật khẩu");
        formPanel.add(passwordField);


        messageLabel = new JLabel(" ", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        formPanel.add(messageLabel);


        formPanel.add(new JLabel());


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        loginButton = new JButton("Đăng Nhập");
        loginButton.setBackground(new Color(0, 150, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);

        exitButton = new JButton("Thoát");
        exitButton.setBackground(new Color(200, 0, 0));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFocusPainted(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);


        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        add(mainPanel);


        setupEventHandlers();
    }

    private void setupEventHandlers() {

        loginButton.addActionListener(e -> attemptLogin());


        exitButton.addActionListener(e -> System.exit(0));


        passwordField.addActionListener(e -> attemptLogin());


        usernameField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                messageLabel.setText(" ");
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                messageLabel.setText(" ");
            }
        });
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());


        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        //Demo login, can ket noi vs phg thuc xac thuc khac
        if (username.equals("admin") && password.equals("admin")) {
            openMainApplication(username);
        } else if (username.equals("user") && password.equals("user")) {
            openMainApplication(username);
        } else {
            messageLabel.setText("Sai tên đăng nhập hoặc mật khẩu!");
            passwordField.setText("");
        }
    }

    private void openMainApplication(String username) {

        this.dispose();

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setLoggedInUser(username);
            mainFrame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}