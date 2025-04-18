package controleur;

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
    private PriseRendezVousVue vue;
    private DisponibiliteDAO dispoDAO;
    private RendezVousDAO rdvDAO;
    private int idPatient;
    private String nomPatient;

    public RendezVousController(int idPatient, String nomPatient) {
        this.idPatient = idPatient;
        this.nomPatient = nomPatient;
        this.vue = new PriseRendezVousVue();

        dispoDAO = new DisponibiliteDAO();
        rdvDAO = new RendezVousDAO();

        vue.setRechercherListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String specialisation = vue.getSpecialisation();
                    String ville = vue.getVille();
                    String dateTexte = vue.getDate();

                    Date date = null;
                    if (!dateTexte.isEmpty()) {
                        try {
                            date = Date.valueOf(dateTexte);
                        } catch (IllegalArgumentException ex) {
                            vue.afficherErreur("Format de date invalide. Utilisez yyyy-mm-dd.");
                            return;
                        }
                    }

                    List<Disponibilite> resultats = dispoDAO.rechercherDisponibilites(
                            specialisation.isEmpty() ? null : specialisation,
                            ville.isEmpty() ? null : ville,
                            date
                    );
                    vue.afficherResultats(resultats);

                } catch (Exception ex) {
                    vue.afficherErreur("Erreur lors de la recherche : " + ex.getMessage());
                }
            }
        });

        vue.setReserverListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Disponibilite selection = vue.getSelection();
                if (selection == null) {
                    vue.afficherErreur("Aucune disponibilité sélectionnée.");
                    return;
                }

                int reponse = JOptionPane.showConfirmDialog(
                        null,
                        "Confirmer la réservation du créneau suivant ?\n\n" + selection.toString(),
                        "Confirmation de rendez-vous",
                        JOptionPane.YES_NO_OPTION
                );

                if (reponse == JOptionPane.YES_OPTION) {
                    boolean success = rdvDAO.reserverRendezVous(idPatient, selection.getId());

                    if (success) {
                        vue.afficherMessage("Rendez-vous confirmé !");
                    } else {
                        vue.afficherErreur("Échec de la réservation.");
                    }
                }
            }
        });

        vue.setVisible(true);
    }
}
