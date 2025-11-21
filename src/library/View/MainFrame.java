package library.View;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hệ Thống Quản Lý Thư Viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        //Demo
        JLabel welcomeLabel = new JLabel("Qly cai gi bay gio??", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(0, 100, 200));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.add(welcomeLabel, BorderLayout.CENTER);

        add(panel);
    }
}