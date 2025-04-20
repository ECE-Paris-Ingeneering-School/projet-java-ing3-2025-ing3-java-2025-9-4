package vue; //appartient au package vue

import modele.Patient; //importe la classe Patient depuis le package modele

//imports de bibliothèques Swing pour l'interface graphique
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionPatientsVue extends JFrame {
    //composants de l'interface
    private JTable table;//tab pour afficher les patients
    private DefaultTableModel tableModel; //modèle de données du tableau
    private JTextField nomField, prenomField, emailField, mdpField; //saisit
    private JComboBox<String> roleBox; //menu deroulant
    private JCheckBox ancienCheck; //ancien patient
    private JButton ajouterButton, modifierButton, supprimerButton; //boutons

    public GestionPatientsVue() {
        //config de la fenêtre
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

        //form en grille : 7 lignes (6 champs + boutons), 2 colonnes
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.add(new JLabel("Nom :")); formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom :")); formPanel.add(prenomField);
        formPanel.add(new JLabel("Email :")); formPanel.add(emailField);
        formPanel.add(new JLabel("Mot de passe :")); formPanel.add(mdpField);
        formPanel.add(new JLabel("Rôle :")); formPanel.add(roleBox);
        formPanel.add(new JLabel("Statut :")); formPanel.add(ancienCheck);

        //boutons
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        formPanel.add(ajouterButton); formPanel.add(modifierButton); formPanel.add(supprimerButton);

        //placement sur la page
        setLayout(new BorderLayout(10, 10));
        add(scroll, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    //ajoute une ligne dans la table avec les infos d'un patient
    public void ajouterLigne(Patient p) {
        tableModel.addRow(new Object[]{
                p.getId(),
                p.getNom(),
                p.getPrenom(),
                p.getEmail(),
                p.getRole(),
                p.isAncien() ? "Oui" : "Non" //bool → texte
        });
    }

    //vide la table
    public void viderTable() {
        tableModel.setRowCount(0);
    }

    //clique sur une ligne → remplit les champs avec les infos du patient
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

    //récupère les infos du form pour créer un patient
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

    //renvoit id de la ligne selectionnée
    public int getIdSelectionne() {
        int row = table.getSelectedRow();
        return row >= 0 ? (int) tableModel.getValueAt(row, 0) : -1;
    }

    //ecoluteurs boutons
    public void setAjouterListener(ActionListener l) { ajouterButton.addActionListener(l); }
    public void setModifierListener(ActionListener l) { modifierButton.addActionListener(l); }
    public void setSupprimerListener(ActionListener l) { supprimerButton.addActionListener(l); }
    public void setSelectionListener() {
        table.getSelectionModel().addListSelectionListener(e -> remplirFormulaireDepuisLigne());
    }

    //message
    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    //mess erreur
    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
