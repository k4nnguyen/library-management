package library.GUI;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

    public StatsPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridLayout(2, 2, 20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createStatCard("Tổng Số Sách", "1,250", new Color(0, 150, 200)));
        add(createStatCard("Độc Giả", "340", new Color(0, 180, 100)));
        add(createStatCard("Đang Mượn", "45", new Color(255, 140, 0)));
        add(createStatCard("Quá Hạn", "5", new Color(200, 0, 0)));
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, JLabel.RIGHT);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 48));
        valueLabel.setForeground(Color.WHITE);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
