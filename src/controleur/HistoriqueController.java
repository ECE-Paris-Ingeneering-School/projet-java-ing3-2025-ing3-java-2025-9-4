package controleur;

import dao.RendezVousDAO;
import modele.RendezVous;
import vue.HistoriquePatientVue;

import java.util.List;

public class HistoriqueController {
    private HistoriquePatientVue vue;
    private RendezVousDAO dao;

    public HistoriqueController(int idPatient, String nomPatient) {
        dao = new RendezVousDAO();
        vue = new HistoriquePatientVue(nomPatient);

        List<RendezVous> historiques = dao.getHistoriqueParPatient(idPatient);
        vue.afficherHistorique(historiques);

        vue.setVisible(true);
    }
}
