package controleur;

import dao.PatientDAO;
import modele.Patient;
import vue.InscriptionVue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InscriptionController {
    private InscriptionVue vue;
    private PatientDAO dao;

    public InscriptionController() {
        vue = new InscriptionVue();
        dao = new PatientDAO();

        vue.setInscriptionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = vue.getNom();
                String prenom = vue.getPrenom();
                String email = vue.getEmail();
                String mdp = vue.getMotDePasse();

                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
                    vue.afficherErreur("Tous les champs sont obligatoires !");
                    return;
                }

                Patient nouveau = new Patient(0, nom, prenom, email, mdp, false, "patient");
                boolean success = dao.ajouterPatient(nouveau);

                if (success) {
                    vue.afficherMessage("Inscription réussie !");
                    vue.dispose();
                } else {
                    vue.afficherErreur("Erreur lors de l'inscription.");
                }
            }
        });

        vue.setVisible(true);
    }
}
