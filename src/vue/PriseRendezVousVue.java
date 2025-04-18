package vue;

import modele.Disponibilite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
        setTitle("Prise de Rendez-vous");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🧩 Filtres
        JPanel filtresPanel = new JPanel(new GridLayout(4, 2));
        filtresPanel.add(new JLabel("Spécialisation :"));
        specialisationField = new JTextField();
        filtresPanel.add(specialisationField);

        filtresPanel.add(new JLabel("Ville :"));
        villeField = new JTextField();
        filtresPanel.add(villeField);

        filtresPanel.add(new JLabel("Date (yyyy-mm-dd) :"));
        dateField = new JTextField();
        filtresPanel.add(dateField);

        rechercherButton = new JButton("Rechercher");
        reserverButton = new JButton("Prendre ce rendez-vous");
        filtresPanel.add(rechercherButton);
        filtresPanel.add(reserverButton);

        // 📜 Liste déroulante avec créneaux
        listeModel = new DefaultListModel<>();
        listeDispos = new JList<>(listeModel);
        listeDispos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(listeDispos);

        setLayout(new BorderLayout());
        add(filtresPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public String getSpecialisation() {
        return specialisationField.getText().trim();
    }

    public String getVille() {
        return villeField.getText().trim();
    }

    public String getDate() {
        return dateField.getText().trim();
    }

    public void setRechercherListener(ActionListener listener) {
        rechercherButton.addActionListener(listener);
    }

    public void setReserverListener(ActionListener listener) {
        reserverButton.addActionListener(listener);
    }

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

    public Disponibilite getSelection() {
        return listeDispos.getSelectedValue();
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
