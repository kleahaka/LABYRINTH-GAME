import java.awt.*;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;

public class LOGIN extends JFrame {

    // Deklarimi i komponentëve GUI
    private JPanel jPanel1;
    private JLabel jLabel2;
    private JLabel username;
    private JButton lg;
    private JTextField emri;
    private JLabel password;
    private JPasswordField pass;
    private JLabel jLabel1;
    private JButton SIGNUP;

    public LOGIN() {
            initComponents();
        }

        private void initComponents() {
            // Inicializimi i komponentëv
            jPanel1 = new JPanel();
        jLabel2 = new JLabel();
        username = new JLabel();
        lg = new JButton();
        emri = new JTextField();
        password = new JLabel();
        pass = new JPasswordField();
        jLabel1 = new JLabel();
        SIGNUP = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Cilësimi i panelit dhe ngjyrave
        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setBounds(0, 0, 300, 900);
        getContentPane().add(jPanel1);

        // Cilësimi i etiketës "LogIn Page"
        jLabel2.setFont(new java.awt.Font("Jokerman", 0, 24));
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("  LogIn Page");
        jLabel2.setBounds(440, 40, 155, 34);
        getContentPane().add(jLabel2);

        // Cilësimi i etiketës "Username"
        username.setFont(new java.awt.Font("Sitka Subheading", 0, 24));
        username.setForeground(new java.awt.Color(0, 102, 102));
        username.setText("  USERNAME");
        username.setBounds(330, 120, 168, 39);
        getContentPane().add(username);

        // Cilësimi i butonit për logim
        lg.setBackground(new java.awt.Color(0, 153, 153));
        lg.setForeground(new java.awt.Color(255, 255, 255));
        lg.setText("LOG IN");
        lg.setBorder(BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 51), new java.awt.Color(0, 51, 51)));
        lg.setBounds(330, 250, 135, 31);
        lg.addActionListener(evt -> lgActionPerformed());
        getContentPane().add(lg);

        // Cilësimi i fushës për emrin e përdoruesit
        emri.setForeground(new java.awt.Color(0, 153, 153));
        emri.setBounds(530, 120, 134, 39);
        getContentPane().add(emri);

        // Cilësimi i etiketës "Password"
        password.setFont(new java.awt.Font("Sitka Banner", 0, 24));
        password.setForeground(new java.awt.Color(0, 102, 102));
        password.setText("  PASSWORD");
        password.setBounds(330, 180, 168, 39);
        getContentPane().add(password);

        // Cilësimi i fushës për fjalëkalimin
        pass.setBounds(530, 180, 134, 42);
        getContentPane().add(pass);

        // Cilësimi i etiketës për regjistrimin e përdoruesve të rinj
        jLabel1.setText("I don't have an account");
        jLabel1.setBounds(330, 330, 140, 30);
        jLabel1.setForeground(Color.GREEN);
        getContentPane().add(jLabel1);

        // Cilësimi i butonit për regjistrim
        SIGNUP.setBackground(new java.awt.Color(0, 153, 153));
        SIGNUP.setForeground(new java.awt.Color(255, 255, 255));
        SIGNUP.setText("SIGN UP");
        SIGNUP.setBounds(540, 330, 110, 30);
        SIGNUP.addActionListener(evt -> SIGNUPActionPerformed());
        getContentPane().add(SIGNUP);

        setSize(600, 400);
        setLocationRelativeTo(null);  // Vendosja në qendër të ekranit
    }

    // Funksioni për të bërë hash të fjalëkalimit
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void lgActionPerformed() {
        String username = emri.getText();
        String password = new String(pass.getPassword());
        String url = "jdbc:mysql://localhost:3306/loja";
        String dbUsername = "root";
        String dbPassword = "klea1234";
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM useri WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPasswordHash = rs.getString("password");
                if (checkPassword(password, storedPasswordHash)) {
                    int userId = rs.getInt("id");
                    Session.setUserId(userId);
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    KontolleriLojes kontolleriLojes = new KontolleriLojes();
                    kontolleriLojes.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid password.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Përdoruesi nuk ekziston. Të lutem bëj Sign Up.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }








    // Funksioni për të kontrolluar password-in
    private boolean checkPassword(String enteredPassword, String storedHash) {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return hashedEnteredPassword.equals(storedHash);
    }

    // Funksioni për regjistrimin e përdoruesve të rinj
    private void SIGNUPActionPerformed() {
        String userInputUsername = emri.getText();
        String userInputPassword = new String(pass.getPassword());

        if (userInputUsername.isEmpty() || userInputPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Të gjitha fushat duhet të plotësohen!");
            return;
        }
        String url = "jdbc:mysql://localhost:3306/loja";
        String dbUsername = "root";
        String dbPassword = "klea1234";
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String hashedPassword = hashPassword(userInputPassword);
            String sql = "INSERT INTO useri (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userInputUsername);
            pstmt.setString(2, hashedPassword);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Regjistrimi u krye me sukses!");
            } else {
                JOptionPane.showMessageDialog(this, "Regjistrimi dështoi.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LOGIN frame = new LOGIN();
            frame.setVisible(true);

        });
    }
}
