package vue;

import modele.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionPatientsVue extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nomField, prenomField, emailField, mdpField;
    private JComboBox<String> roleBox;
    private JCheckBox ancienCheck;
    private JButton ajouterButton, modifierButton, supprimerButton;

    public GestionPatientsVue() {
        setTitle("Gestion des Patients");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "Email", "Rôle", "Ancien"}, 0);
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        // Formulaire
        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        mdpField = new JTextField();
        roleBox = new JComboBox<>(new String[]{"patient", "admin"});
        ancienCheck = new JCheckBox("Ancien patient");

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.add(new JLabel("Nom :")); formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom :")); formPanel.add(prenomField);
        formPanel.add(new JLabel("Email :")); formPanel.add(emailField);
        formPanel.add(new JLabel("Mot de passe :")); formPanel.add(mdpField);
        formPanel.add(new JLabel("Rôle :")); formPanel.add(roleBox);
        formPanel.add(new JLabel("Statut :")); formPanel.add(ancienCheck);

        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        formPanel.add(ajouterButton); formPanel.add(modifierButton); formPanel.add(supprimerButton);

        setLayout(new BorderLayout(10, 10));
        add(scroll, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
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
