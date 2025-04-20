package dao; //appartient au package dao

// import les autres autres documents present dans les autres packages
import modele.BaseDeDonnees;
import modele.Specialiste;

//imports SQL
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialisteDAO {


    public List<Specialiste> getTousLesSpecialistes() { //recup les specialistes depuis la bdd
        List<Specialiste> liste = new ArrayList<>(); //a remplir avec les resultats
        String sql = "SELECT * FROM Specialiste"; //requete sql

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                //on crée un objet Specialiste à partir des colonnes
                liste.add(new Specialiste(
                        rs.getInt("id_specialiste"),
                        rs.getString("nom"),
                        rs.getString("specialisation"),
                        rs.getString("qualification")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erreur chargement spécialistes : " + e.getMessage());
        }

        return liste; //renvoie la liste
    }

    //ajoute un specialiste dans la bdd
    public boolean ajouterSpecialiste(Specialiste s) {
        String sql = "INSERT INTO Specialiste (nom, specialisation, qualification) VALUES (?, ?, ?)";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getNom());
            stmt.setString(2, s.getSpecialisation());
            stmt.setString(3, s.getQualification());
            return stmt.executeUpdate() > 0; //true si au moins 1 ligne ajoutée

        } catch (SQLException e) {
            System.err.println("Erreur ajout spécialiste : " + e.getMessage());
            return false;
        }
    }

    //modifie un specialiste dans la bdd (recherche pas id)
    public boolean modifierSpecialiste(Specialiste s) {
        String sql = "UPDATE Specialiste SET nom = ?, specialisation = ?, qualification = ? WHERE id_specialiste = ?";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getNom());
            stmt.setString(2, s.getSpecialisation());
            stmt.setString(3, s.getQualification());
            stmt.setInt(4, s.getId());
            return stmt.executeUpdate() > 0; //true si la modif a ete effectue

        } catch (SQLException e) {
            System.err.println("Erreur modification spécialiste : " + e.getMessage());
            return false;
        }
    }

    //supp un specialiste par son id
    public boolean supprimerSpecialiste(int id) {
        String sql = "DELETE FROM Specialiste WHERE id_specialiste = ?";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression spécialiste : " + e.getMessage());
            return false;
        }
    }
}
