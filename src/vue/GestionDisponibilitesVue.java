package vue;


import modele.Disponibilite;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionDisponibilitesVue extends JFrame {
    // Ajout des composants JFrame
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> specialisteBox;
    private JComboBox<String> lieuBox;
    private JTextField dateField;
    private JTextField heureField;
    private JCheckBox disponibleCheck;
    private JButton ajouterButton, modifierButton, supprimerButton;

    public GestionDisponibilitesVue() {
        setTitle("Gestion des Créneaux"); // Paramétrage de la fênetre
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tableau d'affichage des créneaux
        tableModel = new DefaultTableModel(new String[]{"ID", "Spécialiste", "Lieu", "Date", "Heure", "Disponible"}, 0);
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        specialisteBox = new JComboBox<>(); // création de champs pour rentrer les informations
        lieuBox = new JComboBox<>();
        dateField = new JTextField();
        heureField = new JTextField();
        disponibleCheck = new JCheckBox("Disponible", true);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5)); // Organisatino des champs
        // Ajout des éléments dans le formulaire
        formPanel.add(new JLabel("Spécialiste :"));
        formPanel.add(specialisteBox);

        formPanel.add(new JLabel("Lieu :"));
        formPanel.add(lieuBox);

        formPanel.add(new JLabel("Date (yyyy-mm-dd) :"));
        formPanel.add(dateField);

        formPanel.add(new JLabel("Heure (hh:mm:ss) :"));
        formPanel.add(heureField);

        formPanel.add(new JLabel("Disponible :"));
        formPanel.add(disponibleCheck);

        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        formPanel.add(ajouterButton); // création des différents bouttons
        formPanel.add(modifierButton);
        formPanel.add(supprimerButton);

        setLayout(new BorderLayout(10, 10));
        add(scroll, BorderLayout.CENTER); // table au centre
        add(formPanel, BorderLayout.SOUTH); // formulaire en bas
    }

    public void setSpecialistes(String[] noms) {
        specialisteBox.setModel(new DefaultComboBoxModel<>(noms));
    } // remplit les boxes avec les specialistes et lieux récuperer par le controleur

    public void setLieux(String[] lieux) {
        lieuBox.setModel(new DefaultComboBoxModel<>(lieux));
    }

    public void ajouterLigne(Disponibilite d) { // Ajout de ligne dans la dispo
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
    } // vide la table

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
}
