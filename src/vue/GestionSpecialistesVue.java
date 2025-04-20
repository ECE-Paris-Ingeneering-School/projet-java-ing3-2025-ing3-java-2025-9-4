package vue; //appartient au package vue

import modele.Specialiste; //import le document  (present dans le package modele) pour pouvoir y faire appel

//import biblio
import javax.swing.*; //construire interface graphique
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener; //cliques boutons

public class GestionSpecialistesVue extends JFrame {
    //composants de l interface
    private JTable table; //tableau liste specialiste
    private DefaultTableModel tableModel; //modele de données pour le tab
    private JTextField nomField, specialisationField, qualificationField; //pour la saisit
    private JButton ajouterButton, modifierButton, supprimerButton; //les boutons

    public GestionSpecialistesVue() {
        //config fenetre
        setTitle("Gestion des Spécialistes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Spécialisation", "Qualification"}, 0);
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        // Formulaire
        JPanel formPanel = new JPanel(new GridLayout(4, 2)); //4 lignes 2 colones
        formPanel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        formPanel.add(nomField);

        formPanel.add(new JLabel("Spécialisation :"));
        specialisationField = new JTextField();
        formPanel.add(specialisationField);

        formPanel.add(new JLabel("Qualification :"));
        qualificationField = new JTextField();
        formPanel.add(qualificationField);

        //boutons
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        formPanel.add(ajouterButton);
        formPanel.add(modifierButton);
        formPanel.add(supprimerButton);

        //placement sur la page
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    //ajoute une ligne dans la table
    public void ajouterLigne(Specialiste s) {
        tableModel.addRow(new Object[]{s.getId(), s.getNom(), s.getSpecialisation(), s.getQualification()});
    }

    //vider la table
    public void viderTable() {
        tableModel.setRowCount(0);
    }

    //recupere les donnees du form
    public Specialiste getSpecialisteFormulaire() {
        return new Specialiste(0, getNom(), getSpecialisation(), getQualification());
    }

    //clique sur une ligne -> peut remplir le form
    public void remplirFormulaireDepuisLigne() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            nomField.setText((String) tableModel.getValueAt(row, 1));
            specialisationField.setText((String) tableModel.getValueAt(row, 2));
            qualificationField.setText((String) tableModel.getValueAt(row, 3));
        }
    }

    //renvoit l id de la ligne selectionner pour la modifier ou la supp
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

    //champs du form
    public String getNom() { return nomField.getText(); }
    public String getSpecialisation() { return specialisationField.getText(); }
    public String getQualification() { return qualificationField.getText(); }

    //ecouteur cliques boutons
    public void setAjouterListener(ActionListener l) { ajouterButton.addActionListener(l); }
    public void setModifierListener(ActionListener l) { modifierButton.addActionListener(l); }
    public void setSupprimerListener(ActionListener l) { supprimerButton.addActionListener(l); }

    public void setSelectionListener() {
        table.getSelectionModel().addListSelectionListener(e -> remplirFormulaireDepuisLigne());
    }

    //affiche mess
    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    //affiche erreur
    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
