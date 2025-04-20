package controleur;

// Importation des classes nécessaires
import dao.DisponibiliteDAO;
import dao.RendezVousDAO;
import modele.Disponibilite;
import vue.PriseRendezVousVue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import javax.swing.*;

public class RendezVousController {
    // Déclaration des attributs nécessaires au contrôleur
    private PriseRendezVousVue vue;             // Vue liée à la prise de rendez-vous
    private DisponibiliteDAO dispoDAO;          // DAO pour les disponibilités
    private RendezVousDAO rdvDAO;               // DAO pour les rendez-vous
    private int idPatient;                      // Identifiant du patient courant
    private String nomPatient;                  // Nom du patient courant

    // Constructeur du contrôleur
    public RendezVousController(int idPatient, String nomPatient) {
        this.idPatient = idPatient;
        this.nomPatient = nomPatient;
        this.vue = new PriseRendezVousVue();    // Initialisation de la vue

        dispoDAO = new DisponibiliteDAO();      // Initialisation du DAO de disponibilité
        rdvDAO = new RendezVousDAO();           // Initialisation du DAO de rendez-vous

        // Ajout du listener pour la recherche de disponibilités
        vue.setRechercherListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Récupération des critères de recherche depuis la vue
                    String specialisation = vue.getSpecialisation();
                    String ville = vue.getVille();
                    String dateTexte = vue.getDate();

                    Date date = null;
                    // Conversion du texte de la date en objet Date
                    if (!dateTexte.isEmpty()) {
                        try {
                            date = Date.valueOf(dateTexte); // Format attendu : yyyy-mm-dd
                        } catch (IllegalArgumentException ex) {
                            // Affiche une erreur si le format est invalide
                            vue.afficherErreur("Format de date invalide. Utilisez yyyy-mm-dd.");
                            return;
                        }
                    }

                    // Recherche des disponibilités en fonction des critères
                    List<Disponibilite> resultats = dispoDAO.rechercherDisponibilites(
                            specialisation.isEmpty() ? null : specialisation,
                            ville.isEmpty() ? null : ville,
                            date
                    );
                    // Affichage des résultats dans la vue
                    vue.afficherResultats(resultats);

                } catch (Exception ex) {
                    // Gestion des erreurs générales
                    vue.afficherErreur("Erreur lors de la recherche : " + ex.getMessage());
                }
            }
        });

        // Ajout du listener pour la réservation d’un rendez-vous
        vue.setReserverListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupération de la disponibilité sélectionnée par l’utilisateur
                Disponibilite selection = vue.getSelection();
                if (selection == null) {
                    // Si rien n’est sélectionné, on affiche une erreur
                    vue.afficherErreur("Aucune disponibilité sélectionnée.");
                    return;
                }

                // Demande de confirmation de la réservation
                int reponse = JOptionPane.showConfirmDialog(
                        null,
                        "Confirmer la réservation du créneau suivant ?\n\n" + selection.toString(),
                        "Confirmation de rendez-vous",
                        JOptionPane.YES_NO_OPTION
                );

                // Si l’utilisateur confirme la réservation
                if (reponse == JOptionPane.YES_OPTION) {
                    // Tentative de réservation via le DAO
                    boolean success = rdvDAO.reserverRendezVous(idPatient, selection.getId());

                    if (success) {
                        // Affiche un message de succès
                        vue.afficherMessage("Rendez-vous confirmé !");
                    } else {
                        // Affiche un message d’échec
                        vue.afficherErreur("Échec de la réservation.");
                    }
                }
            }
        });

        // Affichage de la fenêtre de la vue
        vue.setVisible(true);
    }
}
