package dao;

import modele.BaseDeDonnees;
import modele.Lieu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieuDAO {

    public List<Lieu> getTousLesLieux() {
        List<Lieu> lieux = new ArrayList<>();
        String sql = "SELECT * FROM Lieu ORDER BY ville";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lieux.add(new Lieu(
                        rs.getInt("id_lieu"),
                        rs.getString("adresse"),
                        rs.getString("ville")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erreur chargement lieux : " + e.getMessage());
        }

        return lieux;
    }

    public boolean ajouterLieu(Lieu lieu) {
        String sql = "INSERT INTO Lieu (adresse, ville) VALUES (?, ?)";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lieu.getAdresse());
            stmt.setString(2, lieu.getVille());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout lieu : " + e.getMessage());
            return false;
        }
    }

    public boolean modifierLieu(Lieu lieu) {
        String sql = "UPDATE Lieu SET adresse = ?, ville = ? WHERE id_lieu = ?";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lieu.getAdresse());
            stmt.setString(2, lieu.getVille());
            stmt.setInt(3, lieu.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur modification lieu : " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerLieu(int id) {
        String sql = "DELETE FROM Lieu WHERE id_lieu = ?";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression lieu : " + e.getMessage());
            return false;
        }
    }
}

