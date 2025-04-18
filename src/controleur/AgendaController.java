package controleur;

import dao.RendezVousDAO;
import modele.RendezVous;
import vue.AgendaPatientVue;

import java.util.List;

public class AgendaController {
    private AgendaPatientVue vue;
    private RendezVousDAO dao;

    public AgendaController(int idPatient, String nomPatient) {
        dao = new RendezVousDAO();
        vue = new AgendaPatientVue(nomPatient);

        List<RendezVous> rdvList = dao.getRendezVousParPatient(idPatient);
        vue.afficherListeRdv(rdvList);

        vue.setVisible(true);
    }
}
