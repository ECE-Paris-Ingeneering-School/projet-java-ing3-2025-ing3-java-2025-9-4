package controleur;

import vue.AdminDashboardVue;

public class AdminDashboardController {
    private AdminDashboardVue vue;

    public AdminDashboardController() {
        vue = new AdminDashboardVue();

        vue.setSpecialistesListener(e -> new GestionSpecialistesController());
        {
            System.out.println("Gestion des spécialistes (à venir)");
            // new GestionSpecialistesController();
        };

        vue.setDisponibilitesListener(e -> new GestionDisponibilitesController());
        {
            System.out.println("Gestion des créneaux (à venir)");
            // new GestionDisponibilitesController();
        };

        vue.setPatientsListener(e -> new GestionPatientsController());
        {
            System.out.println("Gestion des patients (à venir)");
            // new GestionPatientsController();
        };

        vue.setDeconnexionListener(e -> {
            vue.dispose();
            new AuthentificationController();
        });

        vue.setVisible(true);
    }
}
