package vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InscriptionVue extends JFrame {
    private RoundedTextField nomField, prenomField, emailField;
    private RoundedPasswordField motDePasseField;
    private RoundedButton inscrireButton;

    public InscriptionVue() {
        setTitle("Inscription - EKSASOTE");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- Fond d'écran ---
        BackgroundPanel backgroundPanel = new BackgroundPanel("Images/2.png");

        // --- Couleurs principales ---
        Color bleu = new Color(0x5271ff);  // Bleu doux
        Color rouge = new Color(0xd03838); // Rouge doux

        Color semiTransparentWhite = new Color(255, 255, 255, 220);

        // --- Fonts ---
        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 18);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        // --- Formulaire ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(500, 380)); // Moins haut
        formPanel.setBackground(semiTransparentWhite);
        formPanel.setBorder(BorderFactory.createLineBorder(rouge, 3, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;



        // --- Champs ---
        gbc.gridy = 0;
        formPanel.add(createLabel("👤 Prénom :", labelFont), gbc);
        gbc.gridy++;
        prenomField = new RoundedTextField();
        prenomField.setFont(inputFont);
        formPanel.add(prenomField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("🔠 Nom :", labelFont), gbc);
        gbc.gridy++;
        nomField = new RoundedTextField();
        nomField.setFont(inputFont);
        formPanel.add(nomField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("✉️ Email :", labelFont), gbc);
        gbc.gridy++;
        emailField = new RoundedTextField();
        emailField.setFont(inputFont);
        formPanel.add(emailField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("🔒 Mot de passe :", labelFont), gbc);
        gbc.gridy++;
        motDePasseField = new RoundedPasswordField();
        motDePasseField.setFont(inputFont);
        formPanel.add(motDePasseField, gbc);

        // --- Bouton ---
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        inscrireButton = new RoundedButton("S'inscrire", rouge, Color.WHITE);
        inscrireButton.setFont(buttonFont);

        buttonPanel.add(inscrireButton);
        formPanel.add(buttonPanel, gbc);

        // --- Conteneur principal ---
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(formPanel, BorderLayout.CENTER);

        // --- Placement : centré verticalement ---
        GridBagConstraints containerConstraints = new GridBagConstraints();
        containerConstraints.gridy = 0;
        containerConstraints.anchor = GridBagConstraints.CENTER; // Au centre
        containerConstraints.insets = new Insets(130, 0, 0, 0); // Pas de décalage vertical

        backgroundPanel.add(container, containerConstraints);

        setContentPane(backgroundPanel);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.BLACK);
        return label;
    }

    public String getNom() { return nomField.getText(); }
    public String getPrenom() { return prenomField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getMotDePasse() { return new String(motDePasseField.getPassword()); }

    public void setInscriptionListener(ActionListener listener) {
        inscrireButton.addActionListener(listener);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    // --- Classes personnalisées internes pour arrondi et fond ---
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                System.err.println("Image de fond introuvable : " + e.getMessage());
            }
            setLayout(new GridBagLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    static class RoundedTextField extends JTextField {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    static class RoundedPasswordField extends JPasswordField {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    static class RoundedButton extends JButton {
        private final Color bgColor;
        private final Color fgColor;

        public RoundedButton(String text, Color bgColor, Color fgColor) {
            super(text);
            this.bgColor = bgColor;
            this.fgColor = fgColor;
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(10, 20, 10, 20));
            setForeground(fgColor);
            setBackground(bgColor);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isRollover() ? bgColor.brighter() : bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g2);
            g2.dispose();
        }
    }
}
