package vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConnexionVue extends JFrame {
    private RoundedTextField emailField;
    private RoundedPasswordField motDePasseField;
    private RoundedButton connexionButton;
    private RoundedButton inscriptionButton;

    public ConnexionVue() {
        setTitle("Connexion - EKSASOTE");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Fond d'écran ---
        BackgroundPanel backgroundLabel = new BackgroundPanel("Images/1.png");

        // --- Couleurs principales ---
        Color bleu = new Color(0x5271ff);  // Bleu doux
        Color rouge = new Color(0xd03838); // Rouge doux

        Color semiTransparentWhite = new Color(255, 255, 255, 220);

        // --- Fonts ---
        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 18);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        // --- Formulaire  ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(500, 300));
        formPanel.setBackground(semiTransparentWhite);
        formPanel.setBorder(BorderFactory.createLineBorder(rouge, 3, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        // --- Email ---
        gbc.gridy = 0;
        formPanel.add(createLabel("✉️ Email :", labelFont), gbc);
        gbc.gridy++;
        emailField = new RoundedTextField();
        emailField.setFont(inputFont);
        formPanel.add(emailField, gbc);

        // --- Mot de passe ---
        gbc.gridy++;
        formPanel.add(createLabel("🔒 Mot de passe :", labelFont), gbc);
        gbc.gridy++;
        motDePasseField = new RoundedPasswordField();
        motDePasseField.setFont(inputFont);
        formPanel.add(motDePasseField, gbc);

        // --- Boutons ---
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        connexionButton = new RoundedButton("Se connecter", rouge, Color.WHITE);
        inscriptionButton = new RoundedButton("Créer un compte", bleu, Color.BLACK);

        connexionButton.setFont(buttonFont);
        inscriptionButton.setFont(buttonFont);

        buttonPanel.add(connexionButton);
        buttonPanel.add(inscriptionButton);

        formPanel.add(buttonPanel, gbc);

        // --- Conteneur principal ---
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(formPanel, BorderLayout.CENTER);

        // --- Placement avec GridBagConstraints pour DESCENDRE le bloc ---
        GridBagConstraints containerConstraints = new GridBagConstraints();
        containerConstraints.gridy = 0;
        containerConstraints.anchor = GridBagConstraints.NORTH; // On ancre en haut
        containerConstraints.insets = new Insets(380, 0, 0, 0); // Décalage vertical supplémentaire

        backgroundLabel.add(container, containerConstraints);

        setContentPane(backgroundLabel);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.BLACK);
        return label;
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getMotDePasse() {
        return new String(motDePasseField.getPassword());
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void setConnexionListener(ActionListener listener) {
        connexionButton.addActionListener(listener);
    }

    public void setInscriptionListener(ActionListener listener) {
        inscriptionButton.addActionListener(listener);
    }

    // --- Panels et Composants Personnalisés ---

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
