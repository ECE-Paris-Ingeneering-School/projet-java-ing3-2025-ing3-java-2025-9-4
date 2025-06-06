package controleur;


import vue.AccueilPatientVue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilController {
    private AccueilPatientVue vue;
    private int idPatient;
    private String nomPatient;

    public AccueilController(int idPatient, String nomPatient) {
        this.idPatient = idPatient;
        this.nomPatient = nomPatient;
        this.vue = new AccueilPatientVue(nomPatient);

        vue.setPrendreRdvListener(e -> new RendezVousController(idPatient, nomPatient));

        vue.setAgendaListener(e -> new AgendaController(idPatient, nomPatient));

        vue.setHistoriqueListener(e -> new HistoriqueController(idPatient, nomPatient));

        
        vue.setDeconnexionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vue.dispose(); // ferme la fenêtre d'accueil
                new AuthentificationController(); // revient à la page de connexion
            }
        });

        vue.setVisible(true);
    }
}
