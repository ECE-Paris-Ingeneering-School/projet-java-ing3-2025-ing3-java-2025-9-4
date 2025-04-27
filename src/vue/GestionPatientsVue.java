package vue;

import modele.Patient;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GestionPatientsVue extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nomField, prenomField, emailField, mdpField;
    private JComboBox<String> roleBox;
    private JCheckBox ancienCheck;
    private JButton ajouterButton, modifierButton, supprimerButton, retourButton;

    public GestionPatientsVue() {
        setTitle("Gestion des Patients - EKSASOTE");
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
        JLabel titreLabel = new JLabel("🏥 Gestion des Patients 📋", SwingConstants.CENTER);
        titreLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titreLabel.setForeground(rouge);
        titreLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 60, 0));

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(550, 400));
        formPanel.setBackground(transparentBlanc);
        formPanel.setBorder(BorderFactory.createLineBorder(rouge, 3, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 2, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        mdpField = new JTextField();
        roleBox = new JComboBox<>(new String[]{"patient", "admin"});
        ancienCheck = new JCheckBox("Ancien patient");

        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        retourButton = new JButton("Retour");
        retourButton.setFont(buttonFont);
        retourButton.setBackground(rouge);
        retourButton.setForeground(Color.WHITE);
        retourButton.addActionListener(e -> dispose()); // Ferme la fenêtre

        gbc.gridy = 0;
        formPanel.add(createLabel("Nom :", labelFont), gbc);
        gbc.gridy++;
        nomField.setFont(inputFont);
        formPanel.add(nomField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Prénom :", labelFont), gbc);
        gbc.gridy++;
        prenomField.setFont(inputFont);
        formPanel.add(prenomField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Email :", labelFont), gbc);
        gbc.gridy++;
        emailField.setFont(inputFont);
        formPanel.add(emailField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Mot de passe :", labelFont), gbc);
        gbc.gridy++;
        mdpField.setFont(inputFont);
        formPanel.add(mdpField, gbc);

        gbc.gridy++;
        formPanel.add(createLabel("Rôle :", labelFont), gbc);
        gbc.gridy++;
        roleBox.setFont(inputFont);
        formPanel.add(roleBox, gbc);

        gbc.gridy++;
        ancienCheck.setFont(labelFont);
        formPanel.add(ancienCheck, gbc);

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
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "Email", "Rôle", "Ancien"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(rouge, 3, true));

        // Conteneur principal
        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);

        GridBagConstraints containerGbc2 = new GridBagConstraints(); // Placement bouton Retour
        containerGbc2.insets = new Insets(20, 20, 20, 20);
        containerGbc2.gridx = 0;
        containerGbc2.gridy = 0;
        containerGbc2.anchor = GridBagConstraints.NORTHWEST; // Ancrage en haut à gauche
        container.add(retourButton, containerGbc2);

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

    public void ajouterLigne(Patient p) {
        tableModel.addRow(new Object[]{
                p.getId(),
                p.getNom(),
                p.getPrenom(),
                p.getEmail(),
                p.getRole(),
                p.isAncien() ? "Oui" : "Non"
        });
    }

    public void viderTable() {
        tableModel.setRowCount(0);
    }

    public void remplirFormulaireDepuisLigne() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            nomField.setText((String) tableModel.getValueAt(row, 1));
            prenomField.setText((String) tableModel.getValueAt(row, 2));
            emailField.setText((String) tableModel.getValueAt(row, 3));
            roleBox.setSelectedItem((String) tableModel.getValueAt(row, 4));
            ancienCheck.setSelected("Oui".equals(tableModel.getValueAt(row, 5)));
        }
    }

    public Patient getPatientFormulaire(int id) {
        return new Patient(
                id,
                nomField.getText(),
                prenomField.getText(),
                emailField.getText(),
                mdpField.getText(),
                ancienCheck.isSelected(),
                (String) roleBox.getSelectedItem()
        );
    }

    public int getIdSelectionne() {
        int row = table.getSelectedRow();
        return row >= 0 ? (int) tableModel.getValueAt(row, 0) : -1;
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

    public void setSelectionListener() {
        table.getSelectionModel().addListSelectionListener(e -> remplirFormulaireDepuisLigne());
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
