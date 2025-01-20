import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class KontolleriLojes extends JFrame {
    private int userId;
    private JPanel mainPanel;
    private JButton startButton, quitButton, showScoresButton;
    private JLabel backgroundLabel;

    // Constructor
    public KontolleriLojes() {
        setTitle("KONTROLLERI LOJES");
        setSize(860, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateComponentSizes();
            }
        });
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private void initComponents() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(153, 153, 153));
        add(mainPanel);

        startButton = createButton("START", e -> startGame());
        quitButton = createButton("QUIT GAME", e -> quitGame());
        showScoresButton = createButton("SHIKO PIKET", e -> showScores());

        backgroundLabel = new JLabel();
        backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(backgroundLabel);

        updateComponentSizes();
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("MV Boli", Font.BOLD, 24));
        button.setBackground(Color.lightGray);
        button.setForeground(Color.BLACK);
        button.addActionListener(actionListener);
        mainPanel.add(button);
        return button;
    }

    private void startGame() {
        JOptionPane.showMessageDialog(null, "Game Starting!");
        new LabirintGUI().setVisible(true);
        dispose();
    }

    private void quitGame() {
        System.exit(0);
    }

    private void showScores() {
        String url = "jdbc:mysql://localhost:3306/loja";
        String user = "root";
        String password = "klea1234";
        String sql = "SELECT u.id AS user_id, u.username, GROUP_CONCAT(g.piket ORDER BY g.piket ASC) AS piket " +
                "FROM game g JOIN useri u ON g.user_id = u.id " +
                "GROUP BY u.id ORDER BY u.id";
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Lojtari ID");
        tableModel.addColumn("Username");
        tableModel.addColumn("Piket");
        JTable scoresTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(scoresTable);
        scoresTable.setFillsViewportHeight(true);
        JFrame frame = new JFrame("Piket e lojtareve");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String scores = rs.getString("piket");
                tableModel.addRow(new Object[]{userId, username, scores});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Gabim gjatë tërheqjes së pikëve.", "Gabim", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateComponentSizes() {
        int width = getWidth();
        int height = getHeight();

        mainPanel.setBounds(0, 0, width, height);

        ImageIcon backgroundImage = new ImageIcon("backgroundi.png");
        Image image = backgroundImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(image));
        backgroundLabel.setBounds(0, 0, width, height);

        startButton.setBounds(width / 3, height / 3, width / 3, height / 10);
        startButton.setFocusable(false);
        quitButton.setBounds(width / 3, height / 2, width / 3, height / 10);
        quitButton.setFocusable(false);
        showScoresButton.setBounds(width / 3, (height * 2) / 3, width / 3, height / 10);
        showScoresButton.setFocusable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KontolleriLojes frame = new KontolleriLojes();
            frame.setVisible(true);
        });
    }
}
