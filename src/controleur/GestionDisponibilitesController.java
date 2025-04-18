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

public class GestionDisponibilitesController {
    private GestionDisponibilitesVue vue;
    private DisponibiliteDAO dao;
    private SpecialisteDAO sdao;
    private LieuDAO ldao;

    private HashMap<String, Specialiste> mapSpecialistes;
    private HashMap<String, Lieu> mapLieux;

    public GestionDisponibilitesController() {
        vue = new GestionDisponibilitesVue();
        dao = new DisponibiliteDAO();
        sdao = new SpecialisteDAO();
        ldao = new LieuDAO();

        chargerListe();

        vue.setAjouterListener(e -> {
            try {
                String nomSpe = vue.getSpecialisteSelectionne();
                String ville = vue.getLieuSelectionne();

                Specialiste s = mapSpecialistes.get(nomSpe);
                Lieu l = mapLieux.get(ville);

                if (s == null || l == null) {
                    vue.afficherErreur("Spécialiste ou lieu invalide.");
                    return;
                }

                Disponibilite d = new Disponibilite(
                        0,
                        s,
                        l,
                        Date.valueOf(vue.getDate()),
                        Time.valueOf(vue.getHeure()),
                        vue.isDisponible()
                );

                if (dao.ajouterDisponibilite(d)) {
                    vue.afficherMessage("Créneau ajouté !");
                    chargerListe();
                } else {
                    vue.afficherErreur("Échec de l'ajout.");
                }

            } catch (Exception ex) {
                vue.afficherErreur("Erreur : " + ex.getMessage());
            }
        });

        vue.setModifierListener(e -> {
            int id = vue.getSelectionId();
            if (id < 0) {
                vue.afficherErreur("Sélectionnez un créneau à modifier.");
                return;
            }

            try {
                Specialiste s = mapSpecialistes.get(vue.getSpecialisteSelectionne());
                Lieu l = mapLieux.get(vue.getLieuSelectionne());

                Disponibilite d = new Disponibilite(
                        id,
                        s,
                        l,
                        Date.valueOf(vue.getDate()),
                        Time.valueOf(vue.getHeure()),
                        vue.isDisponible()
                );

                if (dao.modifierDisponibilite(d)) {
                    vue.afficherMessage("Créneau modifié !");
                    chargerListe();
                } else {
                    vue.afficherErreur("Échec de la modification.");
                }

            } catch (Exception ex) {
                vue.afficherErreur("Erreur : " + ex.getMessage());
            }
        });

        vue.setSupprimerListener(e -> {
            int id = vue.getSelectionId();
            if (id < 0) {
                vue.afficherErreur("Sélectionnez un créneau à supprimer.");
                return;
            }

            if (dao.supprimerDisponibilite(id)) {
                vue.afficherMessage("Créneau supprimé !");
                chargerListe();
            } else {
                vue.afficherErreur("Erreur lors de la suppression.");
            }
        });

        vue.setVisible(true);
    }

    private void chargerListe() {
        vue.viderTable();

        // 1. Charger les créneaux
        List<Disponibilite> dispos = dao.getToutesLesDisponibilites();
        for (Disponibilite d : dispos) {
            vue.ajouterLigne(d);
        }

        List<Specialiste> specialistes = sdao.getTousLesSpecialistes();
        List<String> noms = new ArrayList<>();
        mapSpecialistes = new HashMap<>();

        for (Specialiste s : specialistes) {
            noms.add(s.getNom());
            mapSpecialistes.put(s.getNom(), s);
        }
        vue.setSpecialistes(noms.toArray(new String[0]));

        List<Lieu> lieux = ldao.getTousLesLieux();
        List<String> villes = new ArrayList<>();
        mapLieux = new HashMap<>();

        for (Lieu l : lieux) {
            villes.add(l.getVille());
            mapLieux.put(l.getVille(), l);
        }
        vue.setLieux(villes.toArray(new String[0]));
    }
}
