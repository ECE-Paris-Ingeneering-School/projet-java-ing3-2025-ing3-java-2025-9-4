package vue;

import modele.RendezVous;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoriquePatientVue extends JFrame {
    // Modèle pour gérer dynamiquement les données de la liste
    private DefaultListModel<RendezVous> modelListe;
    
    // Composant JList pour afficher la liste des rendez-vous
    private JList<RendezVous> listeRdv;

    // Constructeur de la fenêtre, prend le nom du patient pour le titre
    public HistoriquePatientVue(String nomPatient) {
        setTitle("Historique de " + nomPatient);               // Titre personnalisé
        setSize(500, 400);                                     // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);     // Fermer uniquement cette vue
        setLocationRelativeTo(null);                           // Centrage de la fenêtre

        // Initialisation de la liste et du modèle associé
        modelListe = new DefaultListModel<>();
        listeRdv = new JList<>(modelListe);

        // Ajout d’un scroll si la liste est longue
        JScrollPane scroll = new JScrollPane(listeRdv);

        // Création d’un titre visuel pour la section
        JLabel titre = new JLabel("Rendez-vous passés", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));       // Mise en forme du titre

        // Agencement de la fenêtre avec BorderLayout
        setLayout(new BorderLayout());
        add(titre, BorderLayout.NORTH);   // Titre en haut
        add(scroll, BorderLayout.CENTER); // Liste au centre
    }

    // Méthode publique pour afficher les rendez-vous passés
    public void afficherHistorique(List<RendezVous> rdvList) {
        modelListe.clear(); // On vide le contenu précédent

        // Si la liste est vide, on ajoute un message par défaut
        if (rdvList.isEmpty()) {
            modelListe.addElement(new RendezVous(-1, null, "Aucun rendez-vous passé."));
        } else {
            // Sinon, on ajoute tous les rendez-vous un par un
            for (RendezVous rdv : rdvList) {
                modelListe.addElement(rdv);
            }
        }
    }
}

