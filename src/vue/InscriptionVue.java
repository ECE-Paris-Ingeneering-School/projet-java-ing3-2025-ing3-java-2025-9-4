package vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InscriptionVue extends JFrame {
    private JTextField nomField, prenomField, emailField;
    private JPasswordField motDePasseField;
    private JButton inscrireButton;

    public InscriptionVue() {
        setTitle("Inscription - EKSASOTE");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fond d'écran
        JLabel backgroundLabel;
        try {
            BufferedImage image = ImageIO.read(new File("Images/fond_inscription.png"));
            backgroundLabel = new JLabel(new ImageIcon(image));
            backgroundLabel.setLayout(new GridBagLayout()); // Centrer le contenu
        } catch (IOException e) {
            System.err.println("Image de fond introuvable : " + e.getMessage());
            backgroundLabel = new JLabel();
            backgroundLabel.setLayout(new GridBagLayout());
            backgroundLabel.setOpaque(true);
            backgroundLabel.setBackground(Color.LIGHT_GRAY);
        }

        // Couleurs
        Color bleu = new Color(0x002366);
        Color jaune = new Color(230, 200, 80);

        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 20);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);

        // Titre sans bandeau, juste en blanc
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
        formPanel.setPreferredSize(new Dimension(600, 450));
        formPanel.setBackground(new Color(255, 255, 255, 230));
        formPanel.setBorder(BorderFactory.createLineBorder(jaune, 4, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // marges
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        gbc.gridy = 0;
        formPanel.add(createLabel("Prénom :", labelFont), gbc);
        gbc.gridy++;
        prenomField = new JTextField();
        prenomField.setFont(inputFont);
        formPanel.add(prenomField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Nom :", labelFont), gbc);
        gbc.gridy++;
        nomField = new JTextField();
        nomField.setFont(inputFont);
        formPanel.add(nomField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Email :", labelFont), gbc);
        gbc.gridy++;
        emailField = new JTextField();
        emailField.setFont(inputFont);
        formPanel.add(emailField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Mot de passe :", labelFont), gbc);
        gbc.gridy++;
        motDePasseField = new JPasswordField();
        motDePasseField.setFont(inputFont);
        formPanel.add(motDePasseField, gbc);

        gbc.gridy++;
        inscrireButton = new JButton("S'inscrire");
        inscrireButton.setFont(buttonFont);
        inscrireButton.setBackground(bleu);
        inscrireButton.setForeground(Color.WHITE);
        formPanel.add(inscrireButton, gbc);

        // Conteneur pour mettre tout ensemble
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setOpaque(false);
        containerPanel.add(titreLabel, BorderLayout.NORTH);
        containerPanel.add(formPanel, BorderLayout.CENTER);

        backgroundLabel.add(containerPanel, new GridBagConstraints());

        setContentPane(backgroundLabel);
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
}
