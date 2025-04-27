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
                String motDePasse = vue.getMotDePasse();

                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
                    vue.afficherErreur("Tous les champs sont obligatoires !");
                    return;
                }
                // Vérification de l'email
                if (!email.contains("@")) {
                    vue.afficherErreur("L'adresse email doit contenir un '@'.");
                    return;
                }


                Patient nouveauPatient = new Patient(0, nom, prenom, email, motDePasse, false, "patient");
                boolean success = dao.ajouterPatient(nouveauPatient);

                if (success) {
                    vue.dispose(); // Fermer la fenêtre sans message inutile
                } else {
                    vue.afficherErreur("Erreur lors de l'inscription."); // On garde l'erreur si problème
                }
            }
        });

        vue.setVisible(true);
    }
}
