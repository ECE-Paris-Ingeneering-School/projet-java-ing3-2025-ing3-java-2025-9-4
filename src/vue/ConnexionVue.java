package vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConnexionVue extends JFrame {
    private JTextField emailField;
    private JPasswordField motDePasseField;
    private JButton connexionButton;
    private JButton inscriptionButton;

    public ConnexionVue() {
        setTitle("Connexion - EKSASOTE");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fond d'écran
        JLabel backgroundLabel;
        try {
            BufferedImage image = ImageIO.read(new File("Images/fond_inscription.png"));
            backgroundLabel = new JLabel(new ImageIcon(image));
            backgroundLabel.setLayout(new GridBagLayout());
        } catch (IOException e) {
            System.err.println("Erreur chargement image : " + e.getMessage());
            backgroundLabel = new JLabel();
            backgroundLabel.setLayout(new GridBagLayout());
            backgroundLabel.setOpaque(true);
            backgroundLabel.setBackground(Color.LIGHT_GRAY);
        }

        // Couleurs
        Color bleu = new Color(0x002366);
        Color jaune = new Color(230, 200, 80);

        // Fonts
        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 20);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);

        // Titre
        JLabel titreLabel = new JLabel("EKSASOTE", SwingConstants.CENTER);
        titreLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titreLabel.setForeground(Color.WHITE);
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
            }
        };
        formPanel.setPreferredSize(new Dimension(500, 350));
        formPanel.setBackground(new Color(255, 255, 255, 230));
        formPanel.setBorder(BorderFactory.createLineBorder(jaune, 4, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        // Email
        gbc.gridy = 0;
        formPanel.add(createLabel("Email :", labelFont), gbc);
        gbc.gridy++;
        emailField = new JTextField();
        emailField.setFont(inputFont);
        formPanel.add(emailField, gbc);

        // Mot de passe
        gbc.gridy++;
        formPanel.add(createLabel("Mot de passe :", labelFont), gbc);
        gbc.gridy++;
        motDePasseField = new JPasswordField();
        motDePasseField.setFont(inputFont);
        formPanel.add(motDePasseField, gbc);

        // Panel pour centrer les boutons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        connexionButton = new JButton("Se connecter");
        connexionButton.setFont(buttonFont);
        connexionButton.setBackground(bleu);
        connexionButton.setForeground(Color.WHITE);
        buttonPanel.add(connexionButton);

        inscriptionButton = new JButton("Créer un compte");
        inscriptionButton.setFont(buttonFont);
        inscriptionButton.setBackground(jaune);
        inscriptionButton.setForeground(Color.BLACK);
        buttonPanel.add(inscriptionButton);

        formPanel.add(buttonPanel, gbc);

        // Placement final
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(titreLabel, BorderLayout.NORTH);
        container.add(formPanel, BorderLayout.CENTER);

        backgroundLabel.add(container);
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
}
