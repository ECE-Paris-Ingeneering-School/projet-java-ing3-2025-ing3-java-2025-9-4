package dao;

import modele.BaseDeDonnees;
import modele.Disponibilite;
import modele.Lieu;
import modele.Specialiste;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisponibiliteDAO {

    public List<Disponibilite> getToutesLesDisponibilites() {
        List<Disponibilite> liste = new ArrayList<>();

        String sql = """
            SELECT d.id_disponibilite, d.date, d.heure, d.disponible,
                   s.id_specialiste, s.nom AS nom_specialiste, s.specialisation, s.qualification,
                   l.id_lieu, l.adresse, l.ville
            FROM Disponibilite d
            JOIN Specialiste s ON d.id_specialiste = s.id_specialiste
            JOIN Lieu l ON d.id_lieu = l.id_lieu
            ORDER BY d.date, d.heure
        """;

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Specialiste s = new Specialiste(
                        rs.getInt("id_specialiste"),
                        rs.getString("nom_specialiste"),
                        rs.getString("specialisation"),
                        rs.getString("qualification")
                );

                Lieu l = new Lieu(
                        rs.getInt("id_lieu"),
                        rs.getString("adresse"),
                        rs.getString("ville")
                );

                Disponibilite d = new Disponibilite(
                        rs.getInt("id_disponibilite"),
                        s,
                        l,
                        rs.getDate("date"),
                        rs.getTime("heure"),
                        rs.getBoolean("disponible")
                );

                liste.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Erreur chargement disponibilités : " + e.getMessage());
        }

        return liste;
    }

    public List<Disponibilite> rechercherDisponibilites(String specialisation, String ville, Date date) {
        List<Disponibilite> resultats = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT d.id_disponibilite, d.date, d.heure, d.disponible,
               s.id_specialiste, s.nom AS nom_specialiste, s.specialisation, s.qualification,
               l.id_lieu, l.adresse, l.ville
        FROM Disponibilite d
        JOIN Specialiste s ON d.id_specialiste = s.id_specialiste
        JOIN Lieu l ON d.id_lieu = l.id_lieu
        WHERE d.disponible = TRUE
    """);

        List<Object> params = new ArrayList<>();

        if (specialisation != null && !specialisation.isEmpty()) {
            sql.append(" AND s.specialisation LIKE ? ");
            params.add("%" + specialisation + "%");
        }

        if (ville != null && !ville.isEmpty()) {
            sql.append(" AND l.ville LIKE ? ");
            params.add("%" + ville + "%");
        }

        if (date != null) {
            sql.append(" AND d.date = ? ");
            params.add(date);
        }

        sql.append(" ORDER BY d.date, d.heure");

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Specialiste s = new Specialiste(
                        rs.getInt("id_specialiste"),
                        rs.getString("nom_specialiste"),
                        rs.getString("specialisation"),
                        rs.getString("qualification")
                );

                Lieu l = new Lieu(
                        rs.getInt("id_lieu"),
                        rs.getString("adresse"),
                        rs.getString("ville")
                );

                Disponibilite d = new Disponibilite(
                        rs.getInt("id_disponibilite"),
                        s,
                        l,
                        rs.getDate("date"),
                        rs.getTime("heure"),
                        rs.getBoolean("disponible")
                );

                resultats.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Erreur recherche disponibilités : " + e.getMessage());
        }

        return resultats;
    }



    public boolean ajouterDisponibilite(Disponibilite d) {
        String sql = "INSERT INTO Disponibilite (id_specialiste, id_lieu, date, heure, disponible) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, d.getSpecialiste().getId());
            stmt.setInt(2, d.getLieu().getId());
            stmt.setDate(3, d.getDate());
            stmt.setTime(4, d.getHeure());
            stmt.setBoolean(5, d.isDisponible());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout disponibilité : " + e.getMessage());
            return false;
        }
    }

    public boolean modifierDisponibilite(Disponibilite d) {
        String sql = """
            UPDATE Disponibilite
            SET id_specialiste = ?, id_lieu = ?, date = ?, heure = ?, disponible = ?
            WHERE id_disponibilite = ?
        """;

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, d.getSpecialiste().getId());
            stmt.setInt(2, d.getLieu().getId());
            stmt.setDate(3, d.getDate());
            stmt.setTime(4, d.getHeure());
            stmt.setBoolean(5, d.isDisponible());
            stmt.setInt(6, d.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur modification disponibilité : " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerDisponibilite(int id) {
        String sql = "DELETE FROM Disponibilite WHERE id_disponibilite = ?";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression disponibilité : " + e.getMessage());
            return false;
        }
    }
}

