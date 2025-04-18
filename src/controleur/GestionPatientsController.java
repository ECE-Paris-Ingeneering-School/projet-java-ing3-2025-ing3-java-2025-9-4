package controleur;

import dao.PatientDAO;
import modele.Patient;
import vue.GestionPatientsVue;

import java.util.List;

public class GestionPatientsController {
    private GestionPatientsVue vue;
    private PatientDAO dao;

    public GestionPatientsController() {
        vue = new GestionPatientsVue();
        dao = new PatientDAO();

        chargerListe();
        vue.setSelectionListener();

        vue.setAjouterListener(e -> {
            try {
                Patient p = vue.getPatientFormulaire(0);
                if (dao.ajouterPatient(p)) {
                    vue.afficherMessage("Patient ajouté !");
                    chargerListe();
                } else {
                    vue.afficherErreur("Erreur à l'ajout.");
                }
            } catch (Exception ex) {
                vue.afficherErreur("Erreur : " + ex.getMessage());
            }
        });

        vue.setModifierListener(e -> {
            int id = vue.getIdSelectionne();
            if (id < 0) {
                vue.afficherErreur("Sélectionnez un patient.");
                return;
            }

            try {
                Patient p = vue.getPatientFormulaire(id);
                if (dao.modifierPatient(p)) {
                    vue.afficherMessage("Patient modifié !");
                    chargerListe();
                } else {
                    vue.afficherErreur("Erreur à la modification.");
                }
            } catch (Exception ex) {
                vue.afficherErreur("Erreur : " + ex.getMessage());
            }
        });

        vue.setSupprimerListener(e -> {
            int id = vue.getIdSelectionne();
            if (id < 0) {
                vue.afficherErreur("Sélectionnez un patient à supprimer.");
                return;
            }

            if (dao.supprimerPatient(id)) {
                vue.afficherMessage("Patient supprimé !");
                chargerListe();
            } else {
                vue.afficherErreur("Erreur lors de la suppression.");
            }
        });

        vue.setVisible(true);
    }

    private void chargerListe() {
        vue.viderTable();
        List<Patient> patients = dao.getTousLesPatients();
        for (Patient p : patients) {
            vue.ajouterLigne(p);
        }
    }
}
