package controleur;

import dao.PatientDAO;
import modele.Patient;
import vue.ConnexionVue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthentificationController {
    private ConnexionVue vue;
    private PatientDAO dao;

    public AuthentificationController() {
        vue = new ConnexionVue();
        dao = new PatientDAO();

        // Listener pour le bouton "Se connecter"
        vue.setConnexionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = vue.getEmail();
                String motDePasse = vue.getMotDePasse();

                Patient patient = dao.getPatientParEmailEtMotDePasse(email, motDePasse);

                if (patient != null) {
                    // Connexion réussie : fermer la fenêtre de connexion
                    vue.dispose();

                    // Rediriger en fonction du rôle
                    if ("admin".equalsIgnoreCase(patient.getRole())) {
                        new AdminDashboardController(); // A créer
                    } else {
                        new AccueilController(patient.getId(), patient.getPrenom());
                    }
                } else {
                    // Connexion échouée : afficher une erreur
                    vue.afficherErreur("Email ou mot de passe incorrect");
                }
            }
        });

        // Listener pour le bouton "Créer un compte"
        vue.setInscriptionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InscriptionController(); // Ouvre la page d'inscription
            }
        });

        // Afficher la fenêtre de connexion
        vue.setVisible(true);
    }
}
