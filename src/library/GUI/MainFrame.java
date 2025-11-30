package library.GUI;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import library.Manager.bookManager;
import library.Manager.dataManager;
import library.Manager.loanManager;
import library.Manager.userManager;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private StatsPanel statsPanel;
    private JLabel dateTimeLabel;
    private JLabel welcomeLabel;
    private Timer dateTimeTimer;
    // keep references to panels so we can trigger refreshes
    private BookPanel bookPanel;
    private ReaderPanel readerPanel;

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

        // Initialize central managers and pass into panels
        bookManager bookMgr = new bookManager(dataManager.loadBooks());
        userManager userMgr = new userManager();
        loanManager loanMgr = new loanManager();
        // inject references so loanManager can persist related data
        loanMgr.setBookManager(bookMgr);
        loanMgr.setUserManager(userMgr);

        // Initialize central managers and pass into panels
        bookManager bookMgr = new bookManager(dataManager.loadBooks());
        userManager userMgr = new userManager();
        loanManager loanMgr = new loanManager();
        // inject references so loanManager can persist related data
        loanMgr.setBookManager(bookMgr);
        loanMgr.setUserManager(userMgr);

        // Add Panels (keep reference to statsPanel so we can refresh)
        statsPanel = new StatsPanel();
        contentPanel.add(statsPanel, "STATS");
        // create panels and keep references
        bookPanel = new BookPanel(bookMgr, loanMgr);
        readerPanel = new ReaderPanel(userMgr, loanMgr);
        contentPanel.add(bookPanel, "BOOKS");
        contentPanel.add(readerPanel, "READERS");
        contentPanel.add(new LoanPanel(loanMgr, bookMgr, userMgr), "LOANS");

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

        // Date and Time Display
        dateTimeLabel = new JLabel();
        dateTimeLabel.setForeground(Color.WHITE);
        dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateDateTime();
        sidebar.add(dateTimeLabel);
        // Welcome username below the current time
        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(Box.createRigidArea(new Dimension(0, 6)));
        sidebar.add(welcomeLabel);
        // Welcome username below the current time
        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(Box.createRigidArea(new Dimension(0, 6)));
        sidebar.add(welcomeLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Start timer to update date/time every second
        dateTimeTimer = new Timer(1000, e -> updateDateTime());
        dateTimeTimer.start();

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

    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateTimeLabel.setText(sdf.format(new Date()));
    }

    private void logout() {
        if (dateTimeTimer != null) {
            dateTimeTimer.stop();
        }
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

    /**
     * Update the welcome label with the logged-in username. Call this after a
     * successful login (e.g. from LoginFrame).
     */
    public void setLoggedInUser(String username) {
        if (welcomeLabel != null) {
            if (username == null || username.trim().isEmpty()) {
                welcomeLabel.setText("Welcome");
            } else {
                welcomeLabel.setText("Welcome " + username.trim());
            }
        }
    }

    /**
     * Update the welcome label with the logged-in username. Call this after a
     * successful login (e.g. from LoginFrame).
     */
    public void setLoggedInUser(String username) {
        if (welcomeLabel != null) {
            if (username == null || username.trim().isEmpty()) {
                welcomeLabel.setText("Welcome");
            } else {
                welcomeLabel.setText("Welcome " + username.trim());
            }
        }
    }

    // Allow other panels to request a stats refresh
    public void refreshStats() {
        if (statsPanel != null) {
            statsPanel.refreshStats();
        }
    }

    // Allow other parts of the app to refresh books and readers display
    public void refreshBooks() {
        if (bookPanel != null) bookPanel.refresh();
    }

    public void refreshReaders() {
        if (readerPanel != null) readerPanel.refresh();
    }
}