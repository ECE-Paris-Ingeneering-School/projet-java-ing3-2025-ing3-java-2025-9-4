package dao;


import modele.BaseDeDonnees;
import modele.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public Patient getPatientParEmailEtMotDePasse(String email, String mdp) {
        Patient patient = null;
        String sql = "SELECT * FROM Patient WHERE email = ? AND mot_de_passe = ?";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, mdp);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient(
                        rs.getInt("id_patient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getBoolean("ancien_patient"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur DAO login patient : " + e.getMessage());
        }

        return patient;
    }

    public List<Patient> getTousLesPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient ORDER BY nom";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id_patient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getBoolean("ancien_patient"),
                        rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erreur chargement patients : " + e.getMessage());
        }

        return patients;
    }

    public boolean ajouterPatient(Patient p) {
        String sql = "INSERT INTO Patient (nom, prenom, email, mot_de_passe, ancien_patient, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNom());
            stmt.setString(2, p.getPrenom());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getMotDePasse());
            stmt.setBoolean(5, p.isAncien());
            stmt.setString(6, p.getRole());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur ajout patient : " + e.getMessage());
            return false;
        }
    }

    public boolean modifierPatient(Patient p) {
        String sql = "UPDATE Patient SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, ancien_patient = ?, role = ? WHERE id_patient = ?";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNom());
            stmt.setString(2, p.getPrenom());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getMotDePasse());
            stmt.setBoolean(5, p.isAncien());
            stmt.setString(6, p.getRole());
            stmt.setInt(7, p.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur modification patient : " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerPatient(int id) {
        String sql = "DELETE FROM Patient WHERE id_patient = ?";

        try (Connection conn = BaseDeDonnees.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression patient : " + e.getMessage());
            return false;
        }
    }
}
