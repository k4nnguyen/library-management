package library.GUI;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import library.Manager.bookManager;
import library.Manager.dataManager;
import library.Manager.loanManager;
import library.Model.Book;
import library.Model.Loan;
import library.Model.Reader;

public class StatsPanel extends JPanel {

    private JLabel totalBooksLabel;
    private JLabel readersLabel;
    private JLabel loansLabel;
    private JLabel overdueLabel;

    public StatsPanel() {
        initializeUI();
        refreshStats();
    }

    private void initializeUI() {
        setLayout(new GridLayout(2, 2, 20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        totalBooksLabel = createValueLabel();
        readersLabel = createValueLabel();
        loansLabel = createValueLabel();
        overdueLabel = createValueLabel();

        add(createStatCard("Tổng Số Sách", totalBooksLabel, new Color(0, 150, 200)));
        add(createStatCard("Độc Giả", readersLabel, new Color(0, 180, 100)));
        add(createStatCard("Đang Mượn", loansLabel, new Color(255, 140, 0)));
        add(createStatCard("Quá Hạn", overdueLabel, new Color(200, 0, 0)));
    }

    private JLabel createValueLabel() {
        JLabel valueLabel = new JLabel("0", JLabel.RIGHT);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 48));
        valueLabel.setForeground(Color.WHITE);
        return valueLabel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    public void refreshStats() {
        // Load persisted data
        List<Book> books = dataManager.loadBooks();
        List<Reader> readers = dataManager.loadReaders();
        List<Loan> loans = dataManager.loadLoans();

        // Compute stats
        bookManager bm = new bookManager(books);
        loanManager lm = new loanManager(loans);

        int totalBooks = bm.getTotalBooks();
        int totalReaders = (readers == null) ? 0 : readers.size();
        int activeLoans = lm.getActiveLoansCount(); // Only count active (not returned) loans
        int totalOverdue = lm.getOverdueLoansCount();

        totalBooksLabel.setText(String.valueOf(totalBooks));
        readersLabel.setText(String.valueOf(totalReaders));
        loansLabel.setText(String.valueOf(activeLoans));
        overdueLabel.setText(String.valueOf(totalOverdue));
    }
}
