package controleur; //appartient au package controleur

//importation des documents present dans d autre package pour pouvoir y faire appel dans le code
import dao.SpecialisteDAO;
import modele.Specialiste;
import vue.GestionSpecialistesVue;

//biblio pour manipuler des listes (de specialistes)
import java.util.List;

public class GestionSpecialistesController {
    private GestionSpecialistesVue vue;
    private SpecialisteDAO dao;

    //constructeur
    public GestionSpecialistesController() {
        vue = new GestionSpecialistesVue(); //creation fenetre gestion specialsites
        dao = new SpecialisteDAO(); //interaction avec bdd

        chargerListe(); //charge et affiche la listee des specialistes

        vue.setSelectionListener();

        //action pour ajouter un specialiste
        vue.setAjouterListener(e -> {
            Specialiste s = vue.getSpecialisteFormulaire(); //recup les infos du formulaire
            if (dao.ajouterSpecialiste(s)) { //appel dao pour inserer dans la bdd
                vue.afficherMessage("Spécialiste ajouté !"); //message de succès
                chargerListe();
            } else {
                vue.afficherErreur("Échec de l'ajout."); //message erreur
            }
        });

        //action pour modifier un specialiste
        vue.setModifierListener(e -> {
            Specialiste s = vue.getSpecialisteSelectionne(); //recup le specialiste dans tableau
            if (s == null) {
                vue.afficherErreur("Aucun spécialiste sélectionné."); //condition de selection valide
                return;
            }
            //mis a jour des infos avec les nouvelles valeurs
            s = new Specialiste(s.getId(), vue.getNom(), vue.getSpecialisation(), vue.getQualification());
            if (dao.modifierSpecialiste(s)) { //modification dans la bdd
                vue.afficherMessage("Spécialiste modifié !"); //message succès
                chargerListe();
            } else {
                vue.afficherErreur("Erreur lors de la modification."); //message erreur
            }
        });

        //action pour delete un specialiste
        vue.setSupprimerListener(e -> {
            int id = vue.getIdSelectionne(); //recup id specialiste
            if (id < 0) {
                vue.afficherErreur("Sélectionnez un spécialiste à supprimer.");
                return;
            }
            if (dao.supprimerSpecialiste(id)) {
                vue.afficherMessage("Spécialiste supprimé !");
                chargerListe();
            } else {
                vue.afficherErreur("Erreur suppression.");
            }
        });

        vue.setVisible(true); //affiche la fenetre
    }

    //methode pour recharger la liste des specialistes dans la vue
    private void chargerListe() {
        vue.viderTable(); //vider le tableau et evite les doublons
        List<Specialiste> specialistes = dao.getTousLesSpecialistes(); //recup depuis bdd
        for (Specialiste s : specialistes) {
            vue.ajouterLigne(s); //ajout dans le tableau de la vue
        }
    }
}
