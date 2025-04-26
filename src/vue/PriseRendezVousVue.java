package vue;

import modele.Disponibilite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PriseRendezVousVue extends JFrame {
    private JTextField specialisationField;
    private JTextField villeField;
    private JTextField dateField;
    private JButton rechercherButton;
    private JButton reserverButton;
    private DefaultListModel<Disponibilite> listeModel;
    private JList<Disponibilite> listeDispos;

    public PriseRendezVousVue() {
        setTitle("Prise de Rendez-vous - EKSASOTE");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fond d'écran
        BackgroundPanel backgroundPanel = new BackgroundPanel("Images/5.png");

        // Couleurs
        Color rouge = new Color(208, 56, 56);
        Color grisFonce = new Color(64, 64, 64);
        Color transparentBlanc = new Color(255, 255, 255, 220);

        // Fonts
        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 18);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);

        // Titre avec emojis
        JLabel titreLabel = new JLabel("📅 Prise de Rendez-vous 🗓️", SwingConstants.CENTER);
        titreLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titreLabel.setForeground(rouge);
        titreLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        // Formulaire de recherche
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(550, 300)); // hauteur augmentée
        formPanel.setBackground(transparentBlanc);
        formPanel.setBorder(BorderFactory.createLineBorder(rouge, 3, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        gbc.gridy = 0;
        formPanel.add(createLabel("Spécialisation :", labelFont), gbc);
        gbc.gridy++;
        specialisationField = new JTextField();
        specialisationField.setFont(inputFont);
        formPanel.add(specialisationField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Ville :", labelFont), gbc);
        gbc.gridy++;
        villeField = new JTextField();
        villeField.setFont(inputFont);
        formPanel.add(villeField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Date (yyyy-mm-dd) :", labelFont), gbc);
        gbc.gridy++;
        dateField = new JTextField();
        dateField.setFont(inputFont);
        formPanel.add(dateField, gbc);

        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        rechercherButton = new JButton("Rechercher");
        rechercherButton.setFont(buttonFont);
        rechercherButton.setBackground(grisFonce);
        rechercherButton.setForeground(Color.WHITE);
        buttonPanel.add(rechercherButton);

        reserverButton = new JButton("Prendre ce rendez-vous");
        reserverButton.setFont(buttonFont);
        reserverButton.setBackground(rouge);
        reserverButton.setForeground(Color.WHITE);
        buttonPanel.add(reserverButton);

        formPanel.add(buttonPanel, gbc);

        // Liste des disponibilités
        listeModel = new DefaultListModel<>();
        listeDispos = new JList<>(listeModel);
        listeDispos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listeDispos.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(listeDispos);
        scrollPane.setPreferredSize(new Dimension(550, 300)); // hauteur augmentée
        scrollPane.setBorder(BorderFactory.createLineBorder(rouge, 3, true));

        // Conteneur principal
        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);

        GridBagConstraints containerGbc = new GridBagConstraints();
        containerGbc.insets = new Insets(-50, 20, 20, 20);
        containerGbc.gridx = 0;
        containerGbc.gridy = 0;
        containerGbc.gridwidth = 2;
        containerGbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(titreLabel, containerGbc);

        containerGbc.gridwidth = 1;
        containerGbc.gridy = 1;
        containerGbc.insets = new Insets(0, 20, 20, 20);
        container.add(formPanel, containerGbc);

        containerGbc.gridx = 1;
        container.add(scrollPane, containerGbc);

        backgroundPanel.add(container);

        setContentPane(backgroundPanel);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.BLACK);
        return label;
    }

    public String getSpecialisation() { return specialisationField.getText().trim(); }
    public String getVille() { return villeField.getText().trim(); }
    public String getDate() { return dateField.getText().trim(); }

    public void setRechercherListener(ActionListener listener) { rechercherButton.addActionListener(listener); }
    public void setReserverListener(ActionListener listener) { reserverButton.addActionListener(listener); }

    public void afficherResultats(List<Disponibilite> disponibilites) {
        listeModel.clear();
        if (disponibilites.isEmpty()) {
            afficherMessage("Aucun créneau trouvé.");
        } else {
            for (Disponibilite d : disponibilites) {
                listeModel.addElement(d);
            }
        }
    }

    public Disponibilite getSelection() { return listeDispos.getSelectedValue(); }

    public void afficherMessage(String message) { JOptionPane.showMessageDialog(this, message); }

    public void afficherErreur(String message) { JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE); }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try { backgroundImage = ImageIO.read(new File(imagePath)); }
            catch (IOException e) { System.err.println("Erreur image : " + e.getMessage()); }
            setLayout(new GridBagLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}