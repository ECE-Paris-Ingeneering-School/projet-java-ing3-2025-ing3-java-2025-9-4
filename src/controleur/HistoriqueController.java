package controleur;

// Importation des classes nécessaires
import dao.RendezVousDAO;
import modele.RendezVous;
import vue.HistoriquePatientVue;

import java.util.List;

public class HistoriqueController {
    // Attributs du contrôleur
    private HistoriquePatientVue vue;    // Vue pour afficher l’historique des rendez-vous
    private RendezVousDAO dao;           // DAO pour accéder aux données de rendez-vous

    // Constructeur du contrôleur
    public HistoriqueController(int idPatient, String nomPatient) {
        dao = new RendezVousDAO();                            // Instanciation du DAO
        vue = new HistoriquePatientVue(nomPatient);           // Création de la vue avec le nom du patient

        // Récupération de l'historique des rendez-vous pour ce patient
        List<RendezVous> historiques = dao.getHistoriqueParPatient(idPatient);

        // Affichage de l'historique dans la vue
        vue.afficherHistorique(historiques);

        // Affichage de la fenêtre
        vue.setVisible(true);
    }
}

