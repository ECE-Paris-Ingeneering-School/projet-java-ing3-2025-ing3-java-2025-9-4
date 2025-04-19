package dao;


import modele.BaseDeDonnees;
import modele.Lieu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieuDAO {

    public List<Lieu> getTousLesLieux() {
        List<Lieu> lieux = new ArrayList<>(); // Initialisation d'une liste pour stocker les lieux
        String sql = "SELECT * FROM Lieu ORDER BY ville"; // Requete SQL qui selection les lieux et les tries par ville

        try (Connection conn = BaseDeDonnees.getConnexion(); // Ouvrir automatiquement la connexion
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) { // Convertit les infos en objets
                lieux.add(new Lieu(
                        rs.getInt("id_lieu"),
                        rs.getString("adresse"),
                        rs.getString("ville")
                ));
            }

        } catch (SQLException e) { // Message d'erreur avec une exeption
            System.err.println("Erreur chargement lieux : " + e.getMessage());
        }

        return lieux;
    }

    public boolean ajouterLieu(Lieu lieu) { // Requete d'ajout de lieu
        String sql = "INSERT INTO Lieu (adresse, ville) VALUES (?, ?)";

        try (Connection conn = BaseDeDonnees.getConnexion(); // Rempli les valeurs dynamiquements
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lieu.getAdresse());
            stmt.setString(2, lieu.getVille());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { // Erreur avec exeption
            System.err.println("Erreur ajout lieu : " + e.getMessage());
            return false;
        }
    }

    public boolean modifierLieu(Lieu lieu) { // Requete de modification d'un lieu
        String sql = "UPDATE Lieu SET adresse = ?, ville = ? WHERE id_lieu = ?";

        try (Connection conn = BaseDeDonnees.getConnexion(); // Assigne les nouvelles valeurs
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lieu.getAdresse());
            stmt.setString(2, lieu.getVille());
            stmt.setInt(3, lieu.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { // Erreur avec exeption
            System.err.println("Erreur modification lieu : " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerLieu(int id) { // Requete de suppresion d'un lieu
        String sql = "DELETE FROM Lieu WHERE id_lieu = ?";

        try (Connection conn = BaseDeDonnees.getConnexion(); // Execute la suppresion
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { // Erreur avec exeption
            System.err.println("Erreur suppression lieu : " + e.getMessage());
            return false;
        }
    }
}

