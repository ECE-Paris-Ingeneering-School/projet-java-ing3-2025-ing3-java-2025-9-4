package vue;

import modele.Disponibilite;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GestionDisponibilitesVue extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> specialisteBox;
    private JComboBox<String> lieuBox;
    private JTextField dateField;
    private JTextField heureField;
    private JCheckBox disponibleCheck;
    private JButton ajouterButton, modifierButton, supprimerButton;

    public GestionDisponibilitesVue() {
        setTitle("Gestion des Créneaux - EKSASOTE");
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

        // Titre
        JLabel titreLabel = new JLabel("🗓️ Gestion des Créneaux 📋", SwingConstants.CENTER);
        titreLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titreLabel.setForeground(rouge);
        titreLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 60, 0));

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(550, 400));
        formPanel.setBackground(transparentBlanc);
        formPanel.setBorder(BorderFactory.createLineBorder(rouge, 3, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        specialisteBox = new JComboBox<>();
        lieuBox = new JComboBox<>();
        dateField = new JTextField();
        heureField = new JTextField();
        disponibleCheck = new JCheckBox("Disponible", true);

        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        gbc.gridy = 0;
        formPanel.add(createLabel("Spécialiste :", labelFont), gbc);
        gbc.gridy++;
        specialisteBox.setFont(inputFont);
        formPanel.add(specialisteBox, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Lieu :", labelFont), gbc);
        gbc.gridy++;
        lieuBox.setFont(inputFont);
        formPanel.add(lieuBox, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Date (yyyy-mm-dd) :", labelFont), gbc);
        gbc.gridy++;
        dateField.setFont(inputFont);
        formPanel.add(dateField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Heure (hh:mm:ss) :", labelFont), gbc);
        gbc.gridy++;
        heureField.setFont(inputFont);
        formPanel.add(heureField, gbc);

        gbc.gridy++;
        formPanel.add(disponibleCheck, gbc);

        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        ajouterButton.setFont(buttonFont);
        ajouterButton.setBackground(grisFonce);
        ajouterButton.setForeground(Color.WHITE);
        buttonPanel.add(ajouterButton);

        modifierButton.setFont(buttonFont);
        modifierButton.setBackground(grisFonce);
        modifierButton.setForeground(Color.WHITE);
        buttonPanel.add(modifierButton);

        supprimerButton.setFont(buttonFont);
        supprimerButton.setBackground(rouge);
        supprimerButton.setForeground(Color.WHITE);
        buttonPanel.add(supprimerButton);

        formPanel.add(buttonPanel, gbc);

        // Tableau
        tableModel = new DefaultTableModel(new String[]{"ID", "Spécialiste", "Lieu", "Date", "Heure", "Disponible"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
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

    public void setSpecialistes(String[] noms) {
        specialisteBox.setModel(new DefaultComboBoxModel<>(noms));
    }

    public void setLieux(String[] lieux) {
        lieuBox.setModel(new DefaultComboBoxModel<>(lieux));
    }

    public void ajouterLigne(Disponibilite d) {
        tableModel.addRow(new Object[]{
                d.getId(),
                d.getSpecialiste().getNom(),
                d.getLieu().getVille(),
                d.getDate(),
                d.getHeure(),
                d.isDisponible() ? "Oui" : "Non"
        });
    }

    public void viderTable() {
        tableModel.setRowCount(0);
    }

    public int getSelectionId() {
        int row = table.getSelectedRow();
        return row >= 0 ? (int) tableModel.getValueAt(row, 0) : -1;
    }

    public String getSpecialisteSelectionne() {
        return (String) specialisteBox.getSelectedItem();
    }

    public String getLieuSelectionne() {
        return (String) lieuBox.getSelectedItem();
    }

    public String getDate() {
        return dateField.getText();
    }

    public String getHeure() {
        return heureField.getText();
    }

    public boolean isDisponible() {
        return disponibleCheck.isSelected();
    }

    public void setAjouterListener(ActionListener l) {
        ajouterButton.addActionListener(l);
    }

    public void setModifierListener(ActionListener l) {
        modifierButton.addActionListener(l);
    }

    public void setSupprimerListener(ActionListener l) {
        supprimerButton.addActionListener(l);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                System.err.println("Erreur image : " + e.getMessage());
            }
            setLayout(new GridBagLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null)
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
