package controleur;

// Importation des classes nécessaires
import dao.RendezVousDAO;
import modele.RendezVous;
import vue.AgendaPatientVue;

import java.util.List;

public class AgendaController {
    // Déclaration des attributs principaux
    private AgendaPatientVue vue;     // Vue affichant l’agenda du patient
    private RendezVousDAO dao;        // DAO permettant d’accéder aux rendez-vous

    // Constructeur du contrôleur
    public AgendaController(int idPatient, String nomPatient) {
        dao = new RendezVousDAO();                    // Initialisation du DAO pour les rendez-vous
        vue = new AgendaPatientVue(nomPatient);       // Création de la vue avec le nom du patient

        // Récupération de la liste des rendez-vous pour le patient donné
        List<RendezVous> rdvList = dao.getRendezVousParPatient(idPatient);

        // Affichage de la liste des rendez-vous dans la vue
        vue.afficherListeRdv(rdvList);

        // Rendre la vue visible
        vue.setVisible(true);
    }
}
