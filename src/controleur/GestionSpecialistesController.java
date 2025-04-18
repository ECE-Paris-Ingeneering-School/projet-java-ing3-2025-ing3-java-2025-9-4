package controleur;

import dao.SpecialisteDAO;
import modele.Specialiste;
import vue.GestionSpecialistesVue;

import java.util.List;

public class GestionSpecialistesController {
    private GestionSpecialistesVue vue;
    private SpecialisteDAO dao;

    public GestionSpecialistesController() {
        vue = new GestionSpecialistesVue();
        dao = new SpecialisteDAO();

        chargerListe();

        vue.setSelectionListener();

        vue.setAjouterListener(e -> {
            Specialiste s = vue.getSpecialisteFormulaire();
            if (dao.ajouterSpecialiste(s)) {
                vue.afficherMessage("Spécialiste ajouté !");
                chargerListe();
            } else {
                vue.afficherErreur("Échec de l'ajout.");
            }
        });

        vue.setModifierListener(e -> {
            Specialiste s = vue.getSpecialisteSelectionne();
            if (s == null) {
                vue.afficherErreur("Aucun spécialiste sélectionné.");
                return;
            }
            s = new Specialiste(s.getId(), vue.getNom(), vue.getSpecialisation(), vue.getQualification());
            if (dao.modifierSpecialiste(s)) {
                vue.afficherMessage("Spécialiste modifié !");
                chargerListe();
            } else {
                vue.afficherErreur("Erreur lors de la modification.");
            }
        });

        vue.setSupprimerListener(e -> {
            int id = vue.getIdSelectionne();
            if (id < 0) {
                vue.afficherErreur("Sélectionnez un spécialiste à supprimer.");
                return;
            }
            if (dao.supprimerSpecialiste(id)) {
                vue.afficherMessage("Spécialiste supprimé !");
                chargerListe();
            } else {
                vue.afficherErreur("Erreur suppression.");
            }
        });

        vue.setVisible(true);
    }

    private void chargerListe() {
        vue.viderTable();
        List<Specialiste> specialistes = dao.getTousLesSpecialistes();
        for (Specialiste s : specialistes) {
            vue.ajouterLigne(s);
        }
    }
}
