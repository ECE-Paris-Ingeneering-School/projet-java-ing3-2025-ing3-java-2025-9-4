package controleur; //appartient au package

import vue.AdminDashboardVue; //importation du document d un autre package pour pouvoir le lier

public class AdminDashboardController {

    private AdminDashboardVue vue;

    //Constructeur
    public AdminDashboardController() {
        vue = new AdminDashboardVue(); //création de la vue admin

        //si clique sur bouton specialiste -> ouverture controleur correspondant
        vue.setSpecialistesListener(e -> new GestionSpecialistesController());
        {
            System.out.println("Gestion des spécialistes (à venir)");
            // new GestionSpecialistesController();
        };

        //si clique sur bouton disponibilite -> ouverture controleur correspondant
        vue.setDisponibilitesListener(e -> new GestionDisponibilitesController());
        {
            System.out.println("Gestion des créneaux (à venir)");
            // new GestionDisponibilitesController();
        };

        //si clique sur bouton patiens -> ouverture controleur correspondant
        vue.setPatientsListener(e -> new GestionPatientsController());
        {
            System.out.println("Gestion des patients (à venir)");
            // new GestionPatientsController();
        };

        //si clique sur bouton deconnexion -> ouverture controleur correspondant
        vue.setDeconnexionListener(e -> {
            vue.dispose();
            new AuthentificationController();
        });

        vue.setVisible(true);
    }
}
