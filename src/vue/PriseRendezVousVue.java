package vue;

// Importation des classes nécessaires
import modele.Disponibilite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PriseRendezVousVue extends JFrame {
    // Champs de texte pour les filtres de recherche
    private JTextField specialisationField;
    private JTextField villeField;
    private JTextField dateField;

    // Boutons pour rechercher et réserver un créneau
    private JButton rechercherButton;
    private JButton reserverButton;

    // Liste des disponibilités à afficher
    private DefaultListModel<Disponibilite> listeModel;
    private JList<Disponibilite> listeDispos;

    // Constructeur de la vue
    public PriseRendezVousVue() {
        setTitle("Prise de Rendez-vous");                         // Titre de la fenêtre
        setSize(600, 500);                                        // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        // Ferme uniquement cette fenêtre sans quitter l'appli
        setLocationRelativeTo(null);                              // Centre la fenêtre à l'écran

        // 🧩 Création du panneau de filtres pour la recherche
        JPanel filtresPanel = new JPanel(new GridLayout(4, 2));

        // Champ : spécialisation
        filtresPanel.add(new JLabel("Spécialisation :"));
        specialisationField = new JTextField();
        filtresPanel.add(specialisationField);

        // Champ : ville
        filtresPanel.add(new JLabel("Ville :"));
        villeField = new JTextField();
        filtresPanel.add(villeField);

        // Champ : date
        filtresPanel.add(new JLabel("Date (yyyy-mm-dd) :"));
        dateField = new JTextField();
        filtresPanel.add(dateField);

        // Boutons de recherche et de réservation
        rechercherButton = new JButton("Rechercher");
        reserverButton = new JButton("Prendre ce rendez-vous");
        filtresPanel.add(rechercherButton);
        filtresPanel.add(reserverButton);

        // 📜 Liste des créneaux disponibles (initialement vide)
        listeModel = new DefaultListModel<>();
        listeDispos = new JList<>(listeModel);
        listeDispos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Une seule sélection possible
        JScrollPane scroll = new JScrollPane(listeDispos);                 // Ajout d’un ascenseur à la liste

        // Mise en page de la fenêtre
        setLayout(new BorderLayout());
        add(filtresPanel, BorderLayout.NORTH);   // Le haut de la fenêtre contient les filtres
        add(scroll, BorderLayout.CENTER);        // Le centre contient la liste des créneaux
    }

    // --- Méthodes pour accéder aux champs de texte ---

    public String getSpecialisation() {
        return specialisationField.getText().trim();
    }

    public String getVille() {
        return villeField.getText().trim();
    }

    public String getDate() {
        return dateField.getText().trim();
    }

    // --- Méthodes pour connecter les boutons à des actions (listeners) ---

    public void setRechercherListener(ActionListener listener) {
        rechercherButton.addActionListener(listener);
    }

    public void setReserverListener(ActionListener listener) {
        reserverButton.addActionListener(listener);
    }

    // Affiche les résultats de la recherche dans la liste
    public void afficherResultats(List<Disponibilite> disponibilites) {
        listeModel.clear(); // On vide d’abord la liste
        if (disponibilites.isEmpty()) {
            afficherMessage("Aucun créneau trouvé.");
        } else {
            for (Disponibilite d : disponibilites) {
                listeModel.addElement(d); // On ajoute chaque dispo dans la liste
            }
        }
    }

    // Récupère la disponibilité sélectionnée par l’utilisateur
    public Disponibilite getSelection() {
        return listeDispos.getSelectedValue();
    }

    // Affiche un message d'information
    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Affiche un message d'erreur
    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
