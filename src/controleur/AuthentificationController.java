package controleur;

import dao.PatientDAO;
import modele.Patient;
import vue.AccueilPatientVue;
import vue.ConnexionVue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthentificationController {
    private ConnexionVue vue;
    private PatientDAO dao;

    public AuthentificationController() {
        vue = new ConnexionVue();
        dao = new PatientDAO();

        vue.setConnexionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = vue.getEmail();
                String mdp = vue.getMotDePasse();
                Patient patient = dao.getPatientParEmailEtMotDePasse(email, mdp);
                if (patient != null) {
                    vue.dispose();
                    JOptionPane.showMessageDialog(null, "Connexion réussie !");

                    if (patient.getRole().equalsIgnoreCase("admin")) {
                        new AdminDashboardController(); // 👈 à créer
                    } else {
                        new AccueilController(patient.getId(), patient.getPrenom());
                    }
                } else {
                    vue.afficherErreur("Email ou mot de passe incorrect");
                }

            }
        });

        // 👉 Nouveau bouton inscription
        vue.setInscriptionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InscriptionController(); // ouvre le formulaire
            }
        });

        vue.setVisible(true);
    }
}
