import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class LabirintGUI extends JFrame {
    private JPanel labirintPanel;
    private JLabel[][] labirintLabels;
    private JLabel infoLabel;
    private JButton lartButton, poshteButton, majtasButton, djathtasButton;
    private JButton ruajButton, ngarkoButton;
    private int gjatesia = 12;
    private int gjeresia = 23;
    private int[][] laberinto = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1},
            {1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
   private lojtari lojtari;
    public LabirintGUI() {
        setTitle("Loja e Labirintit");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        lojtari = new lojtari(1, 1);  // Pozita fillestare e lojtarit është (1, 1)

        // Inicializo komponentët
        labirintPanel = new JPanel(new GridLayout(gjatesia, gjeresia));
        labirintLabels = new JLabel[gjatesia][gjeresia];

        // Krijo panelin e labirintit
        gjeneroLabirint();
        paraqitLabirint();

        // Paneli i informacionit
        infoLabel = new JLabel("Pikët: 0 | Thesaret: 0");
        add(infoLabel, BorderLayout.NORTH);

        // Butonat e kontrollit
        JPanel kontrollPanel = new JPanel(new GridLayout(2, 3));
        lartButton = new JButton("↑");
        poshteButton = new JButton("↓");
        majtasButton = new JButton("←");
        djathtasButton = new JButton("→");
        ruajButton = new JButton("Ruaj");
        ruajButton.setBounds(500,500,50,50);
        ruajButton.setBackground(Color.LIGHT_GRAY);
        ruajButton.setFocusable(false);
        ngarkoButton = new JButton("Ngarko");
        ngarkoButton.setBounds(500,500,50,50);
        ngarkoButton.setBackground(Color.LIGHT_GRAY);
        ngarkoButton.setFocusable(false);
        lartButton.setBackground(Color.PINK);
        poshteButton.setBackground(Color.PINK);
        majtasButton.setBackground(Color.PINK);
        djathtasButton.setBackground(Color.PINK);

        kontrollPanel.add(new JLabel()); // Pjesë bosh për pozicionim
        kontrollPanel.add(lartButton);
        kontrollPanel.add(new JLabel()); // Pjesë bosh për pozicionim
        kontrollPanel.add(majtasButton);
        kontrollPanel.add(poshteButton);
        kontrollPanel.add(djathtasButton);

        add(kontrollPanel, BorderLayout.SOUTH);

        // Ngjit butonat e veçantë
        add(ruajButton, BorderLayout.WEST);
        add(ngarkoButton, BorderLayout.EAST);

        // Eventet e butonave
        lartButton.addActionListener(e -> leviz("lart"));
        poshteButton.addActionListener(e -> leviz("poshte"));
        majtasButton.addActionListener(e -> leviz("majtas"));
        djathtasButton.addActionListener(e -> leviz("djathtas"));
        ruajButton.addActionListener(e -> ruajLojen());
        ngarkoButton.addActionListener(e -> ngarkoLojen());
    }

    private void gjeneroLabirint() {
        laberinto[1][1] = 2;
        laberinto[10][21] = 3;
        laberinto[3][4] = 4;
        laberinto[7][8] = 5;
        laberinto[4][11] = 4;
        laberinto[5][12] = 4;
        laberinto[3][18] = 5;
        laberinto[11][19] = 4;
        laberinto[11][5] = 4;
        laberinto[11][12] = 5;
        laberinto[1][20] = 4;
        laberinto[7][1] = 4;
        laberinto[9][2] = 5;

    }

    private void paraqitLabirint() {
        labirintPanel.removeAll();
        ImageIcon murIcon = new ImageIcon(new ImageIcon("mur.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        ImageIcon lojtarIcon = new ImageIcon(new ImageIcon("lojtar.jpg").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        ImageIcon thesarIcon = new ImageIcon(new ImageIcon("thesari.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        ImageIcon thesar20Icon = new ImageIcon(new ImageIcon("thesari20.jpg").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        ImageIcon daljeIcon = new ImageIcon(new ImageIcon("dalje.jpg").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        ImageIcon rrugeIcon = new ImageIcon(new ImageIcon("rruge.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        for (int i = 0; i < gjatesia; i++) {
            for (int j = 0; j < gjeresia; j++) {
                labirintLabels[i][j] = new JLabel();
                labirintLabels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                switch (laberinto[i][j]) {
                    case 1:
                        labirintLabels[i][j].setIcon(murIcon);
                        break;
                    case 2:
                        labirintLabels[i][j].setIcon(lojtarIcon);
                        break;
                    case 3:
                        labirintLabels[i][j].setIcon(daljeIcon);
                        break;
                    case 4:
                        labirintLabels[i][j].setIcon(thesarIcon);
                        break;
                    case 5:
                        labirintLabels[i][j].setIcon(thesar20Icon);
                        break;
                    default:
                        labirintLabels[i][j].setIcon(rrugeIcon);
                        break;
                }
                labirintPanel.add(labirintLabels[i][j]);
            }
        }
        add(labirintPanel, BorderLayout.CENTER);
        labirintPanel.revalidate();
        labirintPanel.repaint();
    }
    private boolean ktheGjitheThesaret() {
        // Kontrollon nëse ka ende thesare të papërfunduar
        for (int i = 0; i < gjatesia; i++) {
            for (int j = 0; j < gjeresia; j++) {
                if (laberinto[i][j] == 4 || laberinto[i][j] == 5) {
                    return false; // Ka ende thesare të mbetura
                }
            }
        }
        return true; // Të gjitha thesaret janë mbledhur
    }

    private void leviz(String drejtim) {
        int newX = lojtari.getPozitaX();
        int newY = lojtari.getPozitaY();
        switch (drejtim) {
            case "lart":
                newX--;
                break;
            case "poshte":
                newX++;
                break;
            case "majtas":
                newY--;
                break;
            case "djathtas":
                newY++;
                break;
        }
        if (newX >= 0 && newX < gjatesia && newY >= 0 && newY < gjeresia) {
            if (laberinto[newX][newY] == 1) {
                JOptionPane.showMessageDialog(this, "Humbët! Përplasët me murin! Pikët: " + lojtari.getPiket() + ", Thesaret: " + lojtari.getThesare());
                return;
            } else if (laberinto[newX][newY] == 4) {
                lojtari.shtoThesar();
                lojtari.shtoPike(10);
                laberinto[newX][newY] = 0;
                infoLabel.setText("Pikët: " + lojtari.getPiket() + " | Thesaret: " + lojtari.getThesare());
            } else if (laberinto[newX][newY] == 5) {
                lojtari.shtoThesar();
                lojtari.shtoPike(20);
                laberinto[newX][newY] = 0;
                infoLabel.setText("Pikët: " + lojtari.getPiket() + " | Thesaret: " + lojtari.getThesare());
            } else if (laberinto[newX][newY] == 3) {
                if (lojtari.kaMbledhurTeGjitheThesaret(gjatesia, gjeresia, laberinto)) {
                    JOptionPane.showMessageDialog(this, "Fituat! Keni mbledhur të gjitha thesaret dhe keni " + lojtari.getPiket() + " pikë.");
                } else {
                    JOptionPane.showMessageDialog(this, "Ju nuk keni mbledhur të gjitha thesaret. Vazhdoni lojën!");
                }
            }

            laberinto[lojtari.getPozitaX()][lojtari.getPozitaY()] = 0;
            lojtari.setPozitaX(newX);
            lojtari.setPozitaY(newY);
            laberinto[lojtari.getPozitaX()][lojtari.getPozitaY()] = 2;

            paraqitLabirint();
        }
    }

    private void ruajLojen() {
    String url = "jdbc:mysql://localhost:3306/loja";
    String username = "root";
    String password = "klea1234";

    try (Connection conn = DriverManager.getConnection(url, username, password)) {
        int userId = Session.getUserId();
        String sql = "INSERT INTO game (user_id, piket, thesaret, labirinto, data_ruajtjes) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, lojtari.getPiket());
            stmt.setInt(3, lojtari.getThesare());

            stmt.setString(4, formatLabirinto());
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Loja është ruajtur me sukses!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Ka ndodhur një gabim gjatë ruajtjes së lojës.");
    }
}




    // Funksioni që formaton labirintin në një varg tekstual për t'u ruajtur në bazë
    private String formatLabirinto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[i].length; j++) {
                sb.append(laberinto[i][j]);
                if (j < laberinto[i].length - 1) {
                    sb.append(","); // Ndarja e elementeve
                }
            }
            if (i < laberinto.length - 1) {
                sb.append(";"); // Pjesa që ndan rreshtat
            }
        }
        return sb.toString();
    }

    private void ngarkoLabirintin(String labirintiStr) {
        String[] rows = labirintiStr.split(";");  // Ndarja e rreshtave
        for (int i = 0; i < rows.length; i++) {
            String[] cells = rows[i].split(",");  // Ndarja e kolonave
            for (int j = 0; j < cells.length; j++) {
                laberinto[i][j] = Integer.parseInt(cells[j]);  // Konvertimi i çdo vlerë në numra
            }
        }
    }
    private void ngarkoLojen() {
        String url = "jdbc:mysql://localhost:3306/loja";
        String username = "root";
        String password = "klea1234";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM game ORDER BY data_ruajtjes DESC LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int piketDB = rs.getInt("piket");
                    int thesareDB = rs.getInt("thesaret");
                    String labirintiStr = rs.getString("labirinto");
                    lojtari lojtari = new lojtari(piketDB, thesareDB);
                    ngarkoLabirintin(labirintiStr);
                    infoLabel.setText("Pikët: " + piketDB + " | Thesaret: " + thesareDB);
                    paraqitLabirint();
                } else {
                    JOptionPane.showMessageDialog(this, "Nuk ka asnjë lojë të ruajtur!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ka ndodhur një gabim gjatë ngarkimit të lojës.");
        }
    }














    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LabirintGUI().setVisible(true);
        });
    }
}

