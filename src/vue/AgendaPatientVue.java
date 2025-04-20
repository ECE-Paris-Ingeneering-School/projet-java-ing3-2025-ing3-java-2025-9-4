package vue;

import modele.RendezVous;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AgendaPatientVue extends JFrame {
    // Modèle de données pour la liste des rendez-vous
    private DefaultListModel<RendezVous> modelListe;
    
    // Composant graphique qui affichera les rendez-vous dans une liste
    private JList<RendezVous> listeRdv;

    // Constructeur de la vue, avec le nom du patient affiché dans le titre
    public AgendaPatientVue(String nomPatient) {
        setTitle("Agenda de " + nomPatient);                   // Titre de la fenêtre
        setSize(500, 400);                                     // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);     // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null);                           // Centrer la fenêtre sur l'écran

        // Création du modèle et de la liste graphique
        modelListe = new DefaultListModel<>();
        listeRdv = new JList<>(modelListe);

        // Ajout d'une barre de défilement à la liste
        JScrollPane scroll = new JScrollPane(listeRdv);

        // Création d’un titre centré et stylisé
        JLabel titre = new JLabel("Rendez-vous à venir", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));       // Mise en forme du texte

        // Mise en page générale
        setLayout(new BorderLayout());
        add(titre, BorderLayout.NORTH);    // Le titre en haut
        add(scroll, BorderLayout.CENTER);  // La liste au centre
    }

    // Méthode publique pour afficher une liste de rendez-vous
    public void afficherListeRdv(List<RendezVous> rdvList) {
        modelListe.clear();  // On vide la liste actuelle

        // Si la liste est vide, on ajoute un message spécial
        if (rdvList.isEmpty()) {
            modelListe.addElement(new RendezVous(-1, null, "Aucun rendez-vous à venir."));
        } else {
            // Sinon, on ajoute chaque rendez-vous dans la liste
            for (RendezVous rdv : rdvList) {
                modelListe.addElement(rdv);
            }
        }
    }
}
