package vue;

import modele.Specialiste;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionSpecialistesVue extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nomField, specialisationField, qualificationField;
    private JButton ajouterButton, modifierButton, supprimerButton;

    public GestionSpecialistesVue() {
        setTitle("Gestion des Spécialistes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Spécialisation", "Qualification"}, 0);
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        // Formulaire
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        formPanel.add(nomField);

        formPanel.add(new JLabel("Spécialisation :"));
        specialisationField = new JTextField();
        formPanel.add(specialisationField);

        formPanel.add(new JLabel("Qualification :"));
        qualificationField = new JTextField();
        formPanel.add(qualificationField);

        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        formPanel.add(ajouterButton);
        formPanel.add(modifierButton);
        formPanel.add(supprimerButton);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    public void ajouterLigne(Specialiste s) {
        tableModel.addRow(new Object[]{s.getId(), s.getNom(), s.getSpecialisation(), s.getQualification()});
    }

    public void viderTable() {
        tableModel.setRowCount(0);
    }

    public Specialiste getSpecialisteFormulaire() {
        return new Specialiste(0, getNom(), getSpecialisation(), getQualification());
    }

    public void remplirFormulaireDepuisLigne() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            nomField.setText((String) tableModel.getValueAt(row, 1));
            specialisationField.setText((String) tableModel.getValueAt(row, 2));
            qualificationField.setText((String) tableModel.getValueAt(row, 3));
        }
    }

    public int getIdSelectionne() {
        int row = table.getSelectedRow();
        return row >= 0 ? (int) tableModel.getValueAt(row, 0) : -1;
    }

    public Specialiste getSpecialisteSelectionne() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return new Specialiste(
                (int) tableModel.getValueAt(row, 0),
                (String) tableModel.getValueAt(row, 1),
                (String) tableModel.getValueAt(row, 2),
                (String) tableModel.getValueAt(row, 3)
        );
    }

    public String getNom() { return nomField.getText(); }
    public String getSpecialisation() { return specialisationField.getText(); }
    public String getQualification() { return qualificationField.getText(); }

    public void setAjouterListener(ActionListener l) { ajouterButton.addActionListener(l); }
    public void setModifierListener(ActionListener l) { modifierButton.addActionListener(l); }
    public void setSupprimerListener(ActionListener l) { supprimerButton.addActionListener(l); }

    public void setSelectionListener() {
        table.getSelectionModel().addListSelectionListener(e -> remplirFormulaireDepuisLigne());
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
