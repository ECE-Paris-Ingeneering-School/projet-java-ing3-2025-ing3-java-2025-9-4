package dao;

import modele.BaseDeDonnees;
import modele.Disponibilite;
import modele.Lieu;
import modele.RendezVous;
import modele.Specialiste;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAO {

    public boolean reserverRendezVous(int idPatient, int idDisponibilite) {
        String insertRdv = "INSERT INTO RendezVous (id_patient, id_disponibilite) VALUES (?, ?)";
        String majDispo = "UPDATE Disponibilite SET disponible = FALSE WHERE id_disponibilite = ?";

        try (Connection conn = BaseDeDonnees.getConnexion()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(insertRdv);
                 PreparedStatement stmt2 = conn.prepareStatement(majDispo)) {

                stmt1.setInt(1, idPatient);
                stmt1.setInt(2, idDisponibilite);
                stmt1.executeUpdate();

                stmt2.setInt(1, idDisponibilite);
                stmt2.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Erreur transaction RDV : " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Erreur connexion DAO RDV : " + e.getMessage());
        }

        return false;
    }

    public List<RendezVous> getRendezVousParPatient(int idPatient) {
        List<RendezVous> liste = new ArrayList<>();

        String sql = """
            SELECT rdv.id_rdv, d.id_disponibilite, d.date, d.heure,
                   s.id_specialiste, s.nom AS nom_specialiste, s.specialisation, s.qualification,
                   l.id_lieu, l.adresse, l.ville
            FROM RendezVous rdv
            JOIN Disponibilite d ON rdv.id_disponibilite = d.id_disponibilite
            JOIN Specialiste s ON d.id_specialiste = s.id_specialiste
            JOIN Lieu l ON d.id_lieu = l.id_lieu
            WHERE rdv.id_patient = ?
              AND d.date >= CURDATE()
            ORDER BY d.date, d.heure
        """;

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPatient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Specialiste specialiste = new Specialiste(
                        rs.getInt("id_specialiste"),
                        rs.getString("nom_specialiste"),
                        rs.getString("specialisation"),
                        rs.getString("qualification")
                );

                Lieu lieu = new Lieu(
                        rs.getInt("id_lieu"),
                        rs.getString("adresse"),
                        rs.getString("ville")
                );

                Disponibilite dispo = new Disponibilite(
                        rs.getInt("id_disponibilite"),
                        specialiste,
                        lieu,
                        rs.getDate("date"),
                        rs.getTime("heure"),
                        true
                );

                RendezVous rdv = new RendezVous(rs.getInt("id_rdv"), dispo, null);
                liste.add(rdv);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération RDV patient : " + e.getMessage());
        }

        return liste;
    }

    public List<RendezVous> getHistoriqueParPatient(int idPatient) {
        List<RendezVous> liste = new ArrayList<>();

        String sql = """
        SELECT rdv.id_rdv, d.id_disponibilite, d.date, d.heure, rdv.note,
               s.id_specialiste, s.nom AS nom_specialiste, s.specialisation, s.qualification,
               l.id_lieu, l.adresse, l.ville
        FROM RendezVous rdv
        JOIN Disponibilite d ON rdv.id_disponibilite = d.id_disponibilite
        JOIN Specialiste s ON d.id_specialiste = s.id_specialiste
        JOIN Lieu l ON d.id_lieu = l.id_lieu
        WHERE rdv.id_patient = ?
          AND d.date < CURDATE()
        ORDER BY d.date DESC
    """;

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPatient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Specialiste specialiste = new Specialiste(
                        rs.getInt("id_specialiste"),
                        rs.getString("nom_specialiste"),
                        rs.getString("specialisation"),
                        rs.getString("qualification")
                );

                Lieu lieu = new Lieu(
                        rs.getInt("id_lieu"),
                        rs.getString("adresse"),
                        rs.getString("ville")
                );

                Disponibilite dispo = new Disponibilite(
                        rs.getInt("id_disponibilite"),
                        specialiste,
                        lieu,
                        rs.getDate("date"),
                        rs.getTime("heure"),
                        false
                );

                RendezVous rdv = new RendezVous(
                        rs.getInt("id_rdv"),
                        dispo,
                        rs.getString("note")
                );

                liste.add(rdv);
            }

        } catch (SQLException e) {
            System.err.println("Erreur historique RDV : " + e.getMessage());
        }

        return liste;
    }


}
