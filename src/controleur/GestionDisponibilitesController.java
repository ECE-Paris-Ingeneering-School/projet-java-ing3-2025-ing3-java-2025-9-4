package controleur;


import dao.DisponibiliteDAO;
import dao.LieuDAO;
import dao.SpecialisteDAO;
import modele.Disponibilite;
import modele.Lieu;
import modele.Specialiste;
import vue.GestionDisponibilitesVue;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestionDisponibilitesController { // Controleut pour la gestion des des créneaux de dispo
    private GestionDisponibilitesVue vue; // vue associé
    private DisponibiliteDAO dao; // Dao pour accéder au bdd
    private SpecialisteDAO sdao;
    private LieuDAO ldao;

    private HashMap<String, Specialiste> mapSpecialistes; //acces au objet grace au nom/ville
    private HashMap<String, Lieu> mapLieux;

    public GestionDisponibilitesController() {
        vue = new GestionDisponibilitesVue();// Initialisatino de la vue et des DAO
        dao = new DisponibiliteDAO();
        sdao = new SpecialisteDAO();
        ldao = new LieuDAO();

        chargerListe();// chargement donnée utile

        vue.setAjouterListener(e -> { // listener pour l'ajout d'un créneau
            try {
                String nomSpe = vue.getSpecialisteSelectionne(); // récupérer les entrées utilisateurs
                String ville = vue.getLieuSelectionne();

                Specialiste s = mapSpecialistes.get(nomSpe);
                Lieu l = mapLieux.get(ville);

                if (s == null || l == null) { // erreur
                    vue.afficherErreur("Spécialiste ou lieu invalide.");
                    return;
                }

                Disponibilite d = new Disponibilite( // création de l'objet disponibilité
                        0,
                        s,
                        l,
                        Date.valueOf(vue.getDate()),
                        Time.valueOf(vue.getHeure()),
                        vue.isDisponible()
                );

                if (dao.ajouterDisponibilite(d)) { // Ajout en bdd
                    vue.afficherMessage("Créneau ajouté !");
                    chargerListe();// rafraichit la table
                } else {
                    vue.afficherErreur("Échec de l'ajout."); // Message d'echec
                }

            } catch (Exception ex) { // Exeption d'erreur
                vue.afficherErreur("Erreur : " + ex.getMessage());
            }
        });

        vue.setModifierListener(e -> { // listener pour modifier un créneau
            int id = vue.getSelectionId();
            if (id < 0) {
                vue.afficherErreur("Sélectionnez un créneau à modifier.");// si auncun créneau selec, afficher erreur
                return;
            }

            try {
                Specialiste s = mapSpecialistes.get(vue.getSpecialisteSelectionne()); //on recuper la vue saisi
                Lieu l = mapLieux.get(vue.getLieuSelectionne());

                Disponibilite d = new Disponibilite( // créer nouvelle dispo avec objets modifiés
                        id,
                        s,
                        l,
                        Date.valueOf(vue.getDate()),
                        Time.valueOf(vue.getHeure()),
                        vue.isDisponible()
                );

                if (dao.modifierDisponibilite(d)) {// Modification de la bdd
                    vue.afficherMessage("Créneau modifié !");
                    chargerListe();// Rafraichit la table
                } else {
                    vue.afficherErreur("Échec de la modification.");
                }

            } catch (Exception ex) { // Message en cas d'erreur
                vue.afficherErreur("Erreur : " + ex.getMessage());
            }
        });

        vue.setSupprimerListener(e -> { // listener pour supprimer un créneau
            int id = vue.getSelectionId(); // récuperation de l'id séléctionné
            if (id < 0) {
                vue.afficherErreur("Sélectionnez un créneau à supprimer."); // si auncun créneau selec, afficher erreur
                return;
            }

            if (dao.supprimerDisponibilite(id)) { // Appel DAO pour supprimer en BDD
                vue.afficherMessage("Créneau supprimé !");
                chargerListe();
            } else {
                vue.afficherErreur("Erreur lors de la suppression.");
            }
        });

        vue.setVisible(true); // Afficher la fenetre
    }

    private void chargerListe() { // recharge les données dans la vue
        vue.viderTable();

        List<Disponibilite> dispos = dao.getToutesLesDisponibilites();//Charger les créneaux
        for (Disponibilite d : dispos) {
            vue.ajouterLigne(d);
        }

        List<Specialiste> specialistes = sdao.getTousLesSpecialistes(); // charger les spécialités
        List<String> noms = new ArrayList<>();
        mapSpecialistes = new HashMap<>();

        for (Specialiste s : specialistes) {
            noms.add(s.getNom());
            mapSpecialistes.put(s.getNom(), s);
        }
        vue.setSpecialistes(noms.toArray(new String[0]));

        List<Lieu> lieux = ldao.getTousLesLieux(); // charger les lieux
        List<String> villes = new ArrayList<>();
        mapLieux = new HashMap<>();

        for (Lieu l : lieux) {
            villes.add(l.getVille());
            mapLieux.put(l.getVille(), l);
        }
        vue.setLieux(villes.toArray(new String[0]));
    }
}
