package library.GUI;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private StatsPanel statsPanel;

    public MainFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hệ Thống Quản Lý Thư Viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Content Area
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // Add Panels (keep reference to statsPanel so we can refresh)
        statsPanel = new StatsPanel();
        contentPanel.add(statsPanel, "STATS");
        contentPanel.add(new BookPanel(), "BOOKS");
        contentPanel.add(new ReaderPanel(), "READERS");
        contentPanel.add(new LoanPanel(), "LOANS");

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(0, 50, 100));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // App Title in Sidebar
        JLabel appTitle = new JLabel("LIBRARY APP");
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("Arial", Font.BOLD, 20));
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(appTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));

        // Navigation Buttons
        sidebar.add(createNavButton("Trang Chủ", "STATS"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createNavButton("Quản Lý Sách", "BOOKS"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createNavButton("Độc Giả", "READERS"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createNavButton("Mượn / Trả", "LOANS"));

        sidebar.add(Box.createVerticalGlue());

        // Logout Button
        JButton logoutBtn = new JButton("Đăng Xuất");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setBackground(new Color(200, 50, 50));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setMaximumSize(new Dimension(180, 40));
        logoutBtn.addActionListener(e -> logout());
        sidebar.add(logoutBtn);

        return sidebar;
    }

    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setBackground(new Color(0, 80, 160));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addActionListener(e -> {
            // If navigating to stats, refresh the stats panel first so numbers are up-to-date
            if ("STATS".equals(cardName) && statsPanel != null) {
                statsPanel.refreshStats();
            }
            cardLayout.show(contentPanel, cardName);
        });

        return button;
    }

    private void logout() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Allow other panels to request a stats refresh
    public void refreshStats() {
        if (statsPanel != null) {
            statsPanel.refreshStats();
        }
    }
}