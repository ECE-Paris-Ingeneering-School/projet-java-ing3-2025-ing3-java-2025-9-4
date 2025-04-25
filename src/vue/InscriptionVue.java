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

        // Conteneur titre + formulaire
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setOpaque(false);

        // Titre
        JLabel titreLabel = new JLabel("EKSASOTE", SwingConstants.CENTER);
        titreLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titreLabel.setForeground(Color.WHITE);
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Grand Formulaire
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 20, 20));
        formPanel.setPreferredSize(new Dimension(600, 400));
        formPanel.setBackground(new Color(255, 255, 255, 230));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        Font labelFont = new Font("SansSerif", Font.PLAIN, 20);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 20);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);

        JLabel prenomLabel = new JLabel("Prénom:");
        prenomLabel.setFont(labelFont);
        prenomField = new JTextField();
        prenomField.setFont(inputFont);
        formPanel.add(prenomLabel);
        formPanel.add(prenomField);

        JLabel nomLabel = new JLabel("Nom:");
        nomLabel.setFont(labelFont);
        nomField = new JTextField();
        nomField.setFont(inputFont);
        formPanel.add(nomLabel);
        formPanel.add(nomField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        emailField = new JTextField();
        emailField.setFont(inputFont);
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        JLabel mdpLabel = new JLabel("Mot de passe:");
        mdpLabel.setFont(labelFont);
        motDePasseField = new JPasswordField();
        motDePasseField.setFont(inputFont);
        formPanel.add(mdpLabel);
        formPanel.add(motDePasseField);

        inscrireButton = new JButton("S'inscrire");
        inscrireButton.setFont(buttonFont);
        formPanel.add(new JLabel()); // espace vide
        formPanel.add(inscrireButton);

        containerPanel.add(titreLabel, BorderLayout.NORTH);
        containerPanel.add(formPanel, BorderLayout.CENTER);

        backgroundLabel.add(containerPanel, new GridBagConstraints());

        setContentPane(backgroundLabel);
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
